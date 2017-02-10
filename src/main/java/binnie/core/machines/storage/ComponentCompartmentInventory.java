package binnie.core.machines.storage;

import binnie.core.machines.IMachine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.network.INetwork;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.HashMap;
import java.util.Map;

class ComponentCompartmentInventory extends ComponentInventorySlots implements INetwork.GuiNBT {
	private int numberOfTabs;
	private int slotsPerPage;
	private Map<Integer, CompartmentTab> tabs;

	public ComponentCompartmentInventory(final IMachine machine, final int sections) {
		this(machine, sections, 4);
	}

	public ComponentCompartmentInventory(final IMachine machine, final int tabs, final int pageSize) {
		super(machine);
		this.tabs = new HashMap<>();
		this.numberOfTabs = tabs;
		this.slotsPerPage = pageSize;
		for (int i = 0; i < this.numberOfTabs * this.slotsPerPage; ++i) {
			this.addSlot(i, "compartment");
		}
	}

	public int getPageSize() {
		return this.slotsPerPage;
	}

	public int getTabNumber() {
		return this.numberOfTabs;
	}

	public int[] getSlotsForTab(final int currentTab) {
		final int[] slots = new int[this.slotsPerPage];
		for (int i = 0; i < this.slotsPerPage; ++i) {
			slots[i] = i + currentTab * this.slotsPerPage;
		}
		return slots;
	}

	public CompartmentTab getTab(final int i) {
		if (!this.tabs.containsKey(i)) {
			this.tabs.put(i, new CompartmentTab(i));
		}
		return this.tabs.get(i);
	}

	@Override
	public void sendGuiNBTToClient(final Map<String, NBTTagCompound> nbt) {
		final NBTTagList list = new NBTTagList();
		for (int i = 0; i < this.numberOfTabs; ++i) {
			final NBTTagCompound nbt2 = new NBTTagCompound();
			this.getTab(i).writeToNBT(nbt2);
			list.appendTag(nbt2);
		}
		final NBTTagCompound tag = new NBTTagCompound();
		tag.setTag("tabs", list);
		nbt.put("comp-tabs", tag);
	}

	@Override
	public void receiveGuiNBTOnClient(EntityPlayer player, String name, NBTTagCompound nbt) {
		if (name.equals("comp-tabs")) {
			final NBTTagList tags = nbt.getTagList("tabs", 10);
			for (int i = 0; i < tags.tagCount(); ++i) {
				final NBTTagCompound tag = tags.getCompoundTagAt(i);
				final CompartmentTab tab = new CompartmentTab(0);
				tab.readFromNBT(tag);
				this.tabs.put(tab.getId(), tab);
			}
		}
	}

	@Override
	public void receiveGuiNBTOnServer(final EntityPlayer player, final String name, final NBTTagCompound nbt) {
		if (name.equals("comp-change-tab")) {
			final CompartmentTab tab2 = new CompartmentTab(0);
			tab2.readFromNBT(nbt);
			this.tabs.put(tab2.getId(), tab2);
			this.getMachine().getTileEntity().markDirty();
		}
	}

	@Override
	public void readFromNBT(final NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		final NBTTagList tags = nbt.getTagList("tabs", 10);
		for (int i = 0; i < tags.tagCount(); ++i) {
			final NBTTagCompound tag = tags.getCompoundTagAt(i);
			final CompartmentTab tab = new CompartmentTab(0);
			tab.readFromNBT(tag);
			this.tabs.put(tab.getId(), tab);
		}
	}

	@Override
	public NBTTagCompound writeToNBT(final NBTTagCompound nbt4) {
		NBTTagCompound nbt = super.writeToNBT(nbt4);
		final NBTTagList list = new NBTTagList();
		for (int i = 0; i < this.numberOfTabs; ++i) {
			final NBTTagCompound nbt2 = new NBTTagCompound();
			this.getTab(i).writeToNBT(nbt2);
			list.appendTag(nbt2);
		}
		nbt.setTag("tabs", list);
		return nbt;
	}
}
