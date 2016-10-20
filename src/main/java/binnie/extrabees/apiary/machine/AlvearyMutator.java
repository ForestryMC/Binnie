package binnie.extrabees.apiary.machine;

import binnie.core.machines.Machine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.machines.inventory.ValidatorIcon;
import binnie.craftgui.minecraft.IMachineInformation;
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
    public static int slotMutator;
    static Map<ItemStack, Float> mutations;

    public static boolean isMutationItem(final ItemStack item) {
        return getMutationMult(item) > 0.0f;
    }

    public static float getMutationMult(final ItemStack item) {
        if (item == null) {
            return 1.0f;
        }
        for (final ItemStack comp : AlvearyMutator.mutations.keySet()) {
            if (ItemStack.areItemStackTagsEqual(item, comp) && item.isItemEqual(comp)) {
                return AlvearyMutator.mutations.get(comp);
            }
        }
        return 1.0f;
    }

    public static void addMutationItem(final ItemStack item, final float chance) {
        if (item == null) {
            return;
        }
        AlvearyMutator.mutations.put(item, chance);
    }

    public static Collection<ItemStack> getMutagens() {
        return AlvearyMutator.mutations.keySet();
    }

    static {
        AlvearyMutator.slotMutator = 0;
        AlvearyMutator.mutations = new HashMap<ItemStack, Float>();
    }

    public static class PackageAlvearyMutator extends AlvearyMachine.AlvearyPackage implements IMachineInformation {
        public PackageAlvearyMutator() {
            super("mutator", ExtraBeeTexture.AlvearyMutator.getTexture(), false);
        }

        @Override
        public void createMachine(final Machine machine) {
            new ComponentExtraBeeGUI(machine, ExtraBeeGUID.AlvearyMutator);
            final ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
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
            if (this.getUtil().isSlotEmpty(AlvearyMutator.slotMutator)) {
                return 1.0f;
            }
            final float mult = AlvearyMutator.getMutationMult(this.getUtil().getStack(AlvearyMutator.slotMutator));
            return Math.min(mult, 15.0f / currentModifier);
        }

        @Override
        public void onPostQueenDeath(final IBee queen) {
            this.getUtil().decreaseStack(AlvearyMutator.slotMutator, 1);
        }
    }
}
