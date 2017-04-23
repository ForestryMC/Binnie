// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extrabees.gui;

import binnie.extrabees.ExtraBees;
import binnie.core.AbstractMod;
import binnie.extrabees.apiary.machine.AlvearyStimulator;
import binnie.core.craftgui.minecraft.control.ControlSlot;
import binnie.core.craftgui.geometry.Position;
import binnie.core.craftgui.minecraft.control.ControlEnergyBar;
import binnie.core.machines.TileEntityMachine;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.inventory.IInventory;
import net.minecraft.entity.player.EntityPlayer;
import binnie.core.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.core.machines.Machine;
import binnie.core.craftgui.minecraft.Window;

public class WindowAlvearyStimulator extends Window
{
	Machine machine;
	ControlPlayerInventory playerInventory;

	public WindowAlvearyStimulator(final EntityPlayer player, final IInventory inventory, final Side side) {
		super(176.0f, 144.0f, player, inventory, side);
		this.machine = ((TileEntityMachine) inventory).getMachine();
	}

	public static Window create(final EntityPlayer player, final IInventory inventory, final Side side) {
		if (player == null || inventory == null) {
			return null;
		}
		return new WindowAlvearyStimulator(player, inventory, side);
	}

	@Override
	public void initialiseClient() {
		this.setTitle("Stimulator");
		new ControlEnergyBar(this, 75, 29, 60, 16, Position.Left);
		final ControlSlot slot = new ControlSlot(this, 41.0f, 28.0f);
		slot.assign(AlvearyStimulator.slotCircuit);
		this.playerInventory = new ControlPlayerInventory(this);
	}

	@Override
	public AbstractMod getMod() {
		return ExtraBees.instance;
	}

	@Override
	public String getName() {
		return "AlvearyStimulator";
	}
}
