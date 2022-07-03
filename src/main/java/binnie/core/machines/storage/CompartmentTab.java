package binnie.core.machines.storage;

import binnie.core.craftgui.minecraft.EnumColor;
import forestry.api.core.INBTTagable;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

class CompartmentTab implements INBTTagable {
    private String name;
    private ItemStack icon;
    private EnumColor color;
    private int id;

    public CompartmentTab(int id) {
        name = "";
        icon = new ItemStack(Items.paper);
        color = EnumColor.White;
        this.id = id;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        name = nbt.getString("name");
        icon = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("icon"));
        color = EnumColor.values()[nbt.getByte("color")];
        id = nbt.getByte("id");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        nbt.setString("name", name);
        NBTTagCompound n = new NBTTagCompound();
        icon.writeToNBT(n);
        nbt.setTag("icon", n);
        nbt.setByte("color", (byte) color.ordinal());
        nbt.setByte("id", (byte) id);
    }

    public String getName() {
        return name;
    }

    public ItemStack getIcon() {
        return icon;
    }

    public EnumColor getColor() {
        return color;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        if (name == null) {
            this.name = "";
        } else {
            this.name = name;
        }
    }

    public void setIcon(ItemStack icon) {
        this.icon = icon;
    }

    public void setColor(EnumColor color) {
        this.color = color;
    }
}
