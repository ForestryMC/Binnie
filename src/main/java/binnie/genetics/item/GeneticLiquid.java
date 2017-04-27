package binnie.genetics.item;

import binnie.Binnie;
import binnie.core.liquid.FluidContainer;
import binnie.core.liquid.IFluidType;
import binnie.genetics.Genetics;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.FluidStack;

public enum GeneticLiquid implements IFluidType {
	GrowthMedium("Growth Medium", "growthMedium", 15460533),
	Bacteria("Bacteria", "bacteria", 14203521),
	BacteriaPoly("Polymerising Bacteria", "bacteriaPoly", 11443396),
	RawDNA("Raw DNA", "dna.raw", 15089129),
	BacteriaVector("Bacteria Vector", "bacteriaVector", 15960958);

	protected String name;
	protected String ident;
	protected IIcon icon;
	protected int colour;
	protected float transparency;

	GeneticLiquid(String name, String ident, int colour) {
		this.name = name;
		this.ident = ident;
		this.colour = colour;
		transparency = 1.0f;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public IIcon getIcon() {
		return icon;
	}

	@Override
	public void registerIcon(IIconRegister register) {
		icon = Genetics.proxy.getIcon(register, "liquids/" + ident);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getIdentifier() {
		return "binnie." + ident;
	}

	@Override
	public int getColour() {
		return 0xffffff;
	}

	@Override
	public int getContainerColour() {
		return colour;
	}

	@Override
	public FluidStack get(int amount) {
		return Binnie.Liquid.getLiquidStack(getIdentifier(), amount);
	}

	@Override
	public int getTransparency() {
		return 0;
	}

	@Override
	public boolean canPlaceIn(FluidContainer container) {
		return this == GeneticLiquid.GrowthMedium || container == FluidContainer.Cylinder;
	}

	@Override
	public boolean showInCreative(FluidContainer container) {
		return container == FluidContainer.Cylinder;
	}
}
