package binnie.extratrees.liquid;

import javax.annotation.Nullable;

import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import binnie.core.Constants;
import binnie.core.features.FeatureType;
import binnie.core.features.IFeatureConstructor;
import binnie.core.features.RegisterFeatureEvent;
import binnie.core.liquid.FluidContainerType;
import binnie.core.liquid.FluidType;
import binnie.core.liquid.IFluidDefinition;
import binnie.core.modules.ExtraTreesModuleUIDs;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.alcohol.CocktailLiquid;
import binnie.extratrees.alcohol.ICocktailIngredient;
import binnie.extratrees.alcohol.ICocktailIngredientProvider;

@Mod.EventBusSubscriber(modid = Constants.EXTRA_TREES_MOD_ID)
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

	/* Feature */
	private final IFeatureConstructor<FluidType> constructor;
	private final String identifier;
	@Nullable
	private FluidType fluid;

	private final float abv;
	@Nullable
	private CocktailLiquid cocktailLiquid;

	Liqueur(String identifier, int color, double transparency, float abv) {
		this.abv = abv;
		this.identifier = identifier;
		constructor = () -> new FluidType(identifier, String.format("%s.fluid.%s.%s", ExtraTrees.instance.getModId(), "Liqueur", this.name()), color)
			.setTransparency(transparency)
			.setTextures(new ResourceLocation(Constants.EXTRA_TREES_MOD_ID, "blocks/liquids/liquid"))
			.setShowHandler((type) -> type == FluidContainerType.GLASS);
	}

	@SubscribeEvent
	public static void registerFeatures(RegisterFeatureEvent event) {
		event.register(Liqueur.class);
	}

	private void addFlavour(String oreDict) {
	}

	@Override
	public String toString() {
		return fluid.toString();
	}

	@Override
	public ICocktailIngredient getIngredient() {
		if (cocktailLiquid != null) {
			return cocktailLiquid;
		} else {
			throw new IllegalStateException("Called feature getter method before content creation was called in the pre init.");
		}
	}

	/* Feature */
	@Override
	public String getIdentifier() {
		return identifier;
	}

	@Override
	public IFeatureConstructor<FluidType> getConstructor() {
		return constructor;
	}

	@Override
	public boolean hasFluid() {
		return fluid != null;
	}

	@Nullable
	@Override
	public FluidType getFluid() {
		return fluid;
	}

	@Override
	public void setFluid(FluidType fluid) {
		this.fluid = fluid;
		cocktailLiquid = new CocktailLiquid(this.fluid, 0.0F);
	}

	@Override
	public FeatureType getType() {
		return FeatureType.FLUID;
	}

	@Override
	public String getModId() {
		return Constants.EXTRA_TREES_MOD_ID;
	}

	@Override
	public String getModuleId() {
		return ExtraTreesModuleUIDs.ALCOHOL;
	}
}
