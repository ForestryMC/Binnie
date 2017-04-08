package binnie.extratrees.machines.craftgui;

import binnie.core.craftgui.Attribute;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.events.EventMouse;
import binnie.core.craftgui.geometry.Point;
import binnie.core.craftgui.minecraft.ContainerCraftGUI;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.minecraft.control.ControlProgressBase;
import binnie.core.craftgui.renderer.RenderUtil;
import binnie.core.craftgui.resource.Texture;
import binnie.core.craftgui.resource.minecraft.StandardTexture;
import binnie.extratrees.core.ExtraTreeTexture;
import binnie.extratrees.machines.fruitpress.FruitPressMachine;
import binnie.extratrees.machines.fruitpress.FruitPressRecipes;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ControlFruitPressProgress extends ControlProgressBase {
	private static final Texture PressTexture = new StandardTexture(6, 0, 24, 52, ExtraTreeTexture.Gui);
	private static final Texture PressSlot = new StandardTexture(9, 52, 34, 17, ExtraTreeTexture.Gui);

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		CraftGUI.render.texture(ControlFruitPressProgress.PressSlot, new Point(3, 52));
		ContainerCraftGUI container = Window.get(this).getContainer();
		IInventory inventory = Window.get(this).getInventory();
		Slot slotFromInventory = container.getSlotFromInventory(inventory, FruitPressMachine.SLOT_CURRENT);
		if (slotFromInventory == null) {
			return;
		}
		final ItemStack input = slotFromInventory.getStack();
		if (input.isEmpty() || FruitPressRecipes.getOutput(input) == null) {
			return;
		}
		FluidStack fluid = FruitPressRecipes.getOutput(input);
		RenderUtil.drawFluid(new Point(4, 52), fluid);
		RenderUtil.drawItem(new Point(4, 52), input);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderForeground(int guiWidth, int guiHeight) {
		GlStateManager.enableBlend();
		CraftGUI.render.texture(ControlFruitPressProgress.PressTexture, new Point(0, Math.round(16 * this.progress)));
	}

	protected ControlFruitPressProgress(final IWidget parent, final int x, final int y) {
		super(parent, x, y, 37, 69);
		this.addAttribute(Attribute.MouseOver);
		this.addSelfEventHandler(new EventMouse.Down.Handler() {
			@Override
			public void onEvent(final EventMouse.Down event) {
				if (event.getY() - event.getOrigin().getAbsolutePosition().y() > 52 + Math.round(16 * progress)) {
					final NBTTagCompound action = new NBTTagCompound();
					Window.get(ControlFruitPressProgress.this.getWidget()).sendClientAction("clear-fruit", action);
				} else {
					final NBTTagCompound action = new NBTTagCompound();
					Window.get(ControlFruitPressProgress.this.getWidget()).sendClientAction("fruitpress-click", action);
				}
			}
		});
	}

}
