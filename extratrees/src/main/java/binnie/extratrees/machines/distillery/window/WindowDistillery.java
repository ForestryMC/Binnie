package binnie.extratrees.machines.distillery.window;

import binnie.core.api.gui.Alignment;
import binnie.core.gui.minecraft.Window;
import binnie.core.gui.minecraft.control.ControlEnergyBar;
import binnie.core.gui.minecraft.control.ControlErrorState;
import binnie.core.gui.minecraft.control.ControlLiquidTank;
import binnie.core.gui.minecraft.control.ControlPlayerInventory;
import binnie.core.machines.Machine;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.machines.distillery.DistilleryLogic;
import binnie.extratrees.machines.distillery.DistilleryMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class WindowDistillery extends Window {
	public WindowDistillery(final EntityPlayer player, final IInventory inventory, final Side side) {
		super(224, 192, player, inventory, side);
	}

	@Nullable
	public static Window create(final EntityPlayer player, @Nullable final IInventory inventory, final Side side) {
		if (inventory == null) {
			return null;
		}
		return new WindowDistillery(player, inventory, side);
	}

	@Override
	protected String getModId() {
		return ExtraTrees.instance.getModId();
	}

	@Override
	protected String getBackgroundTextureName() {
		return "Distillery";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void initialiseClient() {
		this.setTitle(Machine.getMachine(this.getInventory()).getPackage().getDisplayName());
		int x = 16;
		new ControlLiquidTank(this, x, 35, DistilleryMachine.TANK_INPUT);
		x += 34;
		new ControlDistilleryProgress(this, x, 32);
		x += 64;
		new ControlLiquidTank(this, x, 35, DistilleryMachine.TANK_OUTPUT);
		x += 34;
		new ControlEnergyBar(this, x, 36, 60, 16, Alignment.LEFT);
		new ControlPlayerInventory(this);
		new ControlErrorState(this, x + 21, 62);
	}

	@Override
	public void receiveGuiNBTOnServer(EntityPlayer player, String name, NBTTagCompound nbt) {
		if ("still-level".equals(name)) {
			DistilleryLogic distilleryLogic = Machine.getInterface(DistilleryLogic.class, Window.get(this).getInventory());
			if (distilleryLogic != null) {
				distilleryLogic.setLevel(nbt.getByte("i"));
			}
		}
	}
}
