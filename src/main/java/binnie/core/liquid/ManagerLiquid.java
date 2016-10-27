package binnie.core.liquid;

import binnie.core.ManagerBase;
import forestry.core.render.TextureManager;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class ManagerLiquid extends ManagerBase {
	Map<String, IFluidType> fluids;

	public ManagerLiquid() {
		this.fluids = new LinkedHashMap<>();
	}

	public Collection<IFluidType> getFluidTypes() {
		return this.fluids.values();
	}

	public void createLiquids(final IFluidType[] liquids, int startID) {
		for (final IFluidType liquid : liquids) {
			final BinnieFluid fluid = this.createLiquid(liquid, startID++);
			if (fluid == null) {
				throw new RuntimeException("Liquid registered incorrectly - " + liquid.getIdentifier());
			}
		}
	}

	public BinnieFluid createLiquid(final IFluidType fluid, final int id) {
		this.fluids.put(fluid.getIdentifier().toLowerCase(), fluid);
		final BinnieFluid bFluid = new BinnieFluid(fluid);
		FluidRegistry.registerFluid(bFluid);
		ItemFluidContainer.registerFluid(fluid, id);
		return bFluid;
	}

	public FluidStack getFluidStack(final String name, final int amount) {
		return FluidRegistry.getFluidStack(name.toLowerCase(), amount);
	}

	@Override
	public void init() {
	}

	@Override
	public void postInit() {
		for (final IFluidType fluid : this.fluids.values()) {
			for (final FluidContainer container : FluidContainer.values()) {
				if (container.isActive() && fluid.canPlaceIn(container)) {
					container.registerContainerData(fluid);
				}
			}
		}
	}

	public IFluidType getFluidType(final String liquid) {
		return this.fluids.get(liquid.toLowerCase());
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void registerSprites(TextureStitchEvent event) {
		for (IFluidType fluid : this.fluids.values()) {
			TextureManager.registerSprite(fluid.getFlowing());
			TextureManager.registerSprite(fluid.getStill());
		}
	}
}
