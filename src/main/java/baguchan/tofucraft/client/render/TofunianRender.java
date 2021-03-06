package baguchan.tofucraft.client.render;

import baguchan.tofucraft.client.model.BipedTofunianModel;
import baguchan.tofucraft.client.render.layer.AdvancedHeldItemLayer;
import baguchan.tofucraft.entity.TofunianEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TofunianRender<T extends TofunianEntity> extends MobRenderer<T, BipedTofunianModel<T>> {
	private static final ResourceLocation TEXTURE = new ResourceLocation("tofucraft:textures/entity/tofunian/tofunian.png");

	public TofunianRender(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new BipedTofunianModel<>(), 0.5F);
		this.addLayer(new BipedArmorLayer(this, new BipedModel(0.25F), new BipedModel(0.5F)));
		this.addLayer(new HeadLayer<>(this));
		this.addLayer(new ElytraLayer<>(this));
		this.addLayer(new AdvancedHeldItemLayer<>(this));
	}

	@Override
	public ResourceLocation getEntityTexture(T entity) {
		return TEXTURE;
	}
}
