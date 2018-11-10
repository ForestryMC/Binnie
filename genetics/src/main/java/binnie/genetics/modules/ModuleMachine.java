package binnie.genetics.modules;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import net.minecraftforge.oredict.OreDictionary;

import forestry.api.modules.ForestryModule;

import binnie.core.Constants;
import binnie.core.Mods;
import binnie.core.machines.MachineGroup;
import binnie.core.machines.inventory.ValidatorSprite;
import binnie.core.modules.BlankModule;
import binnie.core.util.RecipeUtil;
import binnie.genetics.CreativeTabGenetics;
import binnie.genetics.Genetics;
import binnie.genetics.item.GeneticsItems;
import binnie.genetics.machine.AdvGeneticMachine;
import binnie.genetics.machine.GeneticMachine;
import binnie.genetics.machine.LaboratoryMachine;
import binnie.genetics.machine.acclimatiser.Acclimatiser;
import binnie.genetics.machine.incubator.Incubator;

@ForestryModule(moduleID = GeneticsModuleUIDs.MACHINES, containerID = Constants.GENETICS_MOD_ID, name = "Machines", unlocalizedDescription = "genetics.module.machines")
public class ModuleMachine extends BlankModule {
	private static ValidatorSprite spriteSequencer;
	private static ValidatorSprite spriteSerum;
	private static ValidatorSprite spriteEnzyme;
	private static ValidatorSprite spriteDye;
	private static ValidatorSprite spriteBacteria;
	private static ValidatorSprite spriteNugget;

	private static MachineGroup packageGenetic;
	private static MachineGroup packageAdvGenetic;
	private static MachineGroup packageLabMachine;

	public static ValidatorSprite getSpriteSequencer() {
		return spriteSequencer;
	}

	public static ValidatorSprite getSpriteSerum() {
		return spriteSerum;
	}

	public static ValidatorSprite getSpriteEnzyme() {
		return spriteEnzyme;
	}

	public static ValidatorSprite getSpriteDye() {
		return spriteDye;
	}

	public static ValidatorSprite getSpriteBacteria() {
		return spriteBacteria;
	}

	public static ValidatorSprite getSpriteNugget() {
		return spriteNugget;
	}

	public static MachineGroup getPackageGenetic() {
		return packageGenetic;
	}

	public static MachineGroup getPackageAdvGenetic() {
		return packageAdvGenetic;
	}

	public static MachineGroup getPackageLabMachine() {
		return packageLabMachine;
	}

	public ModuleMachine() {
		super(Constants.GENETICS_MOD_ID, "core");
	}

	@Override
	public void preInit() {
		(packageGenetic = new MachineGroup(Genetics.instance, "machine", "machine", GeneticMachine.values())).setCreativeTab(CreativeTabGenetics.INSTANCE);
		(packageLabMachine = new MachineGroup(Genetics.instance, "machine.lab_machine", "lab_machine", LaboratoryMachine.values())).setCreativeTab(CreativeTabGenetics.INSTANCE);
		(packageAdvGenetic = new MachineGroup(Genetics.instance, "machine.adv_machine", "adv_machine", AdvGeneticMachine.values())).setCreativeTab(CreativeTabGenetics.INSTANCE);

		spriteSequencer = new ValidatorSprite(Genetics.instance, "validator/sequencer.0", "validator/sequencer.1");
		spriteSerum = new ValidatorSprite(Genetics.instance, "validator/serum.0", "validator/serum.1");
		spriteEnzyme = new ValidatorSprite(Genetics.instance, "validator/enzyme.0", "validator/enzyme.1");
		spriteDye = new ValidatorSprite(Genetics.instance, "validator/dye.0", "validator/dye.1");
		spriteNugget = new ValidatorSprite(Genetics.instance, "validator/nugget.0", "validator/nugget.1");
		spriteBacteria = new ValidatorSprite(Genetics.instance, "validator/bacteria.0", "validator/bacteria.1");
	}

	@Override
	public void doInit() {
		Incubator.addRecipes();

		RecipeUtil recipeUtil = new RecipeUtil(Constants.GENETICS_MOD_ID);
		Acclimatiser.setupRecipes();
		final Object[] standardCircuit = {Mods.Forestry.stack("chipsets", 1, 1)};
		final Object[] advCircuit = {GeneticsItems.IntegratedCircuit.get(1)};
		final String ironGear = !OreDictionary.getOres("gearIron").isEmpty() ? "gearIron" : "ingotIron";
		final String goldGear = !OreDictionary.getOres("gearGold").isEmpty() ? "gearGold" : "ingotIron";
		final String diamondGear = !OreDictionary.getOres("gearDiamond").isEmpty() ? "gearDiamond" : "ingotIron";
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
