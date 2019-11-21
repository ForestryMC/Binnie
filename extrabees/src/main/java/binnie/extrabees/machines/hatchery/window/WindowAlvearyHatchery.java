package binnie.extrabees.machines.hatchery.window;

import binnie.core.Constants;
import binnie.core.gui.minecraft.Window;
import binnie.core.gui.minecraft.control.ControlPlayerInventory;
import binnie.core.gui.minecraft.control.ControlSlotArray;
import binnie.core.machines.Machine;
import binnie.extrabees.machines.hatchery.AlvearyHatchery;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nullable;

public class WindowAlvearyHatchery extends Window {
	public WindowAlvearyHatchery(final EntityPlayer player, final IInventory inventory, final Side side) {
		super(176, 144, player, inventory, side);
	}

	@Nullable
	public static Window create(final EntityPlayer player, @Nullable final IInventory inventory, final Side side) {
		if (inventory == null) {
			return null;
		}
		return new WindowAlvearyHatchery(player, inventory, side);
	}

	@Override
	public void initialiseClient() {
		this.setTitle(Machine.getMachine(this.getInventory()).getPackage().getDisplayName());
		ControlPlayerInventory playerInventory = new ControlPlayerInventory(this);
		final ControlSlotArray.Builder slot = new ControlSlotArray.Builder(this, 43, 30, 5, 1);
		slot.create(AlvearyHatchery.SLOT_LARVAE);
	}

	@Override
	protected String getModId() {
		return Constants.EXTRA_BEES_MOD_ID;
	}

	@Override
	public String getBackgroundTextureName() {
		return "AlvearyHatchery";
	}
}
