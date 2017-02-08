package binnie.core.machines.inventory;

import binnie.Binnie;
import binnie.core.BinnieCore;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nullable;

public class InventorySlot extends BaseSlot<ItemStack> {
	@Nullable
	private ItemStack itemStack;
	private Type type;

	public InventorySlot(final int index, final String unlocName) {
		super(index, unlocName);
		this.itemStack = null;
		this.type = Type.Standard;
	}

	@Override
	@Nullable
	public ItemStack getContent() {
		return this.itemStack;
	}

	@Nullable
	public ItemStack getItemStack() {
		return this.getContent();
	}

	@Override
	public void setContent(@Nullable final ItemStack itemStack) {
		this.itemStack = itemStack;
	}

	@Nullable
	public ItemStack decrStackSize(final int amount) {
		if (this.itemStack == null) {
			return null;
		}
		if (this.itemStack.getCount() <= amount) {
			final ItemStack returnStack = this.itemStack.copy();
			this.itemStack = null;
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
			this.itemStack = null;
		}
	}

	@Override
	public NBTTagCompound writeToNBT(final NBTTagCompound slotNBT) {
		final NBTTagCompound itemNBT = new NBTTagCompound();
		if (this.itemStack != null) {
			this.itemStack.writeToNBT(itemNBT);
		}
		slotNBT.setTag("item", itemNBT);
		return slotNBT;
	}

	public void setItemStack(final ItemStack duplicate) {
		this.setContent(duplicate);
	}

	@Override
	public SlotValidator getValidator() {
		return (SlotValidator) this.validator;
	}

	public void setType(final Type type) {
		this.type = type;
		if (type == Type.Recipe) {
			this.setReadOnly();
			this.forbidInteraction();
		}
	}

	public Type getType() {
		return this.type;
	}

	public boolean isRecipe() {
		return this.type == Type.Recipe;
	}

	@Override
	public String getName() {
		return Binnie.LANGUAGE.localise(BinnieCore.getInstance(), "gui.slot." + this.unlocName);
	}

	public enum Type {
		Standard,
		Recipe;
	}
}
