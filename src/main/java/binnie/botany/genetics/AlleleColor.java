package binnie.botany.genetics;

import forestry.api.genetics.IAlleleInteger;

public class AlleleColor implements IAlleleInteger {
	protected String uid;
	protected boolean dominant;
	protected String name;
	protected int value;
	protected EnumFlowerColor color;

	public AlleleColor(EnumFlowerColor color, String uid, String name, int value) {
		this.color = color;
		this.uid = uid;
		this.name = name;
		this.value = value;
	}

	@Override
	public String getUID() {
		return uid;
	}

	@Override
	public boolean isDominant() {
		return true;
	}

	@Override
	public String getName() {
		return color.getName();
	}

	@Override
	public int getValue() {
		return value;
	}

	public EnumFlowerColor getColor() {
		return color;
	}

	@Override
	public String getUnlocalizedName() {
		return getUID();
	}
}
