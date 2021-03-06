package baguchan.tofucraft.entity.ai;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.Goal;

public class WakeUpGoal extends Goal {
	private final CreatureEntity creature;

	public WakeUpGoal(CreatureEntity creature) {
		this.creature = creature;
	}

	@Override
	public boolean shouldExecute() {
		return this.creature.world.isDaytime() && this.creature.isSleeping() || this.creature.getBedPosition().isPresent() && this.creature.getBedPosition().get().withinDistance(creature.getPositionVec(), 4.0D) && this.creature.isSleeping();
	}

	@Override
	public void startExecuting() {
		super.startExecuting();
		this.creature.wakeUp();
	}
}
