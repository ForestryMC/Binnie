package binnie.extrabees.products;

import binnie.Binnie;
import binnie.core.item.IItemEnum;
import binnie.core.util.I18N;
import binnie.extrabees.ExtraBees;
import forestry.api.recipes.RecipeManagers;
import java.awt.Color;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public enum EnumHoneyDrop implements IItemEnum {
    ENERGY(new Color(0x9c4972), new Color(0xe37171), ""),
    ACID(new Color(0x4bb541), new Color(0x49de3c), "acid"),
    POISON(new Color(0xd106b9), new Color(0xff03e2), "poison"),
    APPLE(new Color(0xc75252), new Color(0xc92a2a), "juice"),
    //	CITRUS,
    ICE(new Color(0xaee8e2), new Color(0x96fff5), "liquidnitrogen"),
    MILK(new Color(0xe0e0e0), new Color(0xffffff), "milk"),
    SEED(new Color(0x7cc272), new Color(0xc2bea7), "seedoil"),
    ALCOHOL(new Color(0xdbe84d), new Color(0xa5e84d), "short.mead"),
    //	FRUIT,
    //	VEGETABLE,
    //	PUMPKIN,
    //	MELON,
    RED(new Color(0xcc4c4c), new Color(0xff0000), "for.honey"),
    YELLOW(new Color(0xe5e533), new Color(0xffdd00), "for.honey"),
    BLUE(new Color(0x99b2f2), new Color(0x0022ff), "for.honey"),
    GREEN(new Color(0x667f33), new Color(0x009900), "for.honey"),
    BLACK(new Color(0x191919), new Color(0x575757), "for.honey"),
    WHITE(new Color(0xd6d6d6), new Color(0xffffff), "for.honey"),
    BROWN(new Color(0x7f664c), new Color(0x5c350f), "for.honey"),
    ORANGE(new Color(0xf2b233), new Color(0xff9d00), "for.honey"),
    CYAN(new Color(0x4c99b2), new Color(0x00ffe5), "for.honey"),
    PURPLE(new Color(0xb266e5), new Color(0xae00ff), "for.honey"),
    GRAY(new Color(0x4c4c4c), new Color(0xbababa), "for.honey"),
    LIGHTBLUE(new Color(0x99b2f2), new Color(0x009dff), "for.honey"),
    PINK(new Color(0xf2b2cc), new Color(0xff80df), "for.honey"),
    LIMEGREEN(new Color(0x7fcc19), new Color(0x00ff08), "for.honey"),
    MAGENTA(new Color(0xe57fd8), new Color(0xff00cc), "for.honey"),
    LIGHTGRAY(new Color(0x999999), new Color(0xc9c9c9), "for.honey");

    public final int primaryColor;
    public final int secondaryColor;

    protected String liquidName;
    protected ItemStack remenant;

    EnumHoneyDrop(Color primaryColor, Color secondaryColor, String liquid) {
        this.primaryColor = primaryColor.getRGB();
        this.secondaryColor = secondaryColor.getRGB();
        liquidName = liquid;
    }

    public static EnumHoneyDrop get(ItemStack itemStack) {
        int i = itemStack.getItemDamage();
        if (i >= 0 && i < values().length) {
            return values()[i];
        }
        return values()[0];
    }

    public void addRemenant(ItemStack stack) {
        remenant = stack;
    }

    public void addRecipe() {
        FluidStack liquid = Binnie.Liquid.getLiquidStack(liquidName, 200);
        if (liquid == null) {
            return;
        }
        RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[] {get(1)}, liquid, remenant, 100);
    }

    @Override
    public boolean isActive() {
        return liquidName == null || FluidRegistry.isFluidRegistered(liquidName);
    }

    @Override
    public ItemStack get(int count) {
        return new ItemStack(ExtraBees.honeyDrop, count, ordinal());
    }

    @Override
    public String getName(ItemStack itemStack) {
        return I18N.localise("extrabees.item.honeydrop." + name().toLowerCase());
    }
}
