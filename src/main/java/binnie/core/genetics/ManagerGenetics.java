package binnie.core.genetics;

import com.google.common.base.Preconditions;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import net.minecraft.item.ItemStack;

import net.minecraftforge.event.world.WorldEvent;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.IBeeRoot;
import forestry.api.arboriculture.ITreeRoot;
import forestry.api.arboriculture.TreeManager;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleFloat;
import forestry.api.genetics.IAlleleInteger;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IGenome;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;
import forestry.api.lepidopterology.ButterflyManager;
import forestry.api.lepidopterology.IButterflyRoot;

import binnie.Binnie;
import binnie.Constants;
import binnie.botany.api.genetics.IFlowerRoot;
import binnie.botany.genetics.FlowerBreedingSystem;
import binnie.botany.genetics.allele.AlleleFlowerColor;
import binnie.botany.modules.BotanyModuleUIDs;
import binnie.core.BinnieCore;
import binnie.core.ManagerBase;
import binnie.modules.ModuleManager;

public class ManagerGenetics extends ManagerBase {
	private final Map<ISpeciesRoot, BreedingSystem> BREEDING_SYSTEMS;
	public BreedingSystem beeBreedingSystem;
	public BreedingSystem treeBreedingSystem;
	public BreedingSystem mothBreedingSystem;
	public BreedingSystem flowerBreedingSystem;
	private List<IChromosomeType> invalidChromosomeTypes;
	private Map<ISpeciesRoot, Map<IChromosomeType, List<IAllele>>> chromosomeArray;

	public ManagerGenetics() {
		this.BREEDING_SYSTEMS = new LinkedHashMap<>();
		this.invalidChromosomeTypes = new ArrayList<>();
		this.chromosomeArray = new LinkedHashMap<>();
	}

	@Override
	public void preInit() {
		if (BinnieCore.isApicultureActive()) {
			this.beeBreedingSystem = new BeeBreedingSystem();
		}
		if (BinnieCore.isArboricultureActive()) {
			this.treeBreedingSystem = new TreeBreedingSystem();
		}
		if (BinnieCore.isLepidopteryActive()) {
			this.mothBreedingSystem = new MothBreedingSystem();
		}
		if (ModuleManager.isModuleEnabled(Constants.BOTANY_MOD_ID, BotanyModuleUIDs.FLOWERS)) {
			this.flowerBreedingSystem = new FlowerBreedingSystem();
		}
	}

	@Override
	public void postInit() {
		this.refreshData();
	}

	public ITreeRoot getTreeRoot() {
		return TreeManager.treeRoot;
	}

	public IBeeRoot getBeeRoot() {
		return BeeManager.beeRoot;
	}

	public IButterflyRoot getButterflyRoot() {
		return ButterflyManager.butterflyRoot;
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

	public int[] getTolerance(final forestry.api.genetics.EnumTolerance tol) {
		return Tolerance.values()[tol.ordinal()].getBounds();
	}

	public Collection<BreedingSystem> getActiveSystems() {
		return this.BREEDING_SYSTEMS.values();
	}

	public void registerBreedingSystem(final BreedingSystem system) {
		this.BREEDING_SYSTEMS.put(system.getSpeciesRoot(), system);
	}

	@Nullable
	public BreedingSystem getConversionSystem(final ItemStack stack) {
		if (!stack.isEmpty()) {
			for (final BreedingSystem system : this.getActiveSystems()) {
				if (system.getConversion(stack) != null) {
					return system;
				}
			}
		}
		return null;
	}

	public ItemStack getConversionStack(final ItemStack stack) {
		if (!stack.isEmpty()) {
			final BreedingSystem system = this.getConversionSystem(stack);
			if (system != null) {
				return system.getConversionStack(stack);
			}
		}
		return ItemStack.EMPTY;
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
		for (ISpeciesRoot root : AlleleManager.alleleRegistry.getSpeciesRoot().values()) {
			BreedingSystem system = this.getSystem(root);
			Map<IChromosomeType, List<IAllele>> chromosomeMap = new LinkedHashMap<>();
			for (IChromosomeType chromosome : root.getKaryotype()) {
				TreeSet<IAllele> alleles = new TreeSet<>(new ComparatorAllele());
				for (IIndividual individual : root.getIndividualTemplates()) {
					IGenome genome = individual.getGenome();
					IAllele activeAllele = genome.getActiveAllele(chromosome);
					IAllele inactiveAllele = genome.getInactiveAllele(chromosome);
					if (chromosome.getAlleleClass().isInstance(activeAllele)) {
						alleles.add(activeAllele);
					}
					if (!chromosome.getAlleleClass().isInstance(inactiveAllele)) {
						continue;
					}
					alleles.add(inactiveAllele);
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
			if (o1 instanceof IAlleleInteger && o2 instanceof IAlleleInteger && !(o1 instanceof AlleleFlowerColor)) {
				return Integer.valueOf(((IAlleleInteger) o1).getValue()).compareTo(((IAlleleInteger) o2).getValue());
			}
			if (o1.getAlleleName() != null && o2.getAlleleName() != null) {
				return o1.getAlleleName().compareTo(o2.getAlleleName());
			}
			return o1.getUID().compareTo(o2.getUID());
		}
	}
}
