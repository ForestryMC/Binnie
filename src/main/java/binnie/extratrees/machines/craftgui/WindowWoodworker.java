package binnie.extratrees.machines.craftgui;

import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import binnie.core.machines.Machine;
import binnie.craftgui.controls.ControlText;
import binnie.craftgui.controls.ControlTextEdit;
import binnie.craftgui.controls.scroll.ControlScrollableContent;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.core.geometry.TextJustification;
import binnie.craftgui.events.EventHandler;
import binnie.craftgui.events.EventTextEdit;
import binnie.craftgui.minecraft.MinecraftGUI;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.minecraft.control.ControlErrorState;
import binnie.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.craftgui.minecraft.control.ControlSlot;
import binnie.craftgui.window.Panel;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.machines.Designer;
import binnie.extratrees.machines.DesignerType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;

public class WindowWoodworker extends Window {
	ControlTextEdit textEdit;
	ControlTileSelect tileSelect;

	@Override
	public void initialiseClient() {
		this.setTitle(Machine.getMachine(this.getInventory()).getPackage().getDisplayName());
		new ControlText(this, new IArea(190, 36, 114, 10), BinnieCore.getBinnieProxy().localise("gui.design"), TextJustification.TopCenter).setColour(4473924);
		new Panel(this, 188, 48, 118, 126, MinecraftGUI.PanelType.Gray);
		this.textEdit = new ControlTextEdit(this, 188, 178, 118, 12);
		final ControlScrollableContent scroll = new ControlScrollableContent(this, 190, 50, 114, 122, 12);
		scroll.setScrollableContent(this.tileSelect = new ControlTileSelect(scroll, 0, 0));
		new ControlPlayerInventory(this).setPosition(new IPoint(14, 96));
		new ControlErrorState(this, 76, 65);
		if (this.getInventory() != null) {
			final ControlSlot slotWood1 = new ControlSlot(this, 22, 34);
			slotWood1.assign(Designer.design1Slot);
			final ControlSlot slotWood2 = new ControlSlot(this, 62, 34);
			slotWood2.assign(Designer.design2Slot);
			final ControlSlot slotBeeswax = new ControlSlot(this, 42, 64);
			slotBeeswax.assign(Designer.beeswaxSlot);
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

	public static Window create(final EntityPlayer player, final IInventory inventory, final Side side) {
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
