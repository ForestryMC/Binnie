package binnie.craftgui.extratrees.dictionary;

import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.events.EventMouse;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.minecraft.control.ControlProgressBase;
import binnie.craftgui.resource.Texture;
import binnie.craftgui.resource.minecraft.StandardTexture;
import binnie.extratrees.core.ExtraTreeTexture;
import binnie.extratrees.machines.Press;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import org.lwjgl.opengl.GL11;

public class ControlFruitPressProgress extends ControlProgressBase {
	static Texture PressTexture = new StandardTexture(6, 0, 24, 52, ExtraTreeTexture.Gui);
	static Texture PressSlot = new StandardTexture(9, 52, 34, 17, ExtraTreeTexture.Gui);

	@Override
	public void onRenderBackground() {
		CraftGUI.Render.texture(ControlFruitPressProgress.PressSlot, new IPoint(3.0f, 52.0f));
		Slot slotFromInventory = Window.get(this).getContainer().getSlotFromInventory(Window.get(this).getInventory(), Press.slotCurrent);
		if (slotFromInventory == null) {
			return;
		}
		final ItemStack input = slotFromInventory.getStack();
		if (input == null || Press.getOutput(input) == null) {
			return;
		}
		final Fluid fluid = Press.getOutput(input).getFluid();
		final int hex = fluid.getColor(Press.getOutput(input));
		final int r = (hex & 0xFF0000) >> 16;
		final int g = (hex & 0xFF00) >> 8;
		final int b = hex & 0xFF;
		//IIcon icon = fluid.getIcon();
		GL11.glColor4f(r / 255.0f, g / 255.0f, b / 255.0f, 1.0f);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		//TODO RENDERING
		//CraftGUI.Render.iconBlock(new IPoint(4.0f, 52.0f), fluid.getIcon());
		GL11.glDisable(3042);
		//icon = input.getIconIndex();
		//CraftGUI.Render.iconItem(new IPoint(4.0f, 52.0f), icon);
	}

	@Override
	public void onRenderForeground() {
		CraftGUI.Render.texture(ControlFruitPressProgress.PressTexture, new IPoint(0.0f, 16.0f * this.progress));
	}

	protected ControlFruitPressProgress(final IWidget parent, final float x, final float y) {
		super(parent, x, y, 37.0f, 69.0f);
		this.addAttribute(Attribute.MouseOver);
		this.addSelfEventHandler(new EventMouse.Down.Handler() {
			@Override
			public void onEvent(final EventMouse.Down event) {
				if (event.getButton() == 0) {
					final NBTTagCompound action = new NBTTagCompound();
					Window.get(ControlFruitPressProgress.this.getWidget()).sendClientAction("fruitpress-click", action);
				} else if (event.getButton() == 1) {
					final NBTTagCompound action = new NBTTagCompound();
					Window.get(ControlFruitPressProgress.this.getWidget()).sendClientAction("clear-fruit", action);
				}
			}
		});
	}

}
