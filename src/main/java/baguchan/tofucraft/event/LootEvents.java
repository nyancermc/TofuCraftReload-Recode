package baguchan.tofucraft.event;

import baguchan.tofucraft.TofuCraftReload;
import com.google.common.collect.Sets;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
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

	private static final Set<ResourceLocation> TEMPLE_LOOT = Sets.newHashSet(LootTables.CHESTS_JUNGLE_TEMPLE);
	private static final Set<ResourceLocation> SHIP_LOOT = Sets.newHashSet(LootTables.CHESTS_SHIPWRECK_SUPPLY);
	private static final Set<ResourceLocation> RUIN_LOOT = Sets.newHashSet(LootTables.CHESTS_UNDERWATER_RUIN_BIG);


	@SubscribeEvent
	public static void onInjectLoot(LootTableLoadEvent event) {
		if (GRASS_LOOT.contains(event.getName())) {
			LootPool pool = LootPool.builder().addEntry(TableLootEntry.builder(new ResourceLocation(TofuCraftReload.MODID, "injections/grass")).weight(1).quality(1)).name("tofustick_grass").build();
			event.getTable().addPool(pool);
		}

		if (TALL_GRASS_LOOT.contains(event.getName())) {
			LootPool pool = LootPool.builder().addEntry(TableLootEntry.builder(new ResourceLocation(TofuCraftReload.MODID, "injections/tall_grass")).weight(1).quality(1)).name("tofustick_grass").build();
			event.getTable().addPool(pool);
		}

		if (TEMPLE_LOOT.contains(event.getName())) {
			LootPool pool = LootPool.builder().addEntry(TableLootEntry.builder(new ResourceLocation(TofuCraftReload.MODID, "injections/tofustick_temple")).weight(1).quality(1)).name("tofustick_temple").build();
			event.getTable().addPool(pool);
		}

		if (SHIP_LOOT.contains(event.getName())) {
			LootPool pool = LootPool.builder().addEntry(TableLootEntry.builder(new ResourceLocation(TofuCraftReload.MODID, "injections/tofustick_ship")).weight(1).quality(1)).name("tofustick_ship").build();
			event.getTable().addPool(pool);
		}

		if (RUIN_LOOT.contains(event.getName())) {
			LootPool pool = LootPool.builder().addEntry(TableLootEntry.builder(new ResourceLocation(TofuCraftReload.MODID, "injections/tofustick_ruin")).weight(1).quality(1)).name("tofustick_ruin").build();
			event.getTable().addPool(pool);
		}
	}
}