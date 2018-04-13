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

	private static final String NBT_KEY_TABS = "tabs";
	private static final String NBT_KEY_COMP_TABS = "comp-tabs";

	@Override
	public void sendGuiNBTToClient(final Map<String, NBTTagCompound> nbt) {
		final NBTTagList list = new NBTTagList();
		for (int i = 0; i < this.tabCount; ++i) {
			final NBTTagCompound nbt2 = new NBTTagCompound();
			this.getTab(i).writeToNBT(nbt2);
			list.appendTag(nbt2);
		}
		final NBTTagCompound tag = new NBTTagCompound();
		tag.setTag(NBT_KEY_TABS, list);
		nbt.put(NBT_KEY_COMP_TABS, tag);
	}

	@Override
	public void receiveGuiNBTOnClient(final EntityPlayer player, final String name, final NBTTagCompound nbt) {
		// TODO: RusTit: I think here should be ACTION_COMP_CHANGE_TAB key, need testing.
		if (name.equals(NBT_KEY_COMP_TABS)) {
			final NBTTagList tags = nbt.getTagList(NBT_KEY_TABS, 10);
			for (int i = 0; i < tags.tagCount(); ++i) {
				final NBTTagCompound tag = tags.getCompoundTagAt(i);
				final CompartmentTab tab = new CompartmentTab(tag);
				this.tabs.put(tab.getId(), tab);
			}
		}
	}

	public static final String ACTION_COMP_CHANGE_TAB = "comp-change-tab";

	@Override
	public void receiveGuiNBTOnServer(final EntityPlayer player, final String name, final NBTTagCompound nbt) {
		if (name.equals(ACTION_COMP_CHANGE_TAB)) {
			final CompartmentTab tab = new CompartmentTab(nbt);
			this.tabs.put(tab.getId(), tab);
			this.getMachine().getTileEntity().markDirty();
		}
	}

	@Override
	public void readFromNBT(final NBTTagCompound compound) {
		super.readFromNBT(compound);
		final NBTTagList tags = compound.getTagList(NBT_KEY_TABS, 10);
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
		nbt.setTag(NBT_KEY_TABS, list);
		return nbt;
	}
}
