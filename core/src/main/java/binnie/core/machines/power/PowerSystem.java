package binnie.core.machines.power;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public enum PowerSystem {
	MJ(100.0),
	RF(10.0),
	EU(40.0);

	private final double conversion;

	PowerSystem(double conversion) {
		this.conversion = conversion;
	}

	public static PowerSystem get(int i) {
		return values()[i % values().length];
	}

	public double convertTo(int value) {
		return value / this.conversion;
	}

	public int convertFrom(double value) {
		return (int) (value * this.conversion);
	}

	public String getUnitName() {
		return this.name();
	}

	public ItemStack saveTo(ItemStack stack) {
		NBTTagCompound tag = stack.getTagCompound();
		if (tag == null) {
			tag = new NBTTagCompound();
		}
		tag.setByte("power-system", (byte) this.ordinal());
		stack.setTagCompound(tag);
		return stack;
	}
}
