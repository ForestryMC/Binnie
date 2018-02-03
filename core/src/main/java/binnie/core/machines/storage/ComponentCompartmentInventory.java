package binnie.core.machines.storage;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import binnie.core.machines.IMachine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.network.INetwork;

class ComponentCompartmentInventory extends ComponentInventorySlots implements INetwork.GuiNBT {
	private final int tabCount;
	private final int slotsPerPage;
	private final Map<Integer, CompartmentTab> tabs;

	public ComponentCompartmentInventory(final IMachine machine, final int tabCount, final int pageSize) {
		super(machine);
		this.tabs = new HashMap<>();
		this.tabCount = tabCount;
		this.slotsPerPage = pageSize;
		for (int i = 0; i < this.tabCount * this.slotsPerPage; ++i) {
			this.addSlot(i, "compartment");
		}
	}

	public int getPageSize() {
		return this.slotsPerPage;
	}

	public int getTabCount() {
		return this.tabCount;
	}

	public int[] getSlotsForTab(final int currentTab) {
		final int[] slots = new int[this.slotsPerPage];
		for (int i = 0; i < this.slotsPerPage; ++i) {
			slots[i] = i + currentTab * this.slotsPerPage;
		}
		return slots;
	}

	public CompartmentTab getTab(final int i) {
		return this.tabs.computeIfAbsent(i, CompartmentTab::new);
	}

	@Override
	public void sendGuiNBTToClient(final Map<String, NBTTagCompound> nbt) {
		final NBTTagList list = new NBTTagList();
		for (int i = 0; i < this.tabCount; ++i) {
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
				final CompartmentTab tab = new CompartmentTab(tag);
				this.tabs.put(tab.getId(), tab);
			}
		}
	}

	@Override
	public void receiveGuiNBTOnServer(final EntityPlayer player, final String name, final NBTTagCompound nbt) {
		if (name.equals("comp-change-tab")) {
			final CompartmentTab tab2 = new CompartmentTab(nbt);
			this.tabs.put(tab2.getId(), tab2);
			this.getMachine().getTileEntity().markDirty();
		}
	}

	@Override
	public void readFromNBT(final NBTTagCompound compound) {
		super.readFromNBT(compound);
		final NBTTagList tags = compound.getTagList("tabs", 10);
		for (int i = 0; i < tags.tagCount(); ++i) {
			final NBTTagCompound tag = tags.getCompoundTagAt(i);
			final CompartmentTab tab = new CompartmentTab(tag);
			this.tabs.put(tab.getId(), tab);
		}
	}

	@Override
	public NBTTagCompound writeToNBT(final NBTTagCompound compound) {
		NBTTagCompound nbt = super.writeToNBT(compound);
		final NBTTagList list = new NBTTagList();
		for (int i = 0; i < this.tabCount; ++i) {
			final NBTTagCompound nbt2 = new NBTTagCompound();
			this.getTab(i).writeToNBT(nbt2);
			list.appendTag(nbt2);
		}
		nbt.setTag("tabs", list);
		return nbt;
	}
}
