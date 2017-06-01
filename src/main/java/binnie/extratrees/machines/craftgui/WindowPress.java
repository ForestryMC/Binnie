package binnie.extratrees.machines.craftgui;

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
import binnie.extratrees.machines.fruitpress.FruitPressLogic;
import binnie.extratrees.machines.fruitpress.FruitPressMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nullable;

public class WindowPress extends Window {
	public WindowPress(final EntityPlayer player, final IInventory inventory, final Side side) {
		super(194, 192, player, inventory, side);
	}

	@Nullable
	public static Window create(final EntityPlayer player, @Nullable final IInventory inventory, final Side side) {
		if (inventory == null) {
			return null;
		}
		return new WindowPress(player, inventory, side);
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
		new ControlSlot.Builder(this, 24, 52).assign(FruitPressMachine.SLOT_FRUIT);
		new ControlLiquidTank(this, 99, 32).setTankID(FruitPressMachine.TANK_OUTPUT);
		new ControlEnergyBar(this, 154, 32, 16, 60, Position.BOTTOM);
		new ControlPlayerInventory(this);
		new ControlErrorState(this, 128, 54);
		new ControlFruitPressProgress(this, 62, 24);
		((Window) this.getTopParent()).getContainer().getOrCreateSlot(InventoryType.Machine, FruitPressMachine.SLOT_CURRENT);
	}

	@Override
	public void receiveGuiNBTOnServer(final EntityPlayer player, final String name, final NBTTagCompound nbt) {
		super.receiveGuiNBTOnServer(player, name, nbt);
		final FruitPressLogic logic = Machine.getInterface(FruitPressLogic.class, this.getInventory());
		if (logic != null) {
			if (name.equals("fruitpress-click") && logic.canWork() == null && (logic.canProgress() == null || logic.canProgress() instanceof ErrorState.InsufficientPower)) {
				logic.alterProgress(2.0f);
			} else if (name.equals("clear-fruit")) {
				logic.setProgress(0.0f);
				this.getInventory().setInventorySlotContents(FruitPressMachine.SLOT_CURRENT, ItemStack.EMPTY);
			}
		}
	}
}
