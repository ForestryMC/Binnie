package binnie.botany.items;

import java.util.List;

import net.minecraft.item.ItemStack;

import binnie.botany.Botany;
import binnie.core.item.IItemMiscProvider;
import binnie.core.util.I18N;

public enum BotanyItems implements IItemMiscProvider {
	AshPowder("powder_ash"),
	PulpPowder("powder_pulp"),
	MulchPowder("powder_mulch"),
	SulphurPowder("powder_sulphur"),
	FertiliserPowder("powder_fertiliser"),
	CompostPowder("powder_compost"),
	Mortar("mortar"),
	Weedkiller("weedkiller");

	String name;

	BotanyItems(String name) {
		this.name = name;
	}

	@Override
	public void addInformation(List<String> tooltip) {
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
		return new ItemStack(Botany.misc, size, this.ordinal());
	}

	@Override
	public boolean isActive() {
		return true;
	}
}
