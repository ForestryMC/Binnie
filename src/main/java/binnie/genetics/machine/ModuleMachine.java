package binnie.genetics.machine;

import binnie.core.BinnieCore;
import binnie.core.IInitializable;
import binnie.core.Mods;
import binnie.core.machines.MachineGroup;
import binnie.core.machines.inventory.ValidatorIcon;
import binnie.genetics.Genetics;
import binnie.genetics.GeneticsCreativeTab;
import binnie.genetics.item.GeneticsItems;
import binnie.genetics.machine.acclimatiser.Acclimatiser;
import binnie.genetics.machine.incubator.Incubator;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ModuleMachine implements IInitializable {
    public static ValidatorIcon IconDye;
    public static ValidatorIcon IconSequencer;
    public static ValidatorIcon IconSerum;
    public static ValidatorIcon IconEnzyme;
    public static ValidatorIcon IconBacteria;
    public static ValidatorIcon IconNugget;

    @Override
    public void preInit() {
        Genetics.packageGenetic = new MachineGroup(Genetics.instance, "machine", "machine", GeneticMachine.values());
        Genetics.packageGenetic.setCreativeTab(GeneticsCreativeTab.instance);

        Genetics.packageLabMachine =
                new MachineGroup(Genetics.instance, "labMachine", "labMachine", LaboratoryMachine.values());
        Genetics.packageLabMachine.setCreativeTab(GeneticsCreativeTab.instance);

        Genetics.packageAdvGenetic =
                new MachineGroup(Genetics.instance, "advMachine", "advMachine", AdvGeneticMachine.values());
        Genetics.packageAdvGenetic.setCreativeTab(GeneticsCreativeTab.instance);
    }

    @Override
    public void init() {
        ModuleMachine.IconSequencer =
                new ValidatorIcon(Genetics.instance, "validator/sequencer.0", "validator/sequencer.1");
        ModuleMachine.IconSerum = new ValidatorIcon(Genetics.instance, "validator/serum.0", "validator/serum.1");
        ModuleMachine.IconEnzyme = new ValidatorIcon(Genetics.instance, "validator/enzyme.0", "validator/enzyme.1");
        ModuleMachine.IconDye = new ValidatorIcon(Genetics.instance, "validator/dye.0", "validator/dye.1");
        ModuleMachine.IconNugget = new ValidatorIcon(Genetics.instance, "validator/nugget.0", "validator/nugget.1");
        ModuleMachine.IconBacteria =
                new ValidatorIcon(Genetics.instance, "validator/bacteria.0", "validator/bacteria.1");
        Incubator.addRecipes();
    }

    @Override
    public void postInit() {
        Acclimatiser.setupRecipes();
        Object[] standardCircuit = {Mods.forestry.stack("chipsets", 1, 1)};
        Object[] advCircuit = {GeneticsItems.IntegratedCircuit.get(1)};
        String ironGear = OreDictionary.getOres("gearIron").isEmpty() ? "gearIron" : "ingotIron";
        String goldGear = OreDictionary.getOres("gearGold").isEmpty() ? "gearIron" : "ingotIron";
        String diamondGear = OreDictionary.getOres("gearDiamond").isEmpty() ? "gearIron" : "ingotIron";

        for (Object circuit : standardCircuit) {
            GameRegistry.addRecipe(new ShapedOreRecipe(LaboratoryMachine.Incubator.get(1), new Object[] {
                "gFg",
                "cCc",
                "aPa",
                'C',
                GeneticsItems.LaboratoryCasing.get(1),
                'F',
                Blocks.furnace,
                'c',
                circuit,
                'g',
                Blocks.glass_pane,
                'P',
                "gearBronze",
                'a',
                ironGear
            }));

            Item alyzer = null;
            if (BinnieCore.isApicultureActive()) {
                alyzer = Mods.forestry.item("beealyzer");
            } else if (BinnieCore.isArboricultureActive()) {
                alyzer = Mods.forestry.item("treealyzer");
            } else if (BinnieCore.isArboricultureActive()) {
                alyzer = Mods.forestry.item("flutterlyzer");
            }

            if (alyzer != null) {
                GameRegistry.addRecipe(new ShapedOreRecipe(LaboratoryMachine.Analyser.get(1), new Object[] {
                    "gBg",
                    "cCc",
                    "aPa",
                    'C',
                    GeneticsItems.LaboratoryCasing.get(1),
                    'B',
                    alyzer,
                    'c',
                    circuit,
                    'g',
                    Blocks.glass_pane,
                    'P',
                    "gearBronze",
                    'a',
                    GeneticsItems.DNADye.get(1)
                }));
            }

            GameRegistry.addRecipe(new ShapedOreRecipe(LaboratoryMachine.Genepool.get(1), new Object[] {
                "gBg",
                "cCc",
                "aPa",
                'C',
                GeneticsItems.LaboratoryCasing.get(1),
                'B',
                "gearBronze",
                'c',
                circuit,
                'g',
                Blocks.glass_pane,
                'P',
                "gearBronze",
                'a',
                Blocks.glass
            }));
            GameRegistry.addRecipe(new ShapedOreRecipe(LaboratoryMachine.Acclimatiser.get(1), new Object[] {
                "gBg",
                "cCc",
                "aPa",
                'C',
                GeneticsItems.LaboratoryCasing.get(1),
                'B',
                Items.lava_bucket,
                'c',
                circuit,
                'g',
                Blocks.glass_pane,
                'P',
                "gearBronze",
                'a',
                Items.water_bucket
            }));
        }

        for (Object circuit : advCircuit) {
            GameRegistry.addRecipe(new ShapedOreRecipe(GeneticMachine.Isolator.get(1), new Object[] {
                "gBg",
                "cCc",
                "aPa",
                'C',
                GeneticsItems.LaboratoryCasing.get(1),
                'B',
                goldGear,
                'c',
                circuit,
                'g',
                Items.gold_nugget,
                'P',
                "gearBronze",
                'a',
                GeneticsItems.Enzyme.get(1)
            }));
            GameRegistry.addRecipe(new ShapedOreRecipe(GeneticMachine.Polymeriser.get(1), new Object[] {
                "gBg",
                "cCc",
                "gPg",
                'C',
                GeneticsItems.LaboratoryCasing.get(1),
                'B',
                ironGear,
                'c',
                circuit,
                'g',
                Items.gold_nugget,
                'P',
                "gearBronze"
            }));
            GameRegistry.addRecipe(new ShapedOreRecipe(GeneticMachine.Sequencer.get(1), new Object[] {
                "gBg",
                "cCc",
                "aPa",
                'C',
                GeneticsItems.LaboratoryCasing.get(1),
                'B',
                "gearBronze",
                'c',
                circuit,
                'g',
                Items.gold_nugget,
                'P',
                "gearBronze",
                'a',
                GeneticsItems.FluorescentDye.get(1)
            }));
            GameRegistry.addRecipe(new ShapedOreRecipe(GeneticMachine.Inoculator.get(1), new Object[] {
                "gBg",
                "cCc",
                "aPa",
                'C',
                GeneticsItems.LaboratoryCasing.get(1),
                'B',
                diamondGear,
                'c',
                circuit,
                'g',
                Items.gold_nugget,
                'P',
                "gearBronze",
                'a',
                Items.emerald
            }));
        }

        GameRegistry.addRecipe(new ShapedOreRecipe(AdvGeneticMachine.Splicer.get(1), new Object[] {
            "gBg",
            "cCc",
            "aPa",
            'C',
            GeneticsItems.IntegratedCasing.get(1),
            'B',
            diamondGear,
            'c',
            GeneticsItems.IntegratedCPU.get(1),
            'g',
            Items.gold_nugget,
            'P',
            "gearBronze",
            'a',
            Items.blaze_rod
        }));
        GameRegistry.addRecipe(new ShapedOreRecipe(LaboratoryMachine.LabMachine.get(1), new Object[] {
            "igi", "gCg", "igi", 'C', GeneticsItems.LaboratoryCasing.get(1), 'i', "ingotIron", 'g', Blocks.glass_pane
        }));
    }
}
