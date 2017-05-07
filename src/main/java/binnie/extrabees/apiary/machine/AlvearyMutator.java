package binnie.extrabees.apiary.machine;

import binnie.core.craftgui.minecraft.IMachineInformation;
import binnie.core.machines.Machine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.machines.inventory.ValidatorIcon;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.apiary.ComponentBeeModifier;
import binnie.extrabees.apiary.ComponentExtraBeeGUI;
import binnie.extrabees.core.ExtraBeeGUID;
import binnie.extrabees.core.ExtraBeeTexture;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeListener;
import forestry.api.apiculture.IBeeModifier;
import net.minecraft.item.ItemStack;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AlvearyMutator {
	public static int slotMutator = 0;

	protected static Map<ItemStack, Float> mutations = new HashMap<>();

	public static boolean isMutationItem(ItemStack item) {
		return getMutationMult(item) > 0.0f;
	}

	public static float getMutationMult(ItemStack item) {
		if (item == null) {
			return 1.0f;
		}

		for (ItemStack comp : AlvearyMutator.mutations.keySet()) {
			if (ItemStack.areItemStackTagsEqual(item, comp) && item.isItemEqual(comp)) {
				return AlvearyMutator.mutations.get(comp);
			}
		}
		return 0.0f;
	}

	public static void addMutationItem(ItemStack item, float chance) {
		if (item == null) {
			return;
		}
		AlvearyMutator.mutations.put(item, chance);
	}

	public static Collection<ItemStack> getMutagens() {
		return AlvearyMutator.mutations.keySet();
	}

	public static class PackageAlvearyMutator extends AlvearyMachine.AlvearyPackage implements IMachineInformation {
		public PackageAlvearyMutator() {
			super("mutator", ExtraBeeTexture.AlvearyMutator.getTexture(), false);
		}

		@Override
		public void createMachine(Machine machine) {
			new ComponentExtraBeeGUI(machine, ExtraBeeGUID.AlvearyMutator);
			ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
			inventory.addSlot(AlvearyMutator.slotMutator, "mutator");
			inventory.getSlot(AlvearyMutator.slotMutator).setValidator(new SlotValidatorMutator());
			new ComponentMutatorModifier(machine);
		}
	}

	public static class SlotValidatorMutator extends SlotValidator {
		public SlotValidatorMutator() {
			super(new ValidatorIcon(ExtraBees.instance, "validator/mutator.0", "validator/mutator.1"));
		}

		@Override
		public boolean isValid(ItemStack itemStack) {
			return AlvearyMutator.isMutationItem(itemStack);
		}

		@Override
		public String getTooltip() {
			return "Mutagenic Agents";
		}
	}

	public static class ComponentMutatorModifier extends ComponentBeeModifier implements IBeeModifier, IBeeListener {
		public ComponentMutatorModifier(Machine machine) {
			super(machine);
		}

		@Override
		public float getMutationModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
			if (getUtil().isSlotEmpty(AlvearyMutator.slotMutator)) {
				return 1.0f;
			}

			float mult = AlvearyMutator.getMutationMult(getUtil().getStack(AlvearyMutator.slotMutator));
			return Math.min(mult, 15.0f / currentModifier);
		}

		@Override
		public void onPostQueenDeath(IBee queen) {
			getUtil().decreaseStack(AlvearyMutator.slotMutator, 1);
		}
	}
}
