package binnie.genetics.machine.polymeriser;

import binnie.core.machines.Machine;
import binnie.core.machines.power.ComponentProcessSetCost;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IProcess;
import binnie.core.util.I18N;
import binnie.genetics.genetics.Engineering;
import net.minecraft.item.ItemStack;

public class PolymeriserComponentLogic extends ComponentProcessSetCost implements IProcess {
    private static float chargePerProcess = 0.4f;

    private float dnaDrain = 0.0f;
    private float bacteriaDrain = 0.0f;

    public PolymeriserComponentLogic(Machine machine) {
        super(machine, Polymeriser.RF_COST, Polymeriser.TIME_PERIOD);
    }

    private float getCatalyst() {
        return (getUtil().getSlotCharge(Polymeriser.SLOT_GOLD) > 0.0f) ? 0.2f : 1.0f;
    }

    @Override
    public int getProcessLength() {
        return (int) (super.getProcessLength() * getNumberOfGenes() * getCatalyst());
    }

    @Override
    public int getProcessEnergy() {
        return (int) (super.getProcessEnergy() * getNumberOfGenes() * getCatalyst());
    }

    private float getDNAPerProcess() {
        return getNumberOfGenes() * 50;
    }

    @Override
    public void onTickTask() {
        super.onTickTask();
        getUtil()
                .useCharge(
                        Polymeriser.SLOT_GOLD,
                        PolymeriserComponentLogic.chargePerProcess * getProgressPerTick() / 100.0f);
        dnaDrain += getDNAPerProcess() * getProgressPerTick() / 100.0f;
        bacteriaDrain += 0.2f * getDNAPerProcess() * getProgressPerTick() / 100.0f;

        if (dnaDrain >= 1.0f) {
            getUtil().drainTank(Polymeriser.TANK_DNA, 1);
            dnaDrain--;
        }

        if (bacteriaDrain >= 1.0f) {
            getUtil().drainTank(Polymeriser.TANK_BACTERIA, 1);
            bacteriaDrain--;
        }
    }

    private int getNumberOfGenes() {
        ItemStack serum = getUtil().getStack(Polymeriser.SLOT_SERUM);
        if (serum == null) {
            return 1;
        }
        return Engineering.getGenes(serum).length;
    }

    @Override
    public String getTooltip() {
        int n = getNumberOfGenes();
        if (n > 1) {
            return I18N.localise("genetics.machine.polymeriser.replicatingWithGenes", n);
        }
        return I18N.localise("genetics.machine.polymeriser.replicatingWithGene");
    }

    @Override
    public ErrorState canWork() {
        if (getUtil().isSlotEmpty(Polymeriser.SLOT_SERUM)) {
            return new ErrorState.NoItem(
                    I18N.localise("genetics.machine.polymeriser.error.noItem"), Polymeriser.SLOT_SERUM);
        }
        if (!getUtil().getStack(Polymeriser.SLOT_SERUM).isItemDamaged()) {
            return new ErrorState.InvalidItem(
                    I18N.localise("genetics.machine.polymeriser.error.itemFilled"), Polymeriser.SLOT_SERUM);
        }
        return super.canWork();
    }

    @Override
    public ErrorState canProgress() {
        if (getUtil().getFluid(Polymeriser.TANK_BACTERIA) == null) {
            return new ErrorState.InsufficientLiquid(
                    I18N.localise("genetics.machine.polymeriser.error.noLiquid"), Polymeriser.TANK_BACTERIA);
        }
        if (getUtil().getFluid(Polymeriser.TANK_DNA) == null) {
            return new ErrorState.InsufficientLiquid(
                    I18N.localise("genetics.machine.polymeriser.error.noDNA"), Polymeriser.TANK_DNA);
        }
        return super.canProgress();
    }

    @Override
    protected void onFinishTask() {
        super.onFinishTask();
        getUtil().damageItem(Polymeriser.SLOT_SERUM, -1);
    }
}
