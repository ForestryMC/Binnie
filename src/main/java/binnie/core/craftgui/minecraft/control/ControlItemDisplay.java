package binnie.core.craftgui.minecraft.control;

import binnie.core.BinnieCore;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.WidgetAttribute;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.minecraft.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class ControlItemDisplay extends Control implements ITooltip {
    public boolean hastooltip;
    private ItemStack itemStack;
    private boolean rotating;

    public ControlItemDisplay(IWidget parent, float x, float y) {
        this(parent, x, y, 16.0f);
    }

    public ControlItemDisplay(IWidget parent, float f, float y, ItemStack stack, boolean tooltip) {
        this(parent, f, y, 16.0f);
        setItemStack(stack);
        if (tooltip) {
            setTooltip();
        }
    }

    public ControlItemDisplay(IWidget parent, float x, float y, float size) {
        super(parent, x, y, size, size);
        itemStack = null;
        hastooltip = false;
        rotating = false;
    }

    public void setTooltip() {
        hastooltip = true;
        addAttribute(WidgetAttribute.MOUSE_OVER);
    }

    @Override
    public void onRenderBackground() {
        IPoint relativeToWindow = getAbsolutePosition().sub(getSuperParent().getPosition());
        if (relativeToWindow.x() > Window.get(this).getSize().x() + 100.0f
                || relativeToWindow.y() > Window.get(this).getSize().y() + 100.0f) {
            return;
        }

        if (itemStack == null) {
            return;
        }

        if (getSize().x() != 16.0f) {
            GL11.glPushMatrix();
            float scale = getSize().x() / 16.0f;
            GL11.glScalef(scale, scale, 1.0f);
            BinnieCore.proxy.getMinecraftInstance();
            float phase = Minecraft.getSystemTime() / 20.0f;
            CraftGUI.render.item(IPoint.ZERO, itemStack, rotating);
            GL11.glPopMatrix();
        } else {
            CraftGUI.render.item(IPoint.ZERO, itemStack, rotating);
        }
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    public void getTooltip(Tooltip tooltip) {
        if (hastooltip && itemStack != null) {
            tooltip.add(itemStack.getTooltip(((Window) getSuperParent()).getPlayer(), false));
        }
        super.getTooltip(tooltip);
    }

    public void setRotating() {
        rotating = true;
    }
}
