// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.genetics;

import java.util.TreeSet;
import java.util.Map;

import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IIndividual;
import forestry.api.arboriculture.EnumGermlingType;
import forestry.api.arboriculture.ITreeRoot;
import forestry.api.arboriculture.TreeManager;
import binnie.extratrees.machines.Lumbermill;

import java.util.Set;

import forestry.api.genetics.IFruitFamily;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

import com.mojang.authlib.GameProfile;

import net.minecraft.world.World;

import net.minecraft.item.ItemStack;

import forestry.api.arboriculture.ITreeGenome;
import forestry.api.arboriculture.IAlleleTreeSpecies;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.arboriculture.IFruitProvider;

import java.util.EnumSet;

import forestry.api.arboriculture.IAlleleFruit;

import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.util.ForgeDirection;

import binnie.core.BinnieCore;
import forestry.api.genetics.IAllelePlantType;
import forestry.api.genetics.IAlleleInteger;
import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.genetics.IChromosomeType;
import forestry.api.arboriculture.IArboristTracker;
import forestry.api.genetics.IBreedingTracker;
import forestry.api.genetics.ISpeciesRoot;
import forestry.api.arboriculture.ITreeMutation;
import forestry.api.genetics.IAllele;

import net.minecraft.entity.player.EntityPlayer;

import forestry.api.genetics.IMutation;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.FakeWorld;
import binnie.Binnie;
import binnie.core.util.UniqueItemStackSet;

public class TreeBreedingSystem extends BreedingSystem
{
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
		this.iconUndiscovered = Binnie.Resource.getItemIcon(ExtraTrees.instance, "icon/undiscoveredTree");
		this.iconDiscovered = Binnie.Resource.getItemIcon(ExtraTrees.instance, "icon/discoveredTree");
	}

	@Override
	public float getChance(IMutation mutation, EntityPlayer player, IAllele species1, IAllele species2) {
		ITreeGenome genome0 = (ITreeGenome) this.getSpeciesRoot().templateAsGenome(this.getSpeciesRoot().getTemplate(species1.getUID()));
		ITreeGenome genome2 = (ITreeGenome) this.getSpeciesRoot().templateAsGenome(this.getSpeciesRoot().getTemplate(species2.getUID()));
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
			return types.isEmpty() ? Binnie.Language.localise(BinnieCore.instance, "allele.none") : types.iterator().next().toString();
		}
		if (chromosome == EnumTreeChromosome.FRUITS && allele.getUID().contains(".")) {
			IFruitProvider provider = ((IAlleleFruit) allele).getProvider();
			return (provider.getProducts().length == 0) ? Binnie.Language.localise(BinnieCore.instance, "allele.none") : provider.getProducts()[0].getDisplayName();
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
		this.discoveredFruits.clear();
		this.discoveredWoods.clear();
		for (IAlleleSpecies species : this.getDiscoveredSpecies(tracker)) {
			IAlleleTreeSpecies tSpecies = (IAlleleTreeSpecies) species;
			ITreeGenome genome = (ITreeGenome) this.getSpeciesRoot().templateAsGenome(this.getSpeciesRoot().getTemplate(tSpecies.getUID()));

			FakeWorld world = FakeWorld.instance;
			genome.getPrimary().getGenerator().setLogBlock(genome, world, 0, 0, 0, ForgeDirection.UP);
			ItemStack wood = world.getWooLog();
			if (wood != null) {
				this.discoveredWoods.add(wood);
			}

			// for (ItemStack wood :
			// tSpecies.getRoot().templateAsIndividual(getSpeciesRoot().getTemplate(tSpecies.getUID())).getProduceList())
			// {
			// this.discoveredWoods.add(wood);
			// }
			for (ItemStack fruit : genome.getFruitProvider().getProducts()) {
				this.discoveredFruits.add(fruit);
			}
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

			// for (ItemStack wood :
			// tSpecies.getRoot().templateAsIndividual(getSpeciesRoot().getTemplate(tSpecies.getUID())).getProduceList())
			// {
			// this.allWoods.add(wood);
			// }
			for (ItemStack fruit : genome.getFruitProvider().getProducts()) {
				this.allFruits.add(fruit);
			}
		}
	}

	public Collection<IAlleleSpecies> getTreesThatBearFruit(ItemStack fruit, boolean nei, World world, GameProfile player) {
		Collection<IAlleleSpecies> set = nei ? this.getAllSpecies() : this.getDiscoveredSpecies(world, player);
		List<IAlleleSpecies> found = new ArrayList<IAlleleSpecies>();
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
		Collection<IAlleleSpecies> set = nei ? this.getAllSpecies() : this.getDiscoveredSpecies(world, player);
		List<IAlleleSpecies> found = new ArrayList<IAlleleSpecies>();
		Set<IFruitFamily> providers = new HashSet<IFruitFamily>();
		for (IAlleleSpecies species : set) {
			IAlleleTreeSpecies tSpecies = (IAlleleTreeSpecies) species;
			ITreeGenome genome = (ITreeGenome) this.getSpeciesRoot().templateAsGenome(this.getSpeciesRoot().getTemplate(tSpecies.getUID()));
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
		Collection<IAlleleSpecies> set = nei ? this.getAllSpecies() : this.getDiscoveredSpecies(world, player);
		List<IAlleleSpecies> found = new ArrayList<IAlleleSpecies>();
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
			return new ArrayList<IAlleleSpecies>();
		}
		Collection<IAlleleSpecies> set = nei ? this.getAllSpecies() : this.getDiscoveredSpecies(world, player);
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
		return ((ITreeRoot) this.getSpeciesRoot()).getType(member) == EnumGermlingType.POLLEN;
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
		return new int[] { EnumGermlingType.SAPLING.ordinal(), EnumGermlingType.POLLEN.ordinal() };
	}

	@Override
	public void addExtraAlleles(IChromosomeType chromosome, TreeSet<IAllele> alleles) {
		switch ((EnumTreeChromosome) chromosome) {
		case FERTILITY: {
			for (ForestryAllele.Saplings a : ForestryAllele.Saplings.values()) {
				alleles.add(a.getAllele());
			}
			break;
		}
		case GIRTH: {
			for (ForestryAllele.Int a2 : ForestryAllele.Int.values()) {
				alleles.add(a2.getAllele());
			}
			break;
		}
		case HEIGHT: {
			for (ForestryAllele.TreeHeight a3 : ForestryAllele.TreeHeight.values()) {
				alleles.add(a3.getAllele());
			}
			break;
		}
		case MATURATION: {
			for (ForestryAllele.Maturation a4 : ForestryAllele.Maturation.values()) {
				alleles.add(a4.getAllele());
			}
			break;
		}
		case SAPPINESS: {
			for (ForestryAllele.Sappiness a5 : ForestryAllele.Sappiness.values()) {
				alleles.add(a5.getAllele());
			}
			break;
		}
		case TERRITORY: {
			for (ForestryAllele.Territory a6 : ForestryAllele.Territory.values()) {
				alleles.add(a6.getAllele());
			}
			break;
		}
		case YIELD: {
			for (ForestryAllele.Yield a7 : ForestryAllele.Yield.values()) {
				alleles.add(a7.getAllele());
			}
			break;
		}
		case FIREPROOF: {
			for (ForestryAllele.Bool a8 : ForestryAllele.Bool.values()) {
				alleles.add(a8.getAllele());
			}
			break;
		}
		}
	}
}
