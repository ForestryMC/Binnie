package binnie.extratrees.alcohol;

import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fluids.FluidStack;

import binnie.core.Constants;
import binnie.core.liquid.FluidContainerType;
import binnie.core.liquid.FluidDefinition;
import binnie.core.liquid.IFluidType;

public enum MiscFluid implements IFluidType, ICocktailLiquid {
	CarbonatedWater("Carbonated Water", "water.carbonated", 13421823, 0.10000000149011612),
	TonicWater("Tonic Water", "water.tonic", 13421823, 0.10000000149011612),
	Cream("Carbonated Water", "cream", 15395550, 2.0),
	GingerAle("Ginger Ale", "gingerAle", 16777215, 0.6000000238418579),
	Coffee("Coffee", "coffee", 5910789, 0.30000001192092896),
	SugarSyrup("Simple Syrup", "syrup.simple", 16120049, 0.10000000149011612),
	AgaveNectar("Agave Nectar", "syrup.agave", 13598245, 0.699999988079071),
	GrenadineSyrup("Grenadine Syrup", "syrup.grenadine", 16009573, 0.800000011920929);

	FluidDefinition definition;

	MiscFluid(final String name, final String ident, final int color, final double transparency) {
		definition = new FluidDefinition(ident, name, color)
			.setTransparency(transparency)
			.setTextures(new ResourceLocation(Constants.EXTRA_TREES_MOD_ID, "blocks/liquids/liquid"))
			.setShowHandler((type)-> type == FluidContainerType.GLASS);
	}

	@Override
	public String toString() {
		return definition.toString();
	}

	@Override
	public String getDisplayName() {
		return definition.getDisplayName();
	}

	@Override
	public String getIdentifier() {
		return definition.getIdentifier();
	}

	@Override
	public int getColor() {
		return definition.getColor();
	}

	@Override
	public FluidStack get(final int amount) {
		return definition.get(amount);
	}

	@Override
	public int getTransparency() {
		return definition.getTransparency();
	}

	@Override
	public String getTooltip(final int ratio) {
		return ratio + " Part" + ((ratio > 1) ? "s " : " ") + this.getDisplayName();
	}

	@Override
	public float getABV() {
		return 0.0f;
	}

	@Override
	public FluidDefinition getDefinition() {
		return definition;
	}
}
