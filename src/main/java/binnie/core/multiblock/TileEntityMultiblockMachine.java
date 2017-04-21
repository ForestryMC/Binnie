package binnie.core.multiblock;

import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import net.minecraft.tileentity.TileEntity;

class TileEntityMultiblockMachine extends TileEntity {
	private boolean inStructure;
	private int tileX;
	private int tileY;
	private int tileZ;

	boolean inStructure() {
		return this.inStructure;
	}

	public Machine getMachine() {
		return this.getMasterMachine();
	}

	private Machine getMasterMachine() {
		if (!this.inStructure) {
			return null;
		}

		TileEntity tile = this.worldObj.getTileEntity(xCoord + tileX, yCoord + tileY, zCoord + tileZ);
		if (tile instanceof TileEntityMachine) {
			return ((TileEntityMachine) tile).getMachine();
		}
		return null;
	}
}
