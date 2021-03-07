package baguchan.tofucraft.client.render;

import baguchan.tofucraft.entity.TofuFishEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.CodModel;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TofuFishRender extends MobRenderer<TofuFishEntity, CodModel<TofuFishEntity>> {
	private static final ResourceLocation TEXTURE = new ResourceLocation("tofucraft:textures/entity/tofufish.png");

	public TofuFishRender(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new CodModel<>(), 0.3F);
	}

	protected void applyRotations(TofuFishEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
		super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
		float f = 4.3F * MathHelper.sin(0.6F * ageInTicks);
		matrixStackIn.rotate(Vector3f.YP.rotationDegrees(f));
		if (!entityLiving.isInWater()) {
			matrixStackIn.translate((double) 0.1F, (double) 0.1F, (double) -0.1F);
			matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(90.0F));
		}

	}

	public ResourceLocation getEntityTexture(TofuFishEntity entity) {
		return TEXTURE;
	}
}
