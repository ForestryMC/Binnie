package binnie.genetics.machine.indexer;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import binnie.core.machines.Machine;
import binnie.core.machines.inventory.ComponentInventory;
import binnie.core.machines.inventory.SetList;

public abstract class ComponentIndexerInventory<T> extends ComponentInventory implements IInventory {
	protected int guiRefreshCounter;
	protected List<Integer> sortedInventory;
	private final int indexerSize;
	private final List<ItemStack> indexerInventory;
	@Nullable
	private T sortingMode;
	private boolean needsSorting;

	public ComponentIndexerInventory(Machine machine) {
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
	public ItemStack getStackInSlot(int index) {
		if (index >= 0 && index < this.indexerInventory.size()) {
			return this.indexerInventory.get(index);
		}
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack decrStackSize(int index, int amount) {
		ItemStack stackInSlot = this.getStackInSlot(index);
		if (!stackInSlot.isEmpty()) {
			ItemStack returnStack = stackInSlot.copy();
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
	public void setInventorySlotContents(int index, ItemStack itemStack) {
		if (index >= 0 && index < this.indexerInventory.size()) {
			this.indexerInventory.set(index, itemStack);
		} else if (!itemStack.isEmpty()) {
			this.indexerInventory.add(itemStack);
		}
		this.needsSorting = true;
		this.markDirty();
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer var1) {
		return true;
	}

	@Nullable
	public T getMode() {
		return this.sortingMode;
	}

	public void setMode(T mode) {
		this.sortingMode = mode;
		this.needsSorting = true;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		NBTTagCompound nbttagcompound = super.writeToNBT(compound);
		NBTTagList indexerNBT = new NBTTagList();
		for (ItemStack item : this.indexerInventory) {
			NBTTagCompound itemNBT = new NBTTagCompound();
			item.writeToNBT(itemNBT);
			indexerNBT.appendTag(itemNBT);
		}
		nbttagcompound.setTag("indexer", indexerNBT);
		return nbttagcompound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		NBTTagList indexerNBT = compound.getTagList("indexer", 10);
		this.indexerInventory.clear();
		for (int i = 0; i < indexerNBT.tagCount(); ++i) {
			NBTTagCompound itemNBT = indexerNBT.getCompoundTagAt(i);
			this.setInventorySlotContents(i, new ItemStack(itemNBT));
		}
		this.needsSorting = true;
		this.markDirty();
	}

	public abstract void Sort();

	public List<ItemStack> getIndexerInventory() {
		return indexerInventory;
	}

	@Nullable
	public T getSortingMode() {
		return sortingMode;
	}

	public boolean isNeedsSorting() {
		return needsSorting;
	}

	public void setNeedsSorting(boolean needsSorting) {
		this.needsSorting = needsSorting;
	}

	public int getGuiRefreshCounter() {
		return guiRefreshCounter;
	}

	public List<Integer> getSortedInventory() {
		return sortedInventory;
	}
}
