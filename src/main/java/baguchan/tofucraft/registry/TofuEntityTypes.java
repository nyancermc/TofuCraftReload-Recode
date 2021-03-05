package baguchan.tofucraft.registry;

import baguchan.tofucraft.TofuCraftReload;
import baguchan.tofucraft.entity.TofunianEntity;
import baguchan.tofucraft.entity.projectile.FukumameEntity;
import baguchan.tofucraft.entity.projectile.NetherFukumameEntity;
import baguchan.tofucraft.entity.projectile.SoulFukumameEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TofuCraftReload.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TofuEntityTypes {
	public static final EntityType<TofunianEntity> TOFUNIAN = EntityType.Builder.create(TofunianEntity::new, EntityClassification.CREATURE)
			.size(0.6F, 1.2F).build(TofuCraftReload.MODID + ":tofunian");

	public static final EntityType<FukumameEntity> FUKUMAME = EntityType.Builder.<FukumameEntity>create(FukumameEntity::new, EntityClassification.MISC)
			.size(0.25F, 0.25F).build(TofuCraftReload.MODID + ":fukumame");
	public static final EntityType<NetherFukumameEntity> NETHER_FUKUMAME = EntityType.Builder.<NetherFukumameEntity>create(NetherFukumameEntity::new, EntityClassification.MISC)
			.size(0.25F, 0.25F).build(TofuCraftReload.MODID + ":nether_fukumame");
	public static final EntityType<SoulFukumameEntity> SOUL_FUKUMAME = EntityType.Builder.<SoulFukumameEntity>create(SoulFukumameEntity::new, EntityClassification.MISC)
			.size(0.25F, 0.25F).build(TofuCraftReload.MODID + ":soul_fukumame");

	@SubscribeEvent
	public static void registerEntityTypes(RegistryEvent.Register<EntityType<?>> registry) {

		registry.getRegistry().register(TOFUNIAN.setRegistryName("tofunian"));

		GlobalEntityTypeAttributes.put(TOFUNIAN, TofunianEntity.registerAttributes().create());
		EntitySpawnPlacementRegistry.register(TOFUNIAN, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::canSpawnOn);

		registry.getRegistry().register(FUKUMAME.setRegistryName("fukumame"));
		registry.getRegistry().register(NETHER_FUKUMAME.setRegistryName("nether_fukumame"));
		registry.getRegistry().register(SOUL_FUKUMAME.setRegistryName("soul_fukumame"));
	}
}
