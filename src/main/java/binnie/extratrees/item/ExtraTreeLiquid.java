package binnie.extratrees.item;

import binnie.Binnie;
import binnie.core.liquid.FluidContainer;
import binnie.core.liquid.ILiquidType;
import binnie.core.util.I18N;
import binnie.extratrees.ExtraTrees;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.FluidStack;

public enum ExtraTreeLiquid implements ILiquidType {
    Sap("sap", 0xbe7542),
    Resin("resin", 0xc96800),
    Latex("latex", 0xd8dbc9),
    Turpentine("turpentine", 0x79533a);

    public String name;

    protected String ident;
    protected IIcon icon;
    protected int color;

    ExtraTreeLiquid(String name, int color) {
        this.name = name;
        this.ident = name;
        this.color = color;
    }

    @Override
    public IIcon getIcon() {
        return icon;
    }

    @Override
    public void registerIcon(IIconRegister register) {
        icon = ExtraTrees.proxy.getIcon(register, "liquids/" + getIdentifier());
    }

    @Override
    public String getName() {
        return I18N.localise("extratrees.fluid." + name);
    }

    @Override
    public String getIdentifier() {
        return ident;
    }

    @Override
    public int getColor() {
        return 0xffffff;
    }

    @Override
    public FluidStack get(int amount) {
        return Binnie.Liquid.getLiquidStack(ident, amount);
    }

    @Override
    public int getTransparency() {
        return 255;
    }

    @Override
    public boolean canPlaceIn(FluidContainer container) {
        return true;
    }

    @Override
    public boolean showInCreative(FluidContainer container) {
        return container == FluidContainer.Bucket
                || container == FluidContainer.Can
                || container == FluidContainer.Capsule;
    }

    @Override
    public int getContainerColor() {
        return color;
    }
}
