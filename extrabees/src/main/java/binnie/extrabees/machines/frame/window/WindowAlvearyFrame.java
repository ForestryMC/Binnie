package binnie.extrabees.machines.frame.window;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

import net.minecraftforge.fml.relauncher.Side;

import binnie.core.Constants;
import binnie.core.gui.minecraft.Window;
import binnie.core.gui.minecraft.control.ControlPlayerInventory;
import binnie.core.gui.minecraft.control.ControlSlot;
import binnie.core.machines.Machine;

public class WindowAlvearyFrame extends Window {

	public WindowAlvearyFrame(EntityPlayer player, IInventory inventory, Side side) {
		super(176, 144, player, inventory, side);
	}

	@Nullable
	public static Window create(EntityPlayer player, @Nullable IInventory inventory, Side side) {
		if (inventory == null) {
			return null;
		}
		return new WindowAlvearyFrame(player, inventory, side);
	}

	@Override
	public void initialiseClient() {
		this.setTitle(Machine.getMachine(this.getInventory()).getPackage().getDisplayName());
		new ControlPlayerInventory(this);
		new ControlSlot.Builder(this, 79, 30).assign(0);
	}

	@Override
	protected String getModId() {
		return Constants.EXTRA_BEES_MOD_ID;
	}

	@Override
	public String getBackgroundTextureName() {
		return "AlvearyFrame";
	}
}
