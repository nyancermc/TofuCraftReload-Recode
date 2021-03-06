package baguchan.tofucraft.block;

import baguchan.tofucraft.registry.TofuFeatures;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class TofuMushroomBlock extends TofuBushBlock implements IGrowable {
	protected static final VoxelShape SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 11.0D, 11.0D);

	public TofuMushroomBlock(Properties properties) {
		super(properties);
	}

	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}

	public boolean placeMushroom(ServerWorld world, BlockPos pos, BlockState state, Random rand) {
		world.removeBlock(pos, false);
		ConfiguredFeature<?, ?> configuredfeature;

		if (rand.nextInt(3) == 0) {
			configuredfeature = TofuFeatures.ZUNDA_MUSHROOM_BIG.configured(NoFeatureConfig.INSTANCE);
		} else {
			configuredfeature = TofuFeatures.ZUNDA_MUSHROOM_SMALL.configured(NoFeatureConfig.INSTANCE);
		}


		if (configuredfeature.place(world, world.getChunkSource().getGenerator(), rand, pos)) {
			return true;
		} else {
			world.setBlock(pos, state, 3);
			return false;
		}
	}

	/**
	 * Whether this IGrowable can performBonemeal
	 */
	public boolean isValidBonemealTarget(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
		return true;
	}

	public boolean isBonemealSuccess(World worldIn, Random rand, BlockPos pos, BlockState state) {
		return (double) worldIn.random.nextFloat() < 0.45D;
	}

	public void performBonemeal(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
		this.placeMushroom(worldIn, pos, state, rand);
	}
}
