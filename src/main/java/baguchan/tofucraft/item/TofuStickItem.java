package baguchan.tofucraft.item;

import baguchan.tofucraft.registry.TofuBlocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Rarity;
import net.minecraft.util.ActionResultType;

public class TofuStickItem extends Item {
	public TofuStickItem(Properties group) {
		super(group);
	}


	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		if (context.getWorld().getBlockState(context.getPos()).getBlock() == TofuBlocks.GRILLEDTOFU)
			if (TofuBlocks.TOFU_PORTAL.trySpawnPortal(context.getWorld(), context.getPos().up())) {
				if (!context.getPlayer().isCreative())
					context.getItem().damageItem(1, (LivingEntity) context.getPlayer(), p_213625_1_ -> p_213625_1_.sendBreakAnimation(context.getHand()));
				return ActionResultType.SUCCESS;
			}
		return super.onItemUse(context);
	}

	@Override
	public Rarity getRarity(ItemStack stack) {
		return Rarity.RARE;
	}

	public boolean hasEffect(ItemStack stack) {
		return true;
	}
}
