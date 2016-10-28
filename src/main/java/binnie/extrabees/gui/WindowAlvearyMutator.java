package binnie.extrabees.gui;

import binnie.core.AbstractMod;
import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.craftgui.controls.ControlText;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.TextJustification;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.minecraft.control.ControlItemDisplay;
import binnie.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.craftgui.minecraft.control.ControlSlot;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.apiary.machine.AlvearyMutator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;

public class WindowAlvearyMutator extends Window {
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
		new ControlText(this, new IArea(0.0f, 52.0f, this.w(), 16.0f), "Possible Mutagens:", TextJustification.MiddleCenter).setColour(5592405);
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
	public String getBackgroundTextureName() {
		return "AlvearyMutator";
	}
}
