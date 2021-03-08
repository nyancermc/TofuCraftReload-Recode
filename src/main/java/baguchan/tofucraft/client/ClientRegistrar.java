package baguchan.tofucraft.client;

import baguchan.tofucraft.client.render.*;
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
		RenderingRegistry.registerEntityRenderingHandler(TofuEntityTypes.TOFUNIAN, TofunianRender::new);
		RenderingRegistry.registerEntityRenderingHandler(TofuEntityTypes.TRAVELER_TOFUNIAN, TravelerTofunianRender::new);
		RenderingRegistry.registerEntityRenderingHandler(TofuEntityTypes.TOFUCOW, TofuCowRender::new);
		RenderingRegistry.registerEntityRenderingHandler(TofuEntityTypes.TOFUFISH, TofuFishRender::new);

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

		setRenderLayer(TofuBlocks.TOFUTORCH_KINU, RenderType.getCutout());
		setRenderLayer(TofuBlocks.TOFUTORCH_MOMEN, RenderType.getCutout());
		setRenderLayer(TofuBlocks.TOFUTORCH_ISHI, RenderType.getCutout());
		setRenderLayer(TofuBlocks.TOFUTORCH_METAL, RenderType.getCutout());

		setRenderLayer(TofuBlocks.WALLTOFUTORCH_KINU, RenderType.getCutout());
		setRenderLayer(TofuBlocks.WALLTOFUTORCH_MOMEN, RenderType.getCutout());
		setRenderLayer(TofuBlocks.WALLTOFUTORCH_ISHI, RenderType.getCutout());
		setRenderLayer(TofuBlocks.WALLTOFUTORCH_METAL, RenderType.getCutout());

		setRenderLayer(TofuBlocks.TOFULADDER_KINU, RenderType.getCutout());
		setRenderLayer(TofuBlocks.TOFULADDER_MOMEN, RenderType.getCutout());
		setRenderLayer(TofuBlocks.TOFULADDER_ISHI, RenderType.getCutout());
		setRenderLayer(TofuBlocks.TOFULADDER_ISHIBRICK, RenderType.getCutout());
		setRenderLayer(TofuBlocks.TOFULADDER_METAL, RenderType.getCutout());

		setRenderLayer(TofuBlocks.TOFU_FLOWER, RenderType.getCutout());
		setRenderLayer(TofuBlocks.BLOCKLEEK, RenderType.getCutout());
		setRenderLayer(TofuBlocks.ZUNDATOFU_MUSHROOM, RenderType.getCutout());

		setRenderLayer(TofuBlocks.TOFU_PORTAL, RenderType.getTranslucent());
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
