package binnie.core.machines.storage;

import binnie.core.*;
import binnie.craftgui.genetics.machine.*;
import binnie.craftgui.minecraft.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;

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
