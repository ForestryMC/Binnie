package binnie.genetics.gui;

import binnie.Binnie;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.minecraft.control.ControlItemDisplay;
import binnie.core.util.I18N;
import binnie.core.util.UniqueItemStackSet;
import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.arboriculture.IAlleleFruit;
import forestry.api.arboriculture.ITree;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IFruitFamily;
import forestry.arboriculture.FruitProviderPod;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class AnalystPageFruit extends AnalystPageProduce {
    public AnalystPageFruit(IWidget parent, IArea area, ITree ind) {
        super(parent, area);
        setColor(0xcc3300);
        int y = 4;
        new ControlTextCentered(this, y, EnumChatFormatting.UNDERLINE + getTitle()).setColor(getColor());

        y += 12;
        String alleleName = Binnie.Genetics.treeBreedingSystem.getAlleleName(
                EnumTreeChromosome.YIELD, ind.getGenome().getActiveAllele(EnumTreeChromosome.YIELD));
        new ControlTextCentered(
                        this,
                        y,
                        EnumChatFormatting.ITALIC + I18N.localise("genetics.gui.analyst.fruit.yield", alleleName))
                .setColor(getColor());

        y += 20;
        Collection<ItemStack> products = new UniqueItemStackSet();
        Collection<ItemStack> specialties = new UniqueItemStackSet();
        Collections.addAll(products, ind.getProduceList());
        Collections.addAll(specialties, ind.getSpecialtyList());

        try {
            if (ind.getGenome().getFruitProvider() instanceof FruitProviderPod) {
                FruitProviderPod pod = (FruitProviderPod) ind.getGenome().getFruitProvider();
                Field f = FruitProviderPod.class.getDeclaredField("drop");
                f.setAccessible(true);
                Collections.addAll(products, (ItemStack[]) f.get(pod));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (products.size() > 0) {
            new ControlTextCentered(this, y, I18N.localise("genetics.gui.analyst.fruit.natural")).setColor(getColor());

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
            new ControlTextCentered(this, y, I18N.localise("genetics.gui.analyst.fruit.speciality"))
                    .setColor(getColor());

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
        allProducts.addAll(products);
        allProducts.addAll(specialties);

        Collection<ItemStack> refinedProducts = new UniqueItemStackSet();
        refinedProducts.addAll(getAllProductsAndFluids(allProducts));
        if (refinedProducts.size() > 0) {
            y = getRefined(I18N.localise("genetics.gui.analyst.fruit.refined"), y, refinedProducts);
            y += 8;
        }

        if (products.size() == 0 && specialties.size() == 0) {
            new ControlTextCentered(this, y, I18N.localise("genetics.gui.analyst.fruit.none")).setColor(getColor());
            y += 28;
        }

        new ControlTextCentered(this, y, I18N.localise("genetics.gui.analyst.fruit.possible")).setColor(getColor());

        y += 12;
        Collection<IAllele> fruitAlleles =
                Binnie.Genetics.getChromosomeMap(Binnie.Genetics.getTreeRoot()).get(EnumTreeChromosome.FRUITS);
        for (IFruitFamily fam : ind.getGenome().getPrimary().getSuitableFruit()) {
            Collection<ItemStack> stacks = new UniqueItemStackSet();
            for (IAllele a : fruitAlleles) {
                if (((IAlleleFruit) a).getProvider().getFamily() == fam) {
                    Collections.addAll(stacks, ((IAlleleFruit) a).getProvider().getProducts());
                    Collections.addAll(stacks, ((IAlleleFruit) a).getProvider().getSpecialty());
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
                            Collections.addAll(stacks, (ItemStack[]) field.get(pod2));
                        }
                    } catch (Exception ex) {
                        // ignored
                    }
                }
            }
            y = getRefined(EnumChatFormatting.ITALIC + fam.getName(), y, stacks) + 2;
        }
        setSize(new IPoint(w(), y + 8));
    }

    @Override
    public String getTitle() {
        return I18N.localise("genetics.gui.analyst.fruit");
    }
}
