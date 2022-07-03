package binnie.extratrees.genetics;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.IInitializable;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IClassification;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ModuleGenetics implements IInitializable {
    protected String[] branches;
    protected List<List<String>> classifications;

    public ModuleGenetics() {
        branches = new String[] {
            "Malus Maleae Amygdaloideae Rosaceae",
            "Musa   Musaceae Zingiberales Commelinids Angiosperms",
            "Sorbus Maleae",
            "Tsuga   Pinaceae",
            "Fraxinus Oleeae  Oleaceae Lamiales Asterids Angiospems"
        };
        classifications = new ArrayList<>();
    }

    @Override
    public void preInit() {
        for (ExtraTreeFruitGene fruit : ExtraTreeFruitGene.values()) {
            AlleleManager.alleleRegistry.registerAllele(fruit);
        }
        for (ExtraTreeSpecies species : ExtraTreeSpecies.values()) {
            AlleleManager.alleleRegistry.registerAllele(species);
        }
    }

    @Override
    public void init() {
        // ignored
    }

    @Override
    public void postInit() {
        ExtraTreeSpecies.init();
        ExtraTreeFruitGene.init();
        for (ExtraTreeSpecies species : ExtraTreeSpecies.values()) {
            Binnie.Genetics.getTreeRoot().registerTemplate(species.getTemplate());
        }

        ExtraTreeMutation.init();
        if (!BinnieCore.isLepidopteryActive()) {
            return;
        }

        for (ButterflySpecies species2 : ButterflySpecies.values()) {
            AlleleManager.alleleRegistry.registerAllele(species2);
            Binnie.Genetics.getButterflyRoot().registerTemplate(species2.getTemplate());
            String scientific = species2.branchName.substring(0, 1).toUpperCase()
                    + species2.branchName.substring(1).toLowerCase();
            String uid = "trees." + species2.branchName.toLowerCase();
            IClassification branch = AlleleManager.alleleRegistry.getClassification("genus." + uid);
            if (branch == null) {
                branch = AlleleManager.alleleRegistry.createAndRegisterClassification(
                        IClassification.EnumClassLevel.GENUS, uid, scientific);
            }
            (species2.branch = branch).addMemberSpecies(species2);
        }
    }

    private void generateBranches() {
        for (String hierarchy : branches) {
            List<String> set = new ArrayList<>();
            for (String string : hierarchy.split(" ", 0)) {
                set.add(string.toLowerCase());
            }
            classifications.add(set);
        }

        for (ExtraTreeSpecies species : ExtraTreeSpecies.values()) {
            IClassification branch =
                    getOrCreateClassification(IClassification.EnumClassLevel.GENUS, species.branchName);
            branch.addMemberSpecies(species);
            species.branch = branch;
            IClassification clss = branch;
            int currentLevel = IClassification.EnumClassLevel.GENUS.ordinal();

            while (clss.getParent() == null) {
                for (List<String> set2 : classifications) {
                    if (set2.contains(clss.getScientific().toLowerCase())) {
                        String nextLevel = "";
                        int index = set2.indexOf(clss.getScientific().toLowerCase()) + 1;
                        while (nextLevel.length() == 0) {
                            try {
                                nextLevel = set2.get(index++);
                            } catch (IndexOutOfBoundsException ex) {
                                throw new RuntimeException("Reached end point at " + set2.get(index - 2));
                            }
                            currentLevel--;
                        }

                        IClassification parent = getOrCreateClassification(
                                IClassification.EnumClassLevel.values()[currentLevel], nextLevel);
                        parent.addMemberGroup(clss);
                        System.out.println("Went from " + clss.getScientific() + " to " + parent.getScientific());
                        clss = parent;
                        break;
                    }
                }
            }
        }
    }

    private IClassification getOrCreateClassification(IClassification.EnumClassLevel level, String name) {
        if (level == IClassification.EnumClassLevel.GENUS) {
            name = "trees." + name;
        }

        String uid = level.name().toLowerCase(Locale.ENGLISH) + "." + name.toLowerCase();
        if (AlleleManager.alleleRegistry.getClassification(uid) != null) {
            return AlleleManager.alleleRegistry.getClassification(uid);
        }
        return AlleleManager.alleleRegistry.createAndRegisterClassification(level, name.toLowerCase(), name);
    }
}
