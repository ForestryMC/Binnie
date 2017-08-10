package binnie.core.machines.errors;

import net.minecraft.nbt.NBTTagCompound;

import forestry.api.core.INbtReadable;
import forestry.api.core.INbtWritable;

public class ErrorState implements INbtReadable, INbtWritable {
	/*private String name;
	private String desc;*/
	private IErrorStateDefinition nameDefinition;
	private IErrorStateDefinition definition;
	private int[] data;
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
		this(nameDefinition, definition, new int[0]);
	}

	public ErrorState(IErrorStateDefinition nameDefinition, IErrorStateDefinition definition, int[] data) {
		EnumErrorType type = definition.getType();
		this.data = data;
		this.itemError = type.isItemError();
		this.tankError = type.isTankError();
		this.powerError = type.isPowerError();
		this.nameDefinition = nameDefinition;
		this.definition = definition;
	}

	public ErrorState(NBTTagCompound nbtTagCompound) {
		this.data = new int[0];
		this.itemError = false;
		this.tankError = false;
		this.powerError = false;
		this.definition = CoreErrorCode.UNKNOWN;
		this.nameDefinition = CoreErrorCode.UNKNOWN;
		readFromNBT(nbtTagCompound);
	}

	@Override
	public String toString() {
		return nameDefinition.getName();
	}

	public String getTooltip() {
		return definition.getDescription();
	}

	public int[] getData() {
		return this.data;
	}

	@Override
	public void readFromNBT(final NBTTagCompound nbt) {
		this.nameDefinition = ErrorStateRegistry.getErrorState(nbt.getString("name"));
		this.definition = ErrorStateRegistry.getErrorState(nbt.getString("desc"));
		this.data = nbt.getIntArray("data");
		this.itemError = nbt.getBoolean("item");
		this.tankError = nbt.getBoolean("tank");
		this.powerError = nbt.getBoolean("power");
	}

	@Override
	public NBTTagCompound writeToNBT(final NBTTagCompound nbt) {
		nbt.setString("name", nameDefinition.getUID());
		nbt.setString("desc", definition.getUID());
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
		return this.itemError;
	}

	public boolean isTankError() {
		return this.tankError;
	}

	public boolean isPowerError() {
		return this.powerError;
	}
}
