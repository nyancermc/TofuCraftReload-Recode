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
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.MerchantOffers;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
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
		this.forceSpawn = true;
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
		return MonsterEntity.func_234295_eP_().createMutableAttribute(Attributes.MOVEMENT_SPEED, (double) 0.24F).createMutableAttribute(Attributes.FOLLOW_RANGE, 20.0D);
	}

	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		compound.putInt("DespawnDelay", this.despawnDelay);
		if (this.wanderTarget != null) {
			compound.put("WanderTarget", NBTUtil.writeBlockPos(this.wanderTarget));
		}

	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		if (compound.contains("DespawnDelay", 99)) {
			this.despawnDelay = compound.getInt("DespawnDelay");
		}

		if (compound.contains("WanderTarget")) {
			this.wanderTarget = NBTUtil.readBlockPos(compound.getCompound("WanderTarget"));
		}

		this.setGrowingAge(Math.max(0, this.getGrowingAge()));
	}

	@Override
	protected void onVillagerTrade(MerchantOffer offer) {
		if (offer.getDoesRewardExp()) {
			int i = 3 + this.rand.nextInt(4);
			this.world.addEntity(new ExperienceOrbEntity(this.world, this.getPosX(), this.getPosY() + 0.5D, this.getPosZ(), i));
		}
	}

	@Override
	protected void populateTradeData() {
		VillagerTrades.ITrade[] avillagertrades$itrade = TofuTrades.TRAVELER_TOFUNIAN_TRADE.get(1);
		VillagerTrades.ITrade[] avillagertrades$itrade1 = TofuTrades.TRAVELER_TOFUNIAN_TRADE.get(2);
		if (avillagertrades$itrade != null && avillagertrades$itrade1 != null) {
			MerchantOffers merchantoffers = this.getOffers();
			this.addTrades(merchantoffers, avillagertrades$itrade, 2);
			this.addTrades(merchantoffers, avillagertrades$itrade1, 2);
		}
	}

	@Nullable
	@Override
	public AgeableEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
		return null;
	}

	public void setDespawnDelay(int delay) {
		this.despawnDelay = delay;
	}

	public int getDespawnDelay() {
		return this.despawnDelay;
	}

	public boolean hasXPBar() {
		return false;
	}

	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	 * use this to react to sunlight and start to burn.
	 */
	public void livingTick() {
		super.livingTick();
		if (!this.world.isRemote) {
			this.handleDespawn();
		}

	}

	private void handleDespawn() {
		if (this.despawnDelay > 0 && !this.hasCustomer() && --this.despawnDelay == 0) {
			this.remove();
		}

	}

	public void setWanderTarget(@Nullable BlockPos pos) {
		this.wanderTarget = pos;
	}

	@Nullable
	private BlockPos getWanderTarget() {
		return this.wanderTarget;
	}

	class MoveToGoal extends Goal {
		final TravelerTofunianEntity traderEntity;
		final double maxDistance;
		final double speed;

		MoveToGoal(TravelerTofunianEntity traderEntityIn, double distanceIn, double speedIn) {
			this.traderEntity = traderEntityIn;
			this.maxDistance = distanceIn;
			this.speed = speedIn;
			this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
		}

		/**
		 * Reset the task's internal state. Called when this task is interrupted by another one
		 */
		public void resetTask() {
			this.traderEntity.setWanderTarget((BlockPos) null);
			TravelerTofunianEntity.this.navigator.clearPath();
		}

		/**
		 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
		 * method as well.
		 */
		public boolean shouldExecute() {
			BlockPos blockpos = this.traderEntity.getWanderTarget();
			return blockpos != null && this.isWithinDistance(blockpos, this.maxDistance);
		}

		/**
		 * Keep ticking a continuous task that has already been started
		 */
		public void tick() {
			BlockPos blockpos = this.traderEntity.getWanderTarget();
			if (blockpos != null && TravelerTofunianEntity.this.navigator.noPath()) {
				if (this.isWithinDistance(blockpos, 10.0D)) {
					Vector3d vector3d = (new Vector3d((double) blockpos.getX() - this.traderEntity.getPosX(), (double) blockpos.getY() - this.traderEntity.getPosY(), (double) blockpos.getZ() - this.traderEntity.getPosZ())).normalize();
					Vector3d vector3d1 = vector3d.scale(10.0D).add(this.traderEntity.getPosX(), this.traderEntity.getPosY(), this.traderEntity.getPosZ());
					TravelerTofunianEntity.this.navigator.tryMoveToXYZ(vector3d1.x, vector3d1.y, vector3d1.z, this.speed);
				} else {
					TravelerTofunianEntity.this.navigator.tryMoveToXYZ((double) blockpos.getX(), (double) blockpos.getY(), (double) blockpos.getZ(), this.speed);
				}
			}

		}

		private boolean isWithinDistance(BlockPos pos, double distance) {
			return !pos.withinDistance(this.traderEntity.getPositionVec(), distance);
		}
	}
}
