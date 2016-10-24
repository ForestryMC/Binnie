package binnie.extrabees.products;

import binnie.core.Mods;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.core.ExtraBeeItems;
import forestry.api.core.IModelManager;
import forestry.api.core.Tabs;
import forestry.core.items.IColoredItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Locale;

public class ItemHoneyComb extends ItemProduct implements IColoredItem {

    public ItemHoneyComb() {
        super(EnumHoneyComb.values());
        this.setCreativeTab(Tabs.tabApiculture);
        this.setUnlocalizedName("honeyComb");
        setRegistryName("honeyComb");
    }

    public static void addSubtypes() {
        final ItemStack beeswax = Mods.Forestry.stack("beeswax");
        final ItemStack honeyDrop = Mods.Forestry.stack("honeyDrop");
        OreDictionary.registerOre("ingotIron", Items.IRON_INGOT);
        OreDictionary.registerOre("ingotGold", Items.GOLD_INGOT);
        OreDictionary.registerOre("gemDiamond", Items.DIAMOND);
        OreDictionary.registerOre("gemEmerald", Items.EMERALD);
        OreDictionary.registerOre("gemLapis", new ItemStack(Items.DYE, 1, 4));
        EnumHoneyComb.BARREN.addProduct(beeswax, 1.00f);
        EnumHoneyComb.BARREN.addProduct(honeyDrop, 0.50f);
        EnumHoneyComb.ROTTEN.addProduct(beeswax, 0.20f);
        EnumHoneyComb.ROTTEN.addProduct(honeyDrop, 0.20f);
        EnumHoneyComb.ROTTEN.addProduct(new ItemStack(Items.ROTTEN_FLESH, 1, 0), 0.80f);
        EnumHoneyComb.BONE.addProduct(beeswax, 0.20f);
        EnumHoneyComb.BONE.addProduct(honeyDrop, 0.20f);
        EnumHoneyComb.BONE.addProduct(new ItemStack(Items.DYE, 1, 15), 0.80f);
        EnumHoneyComb.OIL.tryAddProduct(EnumPropolis.OIL, 0.60f);
        EnumHoneyComb.OIL.addProduct(honeyDrop, 0.75f);
        EnumHoneyComb.COAL.addProduct(beeswax, 0.80f);
        EnumHoneyComb.COAL.addProduct(honeyDrop, 0.75f);
        EnumHoneyComb.COAL.tryAddProduct(ExtraBeeItems.CoalDust, 1.00f);
        EnumHoneyComb.WATER.tryAddProduct(EnumPropolis.WATER, 1.00f);
        EnumHoneyComb.WATER.addProduct(honeyDrop, 0.90f);
        EnumHoneyComb.STONE.addProduct(beeswax, 0.50f);
        EnumHoneyComb.STONE.addProduct(honeyDrop, 0.25f);
        EnumHoneyComb.MILK.tryAddProduct(EnumHoneyDrop.MILK, 1.00f);
        EnumHoneyComb.MILK.addProduct(honeyDrop, 0.90f);
        EnumHoneyComb.FRUIT.tryAddProduct(EnumHoneyDrop.APPLE, 1.00f);
        EnumHoneyComb.FRUIT.addProduct(honeyDrop, 0.90f);
        EnumHoneyComb.SEED.tryAddProduct(EnumHoneyDrop.SEED, 1.00f);
        EnumHoneyComb.SEED.addProduct(honeyDrop, 0.90f);
        EnumHoneyComb.ALCOHOL.tryAddProduct(EnumHoneyDrop.ALCOHOL, 1.00f);
        EnumHoneyComb.ALCOHOL.addProduct(honeyDrop, 0.90f);
        EnumHoneyComb.FUEL.tryAddProduct(EnumPropolis.FUEL, 0.60f);
        EnumHoneyComb.FUEL.addProduct(honeyDrop, 0.50f);
        EnumHoneyComb.CREOSOTE.tryAddProduct(EnumPropolis.CREOSOTE, 0.70f);
        EnumHoneyComb.CREOSOTE.addProduct(honeyDrop, 0.50f);
        EnumHoneyComb.LATEX.addProduct(honeyDrop, 0.50f);
        EnumHoneyComb.LATEX.addProduct(beeswax, 0.85f);
        if (!OreDictionary.getOres("itemRubber").isEmpty()) {
            EnumHoneyComb.LATEX.tryAddProduct(OreDictionary.getOres("itemRubber").get(0), 1.00f);
        } else {
            EnumHoneyComb.LATEX.active = false;
        }
        EnumHoneyComb.REDSTONE.addProduct(beeswax, 0.80f);
        EnumHoneyComb.REDSTONE.addProduct(new ItemStack(Items.REDSTONE, 1, 0), 1.00f);
        EnumHoneyComb.REDSTONE.addProduct(honeyDrop, 0.50f);
        EnumHoneyComb.RESIN.addProduct(beeswax, 1.00f);
        EnumHoneyComb.RESIN.tryAddProduct(Mods.IC2.stack("itemHarz"), 1.00f);
        EnumHoneyComb.RESIN.tryAddProduct(Mods.IC2.stack("itemHarz"), 0.50f);
        EnumHoneyComb.IC2ENERGY.addProduct(beeswax, 0.80f);
        EnumHoneyComb.IC2ENERGY.addProduct(new ItemStack(Items.REDSTONE, 1, 0), 0.75f);
        EnumHoneyComb.IC2ENERGY.tryAddProduct(EnumHoneyDrop.ENERGY, 1.00f);
        EnumHoneyComb.IRON.copyProducts(EnumHoneyComb.STONE);
        EnumHoneyComb.IRON.tryAddProduct(ExtraBeeItems.IronDust, 1.00f);
        EnumHoneyComb.GOLD.copyProducts(EnumHoneyComb.STONE);
        EnumHoneyComb.GOLD.tryAddProduct(ExtraBeeItems.GoldDust, 1.00f);
        EnumHoneyComb.COPPER.copyProducts(EnumHoneyComb.STONE);
        EnumHoneyComb.COPPER.tryAddProduct(ExtraBeeItems.CopperDust, 1.00f);
        EnumHoneyComb.TIN.copyProducts(EnumHoneyComb.STONE);
        EnumHoneyComb.TIN.tryAddProduct(ExtraBeeItems.TinDust, 1.00f);
        EnumHoneyComb.NICKEL.copyProducts(EnumHoneyComb.STONE);
        EnumHoneyComb.NICKEL.tryAddProduct(ExtraBeeItems.NickelDust, 1.00f);
        EnumHoneyComb.SILVER.copyProducts(EnumHoneyComb.STONE);
        EnumHoneyComb.SILVER.tryAddProduct(ExtraBeeItems.SilverDust, 1.00f);
        EnumHoneyComb.URANIUM.copyProducts(EnumHoneyComb.STONE);
        if (!OreDictionary.getOres("crushedUranium").isEmpty()) {
            EnumHoneyComb.URANIUM.tryAddProduct(OreDictionary.getOres("crushedUranium").get(0), 0.50f);
        }
        EnumHoneyComb.CLAY.addProduct(beeswax, 0.25f);
        EnumHoneyComb.CLAY.addProduct(honeyDrop, 0.80f);
        EnumHoneyComb.CLAY.addProduct(new ItemStack(Items.CLAY_BALL), 0.80f);
        EnumHoneyComb.OLD.addProduct(beeswax, 1.00f);
        EnumHoneyComb.OLD.addProduct(honeyDrop, 0.90f);
        EnumHoneyComb.FUNGAL.addProduct(beeswax, 0.90f);
        EnumHoneyComb.FUNGAL.addProduct(new ItemStack(Blocks.BROWN_MUSHROOM_BLOCK, 1, 0), 1.00f);
        EnumHoneyComb.FUNGAL.addProduct(new ItemStack(Blocks.RED_MUSHROOM_BLOCK, 1, 0), 0.75f);
        EnumHoneyComb.ACIDIC.addProduct(beeswax, 0.80f);
        EnumHoneyComb.ACIDIC.tryAddProduct(EnumHoneyDrop.ACID, 0.50f);
        if (!OreDictionary.getOres("dustSulfur").isEmpty()) {
            EnumHoneyComb.ACIDIC.addProduct(OreDictionary.getOres("dustSulfur").get(0), 0.75f);
        }
        EnumHoneyComb.VENOMOUS.addProduct(beeswax, 0.80f);
        EnumHoneyComb.VENOMOUS.tryAddProduct(EnumHoneyDrop.POISON, 0.80f);
        EnumHoneyComb.SLIME.addProduct(beeswax, 1.00f);
        EnumHoneyComb.SLIME.addProduct(honeyDrop, 0.75f);
        EnumHoneyComb.SLIME.addProduct(new ItemStack(Items.SLIME_BALL, 1, 0), 0.75f);
        EnumHoneyComb.BLAZE.addProduct(beeswax, 0.75f);
        EnumHoneyComb.BLAZE.addProduct(new ItemStack(Items.BLAZE_POWDER, 1, 0), 1.00f);
        EnumHoneyComb.COFFEE.addProduct(beeswax, 0.90f);
        EnumHoneyComb.COFFEE.addProduct(honeyDrop, 0.75f);
        EnumHoneyComb.COFFEE.tryAddProduct(Mods.IC2.stack("itemCofeePowder"), 0.75f);
        EnumHoneyComb.GLACIAL.tryAddProduct(EnumHoneyDrop.ICE, 0.80f);
        EnumHoneyComb.GLACIAL.addProduct(honeyDrop, 0.75f);
        EnumHoneyComb.SHADOW.addProduct(honeyDrop, 0.50f);
        if (!OreDictionary.getOres("dustObsidian").isEmpty()) {
            EnumHoneyComb.SHADOW.tryAddProduct(OreDictionary.getOres("dustObsidian").get(0), 0.75f);
        } else {
            EnumHoneyComb.SHADOW.active = false;
        }
        EnumHoneyComb.LEAD.copyProducts(EnumHoneyComb.STONE);
        EnumHoneyComb.LEAD.tryAddProduct(ExtraBeeItems.LeadDust, 1.00f);
        EnumHoneyComb.ZINC.copyProducts(EnumHoneyComb.STONE);
        EnumHoneyComb.ZINC.tryAddProduct(ExtraBeeItems.ZincDust, 1.00f);
        EnumHoneyComb.TITANIUM.copyProducts(EnumHoneyComb.STONE);
        EnumHoneyComb.TITANIUM.tryAddProduct(ExtraBeeItems.TitaniumDust, 1.00f);
        EnumHoneyComb.TUNGSTEN.copyProducts(EnumHoneyComb.STONE);
        EnumHoneyComb.TUNGSTEN.tryAddProduct(ExtraBeeItems.TungstenDust, 1.00f);
        EnumHoneyComb.PLATINUM.copyProducts(EnumHoneyComb.STONE);
        EnumHoneyComb.PLATINUM.tryAddProduct(ExtraBeeItems.PlatinumDust, 1.00f);
        EnumHoneyComb.LAPIS.copyProducts(EnumHoneyComb.STONE);
        EnumHoneyComb.LAPIS.addProduct(new ItemStack(Items.DYE, 6, 4), 1.00f);
        EnumHoneyComb.EMERALD.copyProducts(EnumHoneyComb.STONE);
        EnumHoneyComb.EMERALD.tryAddProduct(ExtraBeeItems.EmeraldShard, 1.00f);
        EnumHoneyComb.RUBY.copyProducts(EnumHoneyComb.STONE);
        EnumHoneyComb.RUBY.tryAddProduct(ExtraBeeItems.RubyShard, 1.00f);
        EnumHoneyComb.SAPPHIRE.copyProducts(EnumHoneyComb.STONE);
        EnumHoneyComb.SAPPHIRE.tryAddProduct(ExtraBeeItems.SapphireShard, 1.00f);
        EnumHoneyComb.DIAMOND.copyProducts(EnumHoneyComb.STONE);
        EnumHoneyComb.DIAMOND.tryAddProduct(ExtraBeeItems.DiamondShard, 1.00f);
        EnumHoneyComb.RED.addProduct(honeyDrop, 0.80f);
        EnumHoneyComb.RED.addProduct(beeswax, 0.80f);
        EnumHoneyComb.GLOWSTONE.addProduct(honeyDrop, 0.25f);
        EnumHoneyComb.GLOWSTONE.addProduct(new ItemStack(Items.GLOWSTONE_DUST), 1.00f);
        EnumHoneyComb.SALTPETER.addProduct(honeyDrop, 0.25f);
        EnumHoneyComb.SALTPETER.tryAddProduct(getOreDictionary("dustSaltpeter"), 1.00f);
        EnumHoneyComb.COMPOST.addProduct(honeyDrop, 0.25f);
        EnumHoneyComb.COMPOST.tryAddProduct(Mods.Forestry.stack("fertilizerBio"), 1.00f);
        EnumHoneyComb.SAWDUST.addProduct(honeyDrop, 0.25f);
        if (!OreDictionary.getOres("dustSawdust").isEmpty()) {
            EnumHoneyComb.SAWDUST.tryAddProduct(OreDictionary.getOres("dustSawdust").get(0), 1.00f);
        } else if (!OreDictionary.getOres("sawdust").isEmpty()) {
            EnumHoneyComb.SAWDUST.tryAddProduct(OreDictionary.getOres("sawdust").get(0), 1.00f);
        }
        EnumHoneyComb.CERTUS.addProduct(honeyDrop, 0.25f);
        EnumHoneyComb.CERTUS.addProduct(new ItemStack(Items.QUARTZ), 0.25f);
        if (!OreDictionary.getOres("dustCertusQuartz").isEmpty()) {
            EnumHoneyComb.CERTUS.tryAddProduct(OreDictionary.getOres("dustCertusQuartz").get(0), 0.20f);
        }
        EnumHoneyComb.ENDERPEARL.addProduct(honeyDrop, 0.25f);
        if (!OreDictionary.getOres("dustEnderPearl").isEmpty()) {
            EnumHoneyComb.ENDERPEARL.tryAddProduct(OreDictionary.getOres("dustEnderPearl").get(0), 0.25f);
        }
        EnumHoneyComb.YELLORIUM.copyProducts(EnumHoneyComb.STONE);
        EnumHoneyComb.CYANITE.copyProducts(EnumHoneyComb.STONE);
        EnumHoneyComb.BLUTONIUM.copyProducts(EnumHoneyComb.STONE);
        EnumHoneyComb.YELLORIUM.tryAddProduct(ExtraBeeItems.YelloriumDust, 0.25f);
        EnumHoneyComb.CYANITE.tryAddProduct(ExtraBeeItems.CyaniteDust, 0.25f);
        EnumHoneyComb.BLUTONIUM.tryAddProduct(ExtraBeeItems.BlutoniumDust, 0.25f);
        OreDictionary.registerOre("beeComb", new ItemStack(ExtraBees.comb, 1, 32767));
        for (int i = 0; i < 16; ++i) {
            final EnumHoneyComb type = EnumHoneyComb.values()[EnumHoneyComb.RED.ordinal() + i];
            if (type != EnumHoneyComb.RED) {
                type.copyProducts(EnumHoneyComb.RED);
            }
        }
        for (int i = 0; i < 16; ++i) {
            final EnumHoneyComb type = EnumHoneyComb.values()[EnumHoneyComb.RED.ordinal() + i];
            final EnumHoneyDrop drop = EnumHoneyDrop.values()[EnumHoneyDrop.RED.ordinal() + i];
            final int[] dyeC = {1, 11, 4, 2, 0, 15, 3, 14, 6, 5, 8, 12, 9, 10, 13, 7};
            final int k = dyeC[i];
            ItemStack dye = new ItemStack(Items.DYE, 1, k);
            switch (k) {
                case 0: {
                    dye = ExtraBeeItems.BlackDye.get(1);
                    break;
                }
                case 1: {
                    dye = ExtraBeeItems.RedDye.get(1);
                    break;
                }
                case 2: {
                    dye = ExtraBeeItems.GreenDye.get(1);
                    break;
                }
                case 3: {
                    dye = ExtraBeeItems.BrownDye.get(1);
                    break;
                }
                case 4: {
                    dye = ExtraBeeItems.BlueDye.get(1);
                    break;
                }
                case 11: {
                    dye = ExtraBeeItems.YellowDye.get(1);
                    break;
                }
                case 15: {
                    dye = ExtraBeeItems.WhiteDye.get(1);
                    break;
                }
            }
            type.addProduct(drop.get(1), 1.00f);
            drop.addRemenant(dye);
        }
    }

    private static ItemStack getOreDictionary(final String string) {
        if (OreDictionary.getOres(string).size() > 0) {
            return OreDictionary.getOres(string).get(0);
        }
        return null;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getColorFromItemstack(ItemStack stack, int tintIndex) {
        if (EnumHoneyComb.get(stack) == null) {
            return 16777215;
        }
        if (tintIndex == 1) {
            return EnumHoneyComb.get(stack).colour[0];
        }
        return EnumHoneyComb.get(stack).colour[1];
    }

    public enum VanillaComb {
        HONEY,
        COCOA,
        SIMMERING,
        STRINGY,
        FROZEN,
        DRIPPING,
        SILKY,
        PARCHED,
        MYSTERIOUS,
        IRRADIATED,
        POWDERY,
        REDDENED,
        DARKENED,
        OMEGA,
        WHEATEN,
        MOSSY,
        QUARTZ;

        public ItemStack get() {
            return new ItemStack(Mods.Forestry.item("beeCombs"), 1, this.ordinal());
        }
    }
}
