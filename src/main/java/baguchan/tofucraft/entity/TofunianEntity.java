package baguchan.tofucraft.entity;

import baguchan.tofucraft.entity.ai.DoSleepingGoal;
import baguchan.tofucraft.entity.ai.SleepOnBedGoal;
import baguchan.tofucraft.entity.ai.WakeUpGoal;
import baguchan.tofucraft.registry.TofuEntityTypes;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.MerchantOffer;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

public class TofunianEntity extends AbstractTofunianEntity {
	private static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(MemoryModuleType.HOME);
	private static final ImmutableList<SensorType<? extends Sensor<? super TofunianEntity>>> SENSOR_TYPES = ImmutableList.of(SensorType.NEAREST_BED);

	public TofunianEntity(EntityType<? extends TofunianEntity> type, World worldIn) {
		super(type, worldIn);
		((GroundPathNavigator) this.getNavigator()).setBreakDoors(true);
		this.setCanPickUpLoot(true);
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

	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		return MonsterEntity.func_234295_eP_().createMutableAttribute(Attributes.MOVEMENT_SPEED, (double) 0.24F).createMutableAttribute(Attributes.FOLLOW_RANGE, 20.0D);
	}

	public Brain<TofunianEntity> getBrain() {
		return (Brain<TofunianEntity>) super.getBrain();
	}

	protected Brain.BrainCodec<TofunianEntity> getBrainCodec() {
		return Brain.createCodec(MEMORY_TYPES, SENSOR_TYPES);
	}

	protected Brain<?> createBrain(Dynamic<?> dynamicIn) {
		return this.getBrainCodec().deserialize(dynamicIn);
	}

	@Override
	protected void updateAITasks() {
		this.world.getProfiler().startSection("tofunianBrain");
		this.getBrain().tick((ServerWorld) this.world, this);
		this.world.getProfiler().endSection();
		super.updateAITasks();
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

	}

	@Nullable
	@Override
	public AgeableEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
		return TofuEntityTypes.TOFUNIAN.create(p_241840_1_);
	}
}
