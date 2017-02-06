package binnie.core.machines.inventory;

import binnie.core.machines.IMachine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.transfer.TransferRequest;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ComponentInventoryTransfer extends MachineComponent {
	private List<Transfer> transfers;

	public ComponentInventoryTransfer(final IMachine machine) {
		super(machine);
		this.transfers = new ArrayList<>();
	}

	public void addRestock(final int[] buffer, final int destination, final int limit) {
		Restock restock = new Restock(this.getMachine(), buffer, destination, limit);
		this.transfers.add(restock);
	}

	public void addRestock(final int[] buffer, final int destination) {
		Restock restock = new Restock(this.getMachine(), buffer, destination);
		this.transfers.add(restock);
	}

	public void addStorage(final int source, final int[] destination) {
		Storage storage = new Storage(this.getMachine(), source, destination);
		this.transfers.add(storage);
	}

	public void performTransfer(final int source, final int[] destination) {
		IInventoryMachine inventoryMachine = this.getMachine().getInterface(IInventoryMachine.class);
		new Storage(this.getMachine(), source, destination).transfer(inventoryMachine);
	}

	@Override
	public void onUpdate() {
		for (final Transfer transfer : this.transfers) {
			transfer.transfer(this.getMachine().getInterface(IInventoryMachine.class));
		}
	}

	public void addStorage(final int source, final int[] destination, final Condition condition) {
		Storage storage = new Storage(this.getMachine(), source, destination);
		Transfer transfer = storage.setCondition(condition);
		this.transfers.add(transfer);
	}

	public abstract class Transfer {
		@Nullable
		protected Condition condition;
		protected final IMachine machine;

		private Transfer(final IMachine machine) {
			this.machine = machine;
		}

		public final void transfer(final IInventory inv) {
			if (this.condition == null || this.fulfilled(inv)) {
				this.doTransfer(inv);
			}
		}

		protected boolean fulfilled(final IInventory inv) {
			return true;
		}

		protected void doTransfer(final IInventory inv) {
		}

		public final Transfer setCondition(final Condition condition) {
			this.condition = condition;
			return condition.transfer = this;
		}

		public final IMachine getMachine() {
			return this.machine;
		}
	}

	private class Restock extends Transfer {
		int[] buffer;
		int destination;
		int limit;

		private Restock(final IMachine machine, final int[] buffer, final int destination, final int limit) {
			super(machine);
			this.buffer = buffer;
			this.destination = destination;
			this.limit = limit;
		}

		private Restock(final IMachine machine, final int[] buffer, final int destination) {
			this(machine, buffer, destination, 64);
		}

		@Override
		protected void doTransfer(final IInventory inv) {
			if (inv.getStackInSlot(this.destination) == null) {
				for (final int i : this.buffer) {
					if (inv.getStackInSlot(i) != null) {
						final ItemStack newStack = inv.decrStackSize(i, this.limit);
						if (newStack != null) {
							inv.setInventorySlotContents(this.destination, newStack);
							return;
						}
					}
				}
			}
		}
	}

	private class Storage extends Transfer {
		int source;
		int[] destination;

		private Storage(final IMachine machine, final int source, final int[] destination) {
			super(machine);
			this.source = source;
			this.destination = destination;
		}

		@Override
		protected void doTransfer(final IInventory inv) {
			ItemStack stackInSlot = inv.getStackInSlot(this.source);
			if (stackInSlot != null) {
				TransferRequest transferRequest = new TransferRequest(stackInSlot, inv).setTargetSlots(this.destination).ignoreValidation();
				ItemStack transfer = transferRequest.transfer(true);
				inv.setInventorySlotContents(this.source, transfer);
			}
		}

		@Override
		protected boolean fulfilled(final IInventory inv) {
			final ItemStack stack = inv.getStackInSlot(this.source);
			return stack != null && (this.condition == null || this.condition.fulfilled(stack));
		}
	}

	public abstract static class Condition {
		@Nullable
		public Transfer transfer;

		public abstract boolean fulfilled(final ItemStack p0);
	}
}
