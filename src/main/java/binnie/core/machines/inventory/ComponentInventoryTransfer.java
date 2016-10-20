// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.machines.inventory;

import binnie.core.machines.transfer.TransferRequest;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.IInventory;
import java.util.ArrayList;
import binnie.core.machines.IMachine;
import java.util.List;
import binnie.core.machines.MachineComponent;

public class ComponentInventoryTransfer extends MachineComponent
{
	private List<Transfer> transfers;

	public ComponentInventoryTransfer(final IMachine machine) {
		super(machine);
		this.transfers = new ArrayList<Transfer>();
	}

	public void addRestock(final int[] buffer, final int destination, final int limit) {
		this.transfers.add(new Restock(this.getMachine(), buffer, destination, limit));
	}

	public void addRestock(final int[] buffer, final int destination) {
		this.transfers.add(new Restock(this.getMachine(), buffer, destination));
	}

	public void addStorage(final int source, final int[] destination) {
		this.transfers.add(new Storage(this.getMachine(), source, destination));
	}

	public void performTransfer(final int source, final int[] destination) {
		new Storage(this.getMachine(), source, destination).transfer(this.getMachine().getInterface(IInventoryMachine.class));
	}

	@Override
	public void onUpdate() {
		for (final Transfer transfer : this.transfers) {
			transfer.transfer(this.getMachine().getInterface(IInventoryMachine.class));
		}
	}

	public void addStorage(final int source, final int[] destination, final Condition condition) {
		this.transfers.add(new Storage(this.getMachine(), source, destination).setCondition(condition));
	}

	public abstract class Transfer
	{
		protected Condition condition;
		protected IMachine machine;

		private Transfer(final IMachine machine) {
			this.machine = machine;
		}

		public final void transfer(final IInventory inv) {
			if (this.condition == null || this.fufilled(inv)) {
				this.doTransfer(inv);
			}
		}

		protected boolean fufilled(final IInventory inv) {
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

	private class Restock extends Transfer
	{
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

	private class Storage extends Transfer
	{
		int source;
		int[] destination;

		private Storage(final IMachine machine, final int source, final int[] destination) {
			super(machine);
			this.source = source;
			this.destination = destination;
		}

		@Override
		protected void doTransfer(final IInventory inv) {
			if (inv.getStackInSlot(this.source) != null) {
				inv.setInventorySlotContents(this.source, new TransferRequest(inv.getStackInSlot(this.source), inv).setTargetSlots(this.destination).ignoreValidation().transfer(true));
			}
		}

		@Override
		protected boolean fufilled(final IInventory inv) {
			final ItemStack stack = inv.getStackInSlot(this.source);
			return stack != null && this.condition.fufilled(stack);
		}
	}

	public abstract static class Condition
	{
		public Transfer transfer;

		public abstract boolean fufilled(final ItemStack p0);
	}
}
