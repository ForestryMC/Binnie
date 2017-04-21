// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.genetics;

import forestry.api.genetics.IClassification;
import binnie.core.BinnieCore;
import binnie.Binnie;
import forestry.api.genetics.ISpeciesRoot;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAlleleSpecies;
import net.minecraft.item.ItemStack;

interface IBreedingMessage
{
	String getTitle();

	String getBody();

	ItemStack getIcon();

	public static class MessageSpeciesDiscovered implements IBreedingMessage
	{
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
				this.stack = root.getMemberStack(root.templateAsIndividual(root.getTemplate(species.getUID())), 0);
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

	public static class BranchDiscovered implements IBreedingMessage
	{
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
				this.stack = root.getMemberStack(root.templateAsIndividual(root.getTemplate(species.getUID())), 0);
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

	public static class EpithetGained implements IBreedingMessage
	{
		String epithet;
		ItemStack stack;

		public EpithetGained(String epithet, ISpeciesRoot root) {
			this.epithet = epithet;
			this.stack = root.getMemberStack(root.templateAsIndividual(root.getDefaultTemplate()), 0);
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
