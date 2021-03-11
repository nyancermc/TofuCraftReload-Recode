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
	public static BlockClusterFeatureConfig NETHER_SOYBEAN_CLUSTER = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(TofuBlocks.SOYBEAN_SOUL.defaultBlockState().setValue(SoybeanSoulCropsBlock.AGE, 7)), SimpleBlockPlacer.INSTANCE).tries(64).noProjection().build());
	public static BlockClusterFeatureConfig SOUL_SOYBEAN_CLUSTER = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(TofuBlocks.SOYBEAN_SOUL.defaultBlockState().setValue(SoybeanSoulCropsBlock.AGE, 7)), SimpleBlockPlacer.INSTANCE).tries(64).noProjection().build());
	public static BlockClusterFeatureConfig TOFU_FLOWER_CLUSTER = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(TofuBlocks.TOFU_FLOWER.defaultBlockState()), SimpleBlockPlacer.INSTANCE).tries(64).build());


	public static final ConfiguredFeature<?, ?> NETHER_SOYBEAN = register("tofucraft:nether_soybean", Feature.RANDOM_PATCH.configured(NETHER_SOYBEAN_CLUSTER).decorated(Features.Placements.HEIGHTMAP_WORLD_SURFACE).range(128).chance(1));
	public static final ConfiguredFeature<?, ?> NETHER_SOYBEAN_PATCH = register("tofucraft:nether_soybean_patch", Feature.RANDOM_PATCH.configured(NETHER_SOYBEAN_CLUSTER).decorated(Features.Placements.HEIGHTMAP_WORLD_SURFACE).range(86).chance(3));
	public static final ConfiguredFeature<?, ?> SOUL_SOYBEAN = register("tofucraft:soul_soybean", Feature.RANDOM_PATCH.configured(SOUL_SOYBEAN_CLUSTER).decorated(Features.Placements.HEIGHTMAP_WORLD_SURFACE).range(128).chance(2));

	public static final ConfiguredFeature<?, ?> TOFU_FLOWER = register("tofucraft:tofu_flower", Feature.FLOWER.configured(TOFU_FLOWER_CLUSTER).decorated(Features.Placements.HEIGHTMAP_WORLD_SURFACE).decorated(Features.Placements.HEIGHTMAP).countRandom(2));

	public static final ConfiguredFeature<?, ?> SOYMILK_LAKE = register("tofucraft:soymilk_lake", Feature.LAKE.configured(new BlockStateFeatureConfig(TofuBlocks.SOYMILK.defaultBlockState())).decorated(Placement.WATER_LAKE.configured(new ChanceConfig(4))));
	public static final ConfiguredFeature<?, ?> SPRING_SOYMILK = register("tofucraft:spring_soymilk", Feature.SPRING.configured(new LiquidsConfig(TofuFluids.SOYMILK.defaultFluidState(), true, 4, 1, ImmutableSet.of(TofuBlocks.TOFU_TERRAIN))).decorated(Placement.RANGE_BIASED.configured(new TopSolidRangeConfig(8, 8, 256))).squared().countRandom(50));

	private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String name, ConfiguredFeature<FC, ?> p_243968_1_) {
		return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, name, p_243968_1_);
	}

	public static void init() {

	}
}
