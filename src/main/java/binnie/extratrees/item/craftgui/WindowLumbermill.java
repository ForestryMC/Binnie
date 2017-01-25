package binnie.extratrees.item.craftgui;

import binnie.core.AbstractMod;
import binnie.core.machines.Machine;
import binnie.craftgui.core.geometry.Position;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.minecraft.control.ControlEnergyBar;
import binnie.craftgui.minecraft.control.ControlErrorState;
import binnie.craftgui.minecraft.control.ControlLiquidTank;
import binnie.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.craftgui.minecraft.control.ControlSlot;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.machines.lumbermill.LumbermillMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;

public class WindowLumbermill extends Window {
	public WindowLumbermill(final EntityPlayer player, final IInventory inventory, final Side side) {
		super(220, 192, player, inventory, side);
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
		new ControlSlot(this, 42, 43).assign(LumbermillMachine.SLOT_LOG);
		new ControlSlot(this, 148, 43).assign(LumbermillMachine.SLOT_PLANKS);
		new ControlSlot(this, 172, 28).assign(LumbermillMachine.SLOT_BARK);
		new ControlSlot(this, 172, 58).assign(LumbermillMachine.SLOT_SAWDUST);
		new ControlLumbermillProgress(this, 70, 43);
		new ControlLiquidTank(this, 16, 32);
		new ControlEnergyBar(this, 8, 112, 16, 60, Position.Bottom);
		new ControlPlayerInventory(this);
		new ControlErrorState(this, 95, 73);
	}

	public static Window create(final EntityPlayer player, final IInventory inventory, final Side side) {
		return new WindowLumbermill(player, inventory, side);
	}
}
