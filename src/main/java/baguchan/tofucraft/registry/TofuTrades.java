package baguchan.tofucraft.registry;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MerchantOffer;
import net.minecraft.util.IItemProvider;

import java.util.Random;

public class TofuTrades {
	public static final Int2ObjectMap<VillagerTrades.ITrade[]> TRAVELER_TOFUNIAN_TRADE = gatAsIntMap(ImmutableMap.of(1, new VillagerTrades.ITrade[]{new ZundaRubyForItemsTrade(Blocks.GLASS, 8, 6, 5), new ZundaRubyForItemsTrade(Blocks.WHITE_WOOL, 9, 6, 5)},
			2, new VillagerTrades.ITrade[]{new ItemsForZundaRubyTrade(TofuItems.TOFUGRILLED, 1, 9, 5, 5), new ItemsForZundaRubyTrade(TofuItems.TOFUSTICK, 10, 1, 2, 5)}));

	private static Int2ObjectMap<VillagerTrades.ITrade[]> gatAsIntMap(ImmutableMap<Integer, VillagerTrades.ITrade[]> p_221238_0_) {
		return new Int2ObjectOpenHashMap<>(p_221238_0_);
	}

	static class ZundaRubyForItemsTrade implements VillagerTrades.ITrade {
		private final Item tradeItem;
		private final int count;
		private final int maxUses;
		private final int xpValue;
		private final float priceMultiplier;

		public ZundaRubyForItemsTrade(IItemProvider tradeItemIn, int countIn, int maxUsesIn, int xpValueIn) {
			this.tradeItem = tradeItemIn.asItem();
			this.count = countIn;
			this.maxUses = maxUsesIn;
			this.xpValue = xpValueIn;
			this.priceMultiplier = 0.05F;
		}

		public MerchantOffer getOffer(Entity trader, Random rand) {
			ItemStack itemstack = new ItemStack(this.tradeItem, this.count);
			return new MerchantOffer(itemstack, new ItemStack(TofuItems.ZUNDARUBY), this.maxUses, this.xpValue, this.priceMultiplier);
		}
	}

	static class ItemsForZundaRubyTrade implements VillagerTrades.ITrade {
		private final ItemStack sellingItem;
		private final int rubyCount;
		private final int sellingItemCount;
		private final int maxUses;
		private final int xpValue;
		private final float priceMultiplier;

		public ItemsForZundaRubyTrade(Block sellingItem, int rubyCount, int sellingItemCount, int maxUses, int xpValue) {
			this(new ItemStack(sellingItem), rubyCount, sellingItemCount, maxUses, xpValue);
		}

		public ItemsForZundaRubyTrade(Item sellingItem, int rubyCount, int sellingItemCount, int xpValue) {
			this(new ItemStack(sellingItem), rubyCount, sellingItemCount, 12, xpValue);
		}

		public ItemsForZundaRubyTrade(Item sellingItem, int rubyCount, int sellingItemCount, int maxUses, int xpValue) {
			this(new ItemStack(sellingItem), rubyCount, sellingItemCount, maxUses, xpValue);
		}

		public ItemsForZundaRubyTrade(ItemStack sellingItem, int rubyCount, int sellingItemCount, int maxUses, int xpValue) {
			this(sellingItem, rubyCount, sellingItemCount, maxUses, xpValue, 0.05F);
		}

		public ItemsForZundaRubyTrade(ItemStack sellingItem, int rubyCount, int sellingItemCount, int maxUses, int xpValue, float priceMultiplier) {
			this.sellingItem = sellingItem;
			this.rubyCount = rubyCount;
			this.sellingItemCount = sellingItemCount;
			this.maxUses = maxUses;
			this.xpValue = xpValue;
			this.priceMultiplier = priceMultiplier;
		}

		public MerchantOffer getOffer(Entity trader, Random rand) {
			return new MerchantOffer(new ItemStack(TofuItems.ZUNDARUBY, this.rubyCount), new ItemStack(this.sellingItem.getItem(), this.sellingItemCount), this.maxUses, this.xpValue, this.priceMultiplier);
		}
	}
}
