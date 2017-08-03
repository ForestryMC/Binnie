package binnie.extratrees.machines.lumbermill.window;

import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.OreDictionary;

import binnie.core.machines.inventory.SlotValidator;
import binnie.core.util.I18N;
import binnie.extratrees.machines.lumbermill.recipes.LumbermillRecipeManager;

public class SlotValidatorLog extends SlotValidator {
	private static final int logWoodId = OreDictionary.getOreID("logWood");

	public SlotValidatorLog() {
		super(SlotValidator.spriteBlock);
	}

	@Override
	public boolean isValid(final ItemStack itemStack) {
		int[] oreIDs = OreDictionary.getOreIDs(itemStack);
		for (int oreId : oreIDs) {
			if (oreId == logWoodId && LumbermillRecipeManager.getPlankProduct(itemStack) != null) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getTooltip() {
		return I18N.localise("extratrees.machine.machine.lumbermill.tooltips.logs");
	}
}
