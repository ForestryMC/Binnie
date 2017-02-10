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
import binnie.craftgui.resource.Texture;
import binnie.craftgui.resource.minecraft.StandardTexture;
import binnie.genetics.Genetics;
import binnie.genetics.core.GeneticsTexture;
import binnie.genetics.machine.incubator.Incubator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WindowIncubator extends WindowMachine {
	static Texture ProgressBase = new StandardTexture(0, 91, 38, 32, GeneticsTexture.GUIProcess);
	static Texture Progress = new StandardTexture(38, 91, 38, 32, GeneticsTexture.GUIProcess);

	public WindowIncubator(final EntityPlayer player, final IInventory inventory, final Side side) {
		super(228, 196, player, inventory, side);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void initialiseClient() {
		super.initialiseClient();
		int x = 16;
		final int y = 32;
		new ControlLiquidTank(this, x, y).setTankID(0);
		x += 26;
		new ControlSlotArray.Builder(this, x, y + 3, 1, 3).create(Incubator.SLOT_QUEUE);
		x += 20;
		new ControlIconDisplay(this, x, y + 3 + 10, GUIIcon.ArrowRight.getIcon().getResourceLocation());
		x += 18;
		new ControlMachineProgress(this, x, y + 6, WindowIncubator.ProgressBase, WindowIncubator.Progress, Position.Left);
		new ControlSlot.Builder(this, x + 11, y + 3 + 10).assign(3);
		x += 40;
		new ControlIconDisplay(this, x, y + 3 + 10, GUIIcon.ArrowRight.getIcon().getResourceLocation());
		x += 18;
		new ControlSlotArray.Builder(this, x, y + 3, 1, 3).create(Incubator.SLOT_OUTPUT);
		x += 26;
		new ControlLiquidTank(this, x, y).setTankID(1);
		x += 34;
		new ControlEnergyBar(this, x, y + 3, 16, 54, Position.Bottom);
		new ControlErrorState(this, 91, 82);
		new ControlPlayerInventory(this);
	}

	@Override
	public String getTitle() {
		return Genetics.proxy.localise("machine.labMachine.incubator");
	}

	@Override
	protected AbstractMod getMod() {
		return Genetics.instance;
	}

	@Override
	protected String getBackgroundTextureName() {
		return "incubator";
	}

}
