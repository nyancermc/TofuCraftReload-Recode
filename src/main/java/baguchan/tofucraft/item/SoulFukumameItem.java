package baguchan.tofucraft.item;

import baguchan.tofucraft.entity.projectile.SoulFukumameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class SoulFukumameItem extends Item {
	public SoulFukumameItem(Properties group) {
		super(group);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		worldIn.playSound((PlayerEntity) null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEvents.ENTITY_EGG_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
		if (!worldIn.isRemote) {
			for (int i = 0; i < 5; i++) {
				SoulFukumameEntity fukumamentity = new SoulFukumameEntity(worldIn, playerIn);
				float d0 = (worldIn.rand.nextFloat() * 20.0F) - 10.0F;

				fukumamentity.func_234612_a_(playerIn, playerIn.rotationPitch + d0 * 0.25F, playerIn.rotationYaw + d0, 0.0F, 1.5F, 0.8F);
				worldIn.addEntity(fukumamentity);
			}
		}

		playerIn.addStat(Stats.ITEM_USED.get(this));
		playerIn.getCooldownTracker().setCooldown(itemstack.getItem(), 10);
		if (!playerIn.abilities.isCreativeMode) {
			itemstack.damageItem(1, playerIn, playerEntity -> playerEntity.sendBreakAnimation(handIn));
		}

		return ActionResult.func_233538_a_(itemstack, worldIn.isRemote());
	}
}
