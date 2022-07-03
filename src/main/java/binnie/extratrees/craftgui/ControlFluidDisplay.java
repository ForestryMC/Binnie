package binnie.extratrees.craftgui;

import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.WidgetAttribute;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.minecraft.Window;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;

public class ControlFluidDisplay extends Control implements ITooltip {
    public boolean hasTooltip;

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
        hasTooltip = false;
    }

    public void setTooltip() {
        hasTooltip = true;
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
        GL11.glColor4f(r / 255.0f, g / 255.0f, b / 255.0f, 1.0f);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        if (getSize().x() != 16.0f) {
            GL11.glPushMatrix();
            float scale = getSize().x() / 16.0f;
            GL11.glScalef(scale, scale, 1.0f);
            CraftGUI.render.iconBlock(IPoint.ZERO, itemStack.getFluid().getIcon(itemStack));
            GL11.glPopMatrix();
        } else {
            CraftGUI.render.iconBlock(IPoint.ZERO, itemStack.getFluid().getIcon(itemStack));
        }
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glColor4f(1, 1, 1, 1.0f);
    }

    public void setItemStack(FluidStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    public void getTooltip(Tooltip tooltip) {
        if (hasTooltip && itemStack != null) {
            tooltip.add(itemStack.getLocalizedName());
        }
    }
}
