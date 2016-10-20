// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.machines.storage;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.init.Items;
import binnie.craftgui.minecraft.EnumColor;
import net.minecraft.item.ItemStack;
import forestry.api.core.INBTTagable;

class CompartmentTab implements INBTTagable
{
	private String name;
	private ItemStack icon;
	private EnumColor color;
	private int id;

	public CompartmentTab(final int id) {
		this.name = "";
		this.icon = new ItemStack(Items.paper);
		this.color = EnumColor.White;
		this.id = id;
	}

	@Override
	public void readFromNBT(final NBTTagCompound nbt) {
		this.name = nbt.getString("name");
		this.icon = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("icon"));
		this.color = EnumColor.values()[nbt.getByte("color")];
		this.id = nbt.getByte("id");
	}

	@Override
	public void writeToNBT(final NBTTagCompound nbt) {
		nbt.setString("name", this.name);
		final NBTTagCompound n = new NBTTagCompound();
		this.icon.writeToNBT(n);
		nbt.setTag("icon", n);
		nbt.setByte("color", (byte) this.color.ordinal());
		nbt.setByte("id", (byte) this.id);
	}

	public String getName() {
		return this.name;
	}

	public ItemStack getIcon() {
		return this.icon;
	}

	public EnumColor getColor() {
		return this.color;
	}

	public int getId() {
		return this.id;
	}

	public void setName(final String name) {
		this.name = ((name == null) ? "" : name);
	}

	public void setIcon(final ItemStack icon) {
		this.icon = icon;
	}

	public void setColor(final EnumColor color) {
		this.color = color;
	}
}
