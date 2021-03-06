package baguchan.tofucraft.entity.ai;

import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.state.properties.BedPart;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
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
	public void tick() {
		super.tick();

		if (this.getIsAboveDestination()) {
			this.creature.startSleeping(this.destinationBlock);
			this.creature.getBrain().setMemory(MemoryModuleType.HOME, GlobalPos.getPosition(this.creature.world.getDimensionKey(), this.destinationBlock));
		}
	}

	@Override
	protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
		BlockState blockstate = worldIn.getBlockState(pos);
		Block block = blockstate.getBlock();

		return blockstate.isIn(BlockTags.BEDS) && blockstate.get(BedBlock.PART) == BedPart.HEAD && !blockstate.get(BedBlock.OCCUPIED);
	}

	protected boolean searchForDestination() {
		if (this.creature.getBrain().hasMemory(MemoryModuleType.HOME)) {
			Brain<?> brain = this.creature.getBrain();
			GlobalPos globalpos = brain.getMemory(MemoryModuleType.HOME).get();
			if (this.creature.world.getDimensionKey() != globalpos.getDimension()) {
				this.creature.getBrain().removeMemory(MemoryModuleType.HOME);
				return false;
			} else {
				if (this.shouldMoveTo(this.creature.world, globalpos.getPos())) {
					this.destinationBlock = globalpos.getPos();
					return true;
				}
			}
		}
		return super.searchForDestination();
	}
}
