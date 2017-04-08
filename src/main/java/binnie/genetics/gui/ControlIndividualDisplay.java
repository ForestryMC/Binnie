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
	public ControlIndividualDisplay(final IWidget parent, final int x, final int y, final IIndividual ind) {
		this(parent, x, y, 16, ind);
	}

	public ControlIndividualDisplay(final IWidget parent, final int x, final int y, final int size, final IIndividual ind) {
		super(parent, x, y, size);
		final BreedingSystem system = Binnie.GENETICS.getSystem(ind.getGenome().getSpeciesRoot());
		this.setItemStack(system.getSpeciesRoot().getMemberStack(ind, system.getDefaultType()));
		this.setTooltip();
	}

	@Override
	public void getTooltip(final Tooltip tooltip) {
		final ItemStack stack = this.getItemStack();
		if (stack.isEmpty()) {
			return;
		}
		final IIndividual ind = AlleleManager.alleleRegistry.getIndividual(stack);
		if (ind == null) {
			return;
		}
		tooltip.add(ind.getGenome().getPrimary().getName());
	}
}
