package binnie.genetics.machine.sequencer;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.geometry.Area;
import binnie.core.craftgui.geometry.TextJustification;
import binnie.core.craftgui.minecraft.MinecraftGUI;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.minecraft.control.ControlProgressBase;
import binnie.core.craftgui.window.Panel;
import binnie.core.machines.IMachine;
import binnie.core.machines.Machine;
import binnie.core.machines.MachineUtil;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class ControlSequencerProgress extends ControlProgressBase {
	static final String[] CODES = {"A", "T", "G", "C"};
	static final String[] COLORS = {"a", "d", "b", "c"};
	ControlText textControl;

	public ControlSequencerProgress(final IWidget parent, final int x, final int y) {
		super(parent, x, y, 100, 52);
		final Panel panel = new Panel(this, 0, 0, 100, 52, MinecraftGUI.PanelType.Gray);
		this.textControl = new ControlText(panel, new Area(4, 4, 92, 44), "", TextJustification.MiddleCenter);
	}

	@Override
	public void onUpdateClient() {
		super.onUpdateClient();
		IMachine machine = Machine.getMachine(Window.get(this).getInventory());
		MachineUtil machineUtil = machine.getMachineUtil();
		ItemStack stackTarget = machineUtil.getStack(Sequencer.SLOT_TARGET);
		if (stackTarget.isEmpty()) {
			this.textControl.setValue("");
		} else {
			Random rand = new Random(stackTarget.getDisplayName().length());
			StringBuilder text = new StringBuilder();
			for (int i = 0; i < 65; ++i) {
				int k = rand.nextInt(4);
				String code = CODES[k];
				if (rand.nextFloat() < this.progress) {
					String color = "�" + COLORS[k];
					text.append("�r").append(color).append("�l").append(code);
				} else {
					text.append("�r�7�k�l").append(code);
				}
			}
			this.textControl.setValue(text.toString());
		}
	}
}
