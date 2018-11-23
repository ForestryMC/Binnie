package binnie.core.machines.inventory;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.util.EnumFacing;

class SidedAccess {
	private final Map<EnumFacing, AccessDirection> accesses;
	private AccessDirection base;
	private boolean insertLocked;
	private boolean extractLocked;

	public SidedAccess() {
		this.accesses = new HashMap<>();
		this.base = AccessDirection.BOTH;
		this.insertLocked = false;
		this.extractLocked = false;
	}

	public AccessDirection getAccess(@Nullable EnumFacing side) {
		return this.accesses.getOrDefault(side, this.base);
	}

	public boolean canInsert(@Nullable EnumFacing side) {
		return this.getAccess(side).canInsert();
	}

	public boolean canExtract(@Nullable EnumFacing side) {
		return this.getAccess(side).canExtract();
	}

	public boolean canAccess(EnumFacing side) {
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
		List<EnumFacing> dirs = new ArrayList<>();
		for (EnumFacing side : EnumFacing.VALUES) {
			AccessDirection direction = getAccess(side);
			if (direction.canInsert()) {
				dirs.add(side);
			}
		}
		return dirs;
	}

	public Collection<EnumFacing> getExtractionSides() {
		List<EnumFacing> dirs = new ArrayList<>();
		for (EnumFacing side : EnumFacing.VALUES) {
			AccessDirection direction = getAccess(side);
			if (direction.canExtract()) {
				dirs.add(side);
			}
		}
		return dirs;
	}

	public void setInsert(EnumFacing side, boolean value) {
		AccessDirection direction = getAccess(side);
		if (direction.canInsert() != value) {
			this.accesses.put(side, direction.changeInsert(value));
		}
	}

	public void setExtract(EnumFacing side, boolean value) {
		AccessDirection direction = getAccess(side);
		if (direction.canExtract() != value) {
			this.accesses.put(side, direction.changeExtract(value));
		}
	}

	public void setInsert(boolean value) {
		if (this.base.canInsert() != value) {
			this.base = this.base.changeInsert(value);
		}
	}

	public void setExtract(boolean value) {
		if (this.base.canExtract() != value) {
			this.base = this.base.changeExtract(value);
		}
	}
}
