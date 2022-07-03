package binnie.extrabees.apiary.machine.stimulator;

import binnie.core.genetics.BeeModifierLogic;
import binnie.core.genetics.EnumBeeBooleanModifier;
import binnie.core.genetics.EnumBeeModifier;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeModifier;
import forestry.api.circuits.ICircuitLayout;

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

    static {
        CircuitType.LowVoltage.logic.setModifier(EnumBeeModifier.PRODUCTION, 1.5f, 5.0f);
        CircuitType.HighVoltage.logic.setModifier(EnumBeeModifier.PRODUCTION, 2.5f, 10.0f);
        CircuitType.Plant.logic.setModifier(EnumBeeModifier.FLOWERING, 1.5f, 5.0f);
        CircuitType.Death.logic.setModifier(EnumBeeModifier.LIFESPAN, 0.8f, 0.2f);
        CircuitType.Life.logic.setModifier(EnumBeeModifier.LIFESPAN, 1.5f, 5.0f);
        CircuitType.Nether.logic.setModifier(EnumBeeBooleanModifier.HELLISH);
        CircuitType.Mutation.logic.setModifier(EnumBeeModifier.MUTATION, 1.5f, 5.0f);
        CircuitType.Inhibitor.logic.setModifier(EnumBeeModifier.TERRITORY, 0.4f, 0.1f);
        CircuitType.Inhibitor.logic.setModifier(EnumBeeModifier.PRODUCTION, 0.9f, 0.5f);
        CircuitType.Territory.logic.setModifier(EnumBeeModifier.TERRITORY, 1.5f, 5.0f);
        for (CircuitType type : values()) {
            type.logic.setModifier(EnumBeeModifier.GENETIC_DECAY, 1.5f, 10.0f);
        }
    }

    public int recipe;
    public int power;

    protected BeeModifierLogic logic;

    CircuitType(int recipe, int power) {
        logic = new BeeModifierLogic();
        this.recipe = recipe;
        this.power = power;
    }

    public void createCircuit(ICircuitLayout layout) {
        StimulatorCircuit circuit = new StimulatorCircuit(this, layout);
        for (EnumBeeModifier modifier : EnumBeeModifier.values()) {
            float mod = logic.getModifier(modifier, 1.0f);
            if (mod == 1.0f) {
                continue;
            }

            if (mod > 1.0f) {
                int increase = (int) ((mod - 1.0f) * 100.0f);
                circuit.addTooltipString("Increases " + modifier.getName() + " by " + increase + "%");
            } else {
                int decrease = (int) ((1.0f - mod) * 100.0f);
                circuit.addTooltipString("Decreases " + modifier.getName() + " by " + decrease + "%");
            }
        }
    }

    @Override
    public float getTerritoryModifier(IBeeGenome genome, float currentModifier) {
        return logic.getModifier(EnumBeeModifier.TERRITORY, currentModifier);
    }

    @Override
    public float getMutationModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
        return logic.getModifier(EnumBeeModifier.MUTATION, currentModifier);
    }

    @Override
    public float getLifespanModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
        return logic.getModifier(EnumBeeModifier.LIFESPAN, currentModifier);
    }

    @Override
    public float getProductionModifier(IBeeGenome genome, float currentModifier) {
        return logic.getModifier(EnumBeeModifier.PRODUCTION, currentModifier);
    }

    @Override
    public float getFloweringModifier(IBeeGenome genome, float currentModifier) {
        return logic.getModifier(EnumBeeModifier.FLOWERING, currentModifier);
    }

    @Override
    public float getGeneticDecay(IBeeGenome genome, float currentModifier) {
        return logic.getModifier(EnumBeeModifier.GENETIC_DECAY, currentModifier);
    }

    @Override
    public boolean isSealed() {
        return logic.getModifier(EnumBeeBooleanModifier.SEALED);
    }

    @Override
    public boolean isSelfLighted() {
        return logic.getModifier(EnumBeeBooleanModifier.SELF_LIGHTED);
    }

    @Override
    public boolean isSunlightSimulated() {
        return logic.getModifier(EnumBeeBooleanModifier.SUNLIGHT_STIMULATED);
    }

    @Override
    public boolean isHellish() {
        return logic.getModifier(EnumBeeBooleanModifier.HELLISH);
    }
}
