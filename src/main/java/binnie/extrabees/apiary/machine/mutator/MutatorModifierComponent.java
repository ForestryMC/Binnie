package binnie.extrabees.apiary.machine.mutator;

import binnie.core.machines.Machine;
import binnie.extrabees.apiary.ComponentBeeModifier;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeListener;
import forestry.api.apiculture.IBeeModifier;

public class MutatorModifierComponent extends ComponentBeeModifier implements IBeeModifier, IBeeListener {
    public MutatorModifierComponent(Machine machine) {
        super(machine);
    }

    @Override
    public float getMutationModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
        if (getUtil().isSlotEmpty(AlvearyMutator.SLOT_MUTATOR)) {
            return 1.0f;
        }

        float mult = AlvearyMutator.getMutationMult(getUtil().getStack(AlvearyMutator.SLOT_MUTATOR));
        return Math.min(mult, 15.0f / currentModifier);
    }

    @Override
    public void onQueenDeath() {
        getUtil().decreaseStack(AlvearyMutator.SLOT_MUTATOR, 1);
    }
}
