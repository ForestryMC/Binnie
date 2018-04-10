package binnie.core.machines.inventory;

import org.apache.commons.lang3.StringUtils;

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

	public ComponentInventorySlots(final IMachine machine) {
		super(machine);
		this.inventory = new LinkedHashMap<>();
	}

	@Override
	public ItemStack removeStackFromSlot(final int index) {
		final InventorySlot slot = getInternalSlot(index);
		if(slot.isFake()){
			return ItemStack.EMPTY;
		}
		final ItemStack content = slot.getItemStack();
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
	public ItemStack getStackInSlot(final int index) {
		if (this.inventory.containsKey(index)) {
			return this.inventory.get(index).getItemStack();
		}
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack decrStackSize(final int index, final int amount) {
		final InventorySlot slot = getInternalSlot(index);
		final ItemStack stack = slot.decrStackSize(amount);
		if (!stack.isEmpty()) {
			this.markDirty();
		}
		return stack;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack itemStack) {
		final InventorySlot slot = getInternalSlot(index);
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
	public void readFromNBT(final NBTTagCompound compound) {
		super.readFromNBT(compound);
		NBTUtil.readFromList(compound, INVENTORY_KEY, (slotNBT) -> {
			final int index = slotNBT.getInteger(INDEX_KEY);
			InventorySlot slot = getInternalSlot(index);
			slot.readFromNBT(slotNBT);
		});
		this.markDirty();
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound = super.writeToNBT(compound);
		NBTUtil.writeToList(compound, INVENTORY_KEY, inventory, NBTUtil.writeToListConsumer(INDEX_KEY));
		return compound;
	}

	public final InventorySlot addSlot(final int index, final ResourceLocation unlocLocation) {
		final InventorySlot slot = new InventorySlot(index, unlocLocation);
		this.inventory.put(index, slot);
		return slot;
	}

	/**
	 * @deprecated use {@link #addSlot(int, ResourceLocation)}
	 */
	@Deprecated
	public final InventorySlot addSlot(final int index, final String unlocName) {
		final ResourceLocation unlocLocation = new ResourceLocation(Constants.CORE_MOD_ID, "gui.slot." + unlocName);
		return addSlot(index, unlocLocation);
	}

	public final InventorySlot[] addSlotArray(final int[] indexes, final ResourceLocation unlocalizedName) {
		return Arrays.stream(indexes)
			.mapToObj(index -> addSlot(index, unlocalizedName))
			.toArray(InventorySlot[]::new);
	}

	/**
	 * @deprecated use {@link #addSlotArray(int[], ResourceLocation)}
	 */
	@Deprecated
	public final InventorySlot[] addSlotArray(final int[] indexes, final String unlocalizedName) {
		return Arrays.stream(indexes)
			.mapToObj(index -> addSlot(index, unlocalizedName))
			.toArray(InventorySlot[]::new);
	}

	@Override
	@Nullable
	public InventorySlot getSlot(final int index) {
		if (this.inventory.containsKey(index)) {
			return this.inventory.get(index);
		}
		return null;
	}

	private InventorySlot getInternalSlot(int index){
		return inventory.getOrDefault(index, FakeSlot.INSTANCE);
	}

	private boolean isReadOnly(final int slot) {
		final InventorySlot iSlot = this.getSlot(slot);
		return iSlot == null || iSlot.isReadOnly();
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public boolean isItemValidForSlot(final int index, final ItemStack itemStack) {
		final InventorySlot slot = this.getSlot(index);
		return slot != null && (slot.isValid(itemStack) && !this.isReadOnly(index));
	}

	@Override
	public void onDestruction() {
		final IMachine machine = getMachine();
		final World world = machine.getWorld();
		final Random rand = world.rand;
		final BlockPos pos = machine.getTileEntity().getPos();
		for (final InventorySlot slot : this.inventory.values()) {
			final ItemStack stack = slot.getItemStack();
			if (slot.isRecipe() || stack.isEmpty()) {
				continue;
			}
			final float xOffset = rand.nextFloat() * 0.8f + 0.1f;
			final float yOffset = rand.nextFloat() * 0.8f + 0.1f;
			final float zOffset = rand.nextFloat() * 0.8f + 0.1f;

			final EntityItem entityItem = new EntityItem(world, pos.getX() + xOffset, pos.getY() + yOffset, pos.getZ() + zOffset, stack.copy());
			entityItem.motionX = (float) rand.nextGaussian() * ACCEL;
			entityItem.motionY = (float) rand.nextGaussian() * ACCEL + 0.2f;
			entityItem.motionZ = (float) rand.nextGaussian() * ACCEL;

			world.spawnEntity(entityItem);
		}
	}

	@Override
	public int[] getSlotsForFace(final EnumFacing facing) {
		return inventory.values().stream().
			filter(slot -> slot.canInsert() || slot.canExtract())
			.mapToInt(InventorySlot::getIndex)
			.toArray();
	}

	@Override
	public boolean canInsertItem(final int index, final ItemStack itemstack, final EnumFacing direction) {
		if (!isItemValidForSlot(index, itemstack)) {
			return false;
		}
		final InventorySlot slot = this.getSlot(index);
		return slot != null && slot.canInsert(direction);
	}

	@Override
	public boolean canExtractItem(final int index, final ItemStack itemstack, final EnumFacing direction) {
		final InventorySlot slot = this.getSlot(index);
		return slot != null && slot.canExtract(direction);
	}

	@Override
	public String getName() {
		return StringUtils.EMPTY;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(final EntityPlayer var1) {
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
