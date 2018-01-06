package binnie.genetics.machine.incubator;

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
import binnie.core.gui.resource.textures.Texture;
import binnie.core.gui.resource.textures.StandardTexture;
import binnie.core.util.I18N;
import binnie.genetics.Genetics;
import binnie.genetics.core.GeneticsTexture;
import binnie.core.gui.window.WindowMachine;

public class WindowIncubator extends WindowMachine {
	private static final Texture PROGRESS_BASE = new StandardTexture(0, 91, 38, 32, GeneticsTexture.GUI_PROCESS);
	private static final Texture PROGRESS = new StandardTexture(38, 91, 38, 32, GeneticsTexture.GUI_PROCESS);

	public WindowIncubator(final EntityPlayer player, final IInventory inventory, final Side side) {
		super(228, 196, player, inventory, side);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void initialiseClient() {
		super.initialiseClient();
		int x = 16;
		final int y = 32;
		new ControlLiquidTank(this, x, y, Incubator.TANK_INPUT);
		x += 26;
		new ControlSlotArray.Builder(this, x, y + 3, 1, 3).create(Incubator.SLOT_QUEUE);
		x += 20;
		new ControlIconDisplay(this, x, y + 3 + 10, GUIIcon.ARROW_RIGHT.getIcon().getResourceLocation());
		x += 18;
		new ControlMachineProgress(this, x, y + 6, PROGRESS_BASE, PROGRESS, Alignment.LEFT);
		new ControlSlot.Builder(this, x + 11, y + 3 + 10).assign(3);
		x += 40;
		new ControlIconDisplay(this, x, y + 3 + 10, GUIIcon.ARROW_RIGHT.getIcon().getResourceLocation());
		x += 18;
		new ControlSlotArray.Builder(this, x, y + 3, 1, 3).create(Incubator.SLOT_OUTPUT);
		x += 26;
		new ControlLiquidTank(this, x, y, Incubator.TANK_OUTPUT);
		x += 34;
		new ControlEnergyBar(this, x, y + 3, 16, 54, Alignment.BOTTOM);
		new ControlErrorState(this, 91, 82);
		new ControlPlayerInventory(this);
	}

	@Override
	public String getTitle() {
		return I18N.localise("genetics.machine.lab_machine.incubator");
	}

	@Override
	protected String getModId() {
		return Genetics.instance.getModId();
	}

	@Override
	protected String getBackgroundTextureName() {
		return "incubator";
	}
}
