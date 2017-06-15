package binnie.extratrees.machines.craftgui;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

import net.minecraftforge.fml.relauncher.Side;

import binnie.core.AbstractMod;
import binnie.core.craftgui.geometry.Position;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.minecraft.control.ControlEnergyBar;
import binnie.core.craftgui.minecraft.control.ControlErrorState;
import binnie.core.craftgui.minecraft.control.ControlLiquidTank;
import binnie.core.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.core.craftgui.minecraft.control.ControlSlot;
import binnie.core.machines.Machine;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.machines.lumbermill.LumbermillMachine;

public class WindowLumbermill extends Window {
	public WindowLumbermill(final EntityPlayer player, final IInventory inventory, final Side side) {
		super(220, 192, player, inventory, side);
	}

	@Nullable
	public static Window create(final EntityPlayer player, @Nullable final IInventory inventory, final Side side) {
		if (inventory == null) {
			return null;
		}
		return new WindowLumbermill(player, inventory, side);
	}

	@Override
	protected AbstractMod getMod() {
		return ExtraTrees.instance;
	}

	@Override
	protected String getBackgroundTextureName() {
		return "Lumbermill";
	}

	@Override
	public void initialiseClient() {
		this.setTitle(Machine.getMachine(this.getInventory()).getPackage().getDisplayName());
		new ControlSlot.Builder(this, 42, 43).assign(LumbermillMachine.SLOT_LOG);
		new ControlSlot.Builder(this, 148, 43).assign(LumbermillMachine.SLOT_PLANKS);
		new ControlSlot.Builder(this, 172, 28).assign(LumbermillMachine.SLOT_BARK);
		new ControlSlot.Builder(this, 172, 58).assign(LumbermillMachine.SLOT_SAWDUST);
		new ControlLumbermillProgress(this, 70, 43);
		new ControlLiquidTank(this, 16, 32);
		new ControlEnergyBar(this, 8, 112, 16, 60, Position.BOTTOM);
		new ControlPlayerInventory(this);
		new ControlErrorState(this, 95, 73);
	}
}
