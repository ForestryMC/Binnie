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
	String[] branches;
	List<List<String>> classifications;

	public ModuleGenetics() {
		this.branches = new String[]{"Malus Maleae Amygdaloideae Rosaceae", "Musa   Musaceae Zingiberales Commelinids Angiosperms", "Sorbus Maleae", "Tsuga   Pinaceae", "Fraxinus Oleeae  Oleaceae Lamiales Asterids Angiospems"};
		this.classifications = new ArrayList<>();
	}

	@Override
	public void preInit() {

	}


	@Override
	public void init() {
		ExtraTreeSpecies.init();
		ExtraTreeFruitGene.init();
		for (final ExtraTreeSpecies species : ExtraTreeSpecies.values()) {
			Binnie.Genetics.getTreeRoot().registerTemplate(species.getTemplate());
		}
		ExtraTreeMutation.init();
	}

	@Override
	public void postInit() {

		if (BinnieCore.isLepidopteryActive()) {
			for (final ButterflySpecies species2 : ButterflySpecies.values()) {
				//AlleleManager.alleleRegistry.registerAllele(species2);
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
			final List<String> set = new ArrayList<>();
			for (final String string : hierarchy.split(" ", 0)) {
				set.add(string.toLowerCase());
			}
			this.classifications.add(set);
		}
		for (final ExtraTreeSpecies species : ExtraTreeSpecies.values()) {
			final IClassification branch = this.getOrCreateClassification(IClassification.EnumClassLevel.GENUS, species.getBranchName());
			branch.addMemberSpecies(species);
			species.setBranch(branch);
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
