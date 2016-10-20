package binnie.core.machines.power;

import binnie.core.machines.IMachine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.network.INetwork;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ComponentProcessIndefinate extends MachineComponent implements IProcess, INetwork.TilePacketSync {
    private float energyPerTick;
    private boolean inProgress;
    private float actionPauseProcess;
    private float actionCancelTask;
    int clientEnergyPerSecond;
    int clientInProgress;

    @Override
    public void syncFromNBT(final NBTTagCompound nbt) {
        this.inProgress = nbt.getBoolean("progress");
    }

    @Override
    public void syncToNBT(final NBTTagCompound nbt) {
        nbt.setBoolean("progress", this.inProgress);
    }

    public ComponentProcessIndefinate(final IMachine machine, final float energyPerTick) {
        super(machine);
        this.energyPerTick = 0.1f;
        this.actionPauseProcess = 0.0f;
        this.actionCancelTask = 0.0f;
        this.clientEnergyPerSecond = 0;
        this.energyPerTick = energyPerTick;
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
    public ErrorState canWork() {
        return (this.actionCancelTask == 0.0f) ? null : new ErrorState("Task Cancelled", "Cancelled by Buildcraft Gate");
    }

    @Override
    public ErrorState canProgress() {
        if (this.actionPauseProcess != 0.0f) {
            return new ErrorState("Process Paused", "Paused by Buildcraft Gate");
        }
        return (this.getPower().getInterface().getEnergy(PowerSystem.RF) < this.getEnergyPerTick()) ? new ErrorState.InsufficientPower() : null;
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
        return "Processing";
    }

    @Override
    public final ProcessInfo getInfo() {
        return new ProcessInfo(this);
    }
}
