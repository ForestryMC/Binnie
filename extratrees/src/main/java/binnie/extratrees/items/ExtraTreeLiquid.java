package binnie.extratrees.items;

import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fluids.FluidStack;

import binnie.core.Constants;
import binnie.core.features.FeatureProvider;
import binnie.core.liquid.FluidContainerType;
import binnie.core.liquid.FluidType;
import binnie.core.liquid.ILiquidDefinition;
import binnie.core.modules.ExtraTreesModuleUIDs;
import binnie.extratrees.ExtraTrees;

@FeatureProvider(containerId = Constants.EXTRA_TREES_MOD_ID, moduleID = ExtraTreesModuleUIDs.CORE)
public enum ExtraTreeLiquid implements ILiquidDefinition {
	SAP("sap", 0xBE7542),
	RESIN("resin", 0xC96800),
	LATEX("latex", 0xD8DBC9),
	TURPENTINE("turpentine", 0x79533A);

	private final FluidType type;

	ExtraTreeLiquid(final String ident, final int color) {
		type = ExtraTrees.instance.registry(ExtraTreesModuleUIDs.CORE).createFluid(ident, String.format("%s.fluid.%s.%s", ExtraTrees.instance.getModId(), "ExtraTreeLiquid", this.name()), color)
			.setShowHandler(fluidType -> fluidType == FluidContainerType.CAN || fluidType == FluidContainerType.CAPSULE)
			.setTransparency(255)
			.setTextures(new ResourceLocation(Constants.EXTRA_TREES_MOD_ID, "blocks/liquids/" + ident));
	}

	@Override
	public FluidStack get(final int amount) {
		return type.stack(amount);
	}

	@Override
	public FluidType getType() {
		return type;
	}
}
