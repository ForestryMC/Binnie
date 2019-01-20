package binnie.core.liquid;

import javax.annotation.Nullable;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import binnie.core.Constants;
import binnie.core.features.FeatureType;
import binnie.core.features.IFeatureConstructor;
import binnie.core.features.IItemFeature;
import binnie.core.features.RegisterFeatureEvent;
import binnie.core.modules.BinnieCoreModuleUIDs;
import binnie.core.util.I18N;

@Mod.EventBusSubscriber(modid = Constants.CORE_MOD_ID)
public enum FluidContainerType implements IItemFeature<Item, Item> {
	CAPSULE(false),
	REFRACTORY(false),
	CAN(false),
	GLASS(true),
	CYLINDER(true);


	private final String identifier;
	private final IFeatureConstructor<Item> constructor;
	@Nullable
	private Item item;

	FluidContainerType(boolean coreContainer) {
		this.identifier = name().toLowerCase();
		if (coreContainer) {
			this.constructor = () -> new ItemFluidContainer(this);
		} else {
			this.constructor = () -> ForgeRegistries.ITEMS.getValue(new ResourceLocation("forestry", identifier));
		}
	}

	@SubscribeEvent
	public static void registerFeatures(RegisterFeatureEvent event) {
		event.register(FluidContainerType.class);
	}

	public String getIdentifier() {
		return identifier;
	}

	@Override
	public IFeatureConstructor<Item> getConstructor() {
		return constructor;
	}

	@Override
	public FeatureType getType() {
		return FeatureType.ITEM;
	}

	@Override
	public String getModId() {
		return Constants.CORE_MOD_ID;
	}

	@Override
	public String getModuleId() {
		return BinnieCoreModuleUIDs.CORE;
	}

	public String getDisplayName() {
		return I18N.localise("binniecore.item.container." + getIdentifier());
	}

	public ItemStack get(int amount) {
		return new ItemStack(item(), amount);
	}

	public ItemStack getEmpty() {
		return new ItemStack(item());
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


	@Override
	public void setItem(Item item) {
		this.item = item;
	}

	@Override
	public boolean hasItem() {
		return item != null;
	}

	@Nullable
	@Override
	public Item getItem() {
		return item;
	}
}
