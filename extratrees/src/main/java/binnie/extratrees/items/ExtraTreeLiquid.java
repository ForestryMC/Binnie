package binnie.extratrees.items;

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
import binnie.core.liquid.ILiquidDefinition;
import binnie.core.modules.ExtraTreesModuleUIDs;
import binnie.extratrees.ExtraTrees;

@Mod.EventBusSubscriber(modid = Constants.EXTRA_TREES_MOD_ID)
public enum ExtraTreeLiquid implements ILiquidDefinition {
	SAP("sap", 0xBE7542),
	RESIN("resin", 0xC96800),
	LATEX("latex", 0xD8DBC9),
	TURPENTINE("turpentine", 0x79533A);

	/* Feature */
	private final IFeatureConstructor<FluidType> constructor;
	private final String identifier;
	@Nullable
	private FluidType fluid;

	ExtraTreeLiquid(String identifier, int color) {
		this.identifier = identifier;
		constructor = () -> new FluidType(identifier, String.format("%s.fluid.%s.%s", ExtraTrees.instance.getModId(), "ExtraTreeLiquid", this.name()), color)
			.setShowHandler(fluidType -> fluidType == FluidContainerType.CAN || fluidType == FluidContainerType.CAPSULE)
			.setTransparency(255)
			.setTextures(new ResourceLocation(Constants.EXTRA_TREES_MOD_ID, "blocks/liquids/" + identifier));
	}

	@SubscribeEvent
	public static void registerFeatures(RegisterFeatureEvent event) {
		event.register(ExtraTreeLiquid.class);
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
