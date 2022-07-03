package binnie.extrabees.products;

import binnie.core.item.IItemEnum;
import binnie.core.util.I18N;
import binnie.extrabees.ExtraBees;
import forestry.api.recipes.RecipeManagers;
import java.awt.Color;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import net.minecraft.item.ItemStack;

public enum EnumHoneyComb implements IItemEnum {
    BARREN(new Color(0x736c44), new Color(0xc2bea7)),
    ROTTEN(new Color(0x3e5221), new Color(0xb1cc89)),
    BONE(new Color(0xc4c4af), new Color(0xdedec1)),
    OIL(new Color(0x060608), new Color(0x2c2b36)),
    COAL(new Color(0x9e9478), new Color(0x38311e)),
    FUEL(new Color(0x9c6f40), new Color(0xffc400)),
    WATER(new Color(0x2732cf), new Color(0x79a8c9)),
    MILK(new Color(0xd7d9c7), new Color(0xffffff)),
    FRUIT(new Color(0x7d2934), new Color(0xdb4f62)),
    SEED(new Color(0x344f33), new Color(0x71cc6e)),
    ALCOHOL(new Color(0x418521), new Color(0xded94e)),
    STONE(new Color(0x8c8c91), new Color(0xc6c6cc)),
    REDSTONE(new Color(0xfa9696), new Color(0xe61010)),
    RESIN(new Color(0xffc74f), new Color(0xc98a00)),
    IC2ENERGY(new Color(0xe9f50f), new Color(0x20b3c9)),
    IRON(new Color(0x363534), new Color(0xa87058)),
    GOLD(new Color(0x363534), new Color(0xe6cc0b)),
    COPPER(new Color(0x363534), new Color(0xd16308)),
    TIN(new Color(0x363534), new Color(0xbdb1bd)),
    SILVER(new Color(0x363534), new Color(0xdbdbdb)),
    //	BRONZE,
    URANIUM(new Color(0x1eff00), new Color(0x41ab33)),
    CLAY(new Color(0x6b563a), new Color(0xb0c0d6)),
    OLD(new Color(0x453314), new Color(0xb39664)),
    FUNGAL(new Color(0x6e654b), new Color(0x2b9443)),
    CREOSOTE(new Color(0x9c810c), new Color(0xbdaa57)),
    LATEX(new Color(0x595541), new Color(0xa8a285)),
    ACIDIC(new Color(0x348543), new Color(0x14f73e)),
    VENOMOUS(new Color(0x7d187d), new Color(0xff33ff)),
    SLIME(new Color(0x3b473c), new Color(0x80d185)),
    BLAZE(new Color(0xff6a00), new Color(0xffcc00)),
    COFFEE(new Color(0x54381d), new Color(0xb37f4b)),
    GLACIAL(new Color(0x4e8787), new Color(0xcbf2f2)),
    //	MINT,
    //	CITRUS,
    //	PEAT,
    SHADOW(new Color(0x000000), new Color(0x361835)),
    LEAD(new Color(0x363534), new Color(0x9a809c)),
    //	BRASS,
    //	ELECTRUM,
    ZINC(new Color(0x363534), new Color(0xedebff)),
    TITANIUM(new Color(0x363534), new Color(0xb0aae3)),
    TUNGSTEN(new Color(0x363534), new Color(0x131214)),
    //	STEEL,
    //	IRIDIUM,
    PLATINUM(new Color(0x363534), new Color(0x9a809c)),
    LAPIS(new Color(0x363534), new Color(0x3d2cdb)),
    //	SODALITE,
    //	PYRITE,
    //	BAUXITE,
    //	CINNABAR,
    //	SPHALERITE,
    EMERALD(new Color(0x363534), new Color(0x1cff03)),
    RUBY(new Color(0x363534), new Color(0xd60000)),
    SAPPHIRE(new Color(0x363534), new Color(0xa47ff)),
    //	OLIVINE,
    DIAMOND(new Color(0x363534), new Color(0x7fbdfa)),
    RED(new Color(0xcc4c4c), new Color(0xff0000)),
    YELLOW(new Color(0xe5e533), new Color(0xffdd00)),
    BLUE(new Color(0x99b2f2), new Color(0x0022ff)),
    GREEN(new Color(0x667f33), new Color(0x009900)),
    BLACK(new Color(0x191919), new Color(0x575757)),
    WHITE(new Color(0xd6d6d6), new Color(0xffffff)),
    BROWN(new Color(0x7f664c), new Color(0x5c350f)),
    ORANGE(new Color(0xf2b233), new Color(0xff9d00)),
    CYAN(new Color(0x4c99b2), new Color(0x00ffe5)),
    PURPLE(new Color(0xb266e5), new Color(0xae00ff)),
    GRAY(new Color(0x4c4c4c), new Color(0xbababa)),
    LIGHTBLUE(new Color(0x99b2f2), new Color(0x009dff)),
    PINK(new Color(0xf2b2cc), new Color(0xff80df)),
    LIMEGREEN(new Color(0x7fcc19), new Color(0x00ff08)),
    MAGENTA(new Color(0xe57fd8), new Color(0xff00cc)),
    LIGHTGRAY(new Color(0x999999), new Color(0xc9c9c9)),
    NICKEL(new Color(0x363534), new Color(0xffdefc)),
    //	INVAR,
    GLOWSTONE(new Color(0xa69c5e), new Color(0xe0c409)),
    SALTPETER(new Color(0xa69c5e), new Color(0xe0c409)),
    // PULP,
    // MULCH,
    COMPOST(new Color(0x423308), new Color(0x6b5e3b)),
    SAWDUST(new Color(0xbfaa71), new Color(0xf2d37e)),
    CERTUS(new Color(0xc6d0ff), new Color(0x394d63)),
    ENDERPEARL(new Color(0x349786), new Color(0x32620)),
    YELLORIUM(new Color(0x27204d), new Color(14019840)),
    CYANITE(new Color(0x27204d), new Color(0x86ed)),
    BLUTONIUM(new Color(0x27204d), new Color(0x1b00e6));

    public Map<ItemStack, Float> products;
    public final int primaryColor;
    public final int secondaryColor;
    public final String name;

    protected boolean active;

    EnumHoneyComb(Color primaryColor, Color secondaryColor) {
        this.primaryColor = primaryColor.getRGB();
        this.secondaryColor = secondaryColor.getRGB();
        name = toString().toLowerCase(Locale.ENGLISH);
        products = new LinkedHashMap<>();
    }

    public static EnumHoneyComb get(ItemStack itemStack) {
        int i = itemStack.getItemDamage();
        if (i >= 0 && i < values().length) {
            return values()[i];
        }
        return values()[0];
    }

    public void addRecipe() {
        RecipeManagers.centrifugeManager.addRecipe(20, get(1), products);
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public ItemStack get(int count) {
        return new ItemStack(ExtraBees.comb, count, ordinal());
    }

    @Override
    public String getName(ItemStack stack) {
        return I18N.localise("extrabees.item.comb." + name);
    }

    public boolean addProduct(ItemStack item, Float chance) {
        if (item == null) {
            return false;
        }
        products.put(item.copy(), chance);
        return true;
    }

    public void tryAddProduct(ItemStack item, Float chance) {
        active = addProduct(item, chance);
    }

    public void tryAddProduct(IItemEnum type, Float chance) {
        tryAddProduct(type.get(1), chance);
        active = active && type.isActive();
    }

    public void copyProducts(EnumHoneyComb comb) {
        products.putAll(comb.products);
    }
}
