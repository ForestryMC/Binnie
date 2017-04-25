// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extrabees.gui;

import binnie.extrabees.ExtraBees;
import binnie.core.AbstractMod;
import binnie.core.craftgui.minecraft.control.ControlItemDisplay;
import net.minecraft.item.ItemStack;
import binnie.extrabees.apiary.machine.AlvearyMutator;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.geometry.TextJustification;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.minecraft.control.ControlSlot;
import binnie.core.machines.TileEntityMachine;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.inventory.IInventory;
import net.minecraft.entity.player.EntityPlayer;
import binnie.core.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.core.machines.Machine;
import binnie.core.craftgui.minecraft.Window;

public class WindowAlvearyMutator extends Window
{
	Machine machine;
	ControlPlayerInventory playerInventory;

	public WindowAlvearyMutator(final EntityPlayer player, final IInventory inventory, final Side side) {
		super(176.0f, 176.0f, player, inventory, side);
		this.machine = ((TileEntityMachine) inventory).getMachine();
	}

	public static Window create(final EntityPlayer player, final IInventory inventory, final Side side) {
		if (player == null || inventory == null) {
			return null;
		}
		return new WindowAlvearyMutator(player, inventory, side);
	}

	@Override
	public void initialiseClient() {
		this.setTitle("Mutator");
		this.playerInventory = new ControlPlayerInventory(this);
		final ControlSlot slot = new ControlSlot(this, 79.0f, 30.0f);
		slot.assign(0);
		new ControlText(this, new IArea(0.0f, 52.0f, this.w(), 16.0f), "Possible Mutagens:", TextJustification.MiddleCenter).setColor(5592405);
		final int size = AlvearyMutator.getMutagens().size();
		final int w = size * 18;
		if (size > 0) {
			float x = (this.w() - w) / 2.0f;
			for (final ItemStack stack : AlvearyMutator.getMutagens()) {
				final ControlItemDisplay display = new ControlItemDisplay(this, x, 66.0f);
				display.setItemStack(stack);
				display.hastooltip = true;
				x += 18.0f;
			}
		}
	}

	@Override
	public AbstractMod getMod() {
		return ExtraBees.instance;
	}

	@Override
	public String getName() {
		return "AlvearyMutator";
	}
}
