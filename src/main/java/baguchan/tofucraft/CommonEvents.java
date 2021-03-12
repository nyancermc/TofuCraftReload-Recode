package baguchan.tofucraft;

import baguchan.tofucraft.registry.TofuItems;
import baguchan.tofucraft.world.TravelerTofunianSpawner;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.world.BlockEvent;
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
		if (!evt.getWorld().isClientSide() && evt.getWorld() instanceof ServerWorld) {
			TRAVELER_TOFUNIAN_SPAWNER_MAP.put((ServerWorld) evt.getWorld(), new TravelerTofunianSpawner((ServerWorld) evt.getWorld()));
		}
	}

	@SubscribeEvent
	public static void worldUnload(WorldEvent.Unload evt) {
		if (!evt.getWorld().isClientSide() && evt.getWorld() instanceof ServerWorld) {
			TRAVELER_TOFUNIAN_SPAWNER_MAP.remove(evt.getWorld());
		}
	}

	@SubscribeEvent
	public static void onServerTick(TickEvent.WorldTickEvent tick) {
		if (!tick.world.isClientSide && tick.world instanceof ServerWorld) {
			ServerWorld serverWorld = (ServerWorld) tick.world;
			TravelerTofunianSpawner spawner = TRAVELER_TOFUNIAN_SPAWNER_MAP.get(serverWorld);
			if (spawner != null) {
				spawner.tick();
			}
		}
	}

	@SubscribeEvent
	public static void onBlockDrop(BlockEvent.BreakEvent event) {
		if (!event.getPlayer().isCreative()) {
			if (event.getWorld().getBlockState(event.getPos()).is(Blocks.GRASS) || event.getWorld().getBlockState(event.getPos()).is(Blocks.TALL_GRASS)) {
				if (event.getWorld() instanceof World && ((World) event.getWorld()).random.nextFloat() < 0.075F) {
					ItemEntity entity = new ItemEntity((World) event.getWorld(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), new ItemStack(TofuItems.SEEDS_SOYBEANS));
					event.getWorld().addFreshEntity(entity);
				}
			}
		}
	}
}
