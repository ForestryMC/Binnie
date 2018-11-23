package binnie.genetics.genetics;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

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

import binnie.core.Binnie;
import binnie.core.api.genetics.IFieldKitPlugin;
import binnie.core.api.gui.IPoint;
import binnie.core.api.gui.ITexture;
import binnie.core.genetics.BreedingSystem;
import binnie.core.genetics.ForestryAllele;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.resource.textures.StandardTexture;
import binnie.core.texture.BinnieCoreTexture;
import binnie.core.util.I18N;
import binnie.core.util.UniqueItemStackSet;
import binnie.genetics.Genetics;

public class TreeBreedingSystem extends BreedingSystem implements binnie.genetics.api.ITreeBreedingSystem {
	private final UniqueItemStackSet allFruits;
	private final UniqueItemStackSet allWoods;
	private final UniqueItemStackSet discoveredPlanks;
	private final UniqueItemStackSet discoveredFruits;
	private final UniqueItemStackSet discoveredWoods;

	public TreeBreedingSystem() {
		this.allFruits = new UniqueItemStackSet();
		this.allWoods = new UniqueItemStackSet();
		this.discoveredFruits = new UniqueItemStackSet();
		this.discoveredWoods = new UniqueItemStackSet();
		this.discoveredPlanks = new UniqueItemStackSet();
		this.iconUndiscovered = Binnie.RESOURCE.getItemSprite(Genetics.instance, "icon/undiscovered_tree");
		this.iconDiscovered = Binnie.RESOURCE.getItemSprite(Genetics.instance, "icon/discovered_tree");
	}

	@Override
	public float getChance(IMutation mutation, EntityPlayer player, IAlleleSpecies firstSpecies, IAlleleSpecies secondSpecies) {
		ISpeciesRoot speciesRoot = this.getSpeciesRoot();
		ITreeGenome genome0 = (ITreeGenome) speciesRoot.templateAsGenome(speciesRoot.getTemplate(firstSpecies));
		ITreeGenome genome2 = (ITreeGenome) speciesRoot.templateAsGenome(speciesRoot.getTemplate(secondSpecies));
		return ((ITreeMutation) mutation).getChance(player.world, player.getPosition(), (IAlleleTreeSpecies) firstSpecies, (IAlleleTreeSpecies) secondSpecies, genome0, genome2);
	}

	@Override
	public ISpeciesRoot getSpeciesRoot() {
		return TreeManager.treeRoot;
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
			IAlleleInteger alleleInteger = (IAlleleInteger) allele;
			return alleleInteger.getValue() + "x" + alleleInteger.getValue();
		}
		if (chromosome == EnumTreeChromosome.FRUITS && allele.getUID().contains(".")) {
			IFruitProvider provider = ((IAlleleFruit) allele).getProvider();
			return (provider.getProducts().isEmpty()) ? I18N.localise("binniecore.allele.none") : provider.getProducts().keySet().iterator().next().getDisplayName();
		}
		return super.getAlleleName(chromosome, allele);
	}

	@Override
	public void onSyncBreedingTracker(IBreedingTracker tracker) {
		this.discoveredFruits.clear();
		this.discoveredWoods.clear();
		for (IAlleleSpecies species : this.getDiscoveredSpecies(tracker)) {
			IAlleleTreeSpecies tSpecies = (IAlleleTreeSpecies) species;
			ITreeGenome genome = (ITreeGenome) this.getSpeciesRoot().templateAsGenome(this.getSpeciesRoot().getTemplate(tSpecies));

			IAlleleTreeSpecies treeSpecies = genome.getPrimary();
			ItemStack wood = treeSpecies.getWoodProvider().getWoodStack();
			if (!wood.isEmpty()) {
				this.discoveredWoods.add(wood);
			}

			/*for (final ItemStack wood : tSpecies.getRoot().templateAsIndividual(getSpeciesRoot().getTemplate(tSpecies.getUID())).getProduceList()) {
				this.discoveredWoods.add(wood);
			}*/
			this.discoveredFruits.addAll(genome.getFruitProvider().getProducts().keySet());
		}
	}

	@Override
	public final void calculateArrays() {
		super.calculateArrays();
		for (IAlleleSpecies species : this.allActiveSpecies) {
			IAlleleTreeSpecies tSpecies = (IAlleleTreeSpecies) species;
			ITreeGenome genome = (ITreeGenome) this.getSpeciesRoot().templateAsGenome(this.getSpeciesRoot().getTemplate(tSpecies));

			IAlleleTreeSpecies treeSpecies = genome.getPrimary();
			ItemStack wood = treeSpecies.getWoodProvider().getWoodStack();
			if (!wood.isEmpty()) {
				this.allWoods.add(wood);
			}

			/*for (final ItemStack wood : tSpecies.getRoot().templateAsIndividual(getSpeciesRoot().getTemplate(tSpecies.getUID())).getProduceList()) {
				this.allWoods.add(wood);
			}*/
			this.allFruits.addAll(genome.getFruitProvider().getProducts().keySet());
		}
	}

	@Override
	public Collection<IAlleleSpecies> getTreesThatBearFruit(ItemStack fruit, boolean master, World world, GameProfile player) {
		Collection<IAlleleSpecies> set = master ? this.getAllSpecies() : this.getDiscoveredSpecies(world, player);
		List<IAlleleSpecies> found = new ArrayList<>();
		for (IAlleleSpecies species : set) {
			IAlleleTreeSpecies tSpecies = (IAlleleTreeSpecies) species;
			ITreeGenome genome = (ITreeGenome) this.getSpeciesRoot().templateAsGenome(this.getSpeciesRoot().getTemplate(tSpecies));
			for (ItemStack fruit2 : genome.getFruitProvider().getProducts().keySet()) {
				if (fruit2.isItemEqual(fruit)) {
					found.add(species);
				}
			}
		}
		return found;
	}

	@Override
	public Collection<IAlleleSpecies> getTreesThatCanBearFruit(ItemStack fruit, boolean master, World world, GameProfile player) {
		Collection<IAlleleSpecies> set = master ? this.getAllSpecies() : this.getDiscoveredSpecies(world, player);
		List<IAlleleSpecies> found = new ArrayList<>();
		Set<IFruitFamily> providers = new HashSet<>();
		for (IAlleleSpecies species : set) {
			IAlleleTreeSpecies tSpecies = (IAlleleTreeSpecies) species;
			ITreeGenome genome = (ITreeGenome) this.getSpeciesRoot().templateAsGenome(this.getSpeciesRoot().getTemplate(tSpecies));
			for (ItemStack fruit2 : genome.getFruitProvider().getProducts().keySet()) {
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

	@Override
	public Collection<IAlleleSpecies> getTreesThatHaveWood(ItemStack wood, boolean master, World world, GameProfile player) {
		Collection<IAlleleSpecies> set = master ? this.getAllSpecies() : this.getDiscoveredSpecies(world, player);
		List<IAlleleSpecies> found = new ArrayList<>();
		for (IAlleleSpecies species : set) {
			IAlleleTreeSpecies tSpecies = (IAlleleTreeSpecies) species;
			ITreeGenome genome = TreeManager.treeRoot.templateAsGenome(TreeManager.treeRoot.getTemplate(tSpecies));
			IAlleleTreeSpecies treeSpecies = genome.getPrimary();
			ItemStack woodStack = treeSpecies.getWoodProvider().getWoodStack();

			if (woodStack.isItemEqual(wood)) {
				found.add(species);
			}
		}
		return found;
	}

	@Override
	public boolean isDNAManipulable(ItemStack member) {
		ISpeciesType type = this.getSpeciesRoot().getType(member);
		return isDNAManipulable(type);
	}

	@Override
	public boolean isDNAManipulable(@Nullable ISpeciesType type) {
		return type == EnumGermlingType.POLLEN;
	}

	@Override
	@Nullable
	public IIndividual getConversion(ItemStack stack) {
		/*for (final Map.Entry<ItemStack, IIndividual> entry : TreeManager.treeRoot.getIndividualTemplates()) {
			if (ItemStack.areItemStacksEqual(stack, entry.getKey())) {
				return entry.getValue();
			}
		}*/
		return TreeManager.treeRoot.getMember(stack);
	}

	@Override
	public ISpeciesType[] getActiveTypes() {
		return new ISpeciesType[]{EnumGermlingType.SAPLING, EnumGermlingType.POLLEN};
	}

	@Override
	public void addExtraAlleles(IChromosomeType chromosome, Set<IAllele> alleles) {
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

	@Override
	public IFieldKitPlugin getFieldKitPlugin() {
		return new FieldKitPlugin();
	}

	@Override
	public Set<ItemStack> getAllFruits() {
		return allFruits;
	}

	@Override
	public Set<ItemStack> getAllWoods() {
		return allWoods;
	}

	private static class FieldKitPlugin implements IFieldKitPlugin {
		@Override
		public Map<IChromosomeType, IPoint> getChromosomePickerPositions() {
			Map<IChromosomeType, IPoint> positions = new HashMap<>();
			positions.put(EnumTreeChromosome.SPECIES, new Point(48, 48));
			positions.put(EnumTreeChromosome.HEIGHT, new Point(43, 84));
			positions.put(EnumTreeChromosome.FERTILITY, new Point(25, 63));
			positions.put(EnumTreeChromosome.FRUITS, new Point(72, 57));
			positions.put(EnumTreeChromosome.YIELD, new Point(21, 43));
			positions.put(EnumTreeChromosome.SAPPINESS, new Point(15, 17));
			positions.put(EnumTreeChromosome.EFFECT, new Point(67, 15));
			positions.put(EnumTreeChromosome.MATURATION, new Point(70, 34));
			positions.put(EnumTreeChromosome.GIRTH, new Point(45, 67));
			positions.put(EnumTreeChromosome.FIREPROOF, new Point(5, 70));
			return positions;
		}

		@Override
		public ITexture getTypeTexture() {
			return new StandardTexture(96, 0, 96, 96, BinnieCoreTexture.GUI_BREEDING);
		}
	}
}
