package baguchan.tofucraft;

import baguchan.tofucraft.world.TravelerTofunianSpawner;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = TofuCraftReload.MODID)
public class CommonEvents {
	private static final Map<ServerWorld, TravelerTofunianSpawner> TRAVELER_TOFUNIAN_SPAWNER_MAP = new HashMap<>();

	@SubscribeEvent
	public static void worldLoad(WorldEvent.Load evt) {
		if (!evt.getWorld().isRemote() && evt.getWorld() instanceof ServerWorld) {
			TRAVELER_TOFUNIAN_SPAWNER_MAP.put((ServerWorld) evt.getWorld(), new TravelerTofunianSpawner((ServerWorld) evt.getWorld()));
		}
	}

	@SubscribeEvent
	public static void worldUnload(WorldEvent.Unload evt) {
		if (!evt.getWorld().isRemote() && evt.getWorld() instanceof ServerWorld) {
			TRAVELER_TOFUNIAN_SPAWNER_MAP.remove(evt.getWorld());
		}
	}

	@SubscribeEvent
	public static void onServerTick(TickEvent.WorldTickEvent tick) {
		if (!tick.world.isRemote && tick.world instanceof ServerWorld) {
			ServerWorld serverWorld = (ServerWorld) tick.world;
			TravelerTofunianSpawner spawner = TRAVELER_TOFUNIAN_SPAWNER_MAP.get(serverWorld);
			if (spawner != null) {
				spawner.tick();
			}
		}

	}
}
