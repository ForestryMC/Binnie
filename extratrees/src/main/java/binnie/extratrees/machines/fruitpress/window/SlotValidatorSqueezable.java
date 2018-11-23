package binnie.extratrees.machines.fruitpress.window;

import net.minecraft.item.ItemStack;

import binnie.core.machines.ManagerMachine;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.util.I18N;
import binnie.extratrees.machines.fruitpress.recipes.FruitPressRecipeManager;

public class SlotValidatorSqueezable extends SlotValidator {
	public SlotValidatorSqueezable() {
		super(ManagerMachine.getSpriteBlock());
	}

	@Override
	public boolean isValid(ItemStack itemStack) {
		return FruitPressRecipeManager.isInput(itemStack);
	}

	@Override
	public String getTooltip() {
		return I18N.localise("extratrees.machine.press.tooltips.fruit");
	}
}
