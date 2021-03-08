package baguchan.tofucraft.world.gen.feature;

import baguchan.tofucraft.registry.TofuBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class BigLeekFeature extends Feature<NoFeatureConfig> {
	private static final Direction[] directionArray = Direction.values();
	private static final BlockState GREEN_LEEK = TofuBlocks.LEEK_GREEN_STEM.getDefaultState();
	private static final BlockState LEEK = TofuBlocks.LEEK_STEM.getDefaultState();

	public BigLeekFeature(Codec<NoFeatureConfig> p_i49919_1_) {
		super(p_i49919_1_);
	}


	public boolean generate(ISeedReader seedReader, ChunkGenerator chunkGenerator, Random rand, BlockPos pos, NoFeatureConfig config) {
		BlockPos pos2 = seedReader.getHeight(Heightmap.Type.MOTION_BLOCKING, pos);


		if (!seedReader.isAirBlock(pos2)) {
			return false;
		}
		BlockState blockstate = seedReader.getBlockState(pos2.down());
		if (!blockstate.isIn(TofuBlocks.TOFU_TERRAIN)) {
			return false;
		}
		setLeekBlock((IWorld) seedReader, rand, pos2);
		return true;
	}


	private void setLeekBlock(IWorld world, Random rand, BlockPos pos) {
		int height = 4 + rand.nextInt(4);

		for (int i = 0; i < height; i++) {
			if ((height - i) < height / 2.5D) {
				world.setBlockState(pos.up(i), GREEN_LEEK, 2);
			} else {
				world.setBlockState(pos.up(i), LEEK, 2);
			}
		}
	}
}