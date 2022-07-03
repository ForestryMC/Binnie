package binnie.extrabees.apiary;

import binnie.core.machines.Machine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.component.IInteraction;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.core.ExtraBeeGUID;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ComponentExtraBeeGUI extends MachineComponent implements IInteraction.RightClick {
    protected ExtraBeeGUID id;

    public ComponentExtraBeeGUI(Machine machine, ExtraBeeGUID id) {
        super(machine);
        this.id = id;
    }

    @Override
    public void onRightClick(World world, EntityPlayer player, int x, int y, int z) {
        ExtraBees.proxy.openGui(id, player, x, y, z);
    }
}
