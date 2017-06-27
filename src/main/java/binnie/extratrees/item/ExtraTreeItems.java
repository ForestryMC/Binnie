package binnie.extratrees.item;

import java.util.List;

import net.minecraft.item.ItemStack;

import binnie.core.item.IItemMiscProvider;
import binnie.core.util.I18N;
import binnie.extratrees.ExtraTrees;

public enum ExtraTreeItems implements IItemMiscProvider {
	CarpentryHammer("carpentry_hammer"),
	Sawdust("sawdust"),
	Bark("bark"),
	ProvenGear("proven_gear"),
	WoodWax("wood_wax"),
	Yeast("yeast"),
	LagerYeast("yeast_lager"),
	GrainWheat("grain_wheat"),
	GrainBarley("grain_barley"),
	GrainRye("grain_rye"),
	GrainCorn("grain_corn"),
	GrainRoasted("grain_roasted"),
	GlassFitting("glass_fitting");
	
	String name;

	ExtraTreeItems(String name) {
		this.name = name;
	}

	@Override
	public String getModelPath() {
		return name;
	}

	@Override
	public void addInformation(final List<String> tooltip) {
	}

	@Override
	public String getDisplayName(final ItemStack stack) {
		return I18N.localise("extratrees.item." + name + ".name");
	}

	@Override
	public boolean isActive() {
		return this != ExtraTreeItems.CarpentryHammer;
	}

	@Override
	public ItemStack get(final int i) {
		return new ItemStack(ExtraTrees.items().itemMisc, i, this.ordinal());
	}
}
