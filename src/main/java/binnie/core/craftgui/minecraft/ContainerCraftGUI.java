package binnie.core.craftgui.minecraft;

import binnie.core.BinnieCore;
import binnie.core.craftgui.minecraft.control.ControlSlot;
import binnie.core.craftgui.minecraft.control.EnumHighlighting;
import binnie.core.machines.IMachine;
import binnie.core.machines.Machine;
import binnie.core.machines.network.INetwork;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IErrorStateSource;
import binnie.core.machines.power.IPoweredMachine;
import binnie.core.machines.power.IProcess;
import binnie.core.machines.power.ITankMachine;
import binnie.core.machines.power.PowerInfo;
import binnie.core.machines.power.ProcessInfo;
import binnie.core.machines.power.TankInfo;
import binnie.core.machines.transfer.TransferRequest;
import binnie.core.network.packet.MessageContainerUpdate;
import com.mojang.authlib.GameProfile;
import cpw.mods.fml.relauncher.Side;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ContainerCraftGUI extends Container {
    private Window window;
    private Map<String, NBTTagCompound> syncedNBT;
    private Map<String, NBTTagCompound> sentNBT;
    private Map<Integer, TankInfo> syncedTanks;
    private PowerInfo syncedPower;
    private ProcessInfo syncedProcess;
    private int errorType;
    private ErrorState error;
    private int mousedOverSlotNumber;

    public ContainerCraftGUI(Window window) {
        this.window = window;
        syncedNBT = new HashMap<>();
        sentNBT = new HashMap<>();
        syncedTanks = new HashMap<>();
        syncedPower = new PowerInfo();
        syncedProcess = new ProcessInfo();
        errorType = 0;
        error = null;
        mousedOverSlotNumber = -1;
        IMachine machine = Machine.getMachine(window.getInventory());
        if (getSide() != Side.SERVER) {
            return;
        }

        inventoryItemStacks = new ListMap();
        inventorySlots = new ListMap();
        if (machine == null) {
            return;
        }

        GameProfile user = machine.getOwner();
        if (user != null) {
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setString("username", user.getName());
            sendNBTToClient("username", nbt);
        }
    }

    @Override
    protected Slot addSlotToContainer(Slot slot) {
        return super.addSlotToContainer(slot);
    }

    private Side getSide() {
        return window.isServer() ? Side.SERVER : Side.CLIENT;
    }

    @Override
    public Slot getSlot(int index) {
        if (index < 0 || index >= inventorySlots.size()) {
            return null;
        }
        return (Slot) inventorySlots.get(index);
    }

    @Override
    public void putStackInSlot(int index, ItemStack stack) {
        if (getSlot(index) != null) {
            getSlot(index).putStack(stack);
        }
    }

    @Override
    public void putStacksInSlots(ItemStack[] par1ArrayOfItemStack) {
        for (int i = 0; i < par1ArrayOfItemStack.length; ++i) {
            if (getSlot(i) != null) {
                getSlot(i).putStack(par1ArrayOfItemStack[i]);
            }
        }
    }

    @Override
    public void onContainerClosed(EntityPlayer player) {
        super.onContainerClosed(player);
        WindowInventory inventory = window.getWindowInventory();
        for (int i = 0; i < inventory.getSizeInventory(); ++i) {
            if (!inventory.dispenseOnClose(i)) {
                continue;
            }

            ItemStack stack = inventory.getStackInSlot(i);
            if (stack == null) {
                continue;
            }

            stack = new TransferRequest(stack, player.inventory).transfer(true);
            if (stack != null) {
                player.dropPlayerItemWithRandomChoice(stack, false);
            }
        }
    }

    @Override
    public ItemStack slotClick(int slotNum, int mouseButton, int modifier, EntityPlayer player) {
        Slot slot = getSlot(slotNum);
        if (slot instanceof CustomSlot && ((CustomSlot) slot).handleClick()) {
            ((CustomSlot) slot).onSlotClick(this, mouseButton, modifier, player);
            return player.inventory.getItemStack();
        }
        return super.slotClick(slotNum, mouseButton, modifier, player);
    }

    public void sendNBTToClient(String key, NBTTagCompound nbt) {
        syncedNBT.put(key, nbt);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        if (player instanceof EntityPlayerMP) {
            if (!crafters.contains(player)) {
                crafters.add(player);
            }
            sentNBT.clear();
        }
        IInventory inventory = window.getInventory();
        return inventory == null || inventory.isUseableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
        return shiftClick(player, slotID);
    }

    private ItemStack shiftClick(EntityPlayer player, int index) {
        TransferRequest request = getShiftClickRequest(player, index);
        if (request == null) {
            return null;
        }

        ItemStack stack = request.transfer(true);
        Slot shiftClickedSlot = (Slot) inventorySlots.get(index);
        shiftClickedSlot.putStack(stack);
        shiftClickedSlot.onSlotChanged();
        return null;
    }

    private TransferRequest getShiftClickRequest(EntityPlayer player, int index) {
        if (index < 0) {
            return null;
        }

        Slot shiftClickedSlot = (Slot) inventorySlots.get(index);
        ItemStack itemstack = null;
        if (shiftClickedSlot.getHasStack()) {
            itemstack = shiftClickedSlot.getStack().copy();
        }

        IInventory playerInventory = player.inventory;
        IInventory containerInventory = window.getInventory();
        IInventory windowInventory = window.getWindowInventory();
        IInventory fromPlayer = (containerInventory == null) ? windowInventory : containerInventory;
        int[] target = new int[36];
        for (int i = 0; i < 36; ++i) {
            target[i] = i;
        }

        TransferRequest request;
        if (shiftClickedSlot.inventory == playerInventory) {
            request = new TransferRequest(itemstack, fromPlayer).setOrigin(shiftClickedSlot.inventory);
        } else {
            request = new TransferRequest(itemstack, playerInventory)
                    .setOrigin(shiftClickedSlot.inventory)
                    .setTargetSlots(target);
        }

        if (window instanceof IWindowAffectsShiftClick) {
            ((IWindowAffectsShiftClick) window).alterRequest(request);
        }
        return request;
    }

    public ItemStack tankClick(EntityPlayer player, int slotID) {
        if (player.inventory.getItemStack() == null) {
            return null;
        }

        ItemStack heldItem = player.inventory.getItemStack().copy();
        heldItem = new TransferRequest(heldItem, window.getInventory())
                .setOrigin(player.inventory)
                .setTargetSlots(new int[0])
                .setTargetTanks(new int[] {slotID})
                .transfer(true);
        player.inventory.setItemStack(heldItem);
        if (player instanceof EntityPlayerMP) {
            ((EntityPlayerMP) player).updateHeldItem();
        }
        return heldItem;
    }

    public boolean handleNBT(Side side, EntityPlayer player, String name, NBTTagCompound action) {
        if (side == Side.SERVER) {
            if (name.equals("tank-click")) {
                tankClick(player, action.getByte("id"));
            }
            if (name.equals("slot-reg")) {
                int type = action.getByte("t");
                int index = action.getShort("i");
                int slotNumber = action.getShort("n");
                getOrCreateSlot(InventoryType.values()[type % 4], index, slotNumber);

                for (Object crafterObject : crafters) {
                    ICrafting crafter = (ICrafting) crafterObject;
                    crafter.sendContainerAndContentsToPlayer(this, getInventory());
                }
            }
        }

        if (name.contains("tank-update")) {
            onTankUpdate(action);
        } else if (name.equals("power-update")) {
            onPowerUpdate(action);
        } else if (name.equals("process-update")) {
            onProcessUpdate(action);
        } else if (name.equals("error-update")) {
            onErrorUpdate(action);
        } else if (name.equals("mouse-over-slot")) {
            onMouseOverSlot(player, action);
        } else if (name.equals("shift-click-info")) {
            onRecieveShiftClickHighlights(player, action);
        }
        return false;
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        ITankMachine tanks = Machine.getInterface(ITankMachine.class, window.getInventory());
        IPoweredMachine powered = Machine.getInterface(IPoweredMachine.class, window.getInventory());
        IErrorStateSource error = Machine.getInterface(IErrorStateSource.class, window.getInventory());
        IProcess process = Machine.getInterface(IProcess.class, window.getInventory());
        if (tanks != null && window.isServer()) {
            for (int i = 0; i < tanks.getTankInfos().length; ++i) {
                TankInfo tank = tanks.getTankInfos()[i];
                if (!getTankInfo(i).equals(tank)) {
                    syncedNBT.put("tank-update-" + i, createTankNBT(i, tank));
                    syncedTanks.put(i, tank);
                }
            }
        }

        if (powered != null && window.isServer()) {
            syncedNBT.put("power-update", createPowerNBT(powered.getPowerInfo()));
        }
        if (process != null && window.isServer()) {
            syncedNBT.put("process-update", createProcessNBT(process.getInfo()));
        }
        if (error != null && window.isServer()) {
            syncedNBT.put("error-update", createErrorNBT(error));
        }

        INetwork.SendGuiNBT machineSync = Machine.getInterface(INetwork.SendGuiNBT.class, window.getInventory());
        if (machineSync != null) {
            machineSync.sendGuiNBT(syncedNBT);
        }

        Map<String, NBTTagCompound> sentThisTime = new HashMap<>();
        for (Map.Entry<String, NBTTagCompound> nbt : syncedNBT.entrySet()) {
            nbt.getValue().setString("type", nbt.getKey());
            boolean shouldSend = true;
            NBTTagCompound lastSent = sentNBT.get(nbt.getKey());
            if (lastSent != null) {
                shouldSend = !lastSent.equals(nbt.getValue());
            }

            if (shouldSend) {
                for (Object crafter : crafters) {
                    if (crafter instanceof EntityPlayerMP) {
                        EntityPlayerMP player = (EntityPlayerMP) crafter;
                        BinnieCore.proxy.sendToPlayer(new MessageContainerUpdate(nbt.getValue()), player);
                    }
                }
                sentThisTime.put(nbt.getKey(), nbt.getValue());
            }
        }

        sentNBT.putAll(sentThisTime);
        syncedNBT.clear();
    }

    private NBTTagCompound createErrorNBT(IErrorStateSource error) {
        NBTTagCompound nbt = new NBTTagCompound();
        ErrorState state = null;
        if (error.canWork() != null) {
            nbt.setByte("type", (byte) 0);
            state = error.canWork();
        } else if (error.canProgress() != null) {
            nbt.setByte("type", (byte) 1);
            state = error.canProgress();
        }

        if (state != null) {
            state.writeToNBT(nbt);
        }
        return nbt;
    }

    public NBTTagCompound createPowerNBT(PowerInfo powerInfo) {
        NBTTagCompound nbt = new NBTTagCompound();
        powerInfo.writeToNBT(nbt);
        return nbt;
    }

    public NBTTagCompound createProcessNBT(ProcessInfo powerInfo) {
        NBTTagCompound nbt = new NBTTagCompound();
        powerInfo.writeToNBT(nbt);
        return nbt;
    }

    public NBTTagCompound createTankNBT(int tank, TankInfo tankInfo) {
        NBTTagCompound nbt = new NBTTagCompound();
        tankInfo.writeToNBT(nbt);
        nbt.setByte("tank", (byte) tank);
        return nbt;
    }

    public void onTankUpdate(NBTTagCompound nbt) {
        int tankID = nbt.getByte("tank");
        TankInfo tank = new TankInfo();
        tank.readFromNBT(nbt);
        syncedTanks.put(tankID, tank);
    }

    public void onProcessUpdate(NBTTagCompound nbt) {
        (syncedProcess = new ProcessInfo()).readFromNBT(nbt);
    }

    public void onPowerUpdate(NBTTagCompound nbt) {
        syncedPower = new PowerInfo();
        syncedPower.readFromNBT(nbt);
    }

    public PowerInfo getPowerInfo() {
        return syncedPower;
    }

    public ProcessInfo getProcessInfo() {
        return syncedProcess;
    }

    public TankInfo getTankInfo(int tank) {
        return syncedTanks.containsKey(tank) ? syncedTanks.get(tank) : new TankInfo();
    }

    public void onErrorUpdate(NBTTagCompound nbt) {
        errorType = nbt.getByte("type");
        if (nbt.hasKey("name")) {
            error = new ErrorState("", "");
            error.readFromNBT(nbt);
        } else {
            error = null;
        }
    }

    public ErrorState getErrorState() {
        return error;
    }

    public int getErrorType() {
        return errorType;
    }

    public CustomSlot[] getCustomSlots() {
        List<CustomSlot> slots = new ArrayList<>();
        for (Object object : inventorySlots) {
            if (object instanceof CustomSlot) {
                slots.add((CustomSlot) object);
            }
        }
        return slots.toArray(new CustomSlot[0]);
    }

    public void setMouseOverSlot(Slot slot) {
        if (slot.slotNumber == mousedOverSlotNumber) {
            return;
        }

        mousedOverSlotNumber = slot.slotNumber;
        ControlSlot.highlighting.get(EnumHighlighting.SHIFT_CLICK).clear();
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setShort("slot", (short) slot.slotNumber);
        window.sendClientAction("mouse-over-slot", nbt);
    }

    private void onMouseOverSlot(EntityPlayer player, NBTTagCompound data) {
        int slotnumber = data.getShort("slot");
        TransferRequest request = getShiftClickRequest(player, slotnumber);
        if (request == null) {
            return;
        }

        request.transfer(false);
        NBTTagCompound nbt = new NBTTagCompound();
        List<Integer> slots = new ArrayList<>();
        for (TransferRequest.TransferSlot tslot : request.getInsertedSlots()) {
            Slot slot = getSlot(tslot.inventory, tslot.id);
            if (slot != null) {
                slots.add(slot.slotNumber);
            }
        }

        int[] array = new int[slots.size()];
        for (int i = 0; i < slots.size(); ++i) {
            array[i] = slots.get(i);
        }

        nbt.setIntArray("slots", array);
        nbt.setShort("origin", (short) slotnumber);
        syncedNBT.put("shift-click-info", nbt);
    }

    private void onRecieveShiftClickHighlights(EntityPlayer player, NBTTagCompound data) {
        ControlSlot.highlighting.get(EnumHighlighting.SHIFT_CLICK).clear();
        for (int slotnumber : data.getIntArray("slots")) {
            ControlSlot.highlighting.get(EnumHighlighting.SHIFT_CLICK).add(slotnumber);
        }
    }

    private CustomSlot getSlot(IInventory inventory, int id) {
        for (Object o : inventorySlots) {
            CustomSlot slot = (CustomSlot) o;
            if (slot.inventory == inventory && slot.getSlotIndex() == id) {
                return slot;
            }
        }
        return null;
    }

    public void recieveNBT(Side side, EntityPlayer player, NBTTagCompound action) {
        String name = action.getString("type");
        if (handleNBT(side, player, name, action)) {
            return;
        }

        window.recieveGuiNBT(getSide(), player, name, action);
        INetwork.RecieveGuiNBT machine = Machine.getInterface(INetwork.RecieveGuiNBT.class, window.getInventory());
        if (machine != null) {
            machine.recieveGuiNBT(getSide(), player, name, action);
        }
    }

    public Slot getOrCreateSlot(InventoryType type, int index) {
        IInventory inventory = getInventory(type);
        Slot slot = getSlot(inventory, index);
        if (slot == null) {
            slot = new CustomSlot(inventory, index);
            addSlotToContainer(slot);
        }

        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setByte("t", (byte) type.ordinal());
        nbt.setShort("i", (short) index);
        nbt.setShort("n", (short) slot.slotNumber);
        window.sendClientAction("slot-reg", nbt);
        return slot;
    }

    protected IInventory getInventory(InventoryType type) {
        if (type == InventoryType.Machine) {
            return window.getInventory();
        }
        if (type == InventoryType.Player) {
            return window.getPlayer().inventory;
        }
        if (type == InventoryType.Window) {
            return window.getWindowInventory();
        }
        return null;
    }

    private Slot getOrCreateSlot(InventoryType type, int index, int slotNumber) {
        IInventory inventory = getInventory(type);
        if (inventorySlots.get(slotNumber) != null) {
            return null;
        }

        Slot slot = new CustomSlot(inventory, index);
        slot.slotNumber = slotNumber;
        inventorySlots.add(slotNumber, slot);
        inventoryItemStacks.add(slotNumber, null);
        return slot;
    }
}
