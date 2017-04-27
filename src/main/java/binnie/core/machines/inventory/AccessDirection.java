package binnie.core.machines.inventory;

import net.minecraft.util.EnumChatFormatting;

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

	public String getTextColour() {
		switch (this) {
			case Both:
				return EnumChatFormatting.GREEN.toString();

			case In:
				return EnumChatFormatting.YELLOW.toString();

			case Neither:
				return EnumChatFormatting.RED.toString();
		}
		return EnumChatFormatting.AQUA.toString();
	}

	public int getShadeColour() {
		switch (this) {
			case Both:
				return 0x5555ff55;

			case In:
				return 0x55ffff55;

			case Neither:
				return 0x55ff5555;
		}
		return 0x5555ffff;
	}
}
