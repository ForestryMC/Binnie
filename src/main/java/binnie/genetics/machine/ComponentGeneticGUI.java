package binnie.genetics.machine;

import binnie.core.machines.Machine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.component.IInteraction;
import binnie.genetics.Genetics;
import binnie.genetics.core.GeneticsGUI;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ComponentGeneticGUI extends MachineComponent implements IInteraction.RightClick {
    public GeneticsGUI id;

    public ComponentGeneticGUI(Machine machine, GeneticsGUI id) {
        super(machine);
        this.id = id;
    }

    @Override
    public void onRightClick(World world, EntityPlayer player, int x, int y, int z) {
        Genetics.proxy.openGui(id, player, x, y, z);
    }
}
