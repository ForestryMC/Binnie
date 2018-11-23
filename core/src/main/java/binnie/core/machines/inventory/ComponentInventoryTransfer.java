package binnie.core.machines.inventory;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import binnie.core.machines.IMachine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.transfer.TransferRequest;
import binnie.core.machines.transfer.TransferResult;

public class ComponentInventoryTransfer extends MachineComponent {
	private final List<Transfer> transfers;

	public ComponentInventoryTransfer(IMachine machine) {
		super(machine);
		this.transfers = new ArrayList<>();
	}

	public void addRestock(int[] buffer, int destination, int limit) {
		Restock restock = new Restock(this.getMachine(), buffer, destination, limit);
		this.transfers.add(restock);
	}

	public void addRestock(int[] buffer, int destination) {
		Restock restock = new Restock(this.getMachine(), buffer, destination);
		this.transfers.add(restock);
	}

	public void addStorage(int source, int[] destination) {
		Storage storage = new Storage(this.getMachine(), source, destination);
		this.transfers.add(storage);
	}

	@Override
	public void onUpdate() {
		for (Transfer transfer : this.transfers) {
			transfer.transfer(this.getMachine().getInterface(IInventoryMachine.class));
		}
	}

	public void addStorage(int source, int[] destination, Condition condition) {
		Storage storage = new Storage(this.getMachine(), source, destination);
		Transfer transfer = storage.setCondition(condition);
		this.transfers.add(transfer);
	}

	@FunctionalInterface
	public interface Condition {
		boolean fulfilled(ItemStack itemStack);
	}

	public abstract static class Transfer {
		protected final IMachine machine;
		@Nullable
		protected Condition condition;

		private Transfer(IMachine machine) {
			this.machine = machine;
		}

		public final void transfer(IInventory inv) {
			if (this.condition == null || this.fulfilled(inv)) {
				this.doTransfer(inv);
			}
		}

		protected boolean fulfilled(IInventory inv) {
			return true;
		}

		protected void doTransfer(IInventory inv) {
		}

		public final Transfer setCondition(Condition condition) {
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

		private Restock(IMachine machine, int[] buffer, int destination, int limit) {
			super(machine);
			this.buffer = buffer;
			this.destination = destination;
			this.limit = limit;
		}

		private Restock(IMachine machine, int[] buffer, int destination) {
			this(machine, buffer, destination, 64);
		}

		@Override
		protected void doTransfer(IInventory inv) {
			if (inv.getStackInSlot(this.destination).isEmpty()) {
				for (int i : this.buffer) {
					if (!inv.getStackInSlot(i).isEmpty()) {
						ItemStack newStack = inv.decrStackSize(i, this.limit);
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

		private Storage(IMachine machine, int source, int[] destination) {
			super(machine);
			this.source = source;
			this.destination = destination;
		}

		@Override
		protected void doTransfer(IInventory inv) {
			ItemStack stackInSlot = inv.getStackInSlot(this.source);
			if (!stackInSlot.isEmpty()) {
				TransferRequest transferRequest = new TransferRequest(stackInSlot, inv).setTargetSlots(this.destination).ignoreValidation();
				TransferResult transferResult = transferRequest.transfer(null, true);
				if (transferResult.isSuccess()) {
					NonNullList<ItemStack> results = transferResult.getRemaining();
					if (results.size() == 1) {
						inv.setInventorySlotContents(this.source, results.get(0));
					}
				}
			}
		}

		@Override
		protected boolean fulfilled(IInventory inv) {
			ItemStack stack = inv.getStackInSlot(this.source);
			return !stack.isEmpty() && (this.condition == null || this.condition.fulfilled(stack));
		}
	}
}
