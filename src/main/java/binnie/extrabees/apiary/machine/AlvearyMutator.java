package binnie.extrabees.apiary.machine;

import binnie.core.craftgui.minecraft.IMachineInformation;
import binnie.core.machines.Machine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.SlotValidator;
import binnie.extrabees.apiary.ComponentBeeModifier;
import binnie.extrabees.apiary.ComponentExtraBeeGUI;
import binnie.extrabees.apiary.ModuleApiary;
import binnie.extrabees.core.ExtraBeeGUID;
import binnie.extrabees.core.ExtraBeeTexture;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeListener;
import forestry.api.apiculture.IBeeModifier;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.apache.commons.lang3.ObjectUtils.firstNonNull;

public class AlvearyMutator {
	public static int slotMutator = 0;
	private static final List<Pair<ItemStack, Float>> MUTATIONS = new ArrayList<>();

	public static boolean isMutationItem(final ItemStack item) {
		return getMutationMult(item) > 0.0f;
	}

	public static float getMutationMult(final ItemStack item) {
		if (!item.isEmpty()) {
			for (final Pair<ItemStack, Float> comp : AlvearyMutator.MUTATIONS) {
				ItemStack key = comp.getKey();
				if (item.isItemEqual(key) && ItemStack.areItemStackTagsEqual(item, key)) {
					return comp.getValue();
				}
			}
		}
		return 1.0f;
	}

	public static void addMutationItem(@Nullable Item item, float chance){
		addMutationItem(new ItemStack(firstNonNull(item, Item.getItemFromBlock(Blocks.AIR))), chance);
	}

	public static void addMutationItem(final ItemStack item, final float chance) {
		if (item.isEmpty()) {
			return;
		}
		AlvearyMutator.MUTATIONS.add(Pair.of(item, chance));
		AlvearyMutator.MUTATIONS.sort(Comparator.comparing(Pair::getValue));
	}

	public static List<Pair<ItemStack, Float>> getMutagens() {
		return Collections.unmodifiableList(AlvearyMutator.MUTATIONS);
	}

	public static class PackageAlvearyMutator extends AlvearyMachine.AlvearyPackage implements IMachineInformation {
		public PackageAlvearyMutator() {
			super("mutator", ExtraBeeTexture.AlvearyMutator, false);
		}

		@Override
		public void createMachine(final Machine machine) {
			new ComponentExtraBeeGUI(machine, ExtraBeeGUID.AlvearyMutator);
			final ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
			inventory.addSlot(AlvearyMutator.slotMutator, "mutator").setValidator(new SlotValidatorMutator());
			new ComponentMutatorModifier(machine);
		}
	}

	public static class SlotValidatorMutator extends SlotValidator {
		public SlotValidatorMutator() {
			super(ModuleApiary.spriteMutator);
		}

		@Override
		public boolean isValid(final ItemStack itemStack) {
			return AlvearyMutator.isMutationItem(itemStack);
		}

		@Override
		public String getTooltip() {
			return "Mutagenic Agents";
		}
	}

	public static class ComponentMutatorModifier extends ComponentBeeModifier implements IBeeModifier, IBeeListener {
		public ComponentMutatorModifier(final Machine machine) {
			super(machine);
		}

		@Override
		public float getMutationModifier(final IBeeGenome genome, final IBeeGenome mate, final float currentModifier) {
			ItemStack mutator = this.getUtil().getStack(AlvearyMutator.slotMutator);
			final float mult = AlvearyMutator.getMutationMult(mutator);
			return Math.min(mult * currentModifier, 15f);
		}

		@Override
		public void onPostQueenDeath(final IBee queen) {
			this.getUtil().decreaseStack(AlvearyMutator.slotMutator, 1);
		}
	}
}
