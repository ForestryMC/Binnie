package binnie.extratrees.item;

import binnie.core.item.IItemMisc;
import binnie.extratrees.ExtraTrees;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public enum ExtraTreeItems implements IItemMisc {
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

	protected String name;
	protected String iconPath;
	protected IIcon icon;

	ExtraTreeItems(String name, String iconPath) {
		this.name = name;
		this.iconPath = iconPath;
	}

	@Override
	public IIcon getIcon(ItemStack itemStack) {
		return icon;
	}

	@Override
	public void registerIcons(IIconRegister register) {
		icon = ExtraTrees.proxy.getIcon(register, iconPath);
	}

	@Override
	public void addInformation(List tooltip) {
		// ignored
	}

	@Override
	public String getName(ItemStack itemStack) {
		return name;
	}

	@Override
	public boolean isActive() {
		return this != ExtraTreeItems.CarpentryHammer;
	}

	@Override
	public ItemStack get(int count) {
		return new ItemStack(ExtraTrees.itemMisc, count, ordinal());
	}
}
