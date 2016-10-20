package binnie.extratrees.block;

import binnie.core.BinnieCore;
import binnie.core.IInitializable;
import binnie.core.Mods;
import binnie.core.block.ItemMetadata;
import binnie.core.block.TileEntityMetadata;
import binnie.core.liquid.ILiquidType;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.block.decor.*;
import binnie.extratrees.genetics.WoodAccess;
import binnie.extratrees.item.ExtraTreeLiquid;
import binnie.extratrees.proxy.Proxy;
import com.google.common.collect.ImmutableMap;
import forestry.api.arboriculture.EnumVanillaWoodType;
import forestry.api.arboriculture.IWoodType;
import forestry.api.arboriculture.TreeManager;
import forestry.api.arboriculture.WoodBlockKind;
import forestry.api.recipes.RecipeManagers;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.IRegistry;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.ModelProcessingHelper;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static binnie.extratrees.block.BlockETLog.woodTypes;

public class ModuleBlocks implements IInitializable {
   // public static int hedgeRenderID;

    @Override
    public void preInit() {
        PlankType.setup();
        ExtraTrees.blockPlanks = new BlockETPlanks();
        ExtraTrees.blockFence = new BlockFence("fence");

        for (int i = 0; i < BlockETLog.GROUP_COUNT; i++) {
            int finalI = i;
            BlockETLog block = new BlockETLog(finalI, false) {
                @Override
                public PropertyEnum<EnumExtraTreeLog> getVariant() {
                    return woodTypes[finalI];
                }
            };
            GameRegistry.register(block);
            Item wood = GameRegistry.register(new BlockETLog.LogItemBlock<BlockETLog>(block));

            WoodAccess.registerWithVariants(block,WoodBlockKind.LOG, woodTypes[finalI]);
            ExtraTrees.proxy.setCustomStateMapper("log", block);

            //fireproof
            BlockETLog block2 = new BlockETLog(i, true) {
                @Override
                public PropertyEnum<EnumExtraTreeLog> getVariant() {
                    return woodTypes[finalI];
                }
            };
            GameRegistry.register(block2);
            ExtraTrees.proxy.setCustomStateMapper("log", block2);
            Item woodFireproof = GameRegistry.register(new BlockETLog.LogItemBlock<BlockETLog>(block2));
            for(EnumExtraTreeLog l : woodTypes[finalI].getAllowedValues()) {
                BinnieCore.proxy.registermodel(wood, l.getMetadata() % BlockETLog.VARIANTS_PER_BLOCK, new ModelResourceLocation("extratrees:log","axis=y,wood_type="+l.getName()));
                BinnieCore.proxy.registermodel(woodFireproof, l.getMetadata() % BlockETLog.VARIANTS_PER_BLOCK, new ModelResourceLocation("extratrees:log","axis=y,wood_type="+l.getName()));
            }
        }


        ExtraTrees.blockGate = new BlockGate();
        ExtraTrees.blockDoor = new BlockETDoor();
        ExtraTrees.blockMultiFence = new BlockMultiFence();
        ExtraTrees.blockSlab = new BlockETSlab(false);
        ExtraTrees.blockDoubleSlab = new BlockETSlab(true);
        ExtraTrees.blockStairs = new BlockETStairs(ExtraTrees.blockPlanks);
        GameRegistry.register(ExtraTrees.blockPlanks);
        GameRegistry.register(new ItemMetadata(ExtraTrees.blockPlanks).setRegistryName(ExtraTrees.blockPlanks.getRegistryName()));
        GameRegistry.register(ExtraTrees.blockFence);
        GameRegistry.register(new ItemMetadata(ExtraTrees.blockFence).setRegistryName(ExtraTrees.blockFence.getRegistryName()));
        GameRegistry.register(ExtraTrees.blockMultiFence);
        GameRegistry.register(new ItemMetadata(ExtraTrees.blockMultiFence).setRegistryName(ExtraTrees.blockMultiFence.getRegistryName()));
        //BinnieCore.proxy.registerCustomItemRenderer(Item.getItemFromBlock(ExtraTrees.blockMultiFence), new ItemMetadataRenderer());

        GameRegistry.register(ExtraTrees.blockGate);
        GameRegistry.register(new ItemMetadata(ExtraTrees.blockGate).setRegistryName(ExtraTrees.blockGate.getRegistryName()));
        GameRegistry.register(ExtraTrees.blockSlab);
        GameRegistry.register(new ItemETSlab(ExtraTrees.blockSlab).setRegistryName(ExtraTrees.blockSlab.getRegistryName()));
        GameRegistry.register(ExtraTrees.blockDoubleSlab);
        GameRegistry.register(new ItemETSlab(ExtraTrees.blockDoubleSlab).setRegistryName(ExtraTrees.blockDoubleSlab.getRegistryName()));
        GameRegistry.register(ExtraTrees.blockDoor);
        GameRegistry.register(new ItemETDoor(ExtraTrees.blockDoor).setRegistryName(ExtraTrees.blockDoor.getRegistryName()));
        GameRegistry.register(ExtraTrees.blockStairs);
        GameRegistry.register(new ItemETStairs(ExtraTrees.blockStairs).setRegistryName(ExtraTrees.blockStairs.getRegistryName()));
        //BinnieCore.proxy.registerCustomItemRenderer(Item.getItemFromBlock(ExtraTrees.blockStairs), new StairItemRenderer());
        //BinnieCore.proxy.registerCustomItemRenderer(Item.getItemFromBlock(ExtraTrees.blockGate), new GateItemRenderer());
        for (final EnumExtraTreeLog plank : EnumExtraTreeLog.values()) {
            OreDictionary.registerOre("logWood", TreeManager.woodAccess.getStack(plank, WoodBlockKind.LOG, false));
        }
//		GameRegistry.addSmelting(ExtraTrees.blockLog, new ItemStack(Items.COAL, 1, 1), 0.15f);
        for (final IPlankType plank2 : PlankType.ExtraTreePlanks.values()) {
            OreDictionary.registerOre("plankWood", plank2.getStack());
        }
        //	FMLInterModComms.sendMessage("forestry", "add-fence-block", "ExtraTrees:fence");
        //	FMLInterModComms.sendMessage("forestry", "add-fence-block", "ExtraTrees:gate");
        // FMLInterModComms.sendMessage("Forestry", "add-alveary-slab",
        // "ExtraTrees:slab");
        //	FMLInterModComms.sendMessage("forestry", "add-fence-block", "ExtraTrees:multifence");
        //ModuleBlocks.hedgeRenderID = BinnieCore.proxy.getUniqueRenderID();
    }

    @Override
    public void init() {
//		ExtraTrees.fenceID = RenderingRegistry.getNextAvailableRenderId();
//		RenderingRegistry.registerBlockHandler(new FenceRenderer());
//		ExtraTrees.stairsID = RenderingRegistry.getNextAvailableRenderId();
//		RenderingRegistry.registerBlockHandler(new StairsRenderer());
//		ExtraTrees.doorRenderId = RenderingRegistry.getNextAvailableRenderId();
//		RenderingRegistry.registerBlockHandler(new DoorBlockRenderer());
//		ExtraTrees.branchRenderId = RenderingRegistry.getNextAvailableRenderId();
//		RenderingRegistry.registerBlockHandler(new BranchBlockRenderer());
//		RenderingRegistry.registerBlockHandler(new HedgeRenderer());
        RecipeSorter.register("extratrees:multifence", MultiFenceRecipeSize.class, RecipeSorter.Category.SHAPED, "");
        RecipeSorter.register("extratrees:multifence2", MultiFenceRecipeEmbedded.class, RecipeSorter.Category.SHAPED, "");
        RecipeSorter.register("extratrees:multifence3", MultiFenceRecipeSolid.class, RecipeSorter.Category.SHAPED, "");
    }

    @Override
    public void postInit() {
        for (final PlankType.ExtraTreePlanks plank : PlankType.ExtraTreePlanks.values()) {
            final ItemStack planks = plank.getStack();
            final ItemStack slabs = TileEntityMetadata.getItemStack(ExtraTrees.blockSlab, plank.ordinal());
            final ItemStack stairs = TileEntityMetadata.getItemStack(ExtraTrees.blockStairs, plank.ordinal());
            stairs.stackSize = 4;
            GameRegistry.addRecipe(stairs.copy(), new Object[]{"#  ", "## ", "###", '#', planks.copy()});
            slabs.stackSize = 6;
            CraftingManager.getInstance().getRecipeList().add(0, new ShapedOreRecipe(slabs.copy(), new Object[]{"###", '#', planks.copy()}));
        }
        GameRegistry.addRecipe(new MultiFenceRecipeSize());
        GameRegistry.addRecipe(new MultiFenceRecipeEmbedded());
        GameRegistry.addRecipe(new MultiFenceRecipeSolid());
        for (final IPlankType plank2 : WoodManager.getAllPlankTypes()) {
            final ItemStack planks2 = plank2.getStack();
            final ItemStack fenceNormal = WoodManager.getFence(plank2, new FenceType(0), 1);
            final ItemStack gate = WoodManager.getGate(plank2);
            final ItemStack doorStandard = WoodManager.getDoor(plank2, DoorType.Standard);
            final ItemStack doorSolid = WoodManager.getDoor(plank2, DoorType.Solid);
            final ItemStack doorSplit = WoodManager.getDoor(plank2, DoorType.Double);
            final ItemStack doorFull = WoodManager.getDoor(plank2, DoorType.Full);
            if (planks2 != null) {
                if (gate == null) {
                    continue;
                }
                gate.stackSize = 1;
                GameRegistry.addRecipe(gate.copy(), new Object[]{"fpf", 'f', fenceNormal.copy(), 'p', planks2.copy()});
                fenceNormal.stackSize = 4;
                GameRegistry.addRecipe(fenceNormal.copy(), new Object[]{"###", "# #", '#', planks2.copy()});
                GameRegistry.addRecipe(doorSolid.copy(), new Object[]{"###", "###", "###", '#', planks2.copy()});
                GameRegistry.addRecipe(doorStandard.copy(), new Object[]{"# #", "###", "###", '#', planks2.copy()});
                GameRegistry.addRecipe(doorSplit.copy(), new Object[]{"# #", "###", "# #", '#', planks2.copy()});
                GameRegistry.addRecipe(doorFull.copy(), new Object[]{"# #", "# #", "# #", '#', planks2.copy()});
            }
        }
        this.addSqueezer(EnumVanillaWoodType.SPRUCE, ExtraTreeLiquid.Resin, 50);
    }

    public void addSqueezer(final IWoodType log, final ILiquidType liquid, final int amount, final float pulpChance) {
        final FluidStack liquidStack = liquid.get(amount);
        RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[]{TreeManager.woodAccess.getStack(log, WoodBlockKind.LOG, false)}, liquidStack, Mods.Forestry.stack("woodPulp"), (int) (100.0f * pulpChance));
    }

    public void addSqueezer(final IWoodType log, final ILiquidType liquid, final int amount) {
        this.addSqueezer(log, liquid, amount, 0.5f);
    }
}
