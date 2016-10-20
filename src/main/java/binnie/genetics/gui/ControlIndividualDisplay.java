package binnie.genetics.gui;

import binnie.Binnie;
import binnie.core.genetics.BreedingSystem;
import binnie.craftgui.core.ITooltip;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.Tooltip;
import binnie.craftgui.minecraft.control.ControlItemDisplay;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IIndividual;
import net.minecraft.item.ItemStack;

public class ControlIndividualDisplay extends ControlItemDisplay implements ITooltip {
    public ControlIndividualDisplay(final IWidget parent, final float x, final float y, final IIndividual ind) {
        this(parent, x, y, 16.0f, ind);
    }

    public ControlIndividualDisplay(final IWidget parent, final float x, final float y, final float size, final IIndividual ind) {
        super(parent, x, y, size);
        final BreedingSystem system = Binnie.Genetics.getSystem(ind.getGenome().getSpeciesRoot());
        this.setItemStack(system.getSpeciesRoot().getMemberStack(ind, system.getDefaultType()));
        this.setTooltip();
    }

    @Override
    public void getTooltip(final Tooltip tooltip) {
        final ItemStack stack = this.getItemStack();
        if (stack == null) {
            return;
        }
        final IIndividual ind = AlleleManager.alleleRegistry.getIndividual(stack);
        if (ind == null) {
            return;
        }
        tooltip.add(ind.getGenome().getPrimary().getName());
    }
}
