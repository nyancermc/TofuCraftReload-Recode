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

public class NetherFukumameEntity extends ThrowableEntity {
	public NetherFukumameEntity(EntityType<? extends NetherFukumameEntity> p_i50154_1_, World p_i50154_2_) {
		super(p_i50154_1_, p_i50154_2_);
	}

	public NetherFukumameEntity(World worldIn, LivingEntity throwerIn) {
		super(TofuEntityTypes.NETHER_FUKUMAME, throwerIn, worldIn);
	}

	public NetherFukumameEntity(World worldIn, double x, double y, double z) {
		super(TofuEntityTypes.NETHER_FUKUMAME, x, y, z, worldIn);
	}

	public NetherFukumameEntity(EntityType<? extends NetherFukumameEntity> p_i50154_1_, World worldIn, double x, double y, double z) {
		super(p_i50154_1_, x, y, z, worldIn);
	}

	@Override
	protected void registerData() {

	}

	/**
	 * Handler for {@link World#setEntityState}
	 */
	@OnlyIn(Dist.CLIENT)
	public void handleStatusUpdate(byte id) {
		if (id == 3) {
			double d0 = 0.08D;
			for (int i = 0; i < 6; ++i) {
				this.world.addParticle(new ItemParticleData(ParticleTypes.ITEM, new ItemStack(TofuItems.SEEDS_SOYBEANS_NETHER)), this.getPosX(), this.getPosY(), this.getPosZ(), ((double) this.rand.nextFloat() - 0.5D) * 0.08D, ((double) this.rand.nextFloat() - 0.5D) * 0.08D, ((double) this.rand.nextFloat() - 0.5D) * 0.08D);
			}
		} else if (id == 4) {
			this.world.addParticle(ParticleTypes.CRIMSON_SPORE, this.getPosX(), this.getPosY(), this.getPosZ(), ((double) this.rand.nextFloat() - 0.5D) * 0.08D, ((double) this.rand.nextFloat() - 0.5D) * 0.08D, ((double) this.rand.nextFloat() - 0.5D) * 0.08D);
		}
	}

	@Override
	public void tick() {
		super.tick();
		if (!this.world.isRemote) {
			this.world.setEntityState(this, (byte) 4);
		}
	}

	/**
	 * Called when the arrow hits an entity
	 */
	protected void onEntityHit(EntityRayTraceResult p_213868_1_) {
		super.onEntityHit(p_213868_1_);
		p_213868_1_.getEntity().attackEntityFrom(DamageSource.causeThrownDamage(this, this.func_234616_v_()), 1.0F);
		p_213868_1_.getEntity().hurtResistantTime = 5;

		if (!p_213868_1_.getEntity().isImmuneToFire() && this.isBurning()) {
			p_213868_1_.getEntity().setFire(8);
		}
	}

	/**
	 * Called when this EntityFireball hits a block or entity.
	 */
	protected void onImpact(RayTraceResult result) {
		super.onImpact(result);
		this.playSound(TofuSounds.SOYBEAN_CRACK, 0.8F, 0.8F + this.world.rand.nextFloat() * 0.4F);
		if (!this.world.isRemote) {
			this.world.setEntityState(this, (byte) 3);
			this.remove();
		}

	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
