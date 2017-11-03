package binnie.extrabees.genetics.gui.analyst;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;

import net.minecraftforge.fluids.FluidStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import forestry.apiculture.ModuleApiculture;

import binnie.core.BinnieCore;
import binnie.core.api.gui.IArea;
import binnie.core.api.gui.ITitledWidget;
import binnie.core.api.gui.IWidget;
import binnie.core.gui.Tooltip;
import binnie.core.gui.controls.ControlFluidDisplay;
import binnie.core.gui.controls.ControlText;
import binnie.core.gui.controls.ControlTextCentered;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.geometry.CraftGUIUtil;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.minecraft.control.ControlItemDisplay;
import binnie.core.util.FluidStackUtil;
import binnie.core.util.ForestryRecipeUtil;
import binnie.core.util.I18N;
import binnie.core.util.TimeUtil;
import binnie.core.util.UniqueItemStackSet;
import binnie.extrabees.ExtraBees;
import binnie.genetics.api.analyst.AnalystConstants;
import binnie.genetics.api.analyst.IAnalystManager;

public class AnalystPageBeeProducts extends Control implements ITitledWidget {
	public AnalystPageBeeProducts(IWidget parent, IArea area, IBee ind, IAnalystManager analystManager) {
		super(parent, area);
		setColor(0xcc3300);
		IBeeGenome genome = ind.getGenome();
		float speed = genome.getSpeed();
		float modeSpeed = BeeManager.beeRoot.getBeekeepingMode(BinnieCore.getBinnieProxy().getWorld()).getBeeModifier().getProductionModifier(genome, 1.0f);

		int y = 4;
		new ControlTextCentered(this, y, TextFormatting.UNDERLINE + getTitle()).setColor(getColor());

		y += 12;
		new ControlTextCentered(this, y, TextFormatting.ITALIC + I18N.localise(AnalystConstants.PRODUCTS_KEY + ".rate") + ": " + ExtraBees.beeBreedingSystem.getAlleleName(EnumBeeChromosome.SPEED, ind.getGenome().getActiveAllele(EnumBeeChromosome.SPEED))).setColor(getColor());

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
				refinedProducts.addAll(analystManager.getAllProducts(entry.getKey()));
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
				refinedProducts.addAll(analystManager.getAllProducts(entry.getKey()));
				createProductEntry(entry.getKey(), entry.getValue(), y, speed * modeSpeed);
				y += 18;
			}
			y += 12;
		}

		new ControlTextCentered(this, y, I18N.localise(AnalystConstants.PRODUCTS_KEY + ".refined")).setColor(getColor());

		y += 12;
		Collection<ItemStack> level2Products = new UniqueItemStackSet();
		for (ItemStack stack : refinedProducts) {
			level2Products.addAll(analystManager.getAllProducts(stack));
		}

		refinedProducts.addAll(level2Products);
		level2Products = new UniqueItemStackSet();
		for (ItemStack stack : refinedProducts) {
			level2Products.addAll(analystManager.getAllProducts(stack));
		}

		refinedProducts.addAll(level2Products);
		NonNullList<FluidStack> allFluids = analystManager.getAllFluidsFromItems(refinedProducts);

		int maxBiomePerLine = (int) ((getWidth() + 2.0f - 16.0f) / 18.0f);
		int biomeListX = (getWidth() - (Math.min(maxBiomePerLine, allFluids.size() + refinedProducts.size()) * 18 - 2)) / 2;
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
			ItemStack container = FluidStackUtil.getContainer(fluidStack);
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
		setSize(new Point(getWidth(), y + dy + 18 + 8));
	}

	private void createProductEntry(ItemStack key, Float value, int y, float speed) {
		ControlItemDisplay item = new BeeProductItem(this, y, key);

		item.setTooltip();
		ControlText textWidget = new ControlTextCentered(this, y + 4, "");
		textWidget.setColor(getColor());
		CraftGUIUtil.moveWidget(textWidget, new Point(12, 0));
		item.setItemStack(key);
		int time = (int) (ModuleApiculture.ticksPerBeeWorkCycle * 100.0 / (speed * value));
		textWidget.setValue(I18N.localise(AnalystConstants.PRODUCTS_KEY + ".every") + " " + TimeUtil.getTimeString(time));
	}

	@Override
	public String getTitle() {
		return I18N.localise(AnalystConstants.PRODUCTS_KEY + ".title");
	}

	private static class BeeProductItem extends ControlItemDisplay {
		private final ItemStack key;

		public BeeProductItem(AnalystPageBeeProducts analystPageBeeProducts, int y, ItemStack key) {
			super(analystPageBeeProducts, 16, y);
			this.key = key;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void getTooltip(Tooltip tooltip, ITooltipFlag tooltipFlag) {
			super.getTooltip(tooltip, tooltipFlag);
			NonNullList<ItemStack> centrifugeProducts = NonNullList.create();
			ForestryRecipeUtil.getCentrifugeOutputs(key, centrifugeProducts);
			if (!centrifugeProducts.isEmpty()) {
				tooltip.add(I18N.localise(AnalystConstants.PRODUCTS_KEY + ".centrifuges") + ": ");
				for (ItemStack prod : centrifugeProducts) {
					NBTTagCompound nbt = new NBTTagCompound();
					prod.writeToNBT(nbt);
					tooltip.add(prod, prod.getDisplayName());
				}
			}
			NonNullList<ItemStack> squeezerProducts = NonNullList.create();
			ForestryRecipeUtil.getSqueezerOutputs(key, squeezerProducts);
			if (!squeezerProducts.isEmpty()) {
				tooltip.add(I18N.localise(AnalystConstants.PRODUCTS_KEY + ".squeezes") + ": ");
				for (ItemStack prod2 : squeezerProducts) {
					NBTTagCompound nbt2 = new NBTTagCompound();
					prod2.writeToNBT(nbt2);
					tooltip.add(prod2, prod2.getDisplayName());
				}
			}
		}
	}
}
