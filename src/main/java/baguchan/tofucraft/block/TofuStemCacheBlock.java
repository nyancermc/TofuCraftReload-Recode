package baguchan.tofucraft.block;

import baguchan.tofucraft.registry.TofuItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class TofuStemCacheBlock extends HorizontalBlock {
	public static final BooleanProperty ZUNDAMA = BooleanProperty.create("zundama");

	public TofuStemCacheBlock(Properties builder) {
		super(builder);
		this.registerDefaultState(this.stateDefinition.any().setValue(ZUNDAMA, true));
	}


	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		boolean hasZunda = state.getValue(ZUNDAMA);
		if (hasZunda) {
			ItemStack salt = new ItemStack(TofuItems.ZUNDAMA, 1);

			if (worldIn instanceof World) {
				float f = 0.7F;
				double d0 = (double) (worldIn.getRandom().nextFloat() * f) + (double) (1.0F - f) * 0.5D;
				double d1 = (double) (worldIn.getRandom().nextFloat() * f) + (double) (1.0F - f) * 0.2D + 0.6D;
				double d2 = (double) (worldIn.getRandom().nextFloat() * f) + (double) (1.0F - f) * 0.5D;
				ItemEntity itemEntity = new ItemEntity((World) worldIn, (double) pos.getX() + d0, (double) pos.getY() + d1, (double) pos.getZ() + d2, salt);
				itemEntity.setPickUpDelay(10);
				worldIn.addFreshEntity(itemEntity);
			}


			worldIn.setBlock(pos, state.setValue(ZUNDAMA, false), 3);
			return ActionResultType.SUCCESS;
		}
		return ActionResultType.PASS;
	}

	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(HorizontalBlock.FACING, ZUNDAMA);
	}
}