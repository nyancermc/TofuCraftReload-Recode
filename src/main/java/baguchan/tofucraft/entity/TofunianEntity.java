package baguchan.tofucraft.entity;

import baguchan.tofucraft.entity.ai.*;
import baguchan.tofucraft.registry.TofuEntityTypes;
import baguchan.tofucraft.registry.TofuItems;
import baguchan.tofucraft.registry.TofuTrades;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.merchant.IReputationTracking;
import net.minecraft.entity.merchant.IReputationType;
import net.minecraft.entity.merchant.villager.VillagerData;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.RavagerEntity;
import net.minecraft.entity.monster.ZoglinEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.MerchantOffers;
import net.minecraft.nbt.*;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.village.GossipManager;
import net.minecraft.village.GossipType;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IExtensibleEnum;
import net.minecraftforge.common.util.ITeleporter;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class TofunianEntity extends AbstractTofunianEntity implements IReputationTracking {
	private static final DataParameter<String> ROLE = EntityDataManager.defineId(TofunianEntity.class, DataSerializers.STRING);
	@Nullable
	private BlockPos tofunainHome;
	@Nullable
	private BlockPos tofunainJobBlock;

	private final GossipManager gossip = new GossipManager();
	private long lastGossipDecay;

	private long lastRestock;
	private int restocksToday;
	private long lastRestockDayTime;

	private int timeUntilReset;
	private boolean leveledUp;
	@Nullable
	private PlayerEntity previousCustomer;

	private int xp;
	private int tofunianLevel;

	public TofunianEntity(EntityType<? extends TofunianEntity> type, World worldIn) {
		super(type, worldIn);
		((GroundPathNavigator) this.getNavigation()).setCanOpenDoors(true);
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
		this.goalSelector.addGoal(3, new FindJobBlockGoal(this, 1.0F, 8));
		this.goalSelector.addGoal(3, new RestockGoal(this, 1.1F, 6));
		this.goalSelector.addGoal(8, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(9, new LookAtWithoutMovingGoal(this, PlayerEntity.class, 3.0F, 1.0F));
		this.goalSelector.addGoal(10, new LookAtGoal(this, MobEntity.class, 8.0F));
	}

	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		return MobEntity.createMobAttributes().add(Attributes.MOVEMENT_SPEED, (double) 0.24F).add(Attributes.FOLLOW_RANGE, 20.0D);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(ROLE, Roles.TOFUNIAN.name());
	}

	public void setRole(Roles role) {
		this.entityData.set(ROLE, role.name());
	}

	public Roles getRole() {
		return Roles.get(this.entityData.get(ROLE));
	}

	public void setTofunainHome(@Nullable BlockPos pos) {
		this.tofunainHome = pos;
	}

	@Nullable
	public BlockPos getTofunainHome() {
		return this.tofunainHome;
	}

	public void setTofunainJobBlock(@Nullable BlockPos tofunainJobBlock) {
		this.tofunainJobBlock = tofunainJobBlock;
	}

	@Nullable
	public BlockPos getTofunainJobBlock() {
		return tofunainJobBlock;
	}

	@Nullable
	@Override
	public Entity changeDimension(ServerWorld server, ITeleporter teleporter) {
		setTofunainHome((BlockPos) null);
		return super.changeDimension(server, teleporter);
	}

	@Override
	protected void customServerAiStep() {
		if (!this.isTrading() && this.timeUntilReset > 0) {
			--this.timeUntilReset;
			if (this.timeUntilReset <= 0) {
				if (this.leveledUp) {
					this.increaseMerchantCareer();
					this.leveledUp = false;
				}

				this.addEffect(new EffectInstance(Effects.REGENERATION, 200, 0));
			}
		}

		if (this.previousCustomer != null && this.getCommandSenderWorld() instanceof ServerWorld) {
			((ServerWorld) this.getCommandSenderWorld()).onReputationEvent(IReputationType.TRADE, this.previousCustomer, this);
			this.getCommandSenderWorld().broadcastEntityEvent(this, (byte) 14);
			this.previousCustomer = null;
		}

		if (this.getRole() == Roles.TOFUNIAN && this.isTrading()) {
			this.stopTrading();
		}

		super.customServerAiStep();
	}

	public void onReputationEvent(IReputationType type, Entity target) {
		if (type == IReputationType.ZOMBIE_VILLAGER_CURED) {
			this.gossip.add(target.getUUID(), GossipType.MAJOR_POSITIVE, 20);
			this.gossip.add(target.getUUID(), GossipType.MINOR_POSITIVE, 25);
		} else if (type == IReputationType.TRADE) {
			this.gossip.add(target.getUUID(), GossipType.TRADING, 2);
		} else if (type == IReputationType.VILLAGER_HURT) {
			this.gossip.add(target.getUUID(), GossipType.MINOR_NEGATIVE, 25);
		} else if (type == IReputationType.VILLAGER_KILLED) {
			this.gossip.add(target.getUUID(), GossipType.MAJOR_NEGATIVE, 25);
		}

	}

	@Override
	protected void rewardTradeXp(MerchantOffer offer) {
		int i = 3 + this.random.nextInt(4);
		this.xp += offer.getXp();
		this.previousCustomer = this.getTradingPlayer();
		if (this.canLevelUp()) {
			this.timeUntilReset = 40;
			this.leveledUp = true;
			i += 5;
		}

		if (offer.shouldRewardExp()) {
			this.getCommandSenderWorld().addFreshEntity(new ExperienceOrbEntity(this.getCommandSenderWorld(), this.getX(), this.getY() + 0.5D, this.getZ(), i));
		}
	}

	public ActionResultType mobInteract(PlayerEntity p_230254_1_, Hand p_230254_2_) {
		ItemStack itemstack = p_230254_1_.getItemInHand(p_230254_2_);
		if (itemstack.getItem() != TofuItems.TOFUNIAN_SPAWNEGG && this.isAlive() && !this.isTrading() && !this.isBaby()) {

			if (this.isBaby()) {
				this.shakeHead();
				return ActionResultType.sidedSuccess(this.getCommandSenderWorld().isClientSide());
			} else {
				boolean flag = this.getOffers().isEmpty();
				if (p_230254_2_ == Hand.MAIN_HAND) {
					if (flag && !this.getCommandSenderWorld().isClientSide()) {
						this.shakeHead();
					}

					p_230254_1_.awardStat(Stats.TALKED_TO_VILLAGER);
				}

				if (flag) {
					return ActionResultType.sidedSuccess(this.getCommandSenderWorld().isClientSide());
				} else {
					if (!this.getCommandSenderWorld().isClientSide() && !this.offers.isEmpty()) {
						this.displayMerchantGui(p_230254_1_);
					}

					return ActionResultType.sidedSuccess(this.getCommandSenderWorld().isClientSide());
				}
			}
		} else {
			return ActionResultType.sidedSuccess(this.getCommandSenderWorld().isClientSide());
		}
	}

	private void displayMerchantGui(PlayerEntity player) {
		this.recalculateSpecialPricesFor(player);
		this.setTradingPlayer(player);
		this.openTradingScreen(player, this.getDisplayName(), tofunianLevel);
	}

	public void setTradingPlayer(@Nullable PlayerEntity player) {
		boolean flag = this.getTradingPlayer() != null && player == null;
		super.setTradingPlayer(player);
		if (flag) {
			this.stopTrading();
		}

	}

	protected void stopTrading() {
		super.stopTrading();
		this.resetSpecialPrices();
	}

	private void resetSpecialPrices() {
		for (MerchantOffer merchantoffer : this.getOffers()) {
			merchantoffer.resetSpecialPriceDiff();
		}
	}

	public boolean canRestock() {
		return true;
	}

	public void restock() {
		this.calculateDemandOfOffers();

		for (MerchantOffer merchantoffer : this.getOffers()) {
			merchantoffer.resetUses();
		}
		this.lastRestock = this.getCommandSenderWorld().getGameTime();
		++this.restocksToday;
	}

	private boolean allowedToRestock() {
		return this.restocksToday == 0 || this.restocksToday < 2 && this.getCommandSenderWorld().getGameTime() > this.lastRestock + 2400L;
	}

	public boolean canResetStock() {
		long i = this.lastRestock + 12000L;
		long j = this.getCommandSenderWorld().getGameTime();
		boolean flag = j > i;
		long k = this.getCommandSenderWorld().getDayTime();
		if (this.lastRestockDayTime > 0L) {
			long l = this.lastRestockDayTime / 24000L;
			long i1 = k / 24000L;
			flag |= i1 > l;
		}

		this.lastRestockDayTime = k;
		if (flag) {
			this.lastRestock = j;
			this.func_223718_eH();
		}

		return this.allowedToRestock() && this.hasUsedOffer();
	}

	private void func_223718_eH() {
		this.resetOffersAndAdjustForDemand();
		this.restocksToday = 0;
	}

	private void resetOffersAndAdjustForDemand() {
		int i = 2 - this.restocksToday;
		if (i > 0) {
			for (MerchantOffer merchantoffer : this.getOffers()) {
				merchantoffer.resetUses();
			}
		}

		for (int j = 0; j < i; ++j) {
			this.calculateDemandOfOffers();
		}

	}

	private boolean hasUsedOffer() {
		for (MerchantOffer merchantoffer : this.getOffers()) {
			if (merchantoffer.needsRestock()) {
				return true;
			}
		}

		return false;
	}

	private void calculateDemandOfOffers() {
		for (MerchantOffer merchantoffer : this.getOffers()) {
			merchantoffer.updateDemand();
		}

	}

	private void recalculateSpecialPricesFor(PlayerEntity playerIn) {
		int i = this.getPlayerReputation(playerIn);
		if (i != 0) {
			for (MerchantOffer merchantoffer : this.getOffers()) {
				merchantoffer.addToSpecialPriceDiff(-MathHelper.floor((float) i * merchantoffer.getPriceMultiplier()));
			}
		}
	}

	public void setOffers(MerchantOffers offersIn) {
		this.offers = offersIn;
	}

	private boolean canLevelUp() {
		int i = this.tofunianLevel;
		return VillagerData.canLevelUp(i) && this.xp >= VillagerData.getMaxXpPerLevel(i);
	}

	private void increaseMerchantCareer() {
		this.setTofunainLevel(this.tofunianLevel + 1);
		this.updateTrades();
	}

	public void setTofunainLevel(int level) {
		this.tofunianLevel = level;
	}

	public int getTofunainLevel() {
		return tofunianLevel;
	}

	public int getPlayerReputation(PlayerEntity player) {
		return this.gossip.getReputation(player.getUUID(), (gossipType) -> {
			return true;
		});
	}

	public void addAdditionalSaveData(CompoundNBT compound) {
		super.addAdditionalSaveData(compound);
		//compound.putByte("FoodLevel", this.foodLevel);
		compound.put("Gossips", this.gossip.store(NBTDynamicOps.INSTANCE).getValue());
		compound.putInt("Xp", this.xp);
		compound.putInt("Level", this.tofunianLevel);
		compound.putLong("LastRestock", this.lastRestock);
		compound.putLong("LastGossipDecay", this.lastGossipDecay);
		compound.putInt("RestocksToday", this.restocksToday);

		if (this.tofunainHome != null) {
			compound.put("TofunianHome", NBTUtil.writeBlockPos(this.tofunainHome));
		}

		if (this.tofunainJobBlock != null) {
			compound.put("TofunianJobBlock", NBTUtil.writeBlockPos(this.tofunainJobBlock));
		}

		compound.putString("Roles", getRole().name());
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readAdditionalSaveData(CompoundNBT compound) {
		super.readAdditionalSaveData(compound);

		if (compound.contains("Offers", 10)) {
			this.offers = new MerchantOffers(compound.getCompound("Offers"));
		}

		/*if (compound.contains("FoodLevel", 1)) {
			this.foodLevel = compound.getByte("FoodLevel");
		}*/

		ListNBT listnbt = compound.getList("Gossips", 10);
		this.gossip.update(new Dynamic<>(NBTDynamicOps.INSTANCE, listnbt));
		if (compound.contains("Xp", 3)) {
			this.xp = compound.getInt("Xp");
		}

		if (compound.contains("Level", 3)) {
			this.tofunianLevel = compound.getInt("Level");
		}

		this.lastGossipDecay = compound.getLong("LastGossipDecay");
		this.lastRestock = compound.getLong("LastRestock");
		this.restocksToday = compound.getInt("RestocksToday");

		if (compound.contains("TofunianHome")) {
			this.tofunainHome = NBTUtil.readBlockPos(compound.getCompound("TofunianHome"));
		}

		if (compound.contains("TofunianJobBlock")) {
			this.tofunainJobBlock = NBTUtil.readBlockPos(compound.getCompound("TofunianJobBlock"));
		}

		if (compound.contains("Roles")) {
			this.setRole(Roles.get(compound.getString("Roles")));
		}

		this.setCanPickUpLoot(true);
	}

	@Override
	protected void updateTrades() {
		Int2ObjectMap<VillagerTrades.ITrade[]> int2objectmap = TofuTrades.TOFUNIAN_TRADE.get(getRole());
		if (int2objectmap != null && !int2objectmap.isEmpty()) {
			VillagerTrades.ITrade[] avillagertrades$itrade = int2objectmap.get(tofunianLevel);
			if (avillagertrades$itrade != null) {
				MerchantOffers merchantoffers = this.getOffers();
				this.addOffersFromItemListings(merchantoffers, avillagertrades$itrade, 3);
			}
		}
	}

	public void tick() {
		super.tick();

		this.tickGossip();
	}

	private void tickGossip() {
		long i = this.getCommandSenderWorld().getGameTime();
		if (this.lastGossipDecay == 0L) {
			this.lastGossipDecay = i;
		} else if (i >= this.lastGossipDecay + 24000L) {
			this.gossip.decay();
			this.lastGossipDecay = i;
		}
	}

	public GossipManager getGossip() {
		return this.gossip;
	}

	public void setGossips(INBT gossip) {
		this.gossip.update(new Dynamic<>(NBTDynamicOps.INSTANCE, gossip));
	}

	public void die(DamageSource cause) {
		super.die(cause);
	}

	@Nullable
	@Override
	public AgeableEntity getBreedOffspring(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
		return TofuEntityTypes.TOFUNIAN.create(p_241840_1_);
	}

	@Override
	public void onReputationEventFrom(IReputationType p_213739_1_, Entity p_213739_2_) {
		if (p_213739_1_ == IReputationType.ZOMBIE_VILLAGER_CURED) {
			this.gossip.add(p_213739_2_.getUUID(), GossipType.MAJOR_POSITIVE, 20);
			this.gossip.add(p_213739_2_.getUUID(), GossipType.MINOR_POSITIVE, 25);
		} else if (p_213739_1_ == IReputationType.TRADE) {
			this.gossip.add(p_213739_2_.getUUID(), GossipType.TRADING, 2);
		} else if (p_213739_1_ == IReputationType.VILLAGER_HURT) {
			this.gossip.add(p_213739_2_.getUUID(), GossipType.MINOR_NEGATIVE, 25);
		} else if (p_213739_1_ == IReputationType.VILLAGER_KILLED) {
			this.gossip.add(p_213739_2_.getUUID(), GossipType.MAJOR_NEGATIVE, 25);
		}

	}

	public enum Roles implements IExtensibleEnum {
		TOFUCOOK,
		TOFUSMITH,
		SOYWORKER,
		TOFUNIAN;
		private static final Map<String, Roles> lookup;

		static {
			lookup = Arrays.<Roles>stream(values()).collect(Collectors.toMap(Enum::name, p_220362_0_ -> p_220362_0_));
		}


		public static Roles create(String name) {
			throw new IllegalStateException("Enum not extended");
		}

		public static Roles get(String nameIn) {
			for (Roles role : values()) {
				if (role.name().equals(nameIn)) {
					return role;
				}
			}
			return TOFUNIAN;
		}
	}
}
