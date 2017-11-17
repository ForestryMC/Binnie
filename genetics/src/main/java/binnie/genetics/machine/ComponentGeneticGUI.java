package binnie.genetics.machine;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import binnie.core.machines.Machine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.component.IInteraction;
import binnie.genetics.Genetics;
import binnie.genetics.core.GeneticsGUI;

public class ComponentGeneticGUI extends MachineComponent implements IInteraction.RightClick {
	private final GeneticsGUI id;

	public ComponentGeneticGUI(final Machine machine, final GeneticsGUI id) {
		super(machine);
		this.id = id;
	}

	@Override
	public void onRightClick(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!player.isSneaking()) {
			Genetics.proxy.openGui(this.id, player, pos);
		}
	}
}
