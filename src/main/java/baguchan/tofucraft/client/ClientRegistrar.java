package baguchan.tofucraft.client;

import baguchan.tofucraft.client.render.*;
import baguchan.tofucraft.client.render.tileentity.TofuBedBlockRenderer;
import baguchan.tofucraft.registry.TofuBlocks;
import baguchan.tofucraft.registry.TofuEntityTypes;
import baguchan.tofucraft.registry.TofuTileEntitys;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = "tofucraft", value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientRegistrar {
	public static void renderEntity() {
		RenderingRegistry.registerEntityRenderingHandler(TofuEntityTypes.TOFUNIAN, TofunianRender::new);
		RenderingRegistry.registerEntityRenderingHandler(TofuEntityTypes.TRAVELER_TOFUNIAN, TravelerTofunianRender::new);
		RenderingRegistry.registerEntityRenderingHandler(TofuEntityTypes.TOFUCOW, TofuCowRender::new);
		RenderingRegistry.registerEntityRenderingHandler(TofuEntityTypes.TOFUFISH, TofuFishRender::new);
		RenderingRegistry.registerEntityRenderingHandler(TofuEntityTypes.TOFUSLIME, TofuSlimeRender::new);

		RenderingRegistry.registerEntityRenderingHandler(TofuEntityTypes.FUKUMAME, FukumameRender::new);
		RenderingRegistry.registerEntityRenderingHandler(TofuEntityTypes.NETHER_FUKUMAME, NetherFukumameRender::new);
		RenderingRegistry.registerEntityRenderingHandler(TofuEntityTypes.SOUL_FUKUMAME, SoulFukumameRender::new);
	}

	public static void renderTileEntity() {
		ClientRegistry.bindTileEntityRenderer(TofuTileEntitys.TOFUBED, TofuBedBlockRenderer::new);
	}

	public static void renderBlockColor() {
		Minecraft.getInstance().getBlockColors().register((state, reader, pos, color) -> {
			return reader != null && pos != null ? BiomeColors.getAverageWaterColor(reader, pos) : -1;
		}, TofuBlocks.SALTPAN);
	}

	public static void renderBlockLayer() {
		setRenderLayer(TofuBlocks.SOYBEAN, RenderType.cutout());
		setRenderLayer(TofuBlocks.SOYBEAN_NETHER, RenderType.cutout());
		setRenderLayer(TofuBlocks.SOYBEAN_SOUL, RenderType.cutout());

		setRenderLayer(TofuBlocks.TOFUTORCH_KINU, RenderType.cutout());
		setRenderLayer(TofuBlocks.TOFUTORCH_MOMEN, RenderType.cutout());
		setRenderLayer(TofuBlocks.TOFUTORCH_ISHI, RenderType.cutout());
		setRenderLayer(TofuBlocks.TOFUTORCH_METAL, RenderType.cutout());

		setRenderLayer(TofuBlocks.WALLTOFUTORCH_KINU, RenderType.cutout());
		setRenderLayer(TofuBlocks.WALLTOFUTORCH_MOMEN, RenderType.cutout());
		setRenderLayer(TofuBlocks.WALLTOFUTORCH_ISHI, RenderType.cutout());
		setRenderLayer(TofuBlocks.WALLTOFUTORCH_METAL, RenderType.cutout());

		setRenderLayer(TofuBlocks.TOFULADDER_KINU, RenderType.cutout());
		setRenderLayer(TofuBlocks.TOFULADDER_MOMEN, RenderType.cutout());
		setRenderLayer(TofuBlocks.TOFULADDER_ISHI, RenderType.cutout());
		setRenderLayer(TofuBlocks.TOFULADDER_ISHIBRICK, RenderType.cutout());
		setRenderLayer(TofuBlocks.TOFULADDER_METAL, RenderType.cutout());

		setRenderLayer(TofuBlocks.TOFU_FLOWER, RenderType.cutout());
		setRenderLayer(TofuBlocks.POTTED_TOFU_FLOWER, RenderType.cutout());
		setRenderLayer(TofuBlocks.BLOCKLEEK, RenderType.cutout());
		setRenderLayer(TofuBlocks.ZUNDATOFU_MUSHROOM, RenderType.cutout());

		setRenderLayer(TofuBlocks.TOFU_PORTAL, RenderType.translucent());
		setRenderLayer(TofuBlocks.SALTPAN, RenderType.cutout());
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

	@SubscribeEvent
	public static void onTextureStitch(TextureStitchEvent.Pre event) {
		if (event.getMap().location().equals(Atlases.BED_SHEET)) {
			event.addSprite(TofuBedBlockRenderer.TOFUBED_LOCATION);
		}
	}
}
