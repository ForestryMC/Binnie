package binnie.extratrees.alcohol;

import binnie.Binnie;
import binnie.core.liquid.FluidContainer;
import binnie.core.liquid.IFluidType;
import binnie.extratrees.ExtraTrees;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.FluidStack;

public enum MiscFluid implements IFluidType, ICocktailLiquid {
	CarbonatedWater("Carbonated Water", "water.carbonated", 0xccccff, 0.10000000149011612),
	TonicWater("Tonic Water", "water.tonic", 0xccccff, 0.10000000149011612),
	Cream("Carbonated Water", "cream", 0xeaeade, 2.0),
	GingerAle("Ginger Ale", "gingerAle", 0xffffff, 0.6000000238418579),
	Coffee("Coffee", "coffee", 0x5a3105, 0.30000001192092896),
	SugarSyrup("Simple Syrup", "syrup.simple", 0xf5f8f1, 0.10000000149011612),
	AgaveNectar("Agave Nectar", "syrup.agave", 0xcf7e25, 0.699999988079071),
	GrenadineSyrup("Grenadine Syrup", "syrup.grenadine", 0xf44965, 0.800000011920929);

	protected String name;
	protected String ident;
	protected IIcon icon;
	protected int colour;
	protected float transparency;

	MiscFluid(String name, String ident, int colour, double transparency) {
		this.name = name;
		this.ident = ident;
		this.colour = colour;
		this.transparency = (float) transparency;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public boolean canPlaceIn(FluidContainer container) {
		return true;
	}

	@Override
	public boolean showInCreative(FluidContainer container) {
		return container == FluidContainer.Glass;
	}

	@Override
	public IIcon getIcon() {
		return icon;
	}

	@Override
	public void registerIcon(IIconRegister register) {
		icon = ExtraTrees.proxy.getIcon(register, "liquids/liquid");
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
	public int getColor() {
		return colour;
	}

	@Override
	public FluidStack get(int amount) {
		return Binnie.Liquid.getLiquidStack(getIdentifier(), amount);
	}

	@Override
	public int getTransparency() {
		return (int) (Math.min(1.0, transparency + 0.3) * 255.0);
	}

	@Override
	public String getTooltip(int ratio) {
		return ratio + " Part" + ((ratio > 1) ? "s " : " ") + getName();
	}

	@Override
	public int getContainerColor() {
		return getColor();
	}

	@Override
	public float getABV() {
		return 0.0f;
	}
}
