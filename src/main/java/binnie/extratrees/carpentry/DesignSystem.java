package binnie.extratrees.carpentry;

import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.api.CarpentryManager;
import binnie.extratrees.api.IDesignMaterial;
import binnie.extratrees.api.IDesignSystem;
import binnie.extratrees.api.IPattern;
import binnie.extratrees.block.PlankType;
import binnie.extratrees.item.ExtraTreeItems;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public enum DesignSystem implements IDesignSystem {
    Wood,
    Glass;

    protected Map<Integer, IIcon> primary;
    protected Map<Integer, IIcon> secondary;

    DesignSystem() {
        primary = new HashMap<>();
        secondary = new HashMap<>();
        DesignerManager.instance.registerDesignSystem(this);
    }

    @Override
    public IDesignMaterial getDefaultMaterial() {
        switch (this) {
            case Glass:
                return GlassType.get(0);

            case Wood:
                return PlankType.ExtraTreePlanks.Fir;
        }
        return null;
    }

    @Override
    public IDesignMaterial getDefaultMaterial2() {
        switch (this) {
            case Glass:
                return GlassType.get(1);

            case Wood:
                return PlankType.ExtraTreePlanks.Whitebeam;
        }
        return null;
    }

    @Override
    public IDesignMaterial getMaterial(int id) {
        switch (this) {
            case Glass:
                return GlassType.get(id);

            case Wood:
                return CarpentryManager.carpentryInterface.getWoodMaterial(id);
        }
        return null;
    }

    @Override
    public int getMaterialIndex(IDesignMaterial id) {
        switch (this) {
            case Glass:
                return GlassType.getIndex(id);

            case Wood:
                return CarpentryManager.carpentryInterface.getCarpentryWoodIndex(id);
        }
        return 0;
    }

    public String getTexturePath() {
        switch (this) {
            case Glass:
                return "glass";

            case Wood:
                return "patterns";
        }
        return "";
    }

    @Override
    public IDesignMaterial getMaterial(ItemStack stack) {
        switch (this) {
            case Glass:
                return GlassType.get(stack);

            case Wood:
                return CarpentryManager.carpentryInterface.getWoodMaterial(stack);
        }
        return null;
    }

    @Override
    public ItemStack getAdhesive() {
        switch (this) {
            case Glass:
                return ExtraTreeItems.GlassFitting.get(1);

            case Wood:
                return ExtraTreeItems.WoodWax.get(1);
        }
        return null;
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
        return ExtraTrees.instance;
    }
}
