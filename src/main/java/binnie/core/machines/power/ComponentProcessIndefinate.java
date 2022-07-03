package binnie.core.machines.power;

import binnie.core.machines.IMachine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.network.INetwork;
import binnie.core.util.I18N;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ComponentProcessIndefinate extends MachineComponent implements IProcess, INetwork.TilePacketSync {
    private float energyPerTick;
    private boolean inProgress;
    private float actionPauseProcess;
    private float actionCancelTask;
    int clientEnergyPerSecond;

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
        this.energyPerTick = energyPerTick;
        actionPauseProcess = 0.0f;
        actionCancelTask = 0.0f;
        clientEnergyPerSecond = 0;
    }

    protected IPoweredMachine getPower() {
        return getMachine().getInterface(IPoweredMachine.class);
    }

    @Override
    public float getEnergyPerTick() {
        return energyPerTick;
    }

    @Override
    public void onUpdate() {
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
        if (actionCancelTask > 0.0f) {
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
        return new ErrorState(
                I18N.localise("binniecore.gui.tooltip.task.canceled"),
                I18N.localise("binniecore.gui.tooltip.task.canceled.desc"));
    }

    @Override
    public ErrorState canProgress() {
        if (actionPauseProcess != 0.0f) {
            return new ErrorState(
                    I18N.localise("binniecore.gui.tooltip.task.paused"),
                    I18N.localise("binniecore.gui.tooltip.task.paused.desc"));
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
        return I18N.localise("binniecore.gui.tooltip.processing");
    }

    @Override
    public ProcessInfo getInfo() {
        return new ProcessInfo(this);
    }
}
