package binnie.extratrees.craftgui.dictionary;

import binnie.core.AbstractMod;
import binnie.core.craftgui.geometry.Position;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.minecraft.control.ControlEnergyBar;
import binnie.core.craftgui.minecraft.control.ControlErrorState;
import binnie.core.craftgui.minecraft.control.ControlLiquidTank;
import binnie.core.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.core.machines.Machine;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.machines.Distillery;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

public class WindowDistillery extends Window {
	public WindowDistillery(EntityPlayer player, IInventory inventory, Side side) {
		super(224.0f, 192.0f, player, inventory, side);
	}

	public static Window create(EntityPlayer player, IInventory inventory, Side side) {
		return new WindowDistillery(player, inventory, side);
	}

	@Override
	protected AbstractMod getMod() {
		return ExtraTrees.instance;
	}

	@Override
	protected String getName() {
		return "Distillery";
	}

	@Override
	public void initialiseClient() {
		setTitle(Machine.getMachine(getInventory()).getPackage().getDisplayName());
		int x = 16;
		new ControlLiquidTank(this, x, 35).setTankID(Distillery.tankInput);
		x += 34;
		new ControlDistilleryProgress(this, x, 32.0f);
		x += 64;
		new ControlLiquidTank(this, x, 35).setTankID(Distillery.tankOutput);
		x += 34;
		new ControlEnergyBar(this, x, 36, 60, 16, Position.LEFT);
		new ControlPlayerInventory(this);
		new ControlErrorState(this, x + 21, 62.0f);
	}
}
