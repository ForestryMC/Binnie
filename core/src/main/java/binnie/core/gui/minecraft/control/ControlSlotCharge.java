package binnie.core.gui.minecraft.control;

import java.text.NumberFormat;

import binnie.core.ModId;
import binnie.core.util.I18N;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.gui.CraftGUI;
import binnie.core.api.gui.IWidget;
import binnie.core.gui.Tooltip;
import binnie.core.gui.controls.core.Control;
import binnie.core.api.gui.Alignment;
import binnie.core.gui.minecraft.Window;
import binnie.core.gui.resource.textures.CraftGUITexture;
import binnie.core.machines.Machine;
import binnie.core.machines.inventory.IChargedSlots;

public class ControlSlotCharge extends Control {
	private final int slot;

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
		CraftGUI.RENDER.texture(CraftGUITexture.PANEL_BLACK, this.getArea());
		CraftGUI.RENDER.texturePercentage(CraftGUI.RENDER.getTexture(CraftGUITexture.SLOT_CHARGE), this.getArea().inset(1), Alignment.BOTTOM, this.getCharge());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getHelpTooltip(final Tooltip tooltip, ITooltipFlag tooltipFlag) {
		NumberFormat percentFormat = I18N.getPercentFormat();
		String chargePercent = percentFormat.format(this.getCharge());
		tooltip.add(I18N.localise(ModId.CORE, "gui.charge.remaining.percent", chargePercent));
	}
}
