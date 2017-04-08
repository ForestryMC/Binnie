package binnie.core.craftgui.minecraft.control;

import binnie.core.craftgui.Attribute;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.geometry.Point;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.renderer.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

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
		this.itemStack = ItemStack.EMPTY;
		this.hastooltip = false;
		this.rotating = false;
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

	public void setItemStack(ItemStack itemStack) {
		this.itemStack = itemStack;
	}

	public ItemStack getItemStack() {
		return this.itemStack;
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
