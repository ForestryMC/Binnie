package binnie.extratrees.machines.lumbermill;

import binnie.core.item.ItemMisc;
import binnie.core.machines.Machine;
import binnie.core.machines.power.ComponentProcessSetCost;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IProcess;
import binnie.core.util.I18N;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.item.ExtraTreeItems;
import net.minecraft.item.ItemStack;

public class LumbermillComponentLogic extends ComponentProcessSetCost implements IProcess {
    public LumbermillComponentLogic(Machine machine) {
        super(machine, Lumbermill.RF_COST, Lumbermill.TIME_PERIOD);
    }

    @Override
    public ErrorState canWork() {
        if (getUtil().isSlotEmpty(Lumbermill.SLOT_WOOD)) {
            return new ErrorState.NoItem(
                    I18N.localise("extratrees.machine.lumbermill.error.noWood"), Lumbermill.SLOT_WOOD);
        }

        ItemStack result = Lumbermill.getPlankProduct(getUtil().getStack(Lumbermill.SLOT_WOOD));
        if (!getUtil().isSlotEmpty(Lumbermill.SLOT_PLANKS) && result != null) {
            ItemStack currentPlank = getUtil().getStack(Lumbermill.SLOT_PLANKS);
            if (!result.isItemEqual(currentPlank)
                    || result.stackSize + currentPlank.stackSize > currentPlank.getMaxStackSize()) {
                return new ErrorState.NoSpace(
                        I18N.localise("extratrees.machine.lumbermill.error.noRoom"), Lumbermill.SLOT_PLANKS);
            }
        }
        return super.canWork();
    }

    @Override
    public ErrorState canProgress() {
        if (!getUtil().liquidInTank(Lumbermill.TANK_WATER, 5)) {
            return new ErrorState.InsufficientLiquid(
                    I18N.localise("extratrees.machine.lumbermill.error.noWater"), Lumbermill.TANK_WATER);
        }
        return super.canProgress();
    }

    @Override
    protected void onFinishTask() {
        ItemStack result = Lumbermill.getPlankProduct(getUtil().getStack(Lumbermill.SLOT_WOOD));
        if (result == null) {
            return;
        }

        getUtil().addStack(Lumbermill.SLOT_PLANKS, result);
        getUtil()
                .addStack(
                        Lumbermill.SLOT_SAWDUST, ((ItemMisc) ExtraTrees.itemMisc).getStack(ExtraTreeItems.Sawdust, 1));
        getUtil().addStack(Lumbermill.SLOT_BARK, ((ItemMisc) ExtraTrees.itemMisc).getStack(ExtraTreeItems.Bark, 1));
        getUtil().decreaseStack(Lumbermill.SLOT_WOOD, 1);
    }

    @Override
    protected void onTickTask() {
        getUtil().drainTank(Lumbermill.TANK_WATER, Lumbermill.WATER_PER_TICK);
    }
}
