// 
// Decompiled by Procyon v0.5.30
// 

package binnie.craftgui.extratrees.dictionary;

import net.minecraft.item.ItemStack;
import binnie.core.machines.power.ErrorState;
import net.minecraft.nbt.NBTTagCompound;
import binnie.craftgui.minecraft.InventoryType;
import binnie.craftgui.minecraft.control.ControlErrorState;
import binnie.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.craftgui.core.geometry.Position;
import binnie.craftgui.minecraft.control.ControlEnergyBar;
import binnie.craftgui.minecraft.control.ControlLiquidTank;
import binnie.extratrees.machines.Press;
import binnie.craftgui.minecraft.control.ControlSlot;
import binnie.core.machines.Machine;
import binnie.extratrees.ExtraTrees;
import binnie.core.AbstractMod;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.inventory.IInventory;
import net.minecraft.entity.player.EntityPlayer;
import binnie.craftgui.minecraft.Window;

public class WindowPress extends Window
{
	public WindowPress(final EntityPlayer player, final IInventory inventory, final Side side) {
		super(194.0f, 192.0f, player, inventory, side);
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
		this.setTitle(Machine.getMachine(this.getInventory()).getPackage().getDisplayName());
		new ControlSlot(this, 24.0f, 52.0f).assign(Press.slotFruit);
		new ControlLiquidTank(this, 99, 32).setTankID(Press.tankWater);
		new ControlEnergyBar(this, 154, 32, 16, 60, Position.Bottom);
		new ControlPlayerInventory(this);
		new ControlErrorState(this, 128.0f, 54.0f);
		new ControlFruitPressProgress(this, 62.0f, 24.0f);
		((Window) this.getSuperParent()).getContainer().getOrCreateSlot(InventoryType.Machine, Press.slotCurrent);
	}

	public static Window create(final EntityPlayer player, final IInventory inventory, final Side side) {
		return new WindowPress(player, inventory, side);
	}

	@Override
	public void recieveGuiNBT(final Side side, final EntityPlayer player, final String name, final NBTTagCompound nbt) {
		final Press.ComponentFruitPressLogic logic = Machine.getInterface(Press.ComponentFruitPressLogic.class, this.getInventory());
		super.recieveGuiNBT(side, player, name, nbt);
		if (side == Side.SERVER && name.equals("fruitpress-click")) {
			if (logic.canWork() == null && (logic.canProgress() == null || logic.canProgress() instanceof ErrorState.InsufficientPower)) {
				logic.alterProgress(2.0f);
			}
			else if (side == Side.SERVER && name.equals("clear-fruit")) {
				logic.setProgress(0.0f);
				this.getInventory().setInventorySlotContents(Press.slotCurrent, (ItemStack) null);
			}
		}
	}
}
