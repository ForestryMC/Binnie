package binnie.craftgui.minecraft.control;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.ITooltip;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.Tooltip;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.core.renderer.RenderUtil;
import binnie.craftgui.minecraft.Window;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;

public class ControlItemDisplay extends Control implements ITooltip {
	private ItemStack itemStack;
	public boolean hastooltip;
	private boolean rotating;

	public void setTooltip() {
		this.hastooltip = true;
		this.addAttribute(Attribute.MouseOver);
	}

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
		this.itemStack = null;
		this.hastooltip = false;
		this.rotating = false;
	}

	@Override
	public void onRenderBackground(int guiWidth, int guiHeight) {
		if (this.itemStack == null) {
			return;
		}

		final IPoint relativeToWindow = this.getAbsolutePosition().sub(this.getSuperParent().getPosition());
		if (relativeToWindow.x() > Window.get(this).getSize().x() + 100 || relativeToWindow.y() > Window.get(this).getSize().y() + 100) {
			return;
		}

		if (this.getSize().x() != 16) {
			GlStateManager.pushMatrix();
			final float scale = this.getSize().x() / 16.0f;
			GlStateManager.scale(scale, scale, 1);
			RenderUtil.drawItem(IPoint.ZERO, this.itemStack, this.rotating);
			GlStateManager.popMatrix();
		} else {
			RenderUtil.drawItem(IPoint.ZERO, this.itemStack, this.rotating);
		}
		GlStateManager.enableAlpha();
	}

	public void setItemStack(final ItemStack itemStack) {
		this.itemStack = itemStack;
	}

	public ItemStack getItemStack() {
		return this.itemStack;
	}

	@Override
	public void getTooltip(final Tooltip tooltip) {
		if (this.hastooltip && this.itemStack != null) {
			tooltip.add(this.itemStack.getTooltip(((Window) this.getSuperParent()).getPlayer(), false));
		}
		super.getTooltip(tooltip);
	}

	public void setRotating() {
		this.rotating = true;
	}
}
