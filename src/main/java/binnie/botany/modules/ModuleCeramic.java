package binnie.botany.modules;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;

import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;

import binnie.Constants;
import binnie.botany.Botany;
import binnie.botany.CreativeTabBotany;
import binnie.botany.blocks.BlockCeramic;
import binnie.botany.blocks.BlockCeramicBrick;
import binnie.botany.blocks.BlockCeramicPatterned;
import binnie.botany.blocks.BlockStainedGlass;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.botany.items.BotanyItems;
import binnie.botany.items.CeramicItems;
import binnie.botany.items.ItemCeramic;
import binnie.botany.items.ItemCeramicBrick;
import binnie.botany.items.ItemClay;
import binnie.botany.items.ItemPigment;
import binnie.botany.items.ItemStainedGlass;
import binnie.botany.recipes.CeramicTileRecipe;
import binnie.botany.recipes.PigmentRecipe;
import binnie.core.block.TileEntityMetadata;
import binnie.core.item.ItemMisc;
import binnie.core.util.RecipeUtil;
import binnie.extratrees.carpentry.ItemDesign;
import binnie.modules.BinnieModule;
import binnie.modules.Module;
import binnie.modules.ModuleManager;

@BinnieModule(moduleID = BotanyModuleUIDs.CERAMIC, moduleContainerID = Constants.BOTANY_MOD_ID, name = "Ceramic", unlocalizedDescription = "botany.module.ceramic")
public class ModuleCeramic extends Module {
	public static BlockCeramic ceramic;
	public static BlockCeramicPatterned ceramicTile;
	public static BlockStainedGlass stained;
	public static BlockCeramicBrick ceramicBrick;

	public static ItemPigment pigment;
	public static ItemClay clay;
	public static ItemMisc misc;

	@Override
	public void registerItemsAndBlocks() {
		ceramic = new BlockCeramic();
		stained = new BlockStainedGlass();
		ceramicTile = new BlockCeramicPatterned();
		ceramicBrick = new BlockCeramicBrick();

		misc = new ItemMisc(CreativeTabBotany.instance, CeramicItems.values(), "misc_ceramic");
		pigment = new ItemPigment();
		clay = new ItemClay();

		Botany.proxy.registerBlock(ceramic, new ItemCeramic(ceramic));
		Botany.proxy.registerBlock(stained, new ItemStainedGlass(stained));
		Botany.proxy.registerBlock(ceramicTile, new ItemDesign(ceramicTile));
		Botany.proxy.registerBlock(ceramicBrick, new ItemCeramicBrick(ceramicBrick));

		Botany.proxy.registerItem(pigment);
		Botany.proxy.registerItem(clay);
		Botany.proxy.registerItem(misc);

		OreDictionary.registerOre("pigment", pigment);
	}

	@Override
	public void postInit() {
		RecipeUtil recipeUtil = new RecipeUtil(Constants.BOTANY_MOD_ID);
		ForgeRegistries.RECIPES.register(new CeramicTileRecipe());

		if(ModuleManager.isEnabled(Constants.BOTANY_MOD_ID, BotanyModuleUIDs.GARDENING)){
			recipeUtil.addShapelessRecipe("mortar_old", CeramicItems.MORTAR.get(1), BotanyItems.MORTAR.get(1));
		}

		recipeUtil.addShapelessRecipe("pigment_black", new ItemStack(pigment, 2, EnumFlowerColor.Black.ordinal()), "pigment", "pigment", "dyeBlack");

		recipeUtil.addRecipe("mortar", CeramicItems.MORTAR.get(6),
			" c ",
			"cgc",
			" c ",
			'c', Items.CLAY_BALL,
			'g', Blocks.GRAVEL);
		for (EnumFlowerColor c : EnumFlowerColor.values()) {
			ItemStack clay = new ItemStack(ModuleCeramic.clay, 1, c.ordinal());
			ItemStack pigment = new ItemStack(ModuleCeramic.pigment, 1, c.ordinal());
			recipeUtil.addShapelessRecipe("clay_" + c.getIdent(), clay, Items.CLAY_BALL, Items.CLAY_BALL, Items.CLAY_BALL, pigment);
			GameRegistry.addSmelting(clay, TileEntityMetadata.getItemStack(ceramic, c.ordinal()), 0.0f);
			ItemStack glass = TileEntityMetadata.getItemStack(stained, c.ordinal());
			glass.setCount(4);

			recipeUtil.addRecipe("mortar_" + c.getIdent(),
				glass,
				" g ",
				"gpg",
				" g ",
				'g', Blocks.GLASS,
				'p', pigment
			);
		}
		ForgeRegistries.RECIPES.register(new PigmentRecipe());
	}
}
