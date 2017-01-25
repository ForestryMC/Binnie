package binnie.extratrees.alcohol;

import binnie.Binnie;
import binnie.Constants;
import binnie.core.liquid.FluidContainerType;
import binnie.core.liquid.IFluidType;
import binnie.extratrees.ExtraTrees;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public enum Alcohol implements IFluidType, ICocktailLiquid {
	Apple("cider.apple", 16432700, 0.3, 0.05),
	Apricot("wine.apricot", 15781686, 0.3, 0.1),
	Banana("wine.banana", 14993485, 0.3, 0.1),
	Cherry("wine.cherry", 11207702, 0.3, 0.1),
	Elderberry("wine.elderberry", 9764865, 0.3, 0.1),
	Peach("cider.peach", 15361563, 0.3, 0.05),
	Pear("ciderpear", 15061095, 0.3, 0.05),
	Plum("wine.plum", 12063752, 0.3, 0.1),
	Carrot("wine.carrot", 16219394, 0.3, 0.1),
	WhiteWine("wine.white", 15587989, 0.1, 0.1),
	RedWine("wine.red", 7670539, 0.2, 0.1),
	SparklingWine("wine.sparkling", 16709566, 0.1, 0.1),
	Agave("wine.agave", 13938276, 0.2, 0.1),
	Potato("fermented.potatoes", 12028240, 0.8, 0.1),
	Citrus("wine.citrus", 16776960, 0.2, 0.1),
	Cranberry("wine.cranberry", 11599874, 0.2, 0.1),
	Pineapple("wine.pineapple", 14724150, 0.2, 0.1),
	Tomato("wine.tomato", 12458521, 0.2, 0.1),
	Fruit("juice", 16432700, 0.2, 0.1),
	Ale("beer.ale", 12991009, 0.7, 0.05),
	Lager("beer.lager", 15301637, 0.7, 0.05),
	WheatBeer("beer.wheat", 14380552, 0.7, 0.05),
	RyeBeer("beer.rye", 10836007, 0.7, 0.05),
	CornBeer("beer.corn", 13411364, 0.7, 0.05),
	Stout("beer.stout", 5843201, 0.8, 0.05),
	Barley("mash.grain", 12991009, 0.9, 0.05),
	Wheat("mash.wheat", 12991009, 0.9, 0.05),
	Rye("mash.rye", 10836007, 0.9, 0.05),
	Corn("mash.corn", 13411364, 0.9, 0.05);

	List<String> fermentationLiquid;
	String fermentationSolid;
	String ident;
	int colour;
	float transparency;
	float abv;

	private void setFementation(final Juice juice) {
		this.fermentationLiquid.add(juice.getIdentifier());
	}

	private void setFementation(final String oreDict) {
		this.fermentationSolid = oreDict;
	}

	Alcohol(final String ident, final int colour, final double transparency, final double abv) {
		this.fermentationLiquid = new ArrayList<>();
		this.fermentationSolid = "";
		this.ident = ident;
		this.colour = colour;
		this.transparency = (float) transparency;
		this.abv = (float) abv;
	}

	@Override
	public String toString() {
		return this.getDisplayName();
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
		return ExtraTrees.proxy.localise("fluid.alcohol." + this.name().toLowerCase());
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
	public boolean canPlaceIn(final FluidContainerType container) {
		return true;
	}

	@Override
	public boolean showInCreative(final FluidContainerType container) {
		return container == FluidContainerType.GLASS;
	}

	@Override
	public int getContainerColour() {
		return this.getColour();
	}

	@Override
	public float getABV() {
		return this.abv;
	}

	static {
		Alcohol.Apple.setFementation(Juice.Apple);
		Alcohol.Apricot.setFementation(Juice.Apricot);
		Alcohol.Banana.setFementation(Juice.Banana);
		Alcohol.Cherry.setFementation(Juice.Cherry);
		Alcohol.Elderberry.setFementation(Juice.Elderberry);
		Alcohol.Peach.setFementation(Juice.Peach);
		Alcohol.Pear.setFementation(Juice.Pear);
		Alcohol.Plum.setFementation(Juice.Plum);
		Alcohol.Carrot.setFementation(Juice.Carrot);
		Alcohol.WhiteWine.setFementation(Juice.WhiteGrape);
		Alcohol.RedWine.setFementation(Juice.RedGrape);
		Alcohol.Citrus.setFementation(Juice.Lemon);
		Alcohol.Citrus.setFementation(Juice.Lime);
		Alcohol.Citrus.setFementation(Juice.Orange);
		Alcohol.Citrus.setFementation(Juice.Grapefruit);
		Alcohol.Tomato.setFementation(Juice.Tomato);
		Alcohol.Cranberry.setFementation(Juice.Cranberry);
		Alcohol.Pineapple.setFementation(Juice.Pineapple);
		Alcohol.Potato.setFementation("cropPotato");
	}
}
