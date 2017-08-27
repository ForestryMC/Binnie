package binnie.extratrees.items;

import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fluids.FluidStack;

import binnie.core.Constants;
import binnie.core.liquid.FluidContainerType;
import binnie.core.liquid.FluidType;
import binnie.core.liquid.ILiquidDefinition;

public enum ExtraTreeLiquid implements ILiquidDefinition {
	Sap("Sap", "sap", 12481858),
	Resin("Resin", "resin", 13199360),
	Latex("Latex", "latex", 14212041),
	Turpentine("Turpentine", "turpentine", 7951162);

	private final FluidType type;

	ExtraTreeLiquid(final String name, final String ident, final int color) {
		type = new FluidType(ident, name, color)
			.setShowHandler((type)->type == FluidContainerType.CAN || type == FluidContainerType.CAPSULE)
			.setTransparency(255)
			.setTextures(new ResourceLocation(Constants.EXTRA_TREES_MOD_ID, "blocks/liquids/" + ident));
	}

	@Override
	public FluidStack get(final int amount) {
		return type.get(amount);
	}

	@Override
	public FluidType getType() {
		return type;
	}
}
