package baguchan.tofucraft.world;

import baguchan.tofucraft.block.SoybeanSoulCropsBlock;
import baguchan.tofucraft.registry.TofuBlocks;
import baguchan.tofucraft.registry.TofuFluids;
import com.google.common.collect.ImmutableSet;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;

public class TofuDefaultBiomeFeatures {
	public static BlockClusterFeatureConfig NETHER_SOYBEAN_CLUSTER = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(TofuBlocks.SOYBEAN_SOUL.getDefaultState().with(SoybeanSoulCropsBlock.AGE, 7)), SimpleBlockPlacer.PLACER).tries(64).func_227317_b_().build());
	public static BlockClusterFeatureConfig SOUL_SOYBEAN_CLUSTER = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(TofuBlocks.SOYBEAN_SOUL.getDefaultState().with(SoybeanSoulCropsBlock.AGE, 7)), SimpleBlockPlacer.PLACER).tries(64).func_227317_b_().build());

	public static final ConfiguredFeature<?, ?> NETHER_SOYBEAN = register("tofucraft:nether_soybean", Feature.RANDOM_PATCH.withConfiguration(NETHER_SOYBEAN_CLUSTER).withPlacement(Features.Placements.VEGETATION_PLACEMENT).range(128).chance(1));
	public static final ConfiguredFeature<?, ?> NETHER_SOYBEAN_PATCH = register("tofucraft:nether_soybean_patch", Feature.RANDOM_PATCH.withConfiguration(NETHER_SOYBEAN_CLUSTER).withPlacement(Features.Placements.VEGETATION_PLACEMENT).range(86).chance(3));
	public static final ConfiguredFeature<?, ?> SOUL_SOYBEAN = register("tofucraft:soul_soybean", Feature.RANDOM_PATCH.withConfiguration(SOUL_SOYBEAN_CLUSTER).withPlacement(Features.Placements.VEGETATION_PLACEMENT).range(128).chance(2));

	public static final ConfiguredFeature<?, ?> SOYMILK_LAKE = register("tofucraft:soymilk_lake", Feature.LAKE.withConfiguration(new BlockStateFeatureConfig(TofuBlocks.SOYMILK.getDefaultState())).withPlacement(Placement.WATER_LAKE.configure(new ChanceConfig(4))));
	public static final ConfiguredFeature<?, ?> SPRING_SOYMILK = register("tofucraft:spring_soymilk", Feature.SPRING_FEATURE.withConfiguration(new LiquidsConfig(TofuFluids.SOYMILK.getDefaultState(), true, 4, 1, ImmutableSet.of(TofuBlocks.TOFU_TERRAIN))).withPlacement(Placement.RANGE_BIASED.configure(new TopSolidRangeConfig(8, 8, 256))).square().func_242731_b(50));

	private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String name, ConfiguredFeature<FC, ?> p_243968_1_) {
		return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, name, p_243968_1_);
	}

	public static void init() {

	}
}
