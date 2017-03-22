package binnie.genetics.machine;

import binnie.core.machines.IMachineType;
import binnie.core.machines.MachinePackage;
import binnie.core.resource.IBinnieTexture;
import binnie.genetics.Genetics;
import binnie.genetics.machine.splicer.PackageSplicer;
import net.minecraft.item.ItemStack;

public enum AdvGeneticMachine implements IMachineType {
	Splicer(PackageSplicer.class);

	Class<? extends MachinePackage> clss;

	AdvGeneticMachine(final Class<? extends MachinePackage> clss) {
		this.clss = clss;
	}

	@Override
	public Class<? extends MachinePackage> getPackageClass() {
		return this.clss;
	}

	@Override
	public boolean isActive() {
		return true;
	}

	public ItemStack get(final int i) {
		Genetics.machine();
		return new ItemStack(ModuleMachine.packageAdvGenetic.getBlock(), i, this.ordinal());
	}

	public abstract static class PackageAdvGeneticBase extends GeneticMachine.PackageGeneticBase {
		protected PackageAdvGeneticBase(final String uid, final IBinnieTexture renderTexture, final int flashColour, final boolean powered) {
			super(uid, renderTexture, flashColour, powered);
		}
	}
}
