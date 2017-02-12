package binnie.extrabees.gui;

import binnie.core.AbstractMod;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.craftgui.minecraft.control.ControlSlotArray;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.apiary.machine.AlvearyHatchery;
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
		this.setTitle("Hatchery");
		ControlPlayerInventory playerInventory = new ControlPlayerInventory(this);
		final ControlSlotArray.Builder slot = new ControlSlotArray.Builder(this, 43, 30, 5, 1);
		slot.create(AlvearyHatchery.slotLarvae);
	}

	@Override
	public AbstractMod getMod() {
		return ExtraBees.instance;
	}

	@Override
	public String getBackgroundTextureName() {
		return "AlvearyHatchery";
	}
}
