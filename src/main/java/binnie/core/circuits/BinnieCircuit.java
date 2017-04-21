package binnie.core.circuits;

import forestry.api.circuits.ChipsetManager;
import forestry.api.circuits.ICircuit;
import forestry.api.circuits.ICircuitLayout;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BinnieCircuit implements ICircuit {
	private String uid;
	private int limit;
	private List<String> tooltips;

	public BinnieCircuit(String uid, int limit, ICircuitLayout layout, ItemStack itemStack) {
		this.tooltips = new ArrayList<String>();
		this.uid = "binnie.circuit." + uid;
		this.limit = limit;
		ChipsetManager.circuitRegistry.registerCircuit(this);
		if (itemStack != null) {
			ChipsetManager.solderManager.addRecipe(layout, itemStack, this);
		}
	}

	public BinnieCircuit(String uid, int limit, ICircuitLayout layout, Item item, int itemMeta) {
		this(uid, limit, layout, new ItemStack(item, 1, itemMeta));
	}

	public void addTooltipString(String string) {
		tooltips.add(string);
	}

	@Override
	public String getUID() {
		return uid;
	}

	@Override
	public boolean requiresDiscovery() {
		return false;
	}

	@Override
	public int getLimit() {
		return limit;
	}

	@Override
	public String getName() {
		return uid;
	}

	@Override
	public void addTooltip(List<String> list) {
		for (String string : tooltips) {
			list.add(" - " + string);
		}
	}

	@Override
	public boolean isCircuitable(Object arg0) {
		return false;
	}

	@Override
	public void onInsertion(int arg0, Object arg1) {
		// ignored
	}

	@Override
	public void onLoad(int arg0, Object arg1) {
		// ignored
	}

	@Override
	public void onRemoval(int arg0, Object arg1) {
		// ignored
	}

	@Override
	public void onTick(int arg0, Object arg1) {
		// ignored
	}
}
