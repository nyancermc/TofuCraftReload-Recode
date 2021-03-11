package baguchan.tofucraft.registry;

import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;

import java.util.function.Supplier;

public enum TofuItemTier implements IItemTier {
	KINU(0, 1, 0.1F, 0.0F, 2, () -> {
		return Ingredient.of(TofuItems.TOFUKINU);
	}),
	MOMEN(0, 2, 0.25F, 0.25F, 5, () -> {
		return Ingredient.of(TofuItems.TOFUMOMEN);
	}),
	SOLID(1, 131, 5.0F, 1.0F, 12, () -> {
		return Ingredient.of(TofuItems.TOFUISHI);
	}),
	METAL(2, 250, 6.0F, 2.0F, 15, () -> {
		return Ingredient.of(TofuItems.TOFUMETAL);
	}),
	TOFUDIAMOND(3, 1600, 8.0F, 4.0F, 12, () -> {
		return Ingredient.of(TofuItems.TOFUDIAMOND);
	});

	private final int level;
	private final int uses;
	private final float speed;
	private final float damage;
	private final int enchantmentValue;
	private final LazyValue<Ingredient> repairIngredient;

	private TofuItemTier(int p_i48458_3_, int p_i48458_4_, float p_i48458_5_, float p_i48458_6_, int p_i48458_7_, Supplier<Ingredient> p_i48458_8_) {
		this.level = p_i48458_3_;
		this.uses = p_i48458_4_;
		this.speed = p_i48458_5_;
		this.damage = p_i48458_6_;
		this.enchantmentValue = p_i48458_7_;
		this.repairIngredient = new LazyValue<>(p_i48458_8_);
	}

	public int getUses() {
		return this.uses;
	}

	public float getSpeed() {
		return this.speed;
	}

	public float getAttackDamageBonus() {
		return this.damage;
	}

	public int getLevel() {
		return this.level;
	}

	public int getEnchantmentValue() {
		return this.enchantmentValue;
	}

	public Ingredient getRepairIngredient() {
		return this.repairIngredient.get();
	}
}