package baguchan.tofucraft.entity.ai;

import baguchan.tofucraft.api.TofunianJobBlocks;
import baguchan.tofucraft.entity.TofunianEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class FindJobBlockGoal extends MoveToBlockGoal {
	private final TofunianEntity creature;

	public FindJobBlockGoal(TofunianEntity creature, double speedIn, int length) {
		super(creature, speedIn, length);
		this.creature = creature;
	}

	@Override
	public boolean shouldExecute() {
		return this.creature.world.isDaytime() && this.creature.getRole() == TofunianEntity.Roles.TOFUNIAN && super.shouldExecute();
	}

	@Override
	public void tick() {
		super.tick();

		if (this.getIsAboveDestination()) {
			this.creature.setTofunainJobBlock(this.destinationBlock);

			BlockState blockstate = this.creature.world.getBlockState(this.destinationBlock);
			Block block = blockstate.getBlock();
			if (!TofunianJobBlocks.getJobBlockList().isEmpty() && TofunianJobBlocks.getJobBlockList().containsKey(block)) {
				this.creature.setRole(TofunianJobBlocks.getJobBlockList().get(block));
			}
		}
	}

	public double getTargetDistanceSq() {
		return 2.0D;
	}

	@Override
	protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
		BlockState blockstate = worldIn.getBlockState(pos);
		Block block = blockstate.getBlock();

		return blockstate.isIn(Blocks.CRAFTING_TABLE) || blockstate.isIn(Blocks.FURNACE) || blockstate.isIn(Blocks.CAULDRON);
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
