package binnie.extrabees.gui;

import binnie.core.AbstractMod;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.craftgui.minecraft.control.ControlSlot;
import binnie.extrabees.ExtraBees;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nullable;

public class WindowAlvearyFrame extends Window {

	public WindowAlvearyFrame(final EntityPlayer player, final IInventory inventory, final Side side) {
		super(176, 144, player, inventory, side);
	}

	@Nullable
	public static Window create(final EntityPlayer player, @Nullable final IInventory inventory, final Side side) {
		if (inventory == null) {
			return null;
		}
		return new WindowAlvearyFrame(player, inventory, side);
	}

	@Override
	public void initialiseClient() {
		this.setTitle("Frame Housing");
		ControlPlayerInventory playerInventory = new ControlPlayerInventory(this);
		new ControlSlot.Builder(this, 79, 30).assign(0);
	}

	@Override
	public AbstractMod getMod() {
		return ExtraBees.instance;
	}

	@Override
	public String getBackgroundTextureName() {
		return "AlvearyFrame";
	}
}
