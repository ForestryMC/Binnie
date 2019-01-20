package binnie.extratrees.liquid;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

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
public enum Alcohol implements IFluidDefinition, ICocktailIngredientProvider {
	Apple("cider.apple", 16432700, 0.3, 0.05F) {
		@Override
		public void init() {
			addFementation(Juice.Apple);
		}
	},
	Apricot("wine.apricot", 15781686, 0.3, 0.1F) {
		@Override
		public void init() {
			addFementation(Juice.Apricot);
		}
	},
	Banana("wine.banana", 14993485, 0.3, 0.1F) {
		@Override
		public void init() {
			addFementation(Juice.Banana);
		}
	},
	Cherry("wine.cherry", 11207702, 0.3, 0.1F) {
		@Override
		public void init() {
			addFementation(Juice.Cherry);
		}
	},
	Elderberry("wine.elderberry", 9764865, 0.3, 0.1F) {
		@Override
		public void init() {
			addFementation(Juice.Elderberry);
		}
	},
	Peach("cider.peach", 15361563, 0.3, 0.05F) {
		@Override
		public void init() {
			addFementation(Juice.Peach);
		}
	},
	Pear("ciderpear", 15061095, 0.3, 0.05F) {
		@Override
		public void init() {
			addFementation(Juice.Pear);
		}
	},
	Plum("wine.plum", 12063752, 0.3, 0.1F) {
		@Override
		public void init() {
			addFementation(Juice.Plum);
		}
	},
	Carrot("wine.carrot", 16219394, 0.3, 0.1F) {
		@Override
		public void init() {
			addFementation(Juice.Carrot);
		}
	},
	WhiteWine("wine.white", 15587989, 0.1, 0.1F) {
		@Override
		public void init() {
			addFementation(Juice.WhiteGrape);
		}
	},
	RedWine("wine.red", 7670539, 0.2, 0.1F) {
		@Override
		public void init() {
			addFementation(Juice.RedGrape);
		}
	},
	SparklingWine("wine.sparkling", 16709566, 0.1, 0.1F),
	Agave("wine.agave", 13938276, 0.2, 0.1F),
	Potato("fermented.potatoes", 12028240, 0.8, 0.1F) {
		@Override
		public void init() {
			addFementation("cropPotato");
		}
	},
	Citrus("wine.citrus", 16776960, 0.2, 0.1F) {
		@Override
		public void init() {
			addFementation(Juice.Lemon);
			addFementation(Juice.Lime);
			addFementation(Juice.Orange);
			addFementation(Juice.Grapefruit);
		}
	},
	Cranberry("wine.cranberry", 11599874, 0.2, 0.1F) {
		@Override
		public void init() {
			addFementation(Juice.Cranberry);
		}
	},
	Pineapple("wine.pineapple", 14724150, 0.2, 0.1F) {
		@Override
		public void init() {
			addFementation(Juice.Pineapple);
		}
	},
	Tomato("wine.tomato", 12458521, 0.2, 0.1F) {
		@Override
		public void init() {
			addFementation(Juice.Tomato);
		}
	},
	Fruit("juice", 16432700, 0.2, 0.1F),
	Ale("beer.ale", 12991009, 0.7, 0.05F),
	Lager("beer.lager", 15301637, 0.7, 0.05F),
	WheatBeer("beer.wheat", 14380552, 0.7, 0.05F),
	RyeBeer("beer.rye", 10836007, 0.7, 0.05F),
	CornBeer("beer.corn", 13411364, 0.7, 0.05F),
	Stout("beer.stout", 5843201, 0.8, 0.05F),
	Barley("mash.grain", 12991009, 0.9, 0.05F),
	Wheat("mash.wheat", 12991009, 0.9, 0.05F),
	Rye("mash.rye", 10836007, 0.9, 0.05F),
	Corn("mash.corn", 13411364, 0.9, 0.05F);

	/* Feature */
	private final IFeatureConstructor<FluidType> constructor;
	private final String identifier;
	@Nullable
	private FluidType fluid;

	private final double transparency;
	private final List<String> fermentationLiquid;
	private String fermentationSolid;
	@Nullable
	private CocktailLiquid cocktailLiquid;

	Alcohol(String identifier, int color, double transparency, float abv) {
		this.fermentationLiquid = new ArrayList<>();
		this.fermentationSolid = "";
		this.transparency = transparency;
		this.identifier = identifier;
		constructor = () -> new FluidType(identifier, String.format("%s.fluid.%s.%s", ExtraTrees.instance.getModId(), "Alcohol", this.name()), color)
			.setTransparency(transparency)
			.setTextures(new ResourceLocation(Constants.EXTRA_TREES_MOD_ID, "blocks/liquids/liquid"))
			.setPlaceHandler(type -> type == FluidContainerType.GLASS);
	}

	@SubscribeEvent
	public static void registerFeatures(RegisterFeatureEvent event) {
		event.register(Alcohol.class);
	}

	@Override
	public ICocktailIngredient getIngredient() {
		return cocktailLiquid;
	}

	public List<String> getFermentationLiquid() {
		return fermentationLiquid;
	}

	protected void addFementation(Juice juice) {
		this.fermentationLiquid.add(identifier);
	}

	protected void addFementation(String oreDict) {
		this.fermentationSolid = oreDict;
	}

	@Override
	public String toString() {
		return fluid.getDisplayName();
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
