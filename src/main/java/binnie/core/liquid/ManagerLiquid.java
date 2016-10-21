package binnie.core.liquid;

import binnie.core.ManagerBase;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

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

    public FluidStack getLiquidStack(final String name, final int amount) {
        return FluidRegistry.getFluidStack(name.toLowerCase(), amount);
    }

//	@SideOnly(Side.CLIENT)
//	public void reloadIcons(final IIconRegister register) {
//		for (final IFluidType type : this.fluids.values()) {
//			final Fluid fluid = this.getLiquidStack(type.getIdentifier(), 1).getFluid();
//			type.registerIcon(register);
//			if (fluid == null) {
//				throw new RuntimeException("[Binnie] Liquid not registered properly - " + type.getIdentifier());
//			}
//			fluid.setIcons(type.getIcon());
//		}
//	}

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
}
