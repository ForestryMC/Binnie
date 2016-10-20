// 
// Decompiled by Procyon v0.5.30
// 

package binnie.botany.genetics;

import binnie.Binnie;
import forestry.api.recipes.RecipeManagers;
import forestry.api.apiculture.FlowerManager;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraft.init.Items;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import binnie.botany.flower.RendererBotany;
import cpw.mods.fml.client.registry.RenderingRegistry;
import binnie.botany.flower.TileEntityFlower;
import binnie.core.BinnieCore;
import net.minecraft.block.Block;
import cpw.mods.fml.common.registry.GameRegistry;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.ISpeciesRoot;
import binnie.botany.core.BotanyCore;
import forestry.api.genetics.AlleleManager;
import binnie.botany.flower.ItemSeed;
import binnie.botany.flower.ItemPollen;
import binnie.botany.flower.ItemFlower;
import binnie.botany.Botany;
import binnie.botany.flower.BlockFlower;
import binnie.core.IInitializable;

public class ModuleGenetics implements IInitializable
{
	static AlleleEffectNone alleleEffectNone;

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
		for (final EnumFlowerColor color : EnumFlowerColor.values()) {
			AlleleManager.alleleRegistry.registerAllele(color.getAllele());
		}
		FlowerSpecies.setupVariants();
		for (final FlowerSpecies species : FlowerSpecies.values()) {
			AlleleManager.alleleRegistry.registerAllele(species);
			BotanyCore.getFlowerRoot().registerTemplate(species.getUID(), species.getTemplate());
			for (final IAllele[] variant : species.getVariants()) {
				BotanyCore.getFlowerRoot().registerTemplate(variant);
			}
		}
		RendererBotany.renderID = RenderingRegistry.getNextAvailableRenderId();
		BinnieCore.proxy.registerBlockRenderer(new RendererBotany());
	}

	@Override
	public void postInit() {
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Botany.encyclopedia), new Object[] { "fff", "fbf", "fff", 'f', new ItemStack(Blocks.red_flower, 1, 32767), 'b', Items.book }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Botany.encyclopedia), new Object[] { "fff", "fbf", "fff", 'f', new ItemStack(Blocks.yellow_flower, 1, 32767), 'b', Items.book }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Botany.encyclopedia), new Object[] { "fff", "fbf", "fff", 'f', new ItemStack(Botany.flower, 1, 32767), 'b', Items.book }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Botany.encyclopediaIron), new Object[] { new ItemStack(Botany.encyclopedia), "ingotIron" }));
		FlowerManager.flowerRegistry.registerAcceptableFlower(Botany.flower, new String[] { "flowersVanilla" });
		RecipeManagers.carpenterManager.addRecipe(100, Binnie.Liquid.getLiquidStack("water", 2000), (ItemStack) null, new ItemStack(Botany.database), new Object[] { "X#X", "YEY", "RDR", '#', Blocks.glass_pane, 'X', Items.gold_ingot, 'Y', Items.gold_nugget, 'R', Items.redstone, 'D', Items.diamond, 'E', Items.emerald });
	}

	static {
		ModuleGenetics.alleleEffectNone = new AlleleEffectNone();
	}
}
