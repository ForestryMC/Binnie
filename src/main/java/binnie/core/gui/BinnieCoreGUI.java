package binnie.core.gui;

import binnie.core.machines.storage.WindowCompartment;
import binnie.craftgui.binniecore.WindowFieldKit;
import binnie.craftgui.binniecore.WindowGenesis;
import binnie.craftgui.minecraft.Window;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;

public enum BinnieCoreGUI implements IBinnieGUID {
	Compartment,
	FieldKit,
	Genesis;

	public Window getWindow(final EntityPlayer player, final IInventory object, final Side side) throws Exception {
		switch (this) {
			case Compartment: {
				return new WindowCompartment(player, object, side);
			}
			case FieldKit: {
				return new WindowFieldKit(player, null, side);
			}
			case Genesis: {
				return new WindowGenesis(player, null, side);
			}
			default: {
				return null;
			}
		}
	}

	@Override
	public Window getWindow(final EntityPlayer player, final World world, final int x, final int y, final int z, final Side side) {
		Window window = null;
		final TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
		IInventory object = null;
		if (tileEntity instanceof IInventory) {
			object = (IInventory) tileEntity;
		}
		try {
			window = this.getWindow(player, object, side);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return window;
	}
}
