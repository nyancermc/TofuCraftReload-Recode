package baguchan.tofucraft.entity.projectile;

import baguchan.tofucraft.registry.TofuEntityTypes;
import baguchan.tofucraft.registry.TofuItems;
import baguchan.tofucraft.registry.TofuSounds;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class SoulFukumameEntity extends ThrowableEntity {
	public SoulFukumameEntity(EntityType<? extends SoulFukumameEntity> p_i50154_1_, World p_i50154_2_) {
		super(p_i50154_1_, p_i50154_2_);
	}

	public SoulFukumameEntity(World levelIn, LivingEntity throwerIn) {
		super(TofuEntityTypes.SOUL_FUKUMAME, throwerIn, levelIn);
	}

	public SoulFukumameEntity(World levelIn, double x, double y, double z) {
		super(TofuEntityTypes.SOUL_FUKUMAME, x, y, z, levelIn);
	}

	public SoulFukumameEntity(EntityType<? extends SoulFukumameEntity> p_i50154_1_, World levelIn, double x, double y, double z) {
		super(p_i50154_1_, x, y, z, levelIn);
	}

	@Override
	protected void defineSynchedData() {

	}

	@OnlyIn(Dist.CLIENT)
	public void handleEntityEvent(byte id) {
		if (id == 3) {
			double d0 = 0.08D;
			for (int i = 0; i < 6; ++i) {
				this.level.addParticle(new ItemParticleData(ParticleTypes.ITEM, new ItemStack(TofuItems.SEEDS_SOYBEANS_SOUL)), this.getX(), this.getY(), this.getZ(), ((double) this.random.nextFloat() - 0.5D) * 0.08D, ((double) this.random.nextFloat() - 0.5D) * 0.08D, ((double) this.random.nextFloat() - 0.5D) * 0.08D);
			}
		} else if (id == 4) {
			double d0 = 0.08D;

			this.level.addParticle(ParticleTypes.WARPED_SPORE, this.getX(), this.getY(), this.getZ(), ((double) this.random.nextFloat() - 0.5D) * 0.08D, ((double) this.random.nextFloat() - 0.5D) * 0.08D, ((double) this.random.nextFloat() - 0.5D) * 0.08D);

		}
	}

	@Override
	public void tick() {
		super.tick();
		if (!this.level.isClientSide) {
			this.level.broadcastEntityEvent(this, (byte) 4);
		}
	}

	/**
	 * Called when the arrow hits an entity
	 */
	protected void onHitEntity(EntityRayTraceResult p_213868_1_) {
		super.onHitEntity(p_213868_1_);
		p_213868_1_.getEntity().hurt(DamageSource.thrown(this, this.getOwner()), 1.0F);
		p_213868_1_.getEntity().invulnerableTime = 5;
	}

	/**
	 * Called when this EntityFireball hits a block or entity.
	 */
	protected void onHit(RayTraceResult result) {
		super.onHit(result);
		this.playSound(TofuSounds.SOYBEAN_CRACK, 0.8F, 0.8F + this.level.random.nextFloat() * 0.4F);
		if (!this.level.isClientSide()) {
			this.level.broadcastEntityEvent(this, (byte) 3);
			this.remove();
		}

	}


	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
