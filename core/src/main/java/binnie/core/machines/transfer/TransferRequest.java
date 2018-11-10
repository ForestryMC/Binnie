package binnie.core.machines.transfer;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.wrapper.InvWrapper;

import binnie.core.machines.inventory.IInventorySlots;
import binnie.core.machines.inventory.InventorySlot;
import binnie.core.machines.power.ITankMachine;

public class TransferRequest {
	private ItemStack returnItem;
	@Nullable
	private IInventory origin;
	@Nullable
	private IInventory destination;
	private int[] targetSlots;
	private int[] targetTanks;
	private boolean transferLiquids;
	private boolean ignoreReadOnly;
	private final List<TransferSlot> insertedSlots;

	public TransferRequest(final ItemStack toTransfer, final IInventory destination) {
		this.returnItem = ItemStack.EMPTY;
		this.targetSlots = new int[0];
		this.targetTanks = new int[0];
		this.transferLiquids = true;
		this.ignoreReadOnly = false;
		this.insertedSlots = new ArrayList<>();
		final int[] target = new int[destination.getSizeInventory()];
		for (int i = 0; i < target.length; ++i) {
			target[i] = i;
		}
		int[] targetTanks = new int[0];
		if (destination instanceof ITankMachine) {
			targetTanks = new int[((ITankMachine) destination).getTanks().length];
			for (int j = 0; j < targetTanks.length; ++j) {
				targetTanks[j] = j;
			}
		}
		if (!toTransfer.isEmpty()) {
			this.setReturnItem(toTransfer.copy());
		}
		this.setOrigin(null);
		this.setDestination(destination);
		this.setTargetSlots(target);
		this.setTargetTanks(targetTanks);
	}

	private static boolean areItemsEqual(final ItemStack merged, final ItemStack itemstack) {
		return ItemStack.areItemStackTagsEqual(itemstack, merged) && itemstack.isItemEqual(merged);
	}

	public static NonNullList<ItemStack> mergeStacks(ItemStack itemstack, final ItemStack merged) {
		if (areItemsEqual(itemstack, merged)) {
			final int space = merged.getMaxStackSize() - merged.getCount();
			if (space > 0) {
				if (itemstack.getCount() > space) {
					itemstack.shrink(space);
					merged.grow(space);
				} else if (itemstack.getCount() <= space) {
					merged.grow(itemstack.getCount());
					itemstack = ItemStack.EMPTY;
				}
			}
		}
		NonNullList<ItemStack> result = NonNullList.create();
		result.add(itemstack);
		result.add(merged);
		return result;
	}

	public TransferRequest ignoreValidation() {
		this.ignoreReadOnly = true;
		return this;
	}

	private void setReturnItem(final ItemStack returnItem) {
		this.returnItem = returnItem;
	}

	public TransferResult transfer(@Nullable EntityPlayer player, boolean doAdd) {
		ItemStack item = this.returnItem;
		if (item.isEmpty() || this.destination == null) {
			return TransferResult.FAILURE;
		}
		if (this.transferLiquids &&
			this.destination instanceof ITankMachine &&
			item.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) {
			ITankMachine tankMachine = (ITankMachine) this.destination;
			IFluidHandler fluidHandler = tankMachine.getHandler(targetTanks);
			if (fluidHandler != null) {
				ItemStack singleCopy = ItemHandlerHelper.copyStackWithSize(item, 1);
				FluidActionResult fluidActionResult;
				if (this.origin != null && doAdd) {
					IItemHandler itemHandler = new InvWrapper(this.origin);
					fluidActionResult = FluidUtil.tryEmptyContainerAndStow(singleCopy, fluidHandler, itemHandler, Fluid.BUCKET_VOLUME, player, doAdd);
					if (!fluidActionResult.isSuccess()) {
						fluidActionResult = FluidUtil.tryFillContainerAndStow(singleCopy, fluidHandler, itemHandler, Fluid.BUCKET_VOLUME, player, doAdd);
					}
				} else {
					fluidActionResult = FluidUtil.tryEmptyContainer(singleCopy, fluidHandler, Fluid.BUCKET_VOLUME, player, doAdd);
					if (!fluidActionResult.isSuccess()) {
						fluidActionResult = FluidUtil.tryFillContainer(singleCopy, fluidHandler, Fluid.BUCKET_VOLUME, player, doAdd);
					}
				}
				if (fluidActionResult.isSuccess()) {
					if (item.getCount() == 1) {
						return new TransferResult(fluidActionResult.result);
					} else {
						ItemStack itemCopy = item.copy();
						itemCopy.shrink(1);
						return new TransferResult(fluidActionResult.result, itemCopy);
					}
				}
				return TransferResult.FAILURE;
			}
		}
		if (!item.isEmpty()) {
			for (final int slot : this.targetSlots) {
				if (this.destination.isItemValidForSlot(slot, item) || this.ignoreReadOnly) {
					if (this.destination instanceof IInventorySlots) {
						InventorySlot inventorySlot = ((IInventorySlots) this.destination).getSlot(slot);
						if (inventorySlot != null && inventorySlot.isRecipe()) {
							continue;
						}
					}
					ItemStack stackInSlot = this.destination.getStackInSlot(slot);
					if (!stackInSlot.isEmpty()) {
						if (item.isStackable()) {
							final ItemStack merged = stackInSlot.copy();
							final NonNullList<ItemStack> newStacks = mergeStacks(item.copy(), merged.copy());
							item = newStacks.get(0);
							if (!areItemsEqual(merged, newStacks.get(1))) {
								this.insertedSlots.add(new TransferSlot(slot, this.destination));
							}
							if (doAdd) {
								this.destination.setInventorySlotContents(slot, newStacks.get(1));
							}
							if (item.isEmpty()) {
								return new TransferResult(ItemStack.EMPTY);
							}
						}
					}
				}
			}
		}
		if (!item.isEmpty()) {
			for (final int slot : this.targetSlots) {
				if (this.destination.isItemValidForSlot(slot, item) || this.ignoreReadOnly) {
					if (this.destination instanceof IInventorySlots) {
						InventorySlot inventorySlot = ((IInventorySlots) this.destination).getSlot(slot);
						if (inventorySlot != null && inventorySlot.isRecipe()) {
							continue;
						}
					}
					if (this.destination.getStackInSlot(slot).isEmpty()) {
						this.insertedSlots.add(new TransferSlot(slot, this.destination));
						if (doAdd) {
							this.destination.setInventorySlotContents(slot, item.copy());
						}
						return new TransferResult(ItemStack.EMPTY);
					}
				}
			}
		}
		this.setReturnItem(item);
		return new TransferResult(item);
	}

	public List<TransferSlot> getInsertedSlots() {
		return this.insertedSlots;
	}

	@Nullable
	public IInventory getOrigin() {
		return this.origin;
	}

	public TransferRequest setOrigin(@Nullable final IInventory origin) {
		this.origin = origin;
		return this;
	}

	@Nullable
	public IInventory getDestination() {
		return this.destination;
	}

	private void setDestination(final IInventory destination) {
		this.destination = destination;
	}

	public TransferRequest setTargetSlots(final int[] targetSlots) {
		this.targetSlots = targetSlots;
		return this;
	}

	public TransferRequest setTargetTanks(final int... targetTanks) {
		this.targetTanks = targetTanks;
		return this;
	}

	public static class TransferSlot {
		private final int id;
		private final IInventory inventory;

		public TransferSlot(final int id, final IInventory inventory) {
			this.id = id;
			this.inventory = inventory;
		}

		public int getId() {
			return id;
		}

		public IInventory getInventory() {
			return inventory;
		}
	}
}
