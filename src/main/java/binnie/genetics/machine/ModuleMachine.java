package binnie.genetics.machine;

import binnie.Constants;
import binnie.core.util.RecipeUtil;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

import net.minecraftforge.fml.common.registry.GameRegistry;

import binnie.core.IInitializable;
import binnie.core.Mods;
import binnie.core.machines.MachineGroup;
import binnie.core.machines.inventory.ValidatorSprite;
import binnie.genetics.CreativeTabGenetics;
import binnie.genetics.Genetics;
import binnie.genetics.item.GeneticsItems;
import binnie.genetics.machine.acclimatiser.Acclimatiser;
import binnie.genetics.machine.incubator.Incubator;

public class ModuleMachine implements IInitializable {
	public static ValidatorSprite spriteSequencer;
	public static ValidatorSprite spriteSerum;
	public static ValidatorSprite spriteEnzyme;
	public static ValidatorSprite spriteDye;
	public static ValidatorSprite spriteBacteria;
	public static ValidatorSprite spriteNugget;

	public static MachineGroup packageGenetic;
	public static MachineGroup packageAdvGenetic;
	public static MachineGroup packageLabMachine;

	@Override
	public void preInit() {
		(packageGenetic = new MachineGroup(Genetics.instance, "machine", "machine", GeneticMachine.values())).setCreativeTab(CreativeTabGenetics.instance);
		(packageLabMachine = new MachineGroup(Genetics.instance, "lab_machine", "lab_machine", LaboratoryMachine.values())).setCreativeTab(CreativeTabGenetics.instance);
		(packageAdvGenetic = new MachineGroup(Genetics.instance, "adv_machine", "adv_machine", AdvGeneticMachine.values())).setCreativeTab(CreativeTabGenetics.instance);

		ModuleMachine.spriteSequencer = new ValidatorSprite(Genetics.instance, "validator/sequencer.0", "validator/sequencer.1");
		ModuleMachine.spriteSerum = new ValidatorSprite(Genetics.instance, "validator/serum.0", "validator/serum.1");
		ModuleMachine.spriteEnzyme = new ValidatorSprite(Genetics.instance, "validator/enzyme.0", "validator/enzyme.1");
		ModuleMachine.spriteDye = new ValidatorSprite(Genetics.instance, "validator/dye.0", "validator/dye.1");
		ModuleMachine.spriteNugget = new ValidatorSprite(Genetics.instance, "validator/nugget.0", "validator/nugget.1");
		ModuleMachine.spriteBacteria = new ValidatorSprite(Genetics.instance, "validator/bacteria.0", "validator/bacteria.1");
	}

	@Override
	public void init() {
		Incubator.addRecipes();
	}

	@Override
	public void postInit() {
		RecipeUtil recipeUtil = new RecipeUtil(Constants.GENETICS_MOD_ID);
		Acclimatiser.setupRecipes();
		final Object[] standardCircuit = {Mods.Forestry.stack("chipsets", 1, 1)};
		final Object[] advCircuit = {GeneticsItems.IntegratedCircuit.get(1)};
		final String ironGear = !OreDictionary.getOres("gearIron").isEmpty() ? "gearIron" : "ingotIron";
		final String goldGear = !OreDictionary.getOres("gearGold").isEmpty() ? "gearIron" : "ingotIron";
		final String diamondGear = !OreDictionary.getOres("gearDiamond").isEmpty() ? "gearIron" : "ingotIron";
		for (final Object circuit : standardCircuit) {
			recipeUtil.addRecipe("incubator", LaboratoryMachine.Incubator.get(1), "gFg", "cCc", "aPa", 'C', GeneticsItems.LaboratoryCasing.get(1), 'F', Blocks.FURNACE, 'c', circuit, 'g', Blocks.GLASS_PANE, 'P', "gearBronze", 'a', ironGear);
			Item alyzer = Mods.Forestry.item("portable_alyzer");
			recipeUtil.addRecipe("analyzer", LaboratoryMachine.Analyser.get(1), "gBg", "cCc", "aPa", 'C', GeneticsItems.LaboratoryCasing.get(1), 'B', alyzer, 'c', circuit, 'g', Blocks.GLASS_PANE, 'P', "gearBronze", 'a', GeneticsItems.DNADye.get(1));
			recipeUtil.addRecipe("genepool", LaboratoryMachine.Genepool.get(1), "gBg", "cCc", "aPa", 'C', GeneticsItems.LaboratoryCasing.get(1), 'B', "gearBronze", 'c', circuit, 'g', Blocks.GLASS_PANE, 'P', "gearBronze", 'a', Blocks.GLASS);
			recipeUtil.addRecipe("acclimatizer", LaboratoryMachine.Acclimatiser.get(1), "gBg", "cCc", "aPa", 'C', GeneticsItems.LaboratoryCasing.get(1), 'B', Items.LAVA_BUCKET, 'c', circuit, 'g', Blocks.GLASS_PANE, 'P', "gearBronze", 'a', Items.WATER_BUCKET);
		}
		for (final Object circuit : advCircuit) {
			recipeUtil.addRecipe("isolator", GeneticMachine.Isolator.get(1), "gBg", "cCc", "aPa", 'C', GeneticsItems.LaboratoryCasing.get(1), 'B', goldGear, 'c', circuit, 'g', Items.GOLD_NUGGET, 'P', "gearBronze", 'a', GeneticsItems.Enzyme.get(1));
			recipeUtil.addRecipe("polymeriser", GeneticMachine.Polymeriser.get(1), "gBg", "cCc", "gPg", 'C', GeneticsItems.LaboratoryCasing.get(1), 'B', ironGear, 'c', circuit, 'g', Items.GOLD_NUGGET, 'P', "gearBronze");
			recipeUtil.addRecipe("sequencer", GeneticMachine.Sequencer.get(1), "gBg", "cCc", "aPa", 'C', GeneticsItems.LaboratoryCasing.get(1), 'B', "gearBronze", 'c', circuit, 'g', Items.GOLD_NUGGET, 'P', "gearBronze", 'a', GeneticsItems.FluorescentDye.get(1));
			recipeUtil.addRecipe("inoculator", GeneticMachine.Inoculator.get(1), "gBg", "cCc", "aPa", 'C', GeneticsItems.LaboratoryCasing.get(1), 'B', diamondGear, 'c', circuit, 'g', Items.GOLD_NUGGET, 'P', "gearBronze", 'a', Items.EMERALD);
		}
		recipeUtil.addRecipe("splicer", AdvGeneticMachine.Splicer.get(1), "gBg", "cCc", "aPa", 'C', GeneticsItems.IntegratedCasing.get(1), 'B', diamondGear, 'c', GeneticsItems.IntegratedCPU.get(1), 'g', Items.GOLD_NUGGET, 'P', "gearBronze", 'a', Items.BLAZE_ROD);
		recipeUtil.addRecipe("lab_machine", LaboratoryMachine.LabMachine.get(1), "igi", "gCg", "igi", 'C', GeneticsItems.LaboratoryCasing.get(1), 'i', "ingotIron", 'g', Blocks.GLASS_PANE);
	}
}
