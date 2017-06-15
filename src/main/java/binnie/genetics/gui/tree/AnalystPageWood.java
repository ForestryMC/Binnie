package binnie.genetics.gui.tree;

import java.util.Collection;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.arboriculture.IAlleleTreeSpecies;
import forestry.api.arboriculture.ITree;
import forestry.api.arboriculture.ITreeGenome;
import forestry.api.genetics.IAlleleBoolean;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.geometry.Area;
import binnie.core.craftgui.geometry.Point;
import binnie.core.craftgui.minecraft.control.ControlIconDisplay;
import binnie.core.craftgui.minecraft.control.ControlItemDisplay;
import binnie.core.util.UniqueItemStackSet;
import binnie.genetics.Genetics;
import binnie.genetics.gui.AnalystPageProduce;
import binnie.genetics.item.ModuleItems;

@SideOnly(Side.CLIENT)
public class AnalystPageWood extends AnalystPageProduce {
	public AnalystPageWood(final IWidget parent, final Area area, final ITree ind) {
		super(parent, area);
		this.setColour(6697728);
		final ITreeGenome genome = ind.getGenome();
		int y = 4;
		new ControlTextCentered(this, y, TextFormatting.UNDERLINE + getTitle()).setColour(this.getColour());
		y += 12;
		if (((IAlleleBoolean) ind.getGenome().getActiveAllele(EnumTreeChromosome.FIREPROOF)).getValue()) {
			new ControlIconDisplay(this, (width() - 16) / 2, y, ModuleItems.iconNoFire).addTooltip(Genetics.proxy.localise("gui.analyst.wood.fireproof"));
		} else {
			new ControlIconDisplay(this, (width() - 16) / 2, y, ModuleItems.iconFire).addTooltip(Genetics.proxy.localise("gui.analyst.wood.flammable"));
		}
		y += 30;
		final Collection<ItemStack> products = new UniqueItemStackSet();

		final IAlleleTreeSpecies treeSpecies = genome.getPrimary();
		final ItemStack stackWood = treeSpecies.getWoodProvider().getWoodStack();
		if (!stackWood.isEmpty()) {
			products.add(stackWood);
		}
		products.addAll(ind.getGenome().getFruitProvider().getProducts().keySet());
		if (products.size() > 0) {
			new ControlTextCentered(this, y, Genetics.proxy.localise("gui.analyst.wood.logs")).setColour(this.getColour());
			y += 10;
			final int w = products.size() * 18 - 2;
			final int i = 0;
			for (final ItemStack stack : products) {
				final ControlItemDisplay d = new ControlItemDisplay(this, (this.width() - w) / 2 + 18 * i, y);
				d.setTooltip();
				d.setItemStack(stack);
			}
			y += 26;
		}
		final Collection<ItemStack> allProducts = new UniqueItemStackSet();
		allProducts.addAll(products);
		final Collection<ItemStack> refinedProducts = new UniqueItemStackSet();
		refinedProducts.addAll(this.getAllProductsAndFluids(allProducts));
		if (refinedProducts.size() > 0) {
			y = this.getRefined(Genetics.proxy.localise("gui.analyst.wood.refined"), y, refinedProducts);
			y += 8;
		}
		if (products.size() == 0) {
			new ControlTextCentered(this, y, Genetics.proxy.localise("gui.analyst.wood.noFruits")).setColour(this.getColour());
			y += 28;
		}
		this.setSize(new Point(this.width(), y + 8));
	}

	@Override
	public String getTitle() {
		return Genetics.proxy.localise("gui.analyst.wood.title");
	}
}
