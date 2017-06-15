package binnie.core.machines.power;

import net.minecraft.nbt.NBTTagCompound;

import forestry.api.core.INbtReadable;
import forestry.api.core.INbtWritable;

public class ErrorState implements INbtReadable, INbtWritable {
	private String name;
	private String desc;
	private int[] data;
	private boolean progress;
	private boolean itemError;
	private boolean tankError;
	private boolean powerError;

	public ErrorState(final String name, final String desc) {
		this.name = "";
		this.desc = "";
		this.data = new int[0];
		this.progress = false;
		this.itemError = false;
		this.tankError = false;
		this.powerError = false;
		this.name = name;
		this.desc = desc;
	}

	public ErrorState(final String name, final String desc, final int[] data) {
		this.name = "";
		this.desc = "";
		this.data = new int[0];
		this.progress = false;
		this.itemError = false;
		this.tankError = false;
		this.powerError = false;
		this.name = name;
		this.desc = desc;
		this.data = data;
	}

	@Override
	public String toString() {
		return this.name;
	}

	public String getTooltip() {
		return this.desc;
	}

	public int[] getData() {
		return this.data;
	}

	public boolean isProgress() {
		return this.progress;
	}

	public void setIsProgress() {
		this.progress = true;
	}

	@Override
	public void readFromNBT(final NBTTagCompound nbt) {
		this.name = nbt.getString("name");
		this.desc = nbt.getString("desc");
		this.data = nbt.getIntArray("data");
		this.itemError = nbt.getBoolean("item");
		this.tankError = nbt.getBoolean("tank");
		this.powerError = nbt.getBoolean("power");
	}

	@Override
	public NBTTagCompound writeToNBT(final NBTTagCompound nbt) {
		nbt.setString("name", this.toString());
		nbt.setString("desc", this.getTooltip());
		nbt.setIntArray("data", this.data);
		if (this.isItemError()) {
			nbt.setBoolean("item", true);
		}
		if (this.isTankError()) {
			nbt.setBoolean("tank", true);
		}
		if (this.isPowerError()) {
			nbt.setBoolean("power", true);
		}
		return nbt;
	}

	public boolean isItemError() {
		return this.itemError || this instanceof Item;
	}

	public boolean isTankError() {
		return this.tankError || this instanceof Tank;
	}

	public boolean isPowerError() {
		return this.powerError || this instanceof InsufficientPower;
	}

	public static class Item extends ErrorState {
		public Item(final String name, final String desc, final int[] slots) {
			super(name, desc, slots);
		}
	}

	public static class Tank extends ErrorState {
		public Tank(final String name, final String desc, final int[] slots) {
			super(name, desc, slots);
		}
	}

	public static class NoItem extends Item {
		public NoItem(final String desc, final int slot) {
			this(desc, new int[]{slot});
		}

		public NoItem(final String desc, final int[] slots) {
			super("No Item", desc, slots);
		}
	}

	public static class InvalidItem extends Item {
		public InvalidItem(final String desc, final int slot) {
			this("Invalid Item", desc, slot);
		}

		public InvalidItem(final String name, final String desc, final int slot) {
			super(name, desc, new int[]{slot});
		}
	}

	public static class NoSpace extends Item {
		public NoSpace(final String desc, final int[] slots) {
			super("No Space", desc, slots);
		}
	}

	public static class InsufficientPower extends ErrorState {
		public InsufficientPower() {
			super("Insufficient Power", "Not enough power to operate");
		}
	}

	public static class TankSpace extends Tank {
		public TankSpace(final String desc, final int tank) {
			super("Tank Full", desc, new int[]{tank});
		}
	}

	public static class InsufficientLiquid extends Tank {
		public InsufficientLiquid(final String desc, final int tank) {
			super("Insufficient Liquid", desc, new int[]{tank});
		}
	}

	public static class InvalidRecipe extends Item {
		public InvalidRecipe(final String string, final int[] slots) {
			super("Invalid Recipe", string, slots);
		}
	}
}
