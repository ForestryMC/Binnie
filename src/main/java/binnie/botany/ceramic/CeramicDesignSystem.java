package binnie.botany.ceramic;

import binnie.botany.Botany;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.botany.items.BotanyItems;
import binnie.core.AbstractMod;
import binnie.extratrees.api.IDesignMaterial;
import binnie.extratrees.api.IDesignSystem;
import binnie.extratrees.carpentry.DesignerManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CeramicDesignSystem implements IDesignSystem {
    public static CeramicDesignSystem instance = new CeramicDesignSystem();
//	Map<Integer, IIcon> primary;
//	Map<Integer, IIcon> secondary;

    CeramicDesignSystem() {
//		this.primary = new HashMap<Integer, IIcon>();
//		this.secondary = new HashMap<Integer, IIcon>();
        DesignerManager.instance.registerDesignSystem(this);
    }

    @Override
    public IDesignMaterial getDefaultMaterial() {
        return CeramicColor.get(EnumFlowerColor.White);
    }

    @Override
    public IDesignMaterial getDefaultMaterial2() {
        return CeramicColor.get(EnumFlowerColor.Black);
    }

    @Override
    public IDesignMaterial getMaterial(final int id) {
        return CeramicColor.get(EnumFlowerColor.get(id));
    }

    @Override
    public int getMaterialIndex(final IDesignMaterial id) {
        return ((CeramicColor) id).color.ordinal();
    }

    public String getTexturePath() {
        return "ceramic";
    }

//	@Override
//	public IIcon getPrimaryIcon(final IPattern pattern) {
//		if (pattern instanceof EnumPattern) {
//			return this.primary.get(((EnumPattern) pattern).ordinal());
//		}
//		return null;
//	}
//
//	@Override
//	public IIcon getSecondaryIcon(final IPattern pattern) {
//		if (pattern instanceof EnumPattern) {
//			return this.secondary.get(((EnumPattern) pattern).ordinal());
//		}
//		return null;
//	}
//
//	@Override
//	public void registerIcons(final IIconRegister register) {
//		for (final EnumPattern pattern : EnumPattern.values()) {
//			this.primary.put(pattern.ordinal(), BinnieCore.proxy.getIcon(register, this.getMod().getModID(), this.getTexturePath() + "/" + pattern.toString().toLowerCase() + ".0"));
//			this.secondary.put(pattern.ordinal(), BinnieCore.proxy.getIcon(register, this.getMod().getModID(), this.getTexturePath() + "/" + pattern.toString().toLowerCase() + ".1"));
//		}
//	}

    public AbstractMod getMod() {
        return Botany.instance;
    }

    @Override
    public ItemStack getAdhesive() {
        return BotanyItems.Mortar.get(1);
    }

    @Override
    public IDesignMaterial getMaterial(final ItemStack itemStack) {
        return (itemStack.getItem() == Item.getItemFromBlock(Botany.ceramic)) ? this.getMaterial(itemStack.getItemDamage()) : null;
    }

}
