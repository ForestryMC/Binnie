package binnie.genetics.api.analyst;

import binnie.core.api.gui.IArea;
import binnie.core.api.gui.ITitledWidget;
import binnie.core.api.gui.IWidget;
import forestry.api.genetics.IIndividual;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collection;

public interface IAnalystManager {
	void registerAnalystPagePlugin(IAnalystPagePlugin<?> analystPageFactory);

	<T extends IIndividual> IAnalystPagePlugin<T> getAnalystPagePlugin(T individual);

	void registerProducePlugin(IProducePlugin producePlugin);

	Collection<IProducePlugin> getProducePlugins();

	NonNullList<ItemStack> getAllProducts(ItemStack key);

	NonNullList<FluidStack> getAllFluidsFromItems(Collection<ItemStack> itemStacks);

	NonNullList<FluidStack> getAllFluidsFromFluids(Collection<FluidStack> fluidStacks);

	Collection<ItemStack> getAllProductsAndFluids(Collection<ItemStack> collection);

	@SideOnly(Side.CLIENT)
	IAnalystIcons getIcons();

	@SideOnly(Side.CLIENT)
	<T extends IIndividual> ITitledWidget createClimatePage(IWidget parent, IArea area, T ind, IClimatePlugin<T> plugin);

	@SideOnly(Side.CLIENT)
	<T extends IIndividual> ITitledWidget createBiologyPage(IWidget parent, IArea area, T ind, IBiologyPlugin<T> plugin);

	@SideOnly(Side.CLIENT)
	<T extends IIndividual> ITitledWidget createBehaviorPage(IWidget parent, IArea area, T ind, IBehaviourPlugin<T> behaviourPlugin);

	@SideOnly(Side.CLIENT)
	int drawRefined(IWidget parent, String string, int y, Collection<ItemStack> products);
}
