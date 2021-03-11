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
	public boolean canUse() {
		return this.creature.getCommandSenderWorld().isDay() && this.creature.getRole() == TofunianEntity.Roles.TOFUNIAN && super.canUse();
	}

	@Override
	public void tick() {
		super.tick();

		if (this.isReachedTarget()) {
			this.creature.setTofunainJobBlock(this.blockPos);

			BlockState blockstate = this.creature.getCommandSenderWorld().getBlockState(this.blockPos);
			Block block = blockstate.getBlock();
			if (!TofunianJobBlocks.getJobBlockList().isEmpty() && TofunianJobBlocks.getJobBlockList().containsKey(block)) {
				this.creature.setRole(TofunianJobBlocks.getJobBlockList().get(block));
			}
		}
	}

	public double getTargetdistSqr() {
		return 2.0D;
	}

	@Override
	protected boolean isValidTarget(IWorldReader worldIn, BlockPos pos) {
		BlockState blockstate = worldIn.getBlockState(pos);
		Block block = blockstate.getBlock();

		return blockstate.is(Blocks.CRAFTING_TABLE) || blockstate.is(Blocks.FURNACE) || blockstate.is(Blocks.CAULDRON);
	}

	protected boolean findNearestBlock() {
		if (this.creature.getTofunainHome() != null) {
			if (this.isValidTarget(this.creature.getCommandSenderWorld(), this.creature.getTofunainHome())) {
				this.blockPos = this.creature.getTofunainHome();
				return true;
			}
		}
		return super.findNearestBlock();
	}
}
