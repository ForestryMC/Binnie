package binnie.genetics.machine.isolator;

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
import binnie.core.gui.minecraft.control.ControlPlayerInventory;
import binnie.core.gui.minecraft.control.ControlProgress;
import binnie.core.gui.minecraft.control.ControlSlot;
import binnie.core.gui.minecraft.control.ControlSlotArray;
import binnie.core.gui.minecraft.control.ControlSlotCharge;
import binnie.core.gui.resource.textures.Texture;
import binnie.core.gui.resource.textures.StandardTexture;
import binnie.core.util.I18N;
import binnie.genetics.Genetics;
import binnie.genetics.core.GeneticsTexture;
import binnie.core.gui.window.WindowMachine;

public class WindowIsolator extends WindowMachine {
	private static final Texture PROGRESS_BASE = new StandardTexture(0, 218, 142, 17, GeneticsTexture.GUI_PROCESS_3);
	private static final Texture PROGRESS = new StandardTexture(0, 201, 142, 17, GeneticsTexture.GUI_PROCESS_3);

	public WindowIsolator(final EntityPlayer player, final IInventory inventory, final Side side) {
		super(330, 208, player, inventory, side);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void initialiseClient() {
		super.initialiseClient();
		int x = 16;
		final int y = 32;
		new ControlLiquidTank(this, x, y, Isolator.TANK_ETHANOL);
		x += 26;
		new ControlSlotArray.Builder(this, x, y + 3, 1, 3).create(Isolator.SLOT_RESERVE);
		x += 20;
		new ControlIconDisplay(this, x, y + 3 + 1, GUIIcon.ARROW_RIGHT.getIcon().getResourceLocation());
		x += 18;
		new ControlSlot.Builder(this, x, y + 3).assign(5);
		new ControlSlot.Builder(this, x, y + 36 + 3).assign(0);
		new ControlSlotCharge(this, x + 18 + 2, y + 36 + 3, 0).setColor(15722671);
		x += 18;
		new ControlProgress(this, x, y + 3, PROGRESS_BASE, PROGRESS, Alignment.LEFT);
		x += 142;
		new ControlSlot.Builder(this, x, y + 3).assign(6);
		new ControlSlot.Builder(this, x, y + 3 + 36).assign(1);
		new ControlIconDisplay(this, x + 1, y + 3 + 19, GUIIcon.ARROW_UP.getIcon().getResourceLocation());
		x += 20;
		new ControlIconDisplay(this, x, y + 3 + 1, GUIIcon.ARROW_RIGHT.getIcon().getResourceLocation());
		x += 18;
		new ControlSlotArray.Builder(this, x, y + 3, 2, 3).create(Isolator.SLOT_FINISHED);
		new ControlEnergyBar(this, 260, 130, 16, 60, Alignment.BOTTOM);
		new ControlErrorState(this, 153, 81);
		new ControlPlayerInventory(this);
	}

	@Override
	public String getTitle() {
		return I18N.localise("genetics.machine.isolator");
	}

	@Override
	protected String getModId() {
		return Genetics.instance.getModId();
	}

	@Override
	protected String getBackgroundTextureName() {
		return "isolator";
	}
}
