package binnie.botany.genetics;

import binnie.Binnie;
import binnie.botany.Botany;
import binnie.botany.api.EnumFlowerStage;
import binnie.botany.core.BotanyCore;
import binnie.botany.flower.BlockFlower;
import binnie.botany.flower.ItemBotany;
import binnie.botany.flower.TileEntityFlower;
import binnie.core.BinnieCore;
import binnie.core.IInitializable;
import forestry.api.apiculture.FlowerManager;
import forestry.api.genetics.AlleleManager;
import forestry.api.recipes.RecipeManagers;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ModuleGenetics implements IInitializable {
	static AlleleEffectNone alleleEffectNone = new AlleleEffectNone();

	@Override
	public void preInit() {
		/* INIT API*/
		binnie.botany.api.FlowerManager.flowerFactory = new FlowerFactory();
		AlleleManager.alleleRegistry.registerSpeciesRoot(BotanyCore.getFlowerRoot());
		AlleleManager.alleleRegistry.registerAllele(ModuleGenetics.alleleEffectNone);
		EnumFlowerColor.setupMutations();
		FlowerDefinition.preInitFlowers();

		Botany.flower = new BlockFlower();
		Botany.proxy.registerBlock(Botany.flower);
		BinnieCore.getBinnieProxy().registerTileEntity(TileEntityFlower.class, "botany.tile.flower", null);

		Botany.flowerItem = new ItemBotany("itemFlower", EnumFlowerStage.FLOWER, "");
		Botany.pollen = new ItemBotany("pollen", EnumFlowerStage.POLLEN, "pollen");
		Botany.seed = new ItemBotany("seed", EnumFlowerStage.SEED, "germling");
		Botany.database = new ItemDictionary();
		Botany.proxy.registerItem(Botany.flowerItem);
		Botany.proxy.registerItem(Botany.pollen);
		Botany.proxy.registerItem(Botany.seed);
		Botany.proxy.registerItem(Botany.database);
	}

	@Override
	public void init() {
		EnumFlowerColor.initColours();
		FlowerDefinition.initFlowers();
	}

	@Override
	public void postInit() {
		FlowerManager.flowerRegistry.registerAcceptableFlower(Botany.flower, "flowersVanilla");
		RecipeManagers.carpenterManager.addRecipe(100, Binnie.LIQUID.getFluidStack("water", 2000), ItemStack.EMPTY, new ItemStack(Botany.database),
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
	}
}
