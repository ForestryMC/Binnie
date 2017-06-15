package binnie.core.machines.power;

import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;

import binnie.core.BinnieCore;
import binnie.core.machines.IMachine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.network.INetwork;

public abstract class ComponentProcessIndefinate extends MachineComponent implements IProcess, INetwork.TilePacketSync {
	int clientEnergyPerSecond;
	int clientInProgress;
	private float energyPerTick;
	private boolean inProgress;
	private float actionPauseProcess;
	private float actionCancelTask;

	public ComponentProcessIndefinate(final IMachine machine, final float energyPerTick) {
		super(machine);
		this.energyPerTick = 0.1f;
		this.actionPauseProcess = 0.0f;
		this.actionCancelTask = 0.0f;
		this.clientEnergyPerSecond = 0;
		this.energyPerTick = energyPerTick;
	}

	@Override
	public void syncFromNBT(final NBTTagCompound nbt) {
		this.inProgress = nbt.getBoolean("progress");
	}

	@Override
	public void syncToNBT(final NBTTagCompound nbt) {
		nbt.setBoolean("progress", this.inProgress);
	}

	protected final IPoweredMachine getPower() {
		return this.getMachine().getInterface(IPoweredMachine.class);
	}

	@Override
	public float getEnergyPerTick() {
		return this.energyPerTick;
	}

	@Override
	public void onUpdate() {
		final float energyAvailable = (float) this.getPower().getInterface().useEnergy(PowerSystem.RF, this.getEnergyPerTick(), false);
		if (this.canWork() == null) {
			if (!this.isInProgress() && this.canProgress() == null) {
				this.onStartTask();
			} else if (this.canProgress() == null) {
				this.progressTick();
				this.onTickTask();
			}
		} else if (this.isInProgress()) {
			this.onCancelTask();
		}
		if (this.actionPauseProcess > 0.0f) {
			--this.actionPauseProcess;
		}
		if (this.actionCancelTask > 0.0f) {
			--this.actionCancelTask;
		}
		super.onUpdate();
		if (this.inProgress != this.inProgress()) {
			this.inProgress = this.inProgress();
			this.getUtil().refreshBlock();
		}
	}

	protected void progressTick() {
		this.getPower().getInterface().useEnergy(PowerSystem.RF, this.getEnergyPerTick(), true);
	}

	@Override
	@Nullable
	public ErrorState canWork() {
		if (this.actionCancelTask == 0.0f) {
			return null;
		} else {
			return new ErrorState(BinnieCore.getBinnieProxy().localise("machine.errors.task.cancelled.desc"), BinnieCore.getBinnieProxy().localise("machine.errors.task.cancelled.info"));
		}
	}

	@Override
	@Nullable
	public ErrorState canProgress() {
		if (this.actionPauseProcess != 0.0f) {
			return new ErrorState(BinnieCore.getBinnieProxy().localise("machine.errors.task.process.paused.desc"), BinnieCore.getBinnieProxy().localise("machine.errors.task.process.paused.info"));
		} else if (this.getPower().getInterface().getEnergy(PowerSystem.RF) < this.getEnergyPerTick()) {
			return new ErrorState.InsufficientPower();
		} else {
			return null;
		}
	}

	@Override
	public final boolean isInProgress() {
		return this.inProgress;
	}

	protected abstract boolean inProgress();

	protected void onCancelTask() {
	}

	protected void onStartTask() {
	}

	protected void onTickTask() {
	}

	@Override
	public String getTooltip() {
		return BinnieCore.getBinnieProxy().localise("machine.tooltips.processing");
	}

	@Override
	public final ProcessInfo getInfo() {
		return new ProcessInfo(this);
	}
}
