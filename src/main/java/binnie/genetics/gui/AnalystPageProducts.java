// 
// Decompiled by Procyon v0.5.30
// 

package binnie.genetics.gui;

import binnie.core.craftgui.controls.ControlText;
import forestry.plugins.PluginApiculture;
import binnie.core.craftgui.geometry.CraftGUIUtil;
import net.minecraft.nbt.NBTTagCompound;
import binnie.core.craftgui.Tooltip;
import forestry.api.apiculture.IBeeGenome;
import binnie.core.craftgui.geometry.IPoint;
import binnie.extratrees.craftgui.kitchen.ControlFluidDisplay;
import net.minecraft.init.Items;
import net.minecraftforge.fluids.FluidContainerRegistry;
import binnie.core.craftgui.minecraft.control.ControlItemDisplay;
import net.minecraftforge.fluids.FluidStack;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import net.minecraft.item.ItemStack;
import java.util.HashMap;
import binnie.core.util.UniqueItemStackSet;
import forestry.api.apiculture.EnumBeeChromosome;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.BinnieCore;
import binnie.Binnie;
import forestry.api.apiculture.IBee;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.IWidget;

public class AnalystPageProducts extends AnalystPageProduce
{
	public AnalystPageProducts(final IWidget parent, final IArea area, final IBee ind) {
		super(parent, area);
		this.setColor(13382400);
		final IBeeGenome genome = ind.getGenome();
		final float speed = genome.getSpeed();
		final float modeSpeed = Binnie.Genetics.getBeeRoot().getBeekeepingMode(BinnieCore.proxy.getWorld()).getBeeModifier().getProductionModifier(genome, 1.0f);
		int y = 4;
		new ControlTextCentered(this, y, "§nProduce").setColor(this.getColor());
		y += 12;
		new ControlTextCentered(this, y, "§oRate: " + Binnie.Genetics.beeBreedingSystem.getAlleleName(EnumBeeChromosome.SPEED, ind.getGenome().getActiveAllele(EnumBeeChromosome.SPEED))).setColor(this.getColor());
		y += 20;
		final Collection<ItemStack> refinedProducts = new UniqueItemStackSet();
		final Collection<ItemStack> productList = new UniqueItemStackSet();
		final Collection<ItemStack> specialtyList = new UniqueItemStackSet();
		Map<ItemStack, Float> products = new HashMap<ItemStack, Float>();
		products.putAll(genome.getPrimary().getProductChances());
		products.putAll(genome.getSecondary().getProductChances());
		if (!products.isEmpty()) {
			new ControlTextCentered(this, y, "Natural Products").setColor(this.getColor());
			y += 12;
			for (final Map.Entry<ItemStack, Float> entry : products.entrySet()) {
				if (!productList.add(entry.getKey())) {
					continue;
				}
				refinedProducts.addAll(this.getAllProducts(entry.getKey()));
				this.createProductEntry(entry.getKey(), entry.getValue(), y, speed * modeSpeed);
				y += 18;
			}
			y += 12;
		}
		products = genome.getPrimary().getSpecialtyChances();
		if (!products.isEmpty()) {
			new ControlTextCentered(this, y, "Specialty Products").setColor(this.getColor());
			y += 12;
			for (final Map.Entry<ItemStack, Float> entry : products.entrySet()) {
				refinedProducts.addAll(this.getAllProducts(entry.getKey()));
				this.createProductEntry(entry.getKey(), entry.getValue(), y, speed * modeSpeed);
				y += 18;
			}
			y += 12;
		}
		new ControlTextCentered(this, y, "Refined Products").setColor(this.getColor());
		y += 12;
		Collection<ItemStack> level2Products = new UniqueItemStackSet();
		for (final ItemStack stack : refinedProducts) {
			level2Products.addAll(this.getAllProducts(stack));
		}
		refinedProducts.addAll(level2Products);
		level2Products = new UniqueItemStackSet();
		for (final ItemStack stack : refinedProducts) {
			level2Products.addAll(this.getAllProducts(stack));
		}
		refinedProducts.addAll(level2Products);
		final Collection<FluidStack> allFluids = new ArrayList<FluidStack>();
		for (final ItemStack stack2 : refinedProducts) {
			for (final FluidStack addition : this.getAllFluids(stack2)) {
				boolean alreadyIn = false;
				for (final FluidStack existing : allFluids) {
					if (existing.isFluidEqual(addition)) {
						alreadyIn = true;
					}
				}
				if (!alreadyIn) {
					allFluids.add(addition);
				}
			}
		}
		final int maxBiomePerLine = (int) ((this.w() + 2.0f - 16.0f) / 18.0f);
		final float biomeListX = (this.w() - (Math.min(maxBiomePerLine, allFluids.size() + refinedProducts.size()) * 18 - 2)) / 2.0f;
		int dx = 0;
		int dy = 0;
		for (final ItemStack soilStack : refinedProducts) {
			if (dx >= 18 * maxBiomePerLine) {
				dx = 0;
				dy += 18;
			}
			final ControlItemDisplay display = new ControlItemDisplay(this, biomeListX + dx, y + dy);
			display.setItemStack(soilStack);
			display.setTooltip();
			dx += 18;
		}
		for (final FluidStack soilStack2 : allFluids) {
			if (dx >= 18 * maxBiomePerLine) {
				dx = 0;
				dy += 18;
			}
			ItemStack container = null;
			for (final FluidContainerRegistry.FluidContainerData data : FluidContainerRegistry.getRegisteredFluidContainerData()) {
				if (data.emptyContainer.isItemEqual(new ItemStack(Items.glass_bottle)) && data.fluid.isFluidEqual(soilStack2)) {
					container = data.filledContainer;
					break;
				}
				if (data.emptyContainer.isItemEqual(new ItemStack(Items.bucket)) && data.fluid.isFluidEqual(soilStack2)) {
					container = data.filledContainer;
					break;
				}
				if (data.fluid.isFluidEqual(soilStack2)) {
					container = data.filledContainer;
					break;
				}
			}
			if (container == null) {
				final ControlFluidDisplay display2 = new ControlFluidDisplay(this, biomeListX + dx, y + dy);
				display2.setItemStack(soilStack2);
				display2.setTooltip();
			}
			else {
				final ControlItemDisplay display3 = new ControlItemDisplay(this, biomeListX + dx, y + dy);
				display3.setItemStack(container);
				display3.setTooltip();
			}
			dx += 18;
		}
		this.setSize(new IPoint(this.w(), y + dy + 18 + 8));
	}

	private void createProductEntry(final ItemStack key, final Float value, final int y, final float speed) {
		final ControlItemDisplay item = new ControlItemDisplay(this, 16.0f, y) {
			@Override
			public void getTooltip(final Tooltip tooltip) {
				super.getTooltip(tooltip);
				final Collection<ItemStack> products = AnalystPageProducts.this.getCentrifuge(key);
				if (!products.isEmpty()) {
					tooltip.add("Centrifuges to give: ");
					for (final ItemStack prod : products) {
						final NBTTagCompound nbt = new NBTTagCompound();
						prod.writeToNBT(nbt);
						tooltip.add(prod, prod.getDisplayName());
					}
				}
				final Collection<ItemStack> liquids = AnalystPageProducts.this.getSqueezer(key);
				if (!liquids.isEmpty()) {
					tooltip.add("Squeezes to give: ");
					for (final ItemStack prod2 : liquids) {
						final NBTTagCompound nbt2 = new NBTTagCompound();
						prod2.writeToNBT(nbt2);
						tooltip.add(prod2, prod2.getDisplayName());
					}
				}
			}
		};
		item.setTooltip();
		final ControlText textWidget = new ControlTextCentered(this, y + 4, "");
		textWidget.setColor(this.getColor());
		CraftGUIUtil.moveWidget(textWidget, new IPoint(12.0f, 0.0f));
		item.setItemStack(key);
		final float time = (int) (PluginApiculture.ticksPerBeeWorkCycle * 100.0 / (speed * value));
		textWidget.setValue("Every " + this.getTimeString(time));
	}

	@Override
	public String getTitle() {
		return "Produce";
	}
}
