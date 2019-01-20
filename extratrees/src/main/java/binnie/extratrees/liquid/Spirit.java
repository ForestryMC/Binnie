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
public enum Spirit implements IFluidDefinition, ICocktailIngredientProvider {
	NeutralSpirit("spirit.neutral", 16777215, 0.05, 0.8F),
	Vodka("vodka", 16053751, 0.05, 0.4F),
	WhiteRum("rum.white", 15132132, 0.05, 0.4F),
	DarkRum("rum.dark", 11018752, 0.4, 0.4F),
	Whiskey("whiskey", 13594368, 0.2, 0.4F),
	CornWhiskey("whiskey.corn", 9835009, 0.2, 0.4F),
	RyeWhiskey("whiskey.rye", 16085800, 0.2, 0.4F),
	WheatWhiskey("whiskey.wheat", 14976530, 0.2, 0.4F),
	FortifiedWine("wine.fortified", 15569439, 0.2, 0.2F),
	Tequila("tequila", 16116160, 0.05, 0.4F),
	Brandy("brandy.grape", 16228128, 0.2, 0.4F),
	AppleBrandy("brandy.apple", 14985790, 0.2, 0.4F),
	PearBrandy("brandy.pear", 16696883, 0.2, 0.4F),
	ApricotBrandy("brandy.apricot", 13336387, 0.2, 0.4F),
	PlumBrandy("brandy.plum", 9511697, 0.2, 0.4F),
	CherryBrandy("brandy.cherry", 8588062, 0.2, 0.4F),
	ElderberryBrandy("brandy.elderberry", 12462919, 0.2, 0.4F),
	CitrusBrandy("brandy.citrus", 13336387, 0.2, 0.4F),
	FruitBrandy("brandy.fruit", 14985790, 0.2, 0.4F),
	Cachaca("spirit.sugarcane", 15331535, 0.1, 0.4F),
	Gin("spirit.gin", 16185078, 0.05, 0.4F),
	AppleLiquor("liquor.apple", 13421772, 0.05, 0.4F),
	PearLiquor("liquor.pear", 13421772, 0.05, 0.4F),
	CherryLiquor("liquor.cherry", 13421772, 0.05, 0.4F),
	ElderberryLiquor("liquor.elderberry", 13421772, 0.05, 0.4F),
	ApricotLiquor("liquor.apricot", 13421772, 0.05, 0.4F),
	FruitLiquor("liquor.fruit", 13421772, 0.05, 0.4F);

	/* Feature */
	private final IFeatureConstructor<FluidType> constructor;
	private final String identifier;
	@Nullable
	private FluidType fluid;

	private final float abv;
	@Nullable
	private CocktailLiquid cocktailLiquid;

	Spirit(String identifier, int color, double transparency, float abv) {
		this.identifier = identifier;
		this.abv = abv;
		constructor = () -> new FluidType(identifier, String.format("%s.fluid.%s.%s", ExtraTrees.instance.getModId(), "Spirit", this.name()), color)
			.setTextures(new ResourceLocation(Constants.EXTRA_TREES_MOD_ID, "blocks/liquids/liquid"))
			.setShowHandler(type -> type == FluidContainerType.GLASS)
			.setTransparency(transparency)
			.setFlammable(true);
	}

	@SubscribeEvent
	public static void registerFeatures(RegisterFeatureEvent event) {
		event.register(Spirit.class);
	}

	@Override
	public String toString() {
		return fluid().toString();
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
		if (abv > 0.5) {
			fluid.setFlammability((int) (abv * 100));
		}
		cocktailLiquid = new CocktailLiquid(fluid, abv);
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
