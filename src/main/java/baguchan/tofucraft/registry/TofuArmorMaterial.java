package baguchan.tofucraft.registry;

import baguchan.tofucraft.TofuCraftReload;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

public enum TofuArmorMaterial implements IArmorMaterial {
	KINU(TofuCraftReload.MODID + ":armor_kinu", 1, new int[]{0, 0, 0, 0}, 8, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, () -> {
		return Ingredient.fromItems(TofuItems.TOFUKINU);
	}),
	MOMEN(TofuCraftReload.MODID + ":armor_momen", 1, new int[]{0, 1, 1, 0}, 10, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 0.0F, 0.0F, () -> {
		return Ingredient.fromItems(TofuItems.TOFUMOMEN);
	}),
	SOLID(TofuCraftReload.MODID + ":armor_solid", 10, new int[]{1, 4, 5, 2}, 16, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F, 0.0F, () -> {
		return Ingredient.fromItems(TofuItems.TOFUISHI);
	}),
	METAL(TofuCraftReload.MODID + ":armor_metal", 15, new int[]{2, 5, 6, 2}, 12, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0.0F, 0.0F, () -> {
		return Ingredient.fromItems(TofuItems.TOFUMETAL);
	}),
	DIAMOND(TofuCraftReload.MODID + ":armor_diamond", 40, new int[]{4, 7, 9, 4}, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.5F, 0.0F, () -> {
		return Ingredient.fromItems(TofuItems.TOFUDIAMOND);
	});

	private static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};
	private final String name;
	private final int maxDamageFactor;
	private final int[] damageReductionAmountArray;
	private final int enchantability;
	private final SoundEvent soundEvent;
	private final float toughness;
	private final float knockbackResistance;
	private final LazyValue<Ingredient> repairMaterial;

	private TofuArmorMaterial(String name, int maxDamageFactor, int[] damageReductionAmountArray, int enchantability, SoundEvent soundEvent, float toughness, float knockbackResistance, Supplier<Ingredient> repairMaterial) {
		this.name = name;
		this.maxDamageFactor = maxDamageFactor;
		this.damageReductionAmountArray = damageReductionAmountArray;
		this.enchantability = enchantability;
		this.soundEvent = soundEvent;
		this.toughness = toughness;
		this.knockbackResistance = knockbackResistance;
		this.repairMaterial = new LazyValue<>(repairMaterial);
	}

	public int getDurability(EquipmentSlotType slotIn) {
		return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.maxDamageFactor;
	}

	public int getDamageReductionAmount(EquipmentSlotType slotIn) {
		return this.damageReductionAmountArray[slotIn.getIndex()];
	}

	public int getEnchantability() {
		return this.enchantability;
	}

	public SoundEvent getSoundEvent() {
		return this.soundEvent;
	}

	public Ingredient getRepairMaterial() {
		return this.repairMaterial.getValue();
	}

	@OnlyIn(Dist.CLIENT)
	public String getName() {
		return this.name;
	}

	public float getToughness() {
		return this.toughness;
	}

	/**
	 * Gets the percentage of knockback resistance provided by armor of the material.
	 */
	public float getKnockbackResistance() {
		return this.knockbackResistance;
	}
}
