package binnie.botany.items;

import binnie.botany.Botany;
import binnie.core.item.IItemMiscProvider;
import net.minecraft.item.ItemStack;

import java.util.List;

public enum BotanyItems implements IItemMiscProvider {
	AshPowder("Ash Powder", "powderAsh"),
	PulpPowder("Wood Pulp Powder", "powderPulp"),
	MulchPowder("Mulch Powder", "powderMulch"),
	SulphurPowder("Sulphur Powder", "powderSulphur"),
	FertiliserPowder("Fertiliser Powder", "powderFertiliser"),
	CompostPowder("Compost Powder", "powderCompost"),
	Mortar("Mortar", "mortar"),
	Weedkiller("Weedkiller", "weedkiller");

	String name;
	String modelPath;

	BotanyItems(String name, String modelPath) {
		this.name = name;
		this.modelPath = modelPath;
	}

	@Override
	public void addInformation(List tooltip) {
	}

	@Override
	public String getName(ItemStack stack) {
		return this.name;
	}

	@Override
	public String getModelPath() {
		return modelPath;
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
