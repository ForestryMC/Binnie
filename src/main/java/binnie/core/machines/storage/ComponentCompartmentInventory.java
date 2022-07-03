package binnie.core.machines.storage;

import binnie.core.machines.IMachine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.network.INetwork;
import cpw.mods.fml.relauncher.Side;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

class ComponentCompartmentInventory extends ComponentInventorySlots implements INetwork.GuiNBT {
    private int numberOfTabs;
    private int slotsPerPage;
    private Map<Integer, CompartmentTab> tabs;

    public ComponentCompartmentInventory(IMachine machine, int sections) {
        this(machine, sections, 4);
    }

    public ComponentCompartmentInventory(IMachine machine, int tabs, int pageSize) {
        super(machine);
        this.tabs = new HashMap<>();
        numberOfTabs = tabs;
        slotsPerPage = pageSize;
        for (int i = 0; i < numberOfTabs * slotsPerPage; ++i) {
            addSlot(i, "compartment");
        }
    }

    public int getPageSize() {
        return slotsPerPage;
    }

    public int getTabNumber() {
        return numberOfTabs;
    }

    public int[] getSlotsForTab(int currentTab) {
        int[] slots = new int[slotsPerPage];
        for (int i = 0; i < slotsPerPage; ++i) {
            slots[i] = i + currentTab * slotsPerPage;
        }
        return slots;
    }

    public CompartmentTab getTab(int i) {
        if (!tabs.containsKey(i)) {
            tabs.put(i, new CompartmentTab(i));
        }
        return tabs.get(i);
    }

    @Override
    public void sendGuiNBT(Map<String, NBTTagCompound> nbts) {
        NBTTagList list = new NBTTagList();
        for (int i = 0; i < numberOfTabs; ++i) {
            NBTTagCompound nbt2 = new NBTTagCompound();
            getTab(i).writeToNBT(nbt2);
            list.appendTag(nbt2);
        }
        NBTTagCompound tag = new NBTTagCompound();
        tag.setTag("tabs", list);
        nbts.put("comp-tabs", tag);
    }

    @Override
    public void recieveGuiNBT(Side side, EntityPlayer player, String name, NBTTagCompound nbt) {
        if (name.equals("comp-tabs")) {
            NBTTagList tags = nbt.getTagList("tabs", 10);
            for (int i = 0; i < tags.tagCount(); ++i) {
                NBTTagCompound tag = tags.getCompoundTagAt(i);
                CompartmentTab tab = new CompartmentTab(0);
                tab.readFromNBT(tag);
                tabs.put(tab.getId(), tab);
            }
        }
        if (name.equals("comp-change-tab")) {
            CompartmentTab tab2 = new CompartmentTab(0);
            tab2.readFromNBT(nbt);
            tabs.put(tab2.getId(), tab2);
            getMachine().getTileEntity().markDirty();
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        NBTTagList tags = nbt.getTagList("tabs", 10);
        for (int i = 0; i < tags.tagCount(); ++i) {
            NBTTagCompound tag = tags.getCompoundTagAt(i);
            CompartmentTab tab = new CompartmentTab(0);
            tab.readFromNBT(tag);
            tabs.put(tab.getId(), tab);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        NBTTagList list = new NBTTagList();
        for (int i = 0; i < numberOfTabs; ++i) {
            NBTTagCompound nbt2 = new NBTTagCompound();
            getTab(i).writeToNBT(nbt2);
            list.appendTag(nbt2);
        }
        nbt.setTag("tabs", list);
    }
}
