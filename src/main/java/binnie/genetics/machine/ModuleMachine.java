package binnie.genetics.machine;

import binnie.core.BinnieCore;
import binnie.core.IInitializable;
import binnie.core.Mods;
import binnie.core.machines.MachineGroup;
import binnie.core.machines.inventory.ValidatorIcon;
import binnie.genetics.CreativeTabGenetics;
import binnie.genetics.Genetics;
import binnie.genetics.item.GeneticsItems;
import binnie.genetics.machine.acclimatiser.Acclimatiser;
import binnie.genetics.machine.incubator.Incubator;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ModuleMachine implements IInitializable {
	static ValidatorIcon IconSequencer;
	public static ValidatorIcon iconSerum;
	static ValidatorIcon IconEnzyme;
	public static ValidatorIcon IconDye;
	static ValidatorIcon IconBacteria;
	public static ValidatorIcon IconNugget;

	@Override
	public void preInit() {
		(Genetics.packageGenetic = new MachineGroup(Genetics.instance, "machine", "machine", GeneticMachine.values())).setCreativeTab(CreativeTabGenetics.instance);
		(Genetics.packageLabMachine = new MachineGroup(Genetics.instance, "labMachine", "labMachine", LaboratoryMachine.values())).setCreativeTab(CreativeTabGenetics.instance);
		(Genetics.packageAdvGenetic = new MachineGroup(Genetics.instance, "advMachine", "advMachine", AdvGeneticMachine.values())).setCreativeTab(CreativeTabGenetics.instance);
	}

	@Override
	public void init() {
		ModuleMachine.IconSequencer = new ValidatorIcon(Genetics.instance, "validator/sequencer.0", "validator/sequencer.1");
		ModuleMachine.iconSerum = new ValidatorIcon(Genetics.instance, "validator/serum.0", "validator/serum.1");
		ModuleMachine.IconEnzyme = new ValidatorIcon(Genetics.instance, "validator/enzyme.0", "validator/enzyme.1");
		ModuleMachine.IconDye = new ValidatorIcon(Genetics.instance, "validator/dye.0", "validator/dye.1");
		ModuleMachine.IconNugget = new ValidatorIcon(Genetics.instance, "validator/nugget.0", "validator/nugget.1");
		ModuleMachine.IconBacteria = new ValidatorIcon(Genetics.instance, "validator/bacteria.0", "validator/bacteria.1");
		Incubator.addRecipes();
	}

	@Override
	public void postInit() {
		Acclimatiser.setupRecipes();
		final Object[] standardCircuit = {Mods.Forestry.stack("chipsets", 1, 1)};
		final Object[] advCircuit = {GeneticsItems.IntegratedCircuit.get(1)};
		final String ironGear = OreDictionary.getOres("gearIron").isEmpty() ? "gearIron" : "ingotIron";
		final String goldGear = OreDictionary.getOres("gearGold").isEmpty() ? "gearIron" : "ingotIron";
		final String diamondGear = OreDictionary.getOres("gearDiamond").isEmpty() ? "gearIron" : "ingotIron";
		for (final Object circuit : standardCircuit) {
			GameRegistry.addRecipe(new ShapedOreRecipe(LaboratoryMachine.Incubator.get(1), "gFg", "cCc", "aPa", 'C', GeneticsItems.LaboratoryCasing.get(1), 'F', Blocks.FURNACE, 'c', circuit, 'g', Blocks.GLASS_PANE, 'P', "gearBronze", 'a', ironGear));
			Item alyzer = null;
			if (BinnieCore.isApicultureActive()) {
				alyzer = Mods.Forestry.item("beealyzer");
			} else if (BinnieCore.isArboricultureActive()) {
				alyzer = Mods.Forestry.item("treealyzer");
			} else if (BinnieCore.isArboricultureActive()) {
				alyzer = Mods.Forestry.item("flutterlyzer");
			}
			if (alyzer != null) {
				GameRegistry.addRecipe(new ShapedOreRecipe(LaboratoryMachine.Analyser.get(1), "gBg", "cCc", "aPa", 'C', GeneticsItems.LaboratoryCasing.get(1), 'B', alyzer, 'c', circuit, 'g', Blocks.GLASS_PANE, 'P', "gearBronze", 'a', GeneticsItems.DNADye.get(1)));
			}
			GameRegistry.addRecipe(new ShapedOreRecipe(LaboratoryMachine.Genepool.get(1), "gBg", "cCc", "aPa", 'C', GeneticsItems.LaboratoryCasing.get(1), 'B', "gearBronze", 'c', circuit, 'g', Blocks.GLASS_PANE, 'P', "gearBronze", 'a', Blocks.GLASS));
			GameRegistry.addRecipe(new ShapedOreRecipe(LaboratoryMachine.Acclimatiser.get(1), "gBg", "cCc", "aPa", 'C', GeneticsItems.LaboratoryCasing.get(1), 'B', Items.LAVA_BUCKET, 'c', circuit, 'g', Blocks.GLASS_PANE, 'P', "gearBronze", 'a', Items.WATER_BUCKET));
		}
		for (final Object circuit : advCircuit) {
			GameRegistry.addRecipe(new ShapedOreRecipe(GeneticMachine.Isolator.get(1), "gBg", "cCc", "aPa", 'C', GeneticsItems.LaboratoryCasing.get(1), 'B', goldGear, 'c', circuit, 'g', Items.GOLD_NUGGET, 'P', "gearBronze", 'a', GeneticsItems.Enzyme.get(1)));
			GameRegistry.addRecipe(new ShapedOreRecipe(GeneticMachine.Polymeriser.get(1), "gBg", "cCc", "gPg", 'C', GeneticsItems.LaboratoryCasing.get(1), 'B', ironGear, 'c', circuit, 'g', Items.GOLD_NUGGET, 'P', "gearBronze"));
			GameRegistry.addRecipe(new ShapedOreRecipe(GeneticMachine.Sequencer.get(1), "gBg", "cCc", "aPa", 'C', GeneticsItems.LaboratoryCasing.get(1), 'B', "gearBronze", 'c', circuit, 'g', Items.GOLD_NUGGET, 'P', "gearBronze", 'a', GeneticsItems.FluorescentDye.get(1)));
			GameRegistry.addRecipe(new ShapedOreRecipe(GeneticMachine.Inoculator.get(1), "gBg", "cCc", "aPa", 'C', GeneticsItems.LaboratoryCasing.get(1), 'B', diamondGear, 'c', circuit, 'g', Items.GOLD_NUGGET, 'P', "gearBronze", 'a', Items.EMERALD));
		}
		GameRegistry.addRecipe(new ShapedOreRecipe(AdvGeneticMachine.Splicer.get(1), "gBg", "cCc", "aPa", 'C', GeneticsItems.IntegratedCasing.get(1), 'B', diamondGear, 'c', GeneticsItems.IntegratedCPU.get(1), 'g', Items.GOLD_NUGGET, 'P', "gearBronze", 'a', Items.BLAZE_ROD));
		GameRegistry.addRecipe(new ShapedOreRecipe(LaboratoryMachine.LabMachine.get(1), "igi", "gCg", "igi", 'C', GeneticsItems.LaboratoryCasing.get(1), 'i', "ingotIron", 'g', Blocks.GLASS_PANE));
	}
}
