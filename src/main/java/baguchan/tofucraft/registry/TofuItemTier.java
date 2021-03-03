package baguchan.tofucraft.registry;

import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;

import java.util.function.Supplier;

public enum TofuItemTier implements IItemTier {
	KINU(0, 1, 0.1F, 0.0F, 2, () -> {
		return Ingredient.fromItems(TofuItems.TOFUKINU);
	}),
	MOMEN(0, 2, 0.25F, 0.25F, 5, () -> {
		return Ingredient.fromItems(TofuItems.TOFUMOMEN);
	}),
	SOLID(1, 131, 5.0F, 1.0F, 12, () -> {
		return Ingredient.fromItems(TofuItems.TOFUISHI);
	}),
	METAL(2, 250, 6.0F, 2.0F, 15, () -> {
		return Ingredient.fromItems(TofuItems.TOFUMETAL);
	}),
	TOFUDIAMOND(3, 1600, 8.0F, 4.0F, 12, () -> {
		return Ingredient.fromItems(TofuItems.TOFUDIAMOND);
	});

	private final int harvestLevel;
	private final int maxUses;
	private final float efficiency;
	private final float attackDamage;
	private final int enchantability;
	private final LazyValue<Ingredient> repairMaterial;

	private TofuItemTier(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn, Supplier<Ingredient> repairMaterialIn) {
		this.harvestLevel = harvestLevelIn;
		this.maxUses = maxUsesIn;
		this.efficiency = efficiencyIn;
		this.attackDamage = attackDamageIn;
		this.enchantability = enchantabilityIn;
		this.repairMaterial = new LazyValue<>(repairMaterialIn);
	}

	public int getMaxUses() {
		return this.maxUses;
	}

	public float getEfficiency() {
		return this.efficiency;
	}

	public float getAttackDamage() {
		return this.attackDamage;
	}

	public int getHarvestLevel() {
		return this.harvestLevel;
	}

	public int getEnchantability() {
		return this.enchantability;
	}

	public Ingredient getRepairMaterial() {
		return this.repairMaterial.getValue();
	}
}