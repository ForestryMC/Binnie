package binnie.core.machines.power;

import binnie.core.machines.inventory.*;
import net.minecraftforge.fluids.*;

public interface ITankMachine extends IFluidHandler, IValidatedTankContainer {
	TankInfo[] getTankInfos();

	IFluidTank[] getTanks();

	TankSlot addTank(int index, String name, int capacity);

	IFluidTank getTank(int index);

	TankSlot getTankSlot(int slot);
}
