package baguchan.tofucraft.entity.ai;

import baguchan.tofucraft.entity.TofunianEntity;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.state.properties.BedPart;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class SleepOnBedGoal extends MoveToBlockGoal {
	private final TofunianEntity creature;

	public SleepOnBedGoal(TofunianEntity creature, double speedIn, int length) {
		super(creature, speedIn, length);
		this.creature = creature;
	}

	@Override
	public boolean shouldExecute() {
		return super.shouldExecute() && !this.creature.world.isDaytime() && !this.creature.isSleeping();
	}

	@Override
	public void tick() {
		super.tick();

		if (this.getIsAboveDestination()) {
			this.creature.startSleeping(this.destinationBlock);
			this.creature.setTofunainHome(this.destinationBlock);
		}
	}

	@Override
	protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
		BlockState blockstate = worldIn.getBlockState(pos);
		Block block = blockstate.getBlock();

		return blockstate.isIn(BlockTags.BEDS) && blockstate.get(BedBlock.PART) == BedPart.HEAD && !blockstate.get(BedBlock.OCCUPIED);
	}

	protected boolean searchForDestination() {
		if (this.creature.getTofunainHome() != null) {
			if (this.shouldMoveTo(this.creature.world, this.creature.getTofunainHome())) {
				this.destinationBlock = this.creature.getTofunainHome();
				return true;
			}
		}
		return super.searchForDestination();
	}
}
