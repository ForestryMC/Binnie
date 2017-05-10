package binnie.genetics.machine.indexer;

import binnie.Binnie;
import binnie.core.machines.Machine;
import binnie.core.machines.inventory.ComponentInventory;
import binnie.core.machines.inventory.SetList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ComponentIndexerInventory<T> extends ComponentInventory implements IInventory {
	int indexerSize;
	public int guiRefreshCounter;
	List<ItemStack> indexerInventory;
	public List<Integer> sortedInventory;
	@Nullable
	T sortingMode;
	boolean needsSorting;

	public ComponentIndexerInventory(final Machine machine) {
		super(machine);
		this.indexerSize = -1;
		this.guiRefreshCounter = 0;
		this.indexerInventory = new SetList<>();
		this.sortedInventory = new SetList<>();
		this.needsSorting = true;
	}

	@Override
	public int getSizeInventory() {
		if (this.indexerSize > 0) {
			return this.indexerSize + 1;
		}
		return this.indexerInventory.size() + 1;
	}

	@Override
	public ItemStack getStackInSlot(final int index) {
		if (index >= 0 && index < this.indexerInventory.size()) {
			return this.indexerInventory.get(index);
		}
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack decrStackSize(final int index, final int amount) {
		ItemStack stackInSlot = this.getStackInSlot(index);
		if (!stackInSlot.isEmpty()) {
			final ItemStack returnStack = stackInSlot.copy();
			this.setInventorySlotContents(index, ItemStack.EMPTY);
			return returnStack;
		}
		return ItemStack.EMPTY;
	}

	@Override
	public void markDirty() {
		super.markDirty();
		++this.guiRefreshCounter;
	}

	@Override
	public void setInventorySlotContents(final int index, final ItemStack itemStack) {
		if (index >= 0 && index < this.indexerInventory.size()) {
			this.indexerInventory.set(index, itemStack);
		} else if (!itemStack.isEmpty()) {
			this.indexerInventory.add(itemStack);
		}
		this.needsSorting = true;
		this.markDirty();
	}

//	@Override
//	public ItemStack getStackInSlotOnClosing(final int var1) {
//		return null;
//	}
//
//	@Override
//	public String getInventoryName() {
//		return "";
//	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(final EntityPlayer var1) {
		return true;
	}

//	@Override
//	public void openInventory() {
//	}
//
//	@Override
//	public void closeInventory() {
//	}

	public void setMode(final T mode) {
		this.sortingMode = mode;
		this.needsSorting = true;
	}

	@Nullable
	public T getMode() {
		return this.sortingMode;
	}

	@Override
	public NBTTagCompound writeToNBT(final NBTTagCompound nbttagcompound2) {
		NBTTagCompound nbttagcompound = super.writeToNBT(nbttagcompound2);
		final NBTTagList indexerNBT = new NBTTagList();
		for (final ItemStack item : this.indexerInventory) {
			final NBTTagCompound itemNBT = new NBTTagCompound();
			item.writeToNBT(itemNBT);
			indexerNBT.appendTag(itemNBT);
		}
		nbttagcompound.setTag("indexer", indexerNBT);
		return nbttagcompound;
	}

	@Override
	public void readFromNBT(final NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		final NBTTagList indexerNBT = nbttagcompound.getTagList("indexer", 10);
		this.indexerInventory.clear();
		for (int i = 0; i < indexerNBT.tagCount(); ++i) {
			final NBTTagCompound itemNBT = indexerNBT.getCompoundTagAt(i);
			this.setInventorySlotContents(i, new ItemStack(itemNBT));
		}
		this.needsSorting = true;
		this.markDirty();
	}

	public abstract void Sort();

}
