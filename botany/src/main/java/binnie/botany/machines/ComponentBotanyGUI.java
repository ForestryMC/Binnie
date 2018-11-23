package binnie.botany.machines;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import binnie.botany.Botany;
import binnie.botany.gui.BotanyGUI;
import binnie.core.machines.Machine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.component.IInteraction;

public class ComponentBotanyGUI extends MachineComponent implements IInteraction.RightClick {
	private final BotanyGUI id;

	public ComponentBotanyGUI(Machine machine, BotanyGUI id) {
		super(machine);
		this.id = id;
	}

	@Override
	public void onRightClick(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!player.isSneaking()) {
			Botany.proxy.openGui(this.id, player, pos);
		}
	}
}
