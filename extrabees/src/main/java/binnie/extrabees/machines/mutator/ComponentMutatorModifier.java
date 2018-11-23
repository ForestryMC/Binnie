package binnie.extrabees.machines.mutator;

import net.minecraft.item.ItemStack;

import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeListener;
import forestry.api.apiculture.IBeeModifier;

import binnie.core.machines.Machine;
import binnie.extrabees.utils.AlvearyMutationHandler;
import binnie.extrabees.utils.ComponentBeeModifier;

public class ComponentMutatorModifier extends ComponentBeeModifier implements IBeeModifier, IBeeListener {
	public ComponentMutatorModifier(Machine machine) {
		super(machine);
	}

	@Override
	public float getMutationModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
		ItemStack mutator = this.getUtil().getStack(AlvearyMutator.SLOT_MUTATOR);
		float mult = AlvearyMutationHandler.getMutationMult(mutator);
		return Math.min(mult * currentModifier, 15f);
	}

	@Override
	public void onQueenDeath() {
		this.getUtil().decreaseStack(AlvearyMutator.SLOT_MUTATOR, 1);
	}
}
