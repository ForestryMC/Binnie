package binnie.genetics.machine;

import binnie.core.machines.IMachineType;
import binnie.core.machines.Machine;
import binnie.core.machines.MachinePackage;
import binnie.core.machines.TileEntityMachine;
import binnie.core.resource.BinnieResource;
import binnie.core.resource.IBinnieTexture;
import binnie.genetics.Genetics;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public enum GeneticMachine implements IMachineType {
	Isolator(Isolator.PackageIsolator.class),
	Sequencer(Sequencer.PackageSequencer.class),
	Polymeriser(Polymeriser.PackagePolymeriser.class),
	Inoculator(Inoculator.PackageInoculator.class);

	protected Class<? extends MachinePackage> clss;

	GeneticMachine(Class<? extends MachinePackage> clss) {
		this.clss = clss;
	}

	@Override
	public Class<? extends MachinePackage> getPackageClass() {
		return clss;
	}

	@Override
	public boolean isActive() {
		return true;
	}

	public ItemStack get(int i) {
		return new ItemStack(Genetics.packageGenetic.getBlock(), i, ordinal());
	}

	public abstract static class PackageGeneticBase extends MachinePackage {
		protected BinnieResource renderTexture;
		protected int colour;

		protected PackageGeneticBase(String uid, IBinnieTexture renderTexture, int flashColor, boolean powered) {
			super(uid, powered);
			this.renderTexture = renderTexture.getTexture();
			colour = flashColor;
		}

		@Override
		public TileEntity createTileEntity() {
			return new TileEntityMachine(this);
		}

		@Override
		public void renderMachine(Machine machine, double x, double y, double z, float partialTick, RenderBlocks renderer) {
			MachineRendererGenetics.instance.renderMachine(machine, colour, renderTexture, x, y, z, partialTick);
		}
	}
}
