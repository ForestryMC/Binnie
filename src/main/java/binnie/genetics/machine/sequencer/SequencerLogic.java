package binnie.genetics.machine.sequencer;

import binnie.core.machines.Machine;
import binnie.core.machines.power.ComponentProcess;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IProcess;
import binnie.genetics.Genetics;
import binnie.genetics.genetics.GeneTracker;
import binnie.genetics.genetics.SequencerItem;
import binnie.genetics.item.GeneticsItems;
import net.minecraft.item.ItemStack;

public class SequencerLogic extends ComponentProcess implements IProcess {
	public SequencerLogic(final Machine machine) {
		super(machine);
	}

	public float getSequenceStrength() {
		final ItemStack stack = this.getUtil().getStack(Sequencer.SLOT_TARGET);
		if (stack == null) {
			return 1.0f;
		}
		final float mult = 1.0f - stack.getItemDamage() % 6 / 5.0f;
		return 1.0f - mult * mult * 0.75f;
	}

	@Override
	public int getProcessLength() {
		return (int) (19200.0f * this.getSequenceStrength());
	}

	@Override
	public int getProcessEnergy() {
		return this.getProcessLength() * 20;
	}

	@Override
	public ErrorState canWork() {
		if (this.getUtil().isSlotEmpty(Sequencer.SLOT_TARGET)) {
			return new ErrorState.NoItem(Genetics.proxy.localise("machine.machine.sequencer.no.dna"), 5);
		}
		return super.canWork();
	}

	@Override
	public ErrorState canProgress() {
		if (this.getMachine().getOwner() == null) {
			return new ErrorState(Genetics.proxy.localise("machine.machine.sequencer.no.owner"), Genetics.proxy.localise("machine.machine.sequencer.no.owner.info"));
		}
		if (this.getUtil().getSlotCharge(Sequencer.SLOT_DYE) == 0.0f) {
			return new ErrorState.NoItem(Genetics.proxy.localise("machine.machine.sequencer.insufficient.dye"), Sequencer.SLOT_DYE);
		}
		if (this.getUtil().getStack(Sequencer.SLOT_DRONE) != null && this.getUtil().getStack(Sequencer.SLOT_DRONE).stackSize >= 64) {
			return new ErrorState.NoSpace(Genetics.proxy.localise("machine.machine.sequencer.no.space"), new int[]{Sequencer.SLOT_DRONE});
		}
		return super.canProgress();
	}

	@Override
	protected void onStartTask() {
		super.onStartTask();
		final ItemStack item = this.getUtil().getStack(Sequencer.SLOT_TARGET);
		final SequencerItem seqItem = new SequencerItem(item);
		final int seq = seqItem.sequenced;
		if (seq != 0) {
			this.setProgress(seq);
		}
	}

	@Override
	protected void onFinishTask() {
		super.onFinishTask();
		this.updateSequence();
		final SequencerItem seqItem = new SequencerItem(this.getUtil().getStack(Sequencer.SLOT_TARGET));
		GeneTracker.getTracker(this.getMachine().getWorld(), this.getMachine().getOwner()).registerGene(seqItem.getGene());
		this.getUtil().decreaseStack(Sequencer.SLOT_TARGET, 1);
		if (this.getUtil().getStack(Sequencer.SLOT_DRONE) == null) {
			this.getUtil().setStack(Sequencer.SLOT_DRONE, GeneticsItems.EmptySequencer.get(1));
		} else {
			this.getUtil().decreaseStack(Sequencer.SLOT_DRONE, -1);
		}
	}

	@Override
	protected void onTickTask() {
		this.updateSequence();
		this.getUtil().useCharge(Sequencer.SLOT_DYE, 0.4f * this.getProgressPerTick() / 100.0f);
	}

	private void updateSequence() {
		final int prog = (int) this.getProgress();
		final ItemStack item = this.getUtil().getStack(Sequencer.SLOT_TARGET);
		final SequencerItem seqItem = new SequencerItem(item);
		final int seq = seqItem.sequenced;
		if (prog != seq) {
			seqItem.sequenced = prog;
			seqItem.writeToItem(item);
		}
	}
}
