package binnie.extratrees.block;

import binnie.core.IInitializable;
import binnie.core.Mods;
import binnie.core.block.ItemMetadata;
import binnie.core.liquid.ILiquidType;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.block.decor.*;
import binnie.extratrees.block.log.BlockETLog;
import binnie.extratrees.block.plank.BlockETPlank;
import binnie.extratrees.block.slab.BlockETSlab;
import binnie.extratrees.block.stairs.BlockETStairs;
import binnie.extratrees.genetics.WoodAccess;
import binnie.extratrees.item.ExtraTreeLiquid;
import forestry.api.arboriculture.EnumVanillaWoodType;
import forestry.api.arboriculture.IWoodType;
import forestry.api.arboriculture.TreeManager;
import forestry.api.arboriculture.WoodBlockKind;
import forestry.api.recipes.RecipeManagers;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;


public class ModuleBlocks implements IInitializable {
	// public static int hedgeRenderID;

	@Override
	public void preInit() {
		PlankType.setup();

		BlockETLog.init();
		BlockETPlank.init();
		BlockETSlab.init();
		BlockETStairs.init();

		ExtraTrees.blockFence = new BlockFence("fence");
		ExtraTrees.blockGate = new BlockGate();
		ExtraTrees.blockDoor = new BlockETDoor();
		ExtraTrees.blockMultiFence = new BlockMultiFence();
		GameRegistry.register(ExtraTrees.blockFence);
		GameRegistry.register(new ItemMetadata(ExtraTrees.blockFence).setRegistryName(ExtraTrees.blockFence.getRegistryName()));
		GameRegistry.register(ExtraTrees.blockMultiFence);
		GameRegistry.register(new ItemMetadata(ExtraTrees.blockMultiFence).setRegistryName(ExtraTrees.blockMultiFence.getRegistryName()));
		//BinnieCore.proxy.registerCustomItemRenderer(Item.getItemFromBlock(ExtraTrees.blockMultiFence), new ItemMetadataRenderer());

		GameRegistry.register(ExtraTrees.blockGate);
		GameRegistry.register(new ItemMetadata(ExtraTrees.blockGate).setRegistryName(ExtraTrees.blockGate.getRegistryName()));
		GameRegistry.register(ExtraTrees.blockDoor);
		GameRegistry.register(new ItemETDoor(ExtraTrees.blockDoor).setRegistryName(ExtraTrees.blockDoor.getRegistryName()));
//		GameRegistry.register(ExtraTrees.blockStairs);
		//	GameRegistry.register(new ItemETStairs(ExtraTrees.blockStairs).setRegistryName(ExtraTrees.blockStairs.getRegistryName()));
		//BinnieCore.proxy.registerCustomItemRenderer(Item.getItemFromBlock(ExtraTrees.blockStairs), new StairItemRenderer());
		//BinnieCore.proxy.registerCustomItemRenderer(Item.getItemFromBlock(ExtraTrees.blockGate), new GateItemRenderer());
		for (final EnumExtraTreeLog plank : EnumExtraTreeLog.values()) {
			OreDictionary.registerOre("logWood", WoodAccess.getInstance().getStack(plank, WoodBlockKind.LOG, false));
			OreDictionary.registerOre("plankWood", WoodAccess.getInstance().getStack(plank, WoodBlockKind.PLANKS, false));
		}
//		GameRegistry.addSmelting(ExtraTrees.blockLog, new ItemStack(Items.COAL, 1, 1), 0.15f);
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
//		RenderingRegistry.registerBlockHandler(new HedgeRenderer());
		RecipeSorter.register("extratrees:multifence", MultiFenceRecipeSize.class, RecipeSorter.Category.SHAPED, "");
		RecipeSorter.register("extratrees:multifence2", MultiFenceRecipeEmbedded.class, RecipeSorter.Category.SHAPED, "");
		RecipeSorter.register("extratrees:multifence3", MultiFenceRecipeSolid.class, RecipeSorter.Category.SHAPED, "");
	}

	@Override
	public void postInit() {
		for (final PlankType.ExtraTreePlanks plank : PlankType.ExtraTreePlanks.values()) {
			final ItemStack planks = plank.getStack();
			//final ItemStack slabs = TileEntityMetadata.getItemStack(ExtraTrees.blockSlab, plank.ordinal());
			//final ItemStack stairs = TileEntityMetadata.getItemStack(ExtraTrees.blockStairs, plank.ordinal());
			//stairs.stackSize = 4;
			//GameRegistry.addRecipe(stairs.copy(), "#  ", "## ", "###", '#', planks.copy());
			//slabs.stackSize = 6;
			//CraftingManager.getInstance().getRecipeList().add(0, new ShapedOreRecipe(slabs.copy(), "###", '#', planks.copy()));
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
				GameRegistry.addRecipe(gate.copy(), "fpf", 'f', fenceNormal.copy(), 'p', planks2.copy());
				fenceNormal.stackSize = 4;
				GameRegistry.addRecipe(fenceNormal.copy(), "###", "# #", '#', planks2.copy());
				GameRegistry.addRecipe(doorSolid.copy(), "###", "###", "###", '#', planks2.copy());
				GameRegistry.addRecipe(doorStandard.copy(), "# #", "###", "###", '#', planks2.copy());
				GameRegistry.addRecipe(doorSplit.copy(), "# #", "###", "# #", '#', planks2.copy());
				GameRegistry.addRecipe(doorFull.copy(), "# #", "# #", "# #", '#', planks2.copy());
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
