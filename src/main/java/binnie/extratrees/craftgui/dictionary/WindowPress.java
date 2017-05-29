package binnie.extratrees.craftgui.dictionary;

import binnie.core.AbstractMod;
import binnie.core.craftgui.geometry.Position;
import binnie.core.craftgui.minecraft.InventoryType;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.minecraft.control.ControlEnergyBar;
import binnie.core.craftgui.minecraft.control.ControlErrorState;
import binnie.core.craftgui.minecraft.control.ControlLiquidTank;
import binnie.core.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.core.craftgui.minecraft.control.ControlSlot;
import binnie.core.machines.Machine;
import binnie.core.machines.power.ErrorState;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.machines.Press;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;

public class WindowPress extends Window {
	public WindowPress(EntityPlayer player, IInventory inventory, Side side) {
		super(194.0f, 192.0f, player, inventory, side);
	}

	public static Window create(EntityPlayer player, IInventory inventory, Side side) {
		return new WindowPress(player, inventory, side);
	}

	@Override
	protected AbstractMod getMod() {
		return ExtraTrees.instance;
	}

	@Override
	protected String getName() {
		return "Press";
	}

	@Override
	public void initialiseClient() {
		setTitle(Machine.getMachine(getInventory()).getPackage().getDisplayName());
		new ControlSlot(this, 24.0f, 52.0f).assign(Press.slotFruit);
		new ControlLiquidTank(this, 99, 32).setTankID(Press.tankWater);
		new ControlEnergyBar(this, 154, 32, 16, 60, Position.BOTTOM);
		new ControlPlayerInventory(this);
		new ControlErrorState(this, 128.0f, 54.0f);
		new ControlFruitPressProgress(this, 62.0f, 24.0f);
		((Window) getSuperParent()).getContainer().getOrCreateSlot(InventoryType.Machine, Press.slotCurrent);
	}

	@Override
	public void recieveGuiNBT(Side side, EntityPlayer player, String name, NBTTagCompound nbt) {
		Press.ComponentFruitPressLogic logic = Machine.getInterface(Press.ComponentFruitPressLogic.class, getInventory());
		super.recieveGuiNBT(side, player, name, nbt);
		if (side != Side.SERVER || !name.equals("fruitpress-click")) {
			return;
		}

		if (logic.canWork() == null && (logic.canProgress() == null || logic.canProgress() instanceof ErrorState.InsufficientPower)) {
			logic.alterProgress(2.0f);
		} else if (side == Side.SERVER && name.equals("clear-fruit")) {
			logic.setProgress(0.0f);
			getInventory().setInventorySlotContents(Press.slotCurrent, null);
		}
	}
}
