package binnie.genetics.machine.craftgui;

import binnie.core.AbstractMod;
import binnie.craftgui.core.geometry.Position;
import binnie.craftgui.minecraft.GUIIcon;
import binnie.craftgui.minecraft.control.ControlEnergyBar;
import binnie.craftgui.minecraft.control.ControlErrorState;
import binnie.craftgui.minecraft.control.ControlIconDisplay;
import binnie.craftgui.minecraft.control.ControlLiquidTank;
import binnie.craftgui.minecraft.control.ControlMachineProgress;
import binnie.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.craftgui.minecraft.control.ControlSlot;
import binnie.craftgui.minecraft.control.ControlSlotArray;
import binnie.craftgui.minecraft.control.ControlSlotCharge;
import binnie.craftgui.resource.Texture;
import binnie.craftgui.resource.minecraft.StandardTexture;
import binnie.extrabees.core.ExtraBeeTexture;
import binnie.genetics.Genetics;
import binnie.genetics.machine.genepool.Genepool;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;

public class WindowGenepool extends WindowMachine {
	static Texture ProgressBase = new StandardTexture(64, 0, 130, 21, ExtraBeeTexture.GUIProgress.getTexture());
	static Texture Progress = new StandardTexture(64, 21, 130, 21, ExtraBeeTexture.GUIProgress.getTexture());

	public WindowGenepool(final EntityPlayer player, final IInventory inventory, final Side side) {
		super(280, 198, player, inventory, side);
	}

	@Override
	public void initialiseClient() {
		super.initialiseClient();
		int x = 16;
		final int y = 32;
		new ControlLiquidTank(this, x, y).setTankID(1);
		x += 26;
		new ControlSlotArray.Builder(this, x, y + 3, 2, 3).create(Genepool.SLOT_RESERVE);
		x += 38;
		new ControlIconDisplay(this, x, y + 3 + 18 + 1, GUIIcon.ArrowRight.getIcon().getResourceLocation());
		x += 18;
		new ControlSlot.Builder(this, x, y + 3 + 18).assign(0);
		x += 18;
		new ControlMachineProgress(this, x, y + 19, WindowGenepool.ProgressBase, WindowGenepool.Progress, Position.Left);
		x += 130;
		new ControlLiquidTank(this, x, y).setTankID(0);
		new ControlEnergyBar(this, 21, 115, 16, 60, Position.Bottom);
		new ControlSlot.Builder(this, 121, 82).assign(7);
		new ControlSlotCharge(this, 143, 82, 7).setColour(15722671);
		new ControlErrorState(this, 181, 83);
		new ControlPlayerInventory(this);
	}

	@Override
	public String getTitle() {
		return Genetics.proxy.localise("machine.labMachine.genepool");
	}

	@Override
	protected AbstractMod getMod() {
		return Genetics.instance;
	}

	@Override
	protected String getBackgroundTextureName() {
		return "Genepool";
	}

}
