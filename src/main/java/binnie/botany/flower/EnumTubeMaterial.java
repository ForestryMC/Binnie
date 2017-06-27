package binnie.botany.flower;

import binnie.core.util.I18N;

enum EnumTubeMaterial {
	Copper(0xe3b78e, "copper"),
	Tin(0xe1eef4, "tin"),
	Bronze(0xddc276, "bronze"),
	Iron(0xd8d8d8, "iron");

	public static final EnumTubeMaterial[] VALUES = values();
	
	int color;
	String name;

	EnumTubeMaterial(final int color, final String name) {
		this.color = color;
		this.name = name;
	}

	public static EnumTubeMaterial get(int meta) {
		return VALUES[meta % VALUES.length];
	}

	public int getColor() {
		return this.color;
	}
	
	public String getDisplayName() {
		return I18N.localise("botany.tube.material." + name + ".name");
	}
}
