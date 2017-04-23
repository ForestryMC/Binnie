package binnie.core.machines.storage;

import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import binnie.core.craftgui.minecraft.Window;
import binnie.genetics.craftgui.WindowMachine;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

// TODO unused class?
public class WindowTest extends WindowMachine {
	public static Window create(EntityPlayer player, IInventory inventory, Side side) {
		return new WindowCompartment(player, inventory, side);
	}

	public WindowTest(EntityPlayer player, IInventory inventory, Side side) {
		super(320, 240, player, inventory, side);
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
		return BinnieCore.instance;
	}

	@Override
	protected String getName() {
		return "Test";
	}
}
