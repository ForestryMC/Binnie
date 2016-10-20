package binnie.craftgui.core;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class Tooltip {
    List<String> tooltip;
    ITooltipType type;
    public int maxWidth;

    public Tooltip() {
        this.tooltip = new ArrayList<String>();
        this.type = Type.Standard;
        this.maxWidth = 256;
    }

    public void add(final String string) {
        this.tooltip.add(string);
    }

    public String getLine(final int index) {
        final String string = this.getList().get(index);
        return string;
    }

    public void add(final List list) {
        for (final Object obj : list) {
            this.tooltip.add((String) obj);
        }
    }

    public List<String> getList() {
        return this.tooltip;
    }

    public boolean exists() {
        return this.tooltip.size() > 0;
    }

    public void setType(final ITooltipType type) {
        this.type = type;
    }

    public void setMaxWidth(final int w) {
        this.maxWidth = w;
    }

    public ITooltipType getType() {
        return this.type;
    }

    public void add(final ItemStack item, final String string) {
        final NBTTagCompound nbt = new NBTTagCompound();
        item.writeToNBT(nbt);
        nbt.setByte("nbt-type", (byte) 105);
        this.add("~~~" + nbt.toString() + "~~~" + string);
    }

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
        Power;
    }

    public interface ITooltipType {
    }
}
