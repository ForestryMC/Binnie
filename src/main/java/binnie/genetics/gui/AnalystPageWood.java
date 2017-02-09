package binnie.genetics.gui;

import binnie.core.util.UniqueItemStackSet;
import binnie.craftgui.controls.ControlTextCentered;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.minecraft.control.ControlItemDisplay;
import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.arboriculture.IAlleleTreeSpecies;
import forestry.api.arboriculture.ITree;
import forestry.api.arboriculture.ITreeGenome;
import forestry.api.genetics.IAlleleBoolean;
import net.minecraft.item.ItemStack;

import java.util.Collection;

public class AnalystPageWood extends AnalystPageProduce {
	public AnalystPageWood(final IWidget parent, final IArea area, final ITree ind) {
		super(parent, area);
		this.setColour(6697728);
		final ITreeGenome genome = ind.getGenome();
		int y = 4;
		new ControlTextCentered(this, y, "§nWood").setColour(this.getColour());
		y += 12;
		if (((IAlleleBoolean) ind.getGenome().getActiveAllele(EnumTreeChromosome.FIREPROOF)).getValue()) {
			//new ControlIconDisplay(this, (this.w() - 16.0f) / 2.0f, y, ModuleItem.iconNoFire.getIcon()).addTooltip("Fireproof");
		} else {
			//new ControlIconDisplay(this, (this.w() - 16.0f) / 2.0f, y, ModuleItem.iconFire.getIcon()).addTooltip("Flammable");
		}
		y += 30;
		final Collection<ItemStack> products = new UniqueItemStackSet();

		final IAlleleTreeSpecies treeSpecies = genome.getPrimary();
		final ItemStack stackWood = treeSpecies.getWoodProvider().getWoodStack();
		if (!stackWood.isEmpty()) {
			products.add(stackWood);
		}
		// for (final ItemStack stack :
		// ind.getGenome().getFruitProvider().getProducts()) {
		// products.add(stack);
		// }
		if (products.size() > 0) {
			new ControlTextCentered(this, y, "Logs").setColour(this.getColour());
			y += 10;
			final int w = products.size() * 18 - 2;
			final int i = 0;
			for (final ItemStack stack : products) {
				final ControlItemDisplay d = new ControlItemDisplay(this, (this.w() - w) / 2 + 18 * i, y);
				d.setTooltip();
				d.setItemStack(stack);
			}
			y += 26;
		}
		final Collection<ItemStack> allProducts = new UniqueItemStackSet();
		for (final ItemStack stack2 : products) {
			allProducts.add(stack2);
		}
		final Collection<ItemStack> refinedProducts = new UniqueItemStackSet();
		refinedProducts.addAll(this.getAllProductsAndFluids(allProducts));
		if (refinedProducts.size() > 0) {
			y = this.getRefined("Refined Products", y, refinedProducts);
			y += 8;
		}
		if (products.size() == 0) {
			new ControlTextCentered(this, y, "This tree has no \nfruits or nuts").setColour(this.getColour());
			y += 28;
		}
		this.setSize(new IPoint(this.w(), y + 8));
	}

	@Override
	public String getTitle() {
		return "Wood";
	}
}
