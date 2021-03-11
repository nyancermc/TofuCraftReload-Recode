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
	public boolean canUse() {
		return super.canUse() && !this.creature.level.isDay() && !this.creature.isSleeping();
	}

	@Override
	public void tick() {
		super.tick();

		if (this.isReachedTarget()) {
			this.creature.startSleeping(this.blockPos);
			this.creature.setTofunainHome(this.blockPos);
		}
	}

	@Override
	protected boolean isValidTarget(IWorldReader worldIn, BlockPos pos) {
		BlockState blockstate = worldIn.getBlockState(pos);
		Block block = blockstate.getBlock();

		return blockstate.is(BlockTags.BEDS) && blockstate.getValue(BedBlock.PART) == BedPart.HEAD && !blockstate.getValue(BedBlock.OCCUPIED);
	}

	protected boolean findNearestBlock() {
		if (this.creature.getTofunainHome() != null) {
			if (this.isValidTarget(this.creature.level, this.creature.getTofunainHome())) {
				this.blockPos = this.creature.getTofunainHome();
				return true;
			}
		}
		return super.findNearestBlock();
	}
}
