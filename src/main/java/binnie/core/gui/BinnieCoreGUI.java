package binnie.core.gui;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;

import binnie.core.gui.minecraft.Window;
import binnie.core.machines.storage.WindowCompartment;

public enum BinnieCoreGUI implements IBinnieGUID {
	Compartment,
	FieldKit,
	Genesis;

	public Window getWindow(final EntityPlayer player, @Nullable final IInventory object, final Side side) {
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
				throw new IllegalArgumentException("Unknown GUI type: " + this);
			}
		}
	}

	@Override
	public Window getWindow(final EntityPlayer player, final World world, final int x, final int y, final int z, final Side side) {
		final TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
		IInventory object = null;
		if (tileEntity instanceof IInventory) {
			object = (IInventory) tileEntity;
		}

		return this.getWindow(player, object, side);
	}
}
