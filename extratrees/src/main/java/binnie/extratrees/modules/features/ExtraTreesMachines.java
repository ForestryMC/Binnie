package binnie.extratrees.modules.features;

import javax.annotation.Nullable;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import forestry.api.core.Tabs;

import binnie.core.Constants;
import binnie.core.features.FeatureType;
import binnie.core.features.IFeatureConstructor;
import binnie.core.features.IMachineFeature;
import binnie.core.features.RegisterFeatureEvent;
import binnie.core.machines.MachineGroup;
import binnie.core.modules.ExtraTreesModuleUIDs;
import binnie.extratrees.machines.ExtraTreeMachine;

@Mod.EventBusSubscriber(modid = Constants.EXTRA_TREES_MOD_ID)
public enum ExtraTreesMachines implements IMachineFeature {
	MACHINE("machine", () -> new MachineGroup("machine", "machine", ExtraTreeMachine.values()));

	private final String identifier;
	private final IFeatureConstructor<MachineGroup> constructor;
	@Nullable
	private MachineGroup group;

	ExtraTreesMachines(String identifier, IFeatureConstructor<MachineGroup> constructor) {
		this.identifier = identifier;
		this.constructor = constructor;
	}

	@SubscribeEvent
	public static void registerFeatures(RegisterFeatureEvent event) {
		event.register(ExtraTreesMachines.class);
	}

	@Override
	public String getIdentifier() {
		return identifier;
	}

	@Override
	public MachineGroup getGroup() {
		return group;
	}

	@Override
	public boolean hasGroup() {
		return group != null;
	}

	@Override
	public void setGroup(@Nullable MachineGroup group) {
		this.group = group;
	}

	@Override
	public MachineGroup apply(MachineGroup group) {
		group.setCreativeTab(Tabs.tabArboriculture);
		return IMachineFeature.super.apply(group);
	}

	@Override
	public IFeatureConstructor<MachineGroup> getConstructor() {
		return constructor;
	}

	@Override
	public FeatureType getType() {
		return FeatureType.MACHINE;
	}

	@Override
	public String getModId() {
		return Constants.EXTRA_TREES_MOD_ID;
	}

	@Override
	public String getModuleId() {
		return ExtraTreesModuleUIDs.MACHINES;
	}
}
