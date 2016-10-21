package binnie.extrabees.apiary.machine;

import binnie.core.Mods;
import binnie.core.circuits.BinnieCircuit;
import binnie.core.genetics.BeeModifierLogic;
import binnie.core.genetics.EnumBeeBooleanModifier;
import binnie.core.genetics.EnumBeeModifier;
import binnie.core.machines.Machine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.craftgui.minecraft.IMachineInformation;
import binnie.extrabees.apiary.ComponentBeeModifier;
import binnie.extrabees.apiary.ComponentExtraBeeGUI;
import binnie.extrabees.core.ExtraBeeGUID;
import binnie.extrabees.core.ExtraBeeTexture;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeListener;
import forestry.api.apiculture.IBeeModifier;
import forestry.api.circuits.ChipsetManager;
import forestry.api.circuits.ICircuit;
import forestry.api.circuits.ICircuitBoard;
import forestry.api.circuits.ICircuitLayout;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class AlvearyStimulator {
    public static int slotCircuit;

    static {
        AlvearyStimulator.slotCircuit = 0;
    }

    public static class PackageAlvearyStimulator extends AlvearyMachine.AlvearyPackage implements IMachineInformation {
        public PackageAlvearyStimulator() {
            super("stimulator", ExtraBeeTexture.AlvearyStimulator.getTexture(), true);
        }

        @Override
        public void createMachine(final Machine machine) {
            new ComponentExtraBeeGUI(machine, ExtraBeeGUID.AlvearyStimulator);
            final ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
            inventory.addSlot(AlvearyStimulator.slotCircuit, "circuit");
            inventory.getSlot(AlvearyStimulator.slotCircuit).setValidator(new SlotValidatorCircuit());
            final ComponentPowerReceptor power = new ComponentPowerReceptor(machine);
            new ComponentStimulatorModifier(machine);
        }
    }

    public static class SlotValidatorCircuit extends SlotValidator {
        public SlotValidatorCircuit() {
            super(SlotValidator.IconCircuit);
        }

        @Override
        public boolean isValid(final ItemStack itemStack) {
            return itemStack != null && ChipsetManager.circuitRegistry.isChipset(itemStack);
        }

        @Override
        public String getTooltip() {
            return "Forestry Circuits";
        }
    }

    public static class ComponentStimulatorModifier extends ComponentBeeModifier implements IBeeModifier, IBeeListener {
        float powerUsage;
        boolean powered;
        StimulatorCircuit[] modifiers;

        public ComponentStimulatorModifier(final Machine machine) {
            super(machine);
            this.powerUsage = 0.0f;
            this.powered = false;
            this.modifiers = new StimulatorCircuit[0];
        }

        @Override
        public void onUpdate() {
            super.onUpdate();
            this.modifiers = this.getCircuits();
            this.powerUsage = 0.0f;
            for (final StimulatorCircuit beeMod : this.modifiers) {
                this.powerUsage += beeMod.getPowerUsage();
            }
            this.powered = this.getUtil().hasEnergyMJ(this.powerUsage);
        }

        public ICircuitBoard getHiveFrame() {
            if (!this.getUtil().isSlotEmpty(AlvearyStimulator.slotCircuit)) {
                return ChipsetManager.circuitRegistry.getCircuitBoard(this.getUtil().getStack(AlvearyStimulator.slotCircuit));
            }
            return null;
        }

        public StimulatorCircuit[] getCircuits() {
            final ICircuitBoard board = this.getHiveFrame();
            if (board == null) {
                return new StimulatorCircuit[0];
            }
            final ICircuit[] circuits = board.getCircuits();
            final List<StimulatorCircuit> mod = new ArrayList<>();
            for (final ICircuit circuit : circuits) {
                if (circuit instanceof StimulatorCircuit) {
                    mod.add((StimulatorCircuit) circuit);
                }
            }
            return mod.toArray(new StimulatorCircuit[0]);
        }

        @Override
        public float getTerritoryModifier(final IBeeGenome genome, final float currentModifier) {
            float mod = 1.0f;
            if (!this.powered) {
                return mod;
            }
            for (final IBeeModifier beeMod : this.modifiers) {
                mod *= beeMod.getTerritoryModifier(genome, mod);
            }
            return mod;
        }

        @Override
        public float getMutationModifier(final IBeeGenome genome, final IBeeGenome mate, final float currentModifier) {
            float mod = 1.0f;
            if (!this.powered) {
                return mod;
            }
            for (final IBeeModifier beeMod : this.modifiers) {
                mod *= beeMod.getMutationModifier(genome, mate, mod);
            }
            return mod;
        }

        @Override
        public float getLifespanModifier(final IBeeGenome genome, final IBeeGenome mate, final float currentModifier) {
            float mod = 1.0f;
            if (!this.powered) {
                return mod;
            }
            for (final IBeeModifier beeMod : this.modifiers) {
                mod *= beeMod.getLifespanModifier(genome, mate, mod);
            }
            return mod;
        }

        @Override
        public float getProductionModifier(final IBeeGenome genome, final float currentModifier) {
            float mod = 1.0f;
            if (!this.powered) {
                return mod;
            }
            for (final IBeeModifier beeMod : this.modifiers) {
                mod *= beeMod.getProductionModifier(genome, mod);
            }
            return mod;
        }

        @Override
        public float getFloweringModifier(final IBeeGenome genome, final float currentModifier) {
            float mod = 1.0f;
            if (!this.powered) {
                return mod;
            }
            for (final IBeeModifier beeMod : this.modifiers) {
                mod *= beeMod.getFloweringModifier(genome, mod);
            }
            return mod;
        }

        @Override
        public float getGeneticDecay(final IBeeGenome genome, final float currentModifier) {
            float mod = 1.0f;
            if (!this.powered) {
                return mod;
            }
            for (final IBeeModifier beeMod : this.modifiers) {
                mod *= beeMod.getGeneticDecay(genome, mod);
            }
            return mod;
        }

        @Override
        public boolean isSealed() {
            if (!this.powered) {
                return false;
            }
            for (final IBeeModifier beeMod : this.modifiers) {
                if (beeMod.isSealed()) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean isSelfLighted() {
            if (!this.powered) {
                return false;
            }
            for (final IBeeModifier beeMod : this.modifiers) {
                if (beeMod.isSelfLighted()) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean isSunlightSimulated() {
            if (!this.powered) {
                return false;
            }
            for (final IBeeModifier beeMod : this.modifiers) {
                if (beeMod.isSunlightSimulated()) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean isHellish() {
            if (!this.powered) {
                return false;
            }
            for (final IBeeModifier beeMod : this.modifiers) {
                if (beeMod.isHellish()) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public void wearOutEquipment(final int amount) {
            this.getUtil().useEnergyMJ(this.powerUsage);
        }
    }

    public static class StimulatorCircuit extends BinnieCircuit implements IBeeModifier {
        CircuitType type;

        public StimulatorCircuit(final CircuitType type, final ICircuitLayout layout) {
            super("stimulator." + type.toString().toLowerCase(), 4, layout, Mods.Forestry.item("thermionicTubes"), type.recipe);
            this.type = type;
        }

        public int getPowerUsage() {
            return this.type.power;
        }

        @Override
        public float getTerritoryModifier(final IBeeGenome genome, final float currentModifier) {
            return this.type.getTerritoryModifier(genome, currentModifier);
        }

        @Override
        public float getMutationModifier(final IBeeGenome genome, final IBeeGenome mate, final float currentModifier) {
            return this.type.getMutationModifier(genome, mate, currentModifier);
        }

        @Override
        public float getLifespanModifier(final IBeeGenome genome, final IBeeGenome mate, final float currentModifier) {
            return this.type.getLifespanModifier(genome, mate, currentModifier);
        }

        @Override
        public float getProductionModifier(final IBeeGenome genome, final float currentModifier) {
            return this.type.getProductionModifier(genome, currentModifier);
        }

        @Override
        public float getFloweringModifier(final IBeeGenome genome, final float currentModifier) {
            return this.type.getFloweringModifier(genome, currentModifier);
        }

        @Override
        public boolean isSealed() {
            return this.type.isSealed();
        }

        @Override
        public boolean isSelfLighted() {
            return this.type.isSelfLighted();
        }

        @Override
        public boolean isSunlightSimulated() {
            return this.type.isSunlightSimulated();
        }

        @Override
        public boolean isHellish() {
            return this.type.isHellish();
        }

        @Override
        public float getGeneticDecay(final IBeeGenome genome, final float currentModifier) {
            return this.type.getGeneticDecay(genome, currentModifier);
        }
    }

    public enum CircuitType implements IBeeModifier {
        LowVoltage(3, 10),
        HighVoltage(5, 20),
        Plant(10, 10),
        Death(6, 10),
        Life(11, 10),
        Nether(7, 15),
        Mutation(4, 15),
        Inhibitor(1, 10),
        Territory(2, 10);

        public int recipe;
        public int power;
        BeeModifierLogic logic;

        CircuitType(final int recipe, final int power) {
            this.logic = new BeeModifierLogic();
            this.recipe = recipe;
            this.power = power;
        }

        public void createCircuit(final ICircuitLayout layout) {
            final StimulatorCircuit circuit = new StimulatorCircuit(this, layout);
            for (final EnumBeeModifier modifier : EnumBeeModifier.values()) {
                final float mod = this.logic.getModifier(modifier, 1.0f);
                if (mod != 1.0f) {
                    if (mod > 1.0f) {
                        final int increase = (int) ((mod - 1.0f) * 100.0f);
                        circuit.addTooltipString("Increases " + modifier.getName() + " by " + increase + "%");
                    } else {
                        final int decrease = (int) ((1.0f - mod) * 100.0f);
                        circuit.addTooltipString("Decreases " + modifier.getName() + " by " + decrease + "%");
                    }
                }
            }
        }

        @Override
        public float getTerritoryModifier(final IBeeGenome genome, final float currentModifier) {
            return this.logic.getModifier(EnumBeeModifier.Territory, currentModifier);
        }

        @Override
        public float getMutationModifier(final IBeeGenome genome, final IBeeGenome mate, final float currentModifier) {
            return this.logic.getModifier(EnumBeeModifier.Mutation, currentModifier);
        }

        @Override
        public float getLifespanModifier(final IBeeGenome genome, final IBeeGenome mate, final float currentModifier) {
            return this.logic.getModifier(EnumBeeModifier.Lifespan, currentModifier);
        }

        @Override
        public float getProductionModifier(final IBeeGenome genome, final float currentModifier) {
            return this.logic.getModifier(EnumBeeModifier.Production, currentModifier);
        }

        @Override
        public float getFloweringModifier(final IBeeGenome genome, final float currentModifier) {
            return this.logic.getModifier(EnumBeeModifier.Flowering, currentModifier);
        }

        @Override
        public float getGeneticDecay(final IBeeGenome genome, final float currentModifier) {
            return this.logic.getModifier(EnumBeeModifier.GeneticDecay, currentModifier);
        }

        @Override
        public boolean isSealed() {
            return this.logic.getModifier(EnumBeeBooleanModifier.Sealed);
        }

        @Override
        public boolean isSelfLighted() {
            return this.logic.getModifier(EnumBeeBooleanModifier.SelfLighted);
        }

        @Override
        public boolean isSunlightSimulated() {
            return this.logic.getModifier(EnumBeeBooleanModifier.SunlightStimulated);
        }

        @Override
        public boolean isHellish() {
            return this.logic.getModifier(EnumBeeBooleanModifier.Hellish);
        }

        static {
            CircuitType.LowVoltage.logic.setModifier(EnumBeeModifier.Production, 1.5f, 5.0f);
            CircuitType.HighVoltage.logic.setModifier(EnumBeeModifier.Production, 2.5f, 10.0f);
            CircuitType.Plant.logic.setModifier(EnumBeeModifier.Flowering, 1.5f, 5.0f);
            CircuitType.Death.logic.setModifier(EnumBeeModifier.Lifespan, 0.8f, 0.2f);
            CircuitType.Life.logic.setModifier(EnumBeeModifier.Lifespan, 1.5f, 5.0f);
            CircuitType.Nether.logic.setModifier(EnumBeeBooleanModifier.Hellish);
            CircuitType.Mutation.logic.setModifier(EnumBeeModifier.Mutation, 1.5f, 5.0f);
            CircuitType.Inhibitor.logic.setModifier(EnumBeeModifier.Territory, 0.4f, 0.1f);
            CircuitType.Inhibitor.logic.setModifier(EnumBeeModifier.Production, 0.9f, 0.5f);
            CircuitType.Territory.logic.setModifier(EnumBeeModifier.Territory, 1.5f, 5.0f);
            for (final CircuitType type : values()) {
                type.logic.setModifier(EnumBeeModifier.GeneticDecay, 1.5f, 10.0f);
            }
        }
    }
}
