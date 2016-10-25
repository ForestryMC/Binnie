package binnie.genetics.item;

import binnie.Binnie;
import binnie.core.liquid.FluidContainer;
import binnie.core.liquid.IFluidType;
import binnie.genetics.Genetics;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

public enum GeneticLiquid implements IFluidType {
    GrowthMedium("Growth Medium", "growthMedium", 15460533),
    Bacteria("Bacteria", "bacteria", 14203521),
    BacteriaPoly("Polymerising Bacteria", "bacteriaPoly", 11443396),
    RawDNA("Raw DNA", "dna.raw", 15089129),
    BacteriaVector("Bacteria Vector", "bacteriaVector", 15960958);

    String name;
    String ident;
    //	IIcon icon;
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

//	@Override
//	public IIcon getIcon() {
//		return this.icon;
//	}
//
//	@Override
//	public void registerIcon(final IIconRegister register) {
//		this.icon = Genetics.proxy.getIcon(register, "liquids/" + this.ident);
//	}

    @Override
    public ResourceLocation getFlowing() {
        return new ResourceLocation(Genetics.instance.getModID(), "blocks/liquids/" + this.ident);
    }

    @Override
    public ResourceLocation getStill() {
        return new ResourceLocation(Genetics.instance.getModID(), "blocks/liquids/" + this.ident);
    }

    @Override
    public String getName() {
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
        return Binnie.Liquid.getFluidStack(this.getIdentifier(), amount);
    }

    @Override
    public int getTransparency() {
        return 0;
    }

    @Override
    public boolean canPlaceIn(final FluidContainer container) {
        return this == GeneticLiquid.GrowthMedium || container == FluidContainer.Cylinder;
    }

    @Override
    public boolean showInCreative(final FluidContainer container) {
        return container == FluidContainer.Cylinder;
    }
}
