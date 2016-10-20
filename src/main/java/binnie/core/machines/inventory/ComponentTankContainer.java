package binnie.core.machines.inventory;

import binnie.core.machines.IMachine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.power.ITankMachine;
import binnie.core.machines.power.TankInfo;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidTank;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ComponentTankContainer extends MachineComponent implements ITankMachine {
    private Map<Integer, TankSlot> tanks;

    public ComponentTankContainer(final IMachine machine) {
        super(machine);
        this.tanks = new LinkedHashMap<Integer, TankSlot>();
    }

    @Override
    public final TankSlot addTank(final int index, final String name, final int capacity) {
        final TankSlot tank = new TankSlot(index, name, capacity);
        this.tanks.put(index, tank);
        return tank;
    }

    @Override
    public final int fill(final EnumFacing from, final FluidStack resource, final boolean doFill) {
        final int index = this.getTankIndexToFill(from, resource);
        if (this.tanks.containsKey(index)) {
            return this.fill(index, resource, doFill);
        }
        return 0;
    }

    @Override
    public final FluidStack drain(final EnumFacing from, final int maxDrain, final boolean doDrain) {
        final int index = this.getTankIndexToDrain(from, null);
        if (this.tanks.containsKey(index)) {
            return this.drain(index, maxDrain, doDrain);
        }
        return null;
    }

    private final int fill(final int tankIndex, final FluidStack resource, final boolean doFill) {
        if (!this.tanks.containsKey(tankIndex)) {
            return 0;
        }
        if (!this.isLiquidValidForTank(resource, tankIndex)) {
            return 0;
        }
        final TankSlot tank = this.tanks.get(tankIndex);
        final int filled = tank.getTank().fill(resource, doFill);
        if (filled > 0) {
            this.markDirty();
        }
        return filled;
    }

    private final FluidStack drain(final int tankIndex, final int maxDrain, final boolean doDrain) {
        if (!this.tanks.containsKey(tankIndex)) {
            return null;
        }
        final TankSlot tank = this.tanks.get(tankIndex);
        final FluidStack drained = tank.getTank().drain(maxDrain, doDrain);
        if (drained != null) {
            this.markDirty();
        }
        return drained;
    }

    private int getTankIndexToFill(final EnumFacing from, final FluidStack resource) {
        for (final TankSlot tank : this.tanks.values()) {
            if (tank.isValid(resource) && tank.canInsert(from) && (tank.getContent() == null || tank.getContent().isFluidEqual(resource))) {
                return tank.getIndex();
            }
        }
        return -1;
    }

    private int getTankIndexToDrain(final EnumFacing from, final FluidStack resource) {
        for (final TankSlot tank : this.tanks.values()) {
            if (tank.getContent() != null && tank.canExtract(from) && (resource == null || resource.isFluidEqual(tank.getContent()))) {
                return tank.getIndex();
            }
        }
        return -1;
    }

    @Override
    public void readFromNBT(final NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        if (nbttagcompound.hasKey("liquidTanks")) {
            final NBTTagList tanksNBT = nbttagcompound.getTagList("liquidTanks", 10);
            for (int i = 0; i < tanksNBT.tagCount(); ++i) {
                final NBTTagCompound tankNBT = tanksNBT.getCompoundTagAt(i);
                final int index = tankNBT.getInteger("index");
                if (this.tanks.containsKey(index)) {
                    this.tanks.get(index).readFromNBT(tankNBT);
                }
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(final NBTTagCompound nbttagcompound2) {
        NBTTagCompound nbttagcompound = super.writeToNBT(nbttagcompound2);
        final NBTTagList tanksNBT = new NBTTagList();
        for (final Map.Entry<Integer, TankSlot> entry : this.tanks.entrySet()) {
            final NBTTagCompound tankNBT = new NBTTagCompound();
            tankNBT.setInteger("index", entry.getKey());
            entry.getValue().writeToNBT(tankNBT);
            tanksNBT.appendTag(tankNBT);
        }
        nbttagcompound.setTag("liquidTanks", tanksNBT);
        return nbttagcompound;
    }

    @Override
    public boolean isTankReadOnly(final int tank) {
        return this.tanks.get(tank).isReadOnly();
    }

    @Override
    public boolean isLiquidValidForTank(final FluidStack liquid, final int tank) {
        final TankSlot slot = this.getTankSlot(tank);
        return slot != null && (slot.isValid(liquid) && !slot.isReadOnly());
    }

    @Override
    public TankInfo[] getTankInfos() {
        return TankInfo.get(this);
    }

    @Override
    public IFluidTank getTank(final int index) {
        return this.getTanks()[index];
    }

    @Override
    public IFluidTank[] getTanks() {
        final List<IFluidTank> ltanks = new ArrayList<IFluidTank>();
        for (final TankSlot tank : this.tanks.values()) {
            ltanks.add(tank.getTank());
        }
        return ltanks.toArray(new IFluidTank[0]);
    }

    @Override
    public TankSlot getTankSlot(final int index) {
        return this.tanks.get(index);
    }

    @Override
    public FluidStack drain(final EnumFacing from, final FluidStack resource, final boolean doDrain) {
        final int index = this.getTankIndexToDrain(from, null);
        if (this.tanks.containsKey(index)) {
            return this.drain(index, resource.amount, doDrain);
        }
        return null;
    }

    @Override
    public boolean canFill(final EnumFacing from, final Fluid fluid) {
        return this.fill(from, new FluidStack(fluid, 1), false) > 0;
    }

    @Override
    public boolean canDrain(final EnumFacing from, final Fluid fluid) {
        return this.drain(from, new FluidStack(fluid, 1), false) != null;
    }

    @Override
    public FluidTankInfo[] getTankInfo(final EnumFacing from) {
        final FluidTankInfo[] info = new FluidTankInfo[this.getTanks().length];
        for (int i = 0; i < info.length; ++i) {
            info[i] = new FluidTankInfo(this.getTanks()[i]);
        }
        return info;
    }

    public void markDirty() {
        if (this.getMachine() != null) {
            this.getMachine().markDirty();
        }
    }
}
