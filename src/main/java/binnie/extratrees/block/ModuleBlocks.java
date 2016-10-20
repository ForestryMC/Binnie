// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.block;

import net.minecraftforge.fluids.FluidStack;
import binnie.core.Mods;
import forestry.api.recipes.RecipeManagers;
import binnie.core.liquid.ILiquidType;
import binnie.extratrees.item.ExtraTreeLiquid;
import binnie.extratrees.block.decor.FenceType;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraft.item.crafting.CraftingManager;
import binnie.core.block.TileEntityMetadata;
import binnie.extratrees.block.decor.MultiFenceRecipeSolid;
import binnie.extratrees.block.decor.MultiFenceRecipeEmbedded;
import net.minecraftforge.oredict.RecipeSorter;
import binnie.extratrees.block.decor.MultiFenceRecipeSize;
import binnie.extratrees.block.decor.HedgeRenderer;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import binnie.extratrees.block.decor.FenceRenderer;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInterModComms;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.client.IItemRenderer;
import binnie.core.block.ItemMetadataRenderer;
import net.minecraft.item.Item;
import binnie.core.BinnieCore;
import net.minecraft.block.Block;
import cpw.mods.fml.common.registry.GameRegistry;
import binnie.core.block.ItemMetadata;
import binnie.extratrees.block.decor.BlockMultiFence;
import binnie.extratrees.block.decor.BlockGate;
import binnie.extratrees.block.decor.BlockFence;
import binnie.extratrees.ExtraTrees;
import binnie.core.IInitializable;

public class ModuleBlocks implements IInitializable
{
	public static int hedgeRenderID;

	@Override
	public void preInit() {
		PlankType.setup();
		ExtraTrees.blockPlanks = new BlockETPlanks();
		ExtraTrees.blockFence = new BlockFence();
		ExtraTrees.blockLog = new BlockETLog();
		ExtraTrees.blockGate = new BlockGate();
		ExtraTrees.blockDoor = new BlockETDoor();
		ExtraTrees.blockMultiFence = new BlockMultiFence();
		ExtraTrees.blockSlab = new BlockETSlab(false);
		ExtraTrees.blockDoubleSlab = new BlockETSlab(true);
		ExtraTrees.blockStairs = new BlockETStairs(ExtraTrees.blockPlanks);
		GameRegistry.registerBlock(ExtraTrees.blockPlanks, ItemMetadata.class, "planks");
		GameRegistry.registerBlock(ExtraTrees.blockFence, ItemMetadata.class, "fence");
		GameRegistry.registerBlock(ExtraTrees.blockMultiFence, ItemMetadata.class, "multifence");
		BinnieCore.proxy.registerCustomItemRenderer(Item.getItemFromBlock(ExtraTrees.blockMultiFence), new ItemMetadataRenderer());
		GameRegistry.registerBlock(ExtraTrees.blockLog, ItemMetadata.class, "log");
		GameRegistry.registerBlock(ExtraTrees.blockGate, ItemMetadata.class, "gate");
		GameRegistry.registerBlock(ExtraTrees.blockSlab, ItemETSlab.class, "slab");
		GameRegistry.registerBlock(ExtraTrees.blockDoubleSlab, ItemETSlab.class, "doubleSlab");
		GameRegistry.registerBlock(ExtraTrees.blockDoor, ItemETDoor.class, "door");
		GameRegistry.registerBlock(ExtraTrees.blockStairs, ItemETStairs.class, "stairs");
		BinnieCore.proxy.registerCustomItemRenderer(Item.getItemFromBlock(ExtraTrees.blockStairs), new StairItemRenderer());
		BinnieCore.proxy.registerCustomItemRenderer(Item.getItemFromBlock(ExtraTrees.blockGate), new GateItemRenderer());
		for (final ILogType plank : ILogType.ExtraTreeLog.values()) {
			OreDictionary.registerOre("logWood", plank.getItemStack());
		}
		GameRegistry.addSmelting(ExtraTrees.blockLog, new ItemStack(Items.coal, 1, 1), 0.15f);
		for (final IPlankType plank2 : PlankType.ExtraTreePlanks.values()) {
			OreDictionary.registerOre("plankWood", plank2.getStack());
		}
		FMLInterModComms.sendMessage("Forestry", "add-fence-block", "ExtraTrees:fence");
		FMLInterModComms.sendMessage("Forestry", "add-fence-block", "ExtraTrees:gate");
		// FMLInterModComms.sendMessage("Forestry", "add-alveary-slab",
		// "ExtraTrees:slab");
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
			GameRegistry.addRecipe(stairs.copy(), new Object[] { "#  ", "## ", "###", '#', planks.copy() });
			slabs.stackSize = 6;
			CraftingManager.getInstance().getRecipeList().add(0, new ShapedOreRecipe(slabs.copy(), new Object[] { "###", '#', planks.copy() }));
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
				GameRegistry.addRecipe(gate.copy(), new Object[] { "fpf", 'f', fenceNormal.copy(), 'p', planks2.copy() });
				fenceNormal.stackSize = 4;
				GameRegistry.addRecipe(fenceNormal.copy(), new Object[] { "###", "# #", '#', planks2.copy() });
				GameRegistry.addRecipe(doorSolid.copy(), new Object[] { "###", "###", "###", '#', planks2.copy() });
				GameRegistry.addRecipe(doorStandard.copy(), new Object[] { "# #", "###", "###", '#', planks2.copy() });
				GameRegistry.addRecipe(doorSplit.copy(), new Object[] { "# #", "###", "# #", '#', planks2.copy() });
				GameRegistry.addRecipe(doorFull.copy(), new Object[] { "# #", "# #", "# #", '#', planks2.copy() });
			}
		}
		this.addSqueezer(ILogType.VanillaLog.Spruce, ExtraTreeLiquid.Resin, 50);
	}

	public void addSqueezer(final ILogType log, final ILiquidType liquid, final int amount, final float pulpChance) {
		final FluidStack liquidStack = liquid.get(amount);
		RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[] { log.getItemStack() }, liquidStack, Mods.Forestry.stack("woodPulp"), (int) (100.0f * pulpChance));
	}

	public void addSqueezer(final ILogType log, final ILiquidType liquid, final int amount) {
		this.addSqueezer(log, liquid, amount, 0.5f);
	}
}
