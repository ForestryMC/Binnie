package binnie.core.machines.storage;

import org.apache.commons.lang3.StringUtils;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import forestry.api.core.INbtWritable;

import binnie.core.gui.minecraft.EnumColor;

class CompartmentTab implements INbtWritable {
	private String name;
	private ItemStack icon;
	private EnumColor color;
	private final int id;

	public CompartmentTab(int id) {
		this.name = StringUtils.EMPTY;
		this.icon = new ItemStack(Items.PAPER);
		this.color = EnumColor.WHITE;
		this.id = id;
	}

	private static final String NBT_KEY_NAME = "name";
	private static final String NBT_KEY_ICON = "icon";
	private static final String NBT_KEY_COLOR = "color";
	private static final String NBT_KEY_ID = "id";

	public CompartmentTab(NBTTagCompound nbt) {
		this.name = nbt.getString(NBT_KEY_NAME);
		this.icon = new ItemStack(nbt.getCompoundTag(NBT_KEY_ICON));
		this.color = EnumColor.values()[nbt.getByte(NBT_KEY_COLOR)];
		this.id = nbt.getByte(NBT_KEY_ID);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setString(NBT_KEY_NAME, this.name);
		final NBTTagCompound n = new NBTTagCompound();
		this.icon.writeToNBT(n);
		nbt.setTag(NBT_KEY_ICON, n);
		nbt.setByte(NBT_KEY_COLOR, (byte) this.color.ordinal());
		nbt.setByte(NBT_KEY_ID, (byte) this.id);
		return nbt;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public ItemStack getIcon() {
		return this.icon;
	}

	public void setIcon(final ItemStack icon) {
		this.icon = icon;
	}

	public EnumColor getColor() {
		return this.color;
	}

	public void setColor(final EnumColor color) {
		this.color = color;
	}

	public int getId() {
		return this.id;
	}
}
