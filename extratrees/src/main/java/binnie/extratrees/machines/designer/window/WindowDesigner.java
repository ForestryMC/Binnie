package binnie.extratrees.machines.designer.window;

import javax.annotation.Nullable;

import binnie.core.api.gui.events.EventHandlerOrigin;
import binnie.extratrees.machines.designer.IDesignerType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.gui.controls.ControlText;
import binnie.core.gui.controls.ControlTextEdit;
import binnie.core.gui.controls.scroll.ControlScrollableContent;
import binnie.core.gui.events.EventTextEdit;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.geometry.TextJustification;
import binnie.core.gui.minecraft.MinecraftGUI;
import binnie.core.gui.minecraft.Window;
import binnie.core.gui.minecraft.control.ControlErrorState;
import binnie.core.gui.minecraft.control.ControlPlayerInventory;
import binnie.core.gui.minecraft.control.ControlSlot;
import binnie.core.gui.window.Panel;
import binnie.core.machines.Machine;
import binnie.core.util.I18N;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.machines.designer.Designer;

public class WindowDesigner extends Window {
	ControlTextEdit textEdit;
	ControlTileSelect tileSelect;

	public WindowDesigner(final EntityPlayer player, final IInventory inventory, final Side side) {
		super(320, 216, player, inventory, side);
		this.addEventHandler(EventTextEdit.class, EventHandlerOrigin.DIRECT_CHILD, this, event -> {
			WindowDesigner.this.tileSelect.refresh(event.getValue());
		});
	}

	@Nullable
	public static Window create(final EntityPlayer player, @Nullable final IInventory inventory, final Side side) {
		if (inventory == null) {
			return null;
		}
		return new WindowDesigner(player, inventory, side);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void initialiseClient() {
		this.setTitle(Machine.getMachine(this.getInventory()).getPackage().getDisplayName());
		new ControlText(this, new Area(190, 36, 114, 10), I18N.localise("binniecore.gui.design"), TextJustification.TOP_CENTER).setColor(4473924);
		new Panel(this, 188, 48, 118, 126, MinecraftGUI.PanelType.GRAY);
		this.textEdit = new ControlTextEdit(this, 188, 178, 118, 12);
		final ControlScrollableContent scroll = new ControlScrollableContent(this, 190, 50, 114, 122, 12);
		scroll.setScrollableContent(this.tileSelect = new ControlTileSelect(scroll, 0, 0));
		new ControlPlayerInventory(this).setPosition(new Point(14, 96));
		new ControlErrorState(this, 76, 65);
		if (this.getInventory() != null) {
			final ControlSlot slotWood1 = new ControlSlot.Builder(this, 22, 34).assign(Designer.DESIGN_SLOT_1);
			final ControlSlot slotWood2 = new ControlSlot.Builder(this, 62, 34).assign(Designer.DESIGN_SLOT_2);
			final ControlSlot slotBeeswax = new ControlSlot.Builder(this, 42, 64).assign(Designer.BEESWAX_SLOT);
			final ControlRecipeSlot slotFinished = new ControlRecipeSlot(this, 112, 34);
		}
	}

	@Override
	protected String getModId() {
		return ExtraTrees.instance.getModId();
	}

	@Override
	protected String getBackgroundTextureName() {
		return "Woodworker";
	}

	public IDesignerType getDesignerType() {
		return Machine.getInterface(ComponentDesignerRecipe.class, this.getInventory()).getDesignerType();
	}
}
