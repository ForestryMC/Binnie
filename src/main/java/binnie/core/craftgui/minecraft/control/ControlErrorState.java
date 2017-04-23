// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui.minecraft.control;

import binnie.core.craftgui.Attribute;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.minecraft.CustomSlot;
import binnie.core.craftgui.minecraft.MinecraftTooltip;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;
import binnie.core.machines.power.ErrorState;
import net.minecraft.entity.player.InventoryPlayer;

public class ControlErrorState extends Control implements ITooltip
{
	private ErrorState errorState;
	private int type;

	@Override
	public void onRenderBackground() {
		Object texture = CraftGUITexture.StateWarning;
		if (errorState == null) {
			texture = CraftGUITexture.StateNone;
		}
		else if (type == 0) {
			texture = CraftGUITexture.StateError;
		}
		CraftGUI.Render.texture(texture, IPoint.ZERO);
		super.onRenderBackground();
	}

	public ErrorState getError() {
		return Window.get(this).getContainer().getErrorState();
	}

	@Override
	public final void onUpdateClient() {
		errorState = getError();
		type = Window.get(this).getContainer().getErrorType();
		ControlSlot.highlighting.get(EnumHighlighting.Error).clear();
		ControlSlot.highlighting.get(EnumHighlighting.Warning).clear();
		ControlLiquidTank.tankError.clear();
		ControlEnergyBar.isError = false;
		if (!isMouseOver() || errorState == null) {
			return;
		}
		ControlEnergyBar.isError = errorState.isPowerError();
		if (errorState.isItemError()) {
			for (final int slot : errorState.getData()) {
				int id = -1;
				for (final CustomSlot cslot : Window.get(this).getContainer().getCustomSlots()) {
					if (!(cslot.inventory instanceof InventoryPlayer) && cslot.getSlotIndex() == slot) {
						id = cslot.slotNumber;
					}
				}
				if (id >= 0) {
					if (type == 0) {
						ControlSlot.highlighting.get(EnumHighlighting.Error).add(id);
					}
					else {
						ControlSlot.highlighting.get(EnumHighlighting.Warning).add(id);
					}
				}
			}
		}
		if (errorState.isTankError()) {
			for (final int slot : errorState.getData()) {
				ControlLiquidTank.tankError.add(slot);
			}
		}
	}

	public ControlErrorState(final IWidget parent, final float x, final float y) {
		super(parent, x, y, 16.0f, 16.0f);
		type = 0;
		addAttribute(Attribute.MouseOver);
	}

	@Override
	public void getTooltip(final Tooltip tooltipOrig) {
		final MinecraftTooltip tooltip = (MinecraftTooltip) tooltipOrig;
		if (errorState != null) {
			if (type == 0) {
				tooltip.setType(MinecraftTooltip.Type.Error);
			}
			else {
				tooltip.setType(MinecraftTooltip.Type.Warning);
			}
			tooltip.add(errorState.toString());
			if (errorState.getTooltip().length() > 0) {
				tooltip.add(errorState.getTooltip());
			}
		}
	}

	public ErrorState getErrorState() {
		return errorState;
	}
}
