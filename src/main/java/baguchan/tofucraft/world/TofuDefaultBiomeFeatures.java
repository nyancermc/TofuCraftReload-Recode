package baguchan.tofucraft.world;

import baguchan.tofucraft.block.SoybeanSoulCropsBlock;
import baguchan.tofucraft.registry.TofuBlocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;

public class TofuDefaultBiomeFeatures {
	public static BlockClusterFeatureConfig NETHER_SOYBEAN_CLUSTER = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(TofuBlocks.SOYBEAN_SOUL.getDefaultState().with(SoybeanSoulCropsBlock.AGE, 7)), SimpleBlockPlacer.PLACER).tries(64).func_227317_b_().build());
	public static BlockClusterFeatureConfig SOUL_SOYBEAN_CLUSTER = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(TofuBlocks.SOYBEAN_SOUL.getDefaultState().with(SoybeanSoulCropsBlock.AGE, 7)), SimpleBlockPlacer.PLACER).tries(64).func_227317_b_().build());

	public static final ConfiguredFeature<?, ?> NETHER_SOYBEAN = register("tofucraft:nether_soybean", Feature.RANDOM_PATCH.withConfiguration(NETHER_SOYBEAN_CLUSTER).withPlacement(Features.Placements.VEGETATION_PLACEMENT).range(128).chance(1));
	public static final ConfiguredFeature<?, ?> NETHER_SOYBEAN_PATCH = register("tofucraft:nether_soybean_patch", Feature.RANDOM_PATCH.withConfiguration(NETHER_SOYBEAN_CLUSTER).withPlacement(Features.Placements.VEGETATION_PLACEMENT).range(86).chance(3));
	public static final ConfiguredFeature<?, ?> SOUL_SOYBEAN = register("tofucraft:soul_soybean", Feature.RANDOM_PATCH.withConfiguration(SOUL_SOYBEAN_CLUSTER).withPlacement(Features.Placements.VEGETATION_PLACEMENT).range(128).chance(2));

	private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String name, ConfiguredFeature<FC, ?> p_243968_1_) {
		return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, name, p_243968_1_);
	}

	public static void init() {

	}
}
