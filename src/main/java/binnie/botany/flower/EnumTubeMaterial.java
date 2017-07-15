package binnie.botany.flower;

import binnie.core.util.I18N;

enum EnumTubeMaterial {
	COPPER(0xe3b78e, "copper"),
	TIN(0xe1eef4, "tin"),
	BRONZE(0xddc276, "bronze"),
	IRON(0xd8d8d8, "iron");

	public static final EnumTubeMaterial[] VALUES = values();

	int color;
	String name;

	EnumTubeMaterial(int color, String name) {
		this.color = color;
		this.name = name;
	}

	public static EnumTubeMaterial get(int meta) {
		return VALUES[meta % VALUES.length];
	}

	public int getColor() {
		return color;
	}

	public String getDisplayName() {
		return I18N.localise("botany.tube.material." + name + ".name");
	}
}
