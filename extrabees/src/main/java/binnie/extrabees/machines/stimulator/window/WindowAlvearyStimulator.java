package binnie.extrabees.machines.stimulator.window;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

import net.minecraftforge.fml.relauncher.Side;

import binnie.core.Constants;
import binnie.core.api.gui.Alignment;
import binnie.core.gui.minecraft.Window;
import binnie.core.gui.minecraft.control.ControlEnergyBar;
import binnie.core.gui.minecraft.control.ControlPlayerInventory;
import binnie.core.gui.minecraft.control.ControlSlot;
import binnie.core.util.I18N;
import binnie.extrabees.machines.stimulator.AlvearyStimulator;

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
		this.setTitle(I18N.localise("extrabees.alveary.stimulator.title"));
		new ControlEnergyBar(this, 75, 29, 60, 16, Alignment.LEFT);
		new ControlSlot.Builder(this, 41, 28).assign(AlvearyStimulator.SLOT_CIRCUIT);
		new ControlPlayerInventory(this);
	}

	@Override
	protected String getModId() {
		return Constants.EXTRA_BEES_MOD_ID;
	}

	@Override
	public String getBackgroundTextureName() {
		return "AlvearyStimulator";
	}
}
