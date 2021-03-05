package baguchan.tofucraft.client.model;

import baguchan.tofucraft.entity.TofunianEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class TofunianModel<T extends TofunianEntity> extends BipedModel<T> {
	public TofunianModel() {
		this(0.0F, false);
	}

	protected TofunianModel(float modelSize, float yOffsetIn, int textureWidthIn, int textureHeightIn) {
		super(modelSize, yOffsetIn, textureWidthIn, textureHeightIn);
	}

	public TofunianModel(float modelSize, boolean par2) {
		super(modelSize, 0.0F, 64, par2 ? 32 : 64);
		this.textureWidth = 64;
		this.textureHeight = 64;
		this.bipedHeadwear = new ModelRenderer(this, 32, 0);
		this.bipedHeadwear.setRotationPoint(0.0F, 13.0F, -0.0F);
		this.bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, modelSize + 0.5F);
		this.bipedHead = new ModelRenderer(this, 0, 0);
		this.bipedHead.setRotationPoint(0.0F, 13.0F, -0.0F);
		this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, modelSize);
		this.bipedHead.addBox(-1.5F, -11.0F, -0.0F, 3.0F, 3.0F, 0.0F, modelSize);
		this.bipedRightArm = new ModelRenderer(this, 28, 16);
		this.bipedRightArm.setRotationPoint(-4.0F, 15.0F, 0.0F);
		this.bipedRightArm.addBox(-1.0F, -1.0F, -1.0F, 2.0F, 5.0F, 2.0F, modelSize);
		this.setRotateAngle(bipedRightArm, 0.0F, 0.0F, 0.10000736647217022F);
		this.bipedBody = new ModelRenderer(this, 8, 16);
		this.bipedBody.setRotationPoint(0.0F, 14.0F, 0.0F);
		this.bipedBody.addBox(-3.0F, 0.0F, -2.0F, 6.0F, 6.0F, 4.0F, modelSize);
		this.bipedLeftArm = new ModelRenderer(this, 28, 16);
		this.bipedLeftArm.mirror = true;
		this.bipedLeftArm.setRotationPoint(4.0F, 15.0F, 0.0F);
		this.bipedLeftArm.addBox(-1.0F, -1.0F, -1.0F, 2.0F, 5.0F, 2.0F, modelSize);
		this.setRotateAngle(bipedLeftArm, 0.0F, 0.0F, -0.10000736647217022F);
		this.bipedRightLeg = new ModelRenderer(this, 0, 16);
		this.bipedRightLeg.setRotationPoint(-1.4F, 18.0F, 0.0F);
		this.bipedRightLeg.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, modelSize);
		this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
		this.bipedLeftLeg.mirror = true;
		this.bipedLeftLeg.setRotationPoint(1.4F, 18.0F, 0.0F);
		this.bipedLeftLeg.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, modelSize);
	}

	@Override
	public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		ImmutableList.of(this.bipedHeadwear, this.bipedHead, this.bipedRightArm, this.bipedBody, this.bipedLeftArm, this.bipedRightLeg, this.bipedLeftLeg).forEach((modelRenderer) -> {
			modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		});
	}

	@Override
	public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

		float f6 = 12.0f;

		this.bipedRightArm.rotationPointX = -4.0F;
		this.bipedLeftArm.rotationPointX = 4.0F;
		this.bipedRightArm.rotationPointZ = 0.0F;
		this.bipedLeftArm.rotationPointZ = 0.0F;
		this.bipedRightLeg.rotationPointZ = 0.0F;
		this.bipedLeftLeg.rotationPointZ = 0.0F;
		this.bipedRightLeg.rotationPointY = 6.0F + f6;
		this.bipedLeftLeg.rotationPointY = 6.0F + f6;
		this.bipedHead.rotationPointZ = -0.0F;
		this.bipedHead.rotationPointY = f6 + 2.0F;
		if (this.isSneak) {
			this.bipedBody.rotateAngleX = 0.5F;
			this.bipedRightArm.rotateAngleX += 0.4F;
			this.bipedLeftArm.rotateAngleX += 0.4F;
			this.bipedRightLeg.rotationPointZ = 3.9F;
			this.bipedLeftLeg.rotationPointZ = 3.9F;
			this.bipedRightLeg.rotationPointY = 6.0F + f6 + 0.2F;
			this.bipedLeftLeg.rotationPointY = 6.0F + f6 + 0.2F;
			this.bipedHead.rotationPointY = f6 + 2.0F + 4.2F;
			this.bipedBody.rotationPointY = 14.0F + 3.2F;
			this.bipedLeftArm.rotationPointY = 3.2F + 15.0F;
			this.bipedRightArm.rotationPointY = 3.2F + 15.0F;
		} else {
			this.bipedBody.rotateAngleX = 0.0F;
			this.bipedRightLeg.rotationPointZ = 0.0F;
			this.bipedLeftLeg.rotationPointZ = 0.0F;
			this.bipedRightLeg.rotationPointY = 6.0F + f6;
			this.bipedLeftLeg.rotationPointY = 6.0F + f6;
			this.bipedHead.rotationPointY = f6 + 2.0F;
			this.bipedBody.rotationPointY = 14.0F;
			this.bipedLeftArm.rotationPointY = 15.0F;
			this.bipedRightArm.rotationPointY = 15.0F;
		}

		this.bipedHeadwear.rotationPointX = this.bipedHead.rotationPointX;
		this.bipedHeadwear.rotationPointY = this.bipedHead.rotationPointY;
		this.bipedHeadwear.rotationPointZ = this.bipedHead.rotationPointZ;
		this.bipedHeadwear.rotateAngleX = this.bipedHead.rotateAngleX;
		this.bipedHeadwear.rotateAngleY = this.bipedHead.rotateAngleY;
		this.bipedHeadwear.rotateAngleZ = this.bipedHead.rotateAngleZ;
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
