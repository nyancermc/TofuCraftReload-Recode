package baguchan.tofucraft.client;

import baguchan.tofucraft.client.render.FukumameRender;
import baguchan.tofucraft.client.render.NetherFukumameRender;
import baguchan.tofucraft.client.render.SoulFukumameRender;
import baguchan.tofucraft.registry.TofuBlocks;
import baguchan.tofucraft.registry.TofuEntityTypes;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@OnlyIn(Dist.CLIENT)
public class ClientRegistrar {
	public static void renderEntity() {
		RenderingRegistry.registerEntityRenderingHandler(TofuEntityTypes.FUKUMAME, FukumameRender::new);
		RenderingRegistry.registerEntityRenderingHandler(TofuEntityTypes.NETHER_FUKUMAME, NetherFukumameRender::new);
		RenderingRegistry.registerEntityRenderingHandler(TofuEntityTypes.SOUL_FUKUMAME, SoulFukumameRender::new);
	}

	public static void renderTileEntity() {
	}

	public static void renderBlockColor() {
		Minecraft.getInstance().getBlockColors().register((state, reader, pos, color) -> {
			return reader != null && pos != null ? BiomeColors.getWaterColor(reader, pos) : -1;
		}, TofuBlocks.SALTPAN);
	}

	public static void renderBlockLayer() {
		setRenderLayer(TofuBlocks.SOYBEAN, RenderType.getCutout());
		setRenderLayer(TofuBlocks.SOYBEAN_NETHER, RenderType.getCutout());
		setRenderLayer(TofuBlocks.SOYBEAN_SOUL, RenderType.getCutout());

		setRenderLayer(TofuBlocks.SALTPAN, RenderType.getCutout());
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
