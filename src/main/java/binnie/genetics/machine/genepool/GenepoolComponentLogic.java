package binnie.genetics.machine.genepool;

import binnie.Binnie;
import binnie.core.machines.Machine;
import binnie.core.machines.inventory.IChargedSlots;
import binnie.core.machines.network.INetwork;
import binnie.core.machines.power.ComponentProcessSetCost;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IProcess;
import binnie.core.util.I18N;
import binnie.genetics.item.GeneticLiquid;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.ISpeciesRoot;
import net.minecraft.item.ItemStack;

public class GenepoolComponentLogic extends ComponentProcessSetCost implements IProcess, INetwork.TilePacketSync {
    private float ethanolDrain;

    public GenepoolComponentLogic(Machine machine) {
        super(machine, Genepool.RF_COST, Genepool.TIME_PERIOD);
        ethanolDrain = 0.0f;
    }

    @Override
    public ErrorState canWork() {
        if (getUtil().isSlotEmpty(Genepool.SLOT_BEE)) {
            return new ErrorState.NoItem(
                    I18N.localise("genetics.machine.genepool.error.noIndividual"), Genepool.SLOT_BEE);
        }
        return super.canWork();
    }

    @Override
    public ErrorState canProgress() {
        if (!getUtil().spaceInTank(Genepool.TANK_DNA, getDNAAmount(getUtil().getStack(Genepool.TANK_DNA)))) {
            return new ErrorState.NoSpace(I18N.localise("genetics.machine.genepool.error.noRoom"), Genepool.TANK_DNA);
        }
        if (!getUtil().liquidInTank(Genepool.TANK_ETHANOL, 1)) {
            return new ErrorState.InsufficientLiquid(
                    I18N.localise("genetics.machine.genepool.error.noEthanol"), Genepool.TANK_ETHANOL);
        }
        if (getUtil().getSlotCharge(Genepool.SLOT_ENZYME) == 0.0f) {
            return new ErrorState.NoItem(
                    I18N.localise("genetics.machine.genepool.error.insufficientEnzyme"), Genepool.SLOT_ENZYME);
        }
        return super.canProgress();
    }

    @Override
    protected void onFinishTask() {
        super.onFinishTask();
        int amount = getDNAAmount(getUtil().getStack(Genepool.TANK_DNA));
        getUtil().fillTank(Genepool.TANK_DNA, GeneticLiquid.RawDNA.get(amount));
        getUtil().deleteStack(Genepool.TANK_DNA);
    }

    private int getDNAAmount(ItemStack stack) {
        ISpeciesRoot root = AlleleManager.alleleRegistry.getSpeciesRoot(stack);
        if (root == null
                || root != Binnie.Genetics.getBeeRoot()
                || Binnie.Genetics.getBeeRoot().isDrone(stack)) {
            return 10;
        }

        if (Binnie.Genetics.getBeeRoot().isMated(stack)) {
            return 50;
        }
        return 30;
    }

    @Override
    protected void onTickTask() {
        ethanolDrain += getDNAAmount(getUtil().getStack(Genepool.TANK_DNA)) * 1.2f * getProgressPerTick() / 100.0f;
        if (ethanolDrain >= 1.0f) {
            getUtil().drainTank(Genepool.TANK_ETHANOL, Genepool.ETHANOL_PER_TICK);
            ethanolDrain--;
        }

        getMachine()
                .getInterface(IChargedSlots.class)
                .alterCharge(Genepool.SLOT_ENZYME, -Genepool.ENZYME_PER_PROCESS * getProgressPerTick() / 100.0f);
    }
}
