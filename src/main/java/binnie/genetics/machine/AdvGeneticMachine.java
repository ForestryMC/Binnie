// 
// Decompiled by Procyon v0.5.30
// 

package binnie.genetics.machine;

import binnie.core.resource.IBinnieTexture;
import net.minecraft.block.Block;
import binnie.genetics.Genetics;
import net.minecraft.item.ItemStack;
import binnie.core.machines.MachinePackage;
import binnie.core.machines.IMachineType;

public enum AdvGeneticMachine implements IMachineType
{
	Splicer(Splicer.PackageSplicer.class);

	Class<? extends MachinePackage> clss;

	private AdvGeneticMachine(final Class<? extends MachinePackage> clss) {
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
		return new ItemStack(Genetics.packageAdvGenetic.getBlock(), i, this.ordinal());
	}

	public abstract static class PackageAdvGeneticBase extends GeneticMachine.PackageGeneticBase
	{
		protected PackageAdvGeneticBase(final String uid, final IBinnieTexture renderTexture, final int flashColour, final boolean powered) {
			super(uid, renderTexture, flashColour, powered);
		}
	}
}
