// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.circuits;

import net.minecraft.item.Item;

import forestry.api.circuits.ChipsetManager;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

import forestry.api.circuits.ICircuitLayout;

import java.util.List;

import forestry.api.circuits.ICircuit;

public class BinnieCircuit implements ICircuit
{
	private String uid;
	private int limit;
	private List<String> tooltips;

	public BinnieCircuit(final String uid, final int limit, final ICircuitLayout layout, final ItemStack itemStack) {
		this.tooltips = new ArrayList<String>();
		this.uid = "binnie.circuit." + uid;
		this.limit = limit;
		ChipsetManager.circuitRegistry.registerCircuit(this);
		if (itemStack != null) {
			ChipsetManager.solderManager.addRecipe(layout, itemStack, this);
		}
	}

	public BinnieCircuit(final String uid, final int limit, final ICircuitLayout layout, final Item item, final int itemMeta) {
		this(uid, limit, layout, new ItemStack(item, 1, itemMeta));
	}

	public void addTooltipString(final String string) {
		this.tooltips.add(string);
	}

	@Override
	public String getUID() {
		return this.uid;
	}

	@Override
	public boolean requiresDiscovery() {
		return false;
	}

	@Override
	public int getLimit() {
		return this.limit;
	}

	@Override
	public String getName() {
		return this.uid;
	}

	@Override
	public void addTooltip(final List<String> list) {
		for (final String string : this.tooltips) {
			list.add(" - " + string);
		}
	}

	@Override
	public boolean isCircuitable(Object arg0) {
		return false;
	}

	@Override
	public void onInsertion(int arg0, Object arg1) {
	}

	@Override
	public void onLoad(int arg0, Object arg1) {
	}

	@Override
	public void onRemoval(int arg0, Object arg1) {
	}

	@Override
	public void onTick(int arg0, Object arg1) {
	}
}
