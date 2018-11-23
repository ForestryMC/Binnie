package binnie.core.machines.inventory;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import binnie.core.Constants;
import binnie.core.machines.IMachine;
import binnie.core.util.NBTUtil;

public class ComponentInventorySlots extends ComponentInventory implements IInventoryMachine, IInventorySlots {
	private static final String INVENTORY_KEY = "inventory";
	private static final String INDEX_KEY = "id";
	private static final float ACCEL = 0.05f;

	private final Map<Integer, InventorySlot> inventory;
	private final Map<EnumFacing, int[]> slotsForFace;

	public ComponentInventorySlots(IMachine machine) {
		super(machine);
		this.inventory = new LinkedHashMap<>();
		this.slotsForFace = new LinkedHashMap<>();
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		InventorySlot slot = getInternalSlot(index);
		if (slot.isFake()) {
			return ItemStack.EMPTY;
		}
		ItemStack content = slot.getItemStack();
		slot.setContent(ItemStack.EMPTY);
		this.markDirty();
		return content;
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentString("");
	}

	@Override
	public int getSizeInventory() {
		int size = 0;
		for (int index : this.inventory.keySet()) {
			size = Math.max(size, index + 1);
		}
		return size;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		if (this.inventory.containsKey(index)) {
			return this.inventory.get(index).getItemStack();
		}
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack decrStackSize(int index, int amount) {
		InventorySlot slot = getInternalSlot(index);
		ItemStack stack = slot.decrStackSize(amount);
		if (!stack.isEmpty()) {
			this.markDirty();
		}
		return stack;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack itemStack) {
		InventorySlot slot = getInternalSlot(index);
		if (!slot.isFake() && (itemStack.isEmpty() || slot.isValid(itemStack))) {
			slot.setContent(itemStack);
			this.markDirty();
		}
	}

	@Override
	public boolean isEmpty() {
		return this.inventory.isEmpty();
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		NBTUtil.readFromList(compound, INVENTORY_KEY, (slotNBT) -> {
			int index = slotNBT.getInteger(INDEX_KEY);
			InventorySlot slot = getInternalSlot(index);
			slot.readFromNBT(slotNBT);
		});
		this.markDirty();
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound = super.writeToNBT(compound);
		NBTUtil.writeToList(compound, INVENTORY_KEY, inventory, (index, slot) -> {
			NBTTagCompound slotTag = new NBTTagCompound();
			slotTag.setInteger(INDEX_KEY, index);
			slot.writeToNBT(slotTag);
			return slotTag;
		});
		return compound;
	}

	public final InventorySlot addSlot(int index, ResourceLocation unlocLocation) {
		InventorySlot slot = new InventorySlot(index, unlocLocation);
		this.inventory.put(index, slot);
		return slot;
	}

	/**
	 * @deprecated use {@link #addSlot(int, ResourceLocation)}
	 */
	@Deprecated
	public final InventorySlot addSlot(int index, String unlocName) {
		ResourceLocation unlocLocation = new ResourceLocation(Constants.CORE_MOD_ID, "gui.slot." + unlocName);
		return addSlot(index, unlocLocation);
	}

	public final InventorySlot[] addSlotArray(int[] indexes, ResourceLocation unlocalizedName) {
		return Arrays.stream(indexes)
			.mapToObj(index -> addSlot(index, unlocalizedName))
			.toArray(InventorySlot[]::new);
	}

	/**
	 * @deprecated use {@link #addSlotArray(int[], ResourceLocation)}
	 */
	@Deprecated
	public final InventorySlot[] addSlotArray(int[] indexes, String unlocalizedName) {
		return Arrays.stream(indexes)
			.mapToObj(index -> addSlot(index, unlocalizedName))
			.toArray(InventorySlot[]::new);
	}

	@Override
	@Nullable
	public InventorySlot getSlot(int index) {
		if (this.inventory.containsKey(index)) {
			return this.inventory.get(index);
		}
		return null;
	}

	private InventorySlot getInternalSlot(int index) {
		return inventory.getOrDefault(index, FakeSlot.INSTANCE);
	}

	private boolean isReadOnly(int slot) {
		InventorySlot iSlot = this.getSlot(slot);
		return iSlot == null || iSlot.isReadOnly();
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack itemStack) {
		InventorySlot slot = this.getSlot(index);
		return slot != null && (slot.isValid(itemStack) && !this.isReadOnly(index));
	}

	@Override
	public void onDestruction() {
		IMachine machine = getMachine();
		World world = machine.getWorld();
		Random rand = world.rand;
		BlockPos pos = machine.getTileEntity().getPos();
		for (InventorySlot slot : this.inventory.values()) {
			ItemStack stack = slot.getItemStack();
			if (slot.isRecipe() || stack.isEmpty()) {
				continue;
			}
			float xOffset = rand.nextFloat() * 0.8f + 0.1f;
			float yOffset = rand.nextFloat() * 0.8f + 0.1f;
			float zOffset = rand.nextFloat() * 0.8f + 0.1f;

			EntityItem entityItem = new EntityItem(world, pos.getX() + xOffset, pos.getY() + yOffset, pos.getZ() + zOffset, stack.copy());
			entityItem.motionX = (float) rand.nextGaussian() * ACCEL;
			entityItem.motionY = (float) rand.nextGaussian() * ACCEL + 0.2f;
			entityItem.motionZ = (float) rand.nextGaussian() * ACCEL;

			world.spawnEntity(entityItem);
		}
	}

	@Override
	public int[] getSlotsForFace(EnumFacing facing) {
		if (!slotsForFace.containsKey(facing)) {
			slotsForFace.put(facing, inventory.values().stream()
				.filter(slot -> slot.canInsert() || slot.canExtract())
				.mapToInt(InventorySlot::getIndex)
				.toArray()
			);
		}
		return slotsForFace.get(facing);
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemstack, EnumFacing direction) {
		if (!isItemValidForSlot(index, itemstack)) {
			return false;
		}
		InventorySlot slot = this.getSlot(index);
		return slot != null && slot.canInsert(direction);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack itemstack, EnumFacing direction) {
		InventorySlot slot = this.getSlot(index);
		return slot != null && slot.canExtract(direction);
	}

	@Override
	public String getName() {
		return "";
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer var1) {
		return true;
	}

	@Override
	public void closeInventory(EntityPlayer player) {
	}

	@Override
	public void openInventory(EntityPlayer player) {
	}

	@Override
	public void clear() {
		for (InventorySlot slot : this.inventory.values()) {
			slot.setContent(ItemStack.EMPTY);
		}
		this.markDirty();
	}

	@Override
	public void setField(int id, int value) {

	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public int getFieldCount() {
		return 0;
	}
}
