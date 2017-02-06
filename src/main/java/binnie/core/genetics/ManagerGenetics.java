package binnie.core.genetics;

import binnie.Binnie;
import binnie.botany.api.IFlowerRoot;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.botany.genetics.FlowerBreedingSystem;
import binnie.core.BinnieCore;
import binnie.core.ManagerBase;
import com.google.common.base.Preconditions;
import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.IBeeRoot;
import forestry.api.arboriculture.ITreeRoot;
import forestry.api.arboriculture.TreeManager;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.EnumTolerance;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleFloat;
import forestry.api.genetics.IAlleleInteger;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IGenome;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;
import forestry.api.lepidopterology.IButterflyRoot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class ManagerGenetics extends ManagerBase {
	public BreedingSystem beeBreedingSystem;
	public BreedingSystem treeBreedingSystem;
	public BreedingSystem mothBreedingSystem;
	public BreedingSystem flowerBreedingSystem;
	private final Map<ISpeciesRoot, BreedingSystem> BREEDING_SYSTEMS;
	private List<IChromosomeType> invalidChromosomeTypes;
	private Map<ISpeciesRoot, Map<IChromosomeType, List<IAllele>>> chromosomeArray;

	public ManagerGenetics() {
		this.BREEDING_SYSTEMS = new LinkedHashMap<>();
		this.invalidChromosomeTypes = new ArrayList<>();
		this.chromosomeArray = new LinkedHashMap<>();
	}

	@Override
	public void init() {
		if (BinnieCore.isApicultureActive()) {
			this.beeBreedingSystem = new BeeBreedingSystem();
		}
		if (BinnieCore.isArboricultureActive()) {
			this.treeBreedingSystem = new TreeBreedingSystem();
		}
		if (BinnieCore.isLepidopteryActive()) {
			this.mothBreedingSystem = new MothBreedingSystem();
		}
		if (BinnieCore.isBotanyActive()) {
			this.flowerBreedingSystem = new FlowerBreedingSystem();
		}
	}

	@Override
	public void postInit() {
		this.refreshData();
	}

	public boolean isSpeciesDiscovered(final IAlleleSpecies species, final World world, final boolean nei) {
		return true;
	}

	public ITreeRoot getTreeRoot() {
		return TreeManager.treeRoot;
	}

	public IBeeRoot getBeeRoot() {
		return BeeManager.beeRoot;
	}

	public IButterflyRoot getButterflyRoot() {
		return (IButterflyRoot) AlleleManager.alleleRegistry.getSpeciesRoot("rootButterflies");
	}

	public IFlowerRoot getFlowerRoot() {
		return (IFlowerRoot) AlleleManager.alleleRegistry.getSpeciesRoot("rootFlowers");
	}

	@Nullable
	public BreedingSystem getSystem(final String string) {
		for (final BreedingSystem system : this.BREEDING_SYSTEMS.values()) {
			if (system.getIdent().equals(string)) {
				return system;
			}
		}
		return null;
	}

	public BreedingSystem getSystem(final ISpeciesRoot root) {
		String rootUID = root.getUID();
		BreedingSystem system = this.getSystem(rootUID);
		Preconditions.checkState(system != null, "Could not find system for species root %s", rootUID);
		return system;
	}

	public ISpeciesRoot getSpeciesRoot(final IAlleleSpecies species) {
		return species.getRoot();
	}

	public IAllele getToleranceAllele(final EnumTolerance tol) {
		return AlleleManager.alleleRegistry.getAllele(Tolerance.values()[tol.ordinal()].getUID());
	}

	public int[] getTolerance(final EnumTolerance tol) {
		return Tolerance.values()[tol.ordinal()].getBounds();
	}

	public Collection<BreedingSystem> getActiveSystems() {
		return this.BREEDING_SYSTEMS.values();
	}

	public void registerBreedingSystem(final BreedingSystem system) {
		this.BREEDING_SYSTEMS.put(system.getSpeciesRoot(), system);
	}

	@Nullable
	public BreedingSystem getConversionSystem(@Nullable final ItemStack stack) {
		if (stack != null) {
			for (final BreedingSystem system : this.getActiveSystems()) {
				if (system.getConversion(stack) != null) {
					return system;
				}
			}
		}
		return null;
	}

	@Nullable
	public ItemStack getConversionStack(@Nullable final ItemStack stack) {
		if (stack != null) {
			final BreedingSystem system = this.getConversionSystem(stack);
			if (system != null) {
				return system.getConversionStack(stack);
			}
		}
		return null;
	}

	@Nullable
	public IIndividual getConversion(final ItemStack stack) {
		final BreedingSystem system = this.getConversionSystem(stack);
		return (system == null) ? null : system.getConversion(stack);
	}

	@SubscribeEvent
	public void onWorldLoad(final WorldEvent.Load event) {
		this.refreshData();
	}

	private void refreshData() {
		this.loadAlleles();
		for (final BreedingSystem system : Binnie.GENETICS.getActiveSystems()) {
			system.calculateArrays();
		}
	}

	private void loadAlleles() {
		this.invalidChromosomeTypes.clear();
		for (final ISpeciesRoot root : AlleleManager.alleleRegistry.getSpeciesRoot().values()) {
			final BreedingSystem system = this.getSystem(root);
			final Map<IChromosomeType, List<IAllele>> chromosomeMap = new LinkedHashMap<>();
			for (final IChromosomeType chromosome : root.getKaryotype()) {
				final TreeSet<IAllele> alleles = new TreeSet<>(new ComparatorAllele());
				for (final IIndividual individual : root.getIndividualTemplates()) {
					final IGenome genome = individual.getGenome();
					final IAllele a1 = genome.getActiveAllele(chromosome);
					final IAllele a2 = genome.getInactiveAllele(chromosome);
					if (chromosome.getAlleleClass().isInstance(a1)) {
						alleles.add(a1);
					}
					if (!chromosome.getAlleleClass().isInstance(a2)) {
						continue;
					}
					alleles.add(a2);
				}
				system.addExtraAlleles(chromosome, alleles);
				if (alleles.size() == 0) {
					this.invalidChromosomeTypes.add(chromosome);
				} else {
					final List<IAllele> alleleList = new ArrayList<>();
					alleleList.addAll(alleles);
					chromosomeMap.put(chromosome, alleleList);
				}
			}
			this.chromosomeArray.put(root, chromosomeMap);
		}
	}

	public Map<IChromosomeType, List<IAllele>> getChromosomeMap(final ISpeciesRoot root) {
		return this.chromosomeArray.get(root);
	}

	public Collection<IChromosomeType> getActiveChromosomes(final ISpeciesRoot root) {
		return this.getChromosomeMap(root).keySet();
	}

	public boolean isInvalidChromosome(final IChromosomeType type) {
		return this.invalidChromosomeTypes.contains(type);
	}

	static class ComparatorAllele implements Comparator<IAllele> {
		@Override
		public int compare(final IAllele o1, final IAllele o2) {
			if (o1 == null || o2 == null) {
				throw new NullPointerException("Allele is null!");
			}
			if (o1 instanceof IAlleleFloat && o2 instanceof IAlleleFloat) {
				return Float.valueOf(((IAlleleFloat) o1).getValue()).compareTo(((IAlleleFloat) o2).getValue());
			}
			if (o1 instanceof IAlleleInteger && o2 instanceof IAlleleInteger && !(o1 instanceof EnumFlowerColor)) {
				return Integer.valueOf(((IAlleleInteger) o1).getValue()).compareTo(((IAlleleInteger) o2).getValue());
			}
			if (o1.getName() != null && o2.getName() != null) {
				return o1.getName().compareTo(o2.getName());
			}
			return o1.getUID().compareTo(o2.getUID());
		}
	}
}
