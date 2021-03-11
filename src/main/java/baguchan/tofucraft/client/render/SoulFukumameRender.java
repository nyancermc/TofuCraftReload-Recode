package baguchan.tofucraft.client.render;

import baguchan.tofucraft.TofuCraftReload;
import baguchan.tofucraft.entity.projectile.SoulFukumameEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class SoulFukumameRender<T extends SoulFukumameEntity> extends FukumameRender<T> {
	public static final ResourceLocation FUKUMAME_TEXTURE = new ResourceLocation(TofuCraftReload.MODID, "textures/entity/projectiles/soul_fukumame.png");

	public SoulFukumameRender(EntityRendererManager manager) {
		super(manager);
	}

	@Override
	public ResourceLocation getTextureLocation(T entity) {
		return FUKUMAME_TEXTURE;
	}
}
