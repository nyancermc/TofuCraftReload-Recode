package baguchan.tofucraft.client.render;

import baguchan.tofucraft.TofuCraftReload;
import baguchan.tofucraft.entity.projectile.NetherFukumameEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class NetherFukumameRender<T extends NetherFukumameEntity> extends FukumameRender<T> {
	public static final ResourceLocation FUKUMAME_TEXTURE = new ResourceLocation(TofuCraftReload.MODID, "textures/entity/projectiles/nether_fukumame.png");

	public NetherFukumameRender(EntityRendererManager manager) {
		super(manager);
	}

	@Override
	public ResourceLocation getTextureLocation(T entity) {
		return FUKUMAME_TEXTURE;
	}
}
