package binnie.extrabees.core;

import binnie.Binnie;
import binnie.core.Mods;
import binnie.core.item.IItemMisc;
import binnie.extrabees.ExtraBees;
import forestry.api.recipes.RecipeManagers;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

public enum ExtraBeeItems implements IItemMisc {
    ScentedGear("Scented Gear", "scentedGear"),
    DiamondShard("Diamond Fragment", "diamondShard"),
    EmeraldShard("Emerald Fragment", "emeraldShard"),
    RubyShard("Ruby Fragment", "rubyShard"),
    SapphireShard("Sapphire Fragment", "sapphireShard"),
    LapisShard("Lapis Fragment", "lapisShard"),
    IronDust("Iron Grains", "ironDust"),
    GoldDust("Gold Grains", "goldDust"),
    SilverDust("Silver Grains", "silverDust"),
    PlatinumDust("Platinum Grains", "platinumDust"),
    CopperDust("Copper Grains", "copperDust"),
    TinDust("Tin Grains", "tinDust"),
    NickelDust("Nickel Grains", "nickelDust"),
    LeadDust("Lead Grains", "leadDust"),
    ZincDust("Zinc Grains", "zincDust"),
    TitaniumDust("Titanium Grains", "titaniumDust"),
    TungstenDust("Tungsten Grains", "tungstenDust"),
    UraniumDust("Radioactive Fragments", "radioactiveDust"),
    CoalDust("Coal Grains", "coalDust"),
    RedDye("Red Dye", "dyeRed"),
    YellowDye("Yellow Dye", "dyeYellow"),
    BlueDye("Blue Dye", "dyeBlue"),
    GreenDye("Green Dye", "dyeGreen"),
    WhiteDye("White Dye", "dyeWhite"),
    BlackDye("Black Dye", "dyeBlack"),
    BrownDye("Brown Dye", "dyeBrown"),
    ClayDust("Clay Dust", "clayDust"),
    YelloriumDust("Yellorium Grains", "yelloriumDust"),
    BlutoniumDust("Blutonium Grains", "blutoniumDust"),
    CyaniteDust("Cyanite Grains", "cyaniteDust");

    String name;
    String iconPath;
    //	IIcon icon;
    String metalString;
    String gemString;

    public static void init() {
        OreDictionary.registerOre("dyeRed", ExtraBeeItems.RedDye.get(1));
        OreDictionary.registerOre("dyeYellow", ExtraBeeItems.YellowDye.get(1));
        OreDictionary.registerOre("dyeBlue", ExtraBeeItems.BlueDye.get(1));
        OreDictionary.registerOre("dyeGreen", ExtraBeeItems.GreenDye.get(1));
        OreDictionary.registerOre("dyeBlack", ExtraBeeItems.BlackDye.get(1));
        OreDictionary.registerOre("dyeWhite", ExtraBeeItems.WhiteDye.get(1));
        OreDictionary.registerOre("dyeBrown", ExtraBeeItems.BrownDye.get(1));
    }

    public static void postInit() {
        final ItemStack lapisShard = ExtraBeeItems.LapisShard.get(1);
        GameRegistry.addShapelessRecipe(new ItemStack(Items.DYE, 1, 4), lapisShard, lapisShard, lapisShard, lapisShard);
        for (final ExtraBeeItems item : values()) {
            if (item.metalString != null) {
                ItemStack dust = null;
                ItemStack ingot = null;
                if (!OreDictionary.getOres("ingot" + item.metalString).isEmpty()) {
                    ingot = OreDictionary.getOres("ingot" + item.metalString).get(0).copy();
                }
                if (!OreDictionary.getOres("dust" + item.metalString).isEmpty()) {
                    dust = OreDictionary.getOres("dust" + item.metalString).get(0).copy();
                }
                final ItemStack input = item.get(1);
                if (dust != null) {
                    GameRegistry.addShapelessRecipe(dust, input, input, input, input);
                } else if (ingot != null) {
                    GameRegistry.addShapelessRecipe(ingot, input, input, input, input, input, input, input, input, input);
                } else if (item == ExtraBeeItems.CoalDust) {
                    GameRegistry.addShapelessRecipe(new ItemStack(Items.COAL), input, input, input, input);
                }
            } else if (item.gemString != null) {
                ItemStack gem = null;
                if (!OreDictionary.getOres("gem" + item.gemString).isEmpty()) {
                    gem = OreDictionary.getOres("gem" + item.gemString).get(0);
                }
                final ItemStack input2 = item.get(1);
                if (gem != null) {
                    GameRegistry.addShapelessRecipe(gem.copy(), input2, input2, input2, input2, input2, input2, input2, input2, input2);
                }
            }
        }
        Item woodGear = null;
        try {
            woodGear = (Item) Class.forName("buildcraft.BuildCraftCore").getField("woodenGearItem").get(null);
        } catch (Exception ex) {
        }
        ItemStack gear = new ItemStack(Blocks.PLANKS, 1);
        if (woodGear != null) {
            gear = new ItemStack(woodGear, 1);
        }
        RecipeManagers.carpenterManager.addRecipe(100, Binnie.Liquid.getLiquidStack("for.honey", 500), null, ExtraBeeItems.ScentedGear.get(1), " j ", "bgb", " p ", 'j', Mods.Forestry.item("royalJelly"), 'b', Mods.Forestry.item("beeswax"), 'p', Mods.Forestry.item("pollen"), 'g', gear);
    }

    ExtraBeeItems(final String name, final String iconPath) {
        this.metalString = null;
        this.gemString = null;
        this.name = name;
        this.iconPath = iconPath;
    }

    private void setGem(final String string) {
        this.gemString = string;
    }

    private void setMetal(final String string) {
        this.metalString = string;
    }

    @Override
    public boolean isActive() {
        if (this.metalString != null) {
            return !OreDictionary.getOres("ingot" + this.metalString).isEmpty() || !OreDictionary.getOres("dust" + this.metalString).isEmpty() || this == ExtraBeeItems.CoalDust;
        }
        return this.gemString == null || !OreDictionary.getOres("gem" + this.gemString).isEmpty();
    }

//	@Override
//	public IIcon getIcon(final ItemStack stack) {
//		return this.icon;
//	}
//
//	@SideOnly(Side.CLIENT)
//	@Override
//	public void registerIcons(final IIconRegister register) {
//		this.icon = ExtraBees.proxy.getIcon(register, "misc/" + this.iconPath);
//	}

    @Override
    public void addInformation(final List par3List) {
    }

    @Override
    public String getName(final ItemStack stack) {
        return this.name;
    }

    @Override
    public ItemStack get(final int i) {
        return new ItemStack(ExtraBees.itemMisc, i, this.ordinal());
    }

    static {
        ExtraBeeItems.TinDust.setMetal("Tin");
        ExtraBeeItems.ZincDust.setMetal("Zinc");
        ExtraBeeItems.CopperDust.setMetal("Copper");
        ExtraBeeItems.IronDust.setMetal("Iron");
        ExtraBeeItems.NickelDust.setMetal("Nickel");
        ExtraBeeItems.LeadDust.setMetal("Lead");
        ExtraBeeItems.SilverDust.setMetal("Silver");
        ExtraBeeItems.GoldDust.setMetal("Gold");
        ExtraBeeItems.PlatinumDust.setMetal("Platinum");
        ExtraBeeItems.TungstenDust.setMetal("Tungsten");
        ExtraBeeItems.TitaniumDust.setMetal("Titanium");
        ExtraBeeItems.CoalDust.setMetal("Coal");
        ExtraBeeItems.YelloriumDust.setMetal("Yellorium");
        ExtraBeeItems.BlutoniumDust.setMetal("Blutonium");
        ExtraBeeItems.CyaniteDust.setMetal("Cyanite");
        ExtraBeeItems.DiamondShard.setGem("Diamond");
        ExtraBeeItems.EmeraldShard.setGem("Emerald");
        ExtraBeeItems.RubyShard.setGem("Ruby");
        ExtraBeeItems.SapphireShard.setGem("Sapphire");
    }
}
