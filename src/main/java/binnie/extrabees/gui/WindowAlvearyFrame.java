// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extrabees.gui;

import binnie.extrabees.ExtraBees;
import binnie.core.AbstractMod;
import binnie.craftgui.minecraft.control.ControlSlot;
import binnie.core.machines.TileEntityMachine;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.inventory.IInventory;
import net.minecraft.entity.player.EntityPlayer;
import binnie.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.core.machines.Machine;
import binnie.craftgui.minecraft.Window;

public class WindowAlvearyFrame extends Window
{
	Machine machine;
	ControlPlayerInventory playerInventory;

	public WindowAlvearyFrame(final EntityPlayer player, final IInventory inventory, final Side side) {
		super(176.0f, 144.0f, player, inventory, side);
		this.machine = ((TileEntityMachine) inventory).getMachine();
	}

	public static Window create(final EntityPlayer player, final IInventory inventory, final Side side) {
		if (player == null || inventory == null) {
			return null;
		}
		return new WindowAlvearyFrame(player, inventory, side);
	}

	@Override
	public void initialiseClient() {
		this.setTitle("Frame Housing");
		this.playerInventory = new ControlPlayerInventory(this);
		final ControlSlot slot = new ControlSlot(this, 79.0f, 30.0f);
		slot.assign(0);
	}

	@Override
	public AbstractMod getMod() {
		return ExtraBees.instance;
	}

	@Override
	public String getName() {
		return "AlvearyFrame";
	}
}
