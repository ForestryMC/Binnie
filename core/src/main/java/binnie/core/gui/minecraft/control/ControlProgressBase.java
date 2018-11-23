package binnie.core.gui.minecraft.control;

import java.text.NumberFormat;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.util.text.TextFormatting;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.ModId;
import binnie.core.api.gui.IWidget;
import binnie.core.gui.Attribute;
import binnie.core.gui.Tooltip;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.minecraft.Window;
import binnie.core.machines.Machine;
import binnie.core.machines.power.IProcess;
import binnie.core.machines.power.ProcessInfo;
import binnie.core.util.I18N;

public class ControlProgressBase extends Control {
	protected float progress;

	public ControlProgressBase(IWidget parent, int x, int y, int w, int h) {
		super(parent, x, y, w, h);
		this.progress = 0.0f;
		this.addAttribute(Attribute.MOUSE_OVER);
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
		ProcessInfo process = this.getProcess();
		if (process != null) {
			this.setProgress(process.getCurrentProgress() / 100.0f);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getHelpTooltip(Tooltip tooltip, ITooltipFlag tooltipFlag) {
		ProcessInfo process = this.getProcess();
		if (process != null) {
			tooltip.add(I18N.localise(ModId.CORE, "gui.progress.title"));
			if (this.progress == 0.0f) {
				tooltip.add(I18N.localise(ModId.CORE, "gui.progress.no.progress"));
			} else if (process.getProcessTime() > 0) {
				IProcess machineProcess = Machine.getMachine(Window.get(this).getInventory()).getInterface(IProcess.class);
				String percentProgress = I18N.getPercentFormat().format((int) process.getCurrentProgress() / 100.0);
				tooltip.add(machineProcess.getTooltip() + " (" + percentProgress + ')');
			} else {
				tooltip.add(I18N.localise(ModId.CORE, "gui.progress.in.progress"));
			}
			if (tooltipFlag.isAdvanced()) {
				NumberFormat numberFormat = I18N.getNumberFormat();
				if (process.getProcessTime() > 0) {
					String timeLeft = convertTime((int) ((1.0f - this.progress) * process.getProcessTime()));
					tooltip.add(TextFormatting.GRAY + I18N.localise(ModId.CORE, "gui.progress.time.left", timeLeft));
					String timeTotal = convertTime(process.getProcessTime());
					tooltip.add(TextFormatting.GRAY + I18N.localise(ModId.CORE, "gui.progress.time.total", timeTotal));
					String energyCostTotal = numberFormat.format(process.getProcessEnergy() * 10);
					tooltip.add(TextFormatting.GRAY + I18N.localise(ModId.CORE, "gui.progress.energy.cost.total", energyCostTotal));
				} else {
					String energyCostPerTick = numberFormat.format(process.getEnergyPerTick() * 10.0f);
					tooltip.add(TextFormatting.GRAY + I18N.localise(ModId.CORE, "gui.energy.cost.per.tick", energyCostPerTick));
				}
			}
		}
	}

	@Override
	public boolean showBasicHelpTooltipsByDefault() {
		return true;
	}
}
