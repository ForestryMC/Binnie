package binnie.core.genetics;

import binnie.Binnie;
import binnie.core.BinnieCore;
import forestry.api.apiculture.EnumBeeType;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IClassification;
import forestry.api.genetics.ISpeciesRoot;
import net.minecraft.item.ItemStack;

interface IBreedingMessage {
	String getTitle();

	String getBody();

	ItemStack getIcon();

	class MessageSpeciesDiscovered implements IBreedingMessage {
		IAlleleSpecies species;
		ItemStack stack;

		public MessageSpeciesDiscovered(final IAlleleSpecies species) {
			this.species = species;
			ISpeciesRoot root = null;
			for (final ISpeciesRoot sRoot : AlleleManager.alleleRegistry.getSpeciesRoot().values()) {
				if (sRoot.getKaryotype()[0].getAlleleClass().isInstance(species)) {
					root = sRoot;
				}
			}
			if (root != null) {
				this.stack = root.getMemberStack(root.templateAsIndividual(root.getTemplate(species.getUID())), EnumBeeType.DRONE);
			}
		}

		@Override
		public String getTitle() {
			return Binnie.Language.localise(BinnieCore.instance, "gui.breedingmessage.species");
		}

		@Override
		public String getBody() {
			return this.species.getName();
		}

		@Override
		public ItemStack getIcon() {
			return this.stack;
		}
	}

	class BranchDiscovered implements IBreedingMessage {
		IAlleleSpecies species;
		IClassification classification;
		ItemStack stack;

		public BranchDiscovered(final IAlleleSpecies species, final IClassification classification) {
			this.species = species;
			this.classification = classification;
			ISpeciesRoot root = null;
			for (final ISpeciesRoot sRoot : AlleleManager.alleleRegistry.getSpeciesRoot().values()) {
				if (sRoot.getKaryotype()[0].getAlleleClass().isInstance(species)) {
					root = sRoot;
				}
			}
			if (root != null) {
				this.stack = root.getMemberStack(root.templateAsIndividual(root.getTemplate(species.getUID())), EnumBeeType.DRONE);
			}
		}

		@Override
		public String getTitle() {
			return Binnie.Language.localise(BinnieCore.instance, "gui.breedingmessage.branch");
		}

		@Override
		public String getBody() {
			return this.classification.getScientific();
		}

		@Override
		public ItemStack getIcon() {
			return this.stack;
		}
	}

	class EpithetGained implements IBreedingMessage {
		String epithet;
		ItemStack stack;

		public EpithetGained(final String epithet, final ISpeciesRoot root) {
			this.epithet = epithet;
			this.stack = root.getMemberStack(root.templateAsIndividual(root.getDefaultTemplate()), EnumBeeType.DRONE);
		}

		@Override
		public String getTitle() {
			return Binnie.Language.localise(BinnieCore.instance, "gui.breedingmessage.epithet");
		}

		@Override
		public String getBody() {
			return this.epithet;
		}

		@Override
		public ItemStack getIcon() {
			return this.stack;
		}
	}
}
