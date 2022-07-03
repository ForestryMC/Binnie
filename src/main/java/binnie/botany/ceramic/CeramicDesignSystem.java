package binnie.botany.ceramic;

import binnie.botany.Botany;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.botany.items.BotanyItems;
import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import binnie.extratrees.api.IDesignMaterial;
import binnie.extratrees.api.IDesignSystem;
import binnie.extratrees.api.IPattern;
import binnie.extratrees.carpentry.DesignerManager;
import binnie.extratrees.carpentry.EnumPattern;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class CeramicDesignSystem implements IDesignSystem {
    public static CeramicDesignSystem instance = new CeramicDesignSystem();

    protected Map<Integer, IIcon> primary;
    protected Map<Integer, IIcon> secondary;

    CeramicDesignSystem() {
        primary = new HashMap<>();
        secondary = new HashMap<>();
        DesignerManager.instance.registerDesignSystem(this);
    }

    @Override
    public IDesignMaterial getDefaultMaterial() {
        return CeramicColor.get(EnumFlowerColor.WHITE);
    }

    @Override
    public IDesignMaterial getDefaultMaterial2() {
        return CeramicColor.get(EnumFlowerColor.BLACK);
    }

    @Override
    public IDesignMaterial getMaterial(int id) {
        return CeramicColor.get(EnumFlowerColor.get(id));
    }

    @Override
    public int getMaterialIndex(IDesignMaterial id) {
        return ((CeramicColor) id).color.ordinal();
    }

    public String getTexturePath() {
        return "ceramic";
    }

    @Override
    public IIcon getPrimaryIcon(IPattern pattern) {
        if (pattern instanceof EnumPattern) {
            return primary.get(((EnumPattern) pattern).ordinal());
        }
        return null;
    }

    @Override
    public IIcon getSecondaryIcon(IPattern pattern) {
        if (pattern instanceof EnumPattern) {
            return secondary.get(((EnumPattern) pattern).ordinal());
        }
        return null;
    }

    @Override
    public void registerIcons(IIconRegister register) {
        for (EnumPattern pattern : EnumPattern.values()) {
            primary.put(
                    pattern.ordinal(),
                    BinnieCore.proxy.getIcon(
                            register,
                            getMod().getModID(),
                            getTexturePath() + "/" + pattern.toString().toLowerCase() + ".0"));
            secondary.put(
                    pattern.ordinal(),
                    BinnieCore.proxy.getIcon(
                            register,
                            getMod().getModID(),
                            getTexturePath() + "/" + pattern.toString().toLowerCase() + ".1"));
        }
    }

    public AbstractMod getMod() {
        return Botany.instance;
    }

    @Override
    public ItemStack getAdhesive() {
        return BotanyItems.Mortar.get(1);
    }

    @Override
    public IDesignMaterial getMaterial(ItemStack stack) {
        return (stack.getItem() == Item.getItemFromBlock(Botany.ceramic)) ? getMaterial(stack.getItemDamage()) : null;
    }
}
