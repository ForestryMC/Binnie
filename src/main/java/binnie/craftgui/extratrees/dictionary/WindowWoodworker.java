// 
// Decompiled by Procyon v0.5.30
// 

package binnie.craftgui.extratrees.dictionary;

import binnie.extratrees.machines.DesignerType;
import binnie.extratrees.ExtraTrees;
import binnie.core.AbstractMod;
import binnie.craftgui.events.EventHandler;
import binnie.craftgui.events.EventTextEdit;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.inventory.IInventory;
import net.minecraft.entity.player.EntityPlayer;
import binnie.extratrees.machines.Designer;
import binnie.craftgui.minecraft.control.ControlSlot;
import binnie.craftgui.minecraft.control.ControlErrorState;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.craftgui.controls.scroll.ControlScrollableContent;
import binnie.craftgui.minecraft.MinecraftGUI;
import binnie.craftgui.window.Panel;
import binnie.craftgui.controls.ControlText;
import binnie.craftgui.core.geometry.TextJustification;
import binnie.core.BinnieCore;
import binnie.craftgui.core.geometry.IArea;
import binnie.core.machines.Machine;
import binnie.craftgui.controls.ControlTextEdit;
import binnie.craftgui.minecraft.Window;

public class WindowWoodworker extends Window
{
	ControlTextEdit textEdit;
	ControlTileSelect tileSelect;

	@Override
	public void initialiseClient() {
		this.setTitle(Machine.getMachine(this.getInventory()).getPackage().getDisplayName());
		new ControlText(this, new IArea(190.0f, 36.0f, 114.0f, 10.0f), BinnieCore.proxy.localise("gui.design"), TextJustification.TopCenter).setColour(4473924);
		new Panel(this, 188.0f, 48.0f, 118.0f, 126.0f, MinecraftGUI.PanelType.Gray);
		this.textEdit = new ControlTextEdit(this, 188.0f, 178.0f, 118.0f, 12.0f);
		final ControlScrollableContent scroll = new ControlScrollableContent(this, 190.0f, 50.0f, 114.0f, 122.0f, 12.0f);
		scroll.setScrollableContent(this.tileSelect = new ControlTileSelect(scroll, 0.0f, 0.0f));
		new ControlPlayerInventory(this).setPosition(new IPoint(14.0f, 96.0f));
		new ControlErrorState(this, 76.0f, 65.0f);
		if (this.getInventory() != null) {
			final ControlSlot slotWood1 = new ControlSlot(this, 22.0f, 34.0f);
			slotWood1.assign(Designer.design1Slot);
			final ControlSlot slotWood2 = new ControlSlot(this, 62.0f, 34.0f);
			slotWood2.assign(Designer.design2Slot);
			final ControlSlot slotBeeswax = new ControlSlot(this, 42.0f, 64.0f);
			slotBeeswax.assign(Designer.beeswaxSlot);
			final ControlRecipeSlot slotFinished = new ControlRecipeSlot(this, 112, 34);
		}
	}

	public WindowWoodworker(final EntityPlayer player, final IInventory inventory, final Side side) {
		super(320.0f, 216.0f, player, inventory, side);
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
	protected String getName() {
		return "Woodworker";
	}

	public DesignerType getDesignerType() {
		return Machine.getInterface(Designer.ComponentWoodworkerRecipe.class, this.getInventory()).getDesignerType();
	}
}
