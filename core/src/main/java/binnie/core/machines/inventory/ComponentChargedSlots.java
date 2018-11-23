package binnie.core.machines.inventory;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import binnie.core.machines.Machine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.network.INetwork;

public class ComponentChargedSlots extends MachineComponent implements INetwork.GuiNBT, IChargedSlots {
	private final Map<Integer, Float> charges;

	public ComponentChargedSlots(Machine machine) {
		super(machine);
		this.charges = new HashMap<>();
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.charges.clear();
		NBTTagList list = compound.getTagList("charges", 10);
		for (int i = 0; i < list.tagCount(); ++i) {
			NBTTagCompound tag = list.getCompoundTagAt(i);
			this.charges.put((int) tag.getByte("i"), tag.getByte("v") / 100.0f);
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		NBTTagCompound nbttagcompound = super.writeToNBT(compound);
		NBTTagList chargeList = new NBTTagList();
		for (Map.Entry<Integer, Float> entry : this.charges.entrySet()) {
			NBTTagCompound chargesNBT = new NBTTagCompound();
			chargesNBT.setByte("i", entry.getKey().byteValue());
			chargesNBT.setByte("v", (byte) (entry.getValue() * 100.0f));
			chargeList.appendTag(chargesNBT);
		}
		nbttagcompound.setTag("charges", chargeList);
		return nbttagcompound;
	}

	public void addCharge(int slot) {
		this.charges.put(slot, 0.0f);
	}

	@Override
	public void receiveGuiNBTOnServer(EntityPlayer player, String name, NBTTagCompound nbt) {

	}

	@Override
	public void receiveGuiNBTOnClient(EntityPlayer player, String name, NBTTagCompound nbt) {
		if (name.equals("slot-charges")) {
			for (int i : this.charges.keySet()) {
				this.charges.put(i, nbt.getShort(String.valueOf(i)) / 100.0f);
			}
		}
	}

	@Override
	public void sendGuiNBTToClient(Map<String, NBTTagCompound> data) {
		NBTTagCompound tag = new NBTTagCompound();
		for (int i : this.charges.keySet()) {
			tag.setShort(String.valueOf(i), (short) (this.charges.get(i) * 100.0f));
		}
		data.put("slot-charges", tag);
	}

	@Override
	public float getCharge(int index) {
		return this.charges.getOrDefault(index, 0.0f);
	}

	@Override
	public void setCharge(int index, float value) {
		if (value > 1.0f) {
			value = 1.0f;
		}
		if (value < 0.0f) {
			value = 0.0f;
		}
		if (this.charges.containsKey(index)) {
			this.charges.put(index, value);
		}
	}

	@Override
	public void onUpdate() {
		for (int slot : this.charges.keySet()) {
			if (this.getCharge(slot) <= 0.0f && !this.getUtil().decreaseStack(slot, 1).isEmpty()) {
				this.setCharge(slot, 1.0f);
			}
		}
	}

	@Override
	public void alterCharge(int index, float value) {
		this.setCharge(index, this.getCharge(index) + value);
	}
}
