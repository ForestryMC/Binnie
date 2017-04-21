package binnie.core.machines.inventory;

import binnie.core.machines.IMachine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.transfer.TransferRequest;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ComponentInventoryTransfer extends MachineComponent {
	private List<Transfer> transfers = new ArrayList<>();

	public ComponentInventoryTransfer(IMachine machine) {
		super(machine);
	}

	public void addRestock(int[] buffer, int destination, int limit) {
		this.transfers.add(new Restock(getMachine(), buffer, destination, limit));
	}

	public void addRestock(int[] buffer, int destination) {
		this.transfers.add(new Restock(getMachine(), buffer, destination));
	}

	public void addStorage(int source, int[] destination) {
		this.transfers.add(new Storage(getMachine(), source, destination));
	}

	public void performTransfer(int source, int[] destination) {
		new Storage(getMachine(), source, destination).transfer(getMachine().getInterface(IInventoryMachine.class));
	}

	@Override
	public void onUpdate() {
		for (Transfer transfer : transfers) {
			transfer.transfer(getMachine().getInterface(IInventoryMachine.class));
		}
	}

	public void addStorage(int source, int[] destination, Condition condition) {
		this.transfers.add(new Storage(getMachine(), source, destination).setCondition(condition));
	}

	public abstract class Transfer {
		protected Condition condition;
		protected IMachine machine;

		private Transfer(IMachine machine) {
			this.machine = machine;
		}

		public void transfer(IInventory inv) {
			if (this.condition == null || fufilled(inv)) {
				this.doTransfer(inv);
			}
		}

		protected boolean fufilled(IInventory inv) {
			return true;
		}

		protected void doTransfer(IInventory inv) {
		}

		public Transfer setCondition(Condition condition) {
			this.condition = condition;
			return condition.transfer = this;
		}

		public IMachine getMachine() {
			return machine;
		}
	}

	private class Restock extends Transfer {
		private int[] buffer;
		private int destination;
		private int limit;

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
			if (inv.getStackInSlot(this.destination) == null) {
				for (int i : buffer) {
					if (inv.getStackInSlot(i) == null) {
						continue;
					}

					ItemStack newStack = inv.decrStackSize(i, limit);
					if (newStack != null) {
						inv.setInventorySlotContents(destination, newStack);
						return;
					}
				}
			}
		}
	}

	private class Storage extends Transfer {
		private int source;
		private int[] destination;

		private Storage(IMachine machine, int source, int[] destination) {
			super(machine);
			this.source = source;
			this.destination = destination;
		}

		@Override
		protected void doTransfer(IInventory inv) {
			if (inv.getStackInSlot(source) != null) {
				TransferRequest request = new TransferRequest(inv.getStackInSlot(source), inv);
				ItemStack itemStack = request.setTargetSlots(destination)
					.ignoreValidation()
					.transfer(true);
				inv.setInventorySlotContents(source, itemStack);
			}
		}

		@Override
		protected boolean fufilled(IInventory inv) {
			ItemStack stack = inv.getStackInSlot(source);
			return stack != null && condition.fufilled(stack);
		}
	}

	public abstract static class Condition {
		public Transfer transfer;

		public abstract boolean fufilled(ItemStack p0);
	}
}
