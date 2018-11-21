package binnie.genetics.modules.features;

import binnie.core.Constants;
import binnie.core.features.FeatureProvider;
import binnie.core.features.IFeatureRegistry;
import binnie.core.machines.MachineGroup;
import binnie.core.modules.GeneticsModuleUIDs;
import binnie.genetics.CreativeTabGenetics;
import binnie.genetics.Genetics;
import binnie.genetics.machine.AdvGeneticMachine;
import binnie.genetics.machine.GeneticMachine;
import binnie.genetics.machine.LaboratoryMachine;

@FeatureProvider(containerId = Constants.GENETICS_MOD_ID, moduleID = GeneticsModuleUIDs.MACHINES)
public class GeneticMachines {
	public static final MachineGroup GENETIC = features().createMachine("machine", "machine", GeneticMachine.values()).setCreativeTab(CreativeTabGenetics.INSTANCE);
	public static final MachineGroup ADV_GENETIC = features().createMachine("machine.lab_machine", "lab_machine", LaboratoryMachine.values()).setCreativeTab(CreativeTabGenetics.INSTANCE);
	public static final MachineGroup LAB_MACHINE = features().createMachine("machine.adv_machine", "adv_machine", AdvGeneticMachine.values()).setCreativeTab(CreativeTabGenetics.INSTANCE);

	private static IFeatureRegistry features() {
		return Genetics.instance.registry(GeneticsModuleUIDs.MACHINES);
	}

	private GeneticMachines() {
	}
}
