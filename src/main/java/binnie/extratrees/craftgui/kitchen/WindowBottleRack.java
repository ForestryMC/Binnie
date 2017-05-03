package binnie.extratrees.craftgui.kitchen;

import binnie.core.AbstractMod;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.core.machines.Machine;
import binnie.extratrees.ExtraTrees;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

public class WindowBottleRack extends Window {
	public WindowBottleRack(EntityPlayer player, IInventory inventory, Side side) {
		super(248.0f, 180.0f, player, inventory, side);
	}

	public static Window create(EntityPlayer player, IInventory inventory, Side side) {
		return new WindowBottleRack(player, inventory, side);
	}

	@Override
	protected AbstractMod getMod() {
		return ExtraTrees.instance;
	}

	@Override
	protected String getName() {
		return "BottleBank";
	}

	@Override
	public void initialiseClient() {
		setTitle(Machine.getMachine(getInventory()).getPackage().getDisplayName());
		for (int i = 0; i < 36; ++i) {
			int x = i % 12;
			int y = i / 12;
			new ControlTankSlot(this, 16 + x * 18, 32 + y * 18, i);
		}
		new ControlPlayerInventory(this);
	}
}
