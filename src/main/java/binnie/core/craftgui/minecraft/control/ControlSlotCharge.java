package binnie.core.craftgui.minecraft.control;

import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.geometry.Position;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;
import binnie.core.machines.Machine;
import binnie.core.machines.inventory.IChargedSlots;

public class ControlSlotCharge extends Control {
    private int slot;

    public ControlSlotCharge(IWidget parent, int x, int y, int slot) {
        super(parent, x, y, 4.0f, 18.0f);
        this.slot = slot;
    }

    float getCharge() {
        IChargedSlots slots =
                Machine.getInterface(IChargedSlots.class, Window.get(this).getInventory());
        return (slots == null) ? 0.0f : slots.getCharge(slot);
    }

    @Override
    public void onRenderBackground() {
        CraftGUI.render.texture(CraftGUITexture.PanelBlack, getArea());
        CraftGUI.render.texturePercentage(
                CraftGUI.render.getTexture(CraftGUITexture.SlotCharge),
                getArea().inset(1),
                Position.BOTTOM,
                getCharge());
    }

    @Override
    public void getHelpTooltip(Tooltip tooltip) {
        tooltip.add("Charge Remaining: " + (int) (getCharge() * 100.0f) + "%");
    }
}
