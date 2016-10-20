// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.machines.inventory;

import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import java.util.List;
import java.util.ArrayList;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import java.util.LinkedHashMap;
import binnie.core.machines.IMachine;
import java.util.Map;

public class ComponentInventorySlots extends ComponentInventory implements IInventoryMachine, IInventorySlots
{
	private Map<Integer, InventorySlot> inventory;

	public ComponentInventorySlots(final IMachine machine) {
		super(machine);
		this.inventory = new LinkedHashMap<Integer, InventorySlot>();
	}

	@Override
	public int getSizeInventory() {
		int size = 0;
		for (final Integer index : this.inventory.keySet()) {
			size = Math.max(size, index + 1);
		}
		return size;
	}

	@Override
	public ItemStack getStackInSlot(final int index) {
		if (this.inventory.containsKey(index)) {
			return this.inventory.get(index).getContent();
		}
		return null;
	}

	@Override
	public ItemStack decrStackSize(final int index, final int amount) {
		if (this.inventory.containsKey(index)) {
			final ItemStack stack = this.inventory.get(index).decrStackSize(amount);
			this.markDirty();
			return stack;
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(final int var1) {
		return null;
	}

	@Override
	public void setInventorySlotContents(final int index, final ItemStack itemStack) {
		if (this.inventory.containsKey(index) && (itemStack == null || this.inventory.get(index).isValid(itemStack))) {
			this.inventory.get(index).setContent(itemStack);
		}
		this.markDirty();
	}

	protected void transferItem(final int indexFrom, final int indexTo) {
		if (this.inventory.containsKey(indexFrom) && this.inventory.containsKey(indexTo)) {
			final ItemStack newStack = this.inventory.get(indexFrom).getContent().copy();
			this.inventory.get(indexFrom).setContent(null);
			this.inventory.get(indexTo).setContent(newStack);
		}
		this.markDirty();
	}

	@Override
	public String getInventoryName() {
		return "";
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(final EntityPlayer var1) {
		return true;
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public void readFromNBT(final NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		if (nbttagcompound.hasKey("inventory")) {
			final NBTTagList inventoryNBT = nbttagcompound.getTagList("inventory", 10);
			for (int i = 0; i < inventoryNBT.tagCount(); ++i) {
				final NBTTagCompound slotNBT = inventoryNBT.getCompoundTagAt(i);
				int index = slotNBT.getInteger("id");
				if (slotNBT.hasKey("Slot")) {
					index = (slotNBT.getByte("Slot") & 0xFF);
				}
				if (this.inventory.containsKey(index)) {
					this.inventory.get(index).readFromNBT(slotNBT);
				}
			}
		}
		this.markDirty();
	}

	@Override
	public void writeToNBT(final NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		final NBTTagList inventoryNBT = new NBTTagList();
		for (final Map.Entry<Integer, InventorySlot> entry : this.inventory.entrySet()) {
			final NBTTagCompound slotNBT = new NBTTagCompound();
			slotNBT.setInteger("id", entry.getKey());
			entry.getValue().writeToNBT(slotNBT);
			inventoryNBT.appendTag(slotNBT);
		}
		nbttagcompound.setTag("inventory", inventoryNBT);
	}

	@Override
	public final InventorySlot addSlot(final int index, final String unlocName) {
		this.inventory.put(index, new InventorySlot(index, unlocName));
		return this.getSlot(index);
	}

	@Override
	public final InventorySlot[] addSlotArray(final int[] indexes, final String unlocName) {
		for (final int k : indexes) {
			this.addSlot(k, unlocName);
		}
		return this.getSlots(indexes);
	}

	@Override
	public InventorySlot getSlot(final int index) {
		if (this.inventory.containsKey(index)) {
			return this.inventory.get(index);
		}
		return null;
	}

	@Override
	public InventorySlot[] getAllSlots() {
		return this.inventory.values().toArray(new InventorySlot[0]);
	}

	@Override
	public InventorySlot[] getSlots(final int[] indexes) {
		final List<InventorySlot> list = new ArrayList<InventorySlot>();
		for (final int i : indexes) {
			if (this.getSlot(i) != null) {
				list.add(this.getSlot(i));
			}
		}
		return list.toArray(new InventorySlot[0]);
	}

	@Override
	public boolean isReadOnly(final int slot) {
		final InventorySlot iSlot = this.getSlot(slot);
		return iSlot == null || iSlot.isReadOnly();
	}

	@Override
	public boolean hasCustomInventoryName() {
		return true;
	}

	@Override
	public boolean isItemValidForSlot(final int slot, final ItemStack itemStack) {
		final InventorySlot iSlot = this.getSlot(slot);
		return iSlot != null && (iSlot.isValid(itemStack) && !this.isReadOnly(slot));
	}

	@Override
	public void onDestruction() {
		for (final InventorySlot slot : this.inventory.values()) {
			final ItemStack stack = slot.getContent();
			if (!slot.isRecipe() && stack != null) {
				final float f = this.getMachine().getWorld().rand.nextFloat() * 0.8f + 0.1f;
				final float f2 = this.getMachine().getWorld().rand.nextFloat() * 0.8f + 0.1f;
				final float f3 = this.getMachine().getWorld().rand.nextFloat() * 0.8f + 0.1f;
				if (stack.stackSize == 0) {
					stack.stackSize = 1;
				}
				final EntityItem entityitem = new EntityItem(this.getMachine().getWorld(), this.getMachine().getTileEntity().xCoord + f, this.getMachine().getTileEntity().yCoord + f2, this.getMachine().getTileEntity().zCoord + f3, stack.copy());
				final float accel = 0.05f;
				entityitem.motionX = (float) this.getMachine().getWorld().rand.nextGaussian() * accel;
				entityitem.motionY = (float) this.getMachine().getWorld().rand.nextGaussian() * accel + 0.2f;
				entityitem.motionZ = (float) this.getMachine().getWorld().rand.nextGaussian() * accel;
				this.getMachine().getWorld().spawnEntityInWorld(entityitem);
			}
		}
	}

	@Override
	public int[] getAccessibleSlotsFromSide(final int var1) {
		final List<Integer> slots = new ArrayList<Integer>();
		for (final InventorySlot slot : this.inventory.values()) {
			if (slot.canInsert() || slot.canExtract()) {
				slots.add(slot.getIndex());
			}
		}
		final int[] ids = new int[slots.size()];
		for (int i = 0; i < slots.size(); ++i) {
			ids[i] = slots.get(i);
		}
		return ids;
	}

	@Override
	public boolean canInsertItem(final int i, final ItemStack itemstack, final int j) {
		return this.isItemValidForSlot(i, itemstack) && this.getSlot(i).canInsert(ForgeDirection.getOrientation(j));
	}

	@Override
	public boolean canExtractItem(final int i, final ItemStack itemstack, final int j) {
		return this.getSlot(i).canExtract(ForgeDirection.getOrientation(j));
	}
}
