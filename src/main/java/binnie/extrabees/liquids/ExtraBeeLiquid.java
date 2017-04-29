package binnie.extrabees.liquids;

import binnie.Binnie;
import binnie.core.liquid.FluidContainer;
import binnie.core.liquid.ILiquidType;
import binnie.extrabees.ExtraBees;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.FluidStack;

public enum ExtraBeeLiquid implements ILiquidType {
	ACID("acid", 0xafeb19),
	POISON("poison", 0xeb14eb),
	GLACIAL("liquidnitrogen", 0x96c8c8);

	protected String ident;
	protected IIcon icon;
	protected int colour;

	ExtraBeeLiquid(String ident, int colour) {
		this.ident = ident;
		this.colour = colour;
	}

	@Override
	public IIcon getIcon() {
		return icon;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcon(IIconRegister register) {
		icon = ExtraBees.proxy.getIcon(register, "liquids/" + getIdentifier());
	}

	@Override
	public String getName() {
		return ExtraBees.proxy.localise(toString().toLowerCase());
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
			|| container == FluidContainer.Capsule
			|| container == FluidContainer.Refractory;
	}

	@Override
	public int getContainerColour() {
		return colour;
	}
}
