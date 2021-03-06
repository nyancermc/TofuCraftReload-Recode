package baguchan.tofucraft.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class SleepOnBedGoal extends MoveToBlockGoal {
	private final CreatureEntity creature;

	public SleepOnBedGoal(CreatureEntity creature, double speedIn, int length) {
		super(creature, speedIn, length);
		this.creature = creature;
	}

	@Override
	public boolean shouldExecute() {
		return super.shouldExecute() && !this.creature.world.isDaytime() && !this.creature.isSleeping();
	}

	@Override
	protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
		BlockState blockstate = worldIn.getBlockState(pos);
		Block block = blockstate.getBlock();

		return blockstate.isIn(BlockTags.BEDS);
	}
}
