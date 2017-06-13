package binnie.extratrees.item;

import binnie.core.item.IItemMisc;
import binnie.core.util.I18N;
import binnie.extratrees.ExtraTrees;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public enum ExtraTreeItems implements IItemMisc {
	CarpentryHammer("carpentryHammer"),
	Sawdust("sawdust"),
	Bark("bark"),
	ProvenGear("provenGear"),
	WoodWax("woodWax"),
	GlassFitting("glassFitting");

	protected String name;
	protected String iconPath;
	protected IIcon icon;

	ExtraTreeItems(String name) {
		this.name = name;
		this.iconPath = name;
	}

	@Override
	public IIcon getIcon(ItemStack itemStack) {
		return icon;
	}

	@Override
	public void registerIcons(IIconRegister register) {
		icon = ExtraTrees.proxy.getIcon(register, iconPath);
	}

	@Override
	public void addInformation(List tooltip) {
		// ignored
	}

	@Override
	public String getName(ItemStack itemStack) {
		return I18N.localise("extratrees.item." + name);
	}

	@Override
	public boolean isActive() {
		return this != ExtraTreeItems.CarpentryHammer;
	}

	@Override
	public ItemStack get(int count) {
		return new ItemStack(ExtraTrees.itemMisc, count, ordinal());
	}
}
