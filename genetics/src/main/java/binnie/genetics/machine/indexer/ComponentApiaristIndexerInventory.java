package binnie.genetics.machine.indexer;

import java.util.HashMap;
import java.util.Map;

import forestry.api.apiculture.BeeManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

import binnie.core.machines.Machine;
import binnie.core.machines.inventory.SetList;

public class ComponentApiaristIndexerInventory extends ComponentIndexerInventory<ComponentApiaristIndexerInventory.Mode> implements IInventory {
	public ComponentApiaristIndexerInventory(final Machine machine) {
		super(machine);
	}

	@Override
	public void Sort() {
		int i = 0;
		while (i < this.getIndexerInventory().size()) {
			if (this.getIndexerInventory().get(i) == null) {
				this.getIndexerInventory().remove(i);
			} else {
				++i;
			}
		}
		if (!this.isNeedsSorting() || this.getSortingMode() == null) {
			return;
		}
		this.setNeedsSorting(false);
		this.guiRefreshCounter = this.getGuiRefreshCounter() + 1;
		switch (this.getSortingMode()) {
			case Species:
			case Type: {

				final Map<Integer, SpeciesList> speciesList = new HashMap<>();
				for (final ItemStack itemStack : this.getIndexerInventory()) {
					final int species = itemStack.getItemDamage();
					if (!speciesList.containsKey(species)) {
						speciesList.put(species, new SpeciesList());
					}
					speciesList.get(species).add(itemStack);
				}
				for (final SpeciesList sortableList : speciesList.values()) {
					for (final ItemStack beeStack : sortableList.getBees()) {
						if (BeeManager.beeRoot.isDrone(beeStack)) {
							sortableList.getDrones().add(this.getIndexerInventory().indexOf(beeStack));
						} else if (BeeManager.beeRoot.isMated(beeStack)) {
							sortableList.getQueens().add(this.getIndexerInventory().indexOf(beeStack));
						} else {
							sortableList.getPrincesses().add(this.getIndexerInventory().indexOf(beeStack));
						}
					}
				}
				this.sortedInventory = new SetList<>();
				switch (this.getSortingMode()) {
					case Species: {
						for (int j = 0; j < 1024; ++j) {
							if (speciesList.containsKey(j)) {
								this.getSortedInventory().addAll(speciesList.get(j).getQueens());
								this.getSortedInventory().addAll(speciesList.get(j).getPrincesses());
								this.getSortedInventory().addAll(speciesList.get(j).getDrones());
							}
						}
						break;
					}
					case Type: {
						for (int j = 0; j < 1024; ++j) {
							if (speciesList.containsKey(j)) {
								this.getSortedInventory().addAll(speciesList.get(j).getQueens());
							}
						}
						for (int j = 0; j < 1024; ++j) {
							if (speciesList.containsKey(j)) {
								this.getSortedInventory().addAll(speciesList.get(j).getPrincesses());
							}
						}
						for (int j = 0; j < 1024; ++j) {
							if (speciesList.containsKey(j)) {
								this.getSortedInventory().addAll(speciesList.get(j).getDrones());
							}
						}
						break;
					}
				}
				break;
			}
			default: {
				this.getSortedInventory().clear();
				for (i = 0; i < this.getIndexerInventory().size(); ++i) {
					this.getSortedInventory().add(i);
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
		return this.getSortedInventory().isEmpty();
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
		Type
	}

}
