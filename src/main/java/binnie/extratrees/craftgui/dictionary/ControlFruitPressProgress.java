package binnie.extratrees.craftgui.dictionary;

import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.WidgetAttribute;
import binnie.core.craftgui.events.EventMouse;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.minecraft.control.ControlProgressBase;
import binnie.core.craftgui.resource.Texture;
import binnie.core.craftgui.resource.minecraft.StandardTexture;
import binnie.extratrees.core.ExtraTreeTexture;
import binnie.extratrees.machines.Press;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;
import org.lwjgl.opengl.GL11;

public class ControlFruitPressProgress extends ControlProgressBase {
	protected static Texture PressTexture = new StandardTexture(6, 0, 24, 52, ExtraTreeTexture.Gui);
	protected static Texture PressSlot = new StandardTexture(9, 52, 34, 17, ExtraTreeTexture.Gui);

	protected ControlFruitPressProgress(IWidget parent, float x, float y) {
		super(parent, x, y, 37.0f, 69.0f);
		addAttribute(WidgetAttribute.MOUSE_OVER);
		addSelfEventHandler(new EventMouse.Down.Handler() {
			@Override
			public void onEvent(EventMouse.Down event) {
				if (event.getButton() == 0) {
					NBTTagCompound action = new NBTTagCompound();
					Window.get(getWidget()).sendClientAction("fruitpress-click", action);
				} else if (event.getButton() == 1) {
					NBTTagCompound action = new NBTTagCompound();
					Window.get(getWidget()).sendClientAction("clear-fruit", action);
				}
			}
		});
	}

	@Override
	public void onRenderBackground() {
		CraftGUI.Render.texture(ControlFruitPressProgress.PressSlot, new IPoint(3.0f, 52.0f));
		ItemStack input = Window.get(this).getContainer().getSlotFromInventory(Window.get(this).getInventory(), Press.slotCurrent).getStack();
		if (input == null || Press.getOutput(input) == null) {
			return;
		}
		Fluid fluid = Press.getOutput(input).getFluid();
		int hex = fluid.getColor(Press.getOutput(input));
		int r = (hex & 0xFF0000) >> 16;
		int g = (hex & 0xFF00) >> 8;
		int b = hex & 0xFF;
		IIcon icon = fluid.getIcon();
		GL11.glColor4f(r / 255.0f, g / 255.0f, b / 255.0f, 1.0f);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		CraftGUI.Render.iconBlock(new IPoint(4.0f, 52.0f), fluid.getIcon());
		GL11.glDisable(3042);
		icon = input.getIconIndex();
		CraftGUI.Render.iconItem(new IPoint(4.0f, 52.0f), icon);
	}

	@Override
	public void onRenderForeground() {
		CraftGUI.Render.texture(ControlFruitPressProgress.PressTexture, new IPoint(0.0f, 16.0f * progress));
	}
}
