package binnie.core.genetics;

import binnie.Binnie;
import binnie.core.BinnieCore;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IClassification;
import forestry.api.genetics.ISpeciesRoot;
import net.minecraft.item.ItemStack;

// TODO unused interface?
interface IBreedingMessage {
	String getTitle();

	String getBody();

	ItemStack getIcon();

	class MessageSpeciesDiscovered implements IBreedingMessage {
		IAlleleSpecies species;
		ItemStack stack;

		public MessageSpeciesDiscovered(IAlleleSpecies species) {
			this.species = species;
			ISpeciesRoot root = null;
			for (ISpeciesRoot sRoot : AlleleManager.alleleRegistry.getSpeciesRoot().values()) {
				if (sRoot.getKaryotype()[0].getAlleleClass().isInstance(species)) {
					root = sRoot;
				}
			}
			if (root != null) {
				stack = root.getMemberStack(root.templateAsIndividual(root.getTemplate(species.getUID())), 0);
			}
		}

		@Override
		public String getTitle() {
			return Binnie.I18N.localise(BinnieCore.instance, "gui.breedingmessage.species");
		}

		@Override
		public String getBody() {
			return species.getName();
		}

		@Override
		public ItemStack getIcon() {
			return stack;
		}
	}

	class BranchDiscovered implements IBreedingMessage {
		IAlleleSpecies species;
		IClassification classification;
		ItemStack stack;

		public BranchDiscovered(IAlleleSpecies species, IClassification classification) {
			this.species = species;
			this.classification = classification;
			ISpeciesRoot root = null;
			for (ISpeciesRoot sRoot : AlleleManager.alleleRegistry.getSpeciesRoot().values()) {
				if (sRoot.getKaryotype()[0].getAlleleClass().isInstance(species)) {
					root = sRoot;
				}
			}
			if (root != null) {
				stack = root.getMemberStack(root.templateAsIndividual(root.getTemplate(species.getUID())), 0);
			}
		}

		@Override
		public String getTitle() {
			return Binnie.I18N.localise(BinnieCore.instance, "gui.breedingmessage.branch");
		}

		@Override
		public String getBody() {
			return classification.getScientific();
		}

		@Override
		public ItemStack getIcon() {
			return stack;
		}
	}

	class EpithetGained implements IBreedingMessage {
		String epithet;
		ItemStack stack;

		public EpithetGained(String epithet, ISpeciesRoot root) {
			this.epithet = epithet;
			stack = root.getMemberStack(root.templateAsIndividual(root.getDefaultTemplate()), 0);
		}

		@Override
		public String getTitle() {
			return Binnie.I18N.localise(BinnieCore.instance, "gui.breedingmessage.epithet");
		}

		@Override
		public String getBody() {
			return epithet;
		}

		@Override
		public ItemStack getIcon() {
			return stack;
		}
	}
}
