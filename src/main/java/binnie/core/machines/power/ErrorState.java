package binnie.core.machines.power;

import binnie.core.util.I18N;
import forestry.api.core.INBTTagable;
import net.minecraft.nbt.NBTTagCompound;

public class ErrorState implements INBTTagable {
    private String name;
    private String desc;
    private int[] data;
    private boolean progress;
    private boolean itemError;
    private boolean tankError;
    private boolean powerError;

    public ErrorState(String name, String desc) {
        data = new int[0];
        progress = false;
        itemError = false;
        tankError = false;
        powerError = false;
        this.name = name;
        this.desc = desc;
    }

    public ErrorState(String name, String desc, int[] data) {
        progress = false;
        itemError = false;
        tankError = false;
        powerError = false;
        this.name = name;
        this.desc = desc;
        this.data = data;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getTooltip() {
        return desc;
    }

    public int[] getData() {
        return data;
    }

    public boolean isProgress() {
        return progress;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        name = nbt.getString("name");
        desc = nbt.getString("desc");
        data = nbt.getIntArray("data");
        itemError = nbt.getBoolean("item");
        tankError = nbt.getBoolean("tank");
        powerError = nbt.getBoolean("power");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        nbt.setString("name", toString());
        nbt.setString("desc", getTooltip());
        nbt.setIntArray("data", data);
        if (isItemError()) {
            nbt.setBoolean("item", true);
        }
        if (isTankError()) {
            nbt.setBoolean("tank", true);
        }
        if (isPowerError()) {
            nbt.setBoolean("power", true);
        }
    }

    public boolean isItemError() {
        return itemError || this instanceof Item;
    }

    public boolean isTankError() {
        return tankError || this instanceof Tank;
    }

    public boolean isPowerError() {
        return powerError || this instanceof InsufficientPower;
    }

    public static class Item extends ErrorState {
        public Item(String name, String desc, int[] slots) {
            super(name, desc, slots);
        }
    }

    public static class Tank extends ErrorState {
        public Tank(String name, String desc, int[] slots) {
            super(name, desc, slots);
        }
    }

    public static class NoItem extends Item {
        public NoItem(String desc, int slot) {
            this(desc, new int[] {slot});
        }

        public NoItem(String desc, int[] slots) {
            super(I18N.localise("binniecore.machine.error.noItem"), desc, slots);
        }
    }

    public static class InvalidItem extends Item {
        public InvalidItem(String desc, int slot) {
            this(I18N.localise("binniecore.machine.error.invalidItem"), desc, slot);
        }

        public InvalidItem(String name, String desc, int slot) {
            super(name, desc, new int[] {slot});
        }
    }

    public static class NoSpace extends Item {
        public NoSpace(String desc, int slot) {
            super(I18N.localise("binniecore.machine.error.noSpace"), desc, new int[] {slot});
        }

        public NoSpace(String desc, int[] slots) {
            super(I18N.localise("binniecore.machine.error.noSpace"), desc, slots);
        }
    }

    public static class InsufficientPower extends ErrorState {
        public InsufficientPower() {
            super(
                    I18N.localise("binniecore.machine.error.insufficientPower.title"),
                    I18N.localise("binniecore.machine.error.insufficientPower.desc"));
        }
    }

    public static class TankSpace extends Tank {
        public TankSpace(String desc, int tank) {
            super(I18N.localise("binniecore.machine.error.tankFull"), desc, new int[] {tank});
        }
    }

    public static class InsufficientLiquid extends Tank {
        public InsufficientLiquid(String desc, int tank) {
            super(I18N.localise("binniecore.machine.error.insufficientLiquid"), desc, new int[] {tank});
        }
    }
}
