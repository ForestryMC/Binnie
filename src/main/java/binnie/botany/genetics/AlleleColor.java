// 
// Decompiled by Procyon v0.5.30
// 

package binnie.botany.genetics;

import forestry.api.genetics.IAlleleInteger;

public class AlleleColor implements IAlleleInteger
{
	String uid;
	boolean dominant;
	String name;
	int value;
	EnumFlowerColor color;

	public AlleleColor(final EnumFlowerColor color, final String uid, final String name, final int value) {
		this.color = color;
		this.uid = uid;
		this.name = name;
		this.value = value;
	}

	@Override
	public String getUID() {
		return this.uid;
	}

	@Override
	public boolean isDominant() {
		return true;
	}

	@Override
	public String getName() {
		return this.color.getName();
	}

	@Override
	public int getValue() {
		return this.value;
	}

	public EnumFlowerColor getColor() {
		return this.color;
	}

	@Override
	public String getUnlocalizedName() {
		return this.getUID();
	}
}
