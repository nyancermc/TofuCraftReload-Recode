package baguchan.tofucraft.block;

import baguchan.tofucraft.registry.TofuBlocks;
import baguchan.tofucraft.world.dimension.TofuWorldTeleporter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BreakableBlock;
import net.minecraft.entity.Entity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class TofuPortalBlock extends BreakableBlock {
	private static final VoxelShape AABB = VoxelShapes.create(new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 0.8125F, 1.0F));

	public TofuPortalBlock(Block.Properties props) {
		super(props);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return AABB;
	}

	public boolean trySpawnPortal(World worldIn, BlockPos pos) {
		Size size = new Size((IWorld) worldIn, pos);
		if (size.isValid()) {
			size.placePortalBlocks();
			worldIn.playSound(null, pos, SoundEvents.BLOCK_END_PORTAL_SPAWN, SoundCategory.BLOCKS, 0.7F, 1.0F);
			return true;
		}
		Size size1 = new Size((IWorld) worldIn, pos);
		if (size1.isValid()) {
			size1.placePortalBlocks();
			worldIn.playSound(null, pos, SoundEvents.BLOCK_END_PORTAL_SPAWN, SoundCategory.BLOCKS, 1.0F, 1.0F);
			return true;
		}
		return false;
	}

	@Override
	@Deprecated
	public void neighborChanged(BlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
		boolean good = world.getBlockState(pos.down()).isSolid();

		for (Direction facing : Direction.Plane.HORIZONTAL) {
			if (!good) break;

			BlockState neighboringState = world.getBlockState(pos.offset(facing));

			good = neighboringState.getBlock() == TofuBlocks.GRILLEDTOFU || neighboringState == state;
		}

		if (!good) {
			world.playEvent(2001, pos, Block.getStateId(state));
			world.setBlockState(pos, TofuBlocks.SOYMILK.getDefaultState(), 0b11);
		}
	}

	@Override
	public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
		if (entityIn.func_242280_ah()) {
			entityIn.func_242279_ag();
		} else {
			attemptSendPlayer(entityIn, false);
		}
	}

	private static RegistryKey<World> getDestination(Entity entity) {
		RegistryKey<World> tofu_world = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("tofucraft:tofu_world"));

		return entity.getEntityWorld().getDimensionKey() != tofu_world
				? tofu_world : World.OVERWORLD;
	}

	public static void attemptSendPlayer(Entity entity, boolean forcedEntry) {
		if (!entity.isAlive() || entity.world.isRemote) {
			return;
		}

		if (entity.isPassenger() || entity.isBeingRidden() || !entity.isNonBoss()) {
			return;
		}

		if (!forcedEntry && entity.func_242280_ah()) {
			return;
		}

		RegistryKey<World> destination = getDestination(entity);
		ServerWorld serverWorld = entity.getEntityWorld().getServer().getWorld(destination);

		if (serverWorld == null)
			return;
		entity.world.getProfiler().startSection("tofu_portal");
		// set a cooldown before this can run again
		entity.func_242279_ag();
		entity.changeDimension(serverWorld, new TofuWorldTeleporter());
		entity.world.getProfiler().endSection();
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		int random = rand.nextInt(100);
		if (random == 0) {
			worldIn.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5F, rand.nextFloat() * 0.4F + 0.8F, false);
		}

		for (int i = 0; i < 4; ++i) {
			double xPos = pos.getX() + rand.nextFloat();
			double yPos = pos.getY() + 1D;
			double zPos = pos.getZ() + rand.nextFloat();
			double xSpeed = (rand.nextFloat() - 0.5D) * 0.5D;
			double ySpeed = rand.nextFloat();
			double zSpeed = (rand.nextFloat() - 0.5D) * 0.5D;

			worldIn.addParticle(ParticleTypes.PORTAL, xPos, yPos, zPos, xSpeed, ySpeed, zSpeed);
		}
	}

	public static class Size {
		private static final int MAX_SIZE = 12;

		private static final int MIN_SIZE = 1;

		private final IWorld world;

		private boolean valid = false;

		private BlockPos nw;

		private BlockPos se;

		public Size(IWorld world, BlockPos pos) {
			this.world = world;
			int east = getDistanceUntilEdge(pos, Direction.EAST);
			int west = getDistanceUntilEdge(pos, Direction.WEST);
			int north = getDistanceUntilEdge(pos, Direction.NORTH);
			int south = getDistanceUntilEdge(pos, Direction.SOUTH);
			int width = east + west - 1;
			int length = north + south - 1;
			if (width > 12 || length > 12)
				return;
			if (width < 1 || length < 1)
				return;
			BlockPos neCorner = pos.east(east).north(north);
			BlockPos nwCorner = pos.west(west).north(north);
			BlockPos seCorner = pos.east(east).south(south);
			BlockPos swCorner = pos.west(west).south(south);
			this.nw = nwCorner.add(1, 0, 1);
			this.se = seCorner.add(-1, 0, -1);
			int wallWidth = width + 2;
			int wallLength = length + 2;
			for (int y = 0; y <= 1; y++) {
				for (int x = 0; x < wallWidth; x++) {
					for (int z = 0; z < wallLength; z++) {
						if (((y == 0 && x != 0 && z != 0 && x != wallWidth - 1 && z != wallLength - 1) || (y == 1 && (x == 0 || z == 0 || x == wallWidth - 1 || z == wallLength - 1))) &&
								!isTofuBlock(world.getBlockState(nwCorner.down().add(x, y, z))))
							return;
					}
				}
			}
			this.valid = true;
		}

		int getDistanceUntilEdge(BlockPos pos, Direction facing) {
			int i;
			for (i = 0; i < 9; i++) {
				BlockPos blockpos = pos.offset(facing, i);
				if (!isEmptyBlock(this.world.getBlockState(blockpos)) || !isTofuBlock(this.world.getBlockState(blockpos.down())))
					break;
			}
			BlockState state = this.world.getBlockState(pos.offset(facing, i));
			return isTofuBlock(state) ? i : 0;
		}

		boolean isEmptyBlock(BlockState state) {
			return (state.getBlock() == TofuBlocks.SOYMILK);
		}

		boolean isTofuBlock(BlockState state) {
			return (state.getBlock() == TofuBlocks.GRILLEDTOFU);
		}

		public boolean isValid() {
			return this.valid;
		}

		void placePortalBlocks() {
			for (BlockPos portalPos : BlockPos.Mutable.getAllInBoxMutable(this.nw, this.se))
				this.world.setBlockState(portalPos, TofuBlocks.TOFU_PORTAL.getDefaultState(), 2);
		}
	}
}