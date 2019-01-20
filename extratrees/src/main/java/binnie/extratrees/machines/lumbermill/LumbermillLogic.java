package binnie.extratrees.machines.lumbermill;

import net.minecraft.item.ItemStack;

import binnie.core.machines.Machine;
import binnie.core.machines.MachineUtil;
import binnie.core.machines.errors.ErrorState;
import binnie.core.machines.power.ComponentProcessSetCost;
import binnie.core.machines.power.IProcess;
import binnie.extratrees.items.ExtraTreeMiscItems;
import binnie.extratrees.machines.ExtraTreesErrorCode;
import binnie.extratrees.machines.lumbermill.recipes.LumbermillRecipeManager;

public class LumbermillLogic extends ComponentProcessSetCost implements IProcess {
	public static final int PROCESS_ENERGY = 900;
	public static final int PROCESS_LENGTH = 30;
	public static final int WATER_PER_TICK = 10;

	public LumbermillLogic(Machine machine) {
		super(machine, PROCESS_ENERGY, PROCESS_LENGTH);
	}

	@Override
	public ErrorState canWork() {
		MachineUtil util = getUtil();
		ItemStack logStack = util.getStack(LumbermillMachine.SLOT_LOG);
		if (logStack.isEmpty()) {
			return new ErrorState(ExtraTreesErrorCode.LUMBERMILL_NO_WOOD, LumbermillMachine.SLOT_LOG);
		}
		ItemStack plankResult = LumbermillRecipeManager.getPlankProduct(logStack, util.getWorld());
		if (!util.isSlotEmpty(LumbermillMachine.SLOT_PLANKS) && !plankResult.isEmpty()) {
			ItemStack currentPlank = util.getStack(LumbermillMachine.SLOT_PLANKS);
			if (!plankResult.isItemEqual(currentPlank) || plankResult.getCount() + currentPlank.getCount() > currentPlank.getMaxStackSize()) {
				return new ErrorState(ExtraTreesErrorCode.LUMBERMILL_NO_SPACE_PLANKS, new int[]{LumbermillMachine.SLOT_PLANKS});
			}
		}
		if (!util.isSlotEmpty(LumbermillMachine.SLOT_BARK)) {
			ItemStack itemStack = util.getStack(LumbermillMachine.SLOT_BARK);
			if (itemStack.getCount() + 2 > itemStack.getMaxStackSize()) {
				return new ErrorState(ExtraTreesErrorCode.LUMBERMILL_NO_SPACE_BARK, new int[]{LumbermillMachine.SLOT_BARK});
			}
		}
		if (!util.isSlotEmpty(LumbermillMachine.SLOT_SAWDUST)) {
			ItemStack itemStack = util.getStack(LumbermillMachine.SLOT_SAWDUST);
			if (itemStack.getCount() + 2 > itemStack.getMaxStackSize()) {
				return new ErrorState(ExtraTreesErrorCode.LUMBERMILL_NO_SPACE_SAW_DUST, new int[]{LumbermillMachine.SLOT_SAWDUST});
			}
		}
		return super.canWork();
	}

	@Override
	public ErrorState canProgress() {
		if (!this.getUtil().liquidInTank(LumbermillMachine.TANK_WATER, 5)) {
			return new ErrorState(ExtraTreesErrorCode.LUMBERMILL_INSUFFICIENT_WATER, LumbermillMachine.TANK_WATER);
		}
		return super.canProgress();
	}

	@Override
	protected void onFinishTask() {
		MachineUtil util = getUtil();
		ItemStack logStack = util.getStack(LumbermillMachine.SLOT_LOG);
		ItemStack result = LumbermillRecipeManager.getPlankProduct(logStack, util.getWorld());
		if (result.isEmpty()) {
			return;
		}
		util.addStack(LumbermillMachine.SLOT_PLANKS, result);
		util.addStack(LumbermillMachine.SLOT_SAWDUST, ExtraTreeMiscItems.SAWDUST.stack(2));
		util.addStack(LumbermillMachine.SLOT_BARK, ExtraTreeMiscItems.Bark.stack(2));
		util.decreaseStack(LumbermillMachine.SLOT_LOG, 1);
	}

	@Override
	protected void onTickTask() {
		this.getUtil().drainTank(LumbermillMachine.TANK_WATER, WATER_PER_TICK);
	}
}
