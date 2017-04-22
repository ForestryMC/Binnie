package binnie.core.machines.inventory;

import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class SidedAccess {
	private Map<ForgeDirection, AccessDirection> accesses = new HashMap<>();
	private AccessDirection base = AccessDirection.Both;
	private boolean insertLocked;
	private boolean extractLocked;

	public SidedAccess() {
		// ignored
	}

	public AccessDirection getAccess(ForgeDirection side) {
		return accesses.containsKey(side) ? accesses.get(side) : base;
	}

	public boolean canInsert(ForgeDirection side) {
		return getAccess(side).canInsert();
	}

	public boolean canExtract(ForgeDirection side) {
		return getAccess(side).canExtract();
	}

	public boolean canAccess(ForgeDirection side) {
		return getAccess(side).canAccess();
	}

	public boolean canChangeInsert() {
		return !insertLocked;
	}

	public boolean canChangeExtract() {
		return !extractLocked;
	}

	public void forbidInsertChange() {
		insertLocked = true;
	}

	public void forbidExtractChange() {
		extractLocked = true;
	}

	public Collection<ForgeDirection> getInsertionSides() {
		List<ForgeDirection> dirs = new ArrayList<>();
		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			if (getAccess(dir).canInsert()) {
				dirs.add(dir);
			}
		}
		return dirs;
	}

	public Collection<ForgeDirection> getExtractionSides() {
		List<ForgeDirection> dirs = new ArrayList<>();
		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			if (getAccess(dir).canExtract()) {
				dirs.add(dir);
			}
		}
		return dirs;
	}

	public void setInsert(ForgeDirection side, boolean b) {
		if (getAccess(side).canInsert() != b) {
			accesses.put(side, getAccess(side).changeInsert(b));
		}
	}

	public void setExtract(ForgeDirection side, boolean b) {
		if (getAccess(side).canExtract() != b) {
			accesses.put(side, getAccess(side).changeExtract(b));
		}
	}

	public void setInsert(boolean b) {
		if (base.canInsert() != b) {
			base = base.changeInsert(b);
		}
	}

	public void setExtract(boolean b) {
		if (base.canExtract() != b) {
			base = base.changeExtract(b);
		}
	}
}
