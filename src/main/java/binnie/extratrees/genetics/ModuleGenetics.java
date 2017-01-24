package binnie.extratrees.genetics;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.IInitializable;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IClassification;

import java.util.ArrayList;
import java.util.List;

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
		AlleleETFruit.init();
		ETTreeDefinition.initTrees();
 		ExtraTreeMutation.init();
	}

	@Override
	public void postInit() {

		if (BinnieCore.isLepidopteryActive()) {
			for (final ButterflySpecies species2 : ButterflySpecies.values()) {
				//AlleleManager.alleleRegistry.registerAllele(species2);
				Binnie.GENETICS.getButterflyRoot().registerTemplate(species2.getTemplate());
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
}