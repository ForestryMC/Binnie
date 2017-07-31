package binnie.core.machines.storage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

import net.minecraftforge.fml.relauncher.Side;

import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import binnie.core.gui.minecraft.Window;
import binnie.genetics.machine.craftgui.WindowMachine;

public class WindowTest extends WindowMachine {
	public WindowTest(final EntityPlayer player, final IInventory inventory, final Side side) {
		super(320, 240, player, inventory, side);
	}

	public static Window create(final EntityPlayer player, final IInventory inventory, final Side side) {
		return new WindowCompartment(player, inventory, side);
	}

	@Override
	public void initialiseClient() {
	}

	@Override
	public String getTitle() {
		return "Test";
	}

	@Override
	protected AbstractMod getMod() {
		return BinnieCore.getInstance();
	}

	@Override
	protected String getBackgroundTextureName() {
		return "Test";
	}
}
