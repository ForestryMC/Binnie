package binnie.extratrees.liquid;

import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fluids.FluidStack;

import binnie.core.Constants;
import binnie.core.features.FeatureProvider;
import binnie.core.liquid.FluidContainerType;
import binnie.core.liquid.FluidType;
import binnie.core.liquid.IFluidDefinition;
import binnie.core.modules.ExtraTreesModuleUIDs;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.alcohol.CocktailLiquid;
import binnie.extratrees.alcohol.ICocktailIngredient;
import binnie.extratrees.alcohol.ICocktailIngredientProvider;

@FeatureProvider(containerId = Constants.EXTRA_TREES_MOD_ID, moduleID = ExtraTreesModuleUIDs.ALCOHOL)
public enum Juice implements IFluidDefinition, ICocktailIngredientProvider {
	Apple("juice.apple", 16763442, 0.7, "Apple", "Apple"),
	Apricot("juice.apricot", 16758046, 0.6, "Apricot", "Apricot"),
	Banana("juice.banana", 15324291, 0.6, "Banana", "Banana"),
	Cherry("juice.cherry", 13044511, 0.6, "Cherry", "Cherry"),
	Elderberry("juice.elderberry", 4204073, 0.6, "Elderberry", "Elderberry"),
	Lemon("juice.lemon", 14604882, 0.7, "Lemon", "Lemon"),
	Lime("juice.lime", 12177007, 0.6, "Lime", "Lime"),
	Orange("juice.orange", 16359983, 0.8, "Orange", "Orange"),
	Peach("juice.peach", 16434525, 0.7, "Peach", "Peach"),
	Plum("juice.plum", 10559249, 0.7, "Plum", "Plum"),
	Carrot("juice.carrot", 16485911, 0.7, "Carrot", "Carrot"),
	Tomato("juice.tomato", 12731438, 0.7, "Tomato", "Tomato"),
	Cranberry("juice.cranberry", 12920629, 0.7, "Cranberry", "Cranberry"),
	Grapefruit("juice.grapefruit", 15897173, 0.6, "Grapefruit", "Grapefruit"),
	Olive("juice.olive", 16042240, 0.6, "Olive", "Olive"),
	Pineapple("juice.pineapple", 15189319, 0.6, "Pineapple", "Pineapple"),
	Pear("juice.pear", 14204773, 0.6, "Pear", "Pear"),
	WhiteGrape("juice.white.grape", 16507769, 0.6, "WhiteGrape", "Grape"),
	RedGrape("juice.red.grape", 9775412, 0.6, "RedGrape", "Grape");

	private String squeezing;
	private String oreDict;
	private final FluidType type;
	private final CocktailLiquid cocktailLiquid;

	Juice(final String ident, final int colour, final double transparency, final String squeezing, String oreDict) {
		this.addSqueezing("crop" + squeezing);
		this.oreDict = "food" + oreDict + "juice";

		type = ExtraTrees.instance.registry(ExtraTreesModuleUIDs.ALCOHOL).createFluid(ident, String.format("%s.fluid.%s.%s", ExtraTrees.instance.getModId(), "Juice", this.name()), colour)
			.setTransparency(transparency)
			.setTextures(new ResourceLocation(Constants.EXTRA_TREES_MOD_ID, "blocks/liquids/liquid"))
			.setShowHandler(type -> type == FluidContainerType.GLASS);
		cocktailLiquid = new CocktailLiquid(type, 0.0F);
	}

	private void addSqueezing(final String oreDict) {
		this.squeezing = oreDict;
	}

	@Override
	public String toString() {
		return type.toString();
	}

	@Override
	public FluidStack get(final int amount) {
		return type.stack(amount);
	}

	@Override
	public ICocktailIngredient getIngredient() {
		return cocktailLiquid;
	}

	@Override
	public FluidType getType() {
		return type;
	}

	public String getSqueezing() {
		return squeezing;
	}

	public String getOreDict() {
		return oreDict;
	}
}
