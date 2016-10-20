package binnie.extrabees.apiary;

import binnie.core.machines.Machine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.component.IInteraction;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.core.ExtraBeeGUID;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ComponentExtraBeeGUI extends MachineComponent implements IInteraction.RightClick {
    ExtraBeeGUID id;

    public ComponentExtraBeeGUI(final Machine machine, final ExtraBeeGUID id) {
        super(machine);
        this.id = id;
    }

    @Override
    public void onRightClick(final World world, final EntityPlayer player, final BlockPos pos) {
        ExtraBees.proxy.openGui(this.id, player, pos);
    }
}
