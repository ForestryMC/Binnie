package binnie.core.machines.inventory;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import binnie.core.Constants;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

import binnie.core.machines.IMachine;

public class ComponentInventorySlots extends ComponentInventory implements IInventoryMachine, IInventorySlots {
	private Map<Integer, InventorySlot> inventory;

	public ComponentInventorySlots(final IMachine machine) {
		super(machine);
		this.inventory = new LinkedHashMap<>();
	}

	@Override
	public void clear() {
		for (InventorySlot slot : this.inventory.values()) {
			slot.setContent(ItemStack.EMPTY);
		}
	}

	@Override
	public void setField(int id, int value) {

	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		InventorySlot inventorySlot = this.inventory.get(index);
		ItemStack content = inventorySlot.getItemStack();
		inventorySlot.setContent(ItemStack.EMPTY);
		return content;
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentString("");
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
			return this.inventory.get(index).getItemStack();
		}
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack decrStackSize(final int index, final int amount) {
		if (this.inventory.containsKey(index)) {
			final ItemStack stack = this.inventory.get(index).decrStackSize(amount);
			this.markDirty();
			return stack;
		}
		return ItemStack.EMPTY;
	}

	@Override
	public void setInventorySlotContents(final int index, final ItemStack itemStack) {
		if (this.inventory.containsKey(index) && (itemStack.isEmpty() || this.inventory.get(index).isValid(itemStack))) {
			this.inventory.get(index).setContent(itemStack);
		}
		this.markDirty();
	}

	@Override
	public boolean isEmpty() {
		return this.inventory.isEmpty();
	}
	
	@Override
	public String getName() {
		return "";
	}
	
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(final EntityPlayer var1) {
		return true;
	}

	@Override
	public void closeInventory(EntityPlayer player) {

	}

	@Override
	public void openInventory(EntityPlayer player) {

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
	public NBTTagCompound writeToNBT(final NBTTagCompound nbttagcompound2) {
		NBTTagCompound nbttagcompound = super.writeToNBT(nbttagcompound2);
		final NBTTagList inventoryNBT = new NBTTagList();
		for (final Map.Entry<Integer, InventorySlot> entry : this.inventory.entrySet()) {
			final NBTTagCompound slotNBT = new NBTTagCompound();
			slotNBT.setInteger("id", entry.getKey());
			entry.getValue().writeToNBT(slotNBT);
			inventoryNBT.appendTag(slotNBT);
		}
		nbttagcompound.setTag("inventory", inventoryNBT);
		return nbttagcompound;
	}

	public final InventorySlot addSlot(final int index, final ResourceLocation unlocLocation) {
		InventorySlot slot = new InventorySlot(index, unlocLocation);
		this.inventory.put(index, slot);
		return slot;
	}

	/**
	 * @deprecated use {@link #addSlot(int, ResourceLocation)}
	 */
	@Deprecated
	public final InventorySlot addSlot(final int index, final String unlocName) {
		ResourceLocation unlocLocation = new ResourceLocation(Constants.CORE_MOD_ID, "gui.slot." + unlocName);
		return addSlot(index, unlocLocation);
	}

	public final InventorySlot[] addSlotArray(final int[] indexes, final ResourceLocation unlocName) {
		for (final int k : indexes) {
			this.addSlot(k, unlocName);
		}
		return this.getSlots(indexes);
	}

	/**
	 * @deprecated use {@link #addSlotArray(int[], ResourceLocation)}
	 */
	@Deprecated
	public final InventorySlot[] addSlotArray(final int[] indexes, final String unlocName) {
		for (final int k : indexes) {
			this.addSlot(k, unlocName);
		}
		return this.getSlots(indexes);
	}

	@Override
	@Nullable
	public InventorySlot getSlot(final int index) {
		if (this.inventory.containsKey(index)) {
			return this.inventory.get(index);
		}
		return null;
	}

	public InventorySlot[] getSlots(final int[] indexes) {
		final List<InventorySlot> list = new ArrayList<>();
		for (final int i : indexes) {
			if (this.getSlot(i) != null) {
				list.add(this.getSlot(i));
			}
		}
		return list.toArray(new InventorySlot[0]);
	}

	private boolean isReadOnly(final int slot) {
		final InventorySlot iSlot = this.getSlot(slot);
		return iSlot == null || iSlot.isReadOnly();
	}

	@Override
	public boolean hasCustomName() {
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
			final ItemStack stack = slot.getItemStack();
			if (!slot.isRecipe() && !stack.isEmpty()) {
				final float f = this.getMachine().getWorld().rand.nextFloat() * 0.8f + 0.1f;
				final float f2 = this.getMachine().getWorld().rand.nextFloat() * 0.8f + 0.1f;
				final float f3 = this.getMachine().getWorld().rand.nextFloat() * 0.8f + 0.1f;
				if (stack.getCount() == 0) {
					stack.setCount(1);
				}
				final EntityItem entityitem = new EntityItem(this.getMachine().getWorld(), this.getMachine().getTileEntity().getPos().getX() + f, this.getMachine().getTileEntity().getPos().getY() + f2, this.getMachine().getTileEntity().getPos().getZ() + f3, stack.copy());
				final float accel = 0.05f;
				entityitem.motionX = (float) this.getMachine().getWorld().rand.nextGaussian() * accel;
				entityitem.motionY = (float) this.getMachine().getWorld().rand.nextGaussian() * accel + 0.2f;
				entityitem.motionZ = (float) this.getMachine().getWorld().rand.nextGaussian() * accel;
				this.getMachine().getWorld().spawnEntity(entityitem);
			}
		}
	}

	@Override
	public int[] getSlotsForFace(final EnumFacing facing) {
		final List<Integer> slots = new ArrayList<>();
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
	public boolean canInsertItem(final int index, final ItemStack itemstack, final EnumFacing direction) {
		if (this.isItemValidForSlot(index, itemstack)) {
			InventorySlot slot = this.getSlot(index);
			if (slot != null && slot.canInsert(direction)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean canExtractItem(final int index, final ItemStack itemstack, final EnumFacing direction) {
		InventorySlot slot = this.getSlot(index);
		return slot != null && slot.canExtract(direction);
	}
}
