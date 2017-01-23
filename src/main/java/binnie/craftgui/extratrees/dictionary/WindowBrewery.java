package binnie.craftgui.extratrees.dictionary;

import binnie.core.AbstractMod;
import binnie.core.machines.Machine;
import binnie.craftgui.core.geometry.Position;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.minecraft.control.ControlEnergyBar;
import binnie.craftgui.minecraft.control.ControlErrorState;
import binnie.craftgui.minecraft.control.ControlLiquidTank;
import binnie.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.craftgui.minecraft.control.ControlSlot;
import binnie.craftgui.minecraft.control.ControlSlotArray;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.machines.brewery.BreweryMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;

public class WindowBrewery extends Window {
	public WindowBrewery(final EntityPlayer player, final IInventory inventory, final Side side) {
		super(228.0f, 218.0f, player, inventory, side);
	}

	@Override
	protected AbstractMod getMod() {
		return ExtraTrees.instance;
	}

	@Override
	protected String getBackgroundTextureName() {
		return "Brewery";
	}

	@Override
	public void initialiseClient() {
		this.setTitle(Machine.getMachine(this.getInventory()).getPackage().getDisplayName());
		new ControlSlotArray(this, 42, 32, 1, 3).create(BreweryMachine.SLOT_RECIPE_GRAINS);
		new ControlSlot(this, 16.0f, 41.0f).assign(BreweryMachine.SLOT_RECIPE_INPUT);
		new ControlSlot(this, 105.0f, 77.0f).assign(BreweryMachine.SLOT_YEAST);
		new ControlLiquidTank(this, 76, 32).setTankID(BreweryMachine.TANK_INPUT);
		new ControlLiquidTank(this, 162, 32).setTankID(BreweryMachine.TANK_OUTPUT);
		new ControlEnergyBar(this, 196, 32, 16, 60, Position.Bottom);
		new ControlBreweryProgress(this, 110.0f, 32.0f);
		new ControlSlotArray(this, (int) (this.getSize().x() / 2.0f - 81.0f), 104, 9, 1).create(BreweryMachine.SLOTS_INVENTORY);
		new ControlPlayerInventory(this);
		new ControlErrorState(this, 133.0f, 79.0f);
	}

	public static Window create(final EntityPlayer player, final IInventory inventory, final Side side) {
		return new WindowBrewery(player, inventory, side);
	}
}
