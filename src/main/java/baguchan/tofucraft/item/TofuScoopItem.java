package baguchan.tofucraft.item;

import baguchan.tofucraft.registry.TofuTags;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TofuScoopItem extends Item {
	public TofuScoopItem(Properties group) {
		super(group);
	}

	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		World worldIn = context.getWorld();
		BlockPos pos = context.getPos();
		if (context.getWorld().getBlockState(context.getPos()).isIn(TofuTags.Blocks.SOFT_TOFU)) {
			ItemStack stack = new ItemStack(Item.getItemFromBlock(context.getWorld().getBlockState(context.getPos()).getBlock()));

			worldIn.removeBlock(context.getPos(), false);
			if (!worldIn.isRemote()) {
				if (context.getPlayer() != null) {
					stack.damageItem(1, context.getPlayer(), p_220036_0_ -> p_220036_0_.sendBreakAnimation(context.getHand()));
				}


				double d0 = (double) (worldIn.rand.nextFloat() * 0.5F) + 0.25D;
				double d1 = (double) (worldIn.rand.nextFloat() * 0.5F) + 0.25D;
				double d2 = (double) (worldIn.rand.nextFloat() * 0.5F) + 0.25D;
				ItemEntity itementity = new ItemEntity(worldIn, (double) pos.getX() + d0, (double) pos.getY() + d1, (double) pos.getZ() + d2, stack);
				itementity.setDefaultPickupDelay();
				worldIn.addEntity(itementity);
			}
			worldIn.playSound(null, pos, SoundEvents.BLOCK_SNOW_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);

			return ActionResultType.SUCCESS;
		}
		return super.onItemUse(context);
	}
}
