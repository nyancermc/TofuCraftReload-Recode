package baguchan.tofucraft.entity;

import baguchan.tofucraft.entity.ai.*;
import baguchan.tofucraft.registry.TofuEntityTypes;
import baguchan.tofucraft.registry.TofuItems;
import baguchan.tofucraft.registry.TofuTrades;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.merchant.IReputationTracking;
import net.minecraft.entity.merchant.IReputationType;
import net.minecraft.entity.merchant.villager.VillagerData;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.monster.*;
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
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class TofunianEntity extends AbstractTofunianEntity implements IReputationTracking {
	private static final DataParameter<String> ROLE = EntityDataManager.createKey(TofunianEntity.class, DataSerializers.STRING);
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
	private int level;

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
		this.goalSelector.addGoal(3, new FindJobBlockGoal(this, 1.0F, 8));
		this.goalSelector.addGoal(3, new RestockGoal(this, 1.1F, 6));
		this.goalSelector.addGoal(8, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(9, new LookAtWithoutMovingGoal(this, PlayerEntity.class, 3.0F, 1.0F));
		this.goalSelector.addGoal(10, new LookAtGoal(this, MobEntity.class, 8.0F));
	}

	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		return MonsterEntity.func_234295_eP_().createMutableAttribute(Attributes.MOVEMENT_SPEED, (double) 0.24F).createMutableAttribute(Attributes.FOLLOW_RANGE, 20.0D);
	}

	@Override
	protected void registerData() {
		super.registerData();
		this.dataManager.register(ROLE, Roles.TOFUNIAN.name());
	}

	public void setRole(Roles role) {
		this.getDataManager().set(ROLE, role.name());
	}

	public Roles getRole() {
		return Roles.get(this.getDataManager().get(ROLE));
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
	protected void updateAITasks() {
		if (!this.hasCustomer() && this.timeUntilReset > 0) {
			--this.timeUntilReset;
			if (this.timeUntilReset <= 0) {
				if (this.leveledUp) {
					this.levelUp();
					this.leveledUp = false;
				}

				this.addPotionEffect(new EffectInstance(Effects.REGENERATION, 200, 0));
			}
		}

		if (this.previousCustomer != null && this.world instanceof ServerWorld) {
			((ServerWorld) this.world).updateReputation(IReputationType.TRADE, this.previousCustomer, this);
			this.world.setEntityState(this, (byte) 14);
			this.previousCustomer = null;
		}

		if (this.getRole() == Roles.TOFUNIAN && this.hasCustomer()) {
			this.resetCustomer();
		}

		super.updateAITasks();
	}

	public void updateReputation(IReputationType type, Entity target) {
		if (type == IReputationType.ZOMBIE_VILLAGER_CURED) {
			this.gossip.add(target.getUniqueID(), GossipType.MAJOR_POSITIVE, 20);
			this.gossip.add(target.getUniqueID(), GossipType.MINOR_POSITIVE, 25);
		} else if (type == IReputationType.TRADE) {
			this.gossip.add(target.getUniqueID(), GossipType.TRADING, 2);
		} else if (type == IReputationType.VILLAGER_HURT) {
			this.gossip.add(target.getUniqueID(), GossipType.MINOR_NEGATIVE, 25);
		} else if (type == IReputationType.VILLAGER_KILLED) {
			this.gossip.add(target.getUniqueID(), GossipType.MAJOR_NEGATIVE, 25);
		}

	}

	@Override
	protected void onVillagerTrade(MerchantOffer offer) {
		int i = 3 + this.rand.nextInt(4);
		this.xp += offer.getGivenExp();
		this.previousCustomer = this.getCustomer();
		if (this.canLevelUp()) {
			this.timeUntilReset = 40;
			this.leveledUp = true;
			i += 5;
		}

		if (offer.getDoesRewardExp()) {
			this.world.addEntity(new ExperienceOrbEntity(this.world, this.getPosX(), this.getPosY() + 0.5D, this.getPosZ(), i));
		}
	}

	public ActionResultType func_230254_b_(PlayerEntity p_230254_1_, Hand p_230254_2_) {
		ItemStack itemstack = p_230254_1_.getHeldItem(p_230254_2_);
		if (itemstack.getItem() != TofuItems.TOFUNIAN_SPAWNEGG && this.isAlive() && !this.hasCustomer() && !this.isChild()) {

			if (this.isChild()) {
				this.shakeHead();
				return ActionResultType.func_233537_a_(this.world.isRemote);
			} else {
				boolean flag = this.getOffers().isEmpty();
				if (p_230254_2_ == Hand.MAIN_HAND) {
					if (flag && !this.world.isRemote) {
						this.shakeHead();
					}

					p_230254_1_.addStat(Stats.TALKED_TO_VILLAGER);
				}

				if (flag) {
					return ActionResultType.func_233537_a_(this.world.isRemote);
				} else {
					if (!this.world.isRemote && !this.offers.isEmpty()) {
						this.displayMerchantGui(p_230254_1_);
					}

					return ActionResultType.func_233537_a_(this.world.isRemote);
				}
			}
		} else {
			return ActionResultType.func_233537_a_(this.world.isRemote);
		}
	}

	private void displayMerchantGui(PlayerEntity player) {
		this.recalculateSpecialPricesFor(player);
		this.setCustomer(player);
		this.openMerchantContainer(player, this.getDisplayName(), level);
	}

	public void setCustomer(@Nullable PlayerEntity player) {
		boolean flag = this.getCustomer() != null && player == null;
		super.setCustomer(player);
		if (flag) {
			this.resetCustomer();
		}

	}

	protected void resetCustomer() {
		super.resetCustomer();
		this.resetAllSpecialPrices();
	}

	private void resetAllSpecialPrices() {
		for (MerchantOffer merchantoffer : this.getOffers()) {
			merchantoffer.resetSpecialPrice();
		}

	}

	public boolean canRestockTrades() {
		return true;
	}

	public void restock() {
		this.calculateDemandOfOffers();

		for (MerchantOffer merchantoffer : this.getOffers()) {
			merchantoffer.resetUses();
		}
		this.lastRestock = this.world.getGameTime();
		++this.restocksToday;
	}

	private boolean canRestock() {
		return this.restocksToday == 0 || this.restocksToday < 2 && this.world.getGameTime() > this.lastRestock + 2400L;
	}

	public boolean canResetStock() {
		long i = this.lastRestock + 12000L;
		long j = this.world.getGameTime();
		boolean flag = j > i;
		long k = this.world.getDayTime();
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

		return this.canRestock() && this.hasUsedOffer();
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
			if (merchantoffer.hasBeenUsed()) {
				return true;
			}
		}

		return false;
	}

	private void calculateDemandOfOffers() {
		for (MerchantOffer merchantoffer : this.getOffers()) {
			merchantoffer.calculateDemand();
		}

	}

	private void recalculateSpecialPricesFor(PlayerEntity playerIn) {
		int i = this.getPlayerReputation(playerIn);
		if (i != 0) {
			for (MerchantOffer merchantoffer : this.getOffers()) {
				merchantoffer.increaseSpecialPrice(-MathHelper.floor((float) i * merchantoffer.getPriceMultiplier()));
			}
		}

		if (playerIn.isPotionActive(Effects.HERO_OF_THE_VILLAGE)) {
			EffectInstance effectinstance = playerIn.getActivePotionEffect(Effects.HERO_OF_THE_VILLAGE);
			int k = effectinstance.getAmplifier();

			for (MerchantOffer merchantoffer1 : this.getOffers()) {
				double d0 = 0.3D + 0.0625D * (double) k;
				int j = (int) Math.floor(d0 * (double) merchantoffer1.getBuyingStackFirst().getCount());
				merchantoffer1.increaseSpecialPrice(-Math.max(j, 1));
			}
		}
	}

	public void setOffers(MerchantOffers offersIn) {
		this.offers = offersIn;
	}

	private boolean canLevelUp() {
		int i = this.level;
		return VillagerData.canLevelUp(i) && this.xp >= VillagerData.getExperienceNext(i);
	}

	private void levelUp() {
		this.setLevel(this.level + 1);
		this.populateTradeData();
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getLevel() {
		return level;
	}

	public int getPlayerReputation(PlayerEntity player) {
		return this.gossip.getReputation(player.getUniqueID(), (gossipType) -> {
			return true;
		});
	}

	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		//compound.putByte("FoodLevel", this.foodLevel);
		compound.put("Gossips", this.gossip.write(NBTDynamicOps.INSTANCE).getValue());
		compound.putInt("Xp", this.xp);
		compound.putInt("Level", this.level);
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
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);

		if (compound.contains("Offers", 10)) {
			this.offers = new MerchantOffers(compound.getCompound("Offers"));
		}

		/*if (compound.contains("FoodLevel", 1)) {
			this.foodLevel = compound.getByte("FoodLevel");
		}*/

		ListNBT listnbt = compound.getList("Gossips", 10);
		this.gossip.read(new Dynamic<>(NBTDynamicOps.INSTANCE, listnbt));
		if (compound.contains("Xp", 3)) {
			this.xp = compound.getInt("Xp");
		}

		if (compound.contains("Level", 3)) {
			this.level = compound.getInt("Level");
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
	protected void populateTradeData() {
		Int2ObjectMap<VillagerTrades.ITrade[]> int2objectmap = TofuTrades.TOFUNIAN_TRADE.get(getRole());
		if (int2objectmap != null && !int2objectmap.isEmpty()) {
			VillagerTrades.ITrade[] avillagertrades$itrade = int2objectmap.get(level);
			if (avillagertrades$itrade != null) {
				MerchantOffers merchantoffers = this.getOffers();
				this.addTrades(merchantoffers, avillagertrades$itrade, 3);
			}
		}
	}

	public void tick() {
		super.tick();

		this.tickGossip();
	}

	private void tickGossip() {
		long i = this.world.getGameTime();
		if (this.lastGossipDecay == 0L) {
			this.lastGossipDecay = i;
		} else if (i >= this.lastGossipDecay + 24000L) {
			this.gossip.tick();
			this.lastGossipDecay = i;
		}
	}

	public GossipManager getGossip() {
		return this.gossip;
	}

	public void setGossips(INBT gossip) {
		this.gossip.read(new Dynamic<>(NBTDynamicOps.INSTANCE, gossip));
	}

	public void onDeath(DamageSource cause) {
		LOGGER.info("Villager {} died, message: '{}'", this, cause.getDeathMessage(this).getString());
		Entity entity = cause.getTrueSource();
		if (entity != null) {
			this.sawMurder(entity);
		}

		super.onDeath(cause);
	}


	private void sawMurder(Entity murderer) {
		if (this.world instanceof ServerWorld) {
			Optional<List<LivingEntity>> optional = this.brain.getMemory(MemoryModuleType.VISIBLE_MOBS);
			if (optional.isPresent()) {
				ServerWorld serverworld = (ServerWorld) this.world;
				optional.get().stream().filter((gossipTarget) -> {
					return gossipTarget instanceof IReputationTracking;
				}).forEach((gossipTarget) -> {
					serverworld.updateReputation(IReputationType.VILLAGER_KILLED, murderer, (IReputationTracking) gossipTarget);
				});
			}
		}
	}

	@Nullable
	@Override
	public AgeableEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
		return TofuEntityTypes.TOFUNIAN.create(p_241840_1_);
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
