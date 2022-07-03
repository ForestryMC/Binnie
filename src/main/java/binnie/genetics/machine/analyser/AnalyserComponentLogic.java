package binnie.genetics.machine.analyser;

import binnie.core.machines.Machine;
import binnie.core.machines.power.ComponentProcessSetCost;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IProcess;
import binnie.core.util.I18N;
import net.minecraft.item.ItemStack;

public class AnalyserComponentLogic extends ComponentProcessSetCost implements IProcess {
    public AnalyserComponentLogic(Machine machine) {
        super(machine, Analyser.RF_COST, Analyser.TIME_PERIOD);
    }

    @Override
    public ErrorState canWork() {
        if (getUtil().isSlotEmpty(Analyser.SLOT_TARGET)) {
            return new ErrorState.NoItem(I18N.localise("genetics.machine.analyser.error.noItem"), Analyser.SLOT_TARGET);
        }

        boolean analysed = Analyser.isAnalysed(getUtil().getStack(Analyser.SLOT_TARGET));
        if (!analysed) {
            return super.canWork();
        }

        return new ErrorState.InvalidItem(
                I18N.localise("genetics.machine.analyser.error.alreadyAnalysed.title"),
                I18N.localise("genetics.machine.analyser.error.alreadyAnalysed"),
                Analyser.SLOT_TARGET);
    }

    @Override
    public ErrorState canProgress() {
        if (getUtil().getSlotCharge(Analyser.SLOT_DYE) != 0.0f) {
            return super.canProgress();
        }

        return new ErrorState.Item(
                I18N.localise("genetics.machine.analyser.error.insufficientDye.title"),
                I18N.localise("genetics.machine.analyser.error.insufficientDye"),
                new int[] {Analyser.SLOT_DYE});
    }

    @Override
    protected void onFinishTask() {
        super.onFinishTask();
        ItemStack itemStack = getUtil().getStack(Analyser.SLOT_TARGET);
        itemStack = Analyser.analyse(itemStack);
        getInventory().setInventorySlotContents(Analyser.SLOT_TARGET, itemStack);
    }

    @Override
    protected void onTickTask() {
        getUtil().useCharge(Analyser.SLOT_DYE, Analyser.DYE_PER_TICK);
    }
}
