package binnie.core.machines.inventory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraftforge.common.util.ForgeDirection;

class SidedAccess {
    private Map<ForgeDirection, AccessDirection> accesses = new HashMap<>();
    private AccessDirection base = AccessDirection.Both;
    private boolean insertLocked;
    private boolean extractLocked;

    public SidedAccess() {
        // ignored
    }

    public AccessDirection getAccess(ForgeDirection side) {
        return accesses.getOrDefault(side, base);
    }

    public boolean canInsert(ForgeDirection side) {
        return getAccess(side).canInsert();
    }

    public boolean canExtract(ForgeDirection side) {
        return getAccess(side).canExtract();
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
