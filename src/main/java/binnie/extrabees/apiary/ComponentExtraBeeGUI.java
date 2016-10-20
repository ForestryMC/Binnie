// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extrabees.apiary;

import binnie.extrabees.ExtraBees;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import binnie.core.machines.Machine;
import binnie.extrabees.core.ExtraBeeGUID;
import binnie.core.machines.component.IInteraction;
import binnie.core.machines.MachineComponent;

public class ComponentExtraBeeGUI extends MachineComponent implements IInteraction.RightClick
{
	ExtraBeeGUID id;

	public ComponentExtraBeeGUI(final Machine machine, final ExtraBeeGUID id) {
		super(machine);
		this.id = id;
	}

	@Override
	public void onRightClick(final World world, final EntityPlayer player, final int x, final int y, final int z) {
		ExtraBees.proxy.openGui(this.id, player, x, y, z);
	}
}
