package binnie.botany.genetics;

import binnie.Binnie;
import binnie.botany.Botany;
import binnie.botany.core.BotanyCore;
import binnie.botany.flower.BlockFlower;
import binnie.botany.flower.ItemFlower;
import binnie.botany.flower.ItemPollen;
import binnie.botany.flower.ItemSeed;
import binnie.botany.flower.RendererBotany;
import binnie.botany.flower.TileEntityFlower;
import binnie.core.BinnieCore;
import binnie.core.IInitializable;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import forestry.api.apiculture.FlowerManager;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.recipes.RecipeManagers;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ModuleGenetics implements IInitializable {
	static AlleleEffectNone alleleEffectNone;

	static {
		ModuleGenetics.alleleEffectNone = new AlleleEffectNone();
	}

	@Override
	public void preInit() {
		EnumFlowerColor.setupMutations();
		Botany.flower = new BlockFlower();
		Botany.flowerItem = new ItemFlower();
		Botany.pollen = new ItemPollen();
		Botany.seed = new ItemSeed();
		AlleleManager.alleleRegistry.registerSpeciesRoot(BotanyCore.speciesRoot);
		AlleleManager.alleleRegistry.registerAllele(ModuleGenetics.alleleEffectNone);
		GameRegistry.registerBlock(Botany.flower, "flower");
		BinnieCore.proxy.registerTileEntity(TileEntityFlower.class, "botany.tile.flower", null);
		Botany.database = new ItemDictionary();
		Botany.encyclopedia = new ItemEncyclopedia(false);
		Botany.encyclopediaIron = new ItemEncyclopedia(true);
	}

	@Override
	public void init() {
		for (EnumFlowerColor color : EnumFlowerColor.values()) {
			AlleleManager.alleleRegistry.registerAllele(color.getAllele());
		}
		FlowerSpecies.setupVariants();
		for (FlowerSpecies species : FlowerSpecies.values()) {
			AlleleManager.alleleRegistry.registerAllele(species);
			BotanyCore.getFlowerRoot().registerTemplate(species.getUID(), species.getTemplate());
			for (IAllele[] variant : species.getVariants()) {
				BotanyCore.getFlowerRoot().registerTemplate(variant);
			}
		}
		RendererBotany.renderID = RenderingRegistry.getNextAvailableRenderId();
		BinnieCore.proxy.registerBlockRenderer(new RendererBotany());
	}

	@Override
	public void postInit() {
		GameRegistry.addRecipe(new ShapedOreRecipe(
			new ItemStack(Botany.encyclopedia),
			new Object[]{
				"fff", "fbf", "fff",
				'f', new ItemStack(Blocks.red_flower, 1, 32767),
				'b', Items.book
			}
		));
		GameRegistry.addRecipe(new ShapedOreRecipe(
			new ItemStack(Botany.encyclopedia),
			new Object[]{
				"fff", "fbf", "fff",
				'f', new ItemStack(Blocks.yellow_flower, 1, 32767),
				'b', Items.book
			}
		));
		GameRegistry.addRecipe(new ShapedOreRecipe(
			new ItemStack(Botany.encyclopedia),
			new Object[]{
				"fff", "fbf", "fff",
				'f', new ItemStack(Botany.flower, 1, 32767),
				'b', Items.book
			}
		));
		GameRegistry.addRecipe(new ShapelessOreRecipe(
			new ItemStack(Botany.encyclopediaIron),
			new Object[]{
				new ItemStack(Botany.encyclopedia),
				"ingotIron"
			}
		));
		FlowerManager.flowerRegistry.registerAcceptableFlower(Botany.flower, "flowersVanilla");
		RecipeManagers.carpenterManager.addRecipe(
			100,
			Binnie.Liquid.getLiquidStack("water", 2000),
			null,
			new ItemStack(Botany.database),
			"X#X", "YEY", "RDR",
			'#', Blocks.glass_pane,
			'X', Items.gold_ingot,
			'Y', Items.gold_nugget,
			'R', Items.redstone,
			'D', Items.diamond,
			'E', Items.emerald
		);
	}
}
