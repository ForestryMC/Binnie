package binnie.genetics.gui;

import binnie.Binnie;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.minecraft.control.ControlItemDisplay;
import binnie.core.genetics.BreedingSystem;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IIndividual;
import net.minecraft.item.ItemStack;

public class ControlIndividualDisplay extends ControlItemDisplay implements ITooltip {
    public ControlIndividualDisplay(IWidget parent, float x, float y, IIndividual ind) {
        this(parent, x, y, 16.0f, ind);
    }

    public ControlIndividualDisplay(IWidget parent, float x, float y, float size, IIndividual ind) {
        super(parent, x, y, size);
        BreedingSystem system = Binnie.Genetics.getSystem(ind.getGenome().getSpeciesRoot());
        setItemStack(system.getSpeciesRoot().getMemberStack(ind, system.getDefaultType()));
        setTooltip();
    }

    @Override
    public void getTooltip(Tooltip tooltip) {
        ItemStack stack = getItemStack();
        if (stack == null) {
            return;
        }

        IIndividual ind = AlleleManager.alleleRegistry.getIndividual(stack);
        if (ind == null) {
            return;
        }
        tooltip.add(ind.getGenome().getPrimary().getName());
    }
}
