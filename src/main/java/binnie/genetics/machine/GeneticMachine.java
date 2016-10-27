package binnie.genetics.machine;

import binnie.core.machines.IMachineType;
import binnie.core.machines.Machine;
import binnie.core.machines.MachinePackage;
import binnie.core.machines.TileEntityMachine;
import binnie.core.resource.BinnieResource;
import binnie.core.resource.IBinnieTexture;
import binnie.genetics.Genetics;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public enum GeneticMachine implements IMachineType {
	Isolator(Isolator.PackageIsolator.class),
	Sequencer(Sequencer.PackageSequencer.class),
	Polymeriser(Polymeriser.PackagePolymeriser.class),
	Inoculator(Inoculator.PackageInoculator.class);

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
		return new ItemStack(Genetics.packageGenetic.getBlock(), i, this.ordinal());
	}

	public abstract static class PackageGeneticBase extends MachinePackage {
		BinnieResource renderTexture;
		int colour;

		protected PackageGeneticBase(final String uid, final IBinnieTexture renderTexture, final int flashColour, final boolean powered) {
			super(uid, powered);
			this.renderTexture = renderTexture.getTexture();
			this.colour = flashColour;
		}

		@Override
		public TileEntity createTileEntity() {
			return new TileEntityMachine(this);
		}

		@Override
		public void register() {
		}

		@Override
		public void renderMachine(Machine machine, double x, double y, double z, float partialTicks, int destroyStage) {
			MachineRendererGenetics.instance.renderMachine(machine, x, y, z, partialTicks, destroyStage);
		}
	}
}
