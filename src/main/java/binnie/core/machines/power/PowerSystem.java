package binnie.core.machines.power;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public enum PowerSystem {
    MJ(100.0),
    RF(10.0),
    EU(40.0);

    double conversion;

    private PowerSystem(final double conversion) {
        this.conversion = conversion;
    }

    public double convertTo(final int value) {
        return value / this.conversion;
    }

    public int convertFrom(final double value) {
        return (int) (value * this.conversion);
    }

    public static PowerSystem get(final int i) {
        return values()[i % values().length];
    }

    public String getUnitName() {
        return this.name();
    }

    public ItemStack saveTo(final ItemStack stack) {
        final NBTTagCompound tag = stack.hasTagCompound() ? stack.getTagCompound() : new NBTTagCompound();
        tag.setByte("power-system", (byte) this.ordinal());
        stack.setTagCompound(tag);
        return stack;
    }
}
