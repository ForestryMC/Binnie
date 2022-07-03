package binnie.extrabees.apiary.machine.stimulator;

import binnie.core.Mods;
import binnie.core.circuits.BinnieCircuit;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeModifier;
import forestry.api.circuits.ICircuitLayout;

public class StimulatorCircuit extends BinnieCircuit implements IBeeModifier {
    protected CircuitType type;

    public StimulatorCircuit(CircuitType type, ICircuitLayout layout) {
        super(
                "stimulator." + type.toString().toLowerCase(),
                4,
                layout,
                Mods.forestry.item("thermionicTubes"),
                type.recipe);
        this.type = type;
    }

    public int getPowerUsage() {
        return type.power;
    }

    @Override
    public float getTerritoryModifier(IBeeGenome genome, float currentModifier) {
        return type.getTerritoryModifier(genome, currentModifier);
    }

    @Override
    public float getMutationModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
        return type.getMutationModifier(genome, mate, currentModifier);
    }

    @Override
    public float getLifespanModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
        return type.getLifespanModifier(genome, mate, currentModifier);
    }

    @Override
    public float getProductionModifier(IBeeGenome genome, float currentModifier) {
        return type.getProductionModifier(genome, currentModifier);
    }

    @Override
    public float getFloweringModifier(IBeeGenome genome, float currentModifier) {
        return type.getFloweringModifier(genome, currentModifier);
    }

    @Override
    public boolean isSealed() {
        return type.isSealed();
    }

    @Override
    public boolean isSelfLighted() {
        return type.isSelfLighted();
    }

    @Override
    public boolean isSunlightSimulated() {
        return type.isSunlightSimulated();
    }

    @Override
    public boolean isHellish() {
        return type.isHellish();
    }

    @Override
    public float getGeneticDecay(IBeeGenome genome, float currentModifier) {
        return type.getGeneticDecay(genome, currentModifier);
    }
}
