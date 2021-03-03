package baguchan.tofucraft.event;

import baguchan.tofucraft.TofuCraftReload;
import com.google.common.collect.Sets;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.TableLootEntry;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;

@Mod.EventBusSubscriber(modid = TofuCraftReload.MODID)
public class LootEvents {
	private static final Set<ResourceLocation> GRASS_LOOT = Sets.newHashSet(new ResourceLocation("minecraft:grass"));
	private static final Set<ResourceLocation> TALL_GRASS_LOOT = Sets.newHashSet(new ResourceLocation("minecraft:tall_grass"));


	@SubscribeEvent
	public static void onInjectLoot(LootTableLoadEvent event) {
		if (GRASS_LOOT.contains(event.getName())) {
			LootPool pool = LootPool.builder().addEntry(TableLootEntry.builder(new ResourceLocation(TofuCraftReload.MODID, "injections/grass")).weight(1).quality(1)).name("tofustick_structure").build();
			event.getTable().addPool(pool);
		}

		if (TALL_GRASS_LOOT.contains(event.getName())) {
			LootPool pool = LootPool.builder().addEntry(TableLootEntry.builder(new ResourceLocation(TofuCraftReload.MODID, "injections/tall_grass")).weight(1).quality(1)).name("tofustick_structure").build();
			event.getTable().addPool(pool);
		}
	}
}