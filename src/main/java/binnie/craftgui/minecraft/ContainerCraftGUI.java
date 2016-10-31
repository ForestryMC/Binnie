package binnie.craftgui.minecraft;

import binnie.core.BinnieCore;
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
import binnie.craftgui.minecraft.control.ControlSlot;
import binnie.craftgui.minecraft.control.EnumHighlighting;
import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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
	private final Set<EntityPlayer> crafters = Sets.newConcurrentHashSet();

	public ContainerCraftGUI(final Window window) {
		this.syncedNBT = new HashMap<>();
		this.sentNBT = new HashMap<>();
		this.syncedTanks = new HashMap<>();
		this.syncedPower = new PowerInfo();
		this.syncedProcess = new ProcessInfo();
		this.errorType = 0;
		this.error = null;
		this.mousedOverSlotNumber = -1;
		this.window = window;
		final IMachine machine = Machine.getMachine(window.getInventory());
		if (this.getSide() == Side.SERVER) {
			this.inventoryItemStacks = new ListMap();
			this.inventorySlots = new ListMap();
			if (machine != null) {
				final GameProfile user = machine.getOwner();
				if (user != null) {
					final NBTTagCompound nbt = new NBTTagCompound();
					nbt.setString("username", user.getName());
					this.sendNBTToClient("username", nbt);
				}
			}
		}
	}

	private Side getSide() {
		return this.window.isServer() ? Side.SERVER : Side.CLIENT;
	}

	@Override
	public Slot getSlot(final int par1) {
		if (par1 < 0 || par1 >= this.inventorySlots.size()) {
			return null;
		}
		return this.inventorySlots.get(par1);
	}

	@Override
	public void putStackInSlot(final int par1, final ItemStack par2ItemStack) {
		if (this.getSlot(par1) != null) {
			this.getSlot(par1).putStack(par2ItemStack);
		}
	}

	@Override
	public void putStacksInSlots(final ItemStack[] par1ArrayOfItemStack) {
		for (int i = 0; i < par1ArrayOfItemStack.length; ++i) {
			if (this.getSlot(i) != null) {
				this.getSlot(i).putStack(par1ArrayOfItemStack[i]);
			}
		}
	}

	@Override
	public void onContainerClosed(final EntityPlayer par1EntityPlayer) {
		this.crafters.remove(par1EntityPlayer);
		super.onContainerClosed(par1EntityPlayer);
		final WindowInventory inventory = this.window.getWindowInventory();
		for (int i = 0; i < inventory.getSizeInventory(); ++i) {
			if (inventory.dispenseOnClose(i)) {
				ItemStack stack = inventory.getStackInSlot(i);
				if (stack != null) {
					stack = new TransferRequest(stack, par1EntityPlayer.inventory).transfer(true);
					if (stack != null) {
						par1EntityPlayer.dropItem(stack, false);
					}
				}
			}
		}
	}


	@Nullable
	@Override
	public ItemStack slotClick(int slotNum, int dragType, ClickType clickType, EntityPlayer player) {
		final Slot slot = this.getSlot(slotNum);
		if (slot instanceof CustomSlot && ((CustomSlot) slot).handleClick()) {
			((CustomSlot) slot).onSlotClick(this, dragType, clickType, player);
			return player.inventory.getItemStack();
		}
		final ItemStack stack = super.slotClick(slotNum, dragType, clickType, player);
		return stack;
	}


	public void sendNBTToClient(final String key, final NBTTagCompound nbt) {
		this.syncedNBT.put(key, nbt);
	}

	@Override
	public boolean canInteractWith(final EntityPlayer var1) {
		if (var1 instanceof EntityPlayerMP) {
			crafters.add((EntityPlayerMP) var1);
			this.sentNBT.clear();
		}
		return true;
	}

	@Override
	public final ItemStack transferStackInSlot(final EntityPlayer player, final int slotID) {
		return this.shiftClick(player, slotID);
	}

	private ItemStack shiftClick(final EntityPlayer player, final int slotnumber) {
		final TransferRequest request = this.getShiftClickRequest(player, slotnumber);
		if (request == null) {
			return null;
		}
		final ItemStack itemstack = request.transfer(true);
		final Slot shiftClickedSlot = this.inventorySlots.get(slotnumber);
		shiftClickedSlot.putStack(itemstack);
		shiftClickedSlot.onSlotChanged();
		return null;
	}

	private TransferRequest getShiftClickRequest(final EntityPlayer player, final int slotnumber) {
		if (slotnumber < 0) {
			return null;
		}
		final Slot shiftClickedSlot = this.inventorySlots.get(slotnumber);
		ItemStack itemstack = null;
		if (shiftClickedSlot.getHasStack()) {
			ItemStack shiftClickedStack = shiftClickedSlot.getStack();
			if (shiftClickedStack != null) {
				itemstack = shiftClickedStack.copy();
			}
		}
		final IInventory playerInventory = player.inventory;
		final IInventory containerInventory = this.window.getInventory();
		final IInventory windowInventory = this.window.getWindowInventory();
		final IInventory fromPlayer = (containerInventory == null) ? windowInventory : containerInventory;
		final int[] target = new int[36];
		for (int i = 0; i < 36; ++i) {
			target[i] = i;
		}
		TransferRequest request;
		if (shiftClickedSlot.inventory == playerInventory) {
			request = new TransferRequest(itemstack, fromPlayer).setOrigin(shiftClickedSlot.inventory);
		} else {
			request = new TransferRequest(itemstack, playerInventory).setOrigin(shiftClickedSlot.inventory).setTargetSlots(target);
		}
		if (this.window instanceof IWindowAffectsShiftClick) {
			((IWindowAffectsShiftClick) this.window).alterRequest(request);
		}
		return request;
	}

	public final ItemStack tankClick(final EntityPlayer player, final int slotID) {
		if (player.inventory.getItemStack() == null) {
			return null;
		}
		ItemStack heldItem = player.inventory.getItemStack().copy();
		heldItem = new TransferRequest(heldItem, this.window.getInventory()).setOrigin(player.inventory).setTargetSlots(new int[0]).setTargetTanks(new int[]{slotID}).transfer(true);
		player.inventory.setItemStack(heldItem);
		if (player instanceof EntityPlayerMP) {
			((EntityPlayerMP) player).updateHeldItem();
		}
		return heldItem;
	}

	public boolean handleNBT(final Side side, final EntityPlayer player, final String name, final NBTTagCompound action) {
		if (side == Side.SERVER) {
			if (name.equals("tank-click")) {
				this.tankClick(player, action.getByte("id"));
			}
			if (name.equals("slot-reg")) {
				final int type = action.getByte("t");
				final int index = action.getShort("i");
				final int slotNumber = action.getShort("n");
				this.createServerSlot(InventoryType.values()[type % 4], index, slotNumber);
				for (IContainerListener listener : listeners) {
					listener.updateCraftingInventory(this, this.getInventory());
				}
			}
		}
		if (name.contains("tank-update")) {
			this.onTankUpdate(action);
		} else if (name.equals("power-update")) {
			this.onPowerUpdate(action);
		} else if (name.equals("process-update")) {
			this.onProcessUpdate(action);
		} else if (name.equals("error-update")) {
			this.onErrorUpdate(action);
		} else if (name.equals("mouse-over-slot")) {
			this.onMouseOverSlot(player, action);
		} else if (name.equals("shift-click-info")) {
			this.onRecieveShiftClickHighlights(player, action);
		}
		return false;
	}

	//TODO: Fix random NullPointerException
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		final ITankMachine tanks = Machine.getInterface(ITankMachine.class, this.window.getInventory());
		final IPoweredMachine powered = Machine.getInterface(IPoweredMachine.class, this.window.getInventory());
		final IErrorStateSource error = Machine.getInterface(IErrorStateSource.class, this.window.getInventory());
		final IProcess process = Machine.getInterface(IProcess.class, this.window.getInventory());
		if (tanks != null && this.window.isServer()) {
			for (int i = 0; i < tanks.getTankInfos().length; ++i) {
				final TankInfo tank = tanks.getTankInfos()[i];
				if (!this.getTankInfo(i).equals(tank)) {
					this.syncedNBT.put("tank-update-" + i, this.createTankNBT(i, tank));
					this.syncedTanks.put(i, tank);
				}
			}
		}
		if (powered != null && this.window.isServer()) {
			this.syncedNBT.put("power-update", this.createPowerNBT(powered.getPowerInfo()));
		}
		if (process != null && this.window.isServer()) {
			this.syncedNBT.put("process-update", this.createProcessNBT(process.getInfo()));
		}
		if (error != null && this.window.isServer()) {
			this.syncedNBT.put("error-update", this.createErrorNBT(error));
		}
		final INetwork.SendGuiNBT machineSync = Machine.getInterface(INetwork.SendGuiNBT.class, this.window.getInventory());
		if (machineSync != null) {
			machineSync.sendGuiNBT(this.syncedNBT);
		}
		final Map<String, NBTTagCompound> sentThisTime = new HashMap<>();
		for (final Map.Entry<String, NBTTagCompound> nbt : this.syncedNBT.entrySet()) {
			nbt.getValue().setString("type", nbt.getKey());
			boolean shouldSend = true;
			final NBTTagCompound lastSent = this.sentNBT.get(nbt.getKey());
			if (lastSent != null) {
				shouldSend = !lastSent.equals(nbt.getValue());
			}
			if (shouldSend) {
				//TODO INVENTORY
				this.crafters.stream().filter(Objects::nonNull).forEach(entityPlayer -> {
					BinnieCore.proxy.sendToPlayer(new MessageContainerUpdate(nbt.getValue()), entityPlayer);
				});
				sentThisTime.put(nbt.getKey(), nbt.getValue());
			}
		}
		this.sentNBT.putAll(sentThisTime);
		this.syncedNBT.clear();
	}

//FIXME hack

	/**
	 * sets whether the player can craft in this inventory or not
	 */
	@Override
	public void setCanCraft(EntityPlayer player, boolean canCraft) {
		super.setCanCraft(player, canCraft);
	}
//end


	private NBTTagCompound createErrorNBT(final IErrorStateSource error) {
		final NBTTagCompound nbt = new NBTTagCompound();
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

	public NBTTagCompound createPowerNBT(final PowerInfo powerInfo) {
		final NBTTagCompound nbt = new NBTTagCompound();
		powerInfo.writeToNBT(nbt);
		return nbt;
	}

	public NBTTagCompound createProcessNBT(final ProcessInfo powerInfo) {
		final NBTTagCompound nbt = new NBTTagCompound();
		powerInfo.writeToNBT(nbt);
		return nbt;
	}

	public NBTTagCompound createTankNBT(final int tank, final TankInfo tankInfo) {
		final NBTTagCompound nbt = new NBTTagCompound();
		tankInfo.writeToNBT(nbt);
		nbt.setByte("tank", (byte) tank);
		return nbt;
	}

	public void onTankUpdate(final NBTTagCompound nbt) {
		final int tankID = nbt.getByte("tank");
		final TankInfo tank = new TankInfo();
		tank.readFromNBT(nbt);
		this.syncedTanks.put(tankID, tank);
	}

	public void onProcessUpdate(final NBTTagCompound nbt) {
		(this.syncedProcess = new ProcessInfo()).readFromNBT(nbt);
	}

	public void onPowerUpdate(final NBTTagCompound nbt) {
		(this.syncedPower = new PowerInfo()).readFromNBT(nbt);
	}

	public PowerInfo getPowerInfo() {
		return this.syncedPower;
	}

	public ProcessInfo getProcessInfo() {
		return this.syncedProcess;
	}

	public TankInfo getTankInfo(final int tank) {
		return this.syncedTanks.containsKey(tank) ? this.syncedTanks.get(tank) : new TankInfo();
	}

	public void onErrorUpdate(final NBTTagCompound nbt) {
		this.errorType = nbt.getByte("type");
		if (nbt.hasKey("name")) {
			(this.error = new ErrorState("", "")).readFromNBT(nbt);
		} else {
			this.error = null;
		}
	}

	public ErrorState getErrorState() {
		return this.error;
	}

	public int getErrorType() {
		return this.errorType;
	}

	public CustomSlot[] getCustomSlots() {
		final List<CustomSlot> slots = new ArrayList<>();
		for (final Object object : this.inventorySlots) {
			if (object instanceof CustomSlot) {
				slots.add((CustomSlot) object);
			}
		}
		return slots.toArray(new CustomSlot[0]);
	}

	public void setMouseOverSlot(final Slot slot) {
		if (slot.slotNumber != this.mousedOverSlotNumber) {
			this.mousedOverSlotNumber = slot.slotNumber;
			ControlSlot.highlighting.get(EnumHighlighting.ShiftClick).clear();
			final NBTTagCompound nbt = new NBTTagCompound();
			nbt.setShort("slot", (short) slot.slotNumber);
			this.window.sendClientAction("mouse-over-slot", nbt);
		}
	}

	private void onMouseOverSlot(final EntityPlayer player, final NBTTagCompound data) {
		final int slotnumber = data.getShort("slot");
		final TransferRequest request = this.getShiftClickRequest(player, slotnumber);
		if (request == null) {
			return;
		}
		request.transfer(false);
		final NBTTagCompound nbt = new NBTTagCompound();
		final List<Integer> slots = new ArrayList<>();
		for (final TransferRequest.TransferSlot tslot : request.getInsertedSlots()) {
			final Slot slot = this.getSlot(tslot.inventory, tslot.id);
			if (slot != null) {
				slots.add(slot.slotNumber);
			}
		}
		final int[] array = new int[slots.size()];
		for (int i = 0; i < slots.size(); ++i) {
			array[i] = slots.get(i);
		}
		nbt.setIntArray("slots", array);
		nbt.setShort("origin", (short) slotnumber);
		this.syncedNBT.put("shift-click-info", nbt);
	}

	private void onRecieveShiftClickHighlights(final EntityPlayer player, final NBTTagCompound data) {
		ControlSlot.highlighting.get(EnumHighlighting.ShiftClick).clear();
		for (final int slotnumber : data.getIntArray("slots")) {
			ControlSlot.highlighting.get(EnumHighlighting.ShiftClick).add(slotnumber);
		}
	}

	private CustomSlot getSlot(final IInventory inventory, final int id) {
		for (final Object o : this.inventorySlots) {
			final CustomSlot slot = (CustomSlot) o;
			if (slot.inventory == inventory && slot.getSlotIndex() == id) {
				return slot;
			}
		}
		return null;
	}

	public void recieveNBT(final Side side, final EntityPlayer player, final NBTTagCompound action) {
		final String name = action.getString("type");
		if (this.handleNBT(side, player, name, action)) {
			return;
		}
		this.window.recieveGuiNBT(this.getSide(), player, name, action);
		final INetwork.RecieveGuiNBT machine = Machine.getInterface(INetwork.RecieveGuiNBT.class, this.window.getInventory());
		if (machine != null) {
			machine.recieveGuiNBT(this.getSide(), player, name, action);
		}
	}

	public Slot getOrCreateSlot(final InventoryType type, final int index) {
		final IInventory inventory = this.getInventory(type);
		Slot slot = this.getSlot(inventory, index);
		if (slot == null) {
			slot = new CustomSlot(inventory, index);
			this.addSlotToContainer(slot);
		}
		final NBTTagCompound nbt = new NBTTagCompound();
		nbt.setByte("t", (byte) type.ordinal());
		nbt.setShort("i", (short) index);
		nbt.setShort("n", (short) slot.slotNumber);
		this.window.sendClientAction("slot-reg", nbt);
		return slot;
	}

	protected IInventory getInventory(final InventoryType type) {
		if (type == InventoryType.Machine) {
			return this.window.getInventory();
		}
		if (type == InventoryType.Player) {
			return this.window.getPlayer().inventory;
		}
		if (type == InventoryType.Window) {
			return this.window.getWindowInventory();
		}
		return null;
	}

	private Slot createServerSlot(final InventoryType type, final int index, final int slotNumber) {
		final IInventory inventory = this.getInventory(type);
		if (this.inventorySlots.get(slotNumber) != null) {
			return null;
		}
		final Slot slot = new CustomSlot(inventory, index);
		slot.slotNumber = slotNumber;
		this.inventorySlots.add(slotNumber, slot);
		this.inventoryItemStacks.add(slotNumber, null);
		return slot;
	}
}
