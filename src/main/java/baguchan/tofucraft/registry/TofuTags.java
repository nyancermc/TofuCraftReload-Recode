package baguchan.tofucraft.registry;

import baguchan.tofucraft.TofuCraftReload;
import net.minecraft.fluid.Fluid;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

public class TofuTags {
	public static class Fluids {
		public static final ITag.INamedTag<Fluid> SOYMILK = tag("soymilk");

		private static ITag.INamedTag<Fluid> tag(String name) {
			return FluidTags.makeWrapperTag(TofuCraftReload.MODID + ":" + name);
		}
	}
}