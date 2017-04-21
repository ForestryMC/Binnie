// 
// Decompiled by Procyon v0.5.30
// 

package binnie.genetics.machine;

import net.minecraft.client.renderer.RenderBlocks;
import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import net.minecraft.tileentity.TileEntity;
import binnie.core.resource.IBinnieTexture;
import binnie.core.resource.BinnieResource;
import net.minecraft.block.Block;
import binnie.genetics.Genetics;
import net.minecraft.item.ItemStack;
import binnie.core.machines.MachinePackage;
import binnie.core.machines.IMachineType;

public enum GeneticMachine implements IMachineType
{
	Isolator(Isolator.PackageIsolator.class),
	Sequencer(Sequencer.PackageSequencer.class),
	Polymeriser(Polymeriser.PackagePolymeriser.class),
	Inoculator(Inoculator.PackageInoculator.class);

	Class<? extends MachinePackage> clss;

	private GeneticMachine(final Class<? extends MachinePackage> clss) {
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

	public abstract static class PackageGeneticBase extends MachinePackage
	{
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
		public void renderMachine(final Machine machine, final double x, final double y, final double z, final float partialTick, final RenderBlocks renderer) {
			MachineRendererGenetics.instance.renderMachine(machine, this.colour, this.renderTexture, x, y, z, partialTick);
		}
	}
}
