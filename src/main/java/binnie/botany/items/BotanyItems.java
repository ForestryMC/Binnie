// 
// Decompiled by Procyon v0.5.30
// 

package binnie.botany.items;

import net.minecraft.item.Item;
import java.util.List;
import binnie.botany.Botany;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import binnie.core.item.IItemMisc;

public enum BotanyItems implements IItemMisc
{
	AshPowder("Ash Powder", "powderAsh"),
	PulpPowder("Wood Pulp Powder", "powderPulp"),
	MulchPowder("Mulch Powder", "powderMulch"),
	SulphurPowder("Sulphur Powder", "powderSulphur"),
	FertiliserPowder("Fertiliser Powder", "powderFertiliser"),
	CompostPowder("Compost Powder", "powderCompost"),
	Mortar("Mortar", "mortar"),
	Weedkiller("Weedkiller", "weedkiller");

	IIcon icon;
	String name;
	String iconPath;

	private BotanyItems(final String name, final String iconPath) {
		this.name = name;
		this.iconPath = iconPath;
	}

	@Override
	public IIcon getIcon(final ItemStack itemStack) {
		return this.icon;
	}

	@Override
	public void registerIcons(final IIconRegister register) {
		this.icon = Botany.proxy.getIcon(register, this.iconPath);
	}

	@Override
	public void addInformation(final List data) {
	}

	@Override
	public String getName(final ItemStack itemStack) {
		return this.name;
	}

	@Override
	public ItemStack get(final int size) {
		return new ItemStack(Botany.misc, size, this.ordinal());
	}

	@Override
	public boolean isActive() {
		return true;
	}
}
