package baguchan.tofucraft.entity;

import baguchan.tofucraft.entity.ai.DoSleepingGoal;
import baguchan.tofucraft.entity.ai.SleepOnBedGoal;
import baguchan.tofucraft.entity.ai.WakeUpGoal;
import baguchan.tofucraft.registry.TofuSounds;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.RavagerEntity;
import net.minecraft.entity.monster.ZoglinEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public abstract class AbstractTofunianEntity extends AbstractVillagerEntity {
	public AbstractTofunianEntity(EntityType<? extends AbstractTofunianEntity> type, World worldIn) {
		super(type, worldIn);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new WakeUpGoal(this));
		this.goalSelector.addGoal(0, new DoSleepingGoal(this));
		this.goalSelector.addGoal(0, new SwimGoal(this));
		this.goalSelector.addGoal(1, new TradeWithPlayerGoal(this));
		this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, ZombieEntity.class, 8.0F, 1.2D, 1.2D));
		this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, AbstractIllagerEntity.class, 12.0F, 1.2D, 1.2D));
		this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, RavagerEntity.class, 12.0F, 1.2D, 1.2D));
		this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, ZoglinEntity.class, 10.0F, 1.2D, 1.2D));
		this.goalSelector.addGoal(2, new SleepOnBedGoal(this, 1.1F, 8));

		this.goalSelector.addGoal(8, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(9, new LookAtWithoutMovingGoal(this, PlayerEntity.class, 3.0F, 1.0F));
		this.goalSelector.addGoal(10, new LookAtGoal(this, MobEntity.class, 8.0F));
	}

	public ActionResultType func_230254_b_(PlayerEntity p_230254_1_, Hand p_230254_2_) {
		ItemStack itemstack = p_230254_1_.getHeldItem(p_230254_2_);
		if (itemstack.getItem() != Items.VILLAGER_SPAWN_EGG && this.isAlive() && !this.hasCustomer() && !this.isChild()) {

			if (this.getOffers().isEmpty()) {
				this.shakeHead();
			} else {
				if (!this.world.isRemote) {
					this.setCustomer(p_230254_1_);
					this.openMerchantContainer(p_230254_1_, this.getDisplayName(), 1);
				}
			}


			return ActionResultType.func_233537_a_(this.world.isRemote);
		} else {


			return ActionResultType.func_233537_a_(this.world.isRemote);
		}
	}

	private void shakeHead() {
		this.setShakeHeadTicks(40);
		if (!this.world.isRemote()) {
			this.playSound(TofuSounds.TOFUNIAN_NO, this.getSoundVolume(), this.getSoundPitch());
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
	public boolean canDespawn(double distanceToClosestPlayer) {
		return false;
	}
}
