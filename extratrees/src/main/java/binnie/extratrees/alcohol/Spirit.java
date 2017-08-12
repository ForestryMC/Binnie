package binnie.extratrees.alcohol;

import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fluids.FluidStack;

import binnie.core.Constants;
import binnie.core.liquid.FluidContainerType;
import binnie.core.liquid.FluidDefinition;
import binnie.core.liquid.IFluidType;

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

	float abv;
	FluidDefinition definition;

	Spirit(final String name, final String ident, final int color, final double transparency, final double abv) {
		this.abv = (float) abv;
		definition = new FluidDefinition(ident, name, color)
			.setTextures(new ResourceLocation(Constants.EXTRA_TREES_MOD_ID, "blocks/liquids/liquid"))
			.setShowHandler(type -> type == FluidContainerType.GLASS)
			.setTransparency(transparency);
	}

	@Override
	public String toString() {
		return definition.toString();
	}

	@Override
	public String getDisplayName() {
		return definition.getDisplayName();
	}

	@Override
	public String getIdentifier() {
		return definition.getIdentifier();
	}

	@Override
	public int getColor() {
		return definition.getColor();
	}

	@Override
	public FluidStack get(final int amount) {
		return definition.get(amount);
	}

	@Override
	public int getTransparency() {
		return definition.getTransparency();
	}

	@Override
	public String getTooltip(final int ratio) {
		return ratio + " Part" + ((ratio > 1) ? "s " : " ") + definition.getDisplayName();
	}
	@Override
	public float getABV() {
		return this.abv;
	}

	@Override
	public FluidDefinition getDefinition() {
		return definition;
	}
}
