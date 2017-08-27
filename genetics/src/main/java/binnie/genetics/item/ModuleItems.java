package binnie.genetics.item;

import com.google.common.base.Preconditions;

import javax.annotation.Nullable;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.common.registry.GameRegistry;

import forestry.api.recipes.RecipeManagers;

import binnie.core.Binnie;
import binnie.core.Constants;
import binnie.core.IInitializable;
import binnie.core.Mods;
import binnie.core.item.ItemMisc;
import binnie.core.liquid.FluidContainerType;
import binnie.core.liquid.ManagerLiquid;
import binnie.core.util.RecipeUtil;
import binnie.genetics.CreativeTabGenetics;
import binnie.genetics.Genetics;
import binnie.genetics.recipes.RegistryRecipe;

public class ModuleItems implements IInitializable {
	public ItemSerum itemSerum;
	public ItemSequence itemSequencer;
	public ItemDatabase database;
	public ItemAnalyst analyst;
	public Item registry;
	public Item masterRegistry;
	public ItemSerumArray itemSerumArray;
	@Nullable
	private Item itemGenetics;

	@Override
	public void preInit() {
		itemSerum = new ItemSerum();
		Genetics.proxy.registerItem(itemSerum);
		itemSerumArray = new ItemSerumArray();
		Genetics.proxy.registerItem(itemSerumArray);

		database = new ItemDatabase();
		Genetics.proxy.registerItem(database);
		analyst = new ItemAnalyst();
		Genetics.proxy.registerItem(analyst);
		registry = new ItemRegistry();
		Genetics.proxy.registerItem(registry);
		masterRegistry = new ItemMasterRegistry();
		Genetics.proxy.registerItem(masterRegistry);
		itemGenetics = new ItemMisc(CreativeTabGenetics.INSTANCE, GeneticsItems.values());
		Genetics.proxy.registerItem(itemGenetics);
		itemSequencer = new ItemSequence();
		Genetics.proxy.registerItem(itemSequencer);

		Binnie.LIQUID.createLiquids(GeneticLiquid.values());
	}

	public Item getItemGenetics() {
		Preconditions.checkState(itemGenetics != null);
		return itemGenetics;
	}

	@Override
	public void init() {
		RecipeUtil recipeUtil = new RecipeUtil(Constants.GENETICS_MOD_ID);
		recipeUtil.addShapelessRecipe("dna_dye_from_glowstone", GeneticsItems.DNADye.get(8),
			Items.GLOWSTONE_DUST, new ItemStack(Items.DYE, 1, 5)
		);
		recipeUtil.addRecipe("laboratory_casing", GeneticsItems.LaboratoryCasing.get(itemGenetics, 1),
			"iii",
			"iYi",
			"iii",
			'i', "ingotIron", 'Y', Mods.Forestry.item("sturdy_machine")
		);
		recipeUtil.addShapelessRecipe("dna_dye", GeneticsItems.DNADye.get(itemGenetics, 2), "dyePurple", "dyeMagenta", "dyePink");
		recipeUtil.addShapelessRecipe("fluorescent_dye", GeneticsItems.FluorescentDye.get(itemGenetics, 2), "dyeOrange", "dyeYellow", "dustGlowstone");
		recipeUtil.addShapelessRecipe("growth_medium", GeneticsItems.GrowthMedium.get(itemGenetics, 2), new ItemStack(Items.DYE, 1, 15), Items.SUGAR);
		recipeUtil.addRecipe("empty_sequencer", GeneticsItems.EmptySequencer.get(itemGenetics, 1),
			" p ",
			"iGi",
			" p ",
			'i', "ingotGold", 'G', Blocks.GLASS_PANE, 'p', Items.PAPER
		);
		recipeUtil.addRecipe("empty_serum", GeneticsItems.EMPTY_SERUM.get(itemGenetics, 1),
			" g ",
			" G ",
			"GGG",
			'g', "ingotGold", 'G', Blocks.GLASS_PANE
		);
		recipeUtil.addRecipe("empty_genome", GeneticsItems.EMPTY_GENOME.get(itemGenetics, 1),
			"sss",
			"sss",
			"sss",
			's', GeneticsItems.EMPTY_SERUM.get(itemGenetics, 1)
		);
		recipeUtil.addRecipe("cylinder", FluidContainerType.CYLINDER.get(8),
			" g ",
			"g g",
			"   ",
			'g', Blocks.GLASS_PANE
		);
		recipeUtil.addRecipe("integrated_circuit", GeneticsItems.IntegratedCircuit.get(itemGenetics, 1),
			"l g",
			" c ",
			"g l",
			'c', Mods.Forestry.stack("chipsets", 1, 1), 'l', new ItemStack(Items.DYE, 1, 4), 'g', "dustGlowstone"
		);
		recipeUtil.addRecipe("integrated_casing", GeneticsItems.IntegratedCasing.get(itemGenetics, 1),
			"ccc",
			"cdc",
			"ccc",
			'c', GeneticsItems.IntegratedCircuit.get(itemGenetics, 1), 'd', GeneticsItems.LaboratoryCasing.get(itemGenetics, 1)
		);
		recipeUtil.addRecipe("integrated_cpu", GeneticsItems.IntegratedCPU.get(itemGenetics, 1),
			"ccc",
			"cdc",
			"ccc",
			'c', GeneticsItems.IntegratedCircuit.get(itemGenetics, 1), 'd', Items.DIAMOND
		);


		RecipeManagers.carpenterManager.addRecipe(100, Binnie.LIQUID.getFluidStack(ManagerLiquid.WATER, 2000), ItemStack.EMPTY,
			new ItemStack(database),
			"X#X",
			"YEY",
			"RDR",
			'#', Blocks.GLASS_PANE, 'X', Items.DIAMOND, 'Y', Items.DIAMOND, 'R', Items.REDSTONE, 'D', Items.ENDER_EYE, 'E', Blocks.OBSIDIAN
		);
		GameRegistry.addSmelting(itemSequencer, GeneticsItems.EmptySequencer.get(itemGenetics, 1), 0.0f);
		GameRegistry.addSmelting(itemSerum, GeneticsItems.EMPTY_SERUM.get(itemGenetics, 1), 0.0f);
		GameRegistry.addSmelting(itemSerumArray, GeneticsItems.EMPTY_GENOME.get(itemGenetics, 1), 0.0f);

		recipeUtil.addRecipe("analyst", new ItemStack(analyst),
			" c ",
			"cac",
			" d ",
			'c', GeneticsItems.IntegratedCircuit.get(itemGenetics, 1), 'a', Mods.Forestry.item("portable_alyzer"), 'd', new ItemStack(Items.DIAMOND)
		);

		RegistryRecipe recipe = RegistryRecipe.create(registry, itemGenetics);
		recipeUtil.addRecipe("registry", recipe);
	}
}
