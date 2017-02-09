package binnie.genetics.machine;

import binnie.core.machines.IMachineType;
import binnie.core.machines.Machine;
import binnie.core.machines.MachinePackage;
import binnie.core.machines.TileEntityMachine;
import binnie.core.resource.IBinnieTexture;
import binnie.genetics.Genetics;
import binnie.genetics.machine.inoculator.PackageInoculator;
import binnie.genetics.machine.isolator.PackageIsolator;
import binnie.genetics.machine.polymeriser.PackagePolymeriser;
import binnie.genetics.machine.sequencer.PackageSequencer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
		return new ItemStack(Genetics.packageGenetic.getBlock(), i, this.ordinal());
	}

	public abstract static class PackageGeneticBase extends MachinePackage {
		private final IBinnieTexture renderTexture;
		private final int colour;

		protected PackageGeneticBase(final String uid, final IBinnieTexture renderTexture, final int flashColour, final boolean powered) {
			super(uid, powered);
			this.renderTexture = renderTexture;
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
		@SideOnly(Side.CLIENT)
		public void renderMachine(Machine machine, double x, double y, double z, float partialTicks, int destroyStage) {
			MachineRendererGenetics.instance.renderMachine(machine, x, y, z, partialTicks, destroyStage);
		}
	}
}
