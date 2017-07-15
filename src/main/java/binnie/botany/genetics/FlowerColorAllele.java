package binnie.botany.genetics;

import forestry.api.genetics.IAlleleInteger;

import binnie.botany.api.IFlowerColor;
import binnie.core.util.I18N;

public class FlowerColorAllele implements IFlowerColor, IAlleleInteger {
	private int color;
	private String uid;
	private boolean isDominant;
	private String name;
	private String unlocalizedName;
	private int colorDis;
	private int id;
	
	public FlowerColorAllele(String uid, int id, int color, int colorDis, String name, String unlocalizedName, boolean isDominant) {
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
		return I18N.localise("botany.color." + getName());
	}
}