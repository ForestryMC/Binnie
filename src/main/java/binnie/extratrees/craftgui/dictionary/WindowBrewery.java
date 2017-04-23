// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.craftgui.dictionary;

import binnie.core.craftgui.minecraft.control.ControlErrorState;
import binnie.core.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.core.craftgui.geometry.Position;
import binnie.core.craftgui.minecraft.control.ControlEnergyBar;
import binnie.core.craftgui.minecraft.control.ControlLiquidTank;
import binnie.core.craftgui.minecraft.control.ControlSlot;
import binnie.extratrees.machines.Brewery;
import binnie.core.craftgui.minecraft.control.ControlSlotArray;
import binnie.core.machines.Machine;
import binnie.extratrees.ExtraTrees;
import binnie.core.AbstractMod;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.inventory.IInventory;
import net.minecraft.entity.player.EntityPlayer;
import binnie.core.craftgui.minecraft.Window;

public class WindowBrewery extends Window
{
	public WindowBrewery(final EntityPlayer player, final IInventory inventory, final Side side) {
		super(228.0f, 218.0f, player, inventory, side);
	}

	@Override
	protected AbstractMod getMod() {
		return ExtraTrees.instance;
	}

	@Override
	protected String getName() {
		return "Brewery";
	}

	@Override
	public void initialiseClient() {
		this.setTitle(Machine.getMachine(this.getInventory()).getPackage().getDisplayName());
		new ControlSlotArray(this, 42, 32, 1, 3).create(Brewery.slotRecipeGrains);
		new ControlSlot(this, 16.0f, 41.0f).assign(Brewery.slotRecipeInput);
		new ControlSlot(this, 105.0f, 77.0f).assign(Brewery.slotRecipeYeast);
		new ControlLiquidTank(this, 76, 32).setTankID(Brewery.tankInput);
		new ControlLiquidTank(this, 162, 32).setTankID(Brewery.tankOutput);
		new ControlEnergyBar(this, 196, 32, 16, 60, Position.Bottom);
		new ControlBreweryProgress(this, 110.0f, 32.0f);
		new ControlSlotArray(this, (int) (this.getSize().x() / 2.0f - 81.0f), 104, 9, 1).create(Brewery.slotInventory);
		new ControlPlayerInventory(this);
		new ControlErrorState(this, 133.0f, 79.0f);
	}

	public static Window create(final EntityPlayer player, final IInventory inventory, final Side side) {
		return new WindowBrewery(player, inventory, side);
	}
}
