package binnie.botany.genetics;

import binnie.botany.api.genetics.IAlleleFlowerColor;
import binnie.core.util.I18N;
import forestry.api.genetics.IAlleleInteger;

import java.awt.Color;

public class AlleleFlowerColor implements IAlleleFlowerColor {
	private final int color;
	private final String uid;
	private final boolean isDominant;
	private final String name;
	private final String unlocalizedName;
	private final int colorWilted;
	private final int id;

	public AlleleFlowerColor(String uid, int id, Color color, Color colorWilted, String name, String unlocalizedName, boolean isDominant) {
		this.color = color.getRGB();
		this.uid = uid;
		this.isDominant = isDominant;
		this.name = name;
		this.unlocalizedName = unlocalizedName;
		this.colorWilted = colorWilted.getRGB();
		this.id = id;
	}

	@Override
	public int getValue() {
		return color;
	}

	@Override
	public String getUID() {
		return uid;
	}

	@Override
	public boolean isDominant() {
		return isDominant;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getAlleleName() {
		return name;
	}

	@Override
	public String getUnlocalizedName() {
		return unlocalizedName;
	}

	@Override
	public int getColor(boolean wilted) {
		return wilted ? colorWilted : color;
	}

	@Override
	public IAlleleInteger getAllele() {
		return this;
	}

	@Override
	public int getID() {
		return id;
	}

	@Override
	public String getColorName() {
		return I18N.localise("botany.color." + getAlleleName());
	}
}