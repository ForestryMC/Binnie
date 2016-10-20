// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.machines.inventory;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import net.minecraftforge.common.util.ForgeDirection;
import java.util.Map;

class SidedAccess
{
	private Map<ForgeDirection, AccessDirection> accesses;
	private AccessDirection base;
	private boolean insertLocked;
	private boolean extractLocked;

	public SidedAccess() {
		this.accesses = new HashMap<ForgeDirection, AccessDirection>();
		this.base = AccessDirection.Both;
		this.insertLocked = false;
		this.extractLocked = false;
	}

	public AccessDirection getAccess(final ForgeDirection side) {
		return this.accesses.containsKey(side) ? this.accesses.get(side) : this.base;
	}

	public boolean canInsert(final ForgeDirection side) {
		return this.getAccess(side).canInsert();
	}

	public boolean canExtract(final ForgeDirection side) {
		return this.getAccess(side).canExtract();
	}

	public boolean canAccess(final ForgeDirection side) {
		return this.getAccess(side).canAccess();
	}

	public boolean canChangeInsert() {
		return !this.insertLocked;
	}

	public boolean canChangeExtract() {
		return !this.extractLocked;
	}

	public void forbidInsertChange() {
		this.insertLocked = true;
	}

	public void forbidExtractChange() {
		this.extractLocked = true;
	}

	public Collection<ForgeDirection> getInsertionSides() {
		final List<ForgeDirection> dirs = new ArrayList<ForgeDirection>();
		for (final ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			if (this.getAccess(dir).canInsert()) {
				dirs.add(dir);
			}
		}
		return dirs;
	}

	public Collection<ForgeDirection> getExtractionSides() {
		final List<ForgeDirection> dirs = new ArrayList<ForgeDirection>();
		for (final ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			if (this.getAccess(dir).canExtract()) {
				dirs.add(dir);
			}
		}
		return dirs;
	}

	public void setInsert(final ForgeDirection side, final boolean b) {
		if (this.getAccess(side).canInsert() != b) {
			this.accesses.put(side, this.getAccess(side).changeInsert(b));
		}
	}

	public void setExtract(final ForgeDirection side, final boolean b) {
		if (this.getAccess(side).canExtract() != b) {
			this.accesses.put(side, this.getAccess(side).changeExtract(b));
		}
	}

	public void setInsert(final boolean b) {
		if (this.base.canInsert() != b) {
			this.base = this.base.changeInsert(b);
		}
	}

	public void setExtract(final boolean b) {
		if (this.base.canExtract() != b) {
			this.base = this.base.changeExtract(b);
		}
	}
}
