package binnie.core.genetics;

import binnie.*;
import binnie.core.*;
import binnie.core.util.*;
import binnie.extratrees.*;
import binnie.extratrees.machines.*;
import com.mojang.authlib.*;
import forestry.api.arboriculture.*;
import forestry.api.genetics.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraftforge.common.*;
import net.minecraftforge.common.util.*;

import java.util.*;

public class TreeBreedingSystem extends BreedingSystem {
	public UniqueItemStackSet allFruits = new UniqueItemStackSet();
	public UniqueItemStackSet allWoods = new UniqueItemStackSet();
	private UniqueItemStackSet discoveredFruits = new UniqueItemStackSet();
	private UniqueItemStackSet discoveredWoods = new UniqueItemStackSet();
	public UniqueItemStackSet discoveredPlanks = new UniqueItemStackSet();

	public TreeBreedingSystem() {
		iconUndiscovered = Binnie.Resource.getItemIcon(ExtraTrees.instance, "icon/undiscoveredTree");
		iconDiscovered = Binnie.Resource.getItemIcon(ExtraTrees.instance, "icon/discoveredTree");
	}

	@Override
	public float getChance(IMutation mutation, EntityPlayer player, IAllele species1, IAllele species2) {
		ISpeciesRoot speciesRoot = getSpeciesRoot();
		ITreeGenome genome0 = (ITreeGenome) speciesRoot.templateAsGenome(speciesRoot.getTemplate(species1.getUID()));
		ITreeGenome genome2 = (ITreeGenome) speciesRoot.templateAsGenome(speciesRoot.getTemplate(species2.getUID()));
		return ((ITreeMutation) mutation).getChance(player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ, (IAlleleTreeSpecies) species1, (IAlleleTreeSpecies) species2, genome0, genome2);
	}

	@Override
	public ISpeciesRoot getSpeciesRoot() {
		return Binnie.Genetics.getTreeRoot();
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
	public String getAlleleName(IChromosomeType chromosome, IAllele allele) {
		if (chromosome == EnumTreeChromosome.GIRTH) {
			return ((IAlleleInteger) allele).getValue() + "x" + ((IAlleleInteger) allele).getValue();
		}

		if (chromosome == EnumTreeChromosome.PLANT) {
			EnumSet<EnumPlantType> types = ((IAllelePlantType) allele).getPlantTypes();
			return types.isEmpty() ?
					Binnie.Language.localise(BinnieCore.instance, "allele.none") :
					types.iterator().next().toString();
		}

		if (chromosome == EnumTreeChromosome.FRUITS && allele.getUID().contains(".")) {
			IFruitProvider provider = ((IAlleleFruit) allele).getProvider();
			return (provider.getProducts().length == 0) ?
					Binnie.Language.localise(BinnieCore.instance, "allele.none") :
					provider.getProducts()[0].getDisplayName();
		}

		if (chromosome == EnumTreeChromosome.GROWTH) {
			if (allele.getUID().contains("Tropical")) {
				return Binnie.Language.localise(BinnieCore.instance, "allele.growth.tropical");
			}
			if (allele.getUID().contains("Lightlevel")) {
				return Binnie.Language.localise(BinnieCore.instance, "allele.growth.lightlevel");
			}
		}
		return super.getAlleleName(chromosome, allele);
	}

	@Override
	public void onSyncBreedingTracker(IBreedingTracker tracker) {
		discoveredFruits.clear();
		discoveredWoods.clear();
		for (IAlleleSpecies species : getDiscoveredSpecies(tracker)) {
			IAlleleTreeSpecies tSpecies = (IAlleleTreeSpecies) species;
			ISpeciesRoot speciesRoot = getSpeciesRoot();
			ITreeGenome genome = (ITreeGenome) speciesRoot.templateAsGenome(speciesRoot.getTemplate(tSpecies.getUID()));

			FakeWorld world = FakeWorld.instance;
			genome.getPrimary().getGenerator().setLogBlock(genome, world, 0, 0, 0, ForgeDirection.UP);
			ItemStack wood = world.getWooLog();
			if (wood != null) {
				discoveredWoods.add(wood);
			}

			// TODO ???
			// for (ItemStack wood :
			// tSpecies.getRoot().templateAsIndividual(getSpeciesRoot().getTemplate(tSpecies.getUID())).getProduceList())
			// {
			// this.discoveredWoods.add(wood);
			// }
			discoveredFruits.addAll(Arrays.asList(genome.getFruitProvider().getProducts()));
			for (ItemStack wood2 : this.discoveredWoods) {
			}
		}
	}

	@Override
	public void calculateArrays() {
		super.calculateArrays();
		for (IAlleleSpecies species : this.allActiveSpecies) {
			IAlleleTreeSpecies tSpecies = (IAlleleTreeSpecies) species;
			ITreeGenome genome = (ITreeGenome) this.getSpeciesRoot().templateAsGenome(this.getSpeciesRoot().getTemplate(tSpecies.getUID()));

			FakeWorld world = FakeWorld.instance;
			genome.getPrimary().getGenerator().setLogBlock(genome, world, 0, 0, 0, ForgeDirection.UP);
			ItemStack wood = world.getWooLog();
			if (wood != null) {
				this.allWoods.add(wood);
			}

			// TODO ???
			// for (ItemStack wood :
			// tSpecies.getRoot().templateAsIndividual(getSpeciesRoot().getTemplate(tSpecies.getUID())).getProduceList())
			// {
			// this.allWoods.add(wood);
			// }
			allFruits.addAll(Arrays.asList(genome.getFruitProvider().getProducts()));
		}
	}

	public Collection<IAlleleSpecies> getTreesThatBearFruit(ItemStack fruit, boolean nei, World world, GameProfile player) {
		Collection<IAlleleSpecies> set = nei ? getAllSpecies() : getDiscoveredSpecies(world, player);
		List<IAlleleSpecies> found = new ArrayList<>();
		for (IAlleleSpecies species : set) {
			IAlleleTreeSpecies tSpecies = (IAlleleTreeSpecies) species;
			ITreeGenome genome = (ITreeGenome) this.getSpeciesRoot().templateAsGenome(this.getSpeciesRoot().getTemplate(tSpecies.getUID()));
			for (ItemStack fruit2 : genome.getFruitProvider().getProducts()) {
				if (fruit2.isItemEqual(fruit)) {
					found.add(species);
				}
			}
		}
		return found;
	}

	public Collection<IAlleleSpecies> getTreesThatCanBearFruit(ItemStack fruit, boolean nei, World world, GameProfile player) {
		Collection<IAlleleSpecies> set = nei ? getAllSpecies() : getDiscoveredSpecies(world, player);
		List<IAlleleSpecies> found = new ArrayList<>();
		Set<IFruitFamily> providers = new HashSet<>();
		for (IAlleleSpecies species : set) {
			IAlleleTreeSpecies tSpecies = (IAlleleTreeSpecies) species;
			ITreeGenome genome = (ITreeGenome) getSpeciesRoot().templateAsGenome(getSpeciesRoot().getTemplate(tSpecies.getUID()));
			for (ItemStack fruit2 : genome.getFruitProvider().getProducts()) {
				if (fruit2.isItemEqual(fruit)) {
					providers.add(genome.getFruitProvider().getFamily());
				}
			}
		}
		for (IAlleleSpecies species : set) {
			IAlleleTreeSpecies tSpecies = (IAlleleTreeSpecies) species;
			for (IFruitFamily family : providers) {
				if (tSpecies.getSuitableFruit().contains(family)) {
					found.add(species);
					break;
				}
			}
		}
		return found;
	}

	public Collection<IAlleleSpecies> getTreesThatHaveWood(ItemStack fruit, boolean nei, World world, GameProfile player) {
		Collection<IAlleleSpecies> set = nei ? getAllSpecies() : getDiscoveredSpecies(world, player);
		List<IAlleleSpecies> found = new ArrayList<>();
		for (IAlleleSpecies species : set) {
			IAlleleTreeSpecies tSpecies = (IAlleleTreeSpecies) species;
			ITreeGenome genome = TreeManager.treeRoot.templateAsGenome(TreeManager.treeRoot.getTemplate(tSpecies.getUID()));
			// for (ItemStack fruit2 :
			// tSpecies.getRoot().getMember(fruit).getProduceList()){
			tSpecies.getGenerator().setLogBlock(genome, FakeWorld.instance, 0, 0, 0, ForgeDirection.UP);
			ItemStack fruit2 = FakeWorld.instance.getWooLog();
			if (fruit2.isItemEqual(fruit)) {
				found.add(species);
				// }
			}
		}
		return found;
	}

	public Collection<IAlleleSpecies> getTreesThatMakePlanks(ItemStack fruit, boolean nei, World world, GameProfile player) {
		if (fruit == null) {
			return new ArrayList<>();
		}
		Collection<IAlleleSpecies> set = nei ? getAllSpecies() : getDiscoveredSpecies(world, player);
		List<IAlleleSpecies> found = new ArrayList<IAlleleSpecies>();
		for (IAlleleSpecies species : set) {
			IAlleleTreeSpecies tSpecies = (IAlleleTreeSpecies) species;
			ITreeGenome genome = TreeManager.treeRoot.templateAsGenome(TreeManager.treeRoot.getTemplate(tSpecies.getUID()));
			tSpecies.getGenerator().setLogBlock(genome, FakeWorld.instance, 0, 0, 0, ForgeDirection.UP);
			ItemStack fruit2 = FakeWorld.instance.getWooLog();
			// for (ItemStack fruit2 :
			// tSpecies.getRoot().getMember(fruit).getProduceList()) {
			if (Lumbermill.getPlankProduct(fruit2) != null && fruit.isItemEqual(Lumbermill.getPlankProduct(fruit2))) {
				found.add(species);
			}
			// }
		}
		return found;
	}

	@Override
	public boolean isDNAManipulable(ItemStack member) {
		return ((ITreeRoot) getSpeciesRoot()).getType(member) == EnumGermlingType.POLLEN;
	}

	@Override
	public IIndividual getConversion(ItemStack stack) {
		if (stack == null) {
			return null;
		}
		for (Map.Entry<ItemStack, IIndividual> entry : AlleleManager.ersatzSaplings.entrySet()) {
			if (ItemStack.areItemStacksEqual(stack, entry.getKey())) {
				return entry.getValue();
			}
		}
		return null;
	}

	@Override
	public int[] getActiveTypes() {
		return new int[]{EnumGermlingType.SAPLING.ordinal(), EnumGermlingType.POLLEN.ordinal()};
	}

	@Override
	public void addExtraAlleles(IChromosomeType chromosome, TreeSet<IAllele> alleles) {
		switch ((EnumTreeChromosome) chromosome) {
			case FERTILITY:
				for (ForestryAllele.Saplings a : ForestryAllele.Saplings.values()) {
					alleles.add(a.getAllele());
				}
				break;

			case GIRTH:
				for (ForestryAllele.Int a2 : ForestryAllele.Int.values()) {
					alleles.add(a2.getAllele());
				}
				break;

			case HEIGHT:
				for (ForestryAllele.TreeHeight a3 : ForestryAllele.TreeHeight.values()) {
					alleles.add(a3.getAllele());
				}
				break;

			case MATURATION:
				for (ForestryAllele.Maturation a4 : ForestryAllele.Maturation.values()) {
					alleles.add(a4.getAllele());
				}
				break;

			case SAPPINESS:
				for (ForestryAllele.Sappiness a5 : ForestryAllele.Sappiness.values()) {
					alleles.add(a5.getAllele());
				}
				break;

			case TERRITORY:
				for (ForestryAllele.Territory a6 : ForestryAllele.Territory.values()) {
					alleles.add(a6.getAllele());
				}
				break;

			case YIELD:
				for (ForestryAllele.Yield a7 : ForestryAllele.Yield.values()) {
					alleles.add(a7.getAllele());
				}
				break;

			case FIREPROOF:
				for (ForestryAllele.Bool a8 : ForestryAllele.Bool.values()) {
					alleles.add(a8.getAllele());
				}
				break;
		}
	}
}
