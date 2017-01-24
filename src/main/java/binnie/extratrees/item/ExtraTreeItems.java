package binnie.extratrees.item;

import binnie.core.item.IItemMiscProvider;
import binnie.extratrees.ExtraTrees;
import net.minecraft.item.ItemStack;

import java.util.List;

public enum ExtraTreeItems implements IItemMiscProvider {
	CarpentryHammer("Fake Hammer", "carpentryHammer"),
	Sawdust("Sawdust", "sawdust"),
	Bark("Bark", "bark"),
	ProvenGear("Proven Gear", "provenGear"),
	WoodWax("Wood Polish", "woodWax"),
	Hops("Hops", "hops"),
	Yeast("Yeast", "yeast"),
	LagerYeast("Lager Yeast", "yeastLager"),
	GrainWheat("Wheat Grain", "grainWheat"),
	GrainBarley("Barley Grain", "grainBarley"),
	GrainRye("Rye Grain", "grainRye"),
	GrainCorn("Corn Grain", "grainCorn"),
	GrainRoasted("Roasted Grain", "grainRoasted"),
	GlassFitting("Glass Fittings", "glassFitting");

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
	public void addInformation(final List par3List) {
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
