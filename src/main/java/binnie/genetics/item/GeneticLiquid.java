package binnie.genetics.item;

import binnie.Binnie;
import binnie.core.liquid.FluidContainer;
import binnie.core.liquid.IFluidType;
import binnie.core.util.I18N;
import binnie.genetics.Genetics;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.FluidStack;

public enum GeneticLiquid implements IFluidType {
    GrowthMedium("growthMedium", 0xebe8b5),
    Bacteria("bacteria", 0xd8ba81),
    BacteriaPoly("bacteriaPoly", 0xae9cc4),
    RawDNA("dna.raw", 0xe63de9),
    BacteriaVector("bacteriaVector", 0xf38b7e);

    protected String name;
    protected String ident;
    protected IIcon icon;
    protected int color;
    protected float transparency;

    GeneticLiquid(String name, int color) {
        this.name = name;
        this.ident = name;
        this.color = color;
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
        return I18N.localise("fluid.binnie." + name);
    }

    @Override
    public String getIdentifier() {
        return "binnie." + ident;
    }

    @Override
    public int getColor() {
        return 0xffffff;
    }

    @Override
    public int getContainerColor() {
        return color;
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
