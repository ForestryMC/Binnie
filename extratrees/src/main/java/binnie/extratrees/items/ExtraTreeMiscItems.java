package binnie.extratrees.items;

import java.util.List;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.item.IItemSubtypeMisc;
import binnie.core.util.I18N;
import binnie.extratrees.modules.features.ExtraTreesItems;

public enum ExtraTreeMiscItems implements IItemSubtypeMisc {
	SAWDUST("sawdust"),
	Bark("bark"),
	PROVEN_GEAR("proven_gear"),
	WOOD_WAX("wood_wax"),
	YEAST("yeast"),
	LAGER_YEAST("yeast_lager"),
	GRAIN_WHEAT("grain_wheat"),
	GRAIN_BARLEY("grain_barley"),
	GRAIN_RYE("grain_rye"),
	GRAIN_CORN("grain_corn"),
	GRAIN_ROASTED("grain_roasted"),
	GLASS_FITTING("glass_fitting");

	private final String name;

	ExtraTreeMiscItems(String name) {
		this.name = name;
	}

	@Override
	public String getModelPath() {
		return name;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(List<String> tooltip) {
	}

	@Override
	public String getDisplayName(ItemStack stack) {
		return I18N.localise("extratrees.item." + name + ".name");
	}

	@Override
	public boolean isActive() {
		return true;
	}

	@Override
	public ItemStack stack(int amount) {
		return ExtraTreesItems.MISC.stack(amount, ordinal());
	}
}
