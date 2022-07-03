package binnie.core.machines.inventory;

import binnie.core.machines.IMachine;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ComponentInventorySlots extends ComponentInventory implements IInventoryMachine, IInventorySlots {
    private Map<Integer, InventorySlot> inventory = new LinkedHashMap<>();

    public ComponentInventorySlots(IMachine machine) {
        super(machine);
    }

    @Override
    public int getSizeInventory() {
        int size = 0;
        for (Integer index : inventory.keySet()) {
            size = Math.max(size, index + 1);
        }
        return size;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        if (inventory.containsKey(index)) {
            return inventory.get(index).getContent();
        }
        return null;
    }

    @Override
    public ItemStack decrStackSize(int index, int amount) {
        if (inventory.containsKey(index)) {
            ItemStack stack = inventory.get(index).decrStackSize(amount);
            markDirty();
            return stack;
        }
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int var1) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack itemStack) {
        if (inventory.containsKey(index)
                && (itemStack == null || inventory.get(index).isValid(itemStack))) {
            inventory.get(index).setContent(itemStack);
        }
        markDirty();
    }

    @Override
    public String getInventoryName() {
        return "";
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer var1) {
        return true;
    }

    @Override
    public void openInventory() {
        // ignored
    }

    @Override
    public void closeInventory() {
        // ignored
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        if (nbttagcompound.hasKey("inventory")) {
            NBTTagList inventoryNBT = nbttagcompound.getTagList("inventory", 10);
            for (int i = 0; i < inventoryNBT.tagCount(); ++i) {
                NBTTagCompound slotNBT = inventoryNBT.getCompoundTagAt(i);
                int index = slotNBT.getInteger("id");
                if (slotNBT.hasKey("Slot")) {
                    index = (slotNBT.getByte("Slot") & 0xFF);
                }
                if (inventory.containsKey(index)) {
                    inventory.get(index).readFromNBT(slotNBT);
                }
            }
        }
        markDirty();
    }

    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        NBTTagList inventoryNBT = new NBTTagList();
        for (Map.Entry<Integer, InventorySlot> entry : inventory.entrySet()) {
            NBTTagCompound slotNBT = new NBTTagCompound();
            slotNBT.setInteger("id", entry.getKey());
            entry.getValue().writeToNBT(slotNBT);
            inventoryNBT.appendTag(slotNBT);
        }
        nbttagcompound.setTag("inventory", inventoryNBT);
    }

    @Override
    public InventorySlot addSlot(int index, String unlocalizedName) {
        inventory.put(index, new InventorySlot(index, unlocalizedName));
        return getSlot(index);
    }

    @Override
    public InventorySlot[] addSlotArray(int[] indexes, String unlocalizedName) {
        for (int k : indexes) {
            addSlot(k, unlocalizedName);
        }
        return getSlots(indexes);
    }

    @Override
    public InventorySlot getSlot(int index) {
        if (inventory.containsKey(index)) {
            return inventory.get(index);
        }
        return null;
    }

    @Override
    public InventorySlot[] getAllSlots() {
        return inventory.values().toArray(new InventorySlot[0]);
    }

    @Override
    public InventorySlot[] getSlots(int[] indexes) {
        List<InventorySlot> list = new ArrayList<>();
        for (int i : indexes) {
            if (getSlot(i) != null) {
                list.add(getSlot(i));
            }
        }
        return list.toArray(new InventorySlot[0]);
    }

    @Override
    public boolean isReadOnly(int slot) {
        InventorySlot iSlot = getSlot(slot);
        return iSlot == null || iSlot.isReadOnly();
    }

    @Override
    public boolean hasCustomInventoryName() {
        return true;
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemStack) {
        InventorySlot iSlot = getSlot(slot);
        return iSlot != null && (iSlot.isValid(itemStack) && !isReadOnly(slot));
    }

    @Override
    public void onDestruction() {
        for (InventorySlot slot : inventory.values()) {
            ItemStack stack = slot.getContent();
            if (slot.isRecipe() || stack == null) {
                continue;
            }

            IMachine machine = getMachine();
            World world = machine.getWorld();
            Random rand = world.rand;
            TileEntity tileEntity = machine.getTileEntity();
            float xOffset = rand.nextFloat() * 0.8f + 0.1f;
            float yOffset = rand.nextFloat() * 0.8f + 0.1f;
            float zOffset = rand.nextFloat() * 0.8f + 0.1f;

            if (stack.stackSize == 0) {
                stack.stackSize = 1;
            }

            EntityItem entityItem = new EntityItem(
                    world,
                    tileEntity.xCoord + xOffset,
                    tileEntity.yCoord + yOffset,
                    tileEntity.zCoord + zOffset,
                    stack.copy());
            float accel = 0.05f;
            entityItem.motionX = (float) rand.nextGaussian() * accel;
            entityItem.motionY = (float) rand.nextGaussian() * accel + 0.2f;
            entityItem.motionZ = (float) rand.nextGaussian() * accel;
            world.spawnEntityInWorld(entityItem);
        }
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int var1) {
        List<Integer> slots = new ArrayList<>();
        for (InventorySlot slot : inventory.values()) {
            if (slot.canInsert() || slot.canExtract()) {
                slots.add(slot.getIndex());
            }
        }

        int[] ids = new int[slots.size()];
        for (int i = 0; i < slots.size(); ++i) {
            ids[i] = slots.get(i);
        }
        return ids;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack itemstack, int direction) {
        return isItemValidForSlot(slot, itemstack) && getSlot(slot).canInsert(ForgeDirection.getOrientation(direction));
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack itemstack, int direction) {
        return getSlot(slot).canExtract(ForgeDirection.getOrientation(direction));
    }
}
