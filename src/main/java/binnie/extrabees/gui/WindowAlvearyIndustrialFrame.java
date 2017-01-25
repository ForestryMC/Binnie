package binnie.extrabees.gui;

import binnie.core.AbstractMod;
import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.craftgui.minecraft.control.ControlSlot;
import binnie.extrabees.ExtraBees;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;

public class WindowAlvearyIndustrialFrame extends Window {
	Machine machine;
	ControlPlayerInventory playerInventory;

	public WindowAlvearyIndustrialFrame(final EntityPlayer player, final IInventory inventory, final Side side) {
		super(176, 144, player, inventory, side);
		this.machine = ((TileEntityMachine) inventory).getMachine();
	}

	public static Window create(final EntityPlayer player, final IInventory inventory, final Side side) {
		if (player == null || inventory == null) {
			return null;
		}
		return new WindowAlvearyIndustrialFrame(player, inventory, side);
	}

	@Override
	public void initialiseClient() {
		this.setTitle("Industrial Frame Housing");
		this.playerInventory = new ControlPlayerInventory(this);
		final ControlSlot slot = new ControlSlot(this, 79, 30);
		slot.assign(0);
	}

	@Override
	public AbstractMod getMod() {
		return ExtraBees.instance;
	}

	@Override
	public String getBackgroundTextureName() {
		return "AlvearyIndustrialFrame";
	}
}
