package binnie.extratrees.liquid;

import binnie.extratrees.ExtraTrees;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fluids.FluidStack;

import binnie.core.Constants;
import binnie.core.liquid.FluidContainerType;
import binnie.core.liquid.FluidType;
import binnie.core.liquid.IFluidDefinition;
import binnie.extratrees.alcohol.CocktailLiquid;
import binnie.extratrees.alcohol.ICocktailIngredient;
import binnie.extratrees.alcohol.ICocktailIngredientProvider;

public enum Liqueur implements IFluidDefinition, ICocktailIngredientProvider {
	Almond("liqueur.almond", 14966063, 0.3, 0.2F),
	Orange("liqueur.orange", 16353536, 0.4, 0.2F),
	Banana("liqueur.banana", 16302592, 0.5, 0.2F),
	Chocolate("liqueur.chocolate", 12667680, 0.3, 0.2F),
	Mint("liqueur.mint", 2737788, 0.4, 0.2F),
	Hazelnut("liqueur.hazelnut", 15570987, 0.3, 0.2F),
	Cinnamon("liqueur.cinnamon", 15028224, 0.3, 0.2F),
	Coffee("liqueur.coffee", 9847577, 0.4, 0.2F),
	Melon("liqueur.melon", 11584049, 0.4, 0.2F),
	Anise("liqueur.anise", 14344681, 0.3, 0.2F),
	Peach("liqueur.peach", 16684384, 0.4, 0.2F),
	Lemon("liqueur.lemon", 16311405, 0.4, 0.2F),
	Herbal("liqueur.herbal", 16700673, 0.3, 0.2F),
	Cherry("liqueur.cherry", 14096641, 0.5, 0.2F),
	Blackcurrant("liqueur.blackcurrant", 6962541, 0.5, 0.2F),
	Blackberry("liqueur.blackberry", 6837581, 0.5, 0.2F),
	Raspberry("liqueur.raspberry", 10158848, 0.5, 0.2F);

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

	Liqueur(final String ident, final int color, final double transparency, float abv) {
		this.abv = abv;
		type = new FluidType(ident, String.format("%s.fluid.%s.%s", ExtraTrees.instance.getModId(), this.getClass().getSimpleName(), this.name()), color)
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
