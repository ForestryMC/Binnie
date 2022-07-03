package binnie.core.craftgui.minecraft.control;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.WidgetAttribute;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.machines.Machine;
import binnie.core.machines.power.IProcess;
import binnie.core.machines.power.ProcessInfo;
import binnie.core.util.I18N;

public class ControlProgressBase extends Control {
    protected float progress;

    public ControlProgressBase(IWidget parent, float x, float y, float w, float h) {
        super(parent, x, y, w, h);
        progress = 0.0f;
        addAttribute(WidgetAttribute.MOUSE_OVER);
    }

    public static String convertTime(int time) {
        int seconds = (int) (time / 20.0f);
        int minutes = 0;
        while (seconds >= 60) {
            minutes++;
            seconds -= 60;
        }

        String ts = "";
        if (minutes > 0) {
            if (minutes == 1) {
                ts += I18N.localise("binniecore.gui.database.time.minute");
            } else {
                ts += I18N.localise("binniecore.gui.database.time.minutes", minutes);
            }
        }

        if (seconds > 0) {
            if (ts.length() > 0) {
                ts += " ";
            }
            if (seconds == 1) {
                ts += I18N.localise("binniecore.gui.database.time.second");
            } else {
                ts += I18N.localise("binniecore.gui.database.time.seconds", seconds);
            }
        }
        return ts;
    }

    public void setProgress(float progress) {
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
        ProcessInfo process = getProcess();
        if (process != null) {
            setProgress(process.getCurrentProgress() / 100.0f);
        }
    }

    @Override
    public void getHelpTooltip(Tooltip tooltip) {
        ProcessInfo process = getProcess();
        IProcess machineProcess =
                Machine.getMachine(Window.get(this).getInventory()).getInterface(IProcess.class);
        if (process == null) {
            return;
        }

        tooltip.add(I18N.localise("binniecore.gui.database.time.progress"));

        if (progress == 0.0f) {
            tooltip.add(I18N.localise("binniecore.gui.database.time.notInProgress"));
        } else if (process.getProcessTime() > 0) {
            tooltip.add(machineProcess.getTooltip() + " (" + (int) process.getCurrentProgress() + "%)");
        } else {
            tooltip.add(I18N.localise("binniecore.gui.database.time.inProgress"));
        }

        if (process.getProcessTime() > 0) {
            tooltip.add(I18N.localise("binniecore.gui.database.time.left", convertTime((int)
                    ((1.0f - progress) * process.getProcessTime()))));
            tooltip.add(I18N.localise("binniecore.gui.database.time.total", convertTime(process.getProcessTime())));
            tooltip.add(I18N.localise("binniecore.gui.database.energyCost.0", process.getProcessEnergy() * 10));
        } else {
            tooltip.add(I18N.localise("binniecore.gui.database.energyCost.1", process.getEnergyPerTick() * 10.0f));
        }
    }
}
