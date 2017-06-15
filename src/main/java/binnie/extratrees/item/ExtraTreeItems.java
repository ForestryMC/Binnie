package binnie.extratrees.item;

import java.util.List;

import net.minecraft.item.ItemStack;

import binnie.core.item.IItemMiscProvider;
import binnie.extratrees.ExtraTrees;

public enum ExtraTreeItems implements IItemMiscProvider {
	CarpentryHammer("Fake Hammer", "carpentry_hammer"),
	Sawdust("Sawdust", "sawdust"),
	Bark("Bark", "bark"),
	ProvenGear("Proven Gear", "proven_gear"),
	WoodWax("Wood Polish", "wood_wax"),
	Hops("Hops", "hops"),
	Yeast("Yeast", "yeast"),
	LagerYeast("Lager Yeast", "yeast_lager"),
	GrainWheat("Wheat Grain", "grain_wheat"),
	GrainBarley("Barley Grain", "grain_barley"),
	GrainRye("Rye Grain", "grain_rye"),
	GrainCorn("Corn Grain", "grain_corn"),
	GrainRoasted("Roasted Grain", "grain_roasted"),
	GlassFitting("Glass Fittings", "glass_fitting");

	String name;
	String modelPath;

	ExtraTreeItems(final String name, final String modelPath) {
		this.name = name;
		this.modelPath = modelPath;
	}

	@Override
	public String getModelPath() {
		return modelPath;
	}

	@Override
	public void addInformation(final List<String> tooltip) {
	}

	@Override
	public String getName(final ItemStack stack) {
		return this.name;
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
