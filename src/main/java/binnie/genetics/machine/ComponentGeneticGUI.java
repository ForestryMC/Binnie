// 
// Decompiled by Procyon v0.5.30
// 

package binnie.genetics.machine;

import binnie.genetics.Genetics;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import binnie.core.machines.Machine;
import binnie.genetics.core.GeneticsGUI;
import binnie.core.machines.component.IInteraction;
import binnie.core.machines.MachineComponent;

public class ComponentGeneticGUI extends MachineComponent implements IInteraction.RightClick
{
	GeneticsGUI id;

	public ComponentGeneticGUI(final Machine machine, final GeneticsGUI id) {
		super(machine);
		this.id = id;
	}

	@Override
	public void onRightClick(final World world, final EntityPlayer player, final int x, final int y, final int z) {
		Genetics.proxy.openGui(this.id, player, x, y, z);
	}
}
