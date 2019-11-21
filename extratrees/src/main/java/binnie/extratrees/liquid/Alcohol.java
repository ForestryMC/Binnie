package binnie.extratrees.liquid;

import binnie.core.Constants;
import binnie.core.liquid.FluidContainerType;
import binnie.core.liquid.FluidType;
import binnie.core.liquid.IFluidDefinition;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.alcohol.CocktailLiquid;
import binnie.extratrees.alcohol.ICocktailIngredient;
import binnie.extratrees.alcohol.ICocktailIngredientProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public enum Alcohol implements IFluidDefinition, ICocktailIngredientProvider {
	Apple("cider.apple", 16432700, 0.3, 0.05F) {
		@Override
		protected void init() {
			addFermentation(Juice.Apple);
		}
	},
	Apricot("wine.apricot", 15781686, 0.3, 0.1F) {
		@Override
		protected void init() {
			addFermentation(Juice.Apricot);
		}
	},
	Banana("wine.banana", 14993485, 0.3, 0.1F) {
		@Override
		protected void init() {
			addFermentation(Juice.Banana);
		}
	},
	Cherry("wine.cherry", 11207702, 0.3, 0.1F) {
		@Override
		protected void init() {
			addFermentation(Juice.Cherry);
		}
	},
	Elderberry("wine.elderberry", 9764865, 0.3, 0.1F) {
		@Override
		protected void init() {
			addFermentation(Juice.Elderberry);
		}
	},
	Peach("cider.peach", 15361563, 0.3, 0.05F) {
		@Override
		protected void init() {
			addFermentation(Juice.Peach);
		}
	},
	Pear("ciderpear", 15061095, 0.3, 0.05F) {
		@Override
		protected void init() {
			addFermentation(Juice.Pear);
		}
	},
	Plum("wine.plum", 12063752, 0.3, 0.1F) {
		@Override
		protected void init() {
			addFermentation(Juice.Plum);
		}
	},
	Carrot("wine.carrot", 16219394, 0.3, 0.1F) {
		@Override
		protected void init() {
			addFermentation(Juice.Carrot);
		}
	},
	WhiteWine("wine.white", 15587989, 0.1, 0.1F) {
		@Override
		protected void init() {
			addFermentation(Juice.WhiteGrape);
		}
	},
	RedWine("wine.red", 7670539, 0.2, 0.1F) {
		@Override
		protected void init() {
			addFermentation(Juice.RedGrape);
		}
	},
	SparklingWine("wine.sparkling", 16709566, 0.1, 0.1F),
	Agave("wine.agave", 13938276, 0.2, 0.1F),
	Potato("fermented.potatoes", 12028240, 0.8, 0.1F) {
		@Override
		protected void init() {
			addFermentation("cropPotato");
		}
	},
	Citrus("wine.citrus", 16776960, 0.2, 0.1F) {
		@Override
		protected void init() {
			addFermentation(Juice.Lemon);
			addFermentation(Juice.Lime);
			addFermentation(Juice.Orange);
			addFermentation(Juice.Grapefruit);
		}
	},
	Cranberry("wine.cranberry", 11599874, 0.2, 0.1F) {
		@Override
		protected void init() {
			addFermentation(Juice.Cranberry);
		}
	},
	Pineapple("wine.pineapple", 14724150, 0.2, 0.1F) {
		@Override
		protected void init() {
			addFermentation(Juice.Pineapple);
		}
	},
	Tomato("wine.tomato", 12458521, 0.2, 0.1F) {
		@Override
		protected void init() {
			addFermentation(Juice.Tomato);
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

	private final List<String> fermentationLiquid;
	private final FluidType type;
	private String fermentationSolid;
	private final CocktailLiquid cocktailLiquid;

	Alcohol(final String ident, final int color, final double transparency, float abv) {
		this.fermentationLiquid = new ArrayList<>();
		this.fermentationSolid = "";
		init();
		type = new FluidType(ident, String.format("%s.fluid.%s.%s", ExtraTrees.instance.getModId(), "Alcohol", this.name()), color)
			.setTransparency(transparency)
			.setTextures(new ResourceLocation(Constants.EXTRA_TREES_MOD_ID, "blocks/liquids/liquid"))
			.setPlaceHandler((type) -> type == FluidContainerType.GLASS);
		cocktailLiquid = new CocktailLiquid(type, abv);
	}

	protected void init() {

	}

	@Override
	public FluidType getType() {
		return type;
	}

	@Override
	public ICocktailIngredient getIngredient() {
		return cocktailLiquid;
	}

	public List<String> getFermentationLiquid() {
		return fermentationLiquid;
	}

	protected void addFermentation(final Juice juice) {
		this.fermentationLiquid.add(juice.getType().getIdentifier());
	}

	protected void addFermentation(final String oreDict) {
		this.fermentationSolid = oreDict;
	}

	@Override
	public String toString() {
		return type.getDisplayName();
	}

	@Override
	public FluidStack get(final int amount) {
		return type.get(amount);
	}


}
