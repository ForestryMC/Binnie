package binnie.extratrees.block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import binnie.Constants;
import binnie.core.IInitializable;
import binnie.core.Mods;
import binnie.core.block.ItemMetadata;
import binnie.core.liquid.ILiquidType;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.block.decor.*;
import binnie.extratrees.block.wood.BlockETFence;
import binnie.extratrees.block.wood.BlockETLog;
import binnie.extratrees.block.wood.BlockETPlank;
import binnie.extratrees.block.wood.BlockETSlab;
import binnie.extratrees.block.wood.ItemBlockETWood;
import binnie.extratrees.block.wood.ItemETSlab;
import binnie.extratrees.genetics.ETTreeDefinition;
import binnie.extratrees.item.ExtraTreeLiquid;
import forestry.api.arboriculture.EnumVanillaWoodType;
import forestry.api.arboriculture.IWoodAccess;
import forestry.api.arboriculture.IWoodType;
import forestry.api.arboriculture.TreeManager;
import forestry.api.arboriculture.WoodBlockKind;
import forestry.api.recipes.RecipeManagers;
import forestry.arboriculture.WoodAccess;
import forestry.arboriculture.blocks.BlockForestryFenceGate;
import forestry.arboriculture.blocks.BlockForestryStairs;
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

	public List<BlockETLog> logs;
	public List<BlockETLog> logsFireproof;
	public List<BlockETPlank> planks;
	public List<BlockETPlank> planksFireproof;
	public List<BlockETSlab> slabs;
	public List<BlockETSlab> slabsDouble;
	public List<BlockETSlab> slabsFireproof;
	public List<BlockETSlab> slabsDoubleFireproof;
	public List<BlockForestryStairs> stairs;
	public List<BlockForestryStairs> stairsFireproof;
	public List<BlockETFence> fences;
	public List<BlockETFence> fencesFireproof;
	public List<BlockForestryFenceGate> fenceGates;
	public List<BlockForestryFenceGate> fenceGatesFireproof;
	public List<BlockETDecorativeLeaves> leavesDecorative;
	public Map<String, ItemStack> speciesToLeavesDecorative;
	public Block blockDoor;
	public BlockMultiFence blockMultiFence;
	public BlockHedge blockHedge;
	//????
	public Block blockBranch;
	
	@Override
	public void preInit() {
		PlankType.setup();
		
		//TODO: clean up
		logs = BlockETLog.create(false);
		for (BlockETLog block : logs) {
			ExtraTrees.proxy.registerBlock(block, new ItemBlockETWood(block));
			registerOreDictWildcard(OreDictUtil.LOG_WOOD, block);
		}

		logsFireproof = BlockETLog.create(true);
		for (BlockETLog block : logsFireproof) {
			ExtraTrees.proxy.registerBlock(block, new ItemBlockETWood(block));
			registerOreDictWildcard(OreDictUtil.LOG_WOOD, block);
		}

		WoodAccess woodAccess = WoodAccess.getInstance();

		woodAccess.registerLogs(logs);
		woodAccess.registerLogs(logsFireproof);
		
		planks = BlockETPlank.create(false);
		for (BlockETPlank block : planks) {
			ExtraTrees.proxy.registerBlock(block, new ItemBlockETWood(block));
			registerOreDictWildcard(OreDictUtil.PLANK_WOOD, block);
		}

		planksFireproof = BlockETPlank.create(true);
		for (BlockETPlank block : planksFireproof) {
			ExtraTrees.proxy.registerBlock(block, new ItemBlockETWood(block));
			registerOreDictWildcard(OreDictUtil.PLANK_WOOD, block);
		}

		woodAccess.registerPlanks(planks);
		woodAccess.registerPlanks(planksFireproof);
		
		slabs = BlockETSlab.create(false, false);
		slabsDouble = BlockETSlab.create(false, true);
		for (int i = 0; i < slabs.size(); i++) {
			BlockETSlab slab = slabs.get(i);
			BlockETSlab slabDouble = slabsDouble.get(i);
			ExtraTrees.proxy.registerBlock(slab, new ItemETSlab(slab, slab, slabDouble));
			ExtraTrees.proxy.registerBlock(slabDouble, new ItemETSlab(slabDouble, slab, slabDouble));
			registerOreDictWildcard(OreDictUtil.SLAB_WOOD, slab);
		}

		slabsFireproof = BlockETSlab.create(true, false);
		slabsDoubleFireproof = BlockETSlab.create(true, true);
		for (int i = 0; i < slabsFireproof.size(); i++) {
			BlockETSlab slab = slabsFireproof.get(i);
			BlockETSlab slabDouble = slabsDoubleFireproof.get(i);
			ExtraTrees.proxy.registerBlock(slab, new ItemETSlab(slab, slab, slabDouble));
			ExtraTrees.proxy.registerBlock(slabDouble, new ItemETSlab(slabDouble, slab, slabDouble));
			registerOreDictWildcard(OreDictUtil.SLAB_WOOD, slab);
		}
		woodAccess.registerSlabs(slabs);
		woodAccess.registerSlabs(slabsFireproof);
		
		stairs = new ArrayList<>();
		for (BlockETPlank plank : planks) {
			for (IBlockState blockState : plank.getBlockState().getValidStates()) {
				int meta = plank.getMetaFromState(blockState);
				EnumETLog woodType = plank.getWoodType(meta);

				BlockForestryStairs stair = new BlockForestryStairs(false, blockState, woodType);
				String name = "stairs." + woodType;
				stair.setRegistryName(new ResourceLocation(Constants.EXTRA_TREES_MOD_ID, name));
				stair.setUnlocalizedName(name);
				ExtraTrees.proxy.registerBlock(stair, new ItemBlockETWood(stair));
				stairs.add(stair);
				registerOreDictWildcard(OreDictUtil.STAIR_WOOD, stair);
			}
		}

		stairsFireproof = new ArrayList<>();
		for (BlockETPlank plank : planksFireproof) {
			for (IBlockState blockState : plank.getBlockState().getValidStates()) {
				int meta = plank.getMetaFromState(blockState);
				EnumETLog woodType = plank.getWoodType(meta);

				BlockForestryStairs stair = new BlockForestryStairs(true, blockState, woodType);
				String name = "stairs.fireproof." + woodType;
				stair.setRegistryName(new ResourceLocation(Constants.EXTRA_TREES_MOD_ID, name));
				stair.setUnlocalizedName(name);
				ExtraTrees.proxy.registerBlock(stair, new ItemBlockETWood(stair));
				stairsFireproof.add(stair);
				registerOreDictWildcard(OreDictUtil.STAIR_WOOD, stair);
			}
		}
		
		woodAccess.registerStairs(stairs);
		woodAccess.registerStairs(stairsFireproof);
		
		fences = BlockETFence.create(false);
		for (BlockETFence block : fences) {
			ExtraTrees.proxy.registerBlock(block, new ItemBlockETWood(block));
			registerOreDictWildcard(OreDictUtil.FENCE_WOOD, block);
			FMLInterModComms.sendMessage("forestry", "add-fence-block", block.getRegistryName().toString());
		}

		fencesFireproof = BlockETFence.create(true);
		for (BlockETFence block : fencesFireproof) {
			ExtraTrees.proxy.registerBlock(block, new ItemBlockETWood(block));
			registerOreDictWildcard(OreDictUtil.FENCE_WOOD, block);
			FMLInterModComms.sendMessage("forestry", "add-fence-block", block.getRegistryName().toString());
		}
		
		woodAccess.registerFences(fences);
		woodAccess.registerFences(fencesFireproof);
		
		fenceGates = new ArrayList<>();
		fenceGatesFireproof = new ArrayList<>();
		for (PlankType.ExtraTreePlanks plankType : PlankType.ExtraTreePlanks.VALUES) {
			EnumETLog woodType = plankType.getWoodType();
			BlockForestryFenceGate fenceGate = new BlockForestryFenceGate(false, woodType);
			String name = "fence.gates." + woodType;
			fenceGate.setRegistryName(new ResourceLocation(Constants.EXTRA_TREES_MOD_ID, name));
			fenceGate.setUnlocalizedName(name);
			ExtraTrees.proxy.registerBlock(fenceGate, new ItemBlockETWood(fenceGate));
			registerOreDictWildcard(OreDictUtil.FENCE_GATE_WOOD, fenceGate);
			fenceGates.add(fenceGate);
			FMLInterModComms.sendMessage("forestry", "add-fence-block", fenceGate.getRegistryName().toString());

			BlockForestryFenceGate fenceGateFireproof = new BlockForestryFenceGate(true, woodType);
			String nameFireproof = "fence.gates.fireproof." + woodType;
			fenceGateFireproof.setRegistryName(new ResourceLocation(Constants.EXTRA_TREES_MOD_ID, nameFireproof));
			fenceGateFireproof.setUnlocalizedName(nameFireproof);
			ExtraTrees.proxy.registerBlock(fenceGateFireproof, new ItemBlockETWood(fenceGateFireproof));
			registerOreDictWildcard(OreDictUtil.FENCE_GATE_WOOD, fenceGateFireproof);
			fenceGatesFireproof.add(fenceGateFireproof);
			FMLInterModComms.sendMessage("forestry", "add-fence-block", fenceGateFireproof.getRegistryName().toString());
		}
		
		woodAccess.registerFenceGates(fenceGates);
		woodAccess.registerFenceGates(fenceGatesFireproof);

		blockMultiFence = new BlockMultiFence();
		GameRegistry.register(blockMultiFence);
		GameRegistry.register(new ItemMetadata(blockMultiFence).setRegistryName(blockMultiFence.getRegistryName()));
		//BinnieCore.proxy.registerCustomItemRenderer(Item.getItemFromBlock(ExtraTrees.blockMultiFence), new ItemMetadataRenderer());

		blockDoor = new BlockETDoor();
		GameRegistry.register(blockDoor);
		GameRegistry.register(new ItemETDoor(blockDoor).setRegistryName(blockDoor.getRegistryName()));
		//BinnieCore.proxy.registerCustomItemRenderer(Item.getItemFromBlock(ExtraTrees.blockStairs), new StairItemRenderer());
		//BinnieCore.proxy.registerCustomItemRenderer(Item.getItemFromBlock(ExtraTrees.blockGate), new GateItemRenderer());
		for (BlockETLog log : logs) {
			ItemStack logInput = new ItemStack(log, 1, OreDictionary.WILDCARD_VALUE);
			ItemStack coalOutput = new ItemStack(Items.COAL, 1, 1);
			RecipeUtil.addSmelting(logInput, coalOutput, 0.15F);
		}
		//ModuleBlocks.hedgeRenderID = BinnieCore.proxy.getUniqueRenderID();
		leavesDecorative = BlockETDecorativeLeaves.create();
		speciesToLeavesDecorative = new HashMap<>();
		for (BlockETDecorativeLeaves leaves : leavesDecorative) {
			ExtraTrees.proxy.registerBlock(leaves, new ItemBlockETDecorativeLeaves(leaves));
			registerOreDictWildcard(OreDictUtil.TREE_LEAVES, leaves);

			for (IBlockState state : leaves.getBlockState().getValidStates()) {
				ETTreeDefinition treeDefinition = state.getValue(leaves.getVariant());
				String speciesUid = treeDefinition.getUID();
				int meta = leaves.getMetaFromState(state);
				speciesToLeavesDecorative.put(speciesUid, new ItemStack(leaves, 1, meta));
			}
		}
	}

	@Override
	public void init() {
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
		for(PlankType.ExtraTreePlanks plankType : PlankType.ExtraTreePlanks.VALUES){
			for(boolean fireproof : new boolean[]{false, true}){
				ItemStack planks = woodAccess.getStack(plankType.getWoodType(), WoodBlockKind.PLANKS, fireproof);
				ItemStack slabs = woodAccess.getStack(plankType.getWoodType(), WoodBlockKind.SLAB, fireproof);
				ItemStack stairs = woodAccess.getStack(plankType.getWoodType(), WoodBlockKind.STAIRS, fireproof);

				stairs.stackSize = 4;
				RecipeUtil.addPriorityRecipe(stairs.copy(),
						"#  ",
						"## ",
						"###",
						'#', planks.copy());

				slabs.stackSize = 6;
				RecipeUtil.addPriorityRecipe(slabs.copy(), "###", '#', planks.copy());
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
		ItemStack itemStack = ExtraTrees.blocks().speciesToLeavesDecorative.get(speciesUid);
		if (itemStack == null) {
			return null;
		}
		return itemStack.copy();
	}
}
