package binnie.extratrees.item;

import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fluids.FluidStack;

import binnie.core.Binnie;
import binnie.core.Constants;
import binnie.core.liquid.FluidContainerType;
import binnie.core.liquid.ILiquidType;

public enum ExtraTreeLiquid implements ILiquidType {
	Sap("Sap", "sap", 12481858),
	Resin("Resin", "resin", 13199360),
	Latex("Latex", "latex", 14212041),
	Turpentine("Turpentine", "turpentine", 7951162);

	public String name;
	String ident;
	int colour;

	ExtraTreeLiquid(final String name, final String ident, final int colour) {
		this.name = "";
		this.name = name;
		this.ident = ident;
		this.colour = colour;
	}

	@Override
	public ResourceLocation getStill() {
		return new ResourceLocation(Constants.EXTRA_TREES_MOD_ID, "blocks/liquids/" + this.getIdentifier());
	}

	@Override
	public ResourceLocation getFlowing() {
		return new ResourceLocation(Constants.EXTRA_TREES_MOD_ID, "blocks/liquids/" + this.getIdentifier());
	}

	@Override
	public String getDisplayName() {
		return this.name;
	}

	@Override
	public String getIdentifier() {
		return this.ident;
	}

	@Override
	public int getColor() {
		return 16777215;
	}

	@Override
	public FluidStack get(final int i) {
		return Binnie.LIQUID.getFluidStack(this.ident, i);
	}

	@Override
	public int getTransparency() {
		return 255;
	}

	@Override
	public boolean canPlaceIn(final FluidContainerType type) {
		return true;
	}

	@Override
	public boolean showInCreative(final FluidContainerType type) {
		return type == FluidContainerType.CAN || type == FluidContainerType.CAPSULE;
	}

	@Override
	public int getContainerColor() {
		return this.colour;
	}
}
