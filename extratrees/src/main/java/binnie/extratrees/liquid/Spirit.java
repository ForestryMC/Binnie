package binnie.extratrees.liquid;

import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fluids.FluidStack;

import binnie.core.Constants;
import binnie.core.liquid.FluidContainerType;
import binnie.core.liquid.FluidType;
import binnie.core.liquid.IFluidDefinition;
import binnie.extratrees.alcohol.CocktailLiquid;
import binnie.extratrees.alcohol.ICocktailIngredient;
import binnie.extratrees.alcohol.ICocktailIngredientProvider;

//TODO:Localise
public enum Spirit implements IFluidDefinition, ICocktailIngredientProvider {
	NeutralSpirit("Neutral Spirit", "spirit.neutral", 16777215, 0.05, 0.8F),
	Vodka("Vodka", "vodka", 16053751, 0.05, 0.4F),
	WhiteRum("White Rum", "rum.white", 15132132, 0.05, 0.4F),
	DarkRum("Dark Rum", "rum.dark", 11018752, 0.4, 0.4F),
	Whiskey("Whiskey", "whiskey", 13594368, 0.2, 0.4F),
	CornWhiskey("Bourbon", "whiskey.corn", 9835009, 0.2, 0.4F),
	RyeWhiskey("Rye Whiskey", "whiskey.rye", 16085800, 0.2, 0.4F),
	WheatWhiskey("Wheat Whiskey", "whiskey.wheat", 14976530, 0.2, 0.4F),
	FortifiedWine("Fortified Wine", "wine.fortified", 15569439, 0.2, 0.2F),
	Tequila("Tequila", "tequila", 16116160, 0.05, 0.4F),
	Brandy("Brandy", "brandy.grape", 16228128, 0.2, 0.4F),
	AppleBrandy("Apple Brandy", "brandy.apple", 14985790, 0.2, 0.4F),
	PearBrandy("Pear Brandy", "brandy.pear", 16696883, 0.2, 0.4F),
	ApricotBrandy("Apricot Brandy", "brandy.apricot", 13336387, 0.2, 0.4F),
	PlumBrandy("Plum Brandy", "brandy.plum", 9511697, 0.2, 0.4F),
	CherryBrandy("Cherry Brandy", "brandy.cherry", 8588062, 0.2, 0.4F),
	ElderberryBrandy("Elderberry Brandy", "brandy.elderberry", 12462919, 0.2, 0.4F),
	CitrusBrandy("Citrus Brandy", "brandy.citrus", 13336387, 0.2, 0.4F),
	FruitBrandy("Fruit Brandy", "brandy.fruit", 14985790, 0.2, 0.4F),
	Cachaca("Cachaca", "spirit.sugarcane", 15331535, 0.1, 0.4F),
	Gin("Gin", "spirit.gin", 16185078, 0.05, 0.4F),
	AppleLiquor("Apple Liquor", "liquor.apple", 13421772, 0.05, 0.4F),
	PearLiquor("Pear Liquor", "liquor.pear", 13421772, 0.05, 0.4F),
	CherryLiquor("Cherry Liquor", "liquor.cherry", 13421772, 0.05, 0.4F),
	ElderberryLiquor("Elderberry Liquor", "liquor.elderberry", 13421772, 0.05, 0.4F),
	ApricotLiquor("Apricot Liquor", "liquor.apricot", 13421772, 0.05, 0.4F),
	FruitLiquor("Fruit Liquor", "liquor.fruit", 13421772, 0.05, 0.4F);

	private final float abv;
	private final FluidType type;
	private final CocktailLiquid cocktailLiquid;

	Spirit(final String name, final String ident, final int color, final double transparency, final float abv) {
		this.abv = abv;
		type = new FluidType(ident, name, color)
			.setTextures(new ResourceLocation(Constants.EXTRA_TREES_MOD_ID, "blocks/liquids/liquid"))
			.setShowHandler(type -> type == FluidContainerType.GLASS)
			.setTransparency(transparency);
		cocktailLiquid = new CocktailLiquid(type, abv);
	}

	@Override
	public String toString() {
		return type.toString();
	}

	@Override
	public FluidStack get(final int amount) {
		return type.get(amount);
	}

	@Override
	public FluidType getType() {
		return type;
	}

	@Override
	public ICocktailIngredient getIngredient() {
		return cocktailLiquid;
	}
}
