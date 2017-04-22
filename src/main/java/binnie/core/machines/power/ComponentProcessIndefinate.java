package binnie.core.machines.power;

import binnie.core.machines.*;
import binnie.core.machines.network.*;
import net.minecraft.nbt.*;

public abstract class ComponentProcessIndefinate extends MachineComponent implements IProcess, INetwork.TilePacketSync {
	private float energyPerTick;
	private boolean inProgress;
	private float actionPauseProcess;
	private float actionCancelTask;
	int clientEnergyPerSecond;
	int clientInProgress;

	@Override
	public void syncFromNBT(NBTTagCompound nbt) {
		inProgress = nbt.getBoolean("progress");
	}

	@Override
	public void syncToNBT(NBTTagCompound nbt) {
		nbt.setBoolean("progress", inProgress);
	}

	public ComponentProcessIndefinate(IMachine machine, float energyPerTick) {
		super(machine);
		actionPauseProcess = 0.0f;
		actionCancelTask = 0.0f;
		clientEnergyPerSecond = 0;
		this.energyPerTick = energyPerTick;
	}

	protected IPoweredMachine getPower() {
		return this.getMachine().getInterface(IPoweredMachine.class);
	}

	@Override
	public float getEnergyPerTick() {
		return this.energyPerTick;
	}

	@Override
	public void onUpdate() {
		float energyAvailable = (float) this.getPower().getInterface().useEnergy(PowerSystem.RF, this.getEnergyPerTick(), false);
		if (canWork() == null) {
			if (!isInProgress() && canProgress() == null) {
				onStartTask();
			} else if (canProgress() == null) {
				progressTick();
				onTickTask();
			}
		} else if (isInProgress()) {
			onCancelTask();
		}
		if (actionPauseProcess > 0.0f) {
			actionPauseProcess--;
		}
		if (this.actionCancelTask > 0.0f) {
			actionCancelTask--;
		}

		super.onUpdate();
		if (inProgress != inProgress()) {
			inProgress = inProgress();
			getUtil().refreshBlock();
		}
	}

	protected void progressTick() {
		getPower().getInterface().useEnergy(PowerSystem.RF, getEnergyPerTick(), true);
	}

	@Override
	public ErrorState canWork() {
		if (actionCancelTask == 0.0f) {
			return null;
		}
		return new ErrorState("Task Cancelled", "Cancelled by Buildcraft Gate");
	}

	@Override
	public ErrorState canProgress() {
		if (actionPauseProcess != 0.0f) {
			return new ErrorState("Process Paused", "Paused by Buildcraft Gate");
		}
		if (getPower().getInterface().getEnergy(PowerSystem.RF) < getEnergyPerTick()) {
			return new ErrorState.InsufficientPower();
		}
		return null;
	}

	@Override
	public boolean isInProgress() {
		return inProgress;
	}

	protected abstract boolean inProgress();

	protected void onCancelTask() {
		// ignored
	}

	protected void onStartTask() {
		// ignored
	}

	protected void onTickTask() {
		// ignored
	}

	@Override
	public String getTooltip() {
		return "Processing";
	}

	@Override
	public ProcessInfo getInfo() {
		return new ProcessInfo(this);
	}
}
