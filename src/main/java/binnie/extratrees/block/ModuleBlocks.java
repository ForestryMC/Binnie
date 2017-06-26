package binnie.extratrees.block;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;

import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

import forestry.api.arboriculture.EnumVanillaWoodType;
import forestry.api.arboriculture.IWoodAccess;
import forestry.api.arboriculture.IWoodType;
import forestry.api.arboriculture.TreeManager;
import forestry.api.arboriculture.WoodBlockKind;
import forestry.api.core.ForestryAPI;
import forestry.api.recipes.RecipeManagers;
import forestry.arboriculture.IWoodTyped;
import forestry.arboriculture.PluginArboriculture;
import forestry.arboriculture.WoodAccess;
import forestry.arboriculture.blocks.BlockForestryFenceGate;
import forestry.arboriculture.blocks.BlockForestryStairs;
import forestry.arboriculture.items.ItemBlockLeaves;
import forestry.core.fluids.Fluids;
import forestry.core.recipes.RecipeUtil;
import forestry.core.utils.OreDictUtil;
import forestry.plugins.ForestryPluginUids;

import binnie.Constants;
import binnie.core.IInitializable;
import binnie.core.Mods;
import binnie.core.block.ItemMetadata;
import binnie.core.liquid.ILiquidType;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.block.decor.BlockHedge;
import binnie.extratrees.block.decor.BlockMultiFence;
import binnie.extratrees.block.decor.MultiFenceRecipeEmbedded;
import binnie.extratrees.block.decor.MultiFenceRecipeSize;
import binnie.extratrees.block.decor.MultiFenceRecipeSolid;
import binnie.extratrees.block.property.PropertyETType;
import binnie.extratrees.block.wood.BlockETDoor;
import binnie.extratrees.block.wood.BlockETFence;
import binnie.extratrees.block.wood.BlockETLog;
import binnie.extratrees.block.wood.BlockETPlank;
import binnie.extratrees.block.wood.BlockETSlab;
import binnie.extratrees.block.wood.BlockShrubLog;
import binnie.extratrees.block.wood.ItemBlockETWood;
import binnie.extratrees.block.wood.ItemBlockETWoodDoor;
import binnie.extratrees.block.wood.ItemETSlab;
import binnie.extratrees.genetics.ETTreeDefinition;
import binnie.extratrees.item.ExtraTreeLiquid;
import binnie.extratrees.worldgen.VillageCreationExtraTrees;

public class ModuleBlocks implements IInitializable {
	public List<BlockETLog> logs;
	public List<BlockETLog> logsFireproof;
	public List<BlockETPlank> planks;
	public List<BlockETPlank> planksFireproof;
	public List<BlockETSlab> slabs;
	public List<BlockETSlab> slabsDouble;
	public List<BlockETSlab> slabsFireproof;
	public List<BlockETSlab> slabsDoubleFireproof;
	public List<BlockForestryStairs<EnumETLog>> stairs;
	public List<BlockForestryStairs<EnumETLog>> stairsFireproof;
	public List<BlockETFence> fences;
	public List<BlockETFence> fencesFireproof;
	public List<BlockForestryFenceGate<EnumETLog>> fenceGates;
	public List<BlockForestryFenceGate<EnumETLog>> fenceGatesFireproof;
	public List<BlockETDoor> doors;
	public List<BlockETDecorativeLeaves> leavesDecorative;
	public Map<String, ItemStack> speciesToLeavesDecorative;
	public List<BlockETDefaultLeaves> leavesDefault;
	public Block blockDoor;
	public BlockMultiFence blockMultiFence;
	public BlockHedge blockHedge;
	public BlockShrubLog shrubLog;
	public BlockHops hops;
	
	private static void registerOreDictWildcard(String oreDictName, Block block) {
		OreDictionary.registerOre(oreDictName, new ItemStack(block, 1, OreDictionary.WILDCARD_VALUE));
	}

	public static ItemStack getDecorativeLeaves(String speciesUid) {
		ItemStack itemStack = ExtraTrees.blocks().speciesToLeavesDecorative.get(speciesUid);
		return itemStack.copy();
	}

	@Override
	public void preInit() {
		WoodAccess woodAccess = WoodAccess.getInstance();
		PlankType.setup();
		
		logs = BlockETLog.create(false);
		logsFireproof = BlockETLog.create(true);
		for (BlockETLog block : logs) {
			ExtraTrees.proxy.registerBlock(block, new ItemBlockETWood<>(block));
			registerOreDictWildcard(OreDictUtil.LOG_WOOD, block);
		}

		logsFireproof = BlockETLog.create(true);
		for (BlockETLog block : logsFireproof) {
			ExtraTrees.proxy.registerBlock(block, new ItemBlockETWood<>(block));
			registerOreDictWildcard(OreDictUtil.LOG_WOOD, block);
		}

		woodAccess.registerLogs(logs);
		woodAccess.registerLogs(logsFireproof);

		planks = BlockETPlank.create(false);
		for (BlockETPlank block : planks) {
			ExtraTrees.proxy.registerBlock(block, new ItemBlockETWood<>(block));
			registerOreDictWildcard(OreDictUtil.PLANK_WOOD, block);
		}

		planksFireproof = BlockETPlank.create(true);
		for (BlockETPlank block : planksFireproof) {
			ExtraTrees.proxy.registerBlock(block, new ItemBlockETWood<>(block));
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

				BlockForestryStairs<EnumETLog> stair = new BlockForestryStairs<>(false, blockState, woodType);
				String name = "stairs." + woodType;
				stair.setRegistryName(new ResourceLocation(Constants.EXTRA_TREES_MOD_ID, name));
				stair.setUnlocalizedName(name);
				ExtraTrees.proxy.registerBlock(stair, new ItemBlockETWood<>(stair));
				stairs.add(stair);
				registerOreDictWildcard(OreDictUtil.STAIR_WOOD, stair);
			}
		}

		stairsFireproof = new ArrayList<>();
		for (BlockETPlank plank : planksFireproof) {
			for (IBlockState blockState : plank.getBlockState().getValidStates()) {
				int meta = plank.getMetaFromState(blockState);
				EnumETLog woodType = plank.getWoodType(meta);

				BlockForestryStairs<EnumETLog> stair = new BlockForestryStairs<>(true, blockState, woodType);
				String name = "stairs.fireproof." + woodType;
				stair.setRegistryName(new ResourceLocation(Constants.EXTRA_TREES_MOD_ID, name));
				stair.setUnlocalizedName(name);
				ExtraTrees.proxy.registerBlock(stair, new ItemBlockETWood<>(stair));
				stairsFireproof.add(stair);
				registerOreDictWildcard(OreDictUtil.STAIR_WOOD, stair);
			}
		}

		woodAccess.registerStairs(stairs);
		woodAccess.registerStairs(stairsFireproof);

		fences = BlockETFence.create(false);
		for (BlockETFence block : fences) {
			ExtraTrees.proxy.registerBlock(block, new ItemBlockETWood<>(block));
			registerOreDictWildcard(OreDictUtil.FENCE_WOOD, block);
			FMLInterModComms.sendMessage("forestry", "add-fence-block", block.getRegistryName().toString());
		}

		fencesFireproof = BlockETFence.create(true);
		for (BlockETFence block : fencesFireproof) {
			ExtraTrees.proxy.registerBlock(block, new ItemBlockETWood<>(block));
			registerOreDictWildcard(OreDictUtil.FENCE_WOOD, block);
			FMLInterModComms.sendMessage("forestry", "add-fence-block", block.getRegistryName().toString());
		}
		
		woodAccess.registerFences(fences);
		woodAccess.registerFences(fencesFireproof);

		fenceGates = new ArrayList<>();
		fenceGatesFireproof = new ArrayList<>();
		for (PlankType.ExtraTreePlanks plankType : PlankType.ExtraTreePlanks.VALUES) {
			EnumETLog woodType = plankType.getWoodType();
			BlockForestryFenceGate<EnumETLog> fenceGate = new BlockForestryFenceGate<>(false, woodType);
			String name = "fence.gates." + woodType;
			fenceGate.setRegistryName(new ResourceLocation(Constants.EXTRA_TREES_MOD_ID, name));
			fenceGate.setUnlocalizedName(name);
			ExtraTrees.proxy.registerBlock(fenceGate, new ItemBlockETWood<>(fenceGate));
			registerOreDictWildcard(OreDictUtil.FENCE_GATE_WOOD, fenceGate);
			fenceGates.add(fenceGate);
			FMLInterModComms.sendMessage("forestry", "add-fence-block", fenceGate.getRegistryName().toString());

			BlockForestryFenceGate<EnumETLog> fenceGateFireproof = new BlockForestryFenceGate<>(true, woodType);
			String nameFireproof = "fence.gates.fireproof." + woodType;
			fenceGateFireproof.setRegistryName(new ResourceLocation(Constants.EXTRA_TREES_MOD_ID, nameFireproof));
			fenceGateFireproof.setUnlocalizedName(nameFireproof);
			ExtraTrees.proxy.registerBlock(fenceGateFireproof, new ItemBlockETWood<>(fenceGateFireproof));
			registerOreDictWildcard(OreDictUtil.FENCE_GATE_WOOD, fenceGateFireproof);
			fenceGatesFireproof.add(fenceGateFireproof);
			FMLInterModComms.sendMessage("forestry", "add-fence-block", fenceGateFireproof.getRegistryName().toString());
		}

		//Forestry's API doesn't allow typed lists to be registered, but making a new one allows us to get away with it
		woodAccess.registerFenceGates(new ArrayList<>(fenceGates));
		woodAccess.registerFenceGates(new ArrayList<>(fenceGatesFireproof));
		
		doors = new ArrayList<>();
		for (EnumETLog woodType : EnumETLog.VALUES) {
			if(woodType.hasProducts()) {
				BlockETDoor door = new BlockETDoor(woodType);
				ExtraTrees.proxy.registerBlock(door, new ItemBlockETWoodDoor(door));
				registerOreDictWildcard(OreDictUtil.DOOR_WOOD, door);
				doors.add(door);
			}
		}
		
		registerDoors(woodAccess, doors);

		blockMultiFence = new BlockMultiFence();
		ExtraTrees.proxy.registerBlock(blockMultiFence, new ItemMetadata(blockMultiFence));
		
		leavesDefault = BlockETDefaultLeaves.create();
		Map speciesToLeavesDefault = PluginArboriculture.getBlocks().speciesToLeavesDefault;
		for (BlockETDefaultLeaves leaves : leavesDefault) {
			ExtraTrees.proxy.registerBlock(leaves, new ItemBlockLeaves(leaves));
			registerOreDictWildcard(OreDictUtil.TREE_LEAVES, leaves);
			
			PropertyETType treeType = leaves.getVariant();
			for (ETTreeDefinition treeDefinition : treeType.getAllowedValues()) {
				Preconditions.checkNotNull(treeDefinition);
				String speciesUid = treeDefinition.getUID();
				IBlockState blockState = leaves.getDefaultState().withProperty(treeType, treeDefinition);
				speciesToLeavesDefault.put(speciesUid, blockState);
			}
		}
		
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

		shrubLog = new BlockShrubLog();
		ExtraTrees.proxy.registerBlock(shrubLog, new ItemBlockETWood(shrubLog));
		woodAccess.register(EnumShrubLog.INSTANCE, WoodBlockKind.LOG, false, shrubLog.getStateFromMeta(0), new ItemStack(shrubLog, 1, 0));
		woodAccess.register(EnumShrubLog.INSTANCE, WoodBlockKind.LOG, true, shrubLog.getStateFromMeta(1), new ItemStack(shrubLog, 1, 1));
		
		hops = new BlockHops();
		ExtraTrees.proxy.registerBlock(hops);
		
		VillageCreationExtraTrees.registerVillageComponents();
	}
	
	public void registerDoors(WoodAccess woodAccess, List<BlockETDoor> blocks) {
		for (BlockETDoor block : blocks) {
			registerWithoutVariants(woodAccess, block, WoodBlockKind.DOOR);
		}
	}
	
	private <T extends Block & IWoodTyped> void registerWithoutVariants(WoodAccess woodAccess, T woodTyped, WoodBlockKind woodBlockKind) {
		boolean fireproof = woodTyped.isFireproof();
		IBlockState blockState = woodTyped.getDefaultState();
		IWoodType woodType = woodTyped.getWoodType(0);
		ItemStack itemStack = new ItemStack(woodTyped);
		if (!(woodType instanceof EnumVanillaWoodType)) {
			PluginArboriculture.proxy.registerWoodModel(woodTyped, false);
		}
		woodAccess.register(woodType, woodBlockKind, fireproof, blockState, itemStack);
	}
	
	@Override
	public void init() {
		VillageCreationExtraTrees villageHandler = new VillageCreationExtraTrees();
		VillagerRegistry villagerRegistry = VillagerRegistry.instance();
		villagerRegistry.registerVillageCreationHandler(villageHandler);
		
		RecipeSorter.register("extratrees:multifence", MultiFenceRecipeSize.class, RecipeSorter.Category.SHAPED, "");
		RecipeSorter.register("extratrees:multifence2", MultiFenceRecipeEmbedded.class, RecipeSorter.Category.SHAPED, "");
		RecipeSorter.register("extratrees:multifence3", MultiFenceRecipeSolid.class, RecipeSorter.Category.SHAPED, "");
	}

	@Override
	public void postInit() {
		for (BlockETLog log : logs) {
			ItemStack logInput = new ItemStack(log, 1, OreDictionary.WILDCARD_VALUE);
			ItemStack coalOutput = new ItemStack(Items.COAL, 1, 1);
			RecipeUtil.addSmelting(logInput, coalOutput, 0.15F);
		}
		
		IWoodAccess woodAccess = TreeManager.woodAccess;
		for(EnumETLog log : EnumETLog.VALUES){
			ItemStack logs = woodAccess.getStack(log, WoodBlockKind.LOG, false);
			ItemStack planks = log.getPlank().getStack(false);
			ItemStack fireproofLogs = woodAccess.getStack(log, WoodBlockKind.LOG, true);
			ItemStack fireproofPlanks = log.getPlank().getStack(true);
			
			planks.setCount(4);
			GameRegistry.addShapelessRecipe(planks.copy(), logs.copy());
			
			fireproofPlanks.setCount(4);
			GameRegistry.addShapelessRecipe(fireproofPlanks.copy(), fireproofLogs);
			
			if (ForestryAPI.enabledPlugins.containsAll(Arrays.asList(ForestryPluginUids.FACTORY, ForestryPluginUids.APICULTURE))) {
				
				logs.setCount(1);
				fireproofLogs.setCount(1);
				RecipeManagers.fabricatorManager.addRecipe(ItemStack.EMPTY, Fluids.GLASS.getFluid(500), fireproofLogs.copy(), new Object[]{
						" # ",
						"#X#",
						" # ",
						'#', Mods.Forestry.item("refractory_wax"),
						'X', logs.copy()});
			}
		}
		
		for (PlankType.ExtraTreePlanks plankType : PlankType.ExtraTreePlanks.VALUES) {
			for (boolean fireproof : new boolean[]{false, true}) {
				ItemStack planks = woodAccess.getStack(plankType.getWoodType(), WoodBlockKind.PLANKS, fireproof);
				ItemStack slabs = woodAccess.getStack(plankType.getWoodType(), WoodBlockKind.SLAB, fireproof);
				ItemStack fences = woodAccess.getStack(plankType.getWoodType(), WoodBlockKind.FENCE, fireproof);
				ItemStack fenceGates = woodAccess.getStack(plankType.getWoodType(), WoodBlockKind.FENCE_GATE, fireproof);
				ItemStack stairs = woodAccess.getStack(plankType.getWoodType(), WoodBlockKind.STAIRS, fireproof);
				
				stairs.setCount(4);
				RecipeUtil.addPriorityRecipe(stairs.copy(),
					"#  ",
					"## ",
					"###",
					'#', planks.copy()
				);

				slabs.setCount(6);
				RecipeUtil.addPriorityRecipe(slabs.copy(), "###", '#', planks.copy());
				
				fences.setCount(3);
				planks.setCount(1);
				RecipeUtil.addRecipe(fences.copy(),
						"#X#",
						"#X#",
						'#', planks.copy(), 'X', "stickWood");
				
				fenceGates.setCount(1);
				planks.setCount(1);
				RecipeUtil.addRecipe(fenceGates.copy(),
						"X#X",
						"X#X",
						'#', planks.copy(), 'X', "stickWood");
				
				if(!fireproof){
					ItemStack doors = woodAccess.getStack(plankType.getWoodType(), WoodBlockKind.DOOR, false);
					doors.setCount(3);
					planks.setCount(1);
					RecipeUtil.addPriorityRecipe(doors.copy(),
							"## ",
							"## ",
							"## ",
							'#', planks.copy());
				}
			}
			// Fabricator recipes
			if (ForestryAPI.enabledPlugins.containsAll(Arrays.asList(ForestryPluginUids.FACTORY, ForestryPluginUids.APICULTURE))) {
				ItemStack planks = woodAccess.getStack(plankType.getWoodType(), WoodBlockKind.PLANKS, false);
				ItemStack fireproofPlanks = woodAccess.getStack(plankType.getWoodType(), WoodBlockKind.PLANKS, true);
				planks.setCount(1);
				fireproofPlanks.setCount(5);
				RecipeManagers.fabricatorManager.addRecipe(ItemStack.EMPTY, Fluids.GLASS.getFluid(500), fireproofPlanks.copy(), new Object[]{
						"X#X",
						"#X#",
						"X#X",
						'#', Mods.Forestry.item("refractory_wax"),
						'X', planks.copy()});
			}
		}
		GameRegistry.addRecipe(new MultiFenceRecipeSize());
		GameRegistry.addRecipe(new MultiFenceRecipeEmbedded());
		GameRegistry.addRecipe(new MultiFenceRecipeSolid());
		this.addSqueezer(EnumVanillaWoodType.SPRUCE, ExtraTreeLiquid.Resin, 50);
	}

	public void addSqueezer(final IWoodType log, final ILiquidType liquid, final int amount, final float pulpChance) {
		final FluidStack liquidStack = liquid.get(amount);
		ItemStack logStack = TreeManager.woodAccess.getStack(log, WoodBlockKind.LOG, false);
		RecipeManagers.squeezerManager.addRecipe(10, logStack, liquidStack, Mods.Forestry.stack("wood_pulp"), (int) (100.0f * pulpChance));
	}

	public void addSqueezer(final IWoodType log, final ILiquidType liquid, final int amount) {
		this.addSqueezer(log, liquid, amount, 0.5f);
	}
}
