package binnie.extrabees.products;

import binnie.Binnie;
import binnie.core.item.IItemEnum;
import binnie.core.util.I18N;
import binnie.extrabees.ExtraBees;
import forestry.api.recipes.RecipeManagers;
import java.awt.Color;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public enum EnumPropolis implements IItemEnum {
    WATER(new Color(0x24b3c9), new Color(0xc2bea7), "Water"),
    OIL(new Color(0x172f33), new Color(0xc2bea7), "oil"),
    FUEL(new Color(0xa38d12), new Color(0xc2bea7), "fuel"),
    //	MILK,
    //	FRUIT,
    //	SEED,
    //	ALCOHOL,
    CREOSOTE(new Color(0x877501), new Color(0xbda613), "creosote");
    //	GLACIAL,
    //	PEAT

    public final int secondaryColor;
    public final int primaryColor;
    public final String liquidName;

    EnumPropolis(Color primaryColor, Color secondaryColor, String liquidName) {
        this.primaryColor = primaryColor.getRGB();
        this.secondaryColor = secondaryColor.getRGB();
        this.liquidName = liquidName;
    }

    public static EnumPropolis get(ItemStack itemStack) {
        int i = itemStack.getItemDamage();
        if (i >= 0 && i < values().length) {
            return values()[i];
        }
        return values()[0];
    }

    public void addRecipe() {
        FluidStack liquid = Binnie.Liquid.getLiquidStack(liquidName, 500);
        if (liquid == null) {
            return;
        }
        RecipeManagers.squeezerManager.addRecipe(20, new ItemStack[] {get(1)}, liquid, null, 0);
    }

    @Override
    public boolean isActive() {
        return Binnie.Liquid.getLiquidStack(liquidName, 100) != null;
    }

    @Override
    public ItemStack get(int count) {
        return new ItemStack(ExtraBees.propolis, count, ordinal());
    }

    @Override
    public String getName(ItemStack itemStack) {
        return I18N.localise("extrabees.item.propolis." + name().toLowerCase());
    }
}
