package binnie.genetics.modules;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import net.minecraftforge.oredict.OreDictionary;

import forestry.api.modules.ForestryModule;

import binnie.core.Constants;
import binnie.core.Mods;
import binnie.core.machines.inventory.ValidatorSprite;
import binnie.core.modules.BinnieModule;
import binnie.core.modules.GeneticsModuleUIDs;
import binnie.core.util.OreDictUtils;
import binnie.core.util.RecipeUtil;
import binnie.genetics.Genetics;
import binnie.genetics.item.GeneticsItems;
import binnie.genetics.machine.AdvGeneticMachine;
import binnie.genetics.machine.GeneticMachine;
import binnie.genetics.machine.LaboratoryMachine;
import binnie.genetics.machine.acclimatiser.Acclimatiser;
import binnie.genetics.machine.incubator.Incubator;

@ForestryModule(moduleID = GeneticsModuleUIDs.MACHINES, containerID = Constants.GENETICS_MOD_ID, name = "Machines", unlocalizedDescription = "genetics.module.machines")
public class ModuleMachine extends BinnieModule {
	private static final ValidatorSprite SPRITE_SEQUENCER = new ValidatorSprite(Genetics.instance, "validator/sequencer.0", "validator/sequencer.1");
	private static final ValidatorSprite SPRITE_SERUM = new ValidatorSprite(Genetics.instance, "validator/serum.0", "validator/serum.1");
	private static final ValidatorSprite SPRITE_ENZYME = new ValidatorSprite(Genetics.instance, "validator/enzyme.0", "validator/enzyme.1");
	private static final ValidatorSprite SPRITE_DYE = new ValidatorSprite(Genetics.instance, "validator/dye.0", "validator/dye.1");
	private static final ValidatorSprite SPRITE_BACTERIA = new ValidatorSprite(Genetics.instance, "validator/nugget.0", "validator/nugget.1");
	private static final ValidatorSprite SPRITE_NUGGET = new ValidatorSprite(Genetics.instance, "validator/bacteria.0", "validator/bacteria.1");

	public static ValidatorSprite getSpriteSequencer() {
		return SPRITE_SEQUENCER;
	}

	public static ValidatorSprite getSpriteSerum() {
		return SPRITE_SERUM;
	}

	public static ValidatorSprite getSpriteEnzyme() {
		return SPRITE_ENZYME;
	}

	public static ValidatorSprite getSpriteDye() {
		return SPRITE_DYE;
	}

	public static ValidatorSprite getSpriteBacteria() {
		return SPRITE_BACTERIA;
	}

	public static ValidatorSprite getSpriteNugget() {
		return SPRITE_NUGGET;
	}

	public ModuleMachine() {
		super(Constants.GENETICS_MOD_ID, GeneticsModuleUIDs.CORE);
	}

	@Override
	public void doInit() {
		Incubator.addRecipes();

		RecipeUtil recipeUtil = new RecipeUtil(Constants.GENETICS_MOD_ID);
		Acclimatiser.setupRecipes();
		Object[] standardCircuit = {Mods.Forestry.stack("chipsets", 1, 1)};
		Object[] advCircuit = {GeneticsItems.IntegratedCircuit.stack(1)};
		String ironGear = OreDictUtils.getOrElse(OreDictUtils.GEAR_IRON, OreDictUtils.INGOT_IRON);
		String goldGear = OreDictUtils.getOrElse(OreDictUtils.GEAR_GOLD, OreDictUtils.INGOT_IRON);
		String diamondGear = !OreDictionary.getOres("gearDiamond").isEmpty() ? "gearDiamond" : "ingotIron";
		for (Object circuit : standardCircuit) {
			recipeUtil.addRecipe("incubator", LaboratoryMachine.Incubator.get(1), "gFg", "cCc", "aPa", 'C', GeneticsItems.LaboratoryCasing.stack(1), 'F', Blocks.FURNACE, 'c', circuit, 'g', Blocks.GLASS_PANE, 'P', OreDictUtils.GEAR_BRONZE, 'a', ironGear);
			Item alyzer = Mods.Forestry.item("portable_alyzer");
			recipeUtil.addRecipe("analyzer", LaboratoryMachine.Analyser.get(1), "gBg", "cCc", "aPa", 'C', GeneticsItems.LaboratoryCasing.stack(1), 'B', alyzer, 'c', circuit, 'g', Blocks.GLASS_PANE, 'P', OreDictUtils.GEAR_BRONZE, 'a', GeneticsItems.DNADye.stack(1));
			recipeUtil.addRecipe("genepool", LaboratoryMachine.Genepool.get(1), "gBg", "cCc", "aPa", 'C', GeneticsItems.LaboratoryCasing.stack(1), 'B', OreDictUtils.GEAR_BRONZE, 'c', circuit, 'g', Blocks.GLASS_PANE, 'P', OreDictUtils.GEAR_BRONZE, 'a', Blocks.GLASS);
			recipeUtil.addRecipe("acclimatizer", LaboratoryMachine.Acclimatiser.get(1), "gBg", "cCc", "aPa", 'C', GeneticsItems.LaboratoryCasing.stack(1), 'B', Items.LAVA_BUCKET, 'c', circuit, 'g', Blocks.GLASS_PANE, 'P', OreDictUtils.GEAR_BRONZE, 'a', Items.WATER_BUCKET);
		}
		for (Object circuit : advCircuit) {
			recipeUtil.addRecipe("isolator", GeneticMachine.Isolator.get(1), "gBg", "cCc", "aPa", 'C', GeneticsItems.LaboratoryCasing.stack(1), 'B', goldGear, 'c', circuit, 'g', Items.GOLD_NUGGET, 'P', OreDictUtils.GEAR_BRONZE, 'a', GeneticsItems.Enzyme.stack(1));
			recipeUtil.addRecipe("polymeriser", GeneticMachine.Polymeriser.get(1), "gBg", "cCc", "gPg", 'C', GeneticsItems.LaboratoryCasing.stack(1), 'B', ironGear, 'c', circuit, 'g', Items.GOLD_NUGGET, 'P', OreDictUtils.GEAR_BRONZE);
			recipeUtil.addRecipe("sequencer", GeneticMachine.Sequencer.get(1), "gBg", "cCc", "aPa", 'C', GeneticsItems.LaboratoryCasing.stack(1), 'B', OreDictUtils.GEAR_BRONZE, 'c', circuit, 'g', Items.GOLD_NUGGET, 'P', OreDictUtils.GEAR_BRONZE, 'a', GeneticsItems.FluorescentDye.stack(1));
			recipeUtil.addRecipe("inoculator", GeneticMachine.Inoculator.get(1), "gBg", "cCc", "aPa", 'C', GeneticsItems.LaboratoryCasing.stack(1), 'B', diamondGear, 'c', circuit, 'g', Items.GOLD_NUGGET, 'P', OreDictUtils.GEAR_BRONZE, 'a', Items.EMERALD);
		}
		recipeUtil.addRecipe("splicer", AdvGeneticMachine.Splicer.get(1), "gBg", "cCc", "aPa", 'C', GeneticsItems.IntegratedCasing.stack(1), 'B', diamondGear, 'c', GeneticsItems.IntegratedCPU.stack(1), 'g', Items.GOLD_NUGGET, 'P', OreDictUtils.GEAR_BRONZE, 'a', Items.BLAZE_ROD);
		recipeUtil.addRecipe("lab_machine", LaboratoryMachine.LabMachine.get(1), "igi", "gCg", "igi", 'C', GeneticsItems.LaboratoryCasing.stack(1), 'i', OreDictUtils.INGOT_IRON, 'g', Blocks.GLASS_PANE);
	}
}
