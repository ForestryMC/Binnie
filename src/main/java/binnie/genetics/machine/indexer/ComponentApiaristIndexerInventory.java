package binnie.genetics.machine.indexer;

import binnie.Binnie;
import binnie.core.machines.Machine;
import binnie.core.machines.inventory.SetList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComponentApiaristIndexerInventory extends ComponentIndexerInventory<ComponentApiaristIndexerInventory.Mode> implements IInventory {
	private static class SpeciesList {
		public List<Integer> drones;
		public List<Integer> queens;
		public List<Integer> princesses;
		public List<ItemStack> bees;

		SpeciesList() {
			this.drones = new ArrayList<>();
			this.queens = new ArrayList<>();
			this.princesses = new ArrayList<>();
			this.bees = new ArrayList<>();
		}

		public void add(final ItemStack stack) {
			this.bees.add(stack);
		}
	}

	public ComponentApiaristIndexerInventory(final Machine machine) {
		super(machine);
	}

	@Override
	public void Sort() {
		int i = 0;
		while (i < this.indexerInventory.size()) {
			if (this.indexerInventory.get(i) == null) {
				this.indexerInventory.remove(i);
			} else {
				++i;
			}
		}
		if (!this.needsSorting || this.sortingMode == null) {
			return;
		}
		this.needsSorting = false;
		++this.guiRefreshCounter;
		switch (this.sortingMode) {
			case Species:
			case Type: {

				final Map<Integer, SpeciesList> speciesList = new HashMap<>();
				for (final ItemStack itemStack : this.indexerInventory) {
					final int species = itemStack.getItemDamage();
					if (!speciesList.containsKey(species)) {
						speciesList.put(species, new SpeciesList());
					}
					speciesList.get(species).add(itemStack);
				}
				for (final SpeciesList sortableList : speciesList.values()) {
					for (final ItemStack beeStack : sortableList.bees) {
						if (Binnie.GENETICS.getBeeRoot().isDrone(beeStack)) {
							sortableList.drones.add(this.indexerInventory.indexOf(beeStack));
						} else if (Binnie.GENETICS.getBeeRoot().isMated(beeStack)) {
							sortableList.queens.add(this.indexerInventory.indexOf(beeStack));
						} else {
							sortableList.princesses.add(this.indexerInventory.indexOf(beeStack));
						}
					}
				}
				this.sortedInventory = new SetList<>();
				switch (this.sortingMode) {
					case Species: {
						for (int j = 0; j < 1024; ++j) {
							if (speciesList.containsKey(j)) {
								this.sortedInventory.addAll(speciesList.get(j).queens);
								this.sortedInventory.addAll(speciesList.get(j).princesses);
								this.sortedInventory.addAll(speciesList.get(j).drones);
							}
						}
						break;
					}
					case Type: {
						for (int j = 0; j < 1024; ++j) {
							if (speciesList.containsKey(j)) {
								this.sortedInventory.addAll(speciesList.get(j).queens);
							}
						}
						for (int j = 0; j < 1024; ++j) {
							if (speciesList.containsKey(j)) {
								this.sortedInventory.addAll(speciesList.get(j).princesses);
							}
						}
						for (int j = 0; j < 1024; ++j) {
							if (speciesList.containsKey(j)) {
								this.sortedInventory.addAll(speciesList.get(j).drones);
							}
						}
						break;
					}
				}
				break;
			}
			default: {
				this.sortedInventory.clear();
				for (i = 0; i < this.indexerInventory.size(); ++i) {
					this.sortedInventory.add(i);
				}
				break;
			}
		}
	}

	@Override
	public String getName() {
		return "";
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentString(getName());
	}

	@Override
	public void clear() {

	}


	@Override
	public boolean isEmpty() {
		return this.sortedInventory.isEmpty();
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStack.EMPTY;
	}

	@Override
	public void openInventory(EntityPlayer player) {

	}

	@Override
	public void closeInventory(EntityPlayer player) {

	}

	@Override
	public boolean isItemValidForSlot(final int i, final ItemStack itemstack) {
		return true;
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {

	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	public enum Mode {
		None,
		Species,
		Type;
	}
}
