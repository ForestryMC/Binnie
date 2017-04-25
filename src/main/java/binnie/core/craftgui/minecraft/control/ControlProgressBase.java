// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui.minecraft.control;

import binnie.core.craftgui.WidgetAttribute;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.machines.Machine;
import binnie.core.machines.power.IProcess;
import binnie.core.machines.power.ProcessInfo;

public class ControlProgressBase extends Control
{
	protected float progress;

	public ControlProgressBase(IWidget parent, float x, float y, float w, float h) {
		super(parent, x, y, w, h);
		progress = 0.0f;
		addAttribute(WidgetAttribute.MouseOver);
	}

	public void setProgress(float progress) {
		this.progress = progress;
		if (this.progress < 0.0f) {
			this.progress = 0.0f;
		}
		else if (this.progress > 1.0f) {
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
		IProcess machineProcess = Machine.getMachine(Window.get(this).getInventory()).getInterface(IProcess.class);
		if (process != null) {
			tooltip.add("Progress");
			if (progress == 0.0f) {
				tooltip.add("Not in Progress");
			}
			else if (process.getProcessTime() > 0) {
				tooltip.add(machineProcess.getTooltip() + " (" + (int) process.getCurrentProgress() + "%)");
			}
			else {
				tooltip.add("In Progress");
			}
			if (process.getProcessTime() > 0) {
				tooltip.add("Time Left: " + convertTime((int) ((1.0f - progress) * process.getProcessTime())));
				tooltip.add("Total Time: " + convertTime(process.getProcessTime()));
				tooltip.add("Energy Cost: " + process.getProcessEnergy() * 10 + " RF");
			}
			else {
				tooltip.add("Energy Cost: " + process.getEnergyPerTick() * 10.0f + " RF / tick");
			}
		}
	}

	public static String convertTime(int time) {
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
