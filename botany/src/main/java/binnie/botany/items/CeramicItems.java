package binnie.botany.items;

import java.util.List;
import java.util.Locale;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.botany.modules.ModuleCeramic;
import binnie.core.item.IItemSubtypeMisc;
import binnie.core.util.I18N;

public enum CeramicItems implements IItemSubtypeMisc {
	MORTAR;

	private final String name;

	CeramicItems() {
		name = name().toLowerCase(Locale.ENGLISH);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(List<String> tooltip) {
		// ignored
	}

	@Override
	public String getDisplayName(ItemStack stack) {
		return I18N.localise("item.botany." + name + ".name");
	}

	@Override
	public String getModelPath() {
		return name;
	}

	@Override
	public ItemStack stack(int size) {
		return new ItemStack(ModuleCeramic.misc, size, ordinal());
	}

	@Override
	public boolean isActive() {
		return true;
	}
}
