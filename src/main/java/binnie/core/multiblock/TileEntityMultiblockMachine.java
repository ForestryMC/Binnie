// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.multiblock;

import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.Machine;
import net.minecraft.tileentity.TileEntity;

class TileEntityMultiblockMachine extends TileEntity
{
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
		final TileEntity tile = this.worldObj.getTileEntity(this.xCoord + this.tileX, this.yCoord + this.tileY, this.zCoord + this.tileZ);
		if (tile instanceof TileEntityMachine) {
			return ((TileEntityMachine) tile).getMachine();
		}
		return null;
	}
}
