package binnie.extrabees.gui;

import binnie.core.AbstractMod;
import binnie.craftgui.core.geometry.Position;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.minecraft.control.ControlEnergyBar;
import binnie.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.craftgui.minecraft.control.ControlSlot;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.apiary.machine.AlvearyStimulator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nullable;

public class WindowAlvearyStimulator extends Window {
	public WindowAlvearyStimulator(final EntityPlayer player, final IInventory inventory, final Side side) {
		super(176, 144, player, inventory, side);
	}

	@Nullable
	public static Window create(final EntityPlayer player, @Nullable final IInventory inventory, final Side side) {
		if (inventory == null) {
			return null;
		}
		return new WindowAlvearyStimulator(player, inventory, side);
	}

	@Override
	public void initialiseClient() {
		this.setTitle("Stimulator");
		new ControlEnergyBar(this, 75, 29, 60, 16, Position.Left);
		new ControlSlot.Builder(this, 41, 28).assign(AlvearyStimulator.slotCircuit);
		new ControlPlayerInventory(this);
	}

	@Override
	public AbstractMod getMod() {
		return ExtraBees.instance;
	}

	@Override
	public String getBackgroundTextureName() {
		return "AlvearyStimulator";
	}
}
