package binnie.genetics.gui;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.geometry.CraftGUIUtil;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.minecraft.control.ControlItemDisplay;
import binnie.core.util.I18N;
import binnie.core.util.UniqueItemStackSet;
import binnie.extratrees.craftgui.ControlFluidDisplay;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import forestry.plugins.PluginApiculture;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

public class AnalystPageProducts extends AnalystPageProduce {
    public AnalystPageProducts(IWidget parent, IArea area, IBee ind) {
        super(parent, area);
        setColor(0xcc3300);
        IBeeGenome genome = ind.getGenome();
        float speed = genome.getSpeed();
        float modeSpeed = Binnie.Genetics.getBeeRoot()
                .getBeekeepingMode(BinnieCore.proxy.getWorld())
                .getBeeModifier()
                .getProductionModifier(genome, 1.0f);
        int y = 4;
        new ControlTextCentered(this, y, EnumChatFormatting.UNDERLINE + getTitle()).setColor(getColor());

        y += 12;
        String alleleName = Binnie.Genetics.beeBreedingSystem.getAlleleName(
                EnumBeeChromosome.SPEED, ind.getGenome().getActiveAllele(EnumBeeChromosome.SPEED));
        new ControlTextCentered(
                        this,
                        y,
                        EnumChatFormatting.ITALIC + I18N.localise("genetics.gui.analyst.produce.rate", alleleName))
                .setColor(getColor());

        y += 20;
        Collection<ItemStack> refinedProducts = new UniqueItemStackSet();
        Collection<ItemStack> productList = new UniqueItemStackSet();
        Map<ItemStack, Float> products = new HashMap<>();
        products.putAll(genome.getPrimary().getProductChances());
        products.putAll(genome.getSecondary().getProductChances());
        if (!products.isEmpty()) {
            new ControlTextCentered(this, y, I18N.localise("genetics.gui.analyst.produce.natural"))
                    .setColor(getColor());

            y += 12;
            for (Map.Entry<ItemStack, Float> entry : products.entrySet()) {
                if (!productList.add(entry.getKey())) {
                    continue;
                }
                refinedProducts.addAll(getAllProducts(entry.getKey()));
                createProductEntry(entry.getKey(), entry.getValue(), y, speed * modeSpeed);
                y += 18;
            }
            y += 12;
        }

        products = genome.getPrimary().getSpecialtyChances();
        if (!products.isEmpty()) {
            new ControlTextCentered(this, y, I18N.localise("genetics.gui.analyst.produce.speciality"))
                    .setColor(getColor());
            y += 12;
            for (Map.Entry<ItemStack, Float> entry : products.entrySet()) {
                refinedProducts.addAll(getAllProducts(entry.getKey()));
                createProductEntry(entry.getKey(), entry.getValue(), y, speed * modeSpeed);
                y += 18;
            }
            y += 12;
        }

        new ControlTextCentered(this, y, I18N.localise("genetics.gui.analyst.produce.refined")).setColor(getColor());

        y += 12;
        Collection<ItemStack> level2Products = new UniqueItemStackSet();
        for (ItemStack stack : refinedProducts) {
            level2Products.addAll(getAllProducts(stack));
        }

        refinedProducts.addAll(level2Products);
        level2Products = new UniqueItemStackSet();
        for (ItemStack stack : refinedProducts) {
            level2Products.addAll(getAllProducts(stack));
        }

        refinedProducts.addAll(level2Products);
        Collection<FluidStack> allFluids = new ArrayList<>();
        for (ItemStack stack2 : refinedProducts) {
            for (FluidStack addition : getAllFluids(stack2)) {
                boolean alreadyIn = false;
                for (FluidStack existing : allFluids) {
                    if (existing.isFluidEqual(addition)) {
                        alreadyIn = true;
                    }
                }
                if (!alreadyIn) {
                    allFluids.add(addition);
                }
            }
        }

        int maxBiomePerLine = (int) ((w() + 2.0f - 16.0f) / 18.0f);
        float biomeListX =
                (w() - (Math.min(maxBiomePerLine, allFluids.size() + refinedProducts.size()) * 18 - 2)) / 2.0f;
        int dx = 0;
        int dy = 0;
        for (ItemStack soilStack : refinedProducts) {
            if (dx >= 18 * maxBiomePerLine) {
                dx = 0;
                dy += 18;
            }
            ControlItemDisplay display = new ControlItemDisplay(this, biomeListX + dx, y + dy);
            display.setItemStack(soilStack);
            display.setTooltip();
            dx += 18;
        }

        for (FluidStack soilStack2 : allFluids) {
            if (dx >= 18 * maxBiomePerLine) {
                dx = 0;
                dy += 18;
            }

            ItemStack container = null;
            for (FluidContainerRegistry.FluidContainerData data :
                    FluidContainerRegistry.getRegisteredFluidContainerData()) {
                if (data.emptyContainer.isItemEqual(new ItemStack(Items.glass_bottle))
                        && data.fluid.isFluidEqual(soilStack2)) {
                    container = data.filledContainer;
                    break;
                }

                if (data.emptyContainer.isItemEqual(new ItemStack(Items.bucket))
                        && data.fluid.isFluidEqual(soilStack2)) {
                    container = data.filledContainer;
                    break;
                }

                if (data.fluid.isFluidEqual(soilStack2)) {
                    container = data.filledContainer;
                    break;
                }
            }

            if (container == null) {
                ControlFluidDisplay display2 = new ControlFluidDisplay(this, biomeListX + dx, y + dy);
                display2.setItemStack(soilStack2);
                display2.setTooltip();
            } else {
                ControlItemDisplay display3 = new ControlItemDisplay(this, biomeListX + dx, y + dy);
                display3.setItemStack(container);
                display3.setTooltip();
            }
            dx += 18;
        }
        setSize(new IPoint(w(), y + dy + 18 + 8));
    }

    private void createProductEntry(ItemStack key, Float value, int y, float speed) {
        ControlItemDisplay item = new ControlItemDisplay(this, 16.0f, y) {
            @Override
            public void getTooltip(Tooltip tooltip) {
                super.getTooltip(tooltip);
                Collection<ItemStack> products = getCentrifuge(key);
                if (!products.isEmpty()) {
                    tooltip.add(I18N.localise("genetics.gui.analyst.produce.centrifuge") + " ");
                    for (ItemStack prod : products) {
                        NBTTagCompound nbt = new NBTTagCompound();
                        prod.writeToNBT(nbt);
                        tooltip.add(prod, prod.getDisplayName());
                    }
                }

                Collection<ItemStack> liquids = getSqueezer(key);
                if (!liquids.isEmpty()) {
                    tooltip.add(I18N.localise("genetics.gui.analyst.produce.squeezes") + " ");
                    for (ItemStack prod2 : liquids) {
                        NBTTagCompound nbt2 = new NBTTagCompound();
                        prod2.writeToNBT(nbt2);
                        tooltip.add(prod2, prod2.getDisplayName());
                    }
                }
            }
        };

        item.setTooltip();
        ControlText textWidget = new ControlTextCentered(this, y + 4, "");
        textWidget.setColor(getColor());
        CraftGUIUtil.moveWidget(textWidget, new IPoint(12.0f, 0.0f));
        item.setItemStack(key);
        float time = (int) (PluginApiculture.ticksPerBeeWorkCycle / (speed * value));
        textWidget.setValue(I18N.localise("genetics.gui.analyst.produce.everyTime", getTimeString(time)));
    }

    @Override
    public String getTitle() {
        return I18N.localise("genetics.gui.analyst.produce");
    }
}
