package binnie.botany.items;

import binnie.botany.Botany;
import binnie.core.item.IItemMisc;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public enum BotanyItems implements IItemMisc {
	AshPowder("Ash Powder", "powderAsh"),
	PulpPowder("Wood Pulp Powder", "powderPulp"),
	MulchPowder("Mulch Powder", "powderMulch"),
	SulphurPowder("Sulphur Powder", "powderSulphur"),
	FertiliserPowder("Fertiliser Powder", "powderFertiliser"),
	CompostPowder("Compost Powder", "powderCompost"),
	Mortar("Mortar", "mortar"),
	Weedkiller("Weedkiller", "weedkiller");

	protected IIcon icon;
	protected String name;
	protected String iconPath;

	BotanyItems(String name, String iconPath) {
		this.name = name;
		this.iconPath = iconPath;
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
		return name;
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
