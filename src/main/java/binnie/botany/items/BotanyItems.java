package binnie.botany.items;

import java.util.List;
import java.util.Locale;

import net.minecraft.item.ItemStack;

import binnie.botany.Botany;
import binnie.core.item.IItemMiscProvider;
import binnie.core.util.I18N;

public enum BotanyItems implements IItemMiscProvider {
	POWDER_ASH,
	POWDER_PULP,
	POWDER_MULCH,
	POWDER_SULPHUR,
	POWDER_FERTILISER,
	POWDER_COMPOST,
	MORTAR,
	WEEDKILLER;

	String name;

	BotanyItems() {
		name = name().toLowerCase(Locale.ENGLISH);
	}

	@Override
	public void addInformation(List<String> tooltip) {
		// ignored
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
		return new ItemStack(Botany.gardening().misc, size, ordinal());
	}

	@Override
	public boolean isActive() {
		return true;
	}
}
