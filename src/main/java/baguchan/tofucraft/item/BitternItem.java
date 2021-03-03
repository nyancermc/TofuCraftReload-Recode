package baguchan.tofucraft.item;

import baguchan.tofucraft.api.BitternRecipes;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.Map;

public class BitternItem extends Item {
	public BitternItem(Properties group) {
		super(group);
	}

	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		return ActionResultType.PASS;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		BlockRayTraceResult blockraytraceresult = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.SOURCE_ONLY);
		BlockRayTraceResult blockraytraceresult1 = blockraytraceresult.withPosition(blockraytraceresult.getPos());
		if (blockraytraceresult.getType() == RayTraceResult.Type.MISS) {
			return new ActionResult<>(ActionResultType.PASS, itemstack);
		}
		if (blockraytraceresult.getType() == RayTraceResult.Type.BLOCK) {

			FluidState fluidState = worldIn.getFluidState(blockraytraceresult1.getPos());
			Map.Entry<Fluid, Block> result = BitternRecipes.getResult(fluidState.getFluid());

			if (result != null) {
				worldIn.setBlockState(blockraytraceresult1.getPos(), result.getValue().getDefaultState(), 11);
				worldIn.playEvent(2001, blockraytraceresult1.getPos(), Block.getStateId(worldIn.getBlockState(blockraytraceresult1.getPos())));

				return new ActionResult<>(ActionResultType.SUCCESS, playerIn.getHeldItem(handIn));
			}
		}
		return new ActionResult<>(ActionResultType.FAIL, itemstack);
	}
}
