package binnie.extratrees.alcohol;

import binnie.Binnie;
import binnie.core.liquid.FluidContainer;
import binnie.core.liquid.IFluidType;
import binnie.extratrees.ExtraTrees;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.FluidStack;

public enum Spirit implements IFluidType, ICocktailLiquid {
	NeutralSpirit("Neutral Spirit", "spiritNeutral", 0xffffff, 0.05, 0.8),
	Vodka("Vodka", "vodka", 0xf4f5f7, 0.05, 0.4),
	WhiteRum("White Rum", "rumWhite", 0xe6e5e4, 0.05, 0.4),
	DarkRum("Dark Rum", "rumDark", 0xa82200, 0.4, 0.4),
	Whiskey("Whiskey", "whiskey", 0xcf6f00, 0.2, 0.4),
	CornWhiskey("Bourbon", "whiskeyCorn", 0x961201, 0.2, 0.4),
	RyeWhiskey("Rye Whiskey", "whiskeyRye", 0xf57328, 0.2, 0.4),
	WheatWhiskey("Wheat Whiskey", "whiskeyWheat", 0xe48612, 0.2, 0.4),
	FortifiedWine("Fortified Wine", "wineFortified", 0xed921f, 0.2, 0.2),
	Tequila("Tequila", "tequila", 0xf5e9c0, 0.05, 0.4),
	Brandy("Brandy", "brandyGrape", 0xf79f20, 0.2, 0.4),
	AppleBrandy("Apple Brandy", "brandyApple", 0xe4aa3e, 0.2, 0.4),
	PearBrandy("Pear Brandy", "brandyPear", 0xfec633, 0.2, 0.4),
	ApricotBrandy("Apricot Brandy", "brandyApricot", 0xcb7f43, 0.2, 0.4),
	PlumBrandy("Plum Brandy", "brandyPlum", 0x912311, 0.2, 0.4),
	CherryBrandy("Cherry Brandy", "brandyCherry", 0x830b1e, 0.2, 0.4),
	ElderberryBrandy("Elderberry Brandy", "brandyElderberry", 0xbe2b47, 0.2, 0.4),
	CitrusBrandy("Citrus Brandy", "brandyCitrus", 0xcb7f43, 0.2, 0.4),
	FruitBrandy("Fruit Brandy", "brandyFruit", 0xe4aa3e, 0.2, 0.4),
	Cachaca("Cachaca", "spiritSugarcane", 0xe9f0cf, 0.1, 0.4),
	Gin("Gin", "spiritGin", 0xf6f6f6, 0.05, 0.4),
	AppleLiquor("Apple Liquor", "liquorApple", 0xcccccc, 0.05, 0.4),
	PearLiquor("Pear Liquor", "liquorPear", 0xcccccc, 0.05, 0.4),
	CherryLiquor("Cherry Liquor", "liquorCherry", 0xcccccc, 0.05, 0.4),
	ElderberryLiquor("Elderberry Liquor", "liquorElderberry", 0xcccccc, 0.05, 0.4),
	ApricotLiquor("Apricot Liquor", "liquorApricot", 0xcccccc, 0.05, 0.4),
	FruitLiquor("Fruit Liquor", "liquorFruit", 0xcccccc, 0.05, 0.4);

	protected String name;
	protected String ident;
	protected IIcon icon;
	protected int colour;
	protected float transparency;
	protected float abv;

	Spirit(String name, String ident, int colour, double transparency, double abv) {
		this.name = name;
		this.ident = ident;
		this.colour = colour;
		this.transparency = (float) transparency;
		this.abv = (float) abv;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public boolean canPlaceIn(FluidContainer container) {
		return true;
	}

	@Override
	public boolean showInCreative(FluidContainer container) {
		return container == FluidContainer.Glass;
	}

	@Override
	public IIcon getIcon() {
		return icon;
	}

	@Override
	public void registerIcon(IIconRegister register) {
		icon = ExtraTrees.proxy.getIcon(register, "liquids/liquid");
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getIdentifier() {
		return "binnie." + ident;
	}

	@Override
	public int getColor() {
		return colour;
	}

	@Override
	public FluidStack get(int amount) {
		return Binnie.Liquid.getLiquidStack(getIdentifier(), amount);
	}

	@Override
	public int getTransparency() {
		return (int) (Math.min(1.0, transparency + 0.3) * 255.0);
	}

	@Override
	public String getTooltip(int ratio) {
		return ratio + " Part" + ((ratio > 1) ? "s " : " ") + getName();
	}

	@Override
	public int getContainerColor() {
		return getColor();
	}

	@Override
	public float getABV() {
		return abv;
	}
}
