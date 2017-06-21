package binnie.extratrees.machines.lumbermill;

import net.minecraft.item.ItemStack;

import binnie.core.machines.Machine;
import binnie.core.machines.MachineUtil;
import binnie.core.machines.power.ComponentProcessSetCost;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IProcess;
import binnie.extratrees.item.ExtraTreeItems;

public class LumbermillLogic extends ComponentProcessSetCost implements IProcess {
	public static final int PROCESS_ENERGY = 900;
	public static final int PROCESS_LENGTH = 30;
	public static final int WATER_PER_TICK = 10;

	public LumbermillLogic(final Machine machine) {
		super(machine, PROCESS_ENERGY, PROCESS_LENGTH);
	}

	@Override
	public ErrorState canWork() {
		MachineUtil util = getUtil();
		ItemStack logStack = util.getStack(LumbermillMachine.SLOT_LOG);
		if (logStack.isEmpty()) {
			return new ErrorState.NoItem("No Wood", LumbermillMachine.SLOT_LOG);
		}
		final ItemStack plankResult = LumbermillRecipes.getPlankProduct(logStack);
		if (!util.isSlotEmpty(LumbermillMachine.SLOT_PLANKS) && !plankResult.isEmpty()) {
			final ItemStack currentPlank = util.getStack(LumbermillMachine.SLOT_PLANKS);
			if (!plankResult.isItemEqual(currentPlank) || plankResult.getCount() + currentPlank.getCount() > currentPlank.getMaxStackSize()) {
				return new ErrorState.NoSpace("No room for new planks", new int[]{LumbermillMachine.SLOT_PLANKS});
			}
		}
		if(!util.isSlotEmpty(LumbermillMachine.SLOT_BARK)){
			ItemStack itemStack = util.getStack(LumbermillMachine.SLOT_BARK);
			if (itemStack.getCount() + 1 > itemStack.getMaxStackSize()) {
				return new ErrorState.NoSpace("No room for new bark", new int[]{LumbermillMachine.SLOT_BARK});
			}
		}
		if(!util.isSlotEmpty(LumbermillMachine.SLOT_SAWDUST)){
			ItemStack itemStack = util.getStack(LumbermillMachine.SLOT_SAWDUST);
			if (itemStack.getCount() + 1 > itemStack.getMaxStackSize()) {
				return new ErrorState.NoSpace("No room for new sawdust", new int[]{LumbermillMachine.SLOT_SAWDUST});
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
		MachineUtil util = getUtil();
		final ItemStack logStack = util.getStack(LumbermillMachine.SLOT_LOG);
		final ItemStack result = LumbermillRecipes.getPlankProduct(logStack);
		if (result == null) {
			return;
		}
		util.addStack(LumbermillMachine.SLOT_PLANKS, result);
		util.addStack(LumbermillMachine.SLOT_SAWDUST, ExtraTreeItems.Sawdust.get(1));
		util.addStack(LumbermillMachine.SLOT_BARK, ExtraTreeItems.Bark.get(1));
		util.decreaseStack(LumbermillMachine.SLOT_LOG, 1);
	}

	@Override
	protected void onTickTask() {
		this.getUtil().drainTank(LumbermillMachine.TANK_WATER, WATER_PER_TICK);
	}
}
