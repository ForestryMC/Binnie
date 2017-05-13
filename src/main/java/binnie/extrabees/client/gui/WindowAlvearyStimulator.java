package binnie.extrabees.client.gui;

import binnie.core.AbstractMod;
import binnie.core.craftgui.geometry.Position;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.minecraft.control.ControlEnergyBar;
import binnie.core.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.core.craftgui.minecraft.control.ControlSlot;
import binnie.extrabees.apiary.machine.AlvearyStimulator;
import binnie.extrabees.client.GuiHack;
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
		new ControlEnergyBar(this, 75, 29, 60, 16, Position.LEFT);
		new ControlSlot.Builder(this, 41, 28).assign(AlvearyStimulator.slotCircuit);
		new ControlPlayerInventory(this);
	}

	@Override
	public AbstractMod getMod() {
		return GuiHack.INSTANCE;
	}

	@Override
	public String getBackgroundTextureName() {
		return "AlvearyStimulator";
	}
}
