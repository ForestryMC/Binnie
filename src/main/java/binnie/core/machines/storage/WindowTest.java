package binnie.core.machines.storage;

import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import binnie.craftgui.genetics.machine.WindowMachine;
import binnie.craftgui.minecraft.Window;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;

public class WindowTest extends WindowMachine {
	public static Window create(final EntityPlayer player, final IInventory inventory, final Side side) {
		return new WindowCompartment(player, inventory, side);
	}

	public WindowTest(final EntityPlayer player, final IInventory inventory, final Side side) {
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
