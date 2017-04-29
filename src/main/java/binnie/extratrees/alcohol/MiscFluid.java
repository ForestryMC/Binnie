// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.alcohol;

import binnie.Binnie;
import net.minecraftforge.fluids.FluidStack;
import binnie.extratrees.ExtraTrees;
import net.minecraft.client.renderer.texture.IIconRegister;
import binnie.core.liquid.FluidContainer;
import net.minecraft.util.IIcon;
import binnie.core.liquid.IFluidType;

public enum MiscFluid implements IFluidType, ICocktailLiquid
{
	CarbonatedWater("Carbonated Water", "water.carbonated", 13421823, 0.10000000149011612),
	TonicWater("Tonic Water", "water.tonic", 13421823, 0.10000000149011612),
	Cream("Carbonated Water", "cream", 15395550, 2.0),
	GingerAle("Ginger Ale", "gingerAle", 16777215, 0.6000000238418579),
	Coffee("Coffee", "coffee", 5910789, 0.30000001192092896),
	SugarSyrup("Simple Syrup", "syrup.simple", 16120049, 0.10000000149011612),
	AgaveNectar("Agave Nectar", "syrup.agave", 13598245, 0.699999988079071),
	GrenadineSyrup("Grenadine Syrup", "syrup.grenadine", 16009573, 0.800000011920929);

	String name;
	String ident;
	IIcon icon;
	int colour;
	float transparency;

	private MiscFluid(final String name, final String ident, final int colour, final double transparency) {
		this.name = name;
		this.ident = ident;
		this.colour = colour;
		this.transparency = (float) transparency;
	}

	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public boolean canPlaceIn(final FluidContainer container) {
		return true;
	}

	@Override
	public boolean showInCreative(final FluidContainer container) {
		return container == FluidContainer.Glass;
	}

	@Override
	public IIcon getIcon() {
		return this.icon;
	}

	@Override
	public void registerIcon(final IIconRegister register) {
		this.icon = ExtraTrees.proxy.getIcon(register, "liquids/liquid");
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
	public int getColor() {
		return this.colour;
	}

	@Override
	public FluidStack get(final int amount) {
		return Binnie.Liquid.getLiquidStack(this.getIdentifier(), amount);
	}

	@Override
	public int getTransparency() {
		return (int) (Math.min(1.0, this.transparency + 0.3) * 255.0);
	}

	@Override
	public String getTooltip(final int ratio) {
		return ratio + " Part" + ((ratio > 1) ? "s " : " ") + this.getName();
	}

	@Override
	public int getContainerColour() {
		return this.getColor();
	}

	@Override
	public float getABV() {
		return 0.0f;
	}
}
