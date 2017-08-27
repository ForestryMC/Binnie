package binnie.genetics.machine.acclimatiser;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.api.gui.Alignment;
import binnie.core.gui.minecraft.control.ControlEnergyBar;
import binnie.core.gui.minecraft.control.ControlErrorState;
import binnie.core.gui.minecraft.control.ControlPlayerInventory;
import binnie.core.gui.minecraft.control.ControlProgress;
import binnie.core.gui.minecraft.control.ControlSlot;
import binnie.core.gui.minecraft.control.ControlSlotArray;
import binnie.core.gui.resource.textures.Texture;
import binnie.core.gui.resource.textures.StandardTexture;
import binnie.core.util.I18N;
import binnie.genetics.Genetics;
import binnie.genetics.core.GeneticsTexture;
import binnie.core.gui.window.WindowMachine;

public class WindowAcclimatiser extends WindowMachine {
	private static final Texture PROGRESS_BASE = new StandardTexture(0, 64, 64, 64, GeneticsTexture.GUI_PROCESS_3);
	private static final Texture PROGRESS = new StandardTexture(0, 64, 64, 64, GeneticsTexture.GUI_PROCESS_3);

	public WindowAcclimatiser(final EntityPlayer player, final IInventory inventory, final Side side) {
		super(280, 198, player, inventory, side);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void initialiseClient() {
		super.initialiseClient();
		new ControlProgress(this, 65, 28 , PROGRESS_BASE, PROGRESS, Alignment.RIGHT);
		int x = 16;
		final int y = 32;
		new ControlSlotArray.Builder(this, x, y, 2, 2).create(Acclimatiser.SLOT_RESERVE);
		x += 54;
		new ControlSlot.Builder(this, x + 18, y).assign(4);
		new ControlSlotArray.Builder(this, x, y + 18 + 18, 3, 1).create(Acclimatiser.SLOT_ACCLIMATISER);
		x += 72;
		new ControlSlotArray.Builder(this, x, y, 2, 2).create(Acclimatiser.SLOT_DRONE);
		new ControlEnergyBar(this, 21, 115, 16, 60, Alignment.BOTTOM);
		new ControlErrorState(this, 181, 83);
		new ControlPlayerInventory(this);
	}

	@Override
	public String getTitle() {
		return I18N.localise("genetics.machine.lab_machine.acclimatiser");
	}

	@Override
	protected String getModId() {
		return Genetics.instance.getModId();
	}

	@Override
	protected String getBackgroundTextureName() {
		return "acclimatiser";
	}
}
