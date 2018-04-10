package binnie.core.machines.inventory;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import binnie.core.machines.transfer.TransferResult;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import binnie.core.machines.IMachine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.transfer.TransferRequest;
import net.minecraft.util.NonNullList;

public class ComponentInventoryTransfer extends MachineComponent {
	private final List<Transfer> transfers;

	public ComponentInventoryTransfer(final IMachine machine) {
		super(machine);
		this.transfers = new ArrayList<>();
	}

	public void addRestock(final int[] buffer, final int destination, final int limit) {
		final Restock restock = new Restock(this.getMachine(), buffer, destination, limit);
		this.transfers.add(restock);
	}

	public void addRestock(final int[] buffer, final int destination) {
		final Restock restock = new Restock(this.getMachine(), buffer, destination);
		this.transfers.add(restock);
	}

	public void addStorage(final int source, final int[] destination) {
		final Storage storage = new Storage(this.getMachine(), source, destination);
		this.transfers.add(storage);
	}

	@Override
	public void onUpdate() {
		for (final Transfer transfer : this.transfers) {
			transfer.transfer(this.getMachine().getInterface(IInventoryMachine.class));
		}
	}

	public void addStorage(final int source, final int[] destination, final Condition condition) {
		final Storage storage = new Storage(this.getMachine(), source, destination);
		final Transfer transfer = storage.setCondition(condition);
		this.transfers.add(transfer);
	}

	@FunctionalInterface
	public interface Condition {
		boolean fulfilled(final ItemStack itemStack);
	}

	public abstract static class Transfer {
		protected final IMachine machine;
		@Nullable
		protected Condition condition;

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
			return this;
		}

		public final IMachine getMachine() {
			return this.machine;
		}
	}

	private class Restock extends Transfer {
		private final int[] buffer;
		private final int destination;
		private final int limit;

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
			if (inv.getStackInSlot(this.destination).isEmpty()) {
				for (final int i : this.buffer) {
					if (!inv.getStackInSlot(i).isEmpty()) {
						final ItemStack newStack = inv.decrStackSize(i, this.limit);
						if (!newStack.isEmpty()) {
							inv.setInventorySlotContents(this.destination, newStack);
							return;
						}
					}
				}
			}
		}
	}

	private class Storage extends Transfer {
		private final int source;
		private final int[] destination;

		private Storage(final IMachine machine, final int source, final int[] destination) {
			super(machine);
			this.source = source;
			this.destination = destination;
		}

		@Override
		protected void doTransfer(final IInventory inv) {
			final ItemStack stackInSlot = inv.getStackInSlot(this.source);
			if (!stackInSlot.isEmpty()) {
				final TransferRequest transferRequest = new TransferRequest(stackInSlot, inv).setTargetSlots(this.destination).ignoreValidation();
				final TransferResult transferResult = transferRequest.transfer(null, true);
				if (transferResult.isSuccess()) {
					final NonNullList<ItemStack> results = transferResult.getRemaining();
					if (results.size() == 1) {
						inv.setInventorySlotContents(this.source, results.get(0));
					}
				}
			}
		}

		@Override
		protected boolean fulfilled(final IInventory inv) {
			final ItemStack stack = inv.getStackInSlot(this.source);
			return !stack.isEmpty() && (this.condition == null || this.condition.fulfilled(stack));
		}
	}
}
