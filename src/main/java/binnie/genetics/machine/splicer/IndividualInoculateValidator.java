package binnie.genetics.machine.splicer;

import binnie.Binnie;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.util.I18N;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.ISpeciesRoot;
import net.minecraft.item.ItemStack;

public class IndividualInoculateValidator extends SlotValidator {
    public IndividualInoculateValidator() {
        super(null);
    }

    @Override
    public boolean isValid(ItemStack object) {
        ISpeciesRoot root = AlleleManager.alleleRegistry.getSpeciesRoot(object);
        return root != null && Binnie.Genetics.getSystem(root).isDNAManipulable(object);
    }

    @Override
    public String getTooltip() {
        return I18N.localise("genetics.machine.splicer.splicableIndividual");
    }
}
