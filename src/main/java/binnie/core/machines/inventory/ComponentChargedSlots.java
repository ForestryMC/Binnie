package binnie.core.machines.inventory;

import binnie.core.machines.Machine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.network.INetwork;
import cpw.mods.fml.relauncher.Side;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class ComponentChargedSlots extends MachineComponent implements INetwork.GuiNBT, IChargedSlots {
    private Map<Integer, Float> charges = new HashMap<>();

    public ComponentChargedSlots(Machine machine) {
        super(machine);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        charges.clear();
        NBTTagList list = nbttagcompound.getTagList("charges", 10);
        for (int i = 0; i < list.tagCount(); ++i) {
            NBTTagCompound tag = list.getCompoundTagAt(i);
            charges.put((int) tag.getByte("i"), tag.getByte("v") / 100.0f);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        NBTTagList chargeList = new NBTTagList();
        for (Map.Entry<Integer, Float> entry : charges.entrySet()) {
            NBTTagCompound chargesNBT = new NBTTagCompound();
            chargesNBT.setByte("i", (byte) (0 + entry.getKey()));
            chargesNBT.setByte("v", (byte) (entry.getValue() * 100.0f));
            chargeList.appendTag(chargesNBT);
        }
        nbttagcompound.setTag("charges", chargeList);
    }

    public void addCharge(int slot) {
        charges.put(slot, 0.0f);
    }

    @Override
    public void recieveGuiNBT(Side side, EntityPlayer player, String name, NBTTagCompound nbt) {
        if (!name.equals("slot-charges")) {
            return;
        }

        for (int i : charges.keySet()) {
            charges.put(i, nbt.getShort("" + i) / 100.0f);
        }
    }

    @Override
    public void sendGuiNBT(Map<String, NBTTagCompound> nbts) {
        NBTTagCompound tag = new NBTTagCompound();
        for (int i : charges.keySet()) {
            tag.setShort("" + i, (short) (charges.get(i) * 100.0f));
        }
        nbts.put("slot-charges", tag);
    }

    @Override
    public float getCharge(int slot) {
        return charges.getOrDefault(slot, 0.0f);
    }

    @Override
    public void setCharge(int slot, float value) {
        if (value > 1.0f) {
            value = 1.0f;
        }
        if (value < 0.0f) {
            value = 0.0f;
        }
        if (charges.containsKey(slot)) {
            charges.put(slot, value);
        }
    }

    @Override
    public void onUpdate() {
        for (int slot : charges.keySet()) {
            if (getCharge(slot) <= 0.0f && getUtil().decreaseStack(slot, 1) != null) {
                setCharge(slot, 1.0f);
            }
        }
    }

    @Override
    public void alterCharge(int slot, float value) {
        setCharge(slot, getCharge(slot) + value);
    }
}
