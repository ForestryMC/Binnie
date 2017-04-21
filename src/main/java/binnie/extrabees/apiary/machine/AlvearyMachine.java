// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extrabees.apiary.machine;

import binnie.core.machines.MachineRendererBlock;
import net.minecraft.client.renderer.RenderBlocks;
import binnie.extrabees.apiary.TileExtraBeeAlveary;
import net.minecraft.tileentity.TileEntity;
import binnie.core.machines.Machine;
import binnie.core.resource.BinnieResource;
import binnie.extrabees.apiary.ModuleApiary;
import net.minecraft.item.ItemStack;
import binnie.core.machines.MachinePackage;
import binnie.core.machines.IMachineType;

public enum AlvearyMachine implements IMachineType
{
	Mutator(AlvearyMutator.PackageAlvearyMutator.class),
	Frame(AlvearyFrame.PackageAlvearyFrame.class),
	RainShield(AlvearyRainShield.PackageAlvearyRainShield.class),
	Lighting(AlvearyLighting.PackageAlvearyLighting.class),
	Stimulator(AlvearyStimulator.PackageAlvearyStimulator.class),
	Hatchery(AlvearyHatchery.PackageAlvearyHatchery.class),
	Transmission(AlvearyTransmission.PackageAlvearyTransmission.class);

	Class<? extends MachinePackage> clss;

	private AlvearyMachine(final Class<? extends MachinePackage> clss) {
		this.clss = clss;
	}

	@Override
	public Class<? extends MachinePackage> getPackageClass() {
		return this.clss;
	}

	public ItemStack get(final int size) {
		return new ItemStack(ModuleApiary.blockComponent, size, this.ordinal());
	}

	@Override
	public boolean isActive() {
		return true;
	}

	public abstract static class AlvearyPackage extends MachinePackage
	{
		BinnieResource machineTexture;

		public AlvearyPackage(final String id, final BinnieResource machineTexture, final boolean powered) {
			super(id, powered);
			this.machineTexture = machineTexture;
		}

		@Override
		public void createMachine(final Machine machine) {
		}

		@Override
		public TileEntity createTileEntity() {
			return new TileExtraBeeAlveary(this);
		}

		@Override
		public void register() {
		}

		@Override
		public void renderMachine(final Machine machine, final double x, final double y, final double z, final float partialTick, final RenderBlocks renderer) {
			MachineRendererBlock.instance.renderMachine(this.machineTexture, x, y, z, partialTick);
		}
	}
}
