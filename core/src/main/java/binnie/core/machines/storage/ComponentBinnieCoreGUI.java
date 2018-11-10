package binnie.core.machines.storage;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import binnie.core.BinnieCore;
import binnie.core.gui.BinnieCoreGUI;
import binnie.core.machines.Machine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.component.IInteraction;

class ComponentBinnieCoreGUI extends MachineComponent implements IInteraction.RightClick {
	private final BinnieCoreGUI id;

	public ComponentBinnieCoreGUI(final Machine machine, final BinnieCoreGUI id) {
		super(machine);
		this.id = id;
	}

	@Override
	public void onRightClick(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!player.isSneaking()) {
			BinnieCore.getBinnieProxy().openGui(this.id, player, pos);
		}
	}
}
