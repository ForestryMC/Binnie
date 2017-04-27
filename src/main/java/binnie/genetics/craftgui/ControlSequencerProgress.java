// 
// Decompiled by Procyon v0.5.30
// 

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
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import java.util.Random;

public class ControlSequencerProgress extends ControlProgressBase
{
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
		}
		else {
			final Random rand = new Random(stack.getDisplayName().length());
			String text = "";
			final String[] codes = { "A", "T", "G", "C" };
			EnumChatFormatting[] colors = {
				EnumChatFormatting.GREEN,
				EnumChatFormatting.LIGHT_PURPLE,
				EnumChatFormatting.AQUA,
				EnumChatFormatting.RED
			};

			for (int i = 0; i < 65; ++i) {
				final int k = rand.nextInt(4);
				final String code = codes[k];
				if (rand.nextFloat() < this.progress) {
					String col = colors[k].toString();
					text = text + EnumChatFormatting.RESET + col + EnumChatFormatting.BOLD + code;
				}
				else {
					text = text + EnumChatFormatting.RESET + EnumChatFormatting.GRAY
						+ EnumChatFormatting.OBFUSCATED + EnumChatFormatting.BOLD + code;
				}
			}
			this.textControl.setValue(text);
		}
	}
}
