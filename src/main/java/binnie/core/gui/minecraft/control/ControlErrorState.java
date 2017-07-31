package binnie.core.gui.minecraft.control;

import javax.annotation.Nullable;

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
import binnie.core.machines.power.ErrorState;

public class ControlErrorState extends Control implements ITooltip {
	@Nullable
	private ErrorState errorState;
	private int type;

	public ControlErrorState(final IWidget parent, final int x, final int y) {
		super(parent, x, y, 16, 16);
		this.type = 0;
		this.addAttribute(Attribute.MouseOver);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		Object texture = CraftGUITexture.StateWarning;
		if (this.errorState == null) {
			texture = CraftGUITexture.StateNone;
		} else if (this.type == 0) {
			texture = CraftGUITexture.StateError;
		}
		CraftGUI.render.texture(texture, Point.ZERO);
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
		ControlSlot.highlighting.get(EnumHighlighting.Error).clear();
		ControlSlot.highlighting.get(EnumHighlighting.Warning).clear();
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
						ControlSlot.highlighting.get(EnumHighlighting.Error).add(id);
					} else {
						ControlSlot.highlighting.get(EnumHighlighting.Warning).add(id);
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
	public void getTooltip(final Tooltip tooltipOrig) {
		final MinecraftTooltip tooltip = (MinecraftTooltip) tooltipOrig;
		if (this.errorState != null) {
			if (this.type == 0) {
				tooltip.setType(MinecraftTooltip.Type.Error);
			} else {
				tooltip.setType(MinecraftTooltip.Type.Warning);
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
