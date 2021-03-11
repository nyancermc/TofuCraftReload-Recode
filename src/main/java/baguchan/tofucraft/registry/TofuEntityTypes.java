package baguchan.tofucraft.registry;

import baguchan.tofucraft.TofuCraftReload;
import baguchan.tofucraft.entity.TofuCowEntity;
import baguchan.tofucraft.entity.TofuFishEntity;
import baguchan.tofucraft.entity.TofunianEntity;
import baguchan.tofucraft.entity.TravelerTofunianEntity;
import baguchan.tofucraft.entity.projectile.FukumameEntity;
import baguchan.tofucraft.entity.projectile.NetherFukumameEntity;
import baguchan.tofucraft.entity.projectile.SoulFukumameEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TofuCraftReload.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TofuEntityTypes {
	public static final EntityType<TofunianEntity> TOFUNIAN = EntityType.Builder.of(TofunianEntity::new, EntityClassification.CREATURE)
			.sized(0.6F, 1.2F).build(TofuCraftReload.MODID + ":tofunian");
	public static final EntityType<TravelerTofunianEntity> TRAVELER_TOFUNIAN = EntityType.Builder.of(TravelerTofunianEntity::new, EntityClassification.CREATURE)
			.sized(0.6F, 1.2F).build(TofuCraftReload.MODID + ":traveler_tofunian");
	public static final EntityType<TofuCowEntity> TOFUCOW = EntityType.Builder.of(TofuCowEntity::new, EntityClassification.CREATURE)
			.sized(0.9F, 1.4F).build(TofuCraftReload.MODID + ":tofucow");
	public static final EntityType<TofuFishEntity> TOFUFISH = EntityType.Builder.of(TofuFishEntity::new, EntityClassification.WATER_AMBIENT)
			.sized(0.5F, 0.3F).setTrackingRange(4).build(TofuCraftReload.MODID + ":tofufish");

	public static final EntityType<FukumameEntity> FUKUMAME = EntityType.Builder.<FukumameEntity>of(FukumameEntity::new, EntityClassification.MISC)
			.sized(0.25F, 0.25F).build(TofuCraftReload.MODID + ":fukumame");
	public static final EntityType<NetherFukumameEntity> NETHER_FUKUMAME = EntityType.Builder.<NetherFukumameEntity>of(NetherFukumameEntity::new, EntityClassification.MISC)
			.sized(0.25F, 0.25F).build(TofuCraftReload.MODID + ":nether_fukumame");
	public static final EntityType<SoulFukumameEntity> SOUL_FUKUMAME = EntityType.Builder.<SoulFukumameEntity>of(SoulFukumameEntity::new, EntityClassification.MISC)
			.sized(0.25F, 0.25F).build(TofuCraftReload.MODID + ":soul_fukumame");

	@SubscribeEvent
	public static void registerEntityTypes(RegistryEvent.Register<EntityType<?>> registry) {

		registry.getRegistry().register(TOFUNIAN.setRegistryName("tofunian"));
		registry.getRegistry().register(TRAVELER_TOFUNIAN.setRegistryName("traveler_tofunian"));
		registry.getRegistry().register(TOFUCOW.setRegistryName("tofucow"));
		registry.getRegistry().register(TOFUFISH.setRegistryName("tofufish"));

		GlobalEntityTypeAttributes.put(TOFUNIAN, TofunianEntity.registerAttributes().build());
		GlobalEntityTypeAttributes.put(TRAVELER_TOFUNIAN, TravelerTofunianEntity.registerAttributes().build());
		GlobalEntityTypeAttributes.put(TOFUCOW, TofuCowEntity.registerAttributes().build());
		GlobalEntityTypeAttributes.put(TOFUFISH, AbstractFishEntity.createAttributes().build());

		EntitySpawnPlacementRegistry.register(TOFUNIAN, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::checkMobSpawnRules);
		EntitySpawnPlacementRegistry.register(TRAVELER_TOFUNIAN, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::checkMobSpawnRules);
		EntitySpawnPlacementRegistry.register(TOFUCOW, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, TofuCowEntity::canTofuCowSpawn);
		EntitySpawnPlacementRegistry.register(TOFUFISH, EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, TofuFishEntity::canTofuFishSpawnOn);

		registry.getRegistry().register(FUKUMAME.setRegistryName("fukumame"));
		registry.getRegistry().register(NETHER_FUKUMAME.setRegistryName("nether_fukumame"));
		registry.getRegistry().register(SOUL_FUKUMAME.setRegistryName("soul_fukumame"));
	}
}
