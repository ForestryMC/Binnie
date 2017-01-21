package binnie.extratrees.block;

import java.util.ArrayList;
import java.util.HashMap;

import binnie.Constants;
import binnie.core.IInitializable;
import binnie.core.Mods;
import binnie.core.block.ItemMetadata;
import binnie.core.liquid.ILiquidType;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.block.decor.*;
import binnie.extratrees.block.log.BlockETLog;
import binnie.extratrees.block.log.ItemETLog;
import binnie.extratrees.block.plank.BlockETPlank;
import binnie.extratrees.block.plank.ItemETPlank;
import binnie.extratrees.block.slab.BlockETSlab;
import binnie.extratrees.block.slab.ItemETSlab;
import binnie.extratrees.block.stairs.ItemETStairs;
import binnie.extratrees.genetics.ETTreeDefinition;
import binnie.extratrees.item.ExtraTreeLiquid;
import forestry.api.arboriculture.EnumVanillaWoodType;
import forestry.api.arboriculture.IWoodAccess;
import forestry.api.arboriculture.IWoodType;
import forestry.api.arboriculture.TreeManager;
import forestry.api.arboriculture.WoodBlockKind;
import forestry.api.recipes.RecipeManagers;
import forestry.arboriculture.WoodAccess;
import forestry.arboriculture.blocks.BlockDecorativeLeaves;
import forestry.arboriculture.blocks.BlockForestryStairs;
import forestry.arboriculture.genetics.LeafProvider;
import forestry.arboriculture.genetics.TreeDefinition;
import forestry.arboriculture.items.ItemBlockDecorativeLeaves;
import forestry.core.recipes.RecipeUtil;
import forestry.core.utils.OreDictUtil;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;


public class ModuleBlocks implements IInitializable {
	// public static int hedgeRenderID;

	@Override
	public void preInit() {
		PlankType.setup();
		
		//TODO: clean up
		ExtraTrees.logs = BlockETLog.create(false);
		for (BlockETLog block : ExtraTrees.logs) {
			ExtraTrees.proxy.registerBlock(block, new ItemETLog(block));
			registerOreDictWildcard(OreDictUtil.LOG_WOOD, block);
		}

		ExtraTrees.logsFireproof = BlockETLog.create(true);
		for (BlockETLog block : ExtraTrees.logsFireproof) {
			ExtraTrees.proxy.registerBlock(block, new ItemETLog(block));
			registerOreDictWildcard(OreDictUtil.LOG_WOOD, block);
		}

		WoodAccess woodAccess = WoodAccess.getInstance();

		woodAccess.registerLogs(ExtraTrees.logs);
		woodAccess.registerLogs(ExtraTrees.logsFireproof);
		
		ExtraTrees.planks = BlockETPlank.create(false);
		for (BlockETPlank block : ExtraTrees.planks) {
			ExtraTrees.proxy.registerBlock(block, new ItemETPlank(block));
			registerOreDictWildcard(OreDictUtil.PLANK_WOOD, block);
		}

		ExtraTrees.planksFireproof = BlockETPlank.create(true);
		for (BlockETPlank block : ExtraTrees.planksFireproof) {
			ExtraTrees.proxy.registerBlock(block, new ItemETPlank(block));
			registerOreDictWildcard(OreDictUtil.PLANK_WOOD, block);
		}

		woodAccess.registerPlanks(ExtraTrees.planks);
		woodAccess.registerPlanks(ExtraTrees.planksFireproof);
		
		ExtraTrees.slabs = BlockETSlab.create(false, false);
		ExtraTrees.slabsDouble = BlockETSlab.create(false, true);
		for (int i = 0; i < ExtraTrees.slabs.size(); i++) {
			BlockETSlab slab = ExtraTrees.slabs.get(i);
			BlockETSlab slabDouble = ExtraTrees.slabsDouble.get(i);
			ExtraTrees.proxy.registerBlock(slab, new ItemETSlab(slab, slab, slabDouble));
			ExtraTrees.proxy.registerBlock(slabDouble, new ItemETSlab(slabDouble, slab, slabDouble));
			registerOreDictWildcard(OreDictUtil.SLAB_WOOD, slab);
		}

		ExtraTrees.slabsFireproof = BlockETSlab.create(true, false);
		ExtraTrees.slabsDoubleFireproof = BlockETSlab.create(true, true);
		for (int i = 0; i < ExtraTrees.slabsFireproof.size(); i++) {
			BlockETSlab slab = ExtraTrees.slabsFireproof.get(i);
			BlockETSlab slabDouble = ExtraTrees.slabsDoubleFireproof.get(i);
			ExtraTrees.proxy.registerBlock(slab, new ItemETSlab(slab, slab, slabDouble));
			ExtraTrees.proxy.registerBlock(slabDouble, new ItemETSlab(slabDouble, slab, slabDouble));
			registerOreDictWildcard(OreDictUtil.SLAB_WOOD, slab);
		}
		woodAccess.registerSlabs(ExtraTrees.slabs);
		woodAccess.registerSlabs(ExtraTrees.slabsFireproof);
		
		ExtraTrees.stairs = new ArrayList<>();
		for (BlockETPlank plank : ExtraTrees.planks) {
			for (IBlockState blockState : plank.getBlockState().getValidStates()) {
				int meta = plank.getMetaFromState(blockState);
				EnumExtraTreeLog woodType = plank.getWoodType(meta);

				BlockForestryStairs stair = new BlockForestryStairs(false, blockState, woodType);
				String name = "stairs." + woodType;
				stair.setRegistryName(new ResourceLocation(Constants.EXTRA_TREES_MOD_ID, name));
				stair.setUnlocalizedName(name);
				ExtraTrees.proxy.registerBlock(stair, new ItemETStairs(stair));
				ExtraTrees.stairs.add(stair);
				registerOreDictWildcard(OreDictUtil.STAIR_WOOD, stair);
			}
		}

		ExtraTrees.stairsFireproof = new ArrayList<>();
		for (BlockETPlank plank : ExtraTrees.planksFireproof) {
			for (IBlockState blockState : plank.getBlockState().getValidStates()) {
				int meta = plank.getMetaFromState(blockState);
				EnumExtraTreeLog woodType = plank.getWoodType(meta);

				BlockForestryStairs stair = new BlockForestryStairs(true, blockState, woodType);
				String name = "stairs.fireproof." + woodType;
				stair.setRegistryName(new ResourceLocation(Constants.EXTRA_TREES_MOD_ID, "stairs.fireproof." + woodType));
				stair.setUnlocalizedName(name);
				ExtraTrees.proxy.registerBlock(stair, new ItemETStairs(stair));
				ExtraTrees.stairsFireproof.add(stair);
				registerOreDictWildcard(OreDictUtil.STAIR_WOOD, stair);
			}
		}

		woodAccess.registerStairs(ExtraTrees.stairs);
		woodAccess.registerStairs(ExtraTrees.stairsFireproof);

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
		for (BlockETLog log : ExtraTrees.logs) {
			ItemStack logInput = new ItemStack(log, 1, OreDictionary.WILDCARD_VALUE);
			ItemStack coalOutput = new ItemStack(Items.COAL, 1, 1);
			RecipeUtil.addSmelting(logInput, coalOutput, 0.15F);
		}
		FMLInterModComms.sendMessage("forestry", "add-fence-block", "ExtraTrees:fence");
		FMLInterModComms.sendMessage("forestry", "add-fence-block", "ExtraTrees:gate");
		FMLInterModComms.sendMessage("forestry", "add-fence-block", "ExtraTrees:multifence");
		//ModuleBlocks.hedgeRenderID = BinnieCore.proxy.getUniqueRenderID();
		ExtraTrees.leavesDecorative = BlockETDecorativeLeaves.create();
		ExtraTrees.speciesToLeavesDecorative = new HashMap<>();
		for (BlockETDecorativeLeaves leaves : ExtraTrees.leavesDecorative) {
			ExtraTrees.proxy.registerBlock(leaves, new ItemBlockETDecorativeLeaves(leaves));
			registerOreDictWildcard(OreDictUtil.TREE_LEAVES, leaves);

			for (IBlockState state : leaves.getBlockState().getValidStates()) {
				ETTreeDefinition treeDefinition = state.getValue(leaves.getVariant());
				String speciesUid = treeDefinition.getUID();
				int meta = leaves.getMetaFromState(state);
				ExtraTrees.speciesToLeavesDecorative.put(speciesUid, new ItemStack(leaves, 1, meta));
			}
		}
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
		IWoodAccess woodAccess = TreeManager.woodAccess;
		for(EnumExtraTreeLog woodType : EnumExtraTreeLog.VALUES){
			for(boolean fireproof : new boolean[]{false, true}){
				ItemStack logs = woodAccess.getStack(woodType, WoodBlockKind.LOG, fireproof);
				ItemStack planks = woodAccess.getStack(woodType, WoodBlockKind.PLANKS, fireproof);
				ItemStack slabs = woodAccess.getStack(woodType, WoodBlockKind.SLAB, fireproof);
				ItemStack stairs = woodAccess.getStack(woodType, WoodBlockKind.STAIRS, fireproof);

				stairs.stackSize = 4;
				RecipeUtil.addPriorityRecipe(stairs.copy(),
						"#  ",
						"## ",
						"###",
						'#', planks.copy());

				slabs.stackSize = 6;
				RecipeUtil.addPriorityRecipe(slabs.copy(), "###", '#', planks.copy());

				ItemStack craftedPlanks = planks.copy();
				craftedPlanks.stackSize = 4;
				RecipeUtil.addShapelessRecipe(craftedPlanks, logs.copy());
			}
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
	
	private static void addRecipeAtPosition(int position, IRecipe recipe){
		CraftingManager manager = CraftingManager.getInstance();
		manager.getRecipeList().add(position, recipe);
	}
	
	private static void registerOreDictWildcard(String oreDictName, Block block) {
		OreDictionary.registerOre(oreDictName, new ItemStack(block, 1, OreDictionary.WILDCARD_VALUE));
	}
	
	public static ItemStack getDecorativeLeaves(String speciesUid) {
		ItemStack itemStack = ExtraTrees.speciesToLeavesDecorative.get(speciesUid);
		if (itemStack == null) {
			return null;
		}
		return itemStack.copy();
	}
}
