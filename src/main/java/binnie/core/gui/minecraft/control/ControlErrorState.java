package binnie.core.gui.minecraft.control;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.InventoryPlayer;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.gui.Attribute;
import binnie.core.gui.CraftGUI;
import binnie.core.gui.ITooltip;
import binnie.core.gui.IWidget;
import binnie.core.gui.Tooltip;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.minecraft.CustomSlot;
import binnie.core.gui.minecraft.MinecraftTooltip;
import binnie.core.gui.minecraft.Window;
import binnie.core.gui.resource.minecraft.CraftGUITexture;
import binnie.core.machines.errors.ErrorState;

public class ControlErrorState extends Control implements ITooltip {
	@Nullable
	private ErrorState errorState;
	private int type;

	public ControlErrorState(final IWidget parent, final int x, final int y) {
		super(parent, x, y, 16, 16);
		this.type = 0;
		this.addAttribute(Attribute.MOUSE_OVER);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		Object texture = CraftGUITexture.STATE_WARNING;
		if (this.errorState == null) {
			texture = CraftGUITexture.STATE_NONE;
		} else if (this.type == 0) {
			texture = CraftGUITexture.STATE_ERROR;
		}
		CraftGUI.RENDER.texture(texture, Point.ZERO);
		super.onRenderBackground(guiWidth, guiHeight);
	}

	@Nullable
	public ErrorState getError() {
		return Window.get(this).getContainer().getErrorState();
	}

	@Override
	public final void onUpdateClient() {
		this.errorState = this.getError();
		this.type = Window.get(this).getContainer().getErrorType();
		ControlSlot.highlighting.get(EnumHighlighting.ERROR).clear();
		ControlSlot.highlighting.get(EnumHighlighting.WARNING).clear();
		ControlLiquidTank.tankError.clear();
		ControlEnergyBar.isError = false;
		if (!this.isMouseOver() || this.errorState == null) {
			return;
		}
		ControlEnergyBar.isError = this.errorState.isPowerError();
		if (this.errorState.isItemError()) {
			for (final int slot : this.errorState.getData()) {
				int id = -1;
				for (final CustomSlot cslot : Window.get(this).getContainer().getCustomSlots()) {
					if (!(cslot.inventory instanceof InventoryPlayer) && cslot.getSlotIndex() == slot) {
						id = cslot.slotNumber;
					}
				}
				if (id >= 0) {
					if (this.type == 0) {
						ControlSlot.highlighting.get(EnumHighlighting.ERROR).add(id);
					} else {
						ControlSlot.highlighting.get(EnumHighlighting.WARNING).add(id);
					}
				}
			}
		}
		if (this.errorState.isTankError()) {
			for (final int slot : this.errorState.getData()) {
				ControlLiquidTank.tankError.add(slot);
			}
		}
	}

	@Override
	public void getTooltip(final Tooltip tooltipOrig, ITooltipFlag tooltipFlag) {
		final MinecraftTooltip tooltip = (MinecraftTooltip) tooltipOrig;
		if (this.errorState != null) {
			if (this.type == 0) {
				tooltip.setType(MinecraftTooltip.Type.ERROR);
			} else {
				tooltip.setType(MinecraftTooltip.Type.WARNING);
			}
			tooltip.add(this.errorState.toString());
			if (this.errorState.getTooltip().length() > 0) {
				tooltip.add(this.errorState.getTooltip());
			}
		}
	}

	@Nullable
	public ErrorState getErrorState() {
		return this.errorState;
	}
}
