package binnie.botany.flower;

enum EnumTubeMaterial {
	Copper(0xe3b78e, "Copper"),
	Tin(0xe1eef4, "Tin"),
	Bronze(0xddc276, "Bronze"),
	Iron(0xd8d8d8, "Iron");

	int color;
	String name;

	EnumTubeMaterial(final int color, final String name) {
		this.color = color;
		this.name = name;
	}

	public static EnumTubeMaterial get(final int i) {
		return values()[i % values().length];
	}

	public int getColor() {
		return this.color;
	}

	public String getName() {
		return this.name;
	}
}
