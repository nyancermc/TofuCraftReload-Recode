package baguchan.tofucraft.client;

import baguchan.tofucraft.registry.TofuBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@OnlyIn(Dist.CLIENT)
public class ClientRegistrar {
	public static void renderEntity() {
	}

	public static void renderTileEntity() {
	}

	public static void renderBlockColor() {
	}

	public static void renderBlockLayer() {
		setRenderLayer(TofuBlocks.SOYBEAN, RenderType.getCutout());
		setRenderLayer(TofuBlocks.SOYBEAN_NETHER, RenderType.getCutout());
		setRenderLayer(TofuBlocks.SOYBEAN_SOUL, RenderType.getCutout());
	}

	private static void setRenderLayer(Block block, RenderType type) {
		RenderTypeLookup.setRenderLayer(block, type::equals);
	}

	public static void setup(final FMLCommonSetupEvent event) {
		ClientRegistrar.renderEntity();
		ClientRegistrar.renderTileEntity();
		ClientRegistrar.renderBlockColor();
		ClientRegistrar.renderBlockLayer();
	}
}
