package binnie.extratrees.item;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.IInitializable;
import binnie.core.Mods;
import binnie.core.item.ItemMisc;
import binnie.core.liquid.ItemFluidContainer;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.block.ILogType;
import cpw.mods.fml.common.registry.GameRegistry;
import forestry.api.core.Tabs;
import forestry.api.fuels.EngineBronzeFuel;
import forestry.api.fuels.FuelManager;
import forestry.api.recipes.RecipeManagers;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ModuleItems implements IInitializable {
    @Override
    public void preInit() {
        ExtraTrees.itemMisc = Binnie.Item.registerMiscItems(ExtraTreeItems.values(), Tabs.tabArboriculture);
        ExtraTrees.itemDictionary = new ItemDictionary();
        if (BinnieCore.isLepidopteryActive()) {
            ExtraTrees.itemDictionaryLepi = new ItemMothDatabase();
        }

        Binnie.Liquid.createLiquids(ExtraTreeLiquid.values(), ItemFluidContainer.LiquidExtraTree);
        ExtraTrees.itemFood = new ItemFood();

        ExtraTrees.itemHammer = new ItemHammer(false);
        ExtraTrees.itemDurableHammer = new ItemHammer(true);
    }

    @Override
    public void init() {
        OreDictionary.registerOre("pulpWood", ExtraTreeItems.Sawdust.get(1));
        Food.registerOreDictionary();
        OreDictionary.registerOre("cropApple", Items.apple);
        OreDictionary.registerOre("seedWheat", Items.wheat_seeds);

        Food.Crabapple.addJuice(10, 150, 10);
        Food.Orange.addJuice(10, 400, 15);
        Food.Kumquat.addJuice(10, 300, 10);
        Food.Lime.addJuice(10, 300, 10);
        Food.WildCherry.addOil(20, 50, 5);
        Food.SourCherry.addOil(20, 50, 3);
        Food.BlackCherry.addOil(20, 50, 5);
        Food.Blackthorn.addJuice(10, 50, 5);
        Food.CherryPlum.addJuice(10, 100, 60);
        Food.Almond.addOil(20, 80, 5);
        Food.Apricot.addJuice(10, 150, 40);
        Food.Grapefruit.addJuice(10, 500, 15);
        Food.Peach.addJuice(10, 150, 40);
        Food.Satsuma.addJuice(10, 300, 10);
        Food.BuddhaHand.addJuice(10, 400, 15);
        Food.Citron.addJuice(10, 400, 15);
        Food.FingerLime.addJuice(10, 300, 10);
        Food.KeyLime.addJuice(10, 300, 10);
        Food.Manderin.addJuice(10, 400, 10);
        Food.Nectarine.addJuice(10, 150, 40);
        Food.Pomelo.addJuice(10, 300, 10);
        Food.Tangerine.addJuice(10, 300, 10);
        Food.Pear.addJuice(10, 300, 20);
        Food.SandPear.addJuice(10, 200, 10);
        Food.Hazelnut.addOil(20, 150, 5);
        Food.Butternut.addOil(20, 180, 5);
        Food.Beechnut.addOil(20, 100, 4);
        Food.Pecan.addOil(29, 50, 2);
        Food.Banana.addJuice(10, 100, 30);
        Food.RedBanana.addJuice(10, 100, 30);
        Food.Plantain.addJuice(10, 100, 40);
        Food.BrazilNut.addOil(20, 20, 2);
        Food.Fig.addOil(20, 50, 3);
        Food.Acorn.addOil(20, 50, 3);
        Food.Elderberry.addJuice(10, 100, 5);
        Food.Olive.addOil(20, 50, 3);
        Food.GingkoNut.addOil(20, 50, 5);
        Food.Coffee.addOil(15, 20, 2);
        Food.OsangeOrange.addJuice(10, 300, 15);
        Food.Clove.addOil(10, 25, 2);
        Food.Coconut.addOil(20, 300, 25);
        Food.Cashew.addJuice(10, 150, 15);
        Food.Avacado.addJuice(10, 300, 15);
        Food.Nutmeg.addOil(20, 50, 10);
        Food.Allspice.addOil(20, 50, 10);
        Food.Chilli.addJuice(10, 100, 10);
        Food.StarAnise.addOil(20, 100, 10);
        Food.Mango.addJuice(10, 400, 20);
        Food.Starfruit.addJuice(10, 300, 10);
        Food.Candlenut.addJuice(20, 50, 10);
    }

    @Override
    public void postInit() {
        GameRegistry.addRecipe(new ShapedOreRecipe(
                new ItemStack(ExtraTrees.itemDurableHammer, 1, 0),
                "wiw",
                " s ",
                " s ",
                'w',
                Blocks.obsidian,
                'i',
                Items.gold_ingot,
                's',
                Items.stick));

        GameRegistry.addRecipe(new ShapedOreRecipe(
                new ItemStack(ExtraTrees.itemHammer, 1, 0),
                "wiw",
                " s ",
                " s ",
                'w',
                "plankWood",
                'i',
                Items.iron_ingot,
                's',
                Items.stick));

        GameRegistry.addRecipe(
                ExtraTreeItems.ProvenGear.get(1), " s ", "s s", " s ", 's', Mods.forestry.stack("oakStick"));

        GameRegistry.addRecipe(
                ExtraTreeItems.GlassFitting.get(6), "s s", " i ", "s s", 'i', Items.iron_ingot, 's', Items.stick);

        try {
            Item minium = (Item) Class.forName("com.pahimar.ee3.lib.ItemIds")
                    .getField("miniumShard")
                    .get(null);
            GameRegistry.addRecipe(new ShapelessOreRecipe(Food.Papayimar.get(1), minium, "cropPapaya"));
        } catch (Exception ex) {
            // ignored
        }

        RecipeManagers.carpenterManager.addRecipe(
                100,
                Binnie.Liquid.getLiquidStack("water", 2000),
                null,
                new ItemStack(ExtraTrees.itemDictionary),
                "X#X",
                "YEY",
                "RDR",
                '#',
                Blocks.glass_pane,
                'X',
                Items.gold_ingot,
                'Y',
                "ingotCopper",
                'R',
                Items.redstone,
                'D',
                Items.diamond,
                'E',
                Items.emerald);

        RecipeManagers.carpenterManager.addRecipe(
                100,
                Binnie.Liquid.getLiquidStack("water", 2000),
                null,
                new ItemStack(ExtraTrees.itemDictionaryLepi),
                "X#X",
                "YEY",
                "RDR",
                '#',
                Blocks.glass_pane,
                'X',
                Items.gold_ingot,
                'Y',
                "ingotBronze",
                'R',
                Items.redstone,
                'D',
                Items.diamond,
                'E',
                Items.emerald);

        RecipeManagers.stillManager.addRecipe(25, ExtraTreeLiquid.Resin.get(5), ExtraTreeLiquid.Turpentine.get(3));

        RecipeManagers.carpenterManager.addRecipe(
                25,
                ExtraTreeLiquid.Turpentine.get(50),
                null,
                ((ItemMisc) ExtraTrees.itemMisc).getStack(ExtraTreeItems.WoodWax, 4),
                "x",
                'x',
                Mods.forestry.stack("beeswax"));

        if (Binnie.Liquid.getLiquidStack("Creosote Oil", 100) != null) {
            RecipeManagers.carpenterManager.addRecipe(
                    25,
                    Binnie.Liquid.getLiquidStack("Creosote Oil", 50),
                    null,
                    ((ItemMisc) ExtraTrees.itemMisc).getStack(ExtraTreeItems.WoodWax, 1),
                    "x",
                    'x',
                    Mods.forestry.stack("beeswax"));
        }

        for (FluidContainerRegistry.FluidContainerData data :
                FluidContainerRegistry.getRegisteredFluidContainerData()) {
            if (data.fluid.isFluidEqual(Binnie.Liquid.getLiquidStack("water", 0)) && data.fluid.amount == 1000) {
                CraftingManager.getInstance()
                        .addRecipe(
                                Mods.forestry.stack("mulch"),
                                " b ",
                                "bwb",
                                " b ",
                                'b',
                                ExtraTreeItems.Bark.get(1),
                                'w',
                                data.filledContainer.copy());
            }
        }

        FuelManager.bronzeEngineFuel.put(
                ExtraTreeLiquid.Sap.get(1).getFluid(),
                new EngineBronzeFuel(ExtraTreeLiquid.Sap.get(1).getFluid(), 20, 10000, 1));
        FuelManager.bronzeEngineFuel.put(
                ExtraTreeLiquid.Resin.get(1).getFluid(),
                new EngineBronzeFuel(ExtraTreeLiquid.Resin.get(1).getFluid(), 30, 10000, 1));
        for (ILogType.ExtraTreeLog log : ILogType.ExtraTreeLog.values()) {
            log.addRecipe();
        }
    }
}
