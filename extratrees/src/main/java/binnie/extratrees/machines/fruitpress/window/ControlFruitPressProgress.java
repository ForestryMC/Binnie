package binnie.extratrees.machines.fruitpress.window;

import binnie.core.api.gui.IWidget;
import binnie.core.gui.Attribute;
import binnie.core.gui.CraftGUI;
import binnie.core.gui.events.EventMouse;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.minecraft.ContainerCraftGUI;
import binnie.core.gui.minecraft.Window;
import binnie.core.gui.minecraft.control.ControlProgressBase;
import binnie.core.gui.renderer.RenderUtil;
import binnie.core.gui.resource.textures.StandardTexture;
import binnie.core.gui.resource.textures.Texture;
import binnie.extratrees.core.ExtraTreeTexture;
import binnie.extratrees.machines.fruitpress.FruitPressMachine;
import binnie.extratrees.machines.fruitpress.recipes.FruitPressRecipeManager;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ControlFruitPressProgress extends ControlProgressBase {
	private static final Texture PressTexture = new StandardTexture(6, 0, 24, 52, ExtraTreeTexture.GUI);
	private static final Texture PressSlot = new StandardTexture(9, 52, 34, 17, ExtraTreeTexture.GUI);

	protected ControlFruitPressProgress(final IWidget parent, final int x, final int y) {
		super(parent, x, y, 37, 69);
		this.addAttribute(Attribute.MOUSE_OVER);
		this.addSelfEventHandler(EventMouse.Down.class, event -> {
			if (event.getY() - event.getOrigin().getAbsolutePosition().yPos() > 52 + Math.round(16 * progress)) {
				final NBTTagCompound action = new NBTTagCompound();
				Window.get(ControlFruitPressProgress.this.getWidget()).sendClientAction("clear-fruit", action);
			} else {
				final NBTTagCompound action = new NBTTagCompound();
				Window.get(ControlFruitPressProgress.this.getWidget()).sendClientAction("fruitpress-click", action);
			}
		});
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		CraftGUI.RENDER.texture(ControlFruitPressProgress.PressSlot, new Point(3, 52));
		ContainerCraftGUI container = Window.get(this).getContainer();
		IInventory inventory = Window.get(this).getInventory();
		Slot slotFromInventory = container.getSlotFromInventory(inventory, FruitPressMachine.SLOT_CURRENT);
		if (slotFromInventory == null) {
			return;
		}
		final ItemStack input = slotFromInventory.getStack();
		if (input.isEmpty() || FruitPressRecipeManager.getOutput(input) == null) {
			return;
		}
		FluidStack fluid = FruitPressRecipeManager.getOutput(input);
		RenderUtil.drawFluid(new Point(4, 52), fluid);
		RenderUtil.drawItem(new Point(4, 52), input);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderForeground(int guiWidth, int guiHeight) {
		GlStateManager.enableBlend();
		CraftGUI.RENDER.texture(ControlFruitPressProgress.PressTexture, new Point(0, Math.round(16 * this.progress)));
	}
}
