package binnie.genetics.machine.inoculator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.AbstractMod;
import binnie.core.gui.geometry.CraftGUIUtil;
import binnie.core.api.gui.Alignment;
import binnie.core.gui.geometry.TextJustification;
import binnie.core.gui.minecraft.GUIIcon;
import binnie.core.gui.minecraft.control.ControlEnergyBar;
import binnie.core.gui.minecraft.control.ControlErrorState;
import binnie.core.gui.minecraft.control.ControlIconDisplay;
import binnie.core.gui.minecraft.control.ControlLiquidTank;
import binnie.core.gui.minecraft.control.ControlMachineProgress;
import binnie.core.gui.minecraft.control.ControlPlayerInventory;
import binnie.core.gui.minecraft.control.ControlSlot;
import binnie.core.gui.minecraft.control.ControlSlotArray;
import binnie.core.gui.resource.Texture;
import binnie.core.gui.resource.minecraft.StandardTexture;
import binnie.core.util.I18N;
import binnie.genetics.Genetics;
import binnie.genetics.core.GeneticsTexture;
import binnie.core.gui.window.WindowMachine;

public class WindowInoculator extends WindowMachine {
	static Texture ProgressBase = new StandardTexture(0, 72, 142, 72, GeneticsTexture.GUI_PROCESS_2);
	static Texture Progress = new StandardTexture(0, 0, 142, 72, GeneticsTexture.GUI_PROCESS_2);

	public WindowInoculator(final EntityPlayer player, final IInventory inventory, final Side side) {
		super(266, 240, player, inventory, side);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void initialiseClient() {
		super.initialiseClient();
		int x = 16;
		final int y = 32;
		new ControlLiquidTank(this, x, y + 18 + 16).setTankID(0);
		CraftGUIUtil.horizontalGrid(x, y,
			new ControlSlotArray.Builder(this, 0, 0, 2, 1).create(Inoculator.SLOT_SERUM_RESERVE),
			new ControlIconDisplay(this, 0, 0, GUIIcon.ARROW_RIGHT.getIcon().getResourceLocation()),
			new ControlSlot.Builder(this, 0, 0).assign(0),
			new ControlIconDisplay(this, 0, 0, GUIIcon.ARROW_RIGHT.getIcon().getResourceLocation()),
			new ControlSlotArray.Builder(this, 0, 0, 2, 1).create(Inoculator.SLOT_SERUM_EXPENDED)
		);
		x += 18;
		new ControlMachineProgress(this, x, y + 24, WindowInoculator.ProgressBase, WindowInoculator.Progress, Alignment.LEFT);
		new ControlEnergyBar(this, 91, 118, 60, 16, Alignment.LEFT);
		new ControlErrorState(this, 161, 118);
		x += 142;
		CraftGUIUtil.verticalGrid(x, y, TextJustification.MIDDLE_LEFT, 8,
			new ControlSlotArray.Builder(this, x, y, 4, 1).create(Inoculator.SLOT_RESERVE),
			new ControlSlot.Builder(this, x, y + 18 + 8).assign(9),
			new ControlSlotArray.Builder(this, x, y + 18 + 8 + 18 + 8, 4, 1).create(Inoculator.SLOT_FINISHED)
		);
		new ControlIconDisplay(this, x + 18, y + 18 + 2, GUIIcon.ARROW_UP_LEFT.getIcon().getResourceLocation());
		new ControlIconDisplay(this, x + 18, y + 18 + 18, GUIIcon.ARROW_LEFT_DOWN.getIcon().getResourceLocation());
		new ControlPlayerInventory(this);
	}

	@Override
	public String getTitle() {
		return I18N.localise("genetics.machine.machine.inoculator");
	}

	@Override
	protected AbstractMod getMod() {
		return Genetics.instance;
	}

	@Override
	protected String getBackgroundTextureName() {
		return "inoculator";
	}
}
