package binnie.extratrees.alcohol;

import binnie.Binnie;
import binnie.core.liquid.FluidContainer;
import binnie.core.liquid.IFluidType;
import binnie.core.util.I18N;
import binnie.extratrees.ExtraTrees;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public enum Alcohol implements IFluidType, ICocktailLiquid {
	Apple("ciderApple", 0xfabe3c, 0.3, 0.05),
	Apricot("wineApricot", 0xf0cf36, 0.3, 0.1),
	Banana("wineBanana", 0xe4c84d, 0.3, 0.1),
	Cherry("wineCherry", 0xab0416, 0.3, 0.1),
	Elderberry("wineElderberry", 0x950001, 0.3, 0.1),
	Peach("ciderPeach", 0xea661b, 0.3, 0.05),
	Pear("ciderPear", 0xe5d067, 0.3, 0.05),
	Plum("winePlum", 0xb81408, 0.3, 0.1),
	Carrot("wineCarrot", 0xf77d02, 0.3, 0.1),
	WhiteWine("wineWhite", 0xedda95, 0.1, 0.1),
	RedWine("wineRed", 0x750b0b, 0.2, 0.1),
	SparklingWine("wineSparkling", 0xfef7be, 0.1, 0.1),
	Agave("wineAgave", 0xd4ae64, 0.2, 0.1),
	Potato("fermentedPotatoes", 0xb78950, 0.8, 0.1),
	Citrus("wineCitrus", 0xffff00, 0.2, 0.1),
	Cranberry("wineCranberry", 0xb10002, 0.2, 0.1),
	Pineapple("winePineapple", 0xe0ac36, 0.2, 0.1),
	Tomato("wineTomato", 0xbe1a19, 0.2, 0.1),
	Fruit("juice", 0xfabe3c, 0.2, 0.1),
	Ale("beerAle", 0xc63a21, 0.7, 0.05),
	Lager("beerLager", 0xe97c05, 0.7, 0.05),
	WheatBeer("beerWheat", 0xdb6e08, 0.7, 0.05),
	RyeBeer("beerRye", 0xa55827, 0.7, 0.05),
	CornBeer("beerCorn", 0xcca424, 0.7, 0.05),
	Stout("beerStout", 0x592901, 0.8, 0.05),
	Barley("mashGrain", 0xc63a21, 0.9, 0.05),
	Wheat("mashWheat", 0xc63a21, 0.9, 0.05),
	Rye("mashRye", 0xa55827, 0.9, 0.05),
	Corn("mashCorn", 0xcca424, 0.9, 0.05);

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

	protected List<String> fermentationLiquid;
	protected String fermentationSolid;
	protected String ident;
	protected IIcon icon;
	protected int colour;
	protected float transparency;
	protected float abv;

	Alcohol(String ident, int colour, double transparency, double abv) {
		fermentationLiquid = new ArrayList<>();
		fermentationSolid = "";
		this.ident = ident;
		this.colour = colour;
		this.transparency = (float) transparency;
		this.abv = (float) abv;
	}

	private void setFementation(Juice juice) {
		fermentationLiquid.add(juice.getIdentifier());
	}

	private void setFementation(String oreDict) {
		fermentationSolid = oreDict;
	}

	@Override
	public String toString() {
		return getName();
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
		return I18N.localise("extrabees.fluid.alcohol." + name().toLowerCase());
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
	public boolean canPlaceIn(FluidContainer container) {
		return true;
	}

	@Override
	public boolean showInCreative(FluidContainer container) {
		return container == FluidContainer.Glass;
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
