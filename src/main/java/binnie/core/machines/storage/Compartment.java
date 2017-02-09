package binnie.core.machines.storage;

import binnie.core.BinnieCore;
import binnie.core.machines.IMachineType;
import binnie.core.machines.Machine;
import binnie.core.machines.MachinePackage;
import binnie.core.machines.TileEntityMachine;
import binnie.core.resource.IBinnieTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

enum Compartment implements IMachineType {
	Compartment(StandardCompartment.PackageCompartment.class),
	CompartmentCopper(StandardCompartment.PackageCompartmentCopper.class),
	CompartmentBronze(StandardCompartment.PackageCompartmentBronze.class),
	CompartmentIron(StandardCompartment.PackageCompartmentIron.class),
	CompartmentGold(StandardCompartment.PackageCompartmentGold.class),
	CompartmentDiamond(StandardCompartment.PackageCompartmentDiamond.class);

	Class<? extends MachinePackage> clss;

	Compartment(final Class<? extends MachinePackage> clss) {
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
		return new ItemStack(BinnieCore.getPackageCompartment().getBlock(), i, this.ordinal());
	}

	public abstract static class PackageCompartment extends MachinePackage {
		private IBinnieTexture renderTexture;

		protected PackageCompartment(final String uid, final IBinnieTexture renderTexture) {
			super(uid, false);
			this.renderTexture = renderTexture;
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
			MachineRendererCompartment.instance.renderMachine(machine, 16777215, renderTexture.getTexture(), x, y, z, partialTicks);
		}
	}
}
