package binnie.botany.machines;

import binnie.botany.Botany;
import binnie.botany.gui.BotanyGUI;
import binnie.core.machines.Machine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.component.IInteraction;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ComponentBotanyGUI extends MachineComponent implements IInteraction.RightClick {
	BotanyGUI id;

	public ComponentBotanyGUI(final Machine machine, final BotanyGUI id) {
		super(machine);
		this.id = id;
	}

	@Override
	public void onRightClick(World p0, EntityPlayer p1, BlockPos pos) {
		Botany.proxy.openGui(this.id, p1, pos);
	}
}
