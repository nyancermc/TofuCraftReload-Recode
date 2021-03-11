package baguchan.tofucraft.entity;

import baguchan.tofucraft.registry.TofuSounds;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public abstract class AbstractTofunianEntity extends AbstractVillagerEntity {
	public AbstractTofunianEntity(EntityType<? extends AbstractTofunianEntity> type, World worldIn) {
		super(type, worldIn);
	}

	protected void shakeHead() {
		this.setUnhappyCounter(40);
		if (!this.level.isClientSide()) {
			this.playSound(TofuSounds.TOFUNIAN_NO, this.getSoundVolume(), this.getVoicePitch());
		}
	}

	public void tick() {
		super.tick();
		if (this.getUnhappyCounter() > 0) {
			this.setUnhappyCounter(this.getUnhappyCounter() - 1);
		}
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return TofuSounds.TOFUNIAN_AMBIENT;
	}

	public SoundEvent getYesSound() {
		return TofuSounds.TOFUNIAN_YES;
	}

	protected SoundEvent getVillagerYesNoSound(boolean getYesSound) {
		return getYesSound ? TofuSounds.TOFUNIAN_YES : TofuSounds.TOFUNIAN_NO;
	}

	@Nullable
	@Override
	protected SoundEvent getDeathSound() {
		return TofuSounds.TOFUNIAN_DEATH;
	}

	@Override
	protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
		return sizeIn.height * 0.8F;
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
	}
}
