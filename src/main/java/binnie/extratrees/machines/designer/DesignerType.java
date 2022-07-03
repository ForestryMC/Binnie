package binnie.extratrees.machines.designer;

import binnie.botany.Botany;
import binnie.botany.ceramic.CeramicDesignSystem;
import binnie.core.util.I18N;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.api.IDesign;
import binnie.extratrees.api.IDesignMaterial;
import binnie.extratrees.api.IDesignSystem;
import binnie.extratrees.carpentry.BlockDesign;
import binnie.extratrees.carpentry.DesignSystem;
import binnie.extratrees.carpentry.EnumDesign;
import binnie.extratrees.carpentry.ModuleCarpentry;
import binnie.extratrees.core.ExtraTreeTexture;
import java.util.Locale;
import net.minecraft.item.ItemStack;

public enum DesignerType {
    WOODWORKER(ExtraTreeTexture.carpenterTexture),
    PANELWORKER(ExtraTreeTexture.panelerTexture),
    GLASSWORKER(ExtraTreeTexture.panelerTexture),
    TILEWORKER(ExtraTreeTexture.tileworkerTexture);

    public String name;
    public String texture;

    DesignerType(String texture) {
        this.name = name().toLowerCase(Locale.ENGLISH);
        this.texture = texture;
    }

    public IDesignSystem getSystem() {
        switch (this) {
            case GLASSWORKER: {
                return DesignSystem.Glass;
            }
            case TILEWORKER: {
                return CeramicDesignSystem.instance;
            }
            default: {
                return DesignSystem.Wood;
            }
        }
    }

    public ItemStack getBlock(IDesignMaterial type1, IDesignMaterial type2, IDesign design) {
        int stackSize = 2;
        if (design == EnumDesign.Blank) {
            type2 = type1;
            stackSize = 1;
        }

        ItemStack stack = ModuleCarpentry.getItemStack(getBlock(), type1, type2, design);
        stack.stackSize = stackSize;
        return stack;
    }

    private BlockDesign getBlock() {
        switch (this) {
            case GLASSWORKER:
                return ExtraTrees.blockStained;

            case PANELWORKER:
                return ExtraTrees.blockPanel;

            case TILEWORKER:
                return Botany.ceramicTile;
        }
        return ExtraTrees.blockCarpentry;
    }

    public ItemStack getDisplayStack(IDesign design) {
        return getBlock(getSystem().getDefaultMaterial(), getSystem().getDefaultMaterial2(), design);
    }

    public String getMaterialTooltip() {
        switch (this) {
            case GLASSWORKER:
                return I18N.localise("extratrees.machine.glassworker.material");

            case PANELWORKER:
                return I18N.localise("extratrees.machine.panelworker.material");

            case TILEWORKER:
                return I18N.localise("extratrees.machine.tileworker.material");

            case WOODWORKER:
                return I18N.localise("extratrees.machine.woodworker.material");
        }
        return "";
    }
}
