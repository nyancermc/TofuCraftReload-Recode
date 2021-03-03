package baguchan.tofucraft.block;

import baguchan.tofucraft.api.HardenRecipes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Map;
import java.util.Random;

public class TofuBlock extends Block {
	public static final IntegerProperty AGE = BlockStateProperties.AGE_0_7;

	public TofuBlock(Properties properties) {
		super(properties);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		super.animateTick(stateIn, worldIn, pos, rand);
		if (isUnderWeight(worldIn, pos)) {
			if (rand.nextInt(5) == 0) {
				double d4 = rand.nextBoolean() ? 0.8 : -0.8;
				double d0 = ((float) pos.getX() + 0.5 + (rand.nextFloat() * d4));
				double d1 = (double) ((float) pos.getY() + rand.nextFloat());
				double d2 = ((float) pos.getZ() + 0.5 + rand.nextFloat() * d4);

				worldIn.addParticle(ParticleTypes.DRIPPING_WATER, d0, d1, d2, 0.0D, 0.0D, 0.0D);
			}
		}
	}


	@Override
	public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
		super.tick(state, worldIn, pos, random);
		if (isUnderWeight(worldIn, pos)) {
			int i = state.get(AGE);
			if (random.nextInt(5) == 0) {
				if (i < 7) {
					worldIn.setBlockState(pos, state.with(AGE, Integer.valueOf(i + 1)), 2);
				} else {
					Map.Entry<Block, Block> result = HardenRecipes.getResult(state.getBlock());

					if (result != null) {
						worldIn.setBlockState(pos, result.getValue().getDefaultState(), 2);
					}
				}
			}
		}
	}

	public boolean isUnderWeight(World world, BlockPos pos) {
		BlockState weightBlock = world.getBlockState(pos.up());

		BlockState baseBlock = world.getBlockState(pos.down());

		boolean isWeightValid = weightBlock != null && (weightBlock.getMaterial() == Material.ROCK || weightBlock.getMaterial() == Material.IRON);

		float baseHardness = baseBlock.getBlockHardness(world, pos.down());

		boolean isBaseValid = baseBlock.isNormalCube(world, pos) && (baseBlock.getMaterial() == Material.ROCK || baseBlock.getMaterial() == Material.IRON || baseHardness >= 1.0F || baseHardness < 0.0F);

		return isWeightValid && isBaseValid;
	}

	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(AGE);
	}
}
