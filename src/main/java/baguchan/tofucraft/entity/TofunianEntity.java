package baguchan.tofucraft.entity;

import baguchan.tofucraft.entity.ai.SleepOnBedGoal;
import baguchan.tofucraft.entity.ai.WakeUpGoal;
import baguchan.tofucraft.registry.TofuSounds;
import com.google.common.collect.ImmutableList;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

public class TofunianEntity extends AbstractVillagerEntity {
	private static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(MemoryModuleType.HOME);
	private static final ImmutableList<SensorType<? extends Sensor<? super TofunianEntity>>> SENSOR_TYPES = ImmutableList.of(SensorType.NEAREST_BED);
	@Nullable
	private PlayerEntity previousCustomer;
	@Nullable
	private BlockPos tofunainHome;
	@Nullable
	private BlockPos tofunainJobBlock;

	public TofunianEntity(EntityType<? extends TofunianEntity> type, World worldIn) {
		super(type, worldIn);
		((GroundPathNavigator) this.getNavigator()).setBreakDoors(true);
		this.setCanPickUpLoot(true);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new SwimGoal(this));
		this.goalSelector.addGoal(1, new TradeWithPlayerGoal(this));
		this.goalSelector.addGoal(1, new SleepOnBedGoal(this, 1.0F, 8));
		this.goalSelector.addGoal(1, new WakeUpGoal(this));
		this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, ZombieEntity.class, 8.0F, 1.2D, 1.2D));
		this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, AbstractIllagerEntity.class, 12.0F, 1.2D, 1.2D));
		this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, RavagerEntity.class, 12.0F, 1.2D, 1.2D));
		this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, ZoglinEntity.class, 10.0F, 1.2D, 1.2D));
		this.goalSelector.addGoal(8, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(9, new LookAtWithoutMovingGoal(this, PlayerEntity.class, 3.0F, 1.0F));
		this.goalSelector.addGoal(10, new LookAtGoal(this, MobEntity.class, 8.0F));
	}

	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		return MonsterEntity.func_234295_eP_().createMutableAttribute(Attributes.MOVEMENT_SPEED, (double) 0.23F).createMutableAttribute(Attributes.FOLLOW_RANGE, 20.0D);
	}

	public Brain<TofunianEntity> getBrain() {
		return (Brain<TofunianEntity>) super.getBrain();
	}

	protected Brain.BrainCodec<TofunianEntity> getBrainCodec() {
		return Brain.createCodec(MEMORY_TYPES, SENSOR_TYPES);
	}

	@Override
	protected void updateAITasks() {
		this.world.getProfiler().startSection("tofunianBrain");
		this.getBrain().tick((ServerWorld) this.world, this);
		this.world.getProfiler().endSection();
		super.updateAITasks();
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
			this.playSound(SoundEvents.ENTITY_VILLAGER_NO, this.getSoundVolume(), this.getSoundPitch());
		}

	}


	@Override
	protected void onVillagerTrade(MerchantOffer offer) {

	}

	@Override
	protected void populateTradeData() {

	}

	@Nullable
	@Override
	public AgeableEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
		return null;
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
