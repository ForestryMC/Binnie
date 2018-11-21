package binnie.core.liquid;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;

import binnie.core.BinnieCore;
import binnie.core.Constants;
import binnie.core.features.FeatureProvider;
import binnie.core.features.IFeatureItem;
import binnie.core.features.IFeatureRegistry;
import binnie.core.modules.BinnieCoreModuleUIDs;
import binnie.core.util.I18N;

@FeatureProvider(containerId = Constants.CORE_MOD_ID, moduleID = BinnieCoreModuleUIDs.CORE)
public enum FluidContainerType {
	CAPSULE(false),
	REFRACTORY(false),
	CAN(false),
	GLASS(true),
	CYLINDER(true);

	private final IFeatureItem<Item> feature;

	FluidContainerType(boolean coreContainer) {
		String itemName = name().toLowerCase();
		IFeatureRegistry registry = BinnieCore.instance.registry(BinnieCoreModuleUIDs.CORE);
		if (coreContainer) {
			this.feature = registry.createItem(itemName, () -> new ItemFluidContainer(this));
		} else {
			this.feature = registry.modItem("forestry", itemName);
		}
	}

	public IFeatureItem<Item> getFeature() {
		return feature;
	}

	public String getIdentifier() {
		return feature.getIdentifier();
	}

	public String getDisplayName() {
		return I18N.localise("binniecore.item.container." + getIdentifier());
	}

	public ItemStack get(int amount) {
		return feature.stack(amount);
	}

	public ItemStack getEmpty() {
		return feature.stack();
	}

	public ItemStack getFilled(Fluid fluid) {
		ItemStack stack = getEmpty();
		stack = stack.copy();
		IFluidHandler fluidHandler = FluidUtil.getFluidHandler(stack);
		if (fluidHandler != null) {
			int fill = fluidHandler.fill(new FluidStack(fluid, Integer.MAX_VALUE), true);
			if (fill > 0) {
				return stack;
			}
		}
		throw new IllegalStateException("Could not fill fluid handler for container: " + this);
	}
}
