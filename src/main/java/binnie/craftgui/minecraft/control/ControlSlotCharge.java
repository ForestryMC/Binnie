package binnie.craftgui.minecraft.control;

import binnie.core.machines.Machine;
import binnie.core.machines.inventory.IChargedSlots;
import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.Tooltip;
import binnie.craftgui.core.geometry.Position;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.resource.minecraft.CraftGUITexture;

public class ControlSlotCharge extends Control {
    private int slot;

    float getCharge() {
        final IChargedSlots slots = Machine.getInterface(IChargedSlots.class, Window.get(this).getInventory());
        return (slots == null) ? 0.0f : slots.getCharge(this.slot);
    }

    @Override
    public void onRenderBackground() {
        CraftGUI.Render.texture(CraftGUITexture.PanelBlack, this.getArea());
        CraftGUI.Render.texturePercentage(CraftGUI.Render.getTexture(CraftGUITexture.SlotCharge), this.getArea().inset(1), Position.Bottom, this.getCharge());
    }

    public ControlSlotCharge(final IWidget parent, final int x, final int y, final int slot) {
        super(parent, x, y, 4.0f, 18.0f);
        this.slot = slot;
    }

    @Override
    public void getHelpTooltip(final Tooltip tooltip) {
        tooltip.add("Charge Remaining: " + (int) (this.getCharge() * 100.0f) + "%");
    }
}
