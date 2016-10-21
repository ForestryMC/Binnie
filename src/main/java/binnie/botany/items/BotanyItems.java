package binnie.botany.items;

import binnie.botany.Botany;
import binnie.core.item.IItemMisc;
import net.minecraft.item.ItemStack;

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

    //	IIcon icon;
    String name;
    String iconPath;

    BotanyItems(final String name, final String iconPath) {
        this.name = name;
        this.iconPath = iconPath;
    }

//	@Override
//	public IIcon getIcon(final ItemStack stack) {
//		return this.icon;
//	}
//
//	@Override
//	public void registerIcons(final IIconRegister register) {
//		this.icon = Botany.proxy.getIcon(register, this.iconPath);
//	}

    @Override
    public void addInformation(final List par3List) {
    }

    @Override
    public String getName(final ItemStack stack) {
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
