package binnie.extratrees.item;

import binnie.Binnie;
import binnie.core.liquid.FluidContainer;
import binnie.core.liquid.ILiquidType;
import binnie.extratrees.ExtraTrees;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.FluidStack;

public enum ExtraTreeLiquid implements ILiquidType {
	Sap("Sap", "sap", 12481858),
	Resin("Resin", "resin", 13199360),
	Latex("Latex", "latex", 14212041),
	Turpentine("Turpentine", "turpentine", 7951162);

	public String name;

	protected String ident;
	protected IIcon icon;
	protected int color;

	ExtraTreeLiquid(String name, String ident, int color) {
		this.name = "";
		this.name = name;
		this.ident = ident;
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
		return name;
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
