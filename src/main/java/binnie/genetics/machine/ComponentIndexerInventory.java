package binnie.genetics.machine;

import binnie.Binnie;
import binnie.core.machines.Machine;
import binnie.core.machines.inventory.ComponentInventory;
import binnie.core.machines.inventory.SetList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO unused class?
public abstract class ComponentIndexerInventory<T> extends ComponentInventory implements IInventory {
	public int guiRefreshCounter;
	public List<Integer> sortedInventory;
	int indexerSize;
	List<ItemStack> indexerInventory;
	T sortingMode;
	boolean needsSorting;

	public ComponentIndexerInventory(Machine machine) {
		super(machine);
		indexerSize = -1;
		guiRefreshCounter = 0;
		indexerInventory = new SetList<>();
		sortedInventory = new SetList<>();
		needsSorting = true;
	}

	@Override
	public int getSizeInventory() {
		if (indexerSize > 0) {
			return indexerSize + 1;
		}
		return indexerInventory.size() + 1;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		if (index >= 0 && index < indexerInventory.size()) {
			return indexerInventory.get(index);
		}
		return null;
	}

	@Override
	public ItemStack decrStackSize(int index, int amount) {
		if (index >= 0) {
			ItemStack returnStack = getStackInSlot(index).copy();
			setInventorySlotContents(index, null);
			return returnStack;
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		return null;
	}

	@Override
	public void markDirty() {
		super.markDirty();
		guiRefreshCounter++;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack itemStack) {
		if (index >= 0 && index < indexerInventory.size()) {
			indexerInventory.set(index, itemStack);
		} else if (itemStack != null) {
			indexerInventory.add(itemStack);
		}
		needsSorting = true;
		markDirty();
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
	public boolean isUseableByPlayer(EntityPlayer var1) {
		return true;
	}

	@Override
	public void openInventory() {
		// ignored
	}

	@Override
	public void closeInventory() {
		// ignored
	}

	public T getMode() {
		return sortingMode;
	}

	public void setMode(T mode) {
		sortingMode = mode;
		needsSorting = true;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		NBTTagList indexerNBT = new NBTTagList();
		for (ItemStack item : indexerInventory) {
			NBTTagCompound itemNBT = new NBTTagCompound();
			item.writeToNBT(itemNBT);
			indexerNBT.appendTag(itemNBT);
		}
		nbttagcompound.setTag("indexer", indexerNBT);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		NBTTagList indexerNBT = nbttagcompound.getTagList("indexer", 10);
		indexerInventory.clear();
		for (int i = 0; i < indexerNBT.tagCount(); ++i) {
			NBTTagCompound itemNBT = indexerNBT.getCompoundTagAt(i);
			setInventorySlotContents(i, ItemStack.loadItemStackFromNBT(itemNBT));
		}
		needsSorting = true;
		markDirty();
	}

	public abstract void Sort();

	public static class ComponentApiaristIndexerInventory extends ComponentIndexerInventory<ComponentApiaristIndexerInventory.Mode> implements IInventory {
		public ComponentApiaristIndexerInventory(Machine machine) {
			super(machine);
		}

		@Override
		public void Sort() {
			int i = 0;
			while (i < indexerInventory.size()) {
				if (indexerInventory.get(i) == null) {
					indexerInventory.remove(i);
				} else {
					++i;
				}
			}
			if (!needsSorting) {
				return;
			}
			needsSorting = false;
			++guiRefreshCounter;
			switch (sortingMode) {
				case Species:
				case Type: {
					class SpeciesList {
						public List<Integer> drones;
						public List<Integer> queens;
						public List<Integer> princesses;
						public List<ItemStack> bees;

						SpeciesList() {
							drones = new ArrayList<>();
							queens = new ArrayList<>();
							princesses = new ArrayList<>();
							bees = new ArrayList<>();
						}

						public void add(ItemStack stack) {
							bees.add(stack);
						}
					}

					Map<Integer, SpeciesList> speciesList = new HashMap<Integer, SpeciesList>();
					for (ItemStack itemStack : indexerInventory) {
						int species = itemStack.getItemDamage();
						if (!speciesList.containsKey(species)) {
							speciesList.put(species, new SpeciesList());
						}
						speciesList.get(species).add(itemStack);
					}

					for (SpeciesList sortableList : speciesList.values()) {
						for (ItemStack beeStack : sortableList.bees) {
							if (Binnie.Genetics.getBeeRoot().isDrone(beeStack)) {
								sortableList.drones.add(indexerInventory.indexOf(beeStack));
							} else if (Binnie.Genetics.getBeeRoot().isMated(beeStack)) {
								sortableList.queens.add(indexerInventory.indexOf(beeStack));
							} else {
								sortableList.princesses.add(indexerInventory.indexOf(beeStack));
							}
						}
					}

					sortedInventory = new SetList<>();
					switch (sortingMode) {
						case Species:
							for (int j = 0; j < 1024; ++j) {
								if (speciesList.containsKey(j)) {
									sortedInventory.addAll(speciesList.get(j).queens);
									sortedInventory.addAll(speciesList.get(j).princesses);
									sortedInventory.addAll(speciesList.get(j).drones);
								}
							}
							break;

						case Type:
							for (int j = 0; j < 1024; ++j) {
								if (speciesList.containsKey(j)) {
									sortedInventory.addAll(speciesList.get(j).queens);
								}
							}
							for (int j = 0; j < 1024; ++j) {
								if (speciesList.containsKey(j)) {
									sortedInventory.addAll(speciesList.get(j).princesses);
								}
							}
							for (int j = 0; j < 1024; ++j) {
								if (speciesList.containsKey(j)) {
									sortedInventory.addAll(speciesList.get(j).drones);
								}
							}
							break;
					}
					break;
				}
				default: {
					sortedInventory.clear();
					for (i = 0; i < indexerInventory.size(); ++i) {
						sortedInventory.add(i);
					}
					break;
				}
			}
		}

		@Override
		public boolean hasCustomInventoryName() {
			return false;
		}

		@Override
		public boolean isItemValidForSlot(int i, ItemStack itemstack) {
			return true;
		}

		public enum Mode {
			None,
			Species,
			Type
		}
	}
}
