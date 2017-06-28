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
	
	public ErrorState(IErrorStateDefinition definition) {
		this(definition, definition);
	}
	
	public ErrorState(IErrorStateDefinition definition, int[] data) {
		this(definition, definition, data);
	}
	
	public ErrorState(IErrorStateDefinition definition, int data) {
		this(definition, definition, new int[]{data});
	}
	
	public ErrorState(IErrorStateDefinition nameDefinition, IErrorStateDefinition definition) {
		this(nameDefinition.getName(), definition.getDescription());
		EnumErrorType type = definition.getType();
		this.itemError = type.isItemError();
		this.tankError = type.isTankError();
		this.powerError = type.isPowerError();
	}
	
	public ErrorState(IErrorStateDefinition nameDefinition, IErrorStateDefinition definition, int data) {
		this(nameDefinition.getName(), definition.getDescription(), new int[]{data});
		EnumErrorType type = definition.getType();
		this.itemError = type.isItemError();
		this.tankError = type.isTankError();
		this.powerError = type.isPowerError();
	}
	
	public ErrorState(IErrorStateDefinition nameDefinition, IErrorStateDefinition definition, int[] data) {
		this(nameDefinition.getName(), definition.getDescription(), data);
		EnumErrorType type = definition.getType();
		this.itemError = type.isItemError();
		this.tankError = type.isTankError();
		this.powerError = type.isPowerError();
	}
	
	public ErrorState(String name, String desc) {
		this.data = new int[0];
		this.progress = false;
		this.itemError = false;
		this.tankError = false;
		this.powerError = false;
		this.name = name;
		this.desc = desc;
	}

	public ErrorState(String name, String desc, int[] data) {
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
		
		public Item(IErrorStateDefinition nameDefinition, IErrorStateDefinition definition, final int[] slots) {
			super(nameDefinition, definition, slots);
		}
	}

	public static class Tank extends ErrorState {
		public Tank(final String name, final String desc, final int[] slots) {
			super(name, desc, slots);
		}
		
		public Tank(IErrorStateDefinition nameDefinition, IErrorStateDefinition definition, final int[] slots) {
			super(nameDefinition, definition, slots);
		}
	}

	public static class NoItem extends Item {
		public NoItem(final String desc, final int slot) {
			this(desc, new int[]{slot});
		}

		public NoItem(final String desc, final int[] slots) {
			super("No Item", desc, slots);
		}
		
		public NoItem(IErrorStateDefinition definition, final int[] slots) {
			super("No Item", definition.getDescription(), slots);
		}
		
		public NoItem(IErrorStateDefinition definition, final int slot) {
			this(definition.getDescription(), new int[]{slot});
		}
	}

	public static class InvalidItem extends Item {
		@Deprecated
		public InvalidItem(final String desc, int slot) {
			this(CoreErrorCode.INVALID_ITEM.getName(), desc, slot);
		}
		
		public InvalidItem(IErrorStateDefinition definition, int slot) {
			super(definition.getName(), definition.getDescription(), new int[]{slot});
		}

		public InvalidItem(String name, String desc, int slot) {
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
			super(CoreErrorCode.INSUFFICIENT_POWER);
		}
	}

	public static class TankSpace extends Tank {
		@Deprecated
		public TankSpace(final String desc, final int tank) {
			super(CoreErrorCode.TANK_FULL.getName(), desc, new int[]{tank});
		}
		
		public TankSpace(IErrorStateDefinition definition, final int tank) {
			super(CoreErrorCode.TANK_FULL, definition, new int[]{tank});
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
