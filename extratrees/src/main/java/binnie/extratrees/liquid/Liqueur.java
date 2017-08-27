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
public enum Liqueur implements IFluidDefinition, ICocktailIngredientProvider {
	Almond("Almond", 14966063, 0.3, 0.2F),
	Orange("Orange", 16353536, 0.4, 0.2F),
	Banana("Banana", 16302592, 0.5, 0.2F),
	Chocolate("Chocolate", 12667680, 0.3, 0.2F),
	Mint("Mint", 2737788, 0.4, 0.2F),
	Hazelnut("Hazelnut", 15570987, 0.3, 0.2F),
	Cinnamon("Cinnamon", 15028224, 0.3, 0.2F),
	Coffee("Coffee", 9847577, 0.4, 0.2F),
	Melon("Melon", 11584049, 0.4, 0.2F),
	Anise("Anise", 14344681, 0.3, 0.2F),
	Peach("Peach", 16684384, 0.4, 0.2F),
	Lemon("Lemon", 16311405, 0.4, 0.2F),
	Herbal("Herbal", 16700673, 0.3, 0.2F),
	Cherry("Cherry", 14096641, 0.5, 0.2F),
	Blackcurrant("Blackcurrant", 6962541, 0.5, 0.2F),
	Blackberry("Blackberry", 6837581, 0.5, 0.2F),
	Raspberry("Raspberry", 10158848, 0.5, 0.2F);

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

	private final float abv;
	private final FluidType type;
	private final CocktailLiquid cocktailLiquid;

	Liqueur(final String name, final int colour, final double transparency,float abv) {
		this(name + " Liqueur", "liqueur." + name.toLowerCase(), colour, transparency, abv);
	}

	Liqueur(final String name, final String ident, final int color, final double transparency, float abv) {
		this.abv = abv;
		type = new FluidType(ident, name, color)
			.setTransparency(transparency)
			.setTextures(new ResourceLocation(Constants.EXTRA_TREES_MOD_ID, "blocks/liquids/liquid"))
			.setShowHandler((type)->type == FluidContainerType.GLASS);
		cocktailLiquid = new CocktailLiquid(type, abv);
	}

	private void addFlavour(final String oreDict) {
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
