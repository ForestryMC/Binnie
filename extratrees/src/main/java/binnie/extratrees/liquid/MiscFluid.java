package binnie.extratrees.liquid;

import javax.annotation.Nullable;

import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fluids.FluidStack;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import binnie.core.Constants;
import binnie.core.features.FeatureType;
import binnie.core.features.IFeatureConstructor;
import binnie.core.features.RegisterFeatureEvent;
import binnie.core.liquid.FluidContainerType;
import binnie.core.liquid.FluidType;
import binnie.core.liquid.IFluidDefinition;
import binnie.core.modules.ExtraTreesModuleUIDs;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.alcohol.ICocktailLiquid;

@Mod.EventBusSubscriber(modid = Constants.CORE_MOD_ID)
public enum MiscFluid implements IFluidDefinition, ICocktailLiquid {
	CarbonatedWater("water.carbonated", 13421823, 0.10000000149011612),
	TonicWater("water.tonic", 13421823, 0.10000000149011612),
	Cream("cream", 15395550, 2.0),
	GingerAle("gingerAle", 16777215, 0.6000000238418579),
	Coffee("coffee", 5910789, 0.30000001192092896),
	SugarSyrup("syrup.simple", 16120049, 0.10000000149011612),
	AgaveNectar("syrup.agave", 13598245, 0.699999988079071),
	GrenadineSyrup("syrup.grenadine", 16009573, 0.800000011920929);

	/* Feature */
	private final IFeatureConstructor<FluidType> constructor;
	private final String identifier;
	@Nullable
	private FluidType fluid;

	MiscFluid(String identifier, int color, double transparency) {
		this.identifier = identifier;
		constructor = () -> new FluidType(identifier, String.format("%s.fluid.%s.%s", ExtraTrees.instance.getModId(), "MiscFluid", this.name()), color)
			.setTransparency(transparency)
			.setTextures(new ResourceLocation(Constants.EXTRA_TREES_MOD_ID, "blocks/liquids/liquid"))
			.setShowHandler((type) -> type == FluidContainerType.GLASS);
	}

	@SubscribeEvent
	public static void registerFeatures(RegisterFeatureEvent event) {
		event.register(MiscFluid.class);
	}

	@Override
	public String toString() {
		return fluid().toString();
	}

	@Override
	public String getDisplayName() {
		return fluid().getDisplayName();
	}

	@Override
	public int getColor() {
		return fluid().getColor();
	}

	@Override
	public FluidStack stack(int amount) {
		return fluid().stack(amount);
	}

	@Override
	public int getTransparency() {
		return fluid().getTransparency();
	}

	@Override
	public String getTooltip(int ratio) {
		return ratio + " Part" + ((ratio > 1) ? "s " : " ") + this.getDisplayName();
	}

	@Override
	public float getABV() {
		return 0.0f;
	}

	/* Feature */
	@Override
	public String getIdentifier() {
		return identifier;
	}

	@Override
	public IFeatureConstructor<FluidType> getConstructor() {
		return constructor;
	}

	@Override
	public boolean hasFluid() {
		return fluid != null;
	}

	@Nullable
	@Override
	public FluidType getFluid() {
		return fluid;
	}

	@Override
	public void setFluid(FluidType fluid) {
		this.fluid = fluid;
	}

	@Override
	public FeatureType getType() {
		return FeatureType.FLUID;
	}

	@Override
	public String getModId() {
		return Constants.EXTRA_TREES_MOD_ID;
	}

	@Override
	public String getModuleId() {
		return ExtraTreesModuleUIDs.CORE;
	}
}
