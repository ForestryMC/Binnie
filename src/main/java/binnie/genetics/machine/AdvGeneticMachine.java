package binnie.genetics.machine;

import binnie.core.machines.IMachineType;
import binnie.core.machines.MachinePackage;
import binnie.core.resource.IBinnieTexture;
import binnie.genetics.Genetics;
import net.minecraft.item.ItemStack;

public enum AdvGeneticMachine implements IMachineType {
	Splicer(Splicer.PackageSplicer.class);

	protected Class<? extends MachinePackage> cls;

	AdvGeneticMachine(Class<? extends MachinePackage> cls) {
		this.cls = cls;
	}

	@Override
	public Class<? extends MachinePackage> getPackageClass() {
		return cls;
	}

	@Override
	public boolean isActive() {
		return true;
	}

	public ItemStack get(int i) {
		return new ItemStack(Genetics.packageAdvGenetic.getBlock(), i, ordinal());
	}

	public abstract static class PackageAdvGeneticBase extends GeneticMachine.PackageGeneticBase {
		protected PackageAdvGeneticBase(String uid, IBinnieTexture renderTexture, int flashColor, boolean powered) {
			super(uid, renderTexture, flashColor, powered);
		}
	}
}
