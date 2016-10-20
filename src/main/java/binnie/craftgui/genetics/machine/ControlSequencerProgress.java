package binnie.craftgui.genetics.machine;

import binnie.core.machines.Machine;
import binnie.craftgui.controls.ControlText;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.TextJustification;
import binnie.craftgui.minecraft.MinecraftGUI;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.minecraft.control.ControlProgressBase;
import binnie.craftgui.window.Panel;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class ControlSequencerProgress extends ControlProgressBase {
    ControlText textControl;

    public ControlSequencerProgress(final IWidget parent, final int x, final int y) {
        super(parent, x, y, 100.0f, 52.0f);
        final Panel panel = new Panel(this, 0.0f, 0.0f, 100.0f, 52.0f, MinecraftGUI.PanelType.Gray);
        this.textControl = new ControlText(panel, new IArea(4.0f, 4.0f, 92.0f, 44.0f), "", TextJustification.MiddleCenter);
    }

    @Override
    public void onUpdateClient() {
        super.onUpdateClient();
        final ItemStack stack = Machine.getMachine(Window.get(this).getInventory()).getMachineUtil().getStack(5);
        if (stack == null) {
            this.textControl.setValue("");
        } else {
            final Random rand = new Random(stack.getDisplayName().length());
            String text = "";
            final String[] codes = {"A", "T", "G", "C"};
            final String[] colors = {"a", "d", "b", "c"};
            for (int i = 0; i < 65; ++i) {
                final int k = rand.nextInt(4);
                final String code = codes[k];
                if (rand.nextFloat() < this.progress) {
                    final String col = "§" + colors[k];
                    text = text + "§r" + col + "§l" + code;
                } else {
                    text = text + "§r§7§k§l" + code;
                }
            }
            this.textControl.setValue(text);
        }
    }
}
