package binnie.genetics.gui.analyst.bee;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import forestry.apiculture.PluginApiculture;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.geometry.Area;
import binnie.core.craftgui.geometry.CraftGUIUtil;
import binnie.core.craftgui.geometry.Point;
import binnie.core.craftgui.minecraft.control.ControlItemDisplay;
import binnie.core.util.I18N;
import binnie.core.util.UniqueItemStackSet;
import binnie.extratrees.kitchen.craftgui.ControlFluidDisplay;
import binnie.genetics.gui.analyst.AnalystConstants;
import binnie.genetics.gui.analyst.AnalystPageProduce;

public class AnalystPageProducts extends AnalystPageProduce {
	public AnalystPageProducts(IWidget parent, Area area, IBee ind) {
		super(parent, area);
		setColor(0xcc3300);
		IBeeGenome genome = ind.getGenome();
		float speed = genome.getSpeed();
		float modeSpeed = Binnie.GENETICS.getBeeRoot().getBeekeepingMode(BinnieCore.getBinnieProxy().getWorld()).getBeeModifier().getProductionModifier(genome, 1.0f);

		int y = 4;
		new ControlTextCentered(this, y, TextFormatting.UNDERLINE + getTitle()).setColor(getColor());

		y += 12;
		new ControlTextCentered(this, y, TextFormatting.ITALIC + I18N.localise(AnalystConstants.PRODUCTS_KEY + ".rate") + ": " + Binnie.GENETICS.beeBreedingSystem.getAlleleName(EnumBeeChromosome.SPEED, ind.getGenome().getActiveAllele(EnumBeeChromosome.SPEED))).setColor(getColor());

		y += 20;
		Collection<ItemStack> refinedProducts = new UniqueItemStackSet();
		Collection<ItemStack> productList = new UniqueItemStackSet();
		Collection<ItemStack> specialtyList = new UniqueItemStackSet();
		Map<ItemStack, Float> products = new HashMap<>();
		products.putAll(genome.getPrimary().getProductChances());
		products.putAll(genome.getSecondary().getProductChances());

		if (!products.isEmpty()) {
			new ControlTextCentered(this, y, I18N.localise(AnalystConstants.PRODUCTS_KEY + ".natural")).setColor(getColor());
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
			new ControlTextCentered(this, y, I18N.localise(AnalystConstants.PRODUCTS_KEY + ".specialty")).setColor(getColor());
			y += 12;
			for (Map.Entry<ItemStack, Float> entry : products.entrySet()) {
				refinedProducts.addAll(getAllProducts(entry.getKey()));
				createProductEntry(entry.getKey(), entry.getValue(), y, speed * modeSpeed);
				y += 18;
			}
			y += 12;
		}

		new ControlTextCentered(this, y, I18N.localise(AnalystConstants.PRODUCTS_KEY + ".refined")).setColor(getColor());

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
		for (ItemStack itemStack : refinedProducts) {
			for (FluidStack addition : getAllFluids(itemStack)) {
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

		int maxBiomePerLine = (int) ((width() + 2.0f - 16.0f) / 18.0f);
		int biomeListX = (width() - (Math.min(maxBiomePerLine, allFluids.size() + refinedProducts.size()) * 18 - 2)) / 2;
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

		for (FluidStack fluidStack : allFluids) {
			if (dx >= 18 * maxBiomePerLine) {
				dx = 0;
				dy += 18;
			}
			ItemStack container = getContainer(fluidStack);
			if (container == null) {
				ControlFluidDisplay display2 = new ControlFluidDisplay(this, biomeListX + dx, y + dy);
				display2.setFluidStack(fluidStack);
				display2.setTooltip();
			} else {
				ControlItemDisplay display3 = new ControlItemDisplay(this, biomeListX + dx, y + dy);
				display3.setItemStack(container);
				display3.setTooltip();
			}
			dx += 18;
		}
		setSize(new Point(width(), y + dy + 18 + 8));
	}

	@Nullable
	public static ItemStack getContainer(FluidStack fluidStack) {
		ItemStack[] containers = {
			new ItemStack(Items.GLASS_BOTTLE),
			new ItemStack(Items.BUCKET)
		};

		for (ItemStack container : containers) {
			IFluidHandlerItem fluidHandler = FluidUtil.getFluidHandler(container);
			if (fluidHandler != null && fluidHandler.fill(fluidStack, true) > 0) {
				return fluidHandler.getContainer();
			}
		}
		return null;
	}

	private void createProductEntry(ItemStack key, Float value, int y, float speed) {
		ControlItemDisplay item = new ControlItemDisplay(this, 16, y) {
			@Override
			@SideOnly(Side.CLIENT)
			public void getTooltip(Tooltip tooltip) {
				super.getTooltip(tooltip);
				Collection<ItemStack> products = getCentrifuge(key);
				if (!products.isEmpty()) {
					tooltip.add(I18N.localise(AnalystConstants.PRODUCTS_KEY + ".centrifuges") + ": ");
					for (ItemStack prod : products) {
						NBTTagCompound nbt = new NBTTagCompound();
						prod.writeToNBT(nbt);
						tooltip.add(prod, prod.getDisplayName());
					}
				}
				Collection<ItemStack> liquids = getSqueezer(key);
				if (!liquids.isEmpty()) {
					tooltip.add(I18N.localise(AnalystConstants.PRODUCTS_KEY + ".squeezes") + ": ");
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
		CraftGUIUtil.moveWidget(textWidget, new Point(12, 0));
		item.setItemStack(key);
		int time = (int) (PluginApiculture.ticksPerBeeWorkCycle * 100.0 / (speed * value));
		textWidget.setValue(I18N.localise(AnalystConstants.PRODUCTS_KEY + ".every") + " " + getTimeString(time));
	}

	@Override
	public String getTitle() {
		return I18N.localise(AnalystConstants.PRODUCTS_KEY + ".title");
	}
}
