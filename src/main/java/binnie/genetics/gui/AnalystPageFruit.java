package binnie.genetics.gui;

import binnie.Binnie;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.minecraft.control.ControlItemDisplay;
import binnie.core.util.UniqueItemStackSet;
import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.arboriculture.IAlleleFruit;
import forestry.api.arboriculture.ITree;
import forestry.api.arboriculture.ITreeGenome;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IFruitFamily;
import forestry.arboriculture.FruitProviderPod;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import java.lang.reflect.Field;
import java.util.Collection;

public class AnalystPageFruit extends AnalystPageProduce {
	public AnalystPageFruit(IWidget parent, IArea area, ITree ind) {
		super(parent, area);
		setColor(0xcc3300);
		ITreeGenome genome = ind.getGenome();
		int y = 4;
		new ControlTextCentered(this, y, EnumChatFormatting.UNDERLINE + "Fruit").setColor(getColor());
		y += 12;
		new ControlTextCentered(this, y, EnumChatFormatting.ITALIC + "Yield: " + Binnie.Genetics.treeBreedingSystem.getAlleleName(EnumTreeChromosome.YIELD, ind.getGenome().getActiveAllele(EnumTreeChromosome.YIELD))).setColor(getColor());
		y += 20;
		Collection<ItemStack> products = new UniqueItemStackSet();
		Collection<ItemStack> specialties = new UniqueItemStackSet();
		Collection<ItemStack> wiid = new UniqueItemStackSet();
		for (ItemStack stack : ind.getProduceList()) {
			products.add(stack);
		}
		for (ItemStack stack : ind.getSpecialtyList()) {
			specialties.add(stack);
		}
		try {
			if (ind.getGenome().getFruitProvider() instanceof FruitProviderPod) {
				FruitProviderPod pod = (FruitProviderPod) ind.getGenome().getFruitProvider();
				Field f = FruitProviderPod.class.getDeclaredField("drop");
				f.setAccessible(true);
				for (ItemStack stack2 : (ItemStack[]) f.get(pod)) {
					products.add(stack2);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (products.size() > 0) {
			new ControlTextCentered(this, y, "Natural Fruit").setColor(getColor());
			y += 10;
			int w = products.size() * 18 - 2;
			int i = 0;
			for (ItemStack stack : products) {
				ControlItemDisplay d = new ControlItemDisplay(this, (w() - w) / 2.0f + 18 * i, y);
				d.setTooltip();
				d.setItemStack(stack);
			}
			y += 26;
		}
		if (specialties.size() > 0) {
			new ControlTextCentered(this, y, "Specialty Fruit").setColor(getColor());
			y += 10;
			int w = products.size() * 18 - 2;
			int i = 0;
			for (ItemStack stack : specialties) {
				ControlItemDisplay d = new ControlItemDisplay(this, (w() - w) / 2.0f + 18 * i, y);
				d.setTooltip();
				d.setItemStack(stack);
			}
			y += 26;
		}
		Collection<ItemStack> allProducts = new UniqueItemStackSet();
		for (ItemStack stack3 : products) {
			allProducts.add(stack3);
		}
		for (ItemStack stack3 : specialties) {
			allProducts.add(stack3);
		}
		Collection<ItemStack> refinedProducts = new UniqueItemStackSet();
		refinedProducts.addAll(getAllProductsAndFluids(allProducts));
		if (refinedProducts.size() > 0) {
			y = getRefined("Refined Products", y, refinedProducts);
			y += 8;
		}
		if (products.size() == 0 && specialties.size() == 0) {
			new ControlTextCentered(this, y, "This tree has no \nfruits or nuts").setColor(getColor());
			y += 28;
		}
		new ControlTextCentered(this, y, "Possible Fruits").setColor(getColor());
		y += 12;
		Collection<IAllele> fruitAlleles = Binnie.Genetics.getChromosomeMap(Binnie.Genetics.getTreeRoot()).get(EnumTreeChromosome.FRUITS);
		for (IFruitFamily fam : ind.getGenome().getPrimary().getSuitableFruit()) {
			Collection<ItemStack> stacks = new UniqueItemStackSet();
			for (IAllele a : fruitAlleles) {
				if (((IAlleleFruit) a).getProvider().getFamily() == fam) {
					for (ItemStack p : ((IAlleleFruit) a).getProvider().getProducts()) {
						stacks.add(p);
					}
					for (ItemStack p : ((IAlleleFruit) a).getProvider().getSpecialty()) {
						stacks.add(p);
					}
					try {
						if (a.getUID().contains("fruitCocoa")) {
							stacks.add(new ItemStack(Items.dye, 1, 3));
						} else {
							if (!(((IAlleleFruit) a).getProvider() instanceof FruitProviderPod)) {
								continue;
							}
							FruitProviderPod pod2 = (FruitProviderPod) ((IAlleleFruit) a).getProvider();
							Field field = FruitProviderPod.class.getDeclaredField("drop");
							field.setAccessible(true);
							for (ItemStack stack4 : (ItemStack[]) field.get(pod2)) {
								stacks.add(stack4);
							}
						}
					} catch (Exception ex) {
					}
				}
			}
			y = getRefined(EnumChatFormatting.ITALIC + fam.getName(), y, stacks);
			y += 2;
		}
		setSize(new IPoint(w(), y + 8));
	}

	@Override
	public String getTitle() {
		return "Fruit";
	}
}
