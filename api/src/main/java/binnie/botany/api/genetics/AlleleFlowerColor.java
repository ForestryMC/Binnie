package binnie.botany.api.genetics;

import forestry.api.genetics.IAlleleInteger;
import net.minecraft.util.text.translation.I18n;

// TODO: move out of API
public class AlleleFlowerColor implements IAlleleFlowerColor {
	private int color;
	private String uid;
	private boolean isDominant;
	private String name;
	private String unlocalizedName;
	private int colorDis;
	private int id;

	public AlleleFlowerColor(String uid, int id, int color, int colorDis, String name, String unlocalizedName, boolean isDominant) {
		this.color = color;
		this.uid = uid;
		this.isDominant = isDominant;
		this.name = name;
		this.unlocalizedName = unlocalizedName;
		this.colorDis = colorDis;
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
	public int getColor(boolean dis) {
		return dis ? colorDis : color;
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
		return I18n.translateToLocal("botany.color." + getAlleleName());
	}
}