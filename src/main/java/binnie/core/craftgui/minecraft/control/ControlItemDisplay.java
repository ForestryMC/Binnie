// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui.minecraft.control;

import binnie.core.BinnieCore;
import binnie.core.craftgui.Attribute;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.minecraft.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class ControlItemDisplay extends Control implements ITooltip
{
	private ItemStack itemStack;
	public boolean hastooltip;
	private boolean rotating;

	public void setTooltip() {
		hastooltip = true;
		addAttribute(Attribute.MouseOver);
	}

	public ControlItemDisplay(final IWidget parent, final float x, final float y) {
		this(parent, x, y, 16.0f);
	}

	public ControlItemDisplay(final IWidget parent, final float f, final float y, final ItemStack stack, final boolean tooltip) {
		this(parent, f, y, 16.0f);
		setItemStack(stack);
		if (tooltip) {
			setTooltip();
		}
	}

	public ControlItemDisplay(final IWidget parent, final float x, final float y, final float size) {
		super(parent, x, y, size, size);
		itemStack = null;
		hastooltip = false;
		rotating = false;
	}

	@Override
	public void onRenderBackground() {
		final IPoint relativeToWindow = getAbsolutePosition().sub(getSuperParent().getPosition());
		if (relativeToWindow.x() > Window.get(this).getSize().x() + 100.0f || relativeToWindow.y() > Window.get(this).getSize().y() + 100.0f) {
			return;
		}
		if (itemStack != null) {

			if (getSize().x() != 16.0f) {
				GL11.glPushMatrix();
				final float scale = getSize().x() / 16.0f;
				GL11.glScalef(scale, scale, 1.0f);
				BinnieCore.proxy.getMinecraftInstance();
				final float phase = Minecraft.getSystemTime() / 20.0f;
				CraftGUI.Render.item(IPoint.ZERO, itemStack, rotating);
				GL11.glPopMatrix();
			}
			else {
				CraftGUI.Render.item(IPoint.ZERO, itemStack, rotating);
			}
		}
	}

	public void setItemStack(final ItemStack itemStack) {
		this.itemStack = itemStack;
	}

	public ItemStack getItemStack() {
		return itemStack;
	}

	@Override
	public void getTooltip(final Tooltip tooltip) {
		if (hastooltip && itemStack != null) {
			tooltip.add(itemStack.getTooltip(((Window) getSuperParent()).getPlayer(), false));
		}
		super.getTooltip(tooltip);
	}

	public void setRotating() {
		rotating = true;
	}
}
