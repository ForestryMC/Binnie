package binnie.core.machines.power;

import forestry.api.core.INBTTagable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

public class TankInfo implements INBTTagable {
    public FluidStack liquid;
    private float capacity;

    public TankInfo(IFluidTank tank) {
        capacity = tank.getCapacity();
        liquid = tank.getFluid();
    }

    public TankInfo() {
        capacity = 0.0f;
    }

    public float getAmount() {
        return (liquid == null) ? 0.0f : liquid.amount;
    }

    public float getCapacity() {
        return capacity;
    }

    public boolean isEmpty() {
        return liquid == null;
    }

    public IIcon getIcon() {
        return liquid.getFluid().getStillIcon();
    }

    public String getName() {
        return (liquid == null) ? "" : liquid.getFluid().getLocalizedName();
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        capacity = nbt.getInteger("capacity");
        if (nbt.hasKey("liquid")) {
            liquid = FluidStack.loadFluidStackFromNBT(nbt.getCompoundTag("liquid"));
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        nbt.setInteger("capacity", (int) getCapacity());
        if (liquid == null) {
            return;
        }
        NBTTagCompound tag = new NBTTagCompound();
        liquid.writeToNBT(tag);
        nbt.setTag("liquid", tag);
    }

    public static TankInfo[] get(ITankMachine machine) {
        TankInfo[] info = new TankInfo[machine.getTanks().length];
        for (int i = 0; i < info.length; ++i) {
            info[i] = new TankInfo(machine.getTanks()[i]);
        }
        return info;
    }
}
