package binnie.extrabees.apiary;

import binnie.core.machines.Machine;
import binnie.core.machines.MachineComponent;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeListener;
import forestry.api.apiculture.IBeeModifier;
import forestry.api.genetics.IIndividual;
import net.minecraft.item.ItemStack;

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
    public float getLifespanModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
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

    // TODO unused method?
    public void onQueenChange(ItemStack queen) {
        // ignored
    }

    @Override
    public void wearOutEquipment(int amount) {
        // ignored
    }

    // TODO unused method?
    public boolean onEggLaid(IBee queen) {
        return false;
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
        // ignored
    }
}
