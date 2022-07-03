package binnie.core.machines.inventory;

import binnie.core.util.I18N;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class InventorySlot extends BaseSlot<ItemStack> {
    private ItemStack itemStack;
    private Type type;

    public InventorySlot(int index, String unlocalizedName) {
        super(index, unlocalizedName);
        itemStack = null;
        type = Type.Standard;
    }

    @Override
    public ItemStack getContent() {
        return itemStack;
    }

    public ItemStack getItemStack() {
        return getContent();
    }

    @Override
    public void setContent(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStack decrStackSize(int amount) {
        if (itemStack == null) {
            return null;
        }

        if (itemStack.stackSize <= amount) {
            ItemStack returnStack = itemStack.copy();
            itemStack = null;
            return returnStack;
        }

        ItemStack returnStack = itemStack.copy();
        ItemStack itemStack = this.itemStack;
        itemStack.stackSize -= amount;
        returnStack.stackSize = amount;
        return returnStack;
    }

    @Override
    public void readFromNBT(NBTTagCompound slotNBT) {
        if (slotNBT.hasKey("item")) {
            NBTTagCompound itemNBT = slotNBT.getCompoundTag("item");
            itemStack = ItemStack.loadItemStackFromNBT(itemNBT);
        } else {
            itemStack = null;
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound slotNBT) {
        NBTTagCompound itemNBT = new NBTTagCompound();
        if (itemStack != null) {
            itemStack.writeToNBT(itemNBT);
        }
        slotNBT.setTag("item", itemNBT);
    }

    public void setItemStack(ItemStack duplicate) {
        setContent(duplicate);
    }

    @Override
    public SlotValidator getValidator() {
        return (SlotValidator) validator;
    }

    public void setType(Type type) {
        this.type = type;
        if (type == Type.Recipe) {
            setReadOnly();
            forbidInteraction();
        }
    }

    public Type getType() {
        return type;
    }

    public boolean isRecipe() {
        return type == Type.Recipe;
    }

    @Override
    public String getName() {
        return I18N.localise("binniecore.gui.slot." + unlocName);
    }

    public enum Type {
        Standard,
        Recipe
    }
}
