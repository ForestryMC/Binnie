package binnie.botany.genetics;

import binnie.Binnie;
import binnie.botany.Botany;
import binnie.botany.core.BotanyCore;
import binnie.botany.flower.*;
import binnie.core.BinnieCore;
import binnie.core.IInitializable;
import forestry.api.apiculture.FlowerManager;
import forestry.api.genetics.AlleleManager;
import forestry.api.recipes.RecipeManagers;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ModuleGenetics implements IInitializable {
    static AlleleEffectNone alleleEffectNone = new AlleleEffectNone();

    @Override
    public void preInit() {
    	binnie.botany.api.FlowerManager.flowerFactory = new FlowerFactory();
        EnumFlowerColor.setupMutations();
        Botany.flower = new BlockFlower();
        Botany.flowerItem = new ItemFlower();
        Botany.pollen = new ItemPollen();
        Botany.seed = new ItemSeed();
        AlleleManager.alleleRegistry.registerSpeciesRoot(BotanyCore.getFlowerRoot());
        AlleleManager.alleleRegistry.registerAllele(ModuleGenetics.alleleEffectNone);
        GameRegistry.register(Botany.flower);
        GameRegistry.register(new ItemBlock(Botany.flower).setRegistryName(Botany.flower.getRegistryName()));
        BinnieCore.proxy.registerTileEntity(TileEntityFlower.class, "botany.tile.flower", null);
        Botany.database = new ItemDictionary();
        Botany.encyclopedia = new ItemEncyclopedia(false);
        Botany.encyclopediaIron = new ItemEncyclopedia(true);
    }

    @Override
    public void init() {
        for (final EnumFlowerColor color : EnumFlowerColor.values()) {
            AlleleManager.alleleRegistry.registerAllele(color.getAllele());
        }
        FlowerDefinition.initFlowers();
        //TODO RENDERING
//		RendererBotany.renderID = RenderingRegistry.getNextAvailableRenderId();
        BinnieCore.proxy.registerBlockRenderer(new RendererBotany());
    }

    @Override
    public void postInit() {
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Botany.encyclopedia), "fff", "fbf", "fff", 'f', new ItemStack(Blocks.RED_FLOWER, 1, 32767), 'b', Items.BOOK));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Botany.encyclopedia), "fff", "fbf", "fff", 'f', new ItemStack(Blocks.YELLOW_FLOWER, 1, 32767), 'b', Items.BOOK));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Botany.encyclopedia), "fff", "fbf", "fff", 'f', new ItemStack(Botany.flower, 1, 32767), 'b', Items.BOOK));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Botany.encyclopediaIron), new ItemStack(Botany.encyclopedia), "ingotIron"));
        FlowerManager.flowerRegistry.registerAcceptableFlower(Botany.flower, "flowersVanilla");
        RecipeManagers.carpenterManager.addRecipe(100, Binnie.Liquid.getLiquidStack("water", 2000), null, new ItemStack(Botany.database), "X#X", "YEY", "RDR", '#', Blocks.GLASS_PANE, 'X', Items.GOLD_INGOT, 'Y', Items.GOLD_NUGGET, 'R', Items.REDSTONE, 'D', Items.DIAMOND, 'E', Items.EMERALD);
    }

}
