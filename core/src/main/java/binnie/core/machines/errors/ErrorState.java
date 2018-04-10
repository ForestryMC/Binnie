package binnie.core.machines.errors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import binnie.core.gui.minecraft.ContainerCraftGUI;
import binnie.core.gui.minecraft.CustomSlot;
import binnie.core.machines.inventory.InventorySlot;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.machines.inventory.Validator;
import binnie.core.util.EmptyHelper;
import it.unimi.dsi.fastutil.ints.IntArraySet;
import it.unimi.dsi.fastutil.ints.IntSet;
import net.minecraft.entity.player.InventoryPlayer;
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

	public ErrorState(final IErrorStateDefinition definition) {
		this(definition, definition);
	}

	public ErrorState(final IErrorStateDefinition definition, final int[] data) {
		this(definition, definition, data);
	}

	public ErrorState(final IErrorStateDefinition definition, final int data) {
		this(definition, definition, new int[]{data});
	}

	public ErrorState(final IErrorStateDefinition nameDefinition, final IErrorStateDefinition definition) {
		this(nameDefinition, definition, EmptyHelper.INT_ARRAY_EMPTY);
	}

	public ErrorState(final IErrorStateDefinition nameDefinition, final IErrorStateDefinition definition, final int[] data) {
		final EnumErrorType type = definition.getType();
		this.data = data;
		this.itemError = type.isItemError();
		this.tankError = type.isTankError();
		this.powerError = type.isPowerError();
		this.nameDefinition = nameDefinition;
		this.definition = definition;
	}

	public ErrorState(NBTTagCompound nbtTagCompound) {
		this.data = EmptyHelper.INT_ARRAY_EMPTY;
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

	public String getTooltip(ContainerCraftGUI container) {
		final Collection<CustomSlot> slots = getCustomSlots(container);
		final Set<Validator<?>> validators = new HashSet<>();
		for (final CustomSlot slot : slots) {
			final InventorySlot inventorySlot = slot.getInventorySlot();
			if (inventorySlot != null) {
				final SlotValidator validator = inventorySlot.getValidator();
				if (validator != null) {
					validators.add(validator);
				}
			}
		}
		return definition.getDescription(validators);
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

	public Collection<CustomSlot> getCustomSlots(ContainerCraftGUI container) {
		final Collection<CustomSlot> slots = new ArrayList<>();
		if (isItemError()) {
			final IntSet slotNumbers = new IntArraySet(getData());
			for (final CustomSlot cslot : container.getCustomSlots()) {
				if (!(cslot.inventory instanceof InventoryPlayer) && slotNumbers.contains(cslot.getSlotIndex())) {
					slots.add(cslot);
				}
			}
		}
		return slots;
	}
}
