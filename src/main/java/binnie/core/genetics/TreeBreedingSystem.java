package binnie.core.genetics;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.util.UniqueItemStackSet;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.machines.lumbermill.LumbermillRecipes;
import com.mojang.authlib.GameProfile;
import forestry.api.arboriculture.EnumGermlingType;
import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.arboriculture.IAlleleFruit;
import forestry.api.arboriculture.IAlleleTreeSpecies;
import forestry.api.arboriculture.IArboristTracker;
import forestry.api.arboriculture.IFruitProvider;
import forestry.api.arboriculture.ITreeGenome;
import forestry.api.arboriculture.ITreeMutation;
import forestry.api.arboriculture.TreeManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleInteger;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IBreedingTracker;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IFruitFamily;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.IMutation;
import forestry.api.genetics.ISpeciesRoot;
import forestry.api.genetics.ISpeciesType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class TreeBreedingSystem extends BreedingSystem {
	public UniqueItemStackSet allFruits;
	public UniqueItemStackSet allWoods;
	private UniqueItemStackSet discoveredFruits;
	private UniqueItemStackSet discoveredWoods;
	public UniqueItemStackSet discoveredPlanks;

	public TreeBreedingSystem() {
		this.allFruits = new UniqueItemStackSet();
		this.allWoods = new UniqueItemStackSet();
		this.discoveredFruits = new UniqueItemStackSet();
		this.discoveredWoods = new UniqueItemStackSet();
		this.discoveredPlanks = new UniqueItemStackSet();
		this.iconUndiscovered = Binnie.RESOURCE.getItemSprite(ExtraTrees.instance, "icon/undiscovered_tree");
		this.iconDiscovered = Binnie.RESOURCE.getItemSprite(ExtraTrees.instance, "icon/discovered_tree");
	}

	@Override
	public float getChance(final IMutation mutation, final EntityPlayer player, final IAlleleSpecies species1, final IAlleleSpecies species2) {
		ISpeciesRoot speciesRoot = this.getSpeciesRoot();
		final ITreeGenome genome0 = (ITreeGenome) speciesRoot.templateAsGenome(speciesRoot.getTemplate(species1));
		final ITreeGenome genome2 = (ITreeGenome) speciesRoot.templateAsGenome(speciesRoot.getTemplate(species2));
		return ((ITreeMutation) mutation).getChance(player.world, player.getPosition(), (IAlleleTreeSpecies) species1, (IAlleleTreeSpecies) species2, genome0, genome2);
	}

	@Override
	public ISpeciesRoot getSpeciesRoot() {
		return Binnie.GENETICS.getTreeRoot();
	}

	@Override
	public int getColour() {
		return 53006;
	}

	@Override
	public Class<? extends IBreedingTracker> getTrackerClass() {
		return IArboristTracker.class;
	}

	@Override
	public String getAlleleName(final IChromosomeType chromosome, final IAllele allele) {
		if (chromosome == EnumTreeChromosome.GIRTH) {
			return ((IAlleleInteger) allele).getValue() + "x" + ((IAlleleInteger) allele).getValue();
		}
		if (chromosome == EnumTreeChromosome.FRUITS && allele.getUID().contains(".")) {
			final IFruitProvider provider = ((IAlleleFruit) allele).getProvider();
			return (provider.getProducts().size() == 0) ? Binnie.LANGUAGE.localise(BinnieCore.getInstance(), "allele.none") : provider.getProducts().keySet().iterator().next().getDisplayName();
		}
		return super.getAlleleName(chromosome, allele);
	}

	@Override
	public void onSyncBreedingTracker(final IBreedingTracker tracker) {
		this.discoveredFruits.clear();
		this.discoveredWoods.clear();
		for (final IAlleleSpecies species : this.getDiscoveredSpecies(tracker)) {
			final IAlleleTreeSpecies tSpecies = (IAlleleTreeSpecies) species;
			final ITreeGenome genome = (ITreeGenome) this.getSpeciesRoot().templateAsGenome(this.getSpeciesRoot().getTemplate(tSpecies));

			IAlleleTreeSpecies treeSpecies = genome.getPrimary();
			final ItemStack wood = treeSpecies.getWoodProvider().getWoodStack();
			if (!wood.isEmpty()) {
				this.discoveredWoods.add(wood);
			}

			// for (final ItemStack wood :
			// tSpecies.getRoot().templateAsIndividual(getSpeciesRoot().getTemplate(tSpecies.getUID())).getProduceList())
			// {
			// this.discoveredWoods.add(wood);
			// }
			for (final ItemStack fruit : genome.getFruitProvider().getProducts().keySet()) {
				this.discoveredFruits.add(fruit);
			}
			for (ItemStack wood2 : this.discoveredWoods) {
			}
		}
	}

	@Override
	public final void calculateArrays() {
		super.calculateArrays();
		for (final IAlleleSpecies species : this.allActiveSpecies) {
			final IAlleleTreeSpecies tSpecies = (IAlleleTreeSpecies) species;
			final ITreeGenome genome = (ITreeGenome) this.getSpeciesRoot().templateAsGenome(this.getSpeciesRoot().getTemplate(tSpecies));

			IAlleleTreeSpecies treeSpecies = genome.getPrimary();
			final ItemStack wood = treeSpecies.getWoodProvider().getWoodStack();
			if (!wood.isEmpty()) {
				this.allWoods.add(wood);
			}

			// for (final ItemStack wood :
			// tSpecies.getRoot().templateAsIndividual(getSpeciesRoot().getTemplate(tSpecies.getUID())).getProduceList())
			// {
			// this.allWoods.add(wood);
			// }
			for (final ItemStack fruit : genome.getFruitProvider().getProducts().keySet()) {
				this.allFruits.add(fruit);
			}
		}
	}

	public Collection<IAlleleSpecies> getTreesThatBearFruit(final ItemStack fruit, final boolean nei, final World world, final GameProfile player) {
		final Collection<IAlleleSpecies> set = nei ? this.getAllSpecies() : this.getDiscoveredSpecies(world, player);
		final List<IAlleleSpecies> found = new ArrayList<>();
		for (final IAlleleSpecies species : set) {
			final IAlleleTreeSpecies tSpecies = (IAlleleTreeSpecies) species;
			final ITreeGenome genome = (ITreeGenome) this.getSpeciesRoot().templateAsGenome(this.getSpeciesRoot().getTemplate(tSpecies));
			for (final ItemStack fruit2 : genome.getFruitProvider().getProducts().keySet()) {
				if (fruit2.isItemEqual(fruit)) {
					found.add(species);
				}
			}
		}
		return found;
	}

	public Collection<IAlleleSpecies> getTreesThatCanBearFruit(final ItemStack fruit, final boolean nei, final World world, final GameProfile player) {
		final Collection<IAlleleSpecies> set = nei ? this.getAllSpecies() : this.getDiscoveredSpecies(world, player);
		final List<IAlleleSpecies> found = new ArrayList<>();
		final Set<IFruitFamily> providers = new HashSet<>();
		for (final IAlleleSpecies species : set) {
			final IAlleleTreeSpecies tSpecies = (IAlleleTreeSpecies) species;
			final ITreeGenome genome = (ITreeGenome) this.getSpeciesRoot().templateAsGenome(this.getSpeciesRoot().getTemplate(tSpecies));
			for (final ItemStack fruit2 : genome.getFruitProvider().getProducts().keySet()) {
				if (fruit2.isItemEqual(fruit)) {
					providers.add(genome.getFruitProvider().getFamily());
				}
			}
		}
		for (final IAlleleSpecies species : set) {
			final IAlleleTreeSpecies tSpecies = (IAlleleTreeSpecies) species;
			for (final IFruitFamily family : providers) {
				if (tSpecies.getSuitableFruit().contains(family)) {
					found.add(species);
					break;
				}
			}
		}
		return found;
	}

	public Collection<IAlleleSpecies> getTreesThatHaveWood(final ItemStack wood, final boolean nei, final World world, final GameProfile player) {
		final Collection<IAlleleSpecies> set = nei ? this.getAllSpecies() : this.getDiscoveredSpecies(world, player);
		final List<IAlleleSpecies> found = new ArrayList<>();
		for (final IAlleleSpecies species : set) {
			IAlleleTreeSpecies tSpecies = (IAlleleTreeSpecies) species;
			ITreeGenome genome = TreeManager.treeRoot.templateAsGenome(TreeManager.treeRoot.getTemplate(tSpecies));
			IAlleleTreeSpecies treeSpecies = genome.getPrimary();
			final ItemStack woodStack = treeSpecies.getWoodProvider().getWoodStack();

			if (woodStack.isItemEqual(wood)) {
				found.add(species);
			}
		}
		return found;
	}

	public Collection<IAlleleSpecies> getTreesThatMakePlanks(final ItemStack fruit, final boolean nei, final World world, final GameProfile player) {
		if (fruit == null) {
			return new ArrayList<>();
		}
		final Collection<IAlleleSpecies> set = nei ? this.getAllSpecies() : this.getDiscoveredSpecies(world, player);
		final List<IAlleleSpecies> found = new ArrayList<>();
		for (final IAlleleSpecies species : set) {
			final IAlleleTreeSpecies tSpecies = (IAlleleTreeSpecies) species;
			ITreeGenome genome = TreeManager.treeRoot.templateAsGenome(TreeManager.treeRoot.getTemplate(tSpecies));
			IAlleleTreeSpecies treeSpecies = genome.getPrimary();
			final ItemStack woodStack = treeSpecies.getWoodProvider().getWoodStack();
			ItemStack plankProduct = LumbermillRecipes.getPlankProduct(woodStack);
			if (!plankProduct.isEmpty() && fruit.isItemEqual(plankProduct)) {
				found.add(species);
			}
		}
		return found;
	}

	@Override
	public boolean isDNAManipulable(final ItemStack member) {
		ISpeciesType type = this.getSpeciesRoot().getType(member);
		return isDNAManipulable(type);
	}

	@Override
	public boolean isDNAManipulable(@Nullable ISpeciesType type) {
		return type == EnumGermlingType.POLLEN;
	}

	@Override
	@Nullable
	public IIndividual getConversion(final ItemStack stack) {
//		for (final Map.Entry<ItemStack, IIndividual> entry : TreeManager.treeRoot.getIndividualTemplates()) {
//			if (ItemStack.areItemStacksEqual(stack, entry.getKey())) {
//				return entry.getValue();
//			}
//		}
		return TreeManager.treeRoot.getMember(stack);
	}

	@Override
	public ISpeciesType[] getActiveTypes() {
		return new ISpeciesType[]{EnumGermlingType.SAPLING, EnumGermlingType.POLLEN};
	}

	@Override
	public void addExtraAlleles(final IChromosomeType chromosome, final TreeSet<IAllele> alleles) {
		switch ((EnumTreeChromosome) chromosome) {
			case FERTILITY: {
				for (final ForestryAllele.Saplings a : ForestryAllele.Saplings.values()) {
					alleles.add(a.getAllele());
				}
				break;
			}
			case GIRTH: {
				for (final ForestryAllele.Int a2 : ForestryAllele.Int.values()) {
					alleles.add(a2.getAllele());
				}
				break;
			}
			case HEIGHT: {
				for (final ForestryAllele.TreeHeight a3 : ForestryAllele.TreeHeight.values()) {
					alleles.add(a3.getAllele());
				}
				break;
			}
			case MATURATION: {
				for (final ForestryAllele.Maturation a4 : ForestryAllele.Maturation.values()) {
					alleles.add(a4.getAllele());
				}
				break;
			}
			case SAPPINESS: {
				for (final ForestryAllele.Sappiness a5 : ForestryAllele.Sappiness.values()) {
					alleles.add(a5.getAllele());
				}
				break;
			}
			case YIELD: {
				for (final ForestryAllele.Yield a7 : ForestryAllele.Yield.values()) {
					alleles.add(a7.getAllele());
				}
				break;
			}
			case FIREPROOF: {
				for (final ForestryAllele.Bool a8 : ForestryAllele.Bool.values()) {
					alleles.add(a8.getAllele());
				}
				break;
			}
		}
	}
}
