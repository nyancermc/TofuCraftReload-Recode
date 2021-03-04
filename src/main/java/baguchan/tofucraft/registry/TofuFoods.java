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
}
