package binnie.extrabees.liquids;

import binnie.Binnie;
import binnie.Constants;
import binnie.core.liquid.FluidContainerType;
import binnie.core.liquid.ILiquidType;
import binnie.extrabees.ExtraBees;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

public enum ExtraBeeLiquid implements ILiquidType {
	ACID("acid", 11528985),
	POISON("poison", 15406315),
	GLACIAL("liquidnitrogen", 9881800);

	String ident;
	int colour;

	ExtraBeeLiquid(final String ident, final int colour) {
		this.ident = ident;
		this.colour = colour;
	}

	@Override
	public ResourceLocation getFlowing() {
		return new ResourceLocation(Constants.EXTRA_BEES_MOD_ID, "blocks/liquids/" + this.getIdentifier());
	}

	@Override
	public ResourceLocation getStill() {
		return new ResourceLocation(Constants.EXTRA_BEES_MOD_ID, "blocks/liquids/" + this.getIdentifier());
	}

	@Override
	public String getDisplayName() {
		return ExtraBees.proxy.localise(this.toString().toLowerCase());
	}

	@Override
	public String getIdentifier() {
		return this.ident;
	}

	@Override
	public int getColour() {
		return 16777215;
	}

	@Override
	public FluidStack get(final int amount) {
		return Binnie.LIQUID.getFluidStack(this.ident, amount);
	}

	@Override
	public int getTransparency() {
		return 255;
	}

	@Override
	public boolean canPlaceIn(final FluidContainerType container) {
		return true;
	}

	@Override
	public boolean showInCreative(final FluidContainerType container) {
		return container == FluidContainerType.CAN || container == FluidContainerType.CAPSULE || container == FluidContainerType.REFRACTORY;
	}

	@Override
	public int getContainerColour() {
		return this.colour;
	}
}
