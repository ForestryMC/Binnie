// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.machines.storage;

import net.minecraft.client.renderer.RenderBlocks;
import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import net.minecraft.tileentity.TileEntity;
import binnie.core.resource.IBinnieTexture;
import binnie.core.resource.BinnieResource;
import net.minecraft.block.Block;
import binnie.core.BinnieCore;
import net.minecraft.item.ItemStack;
import binnie.core.machines.MachinePackage;
import binnie.core.machines.IMachineType;

enum Compartment implements IMachineType
{
	Compartment(StandardCompartment.PackageCompartment.class),
	CompartmentCopper(StandardCompartment.PackageCompartmentCopper.class),
	CompartmentBronze(StandardCompartment.PackageCompartmentBronze.class),
	CompartmentIron(StandardCompartment.PackageCompartmentIron.class),
	CompartmentGold(StandardCompartment.PackageCompartmentGold.class),
	CompartmentDiamond(StandardCompartment.PackageCompartmentDiamond.class);

	Class<? extends MachinePackage> clss;

	private Compartment(final Class<? extends MachinePackage> clss) {
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
		return new ItemStack(BinnieCore.packageCompartment.getBlock(), i, this.ordinal());
	}

	public abstract static class PackageCompartment extends MachinePackage
	{
		private BinnieResource renderTexture;

		protected PackageCompartment(final String uid, final IBinnieTexture renderTexture) {
			super(uid, false);
			this.renderTexture = renderTexture.getTexture();
		}

		@Override
		public TileEntity createTileEntity() {
			return new TileEntityMachine(this);
		}

		@Override
		public void register() {
		}

		@Override
		public void renderMachine(final Machine machine, final double x, final double y, final double z, final float var8, final RenderBlocks renderer) {
			MachineRendererCompartment.instance.renderMachine(machine, 16777215, this.renderTexture, x, y, z, var8);
		}
	}
}
