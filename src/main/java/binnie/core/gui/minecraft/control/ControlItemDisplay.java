package binnie.core.gui.minecraft.control;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.gui.Attribute;
import binnie.core.gui.ITooltip;
import binnie.core.gui.IWidget;
import binnie.core.gui.Tooltip;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.minecraft.Window;
import binnie.core.gui.renderer.RenderUtil;

public class ControlItemDisplay extends Control implements ITooltip {
	public boolean hastooltip;
	private ItemStack itemStack;
	private boolean rotating;

	public ControlItemDisplay(final IWidget parent, final int x, final int y) {
		this(parent, x, y, 16);
	}

	public ControlItemDisplay(final IWidget parent, final int f, final int y, final ItemStack stack, final boolean tooltip) {
		this(parent, f, y, 16);
		this.setItemStack(stack);
		if (tooltip) {
			this.setTooltip();
		}
	}

	public ControlItemDisplay(final IWidget parent, final int x, final int y, final int size) {
		super(parent, x, y, size, size);
		this.itemStack = ItemStack.EMPTY;
		this.hastooltip = false;
		this.rotating = false;
	}

	public void setTooltip() {
		this.hastooltip = true;
		this.addAttribute(Attribute.MouseOver);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		if (this.itemStack.isEmpty()) {
			return;
		}

		final Point relativeToWindow = this.getAbsolutePosition().sub(this.getTopParent().getPosition());
		if (relativeToWindow.x() > Window.get(this).getSize().x() + 100 || relativeToWindow.y() > Window.get(this).getSize().y() + 100) {
			return;
		}

		GlStateManager.enableDepth();
		if (this.getSize().x() != 16) {
			GlStateManager.pushMatrix();
			final float scale = this.getSize().x() / 16.0f;
			GlStateManager.scale(scale, scale, 1);
			RenderUtil.drawItem(Point.ZERO, this.itemStack, this.rotating);
			GlStateManager.popMatrix();
		} else {
			RenderUtil.drawItem(Point.ZERO, this.itemStack, this.rotating);
		}
		GlStateManager.enableAlpha();
	}

	public ItemStack getItemStack() {
		return this.itemStack;
	}

	public void setItemStack(ItemStack itemStack) {
		this.itemStack = itemStack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getTooltip(final Tooltip tooltip) {
		if (this.hastooltip && !this.itemStack.isEmpty()) {
			final boolean advancedItemTooltips = Minecraft.getMinecraft().gameSettings.advancedItemTooltips;
			List<String> itemStackTooltip = this.itemStack.getTooltip(((Window) this.getTopParent()).getPlayer(), advancedItemTooltips);
			tooltip.add(itemStackTooltip);
			tooltip.setItemStack(this.itemStack);
		}
		super.getTooltip(tooltip);
	}

	public void setRotating() {
		this.rotating = true;
	}
}
