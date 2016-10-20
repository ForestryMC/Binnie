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

    AccessDirection changeInsert(final boolean b) {
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

    AccessDirection changeExtract(final boolean b) {
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

    public String getTextColour() {
        switch (this) {
            case Both: {
                return "§a";
            }
            case In: {
                return "§e";
            }
            case Neither: {
                return "§c";
            }
            default: {
                return "§b";
            }
        }
    }

    public int getShadeColour() {
        switch (this) {
            case Both: {
                return 1431699285;
            }
            case In: {
                return 1442840405;
            }
            case Neither: {
                return 1442796885;
            }
            default: {
                return 1431699455;
            }
        }
    }
}
