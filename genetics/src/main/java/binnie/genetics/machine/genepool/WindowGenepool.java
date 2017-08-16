package binnie.genetics.machine.genepool;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.api.gui.Alignment;
import binnie.core.gui.minecraft.GUIIcon;
import binnie.core.gui.minecraft.control.ControlEnergyBar;
import binnie.core.gui.minecraft.control.ControlErrorState;
import binnie.core.gui.minecraft.control.ControlIconDisplay;
import binnie.core.gui.minecraft.control.ControlLiquidTank;
import binnie.core.gui.minecraft.control.ControlMachineProgress;
import binnie.core.gui.minecraft.control.ControlPlayerInventory;
import binnie.core.gui.minecraft.control.ControlSlot;
import binnie.core.gui.minecraft.control.ControlSlotArray;
import binnie.core.gui.minecraft.control.ControlSlotCharge;
import binnie.core.gui.resource.textures.Texture;
import binnie.core.gui.resource.textures.StandardTexture;
import binnie.core.util.I18N;
import binnie.genetics.Genetics;
import binnie.genetics.core.GeneticsTexture;
import binnie.core.gui.window.WindowMachine;

public class WindowGenepool extends WindowMachine {
	static Texture ProgressBase = new StandardTexture(64, 0, 130, 21, GeneticsTexture.GUI_PROCESS_3);
	static Texture Progress = new StandardTexture(64, 21, 130, 21, GeneticsTexture.GUI_PROCESS_3);

	public WindowGenepool(final EntityPlayer player, final IInventory inventory, final Side side) {
		super(280, 198, player, inventory, side);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void initialiseClient() {
		super.initialiseClient();
		int x = 16;
		final int y = 32;
		new ControlLiquidTank(this, x, y).setTankID(1);
		x += 26;
		new ControlSlotArray.Builder(this, x, y + 3, 2, 3).create(Genepool.SLOT_RESERVE);
		x += 38;
		new ControlIconDisplay(this, x, y + 3 + 18 + 1, GUIIcon.ARROW_RIGHT.getIcon().getResourceLocation());
		x += 18;
		new ControlSlot.Builder(this, x, y + 3 + 18).assign(0);
		x += 18;
		new ControlMachineProgress(this, x, y + 19, WindowGenepool.ProgressBase, WindowGenepool.Progress, Alignment.LEFT);
		x += 130;
		new ControlLiquidTank(this, x, y).setTankID(0);
		new ControlEnergyBar(this, 21, 115, 16, 60, Alignment.BOTTOM);
		new ControlSlot.Builder(this, 121, 82).assign(7);
		new ControlSlotCharge(this, 143, 82, 7).setColor(15722671);
		new ControlErrorState(this, 181, 83);
		new ControlPlayerInventory(this);
	}

	@Override
	public String getTitle() {
		return I18N.localise("genetics.machine.lab_machine.genepool");
	}

	@Override
	protected String getModId() {
		return Genetics.instance.getModId();
	}

	@Override
	protected String getBackgroundTextureName() {
		return "genepool";
	}
}
