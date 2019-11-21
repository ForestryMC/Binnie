package binnie.core.machines.base;

import binnie.core.machines.inventory.IInventoryMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

class DefaultMachineInventory implements IInventoryMachine {
	private final DefaultInventory inventory;

	public DefaultMachineInventory() {
		this.inventory = new DefaultInventory();
	}

	@Override
	public IInventory getInventory() {
		return inventory;
	}

	@Override
	public boolean isUsableByPlayer(final EntityPlayer entityplayer) {
		return false;
	}

	@Override
	public void markDirty() {
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return new int[0];
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		return false;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return false;
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentString("");
	}
}
