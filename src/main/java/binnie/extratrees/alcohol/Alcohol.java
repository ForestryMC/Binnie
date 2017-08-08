package binnie.extratrees.alcohol;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fluids.FluidStack;

import binnie.Binnie;
import binnie.Constants;
import binnie.core.liquid.FluidContainerType;
import binnie.core.liquid.IFluidType;
import binnie.core.util.I18N;

public enum Alcohol implements IFluidType, ICocktailLiquid {
	Apple("cider.apple", 16432700, 0.3, 0.05){
		@Override
		protected void init() {
			addFementation(Juice.Apple);
		}
	},
	Apricot("wine.apricot", 15781686, 0.3, 0.1){
		@Override
		protected void init() {
			addFementation(Juice.Apricot);
		}
	},
	Banana("wine.banana", 14993485, 0.3, 0.1){
		@Override
		protected void init() {
			addFementation(Juice.Banana);
		}
	},
	Cherry("wine.cherry", 11207702, 0.3, 0.1){
		@Override
		protected void init() {
			addFementation(Juice.Cherry);
		}
	},
	Elderberry("wine.elderberry", 9764865, 0.3, 0.1){
		@Override
		protected void init() {
			addFementation(Juice.Elderberry);
		}
	},
	Peach("cider.peach", 15361563, 0.3, 0.05){
		@Override
		protected void init() {
			addFementation(Juice.Peach);
		}
	},
	Pear("ciderpear", 15061095, 0.3, 0.05){
		@Override
		protected void init() {
			addFementation(Juice.Pear);
		}
	},
	Plum("wine.plum", 12063752, 0.3, 0.1){
		@Override
		protected void init() {
			addFementation(Juice.Plum);
		}
	},
	Carrot("wine.carrot", 16219394, 0.3, 0.1){
		@Override
		protected void init() {
			addFementation(Juice.Carrot);
		}
	},
	WhiteWine("wine.white", 15587989, 0.1, 0.1){
		@Override
		protected void init() {
			addFementation(Juice.WhiteGrape);
		}
	},
	RedWine("wine.red", 7670539, 0.2, 0.1){
		@Override
		protected void init() {
			addFementation(Juice.RedGrape);
		}
	},
	SparklingWine("wine.sparkling", 16709566, 0.1, 0.1),
	Agave("wine.agave", 13938276, 0.2, 0.1),
	Potato("fermented.potatoes", 12028240, 0.8, 0.1){
		@Override
		protected void init() {
			addFementation("cropPotato");
		}
	},
	Citrus("wine.citrus", 16776960, 0.2, 0.1){
		@Override
		protected void init() {
			addFementation(Juice.Lemon);
			addFementation(Juice.Lime);
			addFementation(Juice.Orange);
			addFementation(Juice.Grapefruit);
		}
	},
	Cranberry("wine.cranberry", 11599874, 0.2, 0.1){
		@Override
		protected void init() {
			addFementation(Juice.Cranberry);
		}
	},
	Pineapple("wine.pineapple", 14724150, 0.2, 0.1){
		@Override
		protected void init() {
			addFementation(Juice.Pineapple);
		}
	},
	Tomato("wine.tomato", 12458521, 0.2, 0.1){
		@Override
		protected void init() {
			addFementation(Juice.Tomato);
		}
	},
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

	static {
		for(Alcohol alcohol : values()){
			alcohol.init();
		}
	}

	protected void init(){

	}

	List<String> fermentationLiquid;
	String fermentationSolid;
	String ident;
	int colour;
	float transparency;
	float abv;

	Alcohol(final String ident, final int colour, final double transparency, final double abv) {
		this.fermentationLiquid = new ArrayList<>();
		this.fermentationSolid = "";
		this.ident = ident;
		this.colour = colour;
		this.transparency = (float) transparency;
		this.abv = (float) abv;
	}

	protected void addFementation(final Juice juice) {
		this.fermentationLiquid.add(juice.getIdentifier());
	}

	protected void addFementation(final String oreDict) {
		this.fermentationSolid = oreDict;
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
		return I18N.localise("extratrees.fluid.alcohol." + this.name().toLowerCase());
	}

	@Override
	public String getIdentifier() {
		return "binnie." + this.ident;
	}

	@Override
	public int getColor() {
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
	public boolean canPlaceIn(final FluidContainerType type) {
		return true;
	}

	@Override
	public boolean showInCreative(final FluidContainerType type) {
		return type == FluidContainerType.GLASS;
	}

	@Override
	public int getContainerColor() {
		return this.getColor();
	}

	@Override
	public float getABV() {
		return this.abv;
	}
}
