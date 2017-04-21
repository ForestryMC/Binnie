// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.item;

import java.util.List;
import binnie.extratrees.ExtraTrees;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import binnie.core.item.IItemMisc;

public enum ExtraTreeItems implements IItemMisc
{
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
	String iconPath;
	IIcon icon;

	private ExtraTreeItems(final String name, final String iconPath) {
		this.name = name;
		this.iconPath = iconPath;
	}

	@Override
	public IIcon getIcon(final ItemStack itemStack) {
		return this.icon;
	}

	@Override
	public void registerIcons(final IIconRegister register) {
		this.icon = ExtraTrees.proxy.getIcon(register, this.iconPath);
	}

	@Override
	public void addInformation(final List data) {
	}

	@Override
	public String getName(final ItemStack itemStack) {
		return this.name;
	}

	@Override
	public boolean isActive() {
		return this != ExtraTreeItems.CarpentryHammer;
	}

	@Override
	public ItemStack get(final int count) {
		return new ItemStack(ExtraTrees.itemMisc, count, this.ordinal());
	}
}
