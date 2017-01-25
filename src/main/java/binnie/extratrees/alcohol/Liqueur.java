package binnie.extratrees.alcohol;

import binnie.Binnie;
import binnie.Constants;
import binnie.core.liquid.FluidContainerType;
import binnie.core.liquid.IFluidType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

public enum Liqueur implements IFluidType, ICocktailLiquid {
	Almond("Almond", 14966063, 0.3, 0.2),
	Orange("Orange", 16353536, 0.4, 0.2),
	Banana("Banana", 16302592, 0.5, 0.2),
	Chocolate("Chocolate", 12667680, 0.3, 0.2),
	Mint("Mint", 2737788, 0.4, 0.2),
	Hazelnut("Hazelnut", 15570987, 0.3, 0.2),
	Cinnamon("Cinnamon", 15028224, 0.3, 0.2),
	Coffee("Coffee", 9847577, 0.4, 0.2),
	Melon("Melon", 11584049, 0.4, 0.2),
	Anise("Anise", 14344681, 0.3, 0.2),
	Peach("Peach", 16684384, 0.4, 0.2),
	Lemon("Lemon", 16311405, 0.4, 0.2),
	Herbal("Herbal", 16700673, 0.3, 0.2),
	Cherry("Cherry", 14096641, 0.5, 0.2),
	Blackcurrant("Blackcurrant", 6962541, 0.5, 0.2),
	Blackberry("Blackberry", 6837581, 0.5, 0.2),
	Raspberry("Raspberry", 10158848, 0.5, 0.2);

	String name;
	String ident;
	int colour;
	float transparency;
	float abv;

	private void addFlavour(final String oreDict) {
	}

	Liqueur(final String name, final int colour, final double transparency, final double abv) {
		this(name + " Liqueur", "liqueur." + name.toLowerCase(), colour, transparency, abv);
	}

	Liqueur(final String name, final String ident, final int colour, final double transparency, final double abv) {
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

	static {
		Liqueur.Almond.addFlavour("cropAlmond");
		Liqueur.Orange.addFlavour("cropOrange");
		Liqueur.Banana.addFlavour("cropBanana");
		Liqueur.Chocolate.addFlavour("cropCocoa");
		Liqueur.Mint.addFlavour("cropMint");
		Liqueur.Hazelnut.addFlavour("cropHazelnut");
		Liqueur.Cinnamon.addFlavour("cropCinnamon");
		Liqueur.Coffee.addFlavour("cropCoffee");
		Liqueur.Melon.addFlavour("cropMelon");
		Liqueur.Anise.addFlavour("cropAnise");
		Liqueur.Peach.addFlavour("cropPeach");
		Liqueur.Lemon.addFlavour("cropLemon");
		Liqueur.Herbal.addFlavour("cropHerbal");
		Liqueur.Cherry.addFlavour("cropCherry");
		Liqueur.Blackcurrant.addFlavour("cropBlackcurrant");
		Liqueur.Blackberry.addFlavour("cropBlackberry");
		Liqueur.Raspberry.addFlavour("cropRaspberry");
	}
}
