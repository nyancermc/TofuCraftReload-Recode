package baguchan.tofucraft.registry;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TofuItemGroup {
	public static final ItemGroup TOFUCRAFT = new ItemGroup("tofucraft") {
		@OnlyIn(Dist.CLIENT)
		public ItemStack createIcon() {
			/* 13 */         return new ItemStack(TofuItems.TOFUMOMEN);
			/*    */       }
	};
}
