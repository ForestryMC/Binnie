package binnie.genetics.gui.tree;

import com.google.common.base.Throwables;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.arboriculture.IAlleleFruit;
import forestry.api.arboriculture.ITree;
import forestry.api.arboriculture.ITreeGenome;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IFruitFamily;
import forestry.arboriculture.FruitProviderPod;

import binnie.Binnie;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.geometry.Area;
import binnie.core.craftgui.geometry.Point;
import binnie.core.craftgui.minecraft.control.ControlItemDisplay;
import binnie.core.util.I18N;
import binnie.core.util.UniqueItemStackSet;
import binnie.genetics.gui.AnalystPageProduce;

public class AnalystPageFruit extends AnalystPageProduce {
	public AnalystPageFruit(IWidget parent, Area area, ITree ind) {
		super(parent, area);
		setColor(13382400);
		ITreeGenome genome = ind.getGenome();
		int y = 4;
		new ControlTextCentered(this, y, TextFormatting.UNDERLINE + getTitle()).setColor(getColor());
		y += 12;
		new ControlTextCentered(this, y, TextFormatting.ITALIC + I18N.localise("genetics.gui.analyst.fruit.yield") + ": " + Binnie.GENETICS.treeBreedingSystem.getAlleleName(EnumTreeChromosome.YIELD, ind.getGenome().getActiveAllele(EnumTreeChromosome.YIELD))).setColor(getColor());
		y += 20;
		Collection<ItemStack> products = new UniqueItemStackSet();
		Collection<ItemStack> specialties = new UniqueItemStackSet();
		Collection<ItemStack> wiid = new UniqueItemStackSet();
		products.addAll(ind.getProducts().keySet());
		specialties.addAll(ind.getSpecialties().keySet());
		try {
			if (ind.getGenome().getFruitProvider() instanceof FruitProviderPod) {
				FruitProviderPod pod = (FruitProviderPod) ind.getGenome().getFruitProvider();
				Field f = FruitProviderPod.class.getDeclaredField("drops");
				f.setAccessible(true);
				Collections.addAll(products, ((Map<ItemStack, Float>) f.get(pod)).keySet().toArray(new ItemStack[0]));
			}
		} catch (Exception e) {
			throw Throwables.propagate(e);
		}
		if (products.size() > 0) {
			new ControlTextCentered(this, y, I18N.localise("genetics.gui.analyst.fruit.natural")).setColor(getColor());
			y += 10;
			int w = products.size() * 18 - 2;
			int i = 0;
			for (ItemStack stack : products) {
				ControlItemDisplay d = new ControlItemDisplay(this, (width() - w) / 2 + 18 * i, y);
				d.setTooltip();
				d.setItemStack(stack);
			}
			y += 26;
		}
		if (specialties.size() > 0) {
			new ControlTextCentered(this, y, I18N.localise("genetics.gui.analyst.fruit.specialty")).setColor(getColor());
			y += 10;
			int w = products.size() * 18 - 2;
			int i = 0;
			for (ItemStack stack : specialties) {
				ControlItemDisplay d = new ControlItemDisplay(this, (width() - w) / 2 + 18 * i, y);
				d.setTooltip();
				d.setItemStack(stack);
			}
			y += 26;
		}
		Collection<ItemStack> allProducts = new UniqueItemStackSet();
		allProducts.addAll(products);
		allProducts.addAll(specialties);
		Collection<ItemStack> refinedProducts = new UniqueItemStackSet();
		refinedProducts.addAll(getAllProductsAndFluids(allProducts));
		if (refinedProducts.size() > 0) {
			y = getRefined(I18N.localise("genetics.gui.analyst.fruit.refined"), y, refinedProducts);
			y += 8;
		}
		if (products.size() == 0 && specialties.size() == 0) {
			new ControlTextCentered(this, y, I18N.localise("genetics.gui.analyst.fruit.noFruits")).setColor(getColor());
			y += 28;
		}
		new ControlTextCentered(this, y, I18N.localise("genetics.gui.analyst.fruit.possible")).setColor(getColor());
		y += 12;
		Collection<IAllele> fruitAlleles = Binnie.GENETICS.getChromosomeMap(Binnie.GENETICS.getTreeRoot()).get(EnumTreeChromosome.FRUITS);
		for (IFruitFamily fam : ind.getGenome().getPrimary().getSuitableFruit()) {
			Collection<ItemStack> stacks = new UniqueItemStackSet();
			for (IAllele a : fruitAlleles) {
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
							FruitProviderPod pod2 = (FruitProviderPod) ((IAlleleFruit) a).getProvider();
							Field field = FruitProviderPod.class.getDeclaredField("drops");
							field.setAccessible(true);
							Collections.addAll(stacks, ((Map<ItemStack, Float>) field.get(pod2)).keySet().toArray(new ItemStack[0]));
						}
					} catch (Exception ex) {
						throw Throwables.propagate(ex);
					}
				}
			}
			y = getRefined(TextFormatting.ITALIC + fam.getName(), y, stacks);
			y += 2;
		}
		setSize(new Point(width(), y + 8));
	}

	@Override
	public String getTitle() {
		return I18N.localise("genetics.gui.analyst.fruit.title");
	}
}
