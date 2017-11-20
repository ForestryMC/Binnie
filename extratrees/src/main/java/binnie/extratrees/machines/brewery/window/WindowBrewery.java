package binnie.extratrees.machines.brewery.window;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

import net.minecraftforge.fml.relauncher.Side;

import binnie.core.api.gui.Alignment;
import binnie.core.gui.minecraft.Window;
import binnie.core.gui.minecraft.control.ControlEnergyBar;
import binnie.core.gui.minecraft.control.ControlErrorState;
import binnie.core.gui.minecraft.control.ControlLiquidTank;
import binnie.core.gui.minecraft.control.ControlPlayerInventory;
import binnie.core.gui.minecraft.control.ControlSlot;
import binnie.core.gui.minecraft.control.ControlSlotArray;
import binnie.core.machines.Machine;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.machines.brewery.BreweryMachine;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WindowBrewery extends Window {
	public static final String LANG_KEY = "extratrees.machine.brewery";

	public WindowBrewery(final EntityPlayer player, final IInventory inventory, final Side side) {
		super(228, 218, player, inventory, side);
	}

	@Nullable
	public static Window create(final EntityPlayer player, @Nullable final IInventory inventory, final Side side) {
		if (inventory == null) {
			return null;
		}
		return new WindowBrewery(player, inventory, side);
	}

	@Override
	protected String getModId() {
		return ExtraTrees.instance.getModId();
	}

	@Override
	protected String getBackgroundTextureName() {
		return "Brewery";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void initialiseClient() {
		this.setTitle(Machine.getMachine(this.getInventory()).getPackage().getDisplayName());
		new ControlSlotArray.Builder(this, 42, 32, 1, 3).create(BreweryMachine.SLOT_RECIPE_GRAINS);
		new ControlSlot.Builder(this, 16, 41).assign(BreweryMachine.SLOT_RECIPE_INPUT);
		new ControlSlot.Builder(this, 105, 77).assign(BreweryMachine.SLOT_YEAST);
		new ControlLiquidTank(this, 76, 32).setTankID(BreweryMachine.TANK_INPUT);
		new ControlLiquidTank(this, 162, 32).setTankID(BreweryMachine.TANK_OUTPUT);
		new ControlEnergyBar(this, 196, 32, 16, 60, Alignment.BOTTOM);
		new ControlBreweryProgress(this, 110, 32);
		new ControlSlotArray.Builder(this, this.getSize().xPos() / 2 - 81, 104, 9, 1).create(BreweryMachine.SLOTS_INVENTORY);
		new ControlPlayerInventory(this);
		new ControlErrorState(this, 133, 79);
	}
}
