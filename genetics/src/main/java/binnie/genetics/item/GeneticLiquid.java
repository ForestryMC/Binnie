package binnie.genetics.item;

import javax.annotation.Nullable;

import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import binnie.core.Constants;
import binnie.core.features.FeatureType;
import binnie.core.features.IFeatureConstructor;
import binnie.core.features.RegisterFeatureEvent;
import binnie.core.liquid.FluidContainerType;
import binnie.core.liquid.FluidType;
import binnie.core.liquid.IFluidDefinition;
import binnie.core.modules.GeneticsModuleUIDs;
import binnie.genetics.Genetics;

@Mod.EventBusSubscriber(modid = Constants.GENETICS_MOD_ID)
public enum GeneticLiquid implements IFluidDefinition {
	GrowthMedium("growth.medium", 15460533),
	Bacteria("bacteria", 14203521),
	BacteriaPoly("bacteria.poly", 11443396),
	RawDNA("dna.raw", 15089129),
	BacteriaVector("bacteria.vector", 15960958);

	/* Feature */
	private final IFeatureConstructor<FluidType> constructor;
	private final String identifier;
	@Nullable
	private FluidType fluid;

	GeneticLiquid(String identifier, int color) {
		this.identifier = identifier;
		constructor = () -> new FluidType(identifier, String.format("%s.fluid.%s.%s", Genetics.instance.getModId(), "GeneticLiquid", this.name()), color)
			.setTextures(new ResourceLocation(Genetics.instance.getModId(), "blocks/liquids/" + identifier.replace(".", "_")))
			.setColor(16777215)
			.setContainerColor(color)
			.setPlaceHandler((type) -> ordinal() == 0 || type == FluidContainerType.CYLINDER)
			.setShowHandler((type) -> type == FluidContainerType.CYLINDER);
	}

	@SubscribeEvent
	public static void registerFeatures(RegisterFeatureEvent event) {
		event.register(GeneticLiquid.class);
	}

	@Override
	public String toString() {
		return fluid().toString();
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
		return Constants.GENETICS_MOD_ID;
	}

	@Override
	public String getModuleId() {
		return GeneticsModuleUIDs.CORE;
	}
}
