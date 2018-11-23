package binnie.genetics.item;

import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fluids.FluidStack;

import binnie.core.Constants;
import binnie.core.features.FeatureProvider;
import binnie.core.liquid.FluidContainerType;
import binnie.core.liquid.FluidType;
import binnie.core.liquid.IFluidDefinition;
import binnie.core.modules.GeneticsModuleUIDs;
import binnie.genetics.Genetics;

@FeatureProvider(containerId = Constants.GENETICS_MOD_ID, moduleID = GeneticsModuleUIDs.CORE)
public enum GeneticLiquid implements IFluidDefinition {
	GrowthMedium("growth.medium", 15460533),
	Bacteria("bacteria", 14203521),
	BacteriaPoly("bacteria.poly", 11443396),
	RawDNA("dna.raw", 15089129),
	BacteriaVector("bacteria.vector", 15960958);

	private final FluidType type;

	GeneticLiquid(String ident, int color) {
		type = Genetics.instance.registry(GeneticsModuleUIDs.CORE).createFluid(ident, String.format("%s.fluid.%s.%s", Genetics.instance.getModId(), "GeneticLiquid", this.name()), color)
			.setTextures(new ResourceLocation(Genetics.instance.getModId(), "blocks/liquids/" + ident.replace(".", "_")))
			.setColor(16777215)
			.setContainerColor(color)
			.setPlaceHandler((type) -> ordinal() == 0 || type == FluidContainerType.CYLINDER)
			.setShowHandler((type) -> type == FluidContainerType.CYLINDER);
	}

	@Override
	public String toString() {
		return type.toString();
	}

	@Override
	public FluidStack get(int amount) {
		return type.stack(amount);
	}

	@Override
	public FluidType getType() {
		return type;
	}
}
