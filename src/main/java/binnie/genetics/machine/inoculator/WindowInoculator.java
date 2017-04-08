package binnie.genetics.machine.inoculator;

import binnie.core.AbstractMod;
import binnie.core.craftgui.geometry.CraftGUIUtil;
import binnie.core.craftgui.geometry.Position;
import binnie.core.craftgui.geometry.TextJustification;
import binnie.core.craftgui.minecraft.GUIIcon;
import binnie.core.craftgui.minecraft.control.ControlEnergyBar;
import binnie.core.craftgui.minecraft.control.ControlErrorState;
import binnie.core.craftgui.minecraft.control.ControlIconDisplay;
import binnie.core.craftgui.minecraft.control.ControlLiquidTank;
import binnie.core.craftgui.minecraft.control.ControlMachineProgress;
import binnie.core.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.core.craftgui.minecraft.control.ControlSlot;
import binnie.core.craftgui.minecraft.control.ControlSlotArray;
import binnie.core.craftgui.resource.Texture;
import binnie.core.craftgui.resource.minecraft.StandardTexture;
import binnie.genetics.Genetics;
import binnie.genetics.core.GeneticsTexture;
import binnie.genetics.machine.craftgui.WindowMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WindowInoculator extends WindowMachine {
	static Texture ProgressBase = new StandardTexture(0, 72, 142, 72, GeneticsTexture.GUIProcess2);
	static Texture Progress = new StandardTexture(0, 0, 142, 72, GeneticsTexture.GUIProcess2);

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
		new ControlMachineProgress(this, x, y + 24, WindowInoculator.ProgressBase, WindowInoculator.Progress, Position.LEFT);
		new ControlEnergyBar(this, 91, 118, 60, 16, Position.LEFT);
		new ControlErrorState(this, 161, 118);
		x += 142;
		CraftGUIUtil.verticalGrid(x, y, TextJustification.MIDDLE_LEFTt, 8,
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
		return Genetics.proxy.localise("machine.machine.inoculator");
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
