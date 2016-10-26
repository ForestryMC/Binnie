package binnie.botany.genetics;

import binnie.Binnie;
import binnie.botany.Botany;
import binnie.botany.api.EnumFlowerStage;
import binnie.botany.core.BotanyCore;
import binnie.botany.flower.*;
import binnie.core.BinnieCore;
import binnie.core.IInitializable;
import forestry.api.apiculture.FlowerManager;
import forestry.api.genetics.AlleleManager;
import forestry.api.recipes.RecipeManagers;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ModuleGenetics implements IInitializable {
    static AlleleEffectNone alleleEffectNone = new AlleleEffectNone();

    @Override
    public void preInit() {
    	/* INIT API*/
    	binnie.botany.api.FlowerManager.flowerFactory = new FlowerFactory();
        AlleleManager.alleleRegistry.registerSpeciesRoot(BotanyCore.getFlowerRoot());
        AlleleManager.alleleRegistry.registerAllele(ModuleGenetics.alleleEffectNone);
        EnumFlowerColor.setupMutations();
        FlowerDefinition.preInitFlowers();
        
        Botany.flower = new BlockFlower();
        Botany.proxy.registerBlock(Botany.flower);
        BinnieCore.proxy.registerTileEntity(TileEntityFlower.class, "botany.tile.flower", null);
        
        Botany.flowerItem = new ItemBotany("itemFlower", EnumFlowerStage.FLOWER, "");
        Botany.pollen = new ItemBotany("pollen", EnumFlowerStage.POLLEN, "Pollen");
        Botany.seed = new ItemBotany("seed", EnumFlowerStage.SEED, " Germling");
        Botany.database = new ItemDictionary();
        Botany.encyclopedia = new ItemEncyclopedia(false);
        Botany.encyclopediaIron = new ItemEncyclopedia(true);
    }

    @Override
    public void init() {
    	EnumFlowerColor.initColours();
        FlowerDefinition.initFlowers();
    }

    @Override
    public void postInit() {
    	OreDictionary.registerOre("flower", new ItemStack(Blocks.RED_FLOWER, 1, OreDictionary.WILDCARD_VALUE));
    	OreDictionary.registerOre("flower", new ItemStack(Blocks.YELLOW_FLOWER, 1, OreDictionary.WILDCARD_VALUE));
    	OreDictionary.registerOre("flower", new ItemStack(Blocks.DOUBLE_PLANT, 1, 0));
    	OreDictionary.registerOre("flower", new ItemStack(Blocks.DOUBLE_PLANT, 1, 1));
    	OreDictionary.registerOre("flower", new ItemStack(Blocks.DOUBLE_PLANT, 1, 4));
    	OreDictionary.registerOre("flower", new ItemStack(Blocks.DOUBLE_PLANT, 1, 5));
    	OreDictionary.registerOre("flower", new ItemStack(Botany.flower, 1, OreDictionary.WILDCARD_VALUE));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Botany.encyclopedia), 
        		"fff", 
        		"fbf", 
        		"fff", 
        		'f', "flower", 
        		'b', Items.BOOK));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Botany.encyclopediaIron), new ItemStack(Botany.encyclopedia), "ingotIron"));
        FlowerManager.flowerRegistry.registerAcceptableFlower(Botany.flower, "flowersVanilla");
        RecipeManagers.carpenterManager.addRecipe(100, Binnie.Liquid.getFluidStack("water", 2000), null, new ItemStack(Botany.database), 
        		"X#X", 
        		"YEY", 
        		"RDR", 
        		'#', Blocks.GLASS_PANE, 
        		'X', Items.GOLD_INGOT, 
        		'Y', Items.GOLD_NUGGET, 
        		'R', Items.REDSTONE, 
        		'D', Items.DIAMOND, 
        		'E', Items.EMERALD);
    }

}
