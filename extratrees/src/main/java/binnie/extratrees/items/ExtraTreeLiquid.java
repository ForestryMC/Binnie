package binnie.extratrees.items;

import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fluids.FluidStack;

import binnie.core.Constants;
import binnie.core.liquid.FluidContainerType;
import binnie.core.liquid.FluidDefinition;
import binnie.core.liquid.ILiquidType;

public enum ExtraTreeLiquid implements ILiquidType {
	Sap("Sap", "sap", 12481858),
	Resin("Resin", "resin", 13199360),
	Latex("Latex", "latex", 14212041),
	Turpentine("Turpentine", "turpentine", 7951162);

	FluidDefinition definition;

	ExtraTreeLiquid(final String name, final String ident, final int color) {
		definition = new FluidDefinition(ident, name, color)
			.setShowHandler((type)->type == FluidContainerType.CAN || type == FluidContainerType.CAPSULE)
			.setTransparency(255)
			.setTextures(new ResourceLocation(Constants.EXTRA_TREES_MOD_ID, "blocks/liquids/" + ident));
	}

	@Override
	public FluidStack get(final int amount) {
		return definition.get(amount);
	}

	@Override
	public FluidDefinition getDefinition() {
		return definition;
	}
}
