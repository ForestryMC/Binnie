package binnie.core.machines.transfer;

import binnie.core.machines.Machine;
import binnie.core.machines.inventory.IInventorySlots;
import binnie.core.machines.inventory.IValidatedTankContainer;
import binnie.core.machines.inventory.InventorySlot;
import binnie.core.machines.power.ITankMachine;
import com.google.common.base.Preconditions;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class TransferRequest {
	private ItemStack itemToTransfer;
	private ItemStack returnItem;
	@Nullable
	private IInventory origin;
	@Nullable
	private IInventory destination;
	private int[] targetSlots;
	private int[] targetTanks;
	private boolean transferLiquids;
	private boolean ignoreReadOnly;
	private List<TransferSlot> insertedSlots;
	private List<Integer> insertedTanks;

	public TransferRequest(final ItemStack toTransfer, final IInventory destination) {
		this.itemToTransfer = ItemStack.EMPTY;
		this.returnItem = ItemStack.EMPTY;
		this.targetSlots = new int[0];
		this.targetTanks = new int[0];
		this.transferLiquids = true;
		this.ignoreReadOnly = false;
		this.insertedSlots = new ArrayList<>();
		this.insertedTanks = new ArrayList<>();
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
			this.setItemToTransfer(toTransfer.copy());
			this.setReturnItem(toTransfer.copy());
		}
		this.setOrigin(null);
		this.setDestination(destination);
		this.setTargetSlots(target);
		this.setTargetTanks(targetTanks);
		this.transferLiquids = true;
	}

	public static ItemStack transferItemToInventory(final ItemStack item, final IInventory destination, final boolean doAdd) {
		ItemStack addition = item.copy();
		for (int i = 0; i < destination.getSizeInventory(); ++i) {
			addition = transferToInventory(addition, destination, new int[]{i}, doAdd, false);
			if (addition.isEmpty()) {
				return ItemStack.EMPTY;
			}
		}
		return addition;
	}

	public static ItemStack transferToInventory(ItemStack item, final IInventory destination, final int[] targetSlots, final boolean doAdd, final boolean ignoreValidation) {
		ItemStack returnItem = item;
		for (final int i : targetSlots) {
			if (destination.isItemValidForSlot(i, returnItem) || ignoreValidation) {
				ItemStack stackInSlot = destination.getStackInSlot(i);
				if (stackInSlot.isEmpty()) {
					if (doAdd) {
						destination.setInventorySlotContents(i, returnItem.copy());
					}
					return ItemStack.EMPTY;
				}
				if (returnItem.isStackable()) {
					final ItemStack merged = stackInSlot.copy();
					final NonNullList<ItemStack> newStacks = mergeStacks(returnItem.copy(), merged.copy());
					returnItem = newStacks.get(0);
					if (doAdd) {
						destination.setInventorySlotContents(i, newStacks.get(1));
					}
				}
			}
		}
		return returnItem;
	}

	private void setItemToTransfer(final ItemStack itemToTransfer) {
		this.itemToTransfer = itemToTransfer;
	}

	private void setReturnItem(final ItemStack returnItem) {
		this.returnItem = returnItem;
	}

	public TransferRequest setOrigin(@Nullable final IInventory origin) {
		this.origin = origin;
		return this;
	}

	private void setDestination(final IInventory destination) {
		this.destination = destination;
	}

	public TransferRequest setTargetSlots(final int[] targetSlots) {
		this.targetSlots = targetSlots;
		return this;
	}

	public TransferRequest setTargetTanks(final int[] targetTanks) {
		this.targetTanks = targetTanks;
		return this;
	}

	public TransferRequest ignoreValidation() {
		this.ignoreReadOnly = true;
		return this;
	}

	public ItemStack getReturnItem() {
		return this.returnItem;
	}

	public ItemStack transfer(final boolean doAdd) {
		ItemStack item = this.returnItem;
		if (item.isEmpty() || this.destination == null) {
			return ItemStack.EMPTY;
		}
		if (this.transferLiquids && this.destination instanceof ITankMachine) {
			if(origin == null){
				return ItemStack.EMPTY;
			}
			ItemStack itemIn = item.copy();
			for (final int tankID : this.targetTanks) {
				item = transferToTank(item, this.origin, (ITankMachine) this.destination, tankID, doAdd);
				if (item.isEmpty() || !ItemStack.areItemStacksEqual(item, itemIn)) {
					break;
				}

				item = transferFromTank(item, this.origin, (ITankMachine) this.destination, tankID, doAdd);
				if (item.isEmpty() || !ItemStack.areItemStacksEqual(item, itemIn)) {
					break;
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
								return ItemStack.EMPTY;
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
						return ItemStack.EMPTY;
					}
				}
			}
		}
		this.setReturnItem(item);
		return this.getReturnItem();
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
					itemstack = null;
				}
			}
		}
		NonNullList<ItemStack> result = NonNullList.create();
		result.add(itemstack);
		result.add(merged);
		return result;
	}

	public static ItemStack transferToTank(final ItemStack itemStack, final IInventory origin, final ITankMachine destination, final int tankID, final boolean doAdd) {
		Preconditions.checkNotNull(itemStack);
		Preconditions.checkArgument(itemStack.getCount() >= 1);

		final ItemStack singleCopy = itemStack.copy();
		singleCopy.setCount(1);

		final IFluidHandlerItem fluidHandler = FluidUtil.getFluidHandler(singleCopy);
		if (fluidHandler != null) {

			final FluidStack containerLiquid = fluidHandler.drain(Integer.MAX_VALUE, false);
			if (containerLiquid != null && containerLiquid.amount > 0) {

				final IFluidTank tank = destination.getTanks()[tankID];
				final IValidatedTankContainer validated = Machine.getInterface(IValidatedTankContainer.class, destination);
				if (validated == null || validated.isLiquidValidForTank(containerLiquid, tankID)) {

					final int amountAdded = tank.fill(containerLiquid, false);

					fluidHandler.drain(amountAdded, true);
					ItemStack drainedContainer = fluidHandler.getContainer();

					if (drainedContainer.isEmpty() || transferItemToInventory(singleCopy, origin, false).isEmpty()) {
						if (doAdd) {
							tank.fill(containerLiquid, true);
							if (!drainedContainer.isEmpty()) {
								transferItemToInventory(drainedContainer, origin, true);
							}
						}

						final ItemStack leftover = itemStack.copy();
						leftover.shrink(1);
						return leftover;
					}
				}
			}
		}
		return itemStack;
	}

	private static ItemStack transferFromTank(ItemStack itemStack, final IInventory origin, final ITankMachine destination, final int tankID, final boolean doAdd) {
		Preconditions.checkNotNull(itemStack);
		Preconditions.checkArgument(itemStack.getCount() >= 1);

		final IFluidTank tank = destination.getTanks()[tankID];
		final FluidStack fluid = tank.getFluid();
		if (fluid != null) {
			final ItemStack singleCopy = itemStack.copy();
			singleCopy.setCount(1);
			IFluidHandlerItem fluidHandler = FluidUtil.getFluidHandler(singleCopy);
			if (fluidHandler != null) {
				final int fillAmount = fluidHandler.fill(fluid, true);
				if (fillAmount > 0) {
					final FluidStack fillFluid = tank.drain(fillAmount, false);
					if (fillFluid != null) {
						fluidHandler.fill(fillFluid, true);
						ItemStack filledContainer = fluidHandler.getContainer();
						if (filledContainer.isEmpty() || transferItemToInventory(singleCopy, origin, false).isEmpty()) {
							if (doAdd) {
								tank.drain(fillFluid.amount, true);
								if (!filledContainer.isEmpty()) {
									transferItemToInventory(filledContainer, origin, true);
								}
							}

							ItemStack leftover = itemStack.copy();
							leftover.shrink(1);
							return leftover;
						}
					}
				}
			}
		}

		return itemStack;
	}

	public List<TransferSlot> getInsertedSlots() {
		return this.insertedSlots;
	}

	public List<Integer> getInsertedTanks() {
		return this.insertedTanks;
	}

	@Nullable
	public IInventory getOrigin() {
		return this.origin;
	}

	@Nullable
	public IInventory getDestination() {
		return this.destination;
	}

	public ItemStack getItemToTransfer() {
		return this.itemToTransfer;
	}

	public int[] getTargetSlots() {
		return this.targetSlots;
	}

	public int[] getTargetTanks() {
		return this.targetTanks;
	}

	public static class TransferSlot {
		public int id;
		public IInventory inventory;

		public TransferSlot(final int id, final IInventory inventory) {
			this.id = id;
			this.inventory = inventory;
		}
	}
}
