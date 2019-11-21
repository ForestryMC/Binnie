package binnie.core.machines.errors;

import binnie.core.gui.minecraft.ContainerCraftGUI;
import binnie.core.gui.minecraft.CustomSlot;
import binnie.core.machines.inventory.InventorySlot;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.machines.inventory.Validator;
import forestry.api.core.INbtReadable;
import forestry.api.core.INbtWritable;
import it.unimi.dsi.fastutil.ints.IntArraySet;
import it.unimi.dsi.fastutil.ints.IntSet;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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

	public String getTooltip(ContainerCraftGUI container) {
		Collection<CustomSlot> slots = getCustomSlots(container);
		Set<Validator<?>> validators = new HashSet<>();
		for (CustomSlot slot : slots) {
			InventorySlot inventorySlot = slot.getInventorySlot();
			if (inventorySlot != null) {
				SlotValidator validator = inventorySlot.getValidator();
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
		Collection<CustomSlot> slots = new ArrayList<>();
		if (isItemError()) {
			IntSet slotNumbers = new IntArraySet(getData());
			for (final CustomSlot cslot : container.getCustomSlots()) {
				if (!(cslot.inventory instanceof InventoryPlayer) && slotNumbers.contains(cslot.getSlotIndex())) {
					slots.add(cslot);
				}
			}
		}
		return slots;
	}
}
