package binnie.core.machines.power;

import binnie.core.machines.IMachine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.errors.CoreErrorCode;
import binnie.core.machines.errors.ErrorState;
import binnie.core.machines.network.INetwork;
import binnie.core.util.I18N;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nullable;

public abstract class ComponentProcessIndefinate extends MachineComponent implements IProcess, INetwork.TilePacketSync {
	private final int clientEnergyPerSecond;
	private int clientInProgress;
	private float energyPerTick;
	private boolean inProgress;
	private float actionPauseProcess;
	private float actionCancelTask;

	public ComponentProcessIndefinate(final IMachine machine, final float energyPerTick) {
		super(machine);
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
			return new ErrorState(CoreErrorCode.TASK_CANCELLED);
		}
	}

	@Override
	@Nullable
	public ErrorState canProgress() {
		if (this.actionPauseProcess != 0.0f) {
			return new ErrorState(CoreErrorCode.TASK_PAUSED);
		} else if (this.getPower().getInterface().getEnergy(PowerSystem.RF) < this.getEnergyPerTick()) {
			return new ErrorState(CoreErrorCode.INSUFFICIENT_POWER);
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
		return I18N.localise("binniecore.machine.tooltips.processing");
	}

	@Override
	public final ProcessInfo getInfo() {
		return new ProcessInfo(this);
	}
}
