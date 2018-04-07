package binnie.core.genetics;

import binnie.core.api.genetics.IBreedingSystem;
import binnie.core.api.genetics.IItemAnalysable;
import com.google.common.base.Preconditions;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import com.google.common.collect.Iterables;
import com.mojang.authlib.GameProfile;
import forestry.api.genetics.IBreedingTracker;
import net.minecraft.item.ItemStack;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleFloat;
import forestry.api.genetics.IAlleleInteger;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IGenome;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;

import binnie.core.Binnie;
import binnie.core.ManagerBase;

public class ManagerGenetics extends ManagerBase {
	private final Map<ISpeciesRoot, IBreedingSystem> BREEDING_SYSTEMS;
	private final List<IChromosomeType> invalidChromosomeTypes;
	private final Map<ISpeciesRoot, Map<IChromosomeType, List<IAllele>>> chromosomeArray;

	public ManagerGenetics() {
		this.BREEDING_SYSTEMS = new LinkedHashMap<>();
		this.invalidChromosomeTypes = new ArrayList<>();
		this.chromosomeArray = new LinkedHashMap<>();
	}

	public static boolean isAnalysable(final ItemStack stack) {
		final IIndividual ind = AlleleManager.alleleRegistry.getIndividual(stack);
		return ind != null || stack.getItem() instanceof IItemAnalysable || Binnie.GENETICS.getConversion(stack) != null;
	}

	public static boolean isAnalysed(final ItemStack stack) {
		if (stack.isEmpty()) {
			return false;
		}
		final IIndividual ind = AlleleManager.alleleRegistry.getIndividual(stack);
		if (ind != null) {
			return ind.isAnalyzed();
		}
		return stack.getItem() instanceof IItemAnalysable && ((IItemAnalysable) stack.getItem()).isAnalysed(stack);
	}

	public static ItemStack analyse(ItemStack stack, final World world, final GameProfile username) {
		if (!stack.isEmpty()) {
			final ItemStack conv = Binnie.GENETICS.getConversionStack(stack).copy();
			if (!conv.isEmpty()) {
				conv.setCount(stack.getCount());
				stack = conv;
			}
			final ISpeciesRoot root = AlleleManager.alleleRegistry.getSpeciesRoot(stack);
			if (root != null) {
				final IIndividual ind = root.getMember(stack);
				ind.analyze();
				final IBreedingTracker breedingTracker = ind.getGenome().getSpeciesRoot().getBreedingTracker(world, username);
				breedingTracker.registerBirth(ind);
				final NBTTagCompound nbttagcompound = new NBTTagCompound();
				ind.writeToNBT(nbttagcompound);
				stack.setTagCompound(nbttagcompound);
				return stack;
			}
			if (stack.getItem() instanceof IItemAnalysable) {
				return ((IItemAnalysable) stack.getItem()).analyse(stack);
			}
		}
		return stack;
	}

	@Override
	public void postInit() {
		this.refreshData();
	}

	@Nullable
	public IBreedingSystem getSystem(final String string) {
		for (final IBreedingSystem system : this.BREEDING_SYSTEMS.values()) {
			if (system.getIdent().equals(string)) {
				return system;
			}
		}
		return null;
	}

	public IBreedingSystem getSystem(final ISpeciesRoot root) {
		final String rootUID = root.getUID();
		final IBreedingSystem system = this.getSystem(rootUID);
		Preconditions.checkState(system != null, "Could not find system for species root %s", rootUID);
		return system;
	}

	public ISpeciesRoot getSpeciesRoot(final IAlleleSpecies species) {
		return species.getRoot();
	}

	public int[] getTolerance(final forestry.api.genetics.EnumTolerance tol) {
		return Tolerance.values()[tol.ordinal()].getBounds();
	}

	public Collection<IBreedingSystem> getActiveSystems() {
		return this.BREEDING_SYSTEMS.values();
	}

	public IBreedingSystem getFirstActiveSystem() {
		final Collection<IBreedingSystem> activeSystems = getActiveSystems();
		final IBreedingSystem first = Iterables.getFirst(activeSystems, null);
		if (first == null) {
			throw new IllegalStateException("There are no breeding systems");
		}
		return first;
	}

	public void registerBreedingSystem(final IBreedingSystem system) {
		this.BREEDING_SYSTEMS.put(system.getSpeciesRoot(), system);
	}

	@Nullable
	public IBreedingSystem getConversionSystem(final ItemStack stack) {
		if (!stack.isEmpty()) {
			for (final IBreedingSystem system : this.getActiveSystems()) {
				if (system.getConversion(stack) != null) {
					return system;
				}
			}
		}
		return null;
	}

	public ItemStack getConversionStack(final ItemStack stack) {
		if (!stack.isEmpty()) {
			final IBreedingSystem system = this.getConversionSystem(stack);
			if (system != null) {
				return system.getConversionStack(stack);
			}
		}
		return ItemStack.EMPTY;
	}

	@Nullable
	public IIndividual getConversion(final ItemStack stack) {
		final IBreedingSystem system = this.getConversionSystem(stack);
		return (system == null) ? null : system.getConversion(stack);
	}

	@SubscribeEvent
	public void onWorldLoad(final WorldEvent.Load event) {
		this.refreshData();
	}

	private void refreshData() {
		this.loadAlleles();
		for (IBreedingSystem system : Binnie.GENETICS.getActiveSystems()) {
			system.calculateArrays();
		}
	}

	private void loadAlleles() {
		this.invalidChromosomeTypes.clear();
		final ComparatorAllele comparator = new ComparatorAllele();
		for (IBreedingSystem system : BREEDING_SYSTEMS.values()) {
			final ISpeciesRoot root = system.getSpeciesRoot();
			final Map<IChromosomeType, List<IAllele>> chromosomeMap = new LinkedHashMap<>();
			for (final IChromosomeType chromosome : root.getKaryotype()) {
				final TreeSet<IAllele> alleles = new TreeSet<>(comparator);
				for (IIndividual individual : root.getIndividualTemplates()) {
					final IGenome genome = individual.getGenome();
					final IAllele activeAllele = genome.getActiveAllele(chromosome);
					final IAllele inactiveAllele = genome.getInactiveAllele(chromosome);
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
					final List<IAllele> alleleList = new ArrayList<>(alleles);
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
				return Float.compare(((IAlleleFloat) o1).getValue(), ((IAlleleFloat) o2).getValue());
			}
			if (o1.getClass().equals(IAlleleInteger.class) && o2.getClass().equals(IAlleleInteger.class)) {
				return Integer.compare(((IAlleleInteger) o1).getValue(), ((IAlleleInteger) o2).getValue());
			}
			if (o1.getAlleleName() != null && o2.getAlleleName() != null) {
				return o1.getAlleleName().compareTo(o2.getAlleleName());
			}
			return o1.getUID().compareTo(o2.getUID());
		}
	}
}
