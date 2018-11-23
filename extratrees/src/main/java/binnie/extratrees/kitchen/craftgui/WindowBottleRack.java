package binnie.extratrees.kitchen.craftgui;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.gui.minecraft.Window;
import binnie.core.gui.minecraft.control.ControlPlayerInventory;
import binnie.core.machines.Machine;
import binnie.extratrees.ExtraTrees;

public class WindowBottleRack extends Window {
	public WindowBottleRack(EntityPlayer player, IInventory inventory, Side side) {
		super(248, 180, player, inventory, side);
	}

	@Nullable
	public static Window create(EntityPlayer player, @Nullable IInventory inventory, Side side) {
		if (inventory == null) {
			return null;
		}
		return new WindowBottleRack(player, inventory, side);
	}

	@Override
	protected String getModId() {
		return ExtraTrees.instance.getModId();
	}

	@Override
	protected String getBackgroundTextureName() {
		return "BottleBank";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void initialiseClient() {
		this.setTitle(Machine.getMachine(this.getInventory()).getPackage().getDisplayName());
		for (int i = 0; i < 36; ++i) {
			int x = i % 12;
			int y = i / 12;
			new ControlTankSlot(this, 16 + x * 18, 32 + y * 18, i);
		}
		new ControlPlayerInventory(this);
	}
}
