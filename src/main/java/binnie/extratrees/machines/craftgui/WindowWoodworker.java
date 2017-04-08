package binnie.extratrees.machines.craftgui;

import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.controls.ControlTextEdit;
import binnie.core.craftgui.controls.scroll.ControlScrollableContent;
import binnie.core.craftgui.events.EventHandler;
import binnie.core.craftgui.events.EventTextEdit;
import binnie.core.craftgui.geometry.Area;
import binnie.core.craftgui.geometry.Point;
import binnie.core.craftgui.geometry.TextJustification;
import binnie.core.craftgui.minecraft.MinecraftGUI;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.minecraft.control.ControlErrorState;
import binnie.core.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.core.craftgui.minecraft.control.ControlSlot;
import binnie.core.craftgui.window.Panel;
import binnie.core.machines.Machine;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.machines.Designer;
import binnie.extratrees.machines.DesignerType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class WindowWoodworker extends Window {
	ControlTextEdit textEdit;
	ControlTileSelect tileSelect;

	@Override
	@SideOnly(Side.CLIENT)
	public void initialiseClient() {
		this.setTitle(Machine.getMachine(this.getInventory()).getPackage().getDisplayName());
		new ControlText(this, new Area(190, 36, 114, 10), BinnieCore.getBinnieProxy().localise("gui.design"), TextJustification.TopCenter).setColour(4473924);
		new Panel(this, 188, 48, 118, 126, MinecraftGUI.PanelType.Gray);
		this.textEdit = new ControlTextEdit(this, 188, 178, 118, 12);
		final ControlScrollableContent scroll = new ControlScrollableContent(this, 190, 50, 114, 122, 12);
		scroll.setScrollableContent(this.tileSelect = new ControlTileSelect(scroll, 0, 0));
		new ControlPlayerInventory(this).setPosition(new Point(14, 96));
		new ControlErrorState(this, 76, 65);
		if (this.getInventory() != null) {
			final ControlSlot slotWood1 = new ControlSlot.Builder(this, 22, 34).assign(Designer.design1Slot);
			final ControlSlot slotWood2 = new ControlSlot.Builder(this, 62, 34).assign(Designer.design2Slot);
			final ControlSlot slotBeeswax = new ControlSlot.Builder(this, 42, 64).assign(Designer.beeswaxSlot);
			final ControlRecipeSlot slotFinished = new ControlRecipeSlot(this, 112, 34);
		}
	}

	public WindowWoodworker(final EntityPlayer player, final IInventory inventory, final Side side) {
		super(320, 216, player, inventory, side);
		this.addEventHandler(new EventTextEdit.Handler() {
			@Override
			public void onEvent(final EventTextEdit event) {
				WindowWoodworker.this.tileSelect.refresh(event.getValue());
			}
		}.setOrigin(EventHandler.Origin.DirectChild, this));
	}

	@Nullable
	public static Window create(final EntityPlayer player, @Nullable final IInventory inventory, final Side side) {
		if (inventory == null) {
			return null;
		}
		return new WindowWoodworker(player, inventory, side);
	}

	@Override
	protected AbstractMod getMod() {
		return ExtraTrees.instance;
	}

	@Override
	protected String getBackgroundTextureName() {
		return "Woodworker";
	}

	public DesignerType getDesignerType() {
		return Machine.getInterface(Designer.ComponentWoodworkerRecipe.class, this.getInventory()).getDesignerType();
	}
}
