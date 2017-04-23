// 
// Decompiled by Procyon v0.5.30
// 

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

public class ControlSlotCharge extends Control
{
	private int slot;

	float getCharge() {
		final IChargedSlots slots = Machine.getInterface(IChargedSlots.class, Window.get(this).getInventory());
		return (slots == null) ? 0.0f : slots.getCharge(slot);
	}

	@Override
	public void onRenderBackground() {
		CraftGUI.Render.texture(CraftGUITexture.PanelBlack, getArea());
		CraftGUI.Render.texturePercentage(CraftGUI.Render.getTexture(CraftGUITexture.SlotCharge), getArea().inset(1), Position.Bottom, getCharge());
	}

	public ControlSlotCharge(final IWidget parent, final int x, final int y, final int slot) {
		super(parent, x, y, 4.0f, 18.0f);
		this.slot = slot;
	}

	@Override
	public void getHelpTooltip(final Tooltip tooltip) {
		tooltip.add("Charge Remaining: " + (int) (getCharge() * 100.0f) + "%");
	}
}
