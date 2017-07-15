package binnie.botany.genetics;

import binnie.Binnie;
import binnie.botany.Botany;
import binnie.botany.CreativeTabBotany;
import binnie.botany.api.EnumFlowerStage;
import binnie.botany.api.FlowerManager;
import binnie.botany.core.BotanyCore;
import binnie.botany.flower.BlockFlower;
import binnie.botany.flower.ItemBotany;
import binnie.botany.flower.TileEntityFlower;
import binnie.core.BinnieCore;
import binnie.core.IInitializable;
import binnie.core.liquid.ManagerLiquid;
import forestry.api.core.ForestryAPI;
import forestry.api.genetics.AlleleManager;
import forestry.api.recipes.RecipeManagers;
import forestry.api.storage.BackpackManager;
import forestry.api.storage.IBackpackInterface;
import forestry.core.recipes.RecipeUtil;
import forestry.storage.BackpackDefinition;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.awt.Color;
import java.util.function.Predicate;

public class ModuleGenetics implements IInitializable {
	static AlleleEffectNone alleleEffectNone = new AlleleEffectNone();

	@Override
	public void preInit() {
		/* INIT API*/
		FlowerManager.flowerFactory = new FlowerFactory();
		AlleleManager.alleleRegistry.registerSpeciesRoot(BotanyCore.getFlowerRoot());
		AlleleManager.alleleRegistry.registerAllele(ModuleGenetics.alleleEffectNone);
		EnumFlowerColor.setupMutations();
		FlowerDefinition.preInitFlowers();
		
		/* BACKPACK*/
		IBackpackInterface backpackInterface = BackpackManager.backpackInterface;

		if (ForestryAPI.enabledPlugins.contains("forestry.storage")) {
			Predicate<ItemStack> filter = BackpackManager.backpackInterface.createNaturalistBackpackFilter("rootFlowers");
			BackpackDefinition definition = new BackpackDefinition(new Color(0xf6e83e), Color.WHITE, filter);
			BackpackManager.backpackInterface.registerBackpackDefinition("botanist", definition);
			Item botanistBackpack = Botany.botanistBackpack = backpackInterface.createNaturalistBackpack("botanist", FlowerManager.flowerRoot).setRegistryName("botanist_bag").setUnlocalizedName("botany.botanist_bag");
			Botany.proxy.registerItem(botanistBackpack);
			botanistBackpack.setCreativeTab(CreativeTabBotany.instance);
		} else {
			Botany.botanistBackpack = null;
		}
		
		/* ITEMS */
		Botany.flower = new BlockFlower();
		Botany.flowerItem = new ItemBotany("itemFlower", EnumFlowerStage.FLOWER, "");
		Botany.pollen = new ItemBotany("pollen", EnumFlowerStage.POLLEN, "pollen");
		Botany.seed = new ItemBotany("seed", EnumFlowerStage.SEED, "germling");
		Botany.database = new ItemDictionary();

		Botany.proxy.registerBlock(Botany.flower);
		Botany.proxy.registerItem(Botany.flowerItem);
		Botany.proxy.registerItem(Botany.pollen);
		Botany.proxy.registerItem(Botany.seed);
		Botany.proxy.registerItem(Botany.database);

		BinnieCore.getBinnieProxy().registerTileEntity(TileEntityFlower.class, "botany.tile.flower", null);
	}

	@Override
	public void init() {
		EnumFlowerColor.initColours();
		FlowerDefinition.initFlowers();
	}

	@Override
	public void postInit() {
		forestry.api.apiculture.FlowerManager.flowerRegistry.registerAcceptableFlower(Botany.flower, "flowersVanilla");
		RecipeManagers.carpenterManager.addRecipe(
			100,
			Binnie.LIQUID.getFluidStack(ManagerLiquid.WATER, 2000),
			ItemStack.EMPTY,
			new ItemStack(Botany.database),
			"X#X",
			"YEY",
			"RDR",
			'#', Blocks.GLASS_PANE,
			'X', Items.GOLD_INGOT,
			'Y', Items.GOLD_NUGGET,
			'R', Items.REDSTONE,
			'D', Items.DIAMOND,
			'E', Items.EMERALD
		);
		RecipeUtil.addRecipe
			(Botany.botanistBackpack,
			"X#X",
			"VYZ",
			"X#X",
			'#', Blocks.WOOL,
			'X', Items.STRING,
			'V', Botany.soilMeter,
			'Z', "toolTrowel",
			'Y', "chestWood"
		);
	}
}
