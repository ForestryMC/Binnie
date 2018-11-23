package binnie.genetics.machine.indexer;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

public class SpeciesList {
	private final List<Integer> drones;
	private final List<Integer> queens;
	private final List<Integer> princesses;
	private final List<ItemStack> bees;

	SpeciesList() {
		this.drones = new ArrayList<>();
		this.queens = new ArrayList<>();
		this.princesses = new ArrayList<>();
		this.bees = new ArrayList<>();
	}

	public void add(ItemStack stack) {
		this.bees.add(stack);
	}

	public List<Integer> getDrones() {
		return drones;
	}

	public List<Integer> getQueens() {
		return queens;
	}

	public List<Integer> getPrincesses() {
		return princesses;
	}

	public List<ItemStack> getBees() {
		return bees;
	}
}
