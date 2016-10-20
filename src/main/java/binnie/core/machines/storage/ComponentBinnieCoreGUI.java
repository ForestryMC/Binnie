// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.machines.storage;

import binnie.core.BinnieCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import binnie.core.machines.Machine;
import binnie.core.gui.BinnieCoreGUI;
import binnie.core.machines.component.IInteraction;
import binnie.core.machines.MachineComponent;

class ComponentBinnieCoreGUI extends MachineComponent implements IInteraction.RightClick
{
	private BinnieCoreGUI id;

	public ComponentBinnieCoreGUI(final Machine machine, final BinnieCoreGUI id) {
		super(machine);
		this.id = id;
	}

	@Override
	public void onRightClick(final World world, final EntityPlayer player, final int x, final int y, final int z) {
		BinnieCore.proxy.openGui(this.id, player, x, y, z);
	}
}
