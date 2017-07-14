package binnie.core.craftgui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.fluids.FluidStack;

public class Tooltip {
	public int maxWidth;
	List<String> tooltip;
	ITooltipType type;
	private ItemStack itemStack = ItemStack.EMPTY;

	public Tooltip() {
		this.tooltip = new ArrayList<>();
		this.type = Type.Standard;
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

	public void add(final String string) {
		this.tooltip.add(string);
	}

	public String getLine(final int index) {
		return this.getList().get(index);
	}

	public void add(final List<String> list) {
		this.tooltip.addAll(list);
	}

	public List<String> getList() {
		return this.tooltip;
	}

	public boolean exists() {
		return this.tooltip.size() > 0;
	}

	public void setMaxWidth(final int w) {
		this.maxWidth = w;
	}

	public ITooltipType getType() {
		return this.type;
	}

	public void setType(final ITooltipType type) {
		this.type = type;
	}

	/**
	 * Add a tooltip that also displays an itemStack on the tooltip directly.
	 */
	public void add(final ItemStack item, final String string) {
		final NBTTagCompound nbt = new NBTTagCompound();
		item.writeToNBT(nbt);
		nbt.setByte("nbt-type", (byte) 105);
		this.add("~~~" + nbt.toString() + "~~~" + string);
	}

	/**
	 * Add a tooltip that also displays an fluidStack on the tooltip directly.
	 */
	public void add(final FluidStack item, final String string) {
		final NBTTagCompound nbt = new NBTTagCompound();
		item.writeToNBT(nbt);
		nbt.setByte("nbt-type", (byte) 102);
		this.add("~~~" + nbt.toString() + "~~~" + string);
	}

	public enum Type implements ITooltipType {
		Standard,
		Help,
		Information,
		User,
		Power
	}

	public interface ITooltipType {
	}
}
