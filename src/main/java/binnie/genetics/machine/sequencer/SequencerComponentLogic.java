package binnie.genetics.machine.sequencer;

import binnie.core.machines.Machine;
import binnie.core.machines.inventory.ComponentInventoryTransfer;
import binnie.core.machines.power.ComponentProcess;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IProcess;
import binnie.core.util.I18N;
import binnie.genetics.genetics.GeneTracker;
import binnie.genetics.genetics.SequencerItem;
import binnie.genetics.item.GeneticsItems;
import net.minecraft.item.ItemStack;

public class SequencerComponentLogic extends ComponentProcess
        implements IProcess, ComponentInventoryTransfer.ITransferRestockListener {
    public SequencerComponentLogic(Machine machine) {
        super(machine);
    }

    public float getSequenceStrength() {
        ItemStack stack = getUtil().getStack(Sequencer.SLOT_TARGET_INDEX);
        if (stack == null) {
            return 1.0f;
        }

        float mult = 1.0f - stack.getItemDamage() % 6 / 5.0f;
        return 1.0f - mult * mult * 0.75f;
    }

    @Override
    public int getProcessLength() {
        return (int) (19200.0f * getSequenceStrength());
    }

    @Override
    public int getProcessEnergy() {
        return getProcessLength() * 20;
    }

    @Override
    public ErrorState canWork() {
        if (getUtil().isSlotEmpty(Sequencer.SLOT_TARGET_INDEX)) {
            return new ErrorState.NoItem(
                    I18N.localise("genetics.machine.sequencer.error.noSequence"), Sequencer.SLOT_TARGET_INDEX);
        }
        return super.canWork();
    }

    @Override
    public ErrorState canProgress() {
        if (getMachine().getOwner() == null) {
            return new ErrorState(
                    I18N.localise("genetics.machine.sequencer.error.noOwner.title"),
                    I18N.localise("genetics.machine.sequencer.error.noOwner"));
        }
        if (getUtil().getSlotCharge(Sequencer.SLOT_DYE_INDEX) == 0.0f) {
            return new ErrorState.NoItem(
                    I18N.localise("genetics.machine.sequencer.error.noDye"), Sequencer.SLOT_DYE_INDEX);
        }

        ItemStack stack = getUtil().getStack(Sequencer.SLOT_DONE_INDEX);
        if (stack != null && stack.stackSize >= 64) {
            return new ErrorState.NoSpace(
                    I18N.localise("genetics.machine.sequencer.error.noSpace"), Sequencer.SLOT_DONE_INDEX);
        }
        return super.canProgress();
    }

    @Override
    public void onRestock(int target) {
        onStartTask();
    }

    @Override
    protected void onStartTask() {
        super.onStartTask();
        ItemStack item = getUtil().getStack(Sequencer.SLOT_TARGET_INDEX);
        SequencerItem seqItem = new SequencerItem(item);
        int seq = seqItem.sequenced;
        if (seq != 0) {
            setProgress(seq);
        }
    }

    @Override
    protected void onFinishTask() {
        super.onFinishTask();
        updateSequence();
        SequencerItem seqItem = new SequencerItem(getUtil().getStack(Sequencer.SLOT_TARGET_INDEX));
        GeneTracker.getTracker(getMachine().getWorld(), getMachine().getOwner()).registerGene(seqItem.getGene());
        getUtil().decreaseStack(Sequencer.SLOT_TARGET_INDEX, 1);

        if (getUtil().getStack(Sequencer.SLOT_DONE_INDEX) == null) {
            getUtil().setStack(Sequencer.SLOT_DONE_INDEX, GeneticsItems.EmptySequencer.get(1));
        } else {
            getUtil().decreaseStack(Sequencer.SLOT_DONE_INDEX, -1);
        }
    }

    @Override
    protected void onTickTask() {
        updateSequence();
        getUtil().useCharge(Sequencer.SLOT_DYE_INDEX, 0.4f * getProgressPerTick() / 100.0f);
    }

    private void updateSequence() {
        int prog = (int) getProgress();
        ItemStack item = getUtil().getStack(Sequencer.SLOT_TARGET_INDEX);
        SequencerItem seqItem = new SequencerItem(item);
        int seq = seqItem.sequenced;
        if (prog != seq) {
            seqItem.sequenced = prog;
            seqItem.writeToItem(item);
        }
    }
}
