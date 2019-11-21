package binnie.extrabees.utils;

import binnie.core.machines.Machine;
import binnie.core.machines.MachineComponent;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeListener;
import forestry.api.apiculture.IBeeModifier;
import forestry.api.genetics.IIndividual;

import javax.annotation.Nullable;

public class ComponentBeeModifier extends MachineComponent implements IBeeModifier, IBeeListener {
	public ComponentBeeModifier(final Machine machine) {
		super(machine);
	}

	@Override
	public float getTerritoryModifier(final IBeeGenome genome, final float currentModifier) {
		return 1.0f;
	}

	@Override
	public float getMutationModifier(final IBeeGenome genome, final IBeeGenome mate, final float currentModifier) {
		return 1.0f;
	}

	@Override
	public float getLifespanModifier(final IBeeGenome genome, @Nullable final IBeeGenome mate, final float currentModifier) {
		return 1.0f;
	}

	@Override
	public float getProductionModifier(final IBeeGenome genome, final float currentModifier) {
		return 1.0f;
	}

	@Override
	public float getFloweringModifier(final IBeeGenome genome, final float currentModifier) {
		return 1.0f;
	}

	@Override
	public boolean isSealed() {
		return false;
	}

	@Override
	public boolean isSelfLighted() {
		return false;
	}

	@Override
	public boolean isSunlightSimulated() {
		return false;
	}

	@Override
	public boolean isHellish() {
		return false;
	}

	@Override
	public void wearOutEquipment(final int amount) {
	}

	@Override
	public float getGeneticDecay(final IBeeGenome genome, final float currentModifier) {
		return 1.0f;
	}

	@Override
	public boolean onPollenRetrieved(IIndividual individual) {
		return false;
	}

	@Override
	public void onQueenDeath() {

	}
}
