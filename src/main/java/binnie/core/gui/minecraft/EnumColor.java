package binnie.core.gui.minecraft;

public enum EnumColor {
	BLACK("Black", 0, '0'),
	DARK_BLUE("Dark Blue", 170, '1'),
	DARK_GREEN("Dark Green", 43520, '2'),
	DARK_AQUA("Dark Aqua", 43690, '3'),
	DARK_RED("Dark Red", 11141120, '4'),
	PURPLE("Purple", 11141290, '5'),
	GOLD("Gold", 16755200, '6'),
	GREY("Grey", 11184810, '7'),
	DARK_GREY("Dark Grey", 5592405, '8'),
	BLUE("Blue", 5592575, '9'),
	GREEN("Green", 5635925, 'a'),
	AQUA("Aqua", 5636095, 'b'),
	RED("Red", 16733525, 'c'),
	PINK("Pink", 16733695, 'd'),
	YELLOW("Yellow", 16777045, 'e'),
	WHITE("White", 16777215, 'f');

	int color;
	String name;
	char code;

	EnumColor(final String name, int color, final char code) {
		this.name = name;
		this.color = color;
		this.code = code;
		System.out.print(name() + "(\"" + name + "\n,"+ Integer.toHexString(color) + ",\'" + code + "\'),\n");
		if(color == 16777215){
			getClass();
		}
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
