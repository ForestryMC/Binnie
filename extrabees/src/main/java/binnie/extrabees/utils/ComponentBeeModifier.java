package binnie.extrabees.utils;

import javax.annotation.Nullable;

import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeListener;
import forestry.api.apiculture.IBeeModifier;
import forestry.api.genetics.IIndividual;

import binnie.core.machines.Machine;
import binnie.core.machines.MachineComponent;

public class ComponentBeeModifier extends MachineComponent implements IBeeModifier, IBeeListener {
	public ComponentBeeModifier(Machine machine) {
		super(machine);
	}

	@Override
	public float getTerritoryModifier(IBeeGenome genome, float currentModifier) {
		return 1.0f;
	}

	@Override
	public float getMutationModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
		return 1.0f;
	}

	@Override
	public float getLifespanModifier(IBeeGenome genome, @Nullable IBeeGenome mate, float currentModifier) {
		return 1.0f;
	}

	@Override
	public float getProductionModifier(IBeeGenome genome, float currentModifier) {
		return 1.0f;
	}

	@Override
	public float getFloweringModifier(IBeeGenome genome, float currentModifier) {
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
	public void wearOutEquipment(int amount) {
	}

	@Override
	public float getGeneticDecay(IBeeGenome genome, float currentModifier) {
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
