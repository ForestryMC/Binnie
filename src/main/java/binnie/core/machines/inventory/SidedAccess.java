package binnie.core.machines.inventory;

import net.minecraft.util.EnumFacing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class SidedAccess {
	private Map<EnumFacing, AccessDirection> accesses;
	private AccessDirection base;
	private boolean insertLocked;
	private boolean extractLocked;

	public SidedAccess() {
		this.accesses = new HashMap<>();
		this.base = AccessDirection.Both;
		this.insertLocked = false;
		this.extractLocked = false;
	}

	public AccessDirection getAccess(final EnumFacing side) {
		return this.accesses.containsKey(side) ? this.accesses.get(side) : this.base;
	}

	public boolean canInsert(final EnumFacing side) {
		return this.getAccess(side).canInsert();
	}

	public boolean canExtract(final EnumFacing side) {
		return this.getAccess(side).canExtract();
	}

	public boolean canAccess(final EnumFacing side) {
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

	public Collection<EnumFacing> getInsertionSides() {
		final List<EnumFacing> dirs = new ArrayList<>();
		for (final EnumFacing dir : EnumFacing.VALUES) {
			if (this.getAccess(dir).canInsert()) {
				dirs.add(dir);
			}
		}
		return dirs;
	}

	public Collection<EnumFacing> getExtractionSides() {
		final List<EnumFacing> dirs = new ArrayList<>();
		for (final EnumFacing dir : EnumFacing.VALUES) {
			if (this.getAccess(dir).canExtract()) {
				dirs.add(dir);
			}
		}
		return dirs;
	}

	public void setInsert(final EnumFacing side, final boolean b) {
		if (this.getAccess(side).canInsert() != b) {
			this.accesses.put(side, this.getAccess(side).changeInsert(b));
		}
	}

	public void setExtract(final EnumFacing side, final boolean b) {
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
