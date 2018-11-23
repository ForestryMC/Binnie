package binnie.core.machines.inventory;

enum AccessDirection {
	BOTH,
	IN,
	OUT,
	NEITHER;

	boolean canInsert() {
		return this == AccessDirection.BOTH || this == AccessDirection.IN;
	}

	boolean canExtract() {
		return this == AccessDirection.BOTH || this == AccessDirection.OUT;
	}

	boolean canAccess() {
		return this != AccessDirection.NEITHER;
	}

	AccessDirection changeInsert(boolean b) {
		if (b) {
			if (this == AccessDirection.OUT) {
				return AccessDirection.BOTH;
			}
			if (this == AccessDirection.NEITHER) {
				return AccessDirection.IN;
			}
		} else {
			if (this == AccessDirection.BOTH) {
				return AccessDirection.OUT;
			}
			if (this == AccessDirection.IN) {
				return AccessDirection.NEITHER;
			}
		}
		return this;
	}

	AccessDirection changeExtract(boolean b) {
		if (b) {
			if (this == AccessDirection.IN) {
				return AccessDirection.BOTH;
			}
			if (this == AccessDirection.NEITHER) {
				return AccessDirection.OUT;
			}
		} else {
			if (this == AccessDirection.BOTH) {
				return AccessDirection.IN;
			}
			if (this == AccessDirection.OUT) {
				return AccessDirection.NEITHER;
			}
		}
		return this;
	}

	public String getTextColour() {
		switch (this) {
			case BOTH: {
				return "§a";
			}
			case IN: {
				return "§e";
			}
			case NEITHER: {
				return "§c";
			}
			default: {
				return "§b";
			}
		}
	}

	public int getShadeColour() {
		switch (this) {
			case BOTH: {
				return 1431699285;
			}
			case IN: {
				return 1442840405;
			}
			case NEITHER: {
				return 1442796885;
			}
			default: {
				return 1431699455;
			}
		}
	}
}
