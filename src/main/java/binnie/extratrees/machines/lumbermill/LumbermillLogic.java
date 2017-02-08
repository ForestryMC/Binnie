package binnie.extratrees.machines.lumbermill;

import binnie.core.machines.Machine;
import binnie.core.machines.power.ComponentProcessSetCost;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IProcess;
import binnie.extratrees.item.ExtraTreeItems;
import net.minecraft.item.ItemStack;

public class LumbermillLogic extends ComponentProcessSetCost implements IProcess {
	public static final int PROCESS_ENERGY = 900;
	public static final int PROCESS_LENGTH = 30;
	public static final int WATER_PER_TICK = 10;

	public LumbermillLogic(final Machine machine) {
		super(machine, PROCESS_ENERGY, PROCESS_LENGTH);
	}

	@Override
	public ErrorState canWork() {
		ItemStack logStack = this.getUtil().getStack(LumbermillMachine.SLOT_LOG);
		if (logStack == null) {
			return new ErrorState.NoItem("No Wood", LumbermillMachine.SLOT_LOG);
		}
		final ItemStack plankResult = LumbermillRecipes.getPlankProduct(logStack);
		if (!this.getUtil().isSlotEmpty(LumbermillMachine.SLOT_PLANKS) && plankResult != null) {
			final ItemStack currentPlank = this.getUtil().getStack(LumbermillMachine.SLOT_PLANKS);
			if (!plankResult.isItemEqual(currentPlank) || plankResult.getCount() + currentPlank.getCount() > currentPlank.getMaxStackSize()) {
				return new ErrorState.NoSpace("No room for new planks", new int[]{LumbermillMachine.SLOT_PLANKS});
			}
		}
		return super.canWork();
	}

	@Override
	public ErrorState canProgress() {
		if (!this.getUtil().liquidInTank(LumbermillMachine.TANK_WATER, 5)) {
			return new ErrorState.InsufficientLiquid("Not Enough Water", LumbermillMachine.TANK_WATER);
		}
		return super.canProgress();
	}

	@Override
	protected void onFinishTask() {
		final ItemStack logStack = this.getUtil().getStack(LumbermillMachine.SLOT_LOG);
		final ItemStack result = LumbermillRecipes.getPlankProduct(logStack);
		if (result == null) {
			return;
		}
		this.getUtil().addStack(LumbermillMachine.SLOT_PLANKS, result);
		this.getUtil().addStack(LumbermillMachine.SLOT_SAWDUST, ExtraTreeItems.Sawdust.get(1));
		this.getUtil().addStack(LumbermillMachine.SLOT_BARK, ExtraTreeItems.Bark.get(1));
		this.getUtil().decreaseStack(LumbermillMachine.SLOT_LOG, 1);
	}

	@Override
	protected void onTickTask() {
		this.getUtil().drainTank(LumbermillMachine.TANK_WATER, WATER_PER_TICK);
	}
}
