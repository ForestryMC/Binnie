package binnie.extrabees.machines;

import binnie.core.machines.IMachineType;
import binnie.core.machines.Machine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.MachinePackage;
import binnie.core.machines.component.IInteraction;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.gui.ExtraBeesGUID;
import binnie.extrabees.machines.frame.AlvearyFrame;
import binnie.extrabees.machines.hatchery.AlvearyHatchery;
import binnie.extrabees.machines.lighting.AlvearyLighting;
import binnie.extrabees.machines.mutator.AlvearyMutator;
import binnie.extrabees.machines.rainshield.AlvearyRainShield;
import binnie.extrabees.machines.stimulator.AlvearyStimulator;
import binnie.extrabees.machines.transmission.AlvearyTransmission;
import binnie.extrabees.modules.ModuleAlveary;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Supplier;

public enum ExtraBeeMachines implements IMachineType {
	MUTATOR(AlvearyMutator::new),
	FRAME(AlvearyFrame::new),
	RAIN_SHIELD(AlvearyRainShield::new),
	LIGHTING(AlvearyLighting::new),
	STIMULATOR(AlvearyStimulator::new),
	HATCHERY(AlvearyHatchery::new),
	TRANSMISSION(AlvearyTransmission::new);

	private final Supplier<MachinePackage> supplier;

	ExtraBeeMachines(Supplier<MachinePackage> supplier) {
		this.supplier = supplier;
	}

	public ItemStack get(final int size) {
		return new ItemStack(ModuleAlveary.blockAlveary, size, this.ordinal());
	}

	@Override
	public Supplier<MachinePackage> getSupplier() {
		return supplier;
	}

	public static class ComponentExtraBeeGUI extends MachineComponent implements IInteraction.RightClick {
		ExtraBeesGUID id;

		public ComponentExtraBeeGUI(final Machine machine, final ExtraBeesGUID id) {
			super(machine);
			this.id = id;
		}

		@Override
		public void onRightClick(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
			if (!player.isSneaking()) {
				ExtraBees.proxy.openGui(this.id, player, pos);
			}
		}
	}

	public abstract static class AlvearyPackage extends MachinePackage {

		public AlvearyPackage(final String id) {
			super(id);
		}

		@Override
		public TileEntity createTileEntity() {
			return new TileExtraBeeAlveary(this);
		}
	}
}
