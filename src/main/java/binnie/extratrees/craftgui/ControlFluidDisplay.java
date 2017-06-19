package binnie.extratrees.craftgui;

import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.WidgetAttribute;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.minecraft.Window;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;

public class ControlFluidDisplay extends Control implements ITooltip {
	public boolean hastooltip;

	protected FluidStack itemStack;

	public ControlFluidDisplay(IWidget parent, float f, float y) {
		this(parent, f, y, 16.0f);
	}

	public ControlFluidDisplay(IWidget parent, float f, float y, FluidStack stack, boolean tooltip) {
		this(parent, f, y, 16.0f);
		setItemStack(stack);
		if (tooltip) {
			setTooltip();
		}
	}

	public ControlFluidDisplay(IWidget parent, float x, float y, float size) {
		super(parent, x, y, size, size);
		itemStack = null;
		hastooltip = false;
	}

	public void setTooltip() {
		hastooltip = true;
		addAttribute(WidgetAttribute.MOUSE_OVER);
	}

	@Override
	public void onRenderForeground() {
		if (itemStack == null) {
			return;
		}

		IPoint relativeToWindow = getAbsolutePosition().sub(getSuperParent().getPosition());
		if (relativeToWindow.x() > Window.get(this).getSize().x() + 100.0f
			|| relativeToWindow.y() > Window.get(this).getSize().y() + 100.0f
			|| itemStack == null) {
			return;
		}

		Fluid fluid = itemStack.getFluid();
		int hex = fluid.getColor(itemStack);
		int r = (hex & 0xFF0000) >> 16;
		int g = (hex & 0xFF00) >> 8;
		int b = hex & 0xFF;
		IIcon icon = itemStack.getFluid().getIcon(itemStack);
		GL11.glColor4f(r / 255.0f, g / 255.0f, b / 255.0f, 1.0f);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);

		if (getSize().x() != 16.0f) {
			GL11.glPushMatrix();
			float scale = getSize().x() / 16.0f;
			GL11.glScalef(scale, scale, 1.0f);
			CraftGUI.Render.iconBlock(IPoint.ZERO, itemStack.getFluid().getIcon(itemStack));
			GL11.glPopMatrix();
		} else {
			CraftGUI.Render.iconBlock(IPoint.ZERO, itemStack.getFluid().getIcon(itemStack));
		}
		GL11.glDisable(3042);
	}

	public void setItemStack(FluidStack itemStack) {
		this.itemStack = itemStack;
	}

	@Override
	public void getTooltip(Tooltip tooltip) {
		if (hastooltip && itemStack != null) {
			tooltip.add(itemStack.getLocalizedName());
		}
	}
}
