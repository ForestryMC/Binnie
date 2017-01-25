package binnie.extratrees.alcohol;

import binnie.Binnie;
import binnie.Constants;
import binnie.core.liquid.FluidContainerType;
import binnie.core.liquid.IFluidType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

public enum Spirit implements IFluidType, ICocktailLiquid {
	NeutralSpirit("Neutral Spirit", "spirit.neutral", 16777215, 0.05, 0.8),
	Vodka("Vodka", "vodka", 16053751, 0.05, 0.4),
	WhiteRum("White Rum", "rum.white", 15132132, 0.05, 0.4),
	DarkRum("Dark Rum", "rum.dark", 11018752, 0.4, 0.4),
	Whiskey("Whiskey", "whiskey", 13594368, 0.2, 0.4),
	CornWhiskey("Bourbon", "whiskey.corn", 9835009, 0.2, 0.4),
	RyeWhiskey("Rye Whiskey", "whiskey.rye", 16085800, 0.2, 0.4),
	WheatWhiskey("Wheat Whiskey", "whiskey.wheat", 14976530, 0.2, 0.4),
	FortifiedWine("Fortified Wine", "wine.fortified", 15569439, 0.2, 0.2),
	Tequila("Tequila", "tequila", 16116160, 0.05, 0.4),
	Brandy("Brandy", "brandy.grape", 16228128, 0.2, 0.4),
	AppleBrandy("Apple Brandy", "brandy.apple", 14985790, 0.2, 0.4),
	PearBrandy("Pear Brandy", "brandy.pear", 16696883, 0.2, 0.4),
	ApricotBrandy("Apricot Brandy", "brandy.apricot", 13336387, 0.2, 0.4),
	PlumBrandy("Plum Brandy", "brandy.plum", 9511697, 0.2, 0.4),
	CherryBrandy("Cherry Brandy", "brandy.cherry", 8588062, 0.2, 0.4),
	ElderberryBrandy("Elderberry Brandy", "brandy.elderberry", 12462919, 0.2, 0.4),
	CitrusBrandy("Citrus Brandy", "brandy.citrus", 13336387, 0.2, 0.4),
	FruitBrandy("Fruit Brandy", "brandy.fruit", 14985790, 0.2, 0.4),
	Cachaca("Cachaca", "spirit.sugarcane", 15331535, 0.1, 0.4),
	Gin("Gin", "spirit.gin", 16185078, 0.05, 0.4),
	AppleLiquor("Apple Liquor", "liquor.apple", 13421772, 0.05, 0.4),
	PearLiquor("Pear Liquor", "liquor.pear", 13421772, 0.05, 0.4),
	CherryLiquor("Cherry Liquor", "liquor.cherry", 13421772, 0.05, 0.4),
	ElderberryLiquor("Elderberry Liquor", "liquor.elderberry", 13421772, 0.05, 0.4),
	ApricotLiquor("Apricot Liquor", "liquor.apricot", 13421772, 0.05, 0.4),
	FruitLiquor("Fruit Liquor", "liquor.fruit", 13421772, 0.05, 0.4);

	String name;
	String ident;
	int colour;
	float transparency;
	float abv;

	Spirit(final String name, final String ident, final int colour, final double transparency, final double abv) {
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
	public boolean canPlaceIn(final FluidContainerType container) {
		return true;
	}

	@Override
	public boolean showInCreative(final FluidContainerType container) {
		return container == FluidContainerType.GLASS;
	}

	@Override
	public ResourceLocation getFlowing() {
		return new ResourceLocation(Constants.EXTRA_TREES_MOD_ID, "blocks/liquids/liquid");
	}

	@Override
	public ResourceLocation getStill() {
		return new ResourceLocation(Constants.EXTRA_TREES_MOD_ID, "blocks/liquids/liquid");
	}

	@Override
	public String getDisplayName() {
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
		return Binnie.LIQUID.getFluidStack(this.getIdentifier(), amount);
	}

	@Override
	public int getTransparency() {
		return (int) (Math.min(1.0, this.transparency + 0.3) * 255.0);
	}

	@Override
	public String getTooltip(final int ratio) {
		return ratio + " Part" + ((ratio > 1) ? "s " : " ") + this.getDisplayName();
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
