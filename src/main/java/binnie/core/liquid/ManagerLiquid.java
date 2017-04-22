package binnie.core.liquid;

import binnie.core.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraftforge.fluids.*;

import java.util.*;

public class ManagerLiquid extends ManagerBase {
	Map<String, IFluidType> fluids;

	public ManagerLiquid() {
		fluids = new LinkedHashMap<String, IFluidType>();
	}

	public Collection<IFluidType> getFluidTypes() {
		return fluids.values();
	}

	public void createLiquids(IFluidType[] liquids, int startID) {
		for (IFluidType liquid : liquids) {
			BinnieFluid fluid = this.createLiquid(liquid, startID++);
			if (fluid == null) {
				throw new RuntimeException("Liquid registered incorrectly - " + liquid.getIdentifier());
			}
		}
	}

	public BinnieFluid createLiquid(IFluidType fluid, int id) {
		fluids.put(fluid.getIdentifier().toLowerCase(), fluid);
		BinnieFluid binnieFluid = new BinnieFluid(fluid);
		FluidRegistry.registerFluid(binnieFluid);
		ItemFluidContainer.registerFluid(fluid, id);
		return binnieFluid;
	}

	public FluidStack getLiquidStack(String name, int amount) {
		return FluidRegistry.getFluidStack(name.toLowerCase(), amount);
	}

	@SideOnly(Side.CLIENT)
	public void reloadIcons(IIconRegister register) {
		for (IFluidType type : fluids.values()) {
			Fluid fluid = getLiquidStack(type.getIdentifier(), 1).getFluid();
			type.registerIcon(register);
			if (fluid == null) {
				throw new RuntimeException("[Binnie] Liquid not registered properly - " + type.getIdentifier());
			}
			fluid.setIcons(type.getIcon());
		}
	}

	@Override
	public void init() {
		// ignored
	}

	@Override
	public void postInit() {
		for (IFluidType fluid : fluids.values()) {
			for (FluidContainer container : FluidContainer.values()) {
				if (container.isActive() && fluid.canPlaceIn(container)) {
					container.registerContainerData(fluid);
				}
			}
		}
	}

	public IFluidType getFluidType(String liquid) {
		return fluids.get(liquid.toLowerCase());
	}
}
