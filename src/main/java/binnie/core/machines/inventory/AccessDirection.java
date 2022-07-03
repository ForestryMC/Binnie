package binnie.core.machines.inventory;

enum AccessDirection {
    Both,
    In,
    Out,
    Neither;

    boolean canInsert() {
        return this == AccessDirection.Both || this == AccessDirection.In;
    }

    boolean canExtract() {
        return this == AccessDirection.Both || this == AccessDirection.Out;
    }

    boolean canAccess() {
        return this != AccessDirection.Neither;
    }

    AccessDirection changeInsert(boolean b) {
        if (b) {
            if (this == AccessDirection.Out) {
                return AccessDirection.Both;
            }
            if (this == AccessDirection.Neither) {
                return AccessDirection.In;
            }
        } else {
            if (this == AccessDirection.Both) {
                return AccessDirection.Out;
            }
            if (this == AccessDirection.In) {
                return AccessDirection.Neither;
            }
        }
        return this;
    }

    AccessDirection changeExtract(boolean b) {
        if (b) {
            if (this == AccessDirection.In) {
                return AccessDirection.Both;
            }
            if (this == AccessDirection.Neither) {
                return AccessDirection.Out;
            }
        } else {
            if (this == AccessDirection.Both) {
                return AccessDirection.In;
            }
            if (this == AccessDirection.Out) {
                return AccessDirection.Neither;
            }
        }
        return this;
    }
}
