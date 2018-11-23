package binnie.genetics.machine;

import java.util.function.Supplier;

import net.minecraft.item.ItemStack;

import binnie.core.machines.IMachineType;
import binnie.core.machines.MachinePackage;
import binnie.genetics.machine.splicer.PackageSplicer;
import binnie.genetics.modules.features.GeneticMachines;

public enum AdvGeneticMachine implements IMachineType {
	Splicer(PackageSplicer::new);

	private final Supplier<MachinePackage> supplier;

	AdvGeneticMachine(Supplier<MachinePackage> supplier) {
		this.supplier = supplier;
	}

	@Override
	public Supplier<MachinePackage> getSupplier() {
		return supplier;
	}

	public ItemStack get(int amount) {
		return GeneticMachines.ADV_GENETIC.stack(amount, ordinal());
	}

	public abstract static class PackageAdvGeneticBase extends GeneticMachine.PackageGeneticBase {
		protected PackageAdvGeneticBase(String uid, int flashColour, boolean powered) {
			super(uid, flashColour);
		}
	}
}
