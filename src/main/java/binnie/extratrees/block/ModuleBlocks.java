package binnie.extratrees.block;

import binnie.core.util.RecipeUtil;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;

import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.arboriculture.EnumVanillaWoodType;
import forestry.api.arboriculture.IAlleleFruit;
import forestry.api.arboriculture.ITreeRoot;
import forestry.api.arboriculture.IWoodAccess;
import forestry.api.arboriculture.IWoodType;
import forestry.api.arboriculture.TreeManager;
import forestry.api.arboriculture.WoodBlockKind;
import forestry.api.core.ForestryAPI;
import forestry.api.genetics.AlleleRegisterEvent;
import forestry.api.genetics.AlleleSpeciesRegisterEvent;
import forestry.api.lepidopterology.IButterflyRoot;
import forestry.api.recipes.RecipeManagers;
import forestry.arboriculture.IWoodTyped;
import forestry.arboriculture.PluginArboriculture;
import forestry.arboriculture.WoodAccess;
import forestry.arboriculture.blocks.BlockForestryFenceGate;
import forestry.arboriculture.blocks.BlockForestryStairs;
import forestry.arboriculture.items.ItemBlockLeaves;
import forestry.core.fluids.Fluids;
import forestry.core.utils.OreDictUtil;
import forestry.plugins.ForestryPluginUids;

import binnie.Constants;
import binnie.core.BinnieCore;
import binnie.core.Mods;
import binnie.core.block.ItemMetadata;
import binnie.core.liquid.ILiquidType;
import binnie.core.models.DoublePassBakedModel;
import binnie.extratrees.ExtraTrees;
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
import binnie.extratrees.genetics.AlleleETFruit;
import binnie.extratrees.genetics.ButterflySpecies;
import binnie.extratrees.genetics.ETTreeDefinition;
import binnie.extratrees.genetics.ExtraTreeMutation;
import binnie.extratrees.genetics.FruitSprite;
import binnie.extratrees.item.ExtraTreeLiquid;
import binnie.extratrees.modules.ExtraTreesModuleUIDs;
import binnie.extratrees.village.VillageCreationExtraTrees;
import binnie.modules.BinnieModule;
import binnie.modules.Module;

@BinnieModule(moduleID = ExtraTreesModuleUIDs.WOOD, moduleContainerID = Constants.EXTRA_TREES_MOD_ID, name = "Wood", unlocalizedDescription = "extratrees.module.wood")
public class ModuleBlocks extends Module {
	public static List<BlockETLog> logs = new ArrayList<>();
	public static List<BlockETLog> logsFireproof = new ArrayList<>();
	public static List<BlockETPlank> planks = new ArrayList<>();
	public static List<BlockETPlank> planksFireproof = new ArrayList<>();
	public static List<BlockETSlab> slabs = new ArrayList<>();
	public static List<BlockETSlab> slabsDouble = new ArrayList<>();
	public static List<BlockETSlab> slabsFireproof = new ArrayList<>();
	public static List<BlockETSlab> slabsDoubleFireproof = new ArrayList<>();
	public static List<BlockForestryStairs<EnumETLog>> stairs = new ArrayList<>();
	public static List<BlockForestryStairs<EnumETLog>> stairsFireproof = new ArrayList<>();
	public static List<BlockETFence> fences = new ArrayList<>();
	public static List<BlockETFence> fencesFireproof = new ArrayList<>();
	public static List<BlockForestryFenceGate<EnumETLog>> fenceGates = new ArrayList<>();
	public static List<BlockForestryFenceGate<EnumETLog>> fenceGatesFireproof = new ArrayList<>();
	public static List<BlockETDoor> doors = new ArrayList<>();
	public static List<BlockETDecorativeLeaves> leavesDecorative = new ArrayList<>();
	public static Map<String, ItemStack> speciesToLeavesDecorative = new HashMap<>();
	public static List<BlockETDefaultLeaves> leavesDefault = new ArrayList<>();
	public static BlockMultiFence blockMultiFence;
	public static BlockShrubLog shrubLog;

	public static String[] branches;
	public static List<List<String>> classifications;

	public ModuleBlocks() {
		branches = new String[]{"Malus Maleae Amygdaloideae Rosaceae", "Musa   Musaceae Zingiberales Commelinids Angiosperms", "Sorbus Maleae", "Tsuga   Pinaceae", "Fraxinus Oleeae  Oleaceae Lamiales Asterids Angiospems"};
		classifications = new ArrayList<>();
	}

	private static void registerOreDictWildcard(String oreDictName, Block block) {
		OreDictionary.registerOre(oreDictName, new ItemStack(block, 1, OreDictionary.WILDCARD_VALUE));
	}

	public static ItemStack getDecorativeLeaves(String speciesUid) {
		ItemStack itemStack = ModuleBlocks.speciesToLeavesDecorative.get(speciesUid);
		return itemStack.copy();
	}

	@Override
	public void preInit() {
		MinecraftForge.EVENT_BUS.register(this);
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

		AlleleETFruit.init();
		ETTreeDefinition.initTrees();
		ExtraTreeMutation.init();
		if (BinnieCore.isLepidopteryActive()) {
			ButterflySpecies.initButterflies();
		}
	}

	@Override
	public void postInit() {
		RecipeUtil recipeUtil = new RecipeUtil(Constants.EXTRA_TREES_MOD_ID);
		for (BlockETLog log : logs) {
			ItemStack logInput = new ItemStack(log, 1, OreDictionary.WILDCARD_VALUE);
			ItemStack coalOutput = new ItemStack(Items.COAL, 1, 1);
			GameRegistry.addSmelting(logInput, coalOutput, 0.15F);
		}
		
		IWoodAccess woodAccess = TreeManager.woodAccess;
		for(EnumETLog log : EnumETLog.VALUES){
			ItemStack logs = woodAccess.getStack(log, WoodBlockKind.LOG, false);
			ItemStack planks = log.getPlank().getStack(false);
			ItemStack fireproofLogs = woodAccess.getStack(log, WoodBlockKind.LOG, true);
			ItemStack fireproofPlanks = log.getPlank().getStack(true);
			
			planks.setCount(4);
			recipeUtil.addShapelessRecipe(log.getUid() + "_planks", planks.copy(), logs.copy());
			
			fireproofPlanks.setCount(4);
			recipeUtil.addShapelessRecipe(log.getUid() + "_fireproof_planks", fireproofPlanks.copy(), fireproofLogs);
			
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

				String plankUid = plankType.getWoodType().getUid();
				if (fireproof) {
					plankUid += "_fireproof";
				}
				stairs.setCount(4);
				recipeUtil.addRecipe(plankUid + "_stairs", stairs.copy(),
					"#  ",
					"## ",
					"###",
					'#', planks.copy()
				);

				slabs.setCount(6);
				recipeUtil.addRecipe(plankUid + "_slabs", slabs.copy(), "###", '#', planks.copy());
				
				fences.setCount(3);
				planks.setCount(1);
				recipeUtil.addRecipe(plankUid + "_fences", fences.copy(),
						"#X#",
						"#X#",
						'#', planks.copy(), 'X', "stickWood");
				
				fenceGates.setCount(1);
				planks.setCount(1);
				recipeUtil.addRecipe(plankUid + "_fence_gates", fenceGates.copy(),
						"X#X",
						"X#X",
						'#', planks.copy(), 'X', "stickWood");
				
				if(!fireproof){
					ItemStack doors = woodAccess.getStack(plankType.getWoodType(), WoodBlockKind.DOOR, false);
					doors.setCount(3);
					planks.setCount(1);
					recipeUtil.addRecipe(plankUid + "_doors", doors.copy(),
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
		ForgeRegistries.RECIPES.register(new MultiFenceRecipeSize());
		ForgeRegistries.RECIPES.register(new MultiFenceRecipeEmbedded());
		ForgeRegistries.RECIPES.register(new MultiFenceRecipeSolid());
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

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void registerSprites(TextureStitchEvent.Pre event) {
		for (FruitSprite sprite : FruitSprite.VALUES) {
			sprite.registerSprites();
		}
		TextureMap map = Minecraft.getMinecraft().getTextureMapBlocks();
		for (IPlankType type : PlankType.ExtraTreePlanks.VALUES) {
			type.registerSprites(map);
		}
		for (IPlankType type : PlankType.ForestryPlanks.values()) {
			type.registerSprites(map);
		}
		for (IPlankType type : PlankType.VanillaPlanks.values()) {
			type.registerSprites(map);
		}
	}

	@SubscribeEvent
	public static void onRegisterAllele(AlleleRegisterEvent<IAlleleFruit> event) {
		if (event.getAlleleClass() == IAlleleFruit.class) {
			AlleleETFruit.preInit();
		}
	}

	@SubscribeEvent
	public static void speciesRegister(AlleleSpeciesRegisterEvent event) {
		if (event.getRoot() instanceof ITreeRoot) {
			ETTreeDefinition.preInitTrees();
			PlankType.ExtraTreePlanks.initWoodTypes();
		} else if (event.getRoot() instanceof IButterflyRoot && BinnieCore.isLepidopteryActive()) {
			ButterflySpecies.preInit();
		}
	}


	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onBakedEvent(ModelBakeEvent e) {
		//Find all ExtraTrees saplings
		List<ModelResourceLocation> models = e.getModelRegistry().getKeys().stream()
			.filter(mrl -> mrl.getResourceDomain().startsWith(Constants.EXTRA_TREES_MOD_ID))
			.filter(mrl -> mrl.getResourcePath().startsWith("germlings")).collect(Collectors.toList());
		//Replace model
		Map<String, ETTreeDefinition> map = Arrays.stream(ETTreeDefinition.values()).collect(Collectors.toMap(o -> o.name().toLowerCase(), o -> o));
		models.forEach(model -> {
			String species = model.getVariant().split("=")[1];
			ETTreeDefinition treeSpecies = map.get(species);
			int primaryColor = treeSpecies.getLeafColor().getRGB();
			int secondaryColor = treeSpecies.getWoodColor().getRGB();
			e.getModelRegistry().putObject(model, new DoublePassBakedModel(e.getModelRegistry().getObject(model), primaryColor, secondaryColor));
		});
	}
}
