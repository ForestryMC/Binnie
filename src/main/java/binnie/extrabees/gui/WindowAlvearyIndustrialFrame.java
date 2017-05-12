package binnie.extrabees.gui;

import binnie.core.AbstractMod;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.core.craftgui.minecraft.control.ControlSlot;
import binnie.extrabees.client.GuiHack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;

public class WindowAlvearyIndustrialFrame extends Window {
	public WindowAlvearyIndustrialFrame(final EntityPlayer player, final IInventory inventory, final Side side) {
		super(176, 144, player, inventory, side);
	}

	public static Window create(final EntityPlayer player, final IInventory inventory, final Side side) {
		return new WindowAlvearyIndustrialFrame(player, inventory, side);
	}

	@Override
	public void initialiseClient() {
		this.setTitle("Industrial Frame Housing");
		ControlPlayerInventory playerInventory = new ControlPlayerInventory(this);
		new ControlSlot.Builder(this, 79, 30).assign(0);
	}

	@Override
	public AbstractMod getMod() {
		return GuiHack.INSTANCE;
	}

	@Override
	public String getBackgroundTextureName() {
		return "AlvearyIndustrialFrame";
	}
}
