package baguchan.tofucraft.entity.ai;

import baguchan.tofucraft.api.TofunianJobBlocks;
import baguchan.tofucraft.entity.TofunianEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class RestockGoal extends MoveToBlockGoal {
	private final TofunianEntity creature;

	public RestockGoal(TofunianEntity creature, double speedIn, int length) {
		super(creature, speedIn, length);
		this.creature = creature;
	}

	@Override
	public boolean shouldExecute() {
		return this.creature.getRole() != TofunianEntity.Roles.TOFUNIAN && this.creature.canResetStock() && this.creature.world.isDaytime() && super.shouldExecute();
	}

	@Override
	public boolean shouldContinueExecuting() {
		return this.shouldExecute() && this.destinationBlock != null;
	}

	@Override
	public void tick() {
		super.tick();

		if (this.getIsAboveDestination()) {
			this.creature.setTofunainJobBlock(this.destinationBlock);
			this.creature.restock();
		}
	}

	@Override
	protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
		BlockState blockstate = worldIn.getBlockState(pos);
		Block block = blockstate.getBlock();

		return TofunianJobBlocks.getJobBlockList().entrySet().stream().findFirst().filter((entry) -> {
			return entry.getValue() == this.creature.getRole() && entry.getKey() == block;
		}).isPresent();
	}

	protected boolean searchForDestination() {
		if (this.creature.getTofunainJobBlock() != null) {
			if (this.shouldMoveTo(this.creature.world, this.creature.getTofunainJobBlock())) {
				this.destinationBlock = this.creature.getTofunainJobBlock();
				return true;
			}
		}
		return super.searchForDestination();
	}
}
