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

public enum Juice implements IFluidDefinition, ICocktailIngredientProvider {
	Apple("juice.apple", 16763442, 0.7, "Apple"),
	Apricot("juice.apricot", 16758046, 0.6, "Apricot"),
	Banana("juice.banana", 15324291, 0.6, "Banana"),
	Cherry("juice.cherry", 13044511, 0.6, "Cherry"),
	Elderberry("juice.elderberry", 4204073, 0.6, "Elderberry"),
	Lemon("juice.lemon", 14604882, 0.7, "Lemon"),
	Lime("juice.lime", 12177007, 0.6, "Lime"),
	Orange("juice.orange", 16359983, 0.8, "Orange"),
	Peach("juice.peach", 16434525, 0.7, "Peach"),
	Plum("juice.plum", 10559249, 0.7, "Plum"),
	Carrot("juice.carrot", 16485911, 0.7, "Carrot"),
	Tomato("juice.tomato", 12731438, 0.7, "Tomato"),
	Cranberry("juice.cranberry", 12920629, 0.7, "Cranberry"),
	Grapefruit("juice.grapefruit", 15897173, 0.6, "Grapefruit"),
	Olive("juice.olive", 16042240, 0.6, "Olive"),
	Pineapple("juice.pineapple", 15189319, 0.6, "Pineapple"),
	Pear("juice.pear", 14204773, 0.6, "Pear"),
	WhiteGrape("juice.white.grape", 16507769, 0.6, "WhiteGrape"),
	RedGrape("juice.red.grape", 9775412, 0.6, "RedGrape");

	private String squeezing;
	private final FluidType type;
	private final CocktailLiquid cocktailLiquid;

	Juice(final String ident, final int colour, final double transparency, final String squeezing) {
		this.addSqueezing("crop" + squeezing);

		type = new FluidType(ident, String.format("%s.fluid.juice.%s", ExtraTrees.instance.getModId(), this.name()), colour)
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
		return type.get(amount);
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
}
