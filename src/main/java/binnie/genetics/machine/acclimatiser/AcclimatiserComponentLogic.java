package binnie.genetics.machine.acclimatiser;

import binnie.core.machines.IMachine;
import binnie.core.machines.power.ComponentProcessIndefinate;
import binnie.core.machines.power.ErrorState;
import binnie.core.util.I18N;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemStack;

public class AcclimatiserComponentLogic extends ComponentProcessIndefinate {
    public AcclimatiserComponentLogic(IMachine machine) {
        super(machine, Acclimatiser.ENERGY_PER_TICK);
    }

    @Override
    public ErrorState canWork() {
        if (getUtil().getStack(Acclimatiser.SLOT_TARGET) == null) {
            return new ErrorState.NoItem(
                    I18N.localise("genetics.machine.acclimatiser.error.noIndividual"), Acclimatiser.SLOT_TARGET);
        }
        if (getUtil().getNonNullStacks(Acclimatiser.SLOT_ACCLIMATISER).isEmpty()) {
            return new ErrorState.NoItem(
                    I18N.localise("genetics.machine.acclimatiser.error.noAcclimatizingItems"),
                    Acclimatiser.SLOT_ACCLIMATISER);
        }
        return super.canWork();
    }

    @Override
    public ErrorState canProgress() {
        if (!Acclimatiser.canAcclimatise(
                getUtil().getStack(Acclimatiser.SLOT_TARGET),
                getUtil().getNonNullStacks(Acclimatiser.SLOT_ACCLIMATISER))) {
            return new ErrorState.InvalidItem(
                    I18N.localise("genetics.machine.acclimatiser.error.invalidAcclimatizingItems"),
                    Acclimatiser.SLOT_TARGET);
        }
        return super.canProgress();
    }

    @Override
    protected boolean inProgress() {
        return canWork() == null;
    }

    @Override
    protected void onTickTask() {
        super.onTickTask();
        if (getUtil().getRandom().nextInt(100) == 0) {
            attemptAcclimatisation();
        }
    }

    protected void attemptAcclimatisation() {
        List<ItemStack> acclms = new ArrayList<>();
        for (ItemStack s : getUtil().getNonNullStacks(Acclimatiser.SLOT_ACCLIMATISER)) {
            if (Acclimatiser.canAcclimatise(getUtil().getStack(Acclimatiser.SLOT_TARGET), s)) {
                acclms.add(s);
            }
        }

        ItemStack acc = acclms.get(getUtil().getRandom().nextInt(acclms.size()));
        ItemStack acclimed = Acclimatiser.acclimatise(getUtil().getStack(Acclimatiser.SLOT_TARGET), acc);
        if (acclimed == null) {
            return;
        }

        getUtil().setStack(Acclimatiser.SLOT_TARGET, acclimed);
        boolean removed = false;
        for (int i : Acclimatiser.SLOT_ACCLIMATISER) {
            if (!removed
                    && getUtil().getStack(i) != null
                    && getUtil().getStack(i).isItemEqual(acc)) {
                getUtil().decreaseStack(i, 1);
                removed = true;
            }
        }
    }
}
