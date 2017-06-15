package binnie.extratrees.kitchen;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.machines.IMachineType;
import binnie.core.machines.Machine;
import binnie.core.machines.MachinePackage;
import binnie.core.machines.TileEntityMachine;
import binnie.core.resource.IBinnieTexture;
import binnie.extratrees.ExtraTrees;

public enum KitchenMachine implements IMachineType {
	// TODO implement
	Worktop(null),
	Cupboard(null),
	BottleRack(BottleRack.PackageBottleRack.class);

	@Nullable
	Class<? extends MachinePackage> clss;

	KitchenMachine(@Nullable final Class<? extends MachinePackage> clss) {
		this.clss = clss;
	}

	@Override
	public Class<? extends MachinePackage> getPackageClass() {
		return this.clss;
	}

	@Override
	public boolean isActive() {
		return this.clss != null;
	}

	public ItemStack get(final int i) {
		return new ItemStack(ExtraTrees.getKitchen().blockKitchen, i, this.ordinal());
	}

	public abstract static class PackageKitchenMachine extends MachinePackage {
		private final IBinnieTexture textureName;

		protected PackageKitchenMachine(final String uid, final IBinnieTexture textureName) {
			super(uid, false);
			this.textureName = textureName;
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
			MachineRendererKitchen.instance.renderMachine(machine, textureName.getTexture(), x, y, z, partialTicks);
		}
	}
}
