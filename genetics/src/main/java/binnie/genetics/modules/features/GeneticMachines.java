package binnie.genetics.modules.features;

import javax.annotation.Nullable;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import binnie.core.Constants;
import binnie.core.features.FeatureType;
import binnie.core.features.IFeatureConstructor;
import binnie.core.features.IMachineFeature;
import binnie.core.features.RegisterFeatureEvent;
import binnie.core.machines.MachineGroup;
import binnie.core.modules.GeneticsModuleUIDs;
import binnie.genetics.CreativeTabGenetics;
import binnie.genetics.machine.AdvGeneticMachine;
import binnie.genetics.machine.GeneticMachine;
import binnie.genetics.machine.LaboratoryMachine;

@Mod.EventBusSubscriber(modid = Constants.GENETICS_MOD_ID)
public enum GeneticMachines implements IMachineFeature {
	GENETIC("machine", () -> new MachineGroup("machine", "machine", GeneticMachine.values())),
	ADV_GENETIC("lab_machine", () -> new MachineGroup("machine.lab_machine", "lab_machine", LaboratoryMachine.values())),
	LAB_MACHINE("adv_machine", () -> new MachineGroup("machine.adv_machine", "adv_machine", AdvGeneticMachine.values()));

	private final String identifier;
	private final IFeatureConstructor<MachineGroup> constructor;
	@Nullable
	private MachineGroup group;

	GeneticMachines(String identifier, IFeatureConstructor<MachineGroup> constructor) {
		this.identifier = identifier;
		this.constructor = constructor;
	}

	@SubscribeEvent
	public static void registerFeatures(RegisterFeatureEvent event) {
		event.register(GeneticMachines.class);
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
		group.setCreativeTab(CreativeTabGenetics.INSTANCE);
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
		return Constants.GENETICS_MOD_ID;
	}

	@Override
	public String getModuleId() {
		return GeneticsModuleUIDs.MACHINES;
	}
}
