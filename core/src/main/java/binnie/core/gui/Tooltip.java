package binnie.core.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.fluids.FluidStack;

public class Tooltip {
	public static final String NBT_SEPARATOR = "~~~";
	public static final String NBT_TYPE_KEY = "nbt-type";
	public static final byte TYPE_ITEM = 105;
	public static final byte TYPE_FLUID = 102;

	private int maxWidth;
	private final List<String> tooltip;
	private ITooltipType type;
	private ItemStack itemStack = ItemStack.EMPTY;

	public Tooltip() {
		this.tooltip = new ArrayList<>();
		this.type = Type.STANDARD;
		this.maxWidth = 256;
	}

	/**
	 * Gets the itemStack seen by tooltip event handlers.
	 */
	public ItemStack getItemStack() {
		return itemStack;
	}

	/**
	 * Sets the itemStack seen by tooltip event handlers.
	 */
	public void setItemStack(ItemStack itemStack) {
		this.itemStack = itemStack;
	}

	public void add(String string) {
		this.tooltip.add(string);
	}

	public void add(List<String> list) {
		this.tooltip.addAll(list);
	}

	public List<String> getList() {
		return this.tooltip;
	}

	public boolean exists() {
		return this.tooltip.size() > 0;
	}

	public void setMaxWidth(int w) {
		this.maxWidth = w;
	}

	public ITooltipType getType() {
		return this.type;
	}

	public void setType(ITooltipType type) {
		this.type = type;
	}

	/**
	 * Add a tooltip that also displays an itemStack on the tooltip directly.
	 */
	public void add(ItemStack itemStack, String string) {
		NBTTagCompound nbt = new NBTTagCompound();
		itemStack.writeToNBT(nbt);
		nbt.setByte(NBT_TYPE_KEY, TYPE_ITEM);
		this.add(NBT_SEPARATOR + nbt.toString() + NBT_SEPARATOR + string);
	}

	/**
	 * Add a tooltip that also displays an fluidStack on the tooltip directly.
	 */
	public void add(FluidStack fluidStack, String string) {
		NBTTagCompound nbt = new NBTTagCompound();
		fluidStack.writeToNBT(nbt);
		nbt.setByte(NBT_TYPE_KEY, TYPE_FLUID);
		this.add(NBT_SEPARATOR + nbt.toString() + NBT_SEPARATOR + string);
	}

	public int getMaxWidth() {
		return maxWidth;
	}

	public enum Type implements ITooltipType {
		STANDARD,
		HELP,
		INFORMATION,
		USER,
		POWER
	}

	public interface ITooltipType {
	}
}
