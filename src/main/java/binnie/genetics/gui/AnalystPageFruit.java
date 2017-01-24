package binnie.genetics.gui;

import binnie.Binnie;
import binnie.core.util.UniqueItemStackSet;
import binnie.craftgui.controls.ControlTextCentered;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.minecraft.control.ControlItemDisplay;
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

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;

public class AnalystPageFruit extends AnalystPageProduce {
	public AnalystPageFruit(final IWidget parent, final IArea area, final ITree ind) {
		super(parent, area);
		this.setColour(13382400);
		final ITreeGenome genome = ind.getGenome();
		int y = 4;
		new ControlTextCentered(this, y, "§nFruit").setColour(this.getColour());
		y += 12;
		new ControlTextCentered(this, y, "§oYield: " + Binnie.Genetics.treeBreedingSystem.getAlleleName(EnumTreeChromosome.YIELD, ind.getGenome().getActiveAllele(EnumTreeChromosome.YIELD))).setColour(this.getColour());
		y += 20;
		final Collection<ItemStack> products = new UniqueItemStackSet();
		final Collection<ItemStack> specialties = new UniqueItemStackSet();
		final Collection<ItemStack> wiid = new UniqueItemStackSet();
		for (final ItemStack stack : ind.getProducts().keySet()) {
			products.add(stack);
		}
		for (final ItemStack stack : ind.getSpecialties().keySet()) {
			specialties.add(stack);
		}
		try {
			if (ind.getGenome().getFruitProvider() instanceof FruitProviderPod) {
				final FruitProviderPod pod = (FruitProviderPod) ind.getGenome().getFruitProvider();
				final Field f = FruitProviderPod.class.getDeclaredField("drop");
				f.setAccessible(true);
				Collections.addAll(products, (ItemStack[]) f.get(pod));
			}
		} catch (Exception e) {
			throw Throwables.propagate(e);
		}
		if (products.size() > 0) {
			new ControlTextCentered(this, y, "Natural Fruit").setColour(this.getColour());
			y += 10;
			final int w = products.size() * 18 - 2;
			final int i = 0;
			for (final ItemStack stack : products) {
				final ControlItemDisplay d = new ControlItemDisplay(this, (this.w() - w) / 2.0f + 18 * i, y);
				d.setTooltip();
				d.setItemStack(stack);
			}
			y += 26;
		}
		if (specialties.size() > 0) {
			new ControlTextCentered(this, y, "Specialty Fruit").setColour(this.getColour());
			y += 10;
			final int w = products.size() * 18 - 2;
			final int i = 0;
			for (final ItemStack stack : specialties) {
				final ControlItemDisplay d = new ControlItemDisplay(this, (this.w() - w) / 2.0f + 18 * i, y);
				d.setTooltip();
				d.setItemStack(stack);
			}
			y += 26;
		}
		final Collection<ItemStack> allProducts = new UniqueItemStackSet();
		for (final ItemStack stack3 : products) {
			allProducts.add(stack3);
		}
		for (final ItemStack stack3 : specialties) {
			allProducts.add(stack3);
		}
		final Collection<ItemStack> refinedProducts = new UniqueItemStackSet();
		refinedProducts.addAll(this.getAllProductsAndFluids(allProducts));
		if (refinedProducts.size() > 0) {
			y = this.getRefined("Refined Products", y, refinedProducts);
			y += 8;
		}
		if (products.size() == 0 && specialties.size() == 0) {
			new ControlTextCentered(this, y, "This tree has no \nfruits or nuts").setColour(this.getColour());
			y += 28;
		}
		new ControlTextCentered(this, y, "Possible Fruits").setColour(this.getColour());
		y += 12;
		final Collection<IAllele> fruitAlleles = Binnie.Genetics.getChromosomeMap(Binnie.Genetics.getTreeRoot()).get(EnumTreeChromosome.FRUITS);
		for (final IFruitFamily fam : ind.getGenome().getPrimary().getSuitableFruit()) {
			final Collection<ItemStack> stacks = new UniqueItemStackSet();
			for (final IAllele a : fruitAlleles) {
				if (((IAlleleFruit) a).getProvider().getFamily() == fam) {
					for (final ItemStack p : ((IAlleleFruit) a).getProvider().getProducts().keySet()) {
						stacks.add(p);
					}
					for (final ItemStack p : ((IAlleleFruit) a).getProvider().getSpecialty().keySet()) {
						stacks.add(p);
					}
					try {
						if (a.getUID().contains("fruitCocoa")) {
							stacks.add(new ItemStack(Items.DYE, 1, 3));
						} else {
							if (!(((IAlleleFruit) a).getProvider() instanceof FruitProviderPod)) {
								continue;
							}
							final FruitProviderPod pod2 = (FruitProviderPod) ((IAlleleFruit) a).getProvider();
							final Field field = FruitProviderPod.class.getDeclaredField("drop");
							field.setAccessible(true);
							Collections.addAll(stacks, (ItemStack[]) field.get(pod2));
						}
					} catch (Exception ex) {
						throw Throwables.propagate(ex);
					}
				}
			}
			y = this.getRefined("§o" + fam.getName(), y, stacks);
			y += 2;
		}
		this.setSize(new IPoint(this.w(), y + 8));
	}

	@Override
	public String getTitle() {
		return "Fruit";
	}
}
