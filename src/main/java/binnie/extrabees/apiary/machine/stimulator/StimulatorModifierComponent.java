package binnie.extrabees.apiary.machine.stimulator;

import binnie.core.machines.Machine;
import binnie.extrabees.apiary.ComponentBeeModifier;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeListener;
import forestry.api.apiculture.IBeeModifier;
import forestry.api.circuits.ChipsetManager;
import forestry.api.circuits.ICircuit;
import forestry.api.circuits.ICircuitBoard;
import java.util.ArrayList;
import java.util.List;

public class StimulatorModifierComponent extends ComponentBeeModifier implements IBeeModifier, IBeeListener {
    protected float powerUsage;
    protected boolean powered;
    protected StimulatorCircuit[] modifiers;

    public StimulatorModifierComponent(Machine machine) {
        super(machine);
        powerUsage = 0.0f;
        powered = false;
        modifiers = new StimulatorCircuit[0];
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        modifiers = getCircuits();
        powerUsage = 0.0f;
        for (StimulatorCircuit beeMod : modifiers) {
            powerUsage += beeMod.getPowerUsage();
        }
        powered = getUtil().hasEnergyMJ(powerUsage);
    }

    public ICircuitBoard getHiveFrame() {
        if (!getUtil().isSlotEmpty(AlvearyStimulator.SLOT_CIRCUIT)) {
            return ChipsetManager.circuitRegistry.getCircuitboard(getUtil().getStack(AlvearyStimulator.SLOT_CIRCUIT));
        }
        return null;
    }

    public StimulatorCircuit[] getCircuits() {
        ICircuitBoard board = getHiveFrame();
        if (board == null) {
            return new StimulatorCircuit[0];
        }

        ICircuit[] circuits = board.getCircuits();
        List<IBeeModifier> mod = new ArrayList<>();
        for (ICircuit circuit : circuits) {
            if (circuit instanceof StimulatorCircuit) {
                mod.add((IBeeModifier) circuit);
            }
        }
        return mod.toArray(new StimulatorCircuit[0]);
    }

    @Override
    public float getTerritoryModifier(IBeeGenome genome, float currentModifier) {
        float mod = 1.0f;
        if (!powered) {
            return mod;
        }

        for (IBeeModifier beeMod : modifiers) {
            mod *= beeMod.getTerritoryModifier(genome, mod);
        }
        return mod;
    }

    @Override
    public float getMutationModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
        float mod = 1.0f;
        if (!powered) {
            return mod;
        }

        for (IBeeModifier beeMod : modifiers) {
            mod *= beeMod.getMutationModifier(genome, mate, mod);
        }
        return mod;
    }

    @Override
    public float getLifespanModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
        float mod = 1.0f;
        if (!powered) {
            return mod;
        }

        for (IBeeModifier beeMod : modifiers) {
            mod *= beeMod.getLifespanModifier(genome, mate, mod);
        }
        return mod;
    }

    @Override
    public float getProductionModifier(IBeeGenome genome, float currentModifier) {
        float mod = 1.0f;
        if (!powered) {
            return mod;
        }

        for (IBeeModifier beeMod : modifiers) {
            mod *= beeMod.getProductionModifier(genome, mod);
        }
        return mod;
    }

    @Override
    public float getFloweringModifier(IBeeGenome genome, float currentModifier) {
        float mod = 1.0f;
        if (!powered) {
            return mod;
        }

        for (IBeeModifier beeMod : modifiers) {
            mod *= beeMod.getFloweringModifier(genome, mod);
        }
        return mod;
    }

    @Override
    public float getGeneticDecay(IBeeGenome genome, float currentModifier) {
        float mod = 1.0f;
        if (!powered) {
            return mod;
        }

        for (IBeeModifier beeMod : modifiers) {
            mod *= beeMod.getGeneticDecay(genome, mod);
        }
        return mod;
    }

    @Override
    public boolean isSealed() {
        if (!powered) {
            return false;
        }

        for (IBeeModifier beeMod : modifiers) {
            if (beeMod.isSealed()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isSelfLighted() {
        if (!powered) {
            return false;
        }

        for (IBeeModifier beeMod : modifiers) {
            if (beeMod.isSelfLighted()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isSunlightSimulated() {
        if (!powered) {
            return false;
        }

        for (IBeeModifier beeMod : modifiers) {
            if (beeMod.isSunlightSimulated()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isHellish() {
        if (!powered) {
            return false;
        }

        for (IBeeModifier beeMod : modifiers) {
            if (beeMod.isHellish()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void wearOutEquipment(int amount) {
        getUtil().useEnergyMJ(powerUsage);
    }
}
