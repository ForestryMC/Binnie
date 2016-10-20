// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.alcohol;

import binnie.Binnie;
import net.minecraftforge.fluids.FluidStack;
import binnie.extratrees.ExtraTrees;
import net.minecraft.client.renderer.texture.IIconRegister;
import binnie.core.liquid.FluidContainer;
import net.minecraft.util.IIcon;
import binnie.core.liquid.IFluidType;

public enum Spirit implements IFluidType, ICocktailLiquid
{
	NeutralSpirit("Neutral Spirit", "spiritNeutral", 16777215, 0.05, 0.8),
	Vodka("Vodka", "vodka", 16053751, 0.05, 0.4),
	WhiteRum("White Rum", "rumWhite", 15132132, 0.05, 0.4),
	DarkRum("Dark Rum", "rumDark", 11018752, 0.4, 0.4),
	Whiskey("Whiskey", "whiskey", 13594368, 0.2, 0.4),
	CornWhiskey("Bourbon", "whiskeyCorn", 9835009, 0.2, 0.4),
	RyeWhiskey("Rye Whiskey", "whiskeyRye", 16085800, 0.2, 0.4),
	WheatWhiskey("Wheat Whiskey", "whiskeyWheat", 14976530, 0.2, 0.4),
	FortifiedWine("Fortified Wine", "wineFortified", 15569439, 0.2, 0.2),
	Tequila("Tequila", "tequila", 16116160, 0.05, 0.4),
	Brandy("Brandy", "brandyGrape", 16228128, 0.2, 0.4),
	AppleBrandy("Apple Brandy", "brandyApple", 14985790, 0.2, 0.4),
	PearBrandy("Pear Brandy", "brandyPear", 16696883, 0.2, 0.4),
	ApricotBrandy("Apricot Brandy", "brandyApricot", 13336387, 0.2, 0.4),
	PlumBrandy("Plum Brandy", "brandyPlum", 9511697, 0.2, 0.4),
	CherryBrandy("Cherry Brandy", "brandyCherry", 8588062, 0.2, 0.4),
	ElderberryBrandy("Elderberry Brandy", "brandyElderberry", 12462919, 0.2, 0.4),
	CitrusBrandy("Citrus Brandy", "brandyCitrus", 13336387, 0.2, 0.4),
	FruitBrandy("Fruit Brandy", "brandyFruit", 14985790, 0.2, 0.4),
	Cachaca("Cachaca", "spiritSugarcane", 15331535, 0.1, 0.4),
	Gin("Gin", "spiritGin", 16185078, 0.05, 0.4),
	AppleLiquor("Apple Liquor", "liquorApple", 13421772, 0.05, 0.4),
	PearLiquor("Pear Liquor", "liquorPear", 13421772, 0.05, 0.4),
	CherryLiquor("Cherry Liquor", "liquorCherry", 13421772, 0.05, 0.4),
	ElderberryLiquor("Elderberry Liquor", "liquorElderberry", 13421772, 0.05, 0.4),
	ApricotLiquor("Apricot Liquor", "liquorApricot", 13421772, 0.05, 0.4),
	FruitLiquor("Fruit Liquor", "liquorFruit", 13421772, 0.05, 0.4);

	String name;
	String ident;
	IIcon icon;
	int colour;
	float transparency;
	float abv;

	private Spirit(final String name, final String ident, final int colour, final double transparency, final double abv) {
		this.name = name;
		this.ident = ident;
		this.colour = colour;
		this.transparency = (float) transparency;
		this.abv = (float) abv;
	}

	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public boolean canPlaceIn(final FluidContainer container) {
		return true;
	}

	@Override
	public boolean showInCreative(final FluidContainer container) {
		return container == FluidContainer.Glass;
	}

	@Override
	public IIcon getIcon() {
		return this.icon;
	}

	@Override
	public void registerIcon(final IIconRegister register) {
		this.icon = ExtraTrees.proxy.getIcon(register, "liquids/liquid");
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getIdentifier() {
		return "binnie." + this.ident;
	}

	@Override
	public int getColour() {
		return this.colour;
	}

	@Override
	public FluidStack get(final int amount) {
		return Binnie.Liquid.getLiquidStack(this.getIdentifier(), amount);
	}

	@Override
	public int getTransparency() {
		return (int) (Math.min(1.0, this.transparency + 0.3) * 255.0);
	}

	@Override
	public String getTooltip(final int ratio) {
		return ratio + " Part" + ((ratio > 1) ? "s " : " ") + this.getName();
	}

	@Override
	public int getContainerColour() {
		return this.getColour();
	}

	@Override
	public float getABV() {
		return this.abv;
	}
}
