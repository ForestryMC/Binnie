package binnie.genetics.machine;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import binnie.core.machines.IMachineType;
import binnie.core.machines.MachinePackage;
import binnie.core.machines.TileEntityMachine;
import binnie.genetics.Genetics;
import binnie.genetics.machine.inoculator.PackageInoculator;
import binnie.genetics.machine.isolator.PackageIsolator;
import binnie.genetics.machine.polymeriser.PackagePolymeriser;
import binnie.genetics.machine.sequencer.PackageSequencer;

public enum GeneticMachine implements IMachineType {
	Isolator(PackageIsolator.class),
	Sequencer(PackageSequencer.class),
	Polymeriser(PackagePolymeriser.class),
	Inoculator(PackageInoculator.class);

	Class<? extends MachinePackage> clss;

	GeneticMachine(final Class<? extends MachinePackage> clss) {
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
		return new ItemStack(ModuleMachine.packageGenetic.getBlock(), i, this.ordinal());
	}

	public abstract static class PackageGeneticBase extends MachinePackage {
		private final int colour;

		protected PackageGeneticBase(final String uid, final int flashColour, final boolean powered) {
			super(uid, powered);
			this.colour = flashColour;
		}

		@Override
		public TileEntity createTileEntity() {
			return new TileEntityMachine(this);
		}

		@Override
		public void register() {
		}
	}
}
