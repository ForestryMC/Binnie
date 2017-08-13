package binnie.genetics.gui.analyst;

import java.util.Collection;

import binnie.core.api.gui.IArea;
import binnie.core.util.FluidStackUtil;
import binnie.genetics.api.GeneticsApi;
import binnie.genetics.api.IProducePlugin;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;

import binnie.core.api.gui.IWidget;
import binnie.core.gui.controls.ControlTextCentered;
import binnie.core.gui.minecraft.control.ControlItemDisplay;
import binnie.core.util.UniqueFluidStackSet;
import binnie.core.util.UniqueItemStackSet;

public abstract class AnalystPageProduce extends ControlAnalystPage {
	public AnalystPageProduce(IWidget parent, IArea area) {
		super(parent, area);
	}

	protected NonNullList<ItemStack> getAllProducts(ItemStack key) {
		NonNullList<ItemStack> products = NonNullList.create();
		for (IProducePlugin plugin : GeneticsApi.getProducePlugins()) {
			plugin.getItems(key, products);
		}
		return products;
	}

	public NonNullList<FluidStack> getAllFluidsFromItems(Collection<ItemStack> itemStacks) {
		NonNullList<FluidStack> allFluids = NonNullList.create();
		for (ItemStack itemStack : itemStacks) {
			for (IProducePlugin producePlugin : GeneticsApi.getProducePlugins()) {
				producePlugin.getFluids(itemStack, allFluids);
			}
		}
		return FluidStackUtil.removeEqualFluids(allFluids);
	}

	public NonNullList<FluidStack> getAllFluidsFromFluids(Collection<FluidStack> fluidStacks) {
		NonNullList<FluidStack> allFluids = NonNullList.create();
		for (FluidStack itemStack : fluidStacks) {
			for (IProducePlugin producePlugin : GeneticsApi.getProducePlugins()) {
				producePlugin.getFluids(itemStack, allFluids);
			}
		}
		return FluidStackUtil.removeEqualFluids(allFluids);
	}

	protected Collection<ItemStack> getAllProductsAndFluids(Collection<ItemStack> collection) {
		Collection<ItemStack> products = new UniqueItemStackSet();
		for (ItemStack stack : collection) {
			products.addAll(getAllProducts(stack));
		}
		Collection<ItemStack> products2 = new UniqueItemStackSet();
		for (ItemStack stack2 : products) {
			products2.addAll(getAllProducts(stack2));
		}
		Collection<ItemStack> products3 = new UniqueItemStackSet();
		for (ItemStack stack3 : products2) {
			products3.addAll(getAllProducts(stack3));
		}
		products.addAll(products2);
		products.addAll(products3);
		Collection<FluidStack> allFluids = new UniqueFluidStackSet();
		allFluids.addAll(getAllFluidsFromItems(collection));

		Collection<FluidStack> fluids2 = new UniqueFluidStackSet();
		fluids2.addAll(getAllFluidsFromFluids(allFluids));

		Collection<FluidStack> fluids3 = new UniqueFluidStackSet();
		fluids3.addAll(getAllFluidsFromFluids(fluids2));

		allFluids.addAll(fluids2);
		allFluids.addAll(fluids3);
		for (FluidStack fluid : allFluids) {
			ItemStack container = FluidStackUtil.getContainer(fluid);
			if (container != null) {
				products.add(container);
			}
		}
		return products;
	}

	protected int getRefined(String string, int y, Collection<ItemStack> products) {
		new ControlTextCentered(this, y, string).setColor(getColor());
		y += 10;
		int maxBiomePerLine = (getWidth() + 2 - 16) / 18;
		int biomeListX = (getWidth() - (Math.min(maxBiomePerLine, products.size()) * 18 - 2)) / 2;
		int dx = 0;
		int dy = 0;
		for (ItemStack soilStack : products) {
			if (dx >= 18 * maxBiomePerLine) {
				dx = 0;
				dy += 18;
			}
			FluidStack fluid = FluidUtil.getFluidContained(soilStack);
			soilStack.setCount(1);
			ControlItemDisplay display = new ControlItemDisplay(this, biomeListX + dx, y + dy, soilStack, fluid == null);
			if (fluid != null) {
				display.addTooltip(fluid.getLocalizedName());
			}
			dx += 18;
		}
		if (dx != 0) {
			dy += 18;
		}
		y += dy;
		return y;
	}
}
