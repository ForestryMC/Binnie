package binnie.extrabees.init;

import binnie.extrabees.utils.ExtraBeesResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

/**
 * Created by Elec332 on 12-5-2017.
 */
public final class FluidRegister {

	//Fluids seem unused,
	//TODO: find out what they were used for
	public static Fluid acid, poison, liquid_nitrogen;

	public static void preInitFluids(){
		acid = createFluid("acid");
		poison = createFluid("poison");
		liquid_nitrogen = createFluid("liquidnitrogen");
	}

	private static Fluid createFluid(String name){
		name = name.toLowerCase();
		Fluid ret = new Fluid(name, new ExtraBeesResourceLocation("blocks/liquids/" + name), new ExtraBeesResourceLocation("blocks/liquids/" + name)){

			@Override
			public int getColor() {
				return 16777215;
			}

		};
		FluidRegistry.registerFluid(ret);
		return ret;
	}

/* Please leave here to verify it all works correctly
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
		return new ExtraBeesResourceLocation("blocks/liquids/" + this.getIdentifier());
	}

	@Override
	public ResourceLocation getStill() {
		return new ExtraBeesResourceLocation("blocks/liquids/" + this.getIdentifier());
	}

	@Override
	public String getDisplayName() {
		return ExtraBees.proxy.localise(this.toString().toLowerCase() + ".name");
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
}*/


}
