package binnie.core.machines.inventory;

import binnie.core.machines.IMachine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.power.ITankMachine;
import binnie.core.machines.power.TankInfo;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidTank;

public class ComponentTankContainer extends MachineComponent implements ITankMachine {
    private Map<Integer, TankSlot> tanks = new LinkedHashMap<>();

    public ComponentTankContainer(IMachine machine) {
        super(machine);
    }

    @Override
    public TankSlot addTank(int index, String name, int capacity) {
        TankSlot tank = new TankSlot(index, name, capacity);
        tanks.put(index, tank);
        return tank;
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        int index = getTankIndexToFill(from, resource);
        if (tanks.containsKey(index)) {
            return fill(index, resource, doFill);
        }
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        int index = getTankIndexToDrain(from, null);
        if (tanks.containsKey(index)) {
            return drain(index, maxDrain, doDrain);
        }
        return null;
    }

    private int fill(int tankIndex, FluidStack resource, boolean doFill) {
        if (!tanks.containsKey(tankIndex) || !isLiquidValidForTank(resource, tankIndex)) {
            return 0;
        }

        TankSlot tank = tanks.get(tankIndex);
        int filled = tank.getTank().fill(resource, doFill);
        if (filled > 0) {
            markDirty();
        }
        return filled;
    }

    private FluidStack drain(int tankIndex, int maxDrain, boolean doDrain) {
        if (!tanks.containsKey(tankIndex)) {
            return null;
        }

        TankSlot tank = tanks.get(tankIndex);
        FluidStack drained = tank.getTank().drain(maxDrain, doDrain);
        if (drained != null) {
            markDirty();
        }
        return drained;
    }

    private int getTankIndexToFill(ForgeDirection from, FluidStack resource) {
        for (TankSlot tank : tanks.values()) {
            if (tank.isValid(resource)
                    && tank.canInsert(from)
                    && (tank.getContent() == null || tank.getContent().isFluidEqual(resource))) {
                return tank.getIndex();
            }
        }
        return -1;
    }

    private int getTankIndexToDrain(ForgeDirection from, FluidStack resource) {
        for (TankSlot tank : tanks.values()) {
            if (tank.getContent() != null
                    && tank.canExtract(from)
                    && (resource == null || resource.isFluidEqual(tank.getContent()))) {
                return tank.getIndex();
            }
        }
        return -1;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        if (!nbttagcompound.hasKey("liquidTanks")) {
            return;
        }

        NBTTagList tanksNBT = nbttagcompound.getTagList("liquidTanks", 10);
        for (int i = 0; i < tanksNBT.tagCount(); ++i) {
            NBTTagCompound tankNBT = tanksNBT.getCompoundTagAt(i);
            int index = tankNBT.getInteger("index");
            if (tanks.containsKey(index)) {
                tanks.get(index).readFromNBT(tankNBT);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        NBTTagList tanksNBT = new NBTTagList();
        for (Map.Entry<Integer, TankSlot> entry : tanks.entrySet()) {
            NBTTagCompound tankNBT = new NBTTagCompound();
            tankNBT.setInteger("index", entry.getKey());
            entry.getValue().writeToNBT(tankNBT);
            tanksNBT.appendTag(tankNBT);
        }
        nbttagcompound.setTag("liquidTanks", tanksNBT);
    }

    @Override
    public boolean isTankReadOnly(int tank) {
        return tanks.get(tank).isReadOnly();
    }

    @Override
    public boolean isLiquidValidForTank(FluidStack liquid, int tank) {
        TankSlot slot = getTankSlot(tank);
        return slot != null && slot.isValid(liquid) && !slot.isReadOnly();
    }

    @Override
    public TankInfo[] getTankInfos() {
        return TankInfo.get(this);
    }

    @Override
    public IFluidTank getTank(int index) {
        return getTanks()[index];
    }

    @Override
    public IFluidTank[] getTanks() {
        List<IFluidTank> ltanks = new ArrayList<>();
        for (TankSlot tank : tanks.values()) {
            ltanks.add(tank.getTank());
        }
        return ltanks.toArray(new IFluidTank[0]);
    }

    @Override
    public TankSlot getTankSlot(int slot) {
        return tanks.get(slot);
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        int index = getTankIndexToDrain(from, null);
        if (!tanks.containsKey(index)) {
            return null;
        }
        return drain(index, resource.amount, doDrain);
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return fill(from, new FluidStack(fluid, 1), false) > 0;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return drain(from, new FluidStack(fluid, 1), false) != null;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        FluidTankInfo[] info = new FluidTankInfo[getTanks().length];
        for (int i = 0; i < info.length; ++i) {
            info[i] = new FluidTankInfo(getTanks()[i]);
        }
        return info;
    }

    public void markDirty() {
        if (getMachine() != null) {
            getMachine().markDirty();
        }
    }
}
