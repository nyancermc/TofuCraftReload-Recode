package baguchan.tofucraft.entity;

import baguchan.tofucraft.registry.TofuTrades;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.RavagerEntity;
import net.minecraft.entity.monster.ZoglinEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class TravelerTofunianEntity extends AbstractTofunianEntity {
	@Nullable
	private BlockPos wanderTarget;
	private int despawnDelay;

	public TravelerTofunianEntity(EntityType<? extends TravelerTofunianEntity> type, World worldIn) {
		super(type, worldIn);
		this.setCanPickUpLoot(false);
		this.forcedLoading = true;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new SwimGoal(this));
		this.goalSelector.addGoal(1, new TradeWithPlayerGoal(this));
		this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, ZombieEntity.class, 8.0F, 1.2D, 1.2D));
		this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, AbstractIllagerEntity.class, 12.0F, 1.2D, 1.2D));
		this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, RavagerEntity.class, 12.0F, 1.2D, 1.2D));
		this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, ZoglinEntity.class, 10.0F, 1.2D, 1.2D));
		this.goalSelector.addGoal(2, new MoveToGoal(this, 10.0F, 1.15F));
		this.goalSelector.addGoal(8, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(9, new LookAtWithoutMovingGoal(this, PlayerEntity.class, 3.0F, 1.0F));
		this.goalSelector.addGoal(10, new LookAtGoal(this, MobEntity.class, 8.0F));
	}

	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		return MobEntity.createMobAttributes().add(Attributes.MOVEMENT_SPEED, (double) 0.24F).add(Attributes.FOLLOW_RANGE, 20.0D);
	}


	public void addAdditionalSaveData(CompoundNBT compound) {
		super.addAdditionalSaveData(compound);
		compound.putInt("DespawnDelay", this.despawnDelay);
		if (this.wanderTarget != null) {
			compound.put("WanderTarget", NBTUtil.writeBlockPos(this.wanderTarget));
		}

	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readAdditionalSaveData(CompoundNBT compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("DespawnDelay", 99)) {
			this.despawnDelay = compound.getInt("DespawnDelay");
		}

		if (compound.contains("WanderTarget")) {
			this.wanderTarget = NBTUtil.readBlockPos(compound.getCompound("WanderTarget"));
		}

		this.setAge(Math.max(0, this.getAge()));
	}

	@Nullable
	public AgeableEntity getBreedOffspring(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
		return null;
	}

	public boolean showProgressBar() {
		return false;
	}

	public ActionResultType mobInteract(PlayerEntity p_230254_1_, Hand p_230254_2_) {
		ItemStack itemstack = p_230254_1_.getItemInHand(p_230254_2_);
		if (itemstack.getItem() != Items.VILLAGER_SPAWN_EGG && this.isAlive() && !this.isTrading() && !this.isBaby()) {
			if (p_230254_2_ == Hand.MAIN_HAND) {
				p_230254_1_.awardStat(Stats.TALKED_TO_VILLAGER);
			}

			if (this.getOffers().isEmpty()) {
				return ActionResultType.sidedSuccess(this.level.isClientSide);
			} else {
				if (!this.level.isClientSide) {
					this.setTradingPlayer(p_230254_1_);
					this.openTradingScreen(p_230254_1_, this.getDisplayName(), 1);
				}

				return ActionResultType.sidedSuccess(this.level.isClientSide);
			}
		} else {
			return super.mobInteract(p_230254_1_, p_230254_2_);
		}
	}

	protected void updateTrades() {
		VillagerTrades.ITrade[] avillagertrades$itrade = TofuTrades.TRAVELER_TOFUNIAN_TRADE.get(1);
		VillagerTrades.ITrade[] avillagertrades$itrade1 = TofuTrades.TRAVELER_TOFUNIAN_TRADE.get(2);
		if (avillagertrades$itrade != null && avillagertrades$itrade1 != null) {
			MerchantOffers merchantoffers = this.getOffers();
			this.addOffersFromItemListings(merchantoffers, avillagertrades$itrade, 3);
			this.addOffersFromItemListings(merchantoffers, avillagertrades$itrade1, 3);

		}
	}

	public boolean removeWhenFarAway(double p_213397_1_) {
		return false;
	}

	protected void rewardTradeXp(MerchantOffer p_213713_1_) {
		if (p_213713_1_.shouldRewardExp()) {
			int i = 3 + this.random.nextInt(4);
			this.level.addFreshEntity(new ExperienceOrbEntity(this.level, this.getX(), this.getY() + 0.5D, this.getZ(), i));
		}

	}

	protected SoundEvent getAmbientSound() {
		return this.isTrading() ? SoundEvents.WANDERING_TRADER_TRADE : SoundEvents.WANDERING_TRADER_AMBIENT;
	}

	protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
		return SoundEvents.WANDERING_TRADER_HURT;
	}

	protected SoundEvent getDeathSound() {
		return SoundEvents.WANDERING_TRADER_DEATH;
	}

	protected SoundEvent getDrinkingSound(ItemStack p_213351_1_) {
		Item item = p_213351_1_.getItem();
		return item == Items.MILK_BUCKET ? SoundEvents.WANDERING_TRADER_DRINK_MILK : SoundEvents.WANDERING_TRADER_DRINK_POTION;
	}

	protected SoundEvent getTradeUpdatedSound(boolean p_213721_1_) {
		return p_213721_1_ ? SoundEvents.WANDERING_TRADER_YES : SoundEvents.WANDERING_TRADER_NO;
	}

	public SoundEvent getNotifyTradeSound() {
		return SoundEvents.WANDERING_TRADER_YES;
	}

	public void setDespawnDelay(int p_213728_1_) {
		this.despawnDelay = p_213728_1_;
	}

	public int getDespawnDelay() {
		return this.despawnDelay;
	}

	public void aiStep() {
		super.aiStep();
		if (!this.level.isClientSide) {
			this.maybeDespawn();
		}

	}

	private void maybeDespawn() {
		if (this.despawnDelay > 0 && !this.isTrading() && --this.despawnDelay == 0) {
			this.remove();
		}

	}

	public void setWanderTarget(@Nullable BlockPos p_213726_1_) {
		this.wanderTarget = p_213726_1_;
	}

	@Nullable
	private BlockPos getWanderTarget() {
		return this.wanderTarget;
	}

	class MoveToGoal extends Goal {
		final TravelerTofunianEntity trader;
		final double stopDistance;
		final double speedModifier;

		MoveToGoal(TravelerTofunianEntity p_i50459_2_, double p_i50459_3_, double p_i50459_5_) {
			this.trader = p_i50459_2_;
			this.stopDistance = p_i50459_3_;
			this.speedModifier = p_i50459_5_;
			this.setFlags(EnumSet.of(Goal.Flag.MOVE));
		}

		public void stop() {
			this.trader.setWanderTarget((BlockPos) null);
			TravelerTofunianEntity.this.navigation.stop();
		}

		public boolean canUse() {
			BlockPos blockpos = this.trader.getWanderTarget();
			return blockpos != null && this.isTooFarAway(blockpos, this.stopDistance);
		}

		public void tick() {
			BlockPos blockpos = this.trader.getWanderTarget();
			if (blockpos != null && TravelerTofunianEntity.this.navigation.isDone()) {
				if (this.isTooFarAway(blockpos, 10.0D)) {
					Vector3d vector3d = (new Vector3d((double) blockpos.getX() - this.trader.getX(), (double) blockpos.getY() - this.trader.getY(), (double) blockpos.getZ() - this.trader.getZ())).normalize();
					Vector3d vector3d1 = vector3d.scale(10.0D).add(this.trader.getX(), this.trader.getY(), this.trader.getZ());
					TravelerTofunianEntity.this.navigation.moveTo(vector3d1.x, vector3d1.y, vector3d1.z, this.speedModifier);
				} else {
					TravelerTofunianEntity.this.navigation.moveTo((double) blockpos.getX(), (double) blockpos.getY(), (double) blockpos.getZ(), this.speedModifier);
				}
			}

		}

		private boolean isTooFarAway(BlockPos p_220846_1_, double p_220846_2_) {
			return !p_220846_1_.closerThan(this.trader.position(), p_220846_2_);
		}
	}
}
