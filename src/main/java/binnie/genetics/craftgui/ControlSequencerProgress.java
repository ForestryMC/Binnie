package binnie.genetics.craftgui;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.TextJustification;
import binnie.core.craftgui.minecraft.MinecraftGUI;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.minecraft.control.ControlProgressBase;
import binnie.core.craftgui.window.Panel;
import binnie.core.machines.Machine;
import java.util.Random;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ControlSequencerProgress extends ControlProgressBase {
    protected ControlText textControl;

    public ControlSequencerProgress(IWidget parent, int x, int y) {
        super(parent, x, y, 100.0f, 52.0f);
        Panel panel = new Panel(this, 0.0f, 0.0f, 100.0f, 52.0f, MinecraftGUI.PanelType.Gray);
        textControl = new ControlText(panel, new IArea(4.0f, 4.0f, 92.0f, 44.0f), "", TextJustification.MIDDLE_CENTER);
    }

    @Override
    public void onUpdateClient() {
        super.onUpdateClient();
        ItemStack stack = Machine.getMachine(Window.get(this).getInventory())
                .getMachineUtil()
                .getStack(5);
        if (stack == null) {
            textControl.setValue("");
            return;
        }

        Random rand = new Random(stack.getDisplayName().length());
        StringBuilder text = new StringBuilder();
        String[] codes = {"A", "T", "G", "C"};
        EnumChatFormatting[] colors = {
            EnumChatFormatting.GREEN, EnumChatFormatting.LIGHT_PURPLE, EnumChatFormatting.AQUA, EnumChatFormatting.RED
        };

        for (int i = 0; i < 65; ++i) {
            int k = rand.nextInt(4);
            String code = codes[k];
            if (rand.nextFloat() < progress) {
                String col = colors[k].toString();
                text.append(EnumChatFormatting.RESET)
                        .append(col)
                        .append(EnumChatFormatting.BOLD)
                        .append(code);
            } else {
                text.append(EnumChatFormatting.RESET)
                        .append(EnumChatFormatting.GRAY)
                        .append(EnumChatFormatting.OBFUSCATED)
                        .append(EnumChatFormatting.BOLD)
                        .append(code);
            }
        }
        textControl.setValue(text.toString());
    }
}
