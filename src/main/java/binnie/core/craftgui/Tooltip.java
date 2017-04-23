// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class Tooltip
{
	List<String> tooltip;
	ITooltipType type;
	public int maxWidth;

	public Tooltip() {
		tooltip = new ArrayList<String>();
		type = Type.Standard;
		maxWidth = 256;
	}

	public void add(final String string) {
		tooltip.add(string);
	}

	public String getLine(final int index) {
		final String string = getList().get(index);
		return string;
	}

	public void add(final List list) {
		for (final Object obj : list) {
			tooltip.add((String) obj);
		}
	}

	public List<String> getList() {
		return tooltip;
	}

	public boolean exists() {
		return tooltip.size() > 0;
	}

	public void setType(final ITooltipType type) {
		this.type = type;
	}

	public void setMaxWidth(final int w) {
		maxWidth = w;
	}

	public ITooltipType getType() {
		return type;
	}

	public void add(final ItemStack item, final String string) {
		final NBTTagCompound nbt = new NBTTagCompound();
		item.writeToNBT(nbt);
		nbt.setByte("nbt-type", (byte) 105);
		add("~~~" + nbt.toString() + "~~~" + string);
	}

	public void add(final FluidStack item, final String string) {
		final NBTTagCompound nbt = new NBTTagCompound();
		item.writeToNBT(nbt);
		nbt.setByte("nbt-type", (byte) 102);
		add("~~~" + nbt.toString() + "~~~" + string);
	}

	public enum Type implements ITooltipType
	{
		Standard,
		Help,
		Information,
		User,
		Power
	}

	public interface ITooltipType
	{
	}
}
