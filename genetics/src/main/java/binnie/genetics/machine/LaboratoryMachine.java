package binnie.genetics.machine;

import java.util.function.Supplier;

import binnie.core.machines.IMachineType;
import binnie.core.machines.MachinePackage;
import binnie.genetics.machine.acclimatiser.PackageAcclimatiser;
import binnie.genetics.machine.analyser.PackageAnalyser;
import binnie.genetics.machine.genepool.PackageGenepool;
import binnie.genetics.machine.incubator.PackageIncubator;
import binnie.genetics.machine.lab.PackageLabMachine;
import net.minecraft.item.ItemStack;

public enum LaboratoryMachine implements IMachineType {
	LabMachine(PackageLabMachine::new),
	Analyser(PackageAnalyser::new),
	Incubator(PackageIncubator::new),
	Genepool(PackageGenepool::new),
	Acclimatiser(PackageAcclimatiser::new);

	private final Supplier<MachinePackage> supplier;

	LaboratoryMachine(final Supplier<MachinePackage> supplier) {
		this.supplier = supplier;
	}

	@Override
	public Supplier<MachinePackage> getSupplier() {
		return supplier;
	}

	public ItemStack get(final int amount) {
		return new ItemStack(ModuleMachine.getPackageLabMachine().getBlock(), amount, this.ordinal());
	}
}
