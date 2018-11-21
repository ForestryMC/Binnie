package binnie.botany.items;

import java.util.List;
import java.util.Locale;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.botany.modules.ModuleGardening;
import binnie.core.item.IItemSubtypeMisc;
import binnie.core.util.I18N;

public enum BotanyItems implements IItemSubtypeMisc {
	POWDER_ASH,
	POWDER_PULP,
	POWDER_MULCH,
	POWDER_SULPHUR,
	POWDER_FERTILISER,
	POWDER_COMPOST,
	MORTAR,
	WEEDKILLER;

	private final String name;

	BotanyItems() {
		name = name().toLowerCase(Locale.ENGLISH);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(List<String> tooltip) {
		if (this == MORTAR) {
			tooltip.add(I18N.localise("item.botany.mortar.tooltip"));
		}
	}

	@Override
	public String getDisplayName(ItemStack stack) {
		return I18N.localise("item.botany." + name + ".name");
	}

	@Override
	public String getModelPath() {
		return name;
	}

	@Override
	public ItemStack get(int size) {
		return new ItemStack(ModuleGardening.misc, size, ordinal());
	}

	@Override
	public boolean isActive() {
		return true;
	}
}
