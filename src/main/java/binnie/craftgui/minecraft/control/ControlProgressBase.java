package binnie.craftgui.minecraft.control;

import binnie.core.machines.Machine;
import binnie.core.machines.power.IProcess;
import binnie.core.machines.power.ProcessInfo;
import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.Tooltip;
import binnie.craftgui.minecraft.Window;

public class ControlProgressBase extends Control {
    protected float progress;

    public ControlProgressBase(final IWidget parent, final float x, final float y, final float w, final float h) {
        super(parent, x, y, w, h);
        this.progress = 0.0f;
        this.addAttribute(Attribute.MouseOver);
    }

    public void setProgress(final float progress) {
        this.progress = progress;
        if (this.progress < 0.0f) {
            this.progress = 0.0f;
        } else if (this.progress > 1.0f) {
            this.progress = 1.0f;
        }
    }

    protected ProcessInfo getProcess() {
        return Window.get(this).getContainer().getProcessInfo();
    }

    @Override
    public void onUpdateClient() {
        final ProcessInfo process = this.getProcess();
        if (process != null) {
            this.setProgress(process.getCurrentProgress() / 100.0f);
        }
    }

    @Override
    public void getHelpTooltip(final Tooltip tooltip) {
        final ProcessInfo process = this.getProcess();
        final IProcess machineProcess = Machine.getMachine(Window.get(this).getInventory()).getInterface(IProcess.class);
        if (process != null) {
            tooltip.add("Progress");
            if (this.progress == 0.0f) {
                tooltip.add("Not in Progress");
            } else if (process.getProcessTime() > 0) {
                tooltip.add(machineProcess.getTooltip() + " (" + (int) process.getCurrentProgress() + "%)");
            } else {
                tooltip.add("In Progress");
            }
            if (process.getProcessTime() > 0) {
                tooltip.add("Time Left: " + convertTime((int) ((1.0f - this.progress) * process.getProcessTime())));
                tooltip.add("Total Time: " + convertTime(process.getProcessTime()));
                tooltip.add("Energy Cost: " + process.getProcessEnergy() * 10 + " RF");
            } else {
                tooltip.add("Energy Cost: " + process.getEnergyPerTick() * 10.0f + " RF / tick");
            }
        }
    }

    public static String convertTime(final int time) {
        int seconds = (int) (time / 20.0f);
        int minutes = 0;
        while (seconds >= 60) {
            ++minutes;
            seconds -= 60;
        }
        String ts = "";
        if (minutes > 0) {
            ts = ts + minutes + " minute" + ((minutes == 1) ? "" : "s");
        }
        if (seconds > 0) {
            if (ts.length() > 0) {
                ts += " ";
            }
            ts = ts + seconds + " second" + ((seconds == 1) ? "" : "s");
        }
        return ts;
    }
}
