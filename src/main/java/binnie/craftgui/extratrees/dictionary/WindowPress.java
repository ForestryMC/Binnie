package binnie.craftgui.extratrees.dictionary;

import binnie.core.AbstractMod;
import binnie.core.machines.Machine;
import binnie.core.machines.power.ErrorState;
import binnie.craftgui.core.geometry.Position;
import binnie.craftgui.minecraft.InventoryType;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.minecraft.control.ControlEnergyBar;
import binnie.craftgui.minecraft.control.ControlErrorState;
import binnie.craftgui.minecraft.control.ControlLiquidTank;
import binnie.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.craftgui.minecraft.control.ControlSlot;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.machines.fruitpress.FruitPressLogic;
import binnie.extratrees.machines.fruitpress.FruitPressMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;

public class WindowPress extends Window {
	public WindowPress(final EntityPlayer player, final IInventory inventory, final Side side) {
		super(194.0f, 192.0f, player, inventory, side);
	}

	@Override
	protected AbstractMod getMod() {
		return ExtraTrees.instance;
	}

	@Override
	protected String getBackgroundTextureName() {
		return "Press";
	}

	@Override
	public void initialiseClient() {
		this.setTitle(Machine.getMachine(this.getInventory()).getPackage().getDisplayName());
		new ControlSlot(this, 24.0f, 52.0f).assign(FruitPressMachine.SLOT_FRUIT);
		new ControlLiquidTank(this, 99, 32).setTankID(FruitPressMachine.TANK_OUTPUT);
		new ControlEnergyBar(this, 154, 32, 16, 60, Position.Bottom);
		new ControlPlayerInventory(this);
		new ControlErrorState(this, 128.0f, 54.0f);
		new ControlFruitPressProgress(this, 62.0f, 24.0f);
		((Window) this.getSuperParent()).getContainer().getOrCreateSlot(InventoryType.Machine, FruitPressMachine.SLOT_CURRENT);
	}

	public static Window create(final EntityPlayer player, final IInventory inventory, final Side side) {
		return new WindowPress(player, inventory, side);
	}

	@Override
	public void recieveGuiNBT(final Side side, final EntityPlayer player, final String name, final NBTTagCompound action) {
		final FruitPressLogic logic = Machine.getInterface(FruitPressLogic.class, this.getInventory());
		super.recieveGuiNBT(side, player, name, action);
		if (side == Side.SERVER && name.equals("fruitpress-click")) {
			if (logic.canWork() == null && (logic.canProgress() == null || logic.canProgress() instanceof ErrorState.InsufficientPower)) {
				logic.alterProgress(2.0f);
			} else if (side == Side.SERVER && name.equals("clear-fruit")) {
				logic.setProgress(0.0f);
				this.getInventory().setInventorySlotContents(FruitPressMachine.SLOT_CURRENT, null);
			}
		}
	}
}
