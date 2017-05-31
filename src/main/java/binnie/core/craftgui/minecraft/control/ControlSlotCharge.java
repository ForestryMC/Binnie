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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ControlSlotCharge extends Control {
	private int slot;

	public ControlSlotCharge(final IWidget parent, final int x, final int y, final int slot) {
		super(parent, x, y, 4, 18);
		this.slot = slot;
	}

	float getCharge() {
		final IChargedSlots slots = Machine.getInterface(IChargedSlots.class, Window.get(this).getInventory());
		return (slots == null) ? 0.0f : slots.getCharge(this.slot);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		CraftGUI.render.texture(CraftGUITexture.PanelBlack, this.getArea());
		CraftGUI.render.texturePercentage(CraftGUI.render.getTexture(CraftGUITexture.SlotCharge), this.getArea().inset(1), Position.BOTTOM, this.getCharge());
	}

	@Override
	public void getHelpTooltip(final Tooltip tooltip) {
		tooltip.add("Charge Remaining: " + (int) (this.getCharge() * 100.0f) + "%");
	}
}
