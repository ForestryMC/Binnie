package binnie.genetics.machine;

import java.util.function.Supplier;

import net.minecraft.item.ItemStack;

import binnie.core.machines.IMachineType;
import binnie.core.machines.MachinePackage;
import binnie.genetics.machine.splicer.PackageSplicer;
import binnie.genetics.modules.ModuleMachine;

public enum AdvGeneticMachine implements IMachineType {
	Splicer(PackageSplicer::new);

	private final Supplier<MachinePackage> supplier;

	AdvGeneticMachine(final Supplier<MachinePackage> supplier) {
		this.supplier = supplier;
	}

	@Override
	public Supplier<MachinePackage> getSupplier() {
		return supplier;
	}

	public ItemStack get(final int i) {
		return new ItemStack(ModuleMachine.getPackageAdvGenetic().getBlock(), i, this.ordinal());
	}

	public abstract static class PackageAdvGeneticBase extends GeneticMachine.PackageGeneticBase {
		protected PackageAdvGeneticBase(final String uid, final int flashColour, final boolean powered) {
			super(uid, flashColour);
		}
	}
}
