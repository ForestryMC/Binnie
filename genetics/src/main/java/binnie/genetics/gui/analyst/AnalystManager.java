package binnie.genetics.gui.analyst;

import binnie.core.api.gui.IArea;
import binnie.core.api.gui.ITitledWidget;
import binnie.core.api.gui.IWidget;
import binnie.core.gui.controls.ControlTextCentered;
import binnie.core.gui.minecraft.control.ControlItemDisplay;
import binnie.core.util.FluidStackUtil;
import binnie.core.util.UniqueFluidStackSet;
import binnie.core.util.UniqueItemStackSet;
import binnie.genetics.Genetics;
import binnie.genetics.api.analyst.IAnalystIcons;
import binnie.genetics.api.analyst.IAnalystManager;
import binnie.genetics.api.analyst.IAnalystPagePlugin;
import binnie.genetics.api.analyst.IBehaviourPlugin;
import binnie.genetics.api.analyst.IBiologyPlugin;
import binnie.genetics.api.analyst.IClimatePlugin;
import binnie.genetics.api.analyst.IProducePlugin;
import binnie.genetics.gui.analyst.tree.AnalystPageClimate;
import forestry.api.genetics.IIndividual;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class AnalystManager implements IAnalystManager {
	private final Collection<IAnalystPagePlugin> analystPagePlugins = new ArrayList<>();
	private final Collection<IProducePlugin> producePlugins = new ArrayList<>();

	@Override
	public void registerAnalystPagePlugin(IAnalystPagePlugin<?> analystPageFactory) {
		analystPagePlugins.add(analystPageFactory);
	}

	@Override
	@Nullable
	public <T extends IIndividual> IAnalystPagePlugin<T> getAnalystPagePlugin(T individual) {
		for (IAnalystPagePlugin plugin : analystPagePlugins) {
			if (plugin.handles(individual)) {
				//noinspection unchecked
				return plugin;
			}
		}
		return null;
	}

	@Override
	public void registerProducePlugin(IProducePlugin producePlugin) {
		producePlugins.add(producePlugin);
	}

	@Override
	public Collection<IProducePlugin> getProducePlugins() {
		return Collections.unmodifiableCollection(producePlugins);
	}

	@Override
	public NonNullList<ItemStack> getAllProducts(ItemStack key) {
		NonNullList<ItemStack> products = NonNullList.create();
		for (IProducePlugin plugin : getProducePlugins()) {
			plugin.getItems(key, products);
		}
		return products;
	}

	@Override
	public NonNullList<FluidStack> getAllFluidsFromItems(Collection<ItemStack> itemStacks) {
		NonNullList<FluidStack> allFluids = NonNullList.create();
		for (ItemStack itemStack : itemStacks) {
			for (IProducePlugin producePlugin : getProducePlugins()) {
				producePlugin.getFluids(itemStack, allFluids);
			}
		}
		return FluidStackUtil.removeEqualFluids(allFluids);
	}

	@Override
	public NonNullList<FluidStack> getAllFluidsFromFluids(Collection<FluidStack> fluidStacks) {
		NonNullList<FluidStack> allFluids = NonNullList.create();
		for (FluidStack itemStack : fluidStacks) {
			for (IProducePlugin producePlugin : Genetics.getAnalystManager().getProducePlugins()) {
				producePlugin.getFluids(itemStack, allFluids);
			}
		}
		return FluidStackUtil.removeEqualFluids(allFluids);
	}

	@Override
	public Collection<ItemStack> getAllProductsAndFluids(Collection<ItemStack> collection) {
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

	@Override
	public IAnalystIcons getIcons() {
		return Genetics.getIcons();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public <T extends IIndividual> ITitledWidget createClimatePage(IWidget parent, IArea area, T ind, IClimatePlugin<T> plugin) {
		return new AnalystPageClimate<>(parent, area, ind, plugin);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public <T extends IIndividual> ITitledWidget createBiologyPage(IWidget parent, IArea area, T ind, IBiologyPlugin<T> plugin) {
		return new AnalystPageBiology<>(parent, area, ind, plugin, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public <T extends IIndividual> ITitledWidget createBehaviorPage(IWidget parent, IArea area, T ind, IBehaviourPlugin<T> behaviourPlugin) {
		return new AnalystPageBehaviour<>(parent, area, ind, behaviourPlugin);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int drawRefined(IWidget parent, String string, int y, Collection<ItemStack> products) {
		new ControlTextCentered(parent, y, string).setColor(parent.getColor());
		y += 10;
		int maxBiomePerLine = (parent.getWidth() + 2 - 16) / 18;
		int biomeListX = (parent.getWidth() - (Math.min(maxBiomePerLine, products.size()) * 18 - 2)) / 2;
		int dx = 0;
		int dy = 0;
		for (ItemStack soilStack : products) {
			if (dx >= 18 * maxBiomePerLine) {
				dx = 0;
				dy += 18;
			}
			FluidStack fluid = FluidUtil.getFluidContained(soilStack);
			soilStack.setCount(1);
			ControlItemDisplay display = new ControlItemDisplay(parent, biomeListX + dx, y + dy, soilStack, fluid == null);
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
