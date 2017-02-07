package binnie.core.multiblock;

import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nullable;

class TileEntityMultiblockMachine extends TileEntity {
	private boolean inStructure;
	private int tileX;
	private int tileY;
	private int tileZ;

	boolean inStructure() {
		return this.inStructure;
	}

	@Nullable
	public Machine getMachine() {
		return this.getMasterMachine();
	}

	@Nullable
	private Machine getMasterMachine() {
		if (!this.inStructure) {
			return null;
		}
		final TileEntity tile = this.worldObj.getTileEntity(this.getPos().add(this.tileX, this.tileY, this.tileZ));
		if (tile instanceof TileEntityMachine) {
			return ((TileEntityMachine) tile).getMachine();
		}
		return null;
	}
}
