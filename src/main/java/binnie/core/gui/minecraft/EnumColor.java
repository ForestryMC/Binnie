package binnie.core.gui.minecraft;

public enum EnumColor {
	BLACK("Black", 0x0,'0'),
	DARK_BLUE("Dark Blue", 0xaa,'1'),
	DARK_GREEN("Dark Green", 0xaa00,'2'),
	DARK_AQUA("Dark Aqua", 0xaaaa,'3'),
	DARK_RED("Dark Red", 0xaa0000,'4'),
	PURPLE("Purple", 0xaa00aa,'5'),
	GOLD("Gold", 0xffaa00,'6'),
	GREY("Grey", 0xaaaaaa,'7'),
	DARK_GREY("Dark Grey", 0x555555,'8'),
	BLUE("Blue", 0x5555ff,'9'),
	GREEN("Green", 0x55ff55,'a'),
	AQUA("Aqua", 0x55ffff,'b'),
	RED("Red", 0xff5555,'c'),
	PINK("Pink", 0xff55ff,'d'),
	YELLOW("Yellow", 0xffff55,'e'),
	WHITE("White", 0xffffff,'f');

	int color;
	String name;
	char code;

	EnumColor(final String name, int color, final char code) {
		this.name = name;
		this.color = color;
		this.code = code;
	}

	public int getColor() {
		return this.color;
	}

	public String getCode() {
		return "§" + this.code;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
