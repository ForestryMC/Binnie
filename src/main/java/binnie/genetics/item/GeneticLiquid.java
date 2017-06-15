package binnie.genetics.item;

import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fluids.FluidStack;

import binnie.Binnie;
import binnie.core.liquid.FluidContainerType;
import binnie.core.liquid.IFluidType;
import binnie.genetics.Genetics;

public enum GeneticLiquid implements IFluidType {
	GrowthMedium("Growth Medium", "growth.medium", 15460533),
	Bacteria("Bacteria", "bacteria", 14203521),
	BacteriaPoly("Polymerising Bacteria", "bacteria.poly", 11443396),
	RawDNA("Raw DNA", "dna.raw", 15089129),
	BacteriaVector("Bacteria Vector", "bacteria.vector", 15960958);

	String name;
	String ident;
	//IIcon icon;
	int colour;
	float transparency;

	GeneticLiquid(final String name, final String ident, final int colour) {
		this.name = name;
		this.ident = ident;
		this.colour = colour;
		this.transparency = 1.0f;
	}

	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public ResourceLocation getFlowing() {
		return new ResourceLocation(Genetics.instance.getModID(), "blocks/liquids/" + this.ident.replace(".", "_"));
	}

	@Override
	public ResourceLocation getStill() {
		return new ResourceLocation(Genetics.instance.getModID(), "blocks/liquids/" + this.ident.replace(".", "_"));
	}

	@Override
	public String getDisplayName() {
		return this.name;
	}

	@Override
	public String getIdentifier() {
		return "binnie." + this.ident;
	}

	@Override
	public int getColour() {
		return 16777215;
	}

	@Override
	public int getContainerColour() {
		return this.colour;
	}

	@Override
	public FluidStack get(final int amount) {
		return Binnie.LIQUID.getFluidStack(this.getIdentifier(), amount);
	}

	@Override
	public int getTransparency() {
		return 0;
	}

	@Override
	public boolean canPlaceIn(final FluidContainerType container) {
		return this == GeneticLiquid.GrowthMedium || container == FluidContainerType.CYLINDER;
	}

	@Override
	public boolean showInCreative(final FluidContainerType container) {
		return container == FluidContainerType.CYLINDER;
	}
}
