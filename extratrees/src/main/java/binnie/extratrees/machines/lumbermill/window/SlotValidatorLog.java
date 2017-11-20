package binnie.extratrees.machines.lumbermill.window;

import binnie.core.machines.ManagerMachine;
import net.minecraft.item.ItemStack;

import binnie.core.machines.inventory.SlotValidator;
import binnie.core.util.I18N;
import binnie.extratrees.machines.lumbermill.recipes.LumbermillRecipeManager;
import net.minecraft.world.World;

public class SlotValidatorLog extends SlotValidator {
	private final World world;

	public SlotValidatorLog(World world) {
		super(ManagerMachine.getSpriteBlock());
		this.world = world;
	}

	@Override
	public boolean isValid(final ItemStack itemStack) {
		ItemStack plank = LumbermillRecipeManager.getPlankProduct(itemStack, world);
		return !plank.isEmpty();
	}

	@Override
	public String getTooltip() {
		return I18N.localise("extratrees.machine.lumbermill.tooltips.logs");
	}
}
