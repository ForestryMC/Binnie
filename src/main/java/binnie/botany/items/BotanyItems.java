package binnie.botany.items;

import binnie.botany.Botany;
import binnie.core.item.IItemMisc;
import binnie.core.util.I18N;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public enum BotanyItems implements IItemMisc {
	AshPowder("powderAsh"),
	PulpPowder("powderPulp"),
	MulchPowder("powderMulch"),
	SulphurPowder("powderSulphur"),
	FertiliserPowder("powderFertiliser"),
	CompostPowder("powderCompost"),
	Mortar("mortar"),
	Weedkiller("weedkiller");

	protected IIcon icon;
	protected String name;
	protected String iconPath;

	BotanyItems(String name) {
		this.name = name;
		this.iconPath = name;
	}

	@Override
	public IIcon getIcon(ItemStack itemStack) {
		return icon;
	}

	@Override
	public void registerIcons(IIconRegister register) {
		icon = Botany.proxy.getIcon(register, iconPath);
	}

	@Override
	public void addInformation(List data) {
		// ignored
	}

	@Override
	public String getName(ItemStack itemStack) {
		return I18N.localise("botany.tube.item." + name + ".name");
	}

	@Override
	public ItemStack get(int count) {
		return new ItemStack(Botany.misc, count, ordinal());
	}

	@Override
	public boolean isActive() {
		return true;
	}
}
