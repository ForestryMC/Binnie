// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.genetics;

import java.util.Locale;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IClassification;
import binnie.core.BinnieCore;
import binnie.Binnie;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.AlleleManager;
import java.util.ArrayList;
import java.util.List;
import binnie.core.IInitializable;

public class ModuleGenetics implements IInitializable
{
	String[] branches;
	List<List<String>> classifications;

	public ModuleGenetics() {
		this.branches = new String[] { "Malus Maleae Amygdaloideae Rosaceae", "Musa   Musaceae Zingiberales Commelinids Angiosperms", "Sorbus Maleae", "Tsuga   Pinaceae", "Fraxinus Oleeae  Oleaceae Lamiales Asterids Angiospems" };
		this.classifications = new ArrayList<List<String>>();
	}

	@Override
	public void preInit() {
		for (final ExtraTreeFruitGene fruit : ExtraTreeFruitGene.values()) {
			AlleleManager.alleleRegistry.registerAllele(fruit);
		}
		for (final ExtraTreeSpecies species : ExtraTreeSpecies.values()) {
			AlleleManager.alleleRegistry.registerAllele(species);
		}
	}

	@Override
	public void init() {
	}

	@Override
	public void postInit() {
		ExtraTreeSpecies.init();
		ExtraTreeFruitGene.init();
		for (final ExtraTreeSpecies species : ExtraTreeSpecies.values()) {
			Binnie.Genetics.getTreeRoot().registerTemplate(species.getTemplate());
		}
		ExtraTreeMutation.init();
		if (BinnieCore.isLepidopteryActive()) {
			for (final ButterflySpecies species2 : ButterflySpecies.values()) {
				AlleleManager.alleleRegistry.registerAllele(species2);
				Binnie.Genetics.getButterflyRoot().registerTemplate(species2.getTemplate());
				final String scientific = species2.branchName.substring(0, 1).toUpperCase() + species2.branchName.substring(1).toLowerCase();
				final String uid = "trees." + species2.branchName.toLowerCase();
				IClassification branch = AlleleManager.alleleRegistry.getClassification("genus." + uid);
				if (branch == null) {
					branch = AlleleManager.alleleRegistry.createAndRegisterClassification(IClassification.EnumClassLevel.GENUS, uid, scientific);
				}
				(species2.branch = branch).addMemberSpecies(species2);
			}
		}
	}

	private void generateBranches() {
		for (final String hierarchy : this.branches) {
			final List<String> set = new ArrayList<String>();
			for (final String string : hierarchy.split(" ", 0)) {
				set.add(string.toLowerCase());
			}
			this.classifications.add(set);
		}
		for (final ExtraTreeSpecies species : ExtraTreeSpecies.values()) {
			final IClassification branch = this.getOrCreateClassification(IClassification.EnumClassLevel.GENUS, species.branchName);
			branch.addMemberSpecies(species);
			species.branch = branch;
			IClassification clss = branch;
			int currentLevel = IClassification.EnumClassLevel.GENUS.ordinal();
			while (clss.getParent() == null) {
				for (final List<String> set2 : this.classifications) {
					if (set2.contains(clss.getScientific().toLowerCase())) {
						String nextLevel = "";
						int index = set2.indexOf(clss.getScientific().toLowerCase()) + 1;
						while (nextLevel.length() == 0) {
							try {
								nextLevel = set2.get(index++);
							} catch (IndexOutOfBoundsException ex) {
								throw new RuntimeException("Reached end point at " + set2.get(index - 2));
							}
							--currentLevel;
						}
						final IClassification parent = this.getOrCreateClassification(IClassification.EnumClassLevel.values()[currentLevel], nextLevel);
						parent.addMemberGroup(clss);
						System.out.println("Went from " + clss.getScientific() + " to " + parent.getScientific());
						clss = parent;
						break;
					}
				}
			}
		}
	}

	private IClassification getOrCreateClassification(final IClassification.EnumClassLevel level, String name) {
		if (level == IClassification.EnumClassLevel.GENUS) {
			name = "trees." + name;
		}
		final String uid = level.name().toLowerCase(Locale.ENGLISH) + "." + name.toLowerCase();
		if (AlleleManager.alleleRegistry.getClassification(uid) != null) {
			return AlleleManager.alleleRegistry.getClassification(uid);
		}
		return AlleleManager.alleleRegistry.createAndRegisterClassification(level, name.toLowerCase(), name);
	}
}
