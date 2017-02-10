package binnie.genetics.gui;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.util.UniqueItemStackSet;
import binnie.craftgui.controls.ControlText;
import binnie.craftgui.controls.ControlTextCentered;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.Tooltip;
import binnie.craftgui.core.geometry.CraftGUIUtil;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.minecraft.control.ControlItemDisplay;
import binnie.extratrees.kitchen.craftgui.ControlFluidDisplay;
import binnie.genetics.Genetics;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import forestry.apiculture.PluginApiculture;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AnalystPageProducts extends AnalystPageProduce {
	public AnalystPageProducts(final IWidget parent, final IArea area, final IBee ind) {
		super(parent, area);
		this.setColour(13382400);
		final IBeeGenome genome = ind.getGenome();
		final float speed = genome.getSpeed();
		final float modeSpeed = Binnie.GENETICS.getBeeRoot().getBeekeepingMode(BinnieCore.getBinnieProxy().getWorld()).getBeeModifier().getProductionModifier(genome, 1.0f);
		int y = 4;
		new ControlTextCentered(this, y, TextFormatting.UNDERLINE + getTitle()).setColour(this.getColour());
		y += 12;
		new ControlTextCentered(this, y, TextFormatting.ITALIC + Genetics.proxy.localise("gui.analyst.produces.rate") + ": " + Binnie.GENETICS.beeBreedingSystem.getAlleleName(EnumBeeChromosome.SPEED, ind.getGenome().getActiveAllele(EnumBeeChromosome.SPEED))).setColour(this.getColour());
		y += 20;
		final Collection<ItemStack> refinedProducts = new UniqueItemStackSet();
		final Collection<ItemStack> productList = new UniqueItemStackSet();
		final Collection<ItemStack> specialtyList = new UniqueItemStackSet();
		Map<ItemStack, Float> products = new HashMap<>();
		products.putAll(genome.getPrimary().getProductChances());
		products.putAll(genome.getSecondary().getProductChances());
		if (!products.isEmpty()) {
			new ControlTextCentered(this, y, Genetics.proxy.localise("gui.analyst.products.natural")).setColour(this.getColour());
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
			new ControlTextCentered(this, y, Genetics.proxy.localise("gui.analyst.products.specialty")).setColour(this.getColour());
			y += 12;
			for (final Map.Entry<ItemStack, Float> entry : products.entrySet()) {
				refinedProducts.addAll(this.getAllProducts(entry.getKey()));
				this.createProductEntry(entry.getKey(), entry.getValue(), y, speed * modeSpeed);
				y += 18;
			}
			y += 12;
		}
		new ControlTextCentered(this, y, Genetics.proxy.localise("gui.analyst.products.refined")).setColour(this.getColour());
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
		final Collection<FluidStack> allFluids = new ArrayList<>();
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
		final int biomeListX = (this.w() - (Math.min(maxBiomePerLine, allFluids.size() + refinedProducts.size()) * 18 - 2)) / 2;
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
			ItemStack container = getContainer(soilStack2);
			if (container == null) {
				final ControlFluidDisplay display2 = new ControlFluidDisplay(this, biomeListX + dx, y + dy);
				display2.setFluidStack(soilStack2);
				display2.setTooltip();
			} else {
				final ControlItemDisplay display3 = new ControlItemDisplay(this, biomeListX + dx, y + dy);
				display3.setItemStack(container);
				display3.setTooltip();
			}
			dx += 18;
		}
		this.setSize(new IPoint(this.w(), y + dy + 18 + 8));
	}

	@Nullable
	public static ItemStack getContainer(final FluidStack fluidStack) {
		ItemStack[] containers = {
				new ItemStack(Items.GLASS_BOTTLE),
				new ItemStack(Items.BUCKET)
		};

		for (ItemStack container : containers) {
			// TODO 1.11 update for IFluidHandlerItem
			IFluidHandler fluidHandler = FluidUtil.getFluidHandler(container);
			if (fluidHandler != null && fluidHandler.fill(fluidStack, true) > 0) {
				return container;
			}
		}

		return null;
	}

	private void createProductEntry(final ItemStack key, final Float value, final int y, final float speed) {
		final ControlItemDisplay item = new ControlItemDisplay(this, 16, y) {
			@Override
			@SideOnly(Side.CLIENT)
			public void getTooltip(final Tooltip tooltip) {
				super.getTooltip(tooltip);
				final Collection<ItemStack> products = AnalystPageProducts.this.getCentrifuge(key);
				if (!products.isEmpty()) {
					tooltip.add(Genetics.proxy.localise("gui.analyst.products.centrifuges") + ": ");
					for (final ItemStack prod : products) {
						final NBTTagCompound nbt = new NBTTagCompound();
						prod.writeToNBT(nbt);
						tooltip.add(prod, prod.getDisplayName());
					}
				}
				final Collection<ItemStack> liquids = AnalystPageProducts.this.getSqueezer(key);
				if (!liquids.isEmpty()) {
					tooltip.add(Genetics.proxy.localise("gui.analyst.products.squeezes") + ": ");
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
		textWidget.setColour(this.getColour());
		CraftGUIUtil.moveWidget(textWidget, new IPoint(12, 0));
		item.setItemStack(key);
		final int time = (int) (PluginApiculture.ticksPerBeeWorkCycle * 100.0 / (speed * value));
		textWidget.setValue(Genetics.proxy.localise("gui.analyst.products.every") + " " + this.getTimeString(time));
	}

	@Override
	public String getTitle() {
		return Genetics.proxy.localise("gui.analyst.produces.tile");
	}
}
