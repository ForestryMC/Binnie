package binnie.core.craftgui;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

public class Tooltip {
    public int maxWidth;

    protected List<String> tooltip;
    protected ITooltipType type;

    public Tooltip() {
        tooltip = new ArrayList<>();
        type = Type.STANDARD;
        maxWidth = 256;
    }

    public void add(String string) {
        tooltip.add(string);
    }

    public void add(ItemStack item, String string) {
        NBTTagCompound nbt = new NBTTagCompound();
        item.writeToNBT(nbt);
        nbt.setByte("nbt-type", (byte) 105);
        add("~~~" + nbt.toString() + "~~~" + string);
    }

    public void add(FluidStack item, String string) {
        NBTTagCompound nbt = new NBTTagCompound();
        item.writeToNBT(nbt);
        nbt.setByte("nbt-type", (byte) 102);
        add("~~~" + nbt.toString() + "~~~" + string);
    }

    public boolean exists() {
        return tooltip.size() > 0;
    }

    public void add(List list) {
        for (Object obj : list) {
            tooltip.add((String) obj);
        }
    }

    public List<String> getList() {
        return tooltip;
    }

    public void setType(ITooltipType type) {
        this.type = type;
    }

    public void setMaxWidth(int w) {
        maxWidth = w;
    }

    public ITooltipType getType() {
        return type;
    }

    public enum Type implements ITooltipType {
        STANDARD,
        HELP,
        INFORMATION,
        USER,
        POWER
    }

    public interface ITooltipType {}
}
