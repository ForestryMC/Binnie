// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extrabees.apiary;

import forestry.api.genetics.IIndividual;
import forestry.api.apiculture.IBee;
import net.minecraft.item.ItemStack;
import forestry.api.apiculture.IBeeGenome;
import binnie.core.machines.Machine;
import forestry.api.apiculture.IBeeListener;
import forestry.api.apiculture.IBeeModifier;
import binnie.core.machines.MachineComponent;

public class ComponentBeeModifier extends MachineComponent implements IBeeModifier, IBeeListener
{
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
	public float getLifespanModifier(final IBeeGenome genome, final IBeeGenome mate, final float currentModifier) {
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

	public void onQueenChange(final ItemStack queen) {
	}

	@Override
	public void wearOutEquipment(final int amount) {
	}

	public void onPostQueenDeath(final IBee queen) {
	}

	public boolean onEggLaid(final IBee queen) {
		return false;
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
