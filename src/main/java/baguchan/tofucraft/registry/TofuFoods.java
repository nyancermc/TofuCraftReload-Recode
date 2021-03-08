package baguchan.tofucraft.registry;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class TofuFoods {
	public static final Food TOFU = (new Food.Builder()).hunger(2).saturation(0.12F).fastToEat().build();
	public static final Food ISHITOFU = (new Food.Builder()).hunger(2).saturation(0.1F).fastToEat().build();
	public static final Food TOFUHELL = (new Food.Builder()).hunger(2).saturation(0.12F).setAlwaysEdible().effect(() -> new EffectInstance(Effects.FIRE_RESISTANCE, 600), 1.0F).build();
	public static final Food TOFUSOUL = (new Food.Builder()).hunger(2).saturation(0.12F).fastToEat().build();
	public static final Food TOFUGRILLED = (new Food.Builder()).hunger(3).saturation(0.15F).fastToEat().build();
	public static final Food TOFUZUNDA = (new Food.Builder()).hunger(3).saturation(0.2F).fastToEat().build();

	public static final Food BOILED_EDAMAME = (new Food.Builder()).hunger(1).saturation(0.1F).fastToEat().build();

	public static final Food TOFU_HAMBURG = (new Food.Builder()).hunger(6).saturation(0.6F).build();
	public static final Food RAW_TOFUFISH = (new Food.Builder()).hunger(1).saturation(0.1F).build();
	public static final Food COOKED_TOFUFISH = (new Food.Builder()).hunger(5).saturation(0.6F).build();
	public static final Food TOFUCOOKIE = (new Food.Builder()).hunger(1).saturation(0.1F).build();

	public static final Food KINAKO_MANJU = (new Food.Builder()).hunger(2).saturation(0.24F).fastToEat().build();
	public static final Food ZUNDA_MANJU = (new Food.Builder()).hunger(3).saturation(0.26F).fastToEat().build();
}
