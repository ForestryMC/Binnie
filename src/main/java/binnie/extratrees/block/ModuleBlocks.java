package binnie.extratrees.block;

import binnie.core.BinnieCore;
import binnie.core.IInitializable;
import binnie.core.Mods;
import binnie.core.block.ItemMetadata;
import binnie.core.block.ItemMetadataRenderer;
import binnie.core.block.TileEntityMetadata;
import binnie.core.liquid.ILiquidType;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.block.decor.BlockFence;
import binnie.extratrees.block.decor.BlockGate;
import binnie.extratrees.block.decor.BlockMultiFence;
import binnie.extratrees.block.decor.FenceRenderer;
import binnie.extratrees.block.decor.FenceType;
import binnie.extratrees.block.decor.HedgeRenderer;
import binnie.extratrees.block.decor.MultiFenceRecipeEmbedded;
import binnie.extratrees.block.decor.MultiFenceRecipeSize;
import binnie.extratrees.block.decor.MultiFenceRecipeSolid;
import binnie.extratrees.item.ExtraTreeLiquid;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.GameRegistry;
import forestry.api.recipes.RecipeManagers;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ModuleBlocks implements IInitializable {
    public static int hedgeRenderID;

    @Override
    public void preInit() {
        PlankType.setup();
        ExtraTrees.blockPlanks = new BlockETPlanks();
        ExtraTrees.blockFence = new BlockFence();
        ExtraTrees.blockLog = new BlockETLog();
        ExtraTrees.blockShrubLeaves = new BlockShrubLeaves();
        ExtraTrees.blockGate = new BlockGate();
        ExtraTrees.blockDoor = new BlockETDoor();
        ExtraTrees.blockMultiFence = new BlockMultiFence();
        ExtraTrees.blockSlab = new BlockETSlab(false);
        ExtraTrees.blockDoubleSlab = new BlockETSlab(true);
        ExtraTrees.blockStairs = new BlockETStairs(ExtraTrees.blockPlanks);

        GameRegistry.registerBlock(ExtraTrees.blockPlanks, ItemMetadata.class, "planks");
        GameRegistry.registerBlock(ExtraTrees.blockFence, ItemMetadata.class, "fence");
        GameRegistry.registerBlock(ExtraTrees.blockMultiFence, ItemMetadata.class, "multifence");
        BinnieCore.proxy.registerCustomItemRenderer(
                Item.getItemFromBlock(ExtraTrees.blockMultiFence), new ItemMetadataRenderer());
        GameRegistry.registerBlock(ExtraTrees.blockLog, ItemMetadata.class, "log");
        GameRegistry.registerBlock(ExtraTrees.blockGate, ItemMetadata.class, "gate");
        GameRegistry.registerBlock(ExtraTrees.blockSlab, ItemETSlab.class, "slab");
        GameRegistry.registerBlock(ExtraTrees.blockDoubleSlab, ItemETSlab.class, "doubleSlab");
        GameRegistry.registerBlock(ExtraTrees.blockDoor, ItemETDoor.class, "door");
        GameRegistry.registerBlock(ExtraTrees.blockStairs, ItemETStairs.class, "stairs");

        BinnieCore.proxy.registerCustomItemRenderer(
                Item.getItemFromBlock(ExtraTrees.blockStairs), new StairItemRenderer());
        BinnieCore.proxy.registerCustomItemRenderer(
                Item.getItemFromBlock(ExtraTrees.blockGate), new GateItemRenderer());
        for (ILogType plank : ILogType.ExtraTreeLog.values()) {
            OreDictionary.registerOre("logWood", plank.getItemStack());
        }

        GameRegistry.addSmelting(ExtraTrees.blockLog, new ItemStack(Items.coal, 1, 1), 0.15f);
        for (IPlankType plank2 : PlankType.ExtraTreePlanks.values()) {
            OreDictionary.registerOre("plankWood", plank2.getStack());
        }

        FMLInterModComms.sendMessage("Forestry", "add-fence-block", "ExtraTrees:fence");
        FMLInterModComms.sendMessage("Forestry", "add-fence-block", "ExtraTrees:gate");
        // FMLInterModComms.sendMessage("Forestry", "add-alveary-slab", "ExtraTrees:slab");
        FMLInterModComms.sendMessage("Forestry", "add-fence-block", "ExtraTrees:multifence");
        ModuleBlocks.hedgeRenderID = BinnieCore.proxy.getUniqueRenderID();
    }

    @Override
    public void init() {
        ExtraTrees.fenceID = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(new FenceRenderer());
        ExtraTrees.stairsID = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(new StairsRenderer());
        ExtraTrees.doorRenderId = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(new DoorBlockRenderer());
        ExtraTrees.branchRenderId = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(new BranchBlockRenderer());
        RenderingRegistry.registerBlockHandler(new HedgeRenderer());
        RecipeSorter.register("extratrees:multifence", MultiFenceRecipeSize.class, RecipeSorter.Category.SHAPED, "");
        RecipeSorter.register(
                "extratrees:multifence2", MultiFenceRecipeEmbedded.class, RecipeSorter.Category.SHAPED, "");
        RecipeSorter.register("extratrees:multifence3", MultiFenceRecipeSolid.class, RecipeSorter.Category.SHAPED, "");
    }

    @Override
    public void postInit() {
        for (PlankType.ExtraTreePlanks plank : PlankType.ExtraTreePlanks.values()) {
            ItemStack planks = plank.getStack();
            ItemStack slabs = TileEntityMetadata.getItemStack(ExtraTrees.blockSlab, plank.ordinal());
            ItemStack stairs = TileEntityMetadata.getItemStack(ExtraTrees.blockStairs, plank.ordinal());
            stairs.stackSize = 4;
            GameRegistry.addRecipe(stairs.copy(), "#  ", "## ", "###", '#', planks.copy());
            slabs.stackSize = 6;
            CraftingManager.getInstance()
                    .getRecipeList()
                    .add(0, new ShapedOreRecipe(slabs.copy(), "###", '#', planks.copy()));
        }

        GameRegistry.addRecipe(new MultiFenceRecipeSize());
        GameRegistry.addRecipe(new MultiFenceRecipeEmbedded());
        GameRegistry.addRecipe(new MultiFenceRecipeSolid());
        for (IPlankType plank2 : WoodManager.getAllPlankTypes()) {
            ItemStack planks2 = plank2.getStack();
            ItemStack fenceNormal = WoodManager.getFence(plank2, new FenceType(0), 1);
            ItemStack gate = WoodManager.getGate(plank2);
            ItemStack doorStandard = WoodManager.getDoor(plank2, DoorType.STANDARD);
            ItemStack doorSolid = WoodManager.getDoor(plank2, DoorType.SOLID);
            ItemStack doorSplit = WoodManager.getDoor(plank2, DoorType.DOUBLE);
            ItemStack doorFull = WoodManager.getDoor(plank2, DoorType.FULL);
            if (planks2 != null) {
                if (gate == null) {
                    continue;
                }
                gate.stackSize = 1;
                GameRegistry.addRecipe(gate.copy(), "fpf", 'f', fenceNormal.copy(), 'p', planks2.copy());
                fenceNormal.stackSize = 4;
                GameRegistry.addRecipe(fenceNormal.copy(), "###", "# #", '#', planks2.copy());
                GameRegistry.addRecipe(doorSolid.copy(), "###", "###", "###", '#', planks2.copy());
                GameRegistry.addRecipe(doorStandard.copy(), "# #", "###", "###", '#', planks2.copy());
                GameRegistry.addRecipe(doorSplit.copy(), "# #", "###", "# #", '#', planks2.copy());
                GameRegistry.addRecipe(doorFull.copy(), "# #", "# #", "# #", '#', planks2.copy());
            }
        }
        addSqueezer(ILogType.VanillaLog.Spruce, ExtraTreeLiquid.Resin, 50);
    }

    public void addSqueezer(ILogType log, ILiquidType liquid, int amount, float pulpChance) {
        FluidStack liquidStack = liquid.get(amount);
        RecipeManagers.squeezerManager.addRecipe(
                10, new ItemStack[] {log.getItemStack()}, liquidStack, Mods.forestry.stack("woodPulp"), (int)
                        (100.0f * pulpChance));
    }

    public void addSqueezer(ILogType log, ILiquidType liquid, int amount) {
        addSqueezer(log, liquid, amount, 0.5f);
    }
}
