package baguchan.tofucraft.entity;

import baguchan.tofucraft.registry.TofuBlocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.entity.passive.fish.AbstractGroupFishEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.Random;

public class TofuFishEntity extends AbstractGroupFishEntity {
	public TofuFishEntity(EntityType<? extends TofuFishEntity> p_i50279_1_, World p_i50279_2_) {
		super(p_i50279_1_, p_i50279_2_);
	}

	public static boolean canTofuFishSpawnOn(EntityType<? extends AbstractFishEntity> type, IWorld worldIn, SpawnReason reason, BlockPos p_223363_3_, Random randomIn) {
		return worldIn.getBlockState(p_223363_3_).isIn(TofuBlocks.SOYMILK) && worldIn.getBlockState(p_223363_3_.up()).isIn(TofuBlocks.SOYMILK);
	}

	protected ItemStack getFishBucket() {
		return new ItemStack(Items.COD_BUCKET);
	}

	protected SoundEvent getAmbientSound() {
		return SoundEvents.ENTITY_COD_AMBIENT;
	}

	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_COD_DEATH;
	}

	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.ENTITY_COD_HURT;
	}

	protected SoundEvent getFlopSound() {
		return SoundEvents.ENTITY_COD_FLOP;
	}
}
