package binnie.core.modules.features;

import javax.annotation.Nullable;

import net.minecraft.creativetab.CreativeTabs;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import binnie.core.Constants;
import binnie.core.features.FeatureType;
import binnie.core.features.IFeatureConstructor;
import binnie.core.features.IMachineFeature;
import binnie.core.features.RegisterFeatureEvent;
import binnie.core.machines.MachineGroup;
import binnie.core.machines.storage.Compartment;
import binnie.core.modules.BinnieCoreModuleUIDs;

@Mod.EventBusSubscriber(modid = Constants.CORE_MOD_ID)
public enum StorageMachines implements IMachineFeature {
	COMPARTMENT("storage", () -> new MachineGroup("machine.storage", "storage", Compartment.values()));

	private final String identifier;
	private final IFeatureConstructor<MachineGroup> constructor;
	@Nullable
	private MachineGroup group;

	StorageMachines(String identifier, IFeatureConstructor<MachineGroup> constructor) {
		this.identifier = identifier;
		this.constructor = constructor;
	}

	@SubscribeEvent
	public static void registerFeatures(RegisterFeatureEvent event) {
		event.register(StorageMachines.class);
	}

	@Override
	public MachineGroup apply(MachineGroup group) {
		group.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		return IMachineFeature.super.apply(group);
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
	public IFeatureConstructor<MachineGroup> getConstructor() {
		return constructor;
	}

	@Override
	public FeatureType getType() {
		return FeatureType.MACHINE;
	}

	@Override
	public String getModId() {
		return Constants.CORE_MOD_ID;
	}

	@Override
	public String getModuleId() {
		return BinnieCoreModuleUIDs.STORAGE;
	}
}
