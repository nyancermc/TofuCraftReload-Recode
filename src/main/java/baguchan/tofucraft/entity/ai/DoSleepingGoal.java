package baguchan.tofucraft.entity.ai;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.Goal;

import java.util.EnumSet;

public class DoSleepingGoal extends Goal {
	private final CreatureEntity creature;

	public DoSleepingGoal(CreatureEntity creature) {
		this.creature = creature;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
	}

	@Override
	public boolean canUse() {
		/* 1209 */
		return this.creature.isSleeping();
		/*      */
	}
}
