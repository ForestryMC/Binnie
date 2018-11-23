package binnie.core.machines.storage;

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
		this.name = "";
		this.icon = new ItemStack(Items.PAPER);
		this.color = EnumColor.WHITE;
		this.id = id;
	}

	public CompartmentTab(NBTTagCompound nbt) {
		this.name = nbt.getString("name");
		this.icon = new ItemStack(nbt.getCompoundTag("icon"));
		this.color = EnumColor.values()[nbt.getByte("color")];
		this.id = nbt.getByte("id");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setString("name", this.name);
		NBTTagCompound n = new NBTTagCompound();
		this.icon.writeToNBT(n);
		nbt.setTag("icon", n);
		nbt.setByte("color", (byte) this.color.ordinal());
		nbt.setByte("id", (byte) this.id);
		return nbt;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ItemStack getIcon() {
		return this.icon;
	}

	public void setIcon(ItemStack icon) {
		this.icon = icon;
	}

	public EnumColor getColor() {
		return this.color;
	}

	public void setColor(EnumColor color) {
		this.color = color;
	}

	public int getId() {
		return this.id;
	}
}
