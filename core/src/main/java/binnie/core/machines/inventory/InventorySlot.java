package binnie.core.machines.inventory;

import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import binnie.core.ModId;
import binnie.core.util.I18N;

public class InventorySlot extends BaseSlot<ItemStack> {
	private ItemStack content;
	private Type type;

	public InventorySlot(final int index, @Nullable final ResourceLocation unlocLocation) {
		super(index, unlocLocation);
		this.content = ItemStack.EMPTY;
		this.type = Type.Standard;
	}

	public boolean isFake(){
		return false;
	}

	@Override
	@Nonnull
	public ItemStack getContent() {
		return this.content.isEmpty() ? ItemStack.EMPTY : this.content;
	}

	public void setContent(final ItemStack itemStack) {
		this.content = itemStack;
	}

	public ItemStack getItemStack() {
		return this.content;
	}

	public ItemStack decrStackSize(final int amount) {
		if (this.content.isEmpty()) {
			return ItemStack.EMPTY;
		}
		if (this.content.getCount() <= amount) {
			ItemStack stack = this.content.copy();
			this.content = ItemStack.EMPTY;
			return stack;
		}
		 ItemStack stack = this.content.copy();
		content.shrink(amount);
		stack.setCount(amount);
		return stack;
	}

	@Override
	public void readFromNBT(final NBTTagCompound slotNBT) {
		if (slotNBT.hasKey("item")) {
			final NBTTagCompound itemNBT = slotNBT.getCompoundTag("item");
			this.content = new ItemStack(itemNBT);
		} else {
			this.content = ItemStack.EMPTY;
		}
	}

	@Override
	public NBTTagCompound writeToNBT(final NBTTagCompound slotNBT) {
		final NBTTagCompound itemNBT = new NBTTagCompound();
		if (!this.content.isEmpty()) {
			this.content.writeToNBT(itemNBT);
		}
		slotNBT.setTag("item", itemNBT);
		return slotNBT;
	}

	@Override
	public SlotValidator getValidator() {
		return (SlotValidator) super.getValidator();
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
		final SlotValidator validator = getValidator();
		if (validator != null) {
			return I18N.localise(ModId.CORE, "gui.slot.validated", validator.getTooltip());
		}
		if (this.unlocLocation == null) {
			return StringUtils.EMPTY;
		}
		return I18N.localise(this.unlocLocation);
	}

	public enum Type {
		Standard,
		Recipe
	}
}
