package binnie.core.gui.controls;

import binnie.core.api.genetics.IBreedingSystem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;

import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IIndividual;

import binnie.core.Binnie;
import binnie.core.gui.ITooltip;
import binnie.core.api.gui.IWidget;
import binnie.core.gui.Tooltip;
import binnie.core.gui.minecraft.control.ControlItemDisplay;

public class ControlIndividualDisplay extends ControlItemDisplay implements ITooltip {
	public ControlIndividualDisplay(IWidget parent, int x, int y, IIndividual ind) {
		this(parent, x, y, 16, ind);
	}

	public ControlIndividualDisplay(IWidget parent, int x, int y, int size, IIndividual ind) {
		super(parent, x, y, size);
		final IBreedingSystem system = Binnie.GENETICS.getSystem(ind.getGenome().getSpeciesRoot());
		setItemStack(system.getSpeciesRoot().getMemberStack(ind, system.getDefaultType()));
		setTooltip();
	}

	@Override
	public void getTooltip(Tooltip tooltip, ITooltipFlag tooltipFlag) {
		final ItemStack stack = getItemStack();
		if (stack.isEmpty()) {
			return;
		}
		final IIndividual ind = AlleleManager.alleleRegistry.getIndividual(stack);
		if (ind == null) {
			return;
		}
		tooltip.add(ind.getGenome().getPrimary().getAlleleName());
	}
}
