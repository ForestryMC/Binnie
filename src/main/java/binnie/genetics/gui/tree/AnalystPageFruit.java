package binnie.genetics.gui.tree;

import binnie.Binnie;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.geometry.Area;
import binnie.core.craftgui.geometry.Point;
import binnie.core.craftgui.minecraft.control.ControlItemDisplay;
import binnie.core.util.UniqueItemStackSet;
import binnie.genetics.Genetics;
import binnie.genetics.gui.AnalystPageProduce;
import com.google.common.base.Throwables;
import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.arboriculture.IAlleleFruit;
import forestry.api.arboriculture.ITree;
import forestry.api.arboriculture.ITreeGenome;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IFruitFamily;
import forestry.arboriculture.FruitProviderPod;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class AnalystPageFruit extends AnalystPageProduce {
	public AnalystPageFruit(final IWidget parent, final Area area, final ITree ind) {
		super(parent, area);
		this.setColour(13382400);
		final ITreeGenome genome = ind.getGenome();
		int y = 4;
		new ControlTextCentered(this, y, TextFormatting.UNDERLINE + getTitle()).setColour(this.getColour());
		y += 12;
		new ControlTextCentered(this, y, TextFormatting.ITALIC + Genetics.proxy.localise("gui.analyst.fruit.yield") + ": " + Binnie.GENETICS.treeBreedingSystem.getAlleleName(EnumTreeChromosome.YIELD, ind.getGenome().getActiveAllele(EnumTreeChromosome.YIELD))).setColour(this.getColour());
		y += 20;
		final Collection<ItemStack> products = new UniqueItemStackSet();
		final Collection<ItemStack> specialties = new UniqueItemStackSet();
		final Collection<ItemStack> wiid = new UniqueItemStackSet();
		products.addAll(ind.getProducts().keySet());
		specialties.addAll(ind.getSpecialties().keySet());
		try {
			if (ind.getGenome().getFruitProvider() instanceof FruitProviderPod) {
				final FruitProviderPod pod = (FruitProviderPod) ind.getGenome().getFruitProvider();
				final Field f = FruitProviderPod.class.getDeclaredField("drops");
				f.setAccessible(true);
				Collections.addAll(products, ((Map<ItemStack, Float>) f.get(pod)).keySet().toArray(new ItemStack[0]));
			}
		} catch (Exception e) {
			throw Throwables.propagate(e);
		}
		if (products.size() > 0) {
			new ControlTextCentered(this, y, Genetics.proxy.localise("gui.analyst.fruit.natural")).setColour(this.getColour());
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
		if (specialties.size() > 0) {
			new ControlTextCentered(this, y, Genetics.proxy.localise("gui.analyst.fruit.specialty")).setColour(this.getColour());
			y += 10;
			final int w = products.size() * 18 - 2;
			final int i = 0;
			for (final ItemStack stack : specialties) {
				final ControlItemDisplay d = new ControlItemDisplay(this, (this.width() - w) / 2 + 18 * i, y);
				d.setTooltip();
				d.setItemStack(stack);
			}
			y += 26;
		}
		final Collection<ItemStack> allProducts = new UniqueItemStackSet();
		allProducts.addAll(products);
		allProducts.addAll(specialties);
		final Collection<ItemStack> refinedProducts = new UniqueItemStackSet();
		refinedProducts.addAll(this.getAllProductsAndFluids(allProducts));
		if (refinedProducts.size() > 0) {
			y = this.getRefined(Genetics.proxy.localise("gui.analyst.fruit.refined"), y, refinedProducts);
			y += 8;
		}
		if (products.size() == 0 && specialties.size() == 0) {
			new ControlTextCentered(this, y, Genetics.proxy.localise("gui.analyst.fruit.noFruits")).setColour(this.getColour());
			y += 28;
		}
		new ControlTextCentered(this, y, Genetics.proxy.localise("gui.analyst.fruit.possible")).setColour(this.getColour());
		y += 12;
		final Collection<IAllele> fruitAlleles = Binnie.GENETICS.getChromosomeMap(Binnie.GENETICS.getTreeRoot()).get(EnumTreeChromosome.FRUITS);
		for (final IFruitFamily fam : ind.getGenome().getPrimary().getSuitableFruit()) {
			final Collection<ItemStack> stacks = new UniqueItemStackSet();
			for (final IAllele a : fruitAlleles) {
				if (((IAlleleFruit) a).getProvider().getFamily() == fam) {
					stacks.addAll(((IAlleleFruit) a).getProvider().getProducts().keySet());
					stacks.addAll(((IAlleleFruit) a).getProvider().getSpecialty().keySet());
					try {
						if (a.getUID().contains("fruitCocoa")) {
							stacks.add(new ItemStack(Items.DYE, 1, 3));
						} else {
							if (!(((IAlleleFruit) a).getProvider() instanceof FruitProviderPod)) {
								continue;
							}
							final FruitProviderPod pod2 = (FruitProviderPod) ((IAlleleFruit) a).getProvider();
							final Field field = FruitProviderPod.class.getDeclaredField("drops");
							field.setAccessible(true);
							Collections.addAll(stacks, ((Map<ItemStack, Float>) field.get(pod2)).keySet().toArray(new ItemStack[0]));
						}
					} catch (Exception ex) {
						throw Throwables.propagate(ex);
					}
				}
			}
			y = this.getRefined(TextFormatting.ITALIC + fam.getName(), y, stacks);
			y += 2;
		}
		this.setSize(new Point(this.width(), y + 8));
	}

	@Override
	public String getTitle() {
		return Genetics.proxy.localise("gui.analyst.fruit.title");
	}
}
