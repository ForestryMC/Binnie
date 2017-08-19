package binnie.core.machines.inventory;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import binnie.core.util.I18N;
import net.minecraft.util.ResourceLocation;

public class InventorySlot extends BaseSlot<ItemStack> {
	private ItemStack itemStack;
	private Type type;

	public InventorySlot(final int index, final ResourceLocation unlocLocation) {
		super(index, unlocLocation);
		this.itemStack = ItemStack.EMPTY;
		this.type = Type.Standard;
	}

	@Override
	@Nullable
	public ItemStack getContent() {
		return this.itemStack.isEmpty() ? null : this.itemStack;
	}

	public void setContent(final ItemStack itemStack) {
		this.itemStack = itemStack;
	}

	public ItemStack getItemStack() {
		return this.itemStack;
	}

	public ItemStack decrStackSize(final int amount) {
		if (this.itemStack.isEmpty()) {
			return ItemStack.EMPTY;
		}
		if (this.itemStack.getCount() <= amount) {
			final ItemStack returnStack = this.itemStack.copy();
			this.itemStack = ItemStack.EMPTY;
			return returnStack;
		}
		final ItemStack returnStack = this.itemStack.copy();
		final ItemStack itemStack = this.itemStack;
		itemStack.shrink(amount);
		returnStack.setCount(amount);
		return returnStack;
	}

	@Override
	public void readFromNBT(final NBTTagCompound slotNBT) {
		if (slotNBT.hasKey("item")) {
			final NBTTagCompound itemNBT = slotNBT.getCompoundTag("item");
			this.itemStack = new ItemStack(itemNBT);
		} else {
			this.itemStack = ItemStack.EMPTY;
		}
	}

	@Override
	public NBTTagCompound writeToNBT(final NBTTagCompound slotNBT) {
		final NBTTagCompound itemNBT = new NBTTagCompound();
		if (!this.itemStack.isEmpty()) {
			this.itemStack.writeToNBT(itemNBT);
		}
		slotNBT.setTag("item", itemNBT);
		return slotNBT;
	}

	@Override
	public SlotValidator getValidator() {
		return (SlotValidator) this.validator;
	}

	public void setType(final Type type) {
		this.type = type;
		if (type == Type.Recipe) {
			//this.setReadOnly();
			this.forbidInteraction();
		}
	}

	public boolean isRecipe() {
		return this.type == Type.Recipe;
	}

	@Override
	public String getName() {
		if (this.unlocLocation == null) {
			return "";
		}
		return I18N.localise(this.unlocLocation);
	}

	public enum Type {
		Standard,
		Recipe
	}
}
