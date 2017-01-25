package binnie.extratrees.machines.lumbermill;

import binnie.core.machines.inventory.SlotValidator;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class SlotValidatorLog extends SlotValidator {
	private static final int logWoodId = OreDictionary.getOreID("logWood");

	public SlotValidatorLog() {
		super(SlotValidator.spriteBlock);
	}

	@Override
	public boolean isValid(final ItemStack itemStack) {
		int[] oreIDs = OreDictionary.getOreIDs(itemStack);
		for (int oreId : oreIDs) {
			if (oreId == logWoodId && LumbermillRecipes.getPlankProduct(itemStack) != null) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getTooltip() {
		return "Logs";
	}
}
