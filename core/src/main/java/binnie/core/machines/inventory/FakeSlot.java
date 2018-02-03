package binnie.core.machines.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public final class FakeSlot extends InventorySlot {
	public static final FakeSlot INSTANCE = new FakeSlot();

	private FakeSlot() {
		super(0, null);
	}

	@Override
	public boolean isFake() {
		return true;
	}

	@Override
	public void setType(Type type) {
	}

	@Override
	public void setContent(ItemStack itemStack) {
	}

	@Override
	public ItemStack decrStackSize(int amount) {
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack getContent() {
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack getItemStack() {
		return ItemStack.EMPTY;
	}

	@Override
	public String getName() {
		return "Fake Slot!";
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound slotNBT) {
		return slotNBT;
	}

	@Override
	public void readFromNBT(NBTTagCompound slotNBT) {
	}
}
