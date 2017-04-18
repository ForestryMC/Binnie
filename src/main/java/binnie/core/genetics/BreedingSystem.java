package binnie.core.genetics;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.resource.BinnieIcon;
import binnie.extrabees.genetics.ExtraBeeMutation;
import com.mojang.authlib.GameProfile;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import forestry.api.core.ForestryEvent;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleBoolean;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IBreedingTracker;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IClassification;
import forestry.api.genetics.IGenome;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.IMutation;
import forestry.api.genetics.ISpeciesRoot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public abstract class BreedingSystem implements IItemStackRepresentitive {
	protected BinnieIcon iconUndiscovered;
	protected BinnieIcon iconDiscovered;
	private List<IClassification> allBranches;
	List<IAlleleSpecies> allActiveSpecies;
	private List<IAlleleSpecies> allSpecies;
	private List<IMutation> allMutations;
	private Map<IAlleleSpecies, List<IMutation>> resultantMutations;
	private Map<IAlleleSpecies, List<IMutation>> furtherMutations;
	private Map<IAlleleSpecies, List<IMutation>> allResultantMutations;
	private Map<IAlleleSpecies, List<IMutation>> allFurtherMutations;
	public float discoveredSpeciesPercentage;
	public int totalSpeciesCount;
	public int discoveredSpeciesCount;
	public int totalSecretCount;
	public int discoveredSecretCount;
	public float discoveredBranchPercentage;
	public int totalBranchCount;
	public int discoveredBranchCount;
	private int totalSecretBranchCount;
	private int discoveredSecretBranchCount;
	String currentEpithet;

	public BreedingSystem() {
		allBranches = new ArrayList<IClassification>();
		allActiveSpecies = new ArrayList<IAlleleSpecies>();
		allSpecies = new ArrayList<IAlleleSpecies>();
		allMutations = new ArrayList<IMutation>();
		resultantMutations = new HashMap<IAlleleSpecies, List<IMutation>>();
		furtherMutations = new HashMap<IAlleleSpecies, List<IMutation>>();
		allResultantMutations = new HashMap<IAlleleSpecies, List<IMutation>>();
		allFurtherMutations = new HashMap<IAlleleSpecies, List<IMutation>>();
		Binnie.Genetics.registerBreedingSystem(this);
		MinecraftForge.EVENT_BUS.register(this);
	}

	public String getChromosomeName(final IChromosomeType chromo) {
		return BinnieCore.proxy.localise(this.getSpeciesRoot().getUID() + ".chromosome." + chromo.getName());
	}

	public String getChromosomeShortName(final IChromosomeType chromo) {
		return BinnieCore.proxy.localise(this.getSpeciesRoot().getUID() + ".chromosome." + chromo.getName() + ".short");
	}

	public final String getEpitome(final float discoveredPercentage) {
		int i = 0;
		if (discoveredPercentage == 1.0f) {
			i = 6;
		} else if (discoveredPercentage < 0.1f) {
			i = 0;
		} else if (discoveredPercentage < 0.3f) {
			i = 1;
		} else if (discoveredPercentage < 0.5f) {
			i = 2;
		} else if (discoveredPercentage < 0.7f) {
			i = 3;
		} else if (discoveredPercentage < 0.9f) {
			i = 4;
		} else if (discoveredPercentage < 1.0f) {
			i = 5;
		}
		return BinnieCore.proxy.localise(this.getSpeciesRoot().getUID() + ".epitome." + i);
	}

	public abstract ISpeciesRoot getSpeciesRoot();

	public final List<IClassification> getAllBranches() {
		return allBranches;
	}

	public final Collection<IAlleleSpecies> getAllSpecies() {
		return allActiveSpecies;
	}

	public final Collection<IMutation> getAllMutations() {
		return allMutations;
	}

	public void calculateArrays() {
		final Collection<IAllele> allAlleles = AlleleManager.alleleRegistry.getRegisteredAlleles().values();
		resultantMutations = new HashMap<IAlleleSpecies, List<IMutation>>();
		furtherMutations = new HashMap<IAlleleSpecies, List<IMutation>>();
		allResultantMutations = new HashMap<IAlleleSpecies, List<IMutation>>();
		allFurtherMutations = new HashMap<IAlleleSpecies, List<IMutation>>();
		allActiveSpecies = new ArrayList<IAlleleSpecies>();
		allSpecies = new ArrayList<IAlleleSpecies>();
		for (final IAllele species : allAlleles) {
			if (getSpeciesRoot().getTemplate(species.getUID()) != null) {
				resultantMutations.put((IAlleleSpecies) species, new ArrayList<IMutation>());
				furtherMutations.put((IAlleleSpecies) species, new ArrayList<IMutation>());
				allResultantMutations.put((IAlleleSpecies) species, new ArrayList<IMutation>());
				allFurtherMutations.put((IAlleleSpecies) species, new ArrayList<IMutation>());
				allSpecies.add((IAlleleSpecies) species);
				if (isBlacklisted(species) || species.getUID().contains("speciesBotAlfheim")) {
					continue;
				}
				allActiveSpecies.add((IAlleleSpecies) species);
			}
		}

		allMutations = new ArrayList<IMutation>();
		final Collection<IClassification> allRegBranches = AlleleManager.alleleRegistry.getRegisteredClassifications().values();
		allBranches = new ArrayList<IClassification>();
		for (final IClassification branch : allRegBranches) {
			if (branch.getMemberSpecies().length > 0 && this.getSpeciesRoot().getTemplate(branch.getMemberSpecies()[0].getUID()) != null) {
				boolean possible = false;
				for (final IAlleleSpecies species2 : branch.getMemberSpecies()) {
					if (this.allActiveSpecies.contains(species2)) {
						possible = true;
					}
				}
				if (!possible) {
					continue;
				}
				allBranches.add(branch);
			}
		}
		if (this.getSpeciesRoot().getMutations(false) != null) {
			final Set<IMutation> mutations = new LinkedHashSet<IMutation>();
			mutations.addAll(this.getSpeciesRoot().getMutations(false));
			if (this == Binnie.Genetics.beeBreedingSystem) {
				mutations.addAll(ExtraBeeMutation.mutations);
			}
			for (final IMutation mutation : mutations) {
				this.allMutations.add(mutation);
				final Set<IAlleleSpecies> participatingSpecies = new LinkedHashSet<IAlleleSpecies>();
				if (mutation.getAllele0() instanceof IAlleleSpecies) {
					participatingSpecies.add(mutation.getAllele0());
				}
				if (mutation.getAllele1() instanceof IAlleleSpecies) {
					participatingSpecies.add(mutation.getAllele1());
				}
				for (final IAlleleSpecies species3 : participatingSpecies) {
					this.allFurtherMutations.get(species3).add(mutation);
					if (this.allActiveSpecies.contains(species3)) {
						this.furtherMutations.get(species3).add(mutation);
					}
				}
				if (this.resultantMutations.containsKey(mutation.getTemplate()[0])) {
					this.allResultantMutations.get(mutation.getTemplate()[0]).add(mutation);
					this.resultantMutations.get(mutation.getTemplate()[0]).add(mutation);
				}
			}
		}
	}

	public final boolean isBlacklisted(final IAllele allele) {
		return AlleleManager.alleleRegistry.isBlacklisted(allele.getUID());
	}

	public final List<IMutation> getResultantMutations(final IAlleleSpecies species, final boolean includeInactive) {
		if (this.resultantMutations.isEmpty()) {
			this.calculateArrays();
		}
		return includeInactive ? this.allResultantMutations.get(species) : this.resultantMutations.get(species);
	}

	public final List<IMutation> getResultantMutations(final IAlleleSpecies species) {
		if (this.resultantMutations.isEmpty()) {
			this.calculateArrays();
		}
		return this.resultantMutations.get(species);
	}

	public final List<IMutation> getFurtherMutations(final IAlleleSpecies species, final boolean includeInactive) {
		if (this.furtherMutations.isEmpty()) {
			this.calculateArrays();
		}
		return includeInactive ? this.allFurtherMutations.get(species) : this.furtherMutations.get(species);
	}

	public final List<IMutation> getFurtherMutations(final IAlleleSpecies species) {
		if (this.furtherMutations.isEmpty()) {
			this.calculateArrays();
		}
		return this.furtherMutations.get(species);
	}

	public final boolean isMutationDiscovered(final IMutation mutation, final World world, final GameProfile name) {
		return this.isMutationDiscovered(mutation, this.getSpeciesRoot().getBreedingTracker(world, name));
	}

	public final boolean isMutationDiscovered(final IMutation mutation, final IBreedingTracker tracker) {
		return tracker == null || tracker.isDiscovered(mutation);
	}

	public final boolean isSpeciesDiscovered(final IAlleleSpecies species, final World world, final GameProfile name) {
		return this.isSpeciesDiscovered(species, this.getSpeciesRoot().getBreedingTracker(world, name));
	}

	public final boolean isSpeciesDiscovered(final IAlleleSpecies species, final IBreedingTracker tracker) {
		return tracker == null || tracker.isDiscovered(species);
	}

	public final boolean isSecret(final IAlleleSpecies species) {
		return !species.isCounted();
	}

	public final boolean isSecret(final IClassification branch) {
		for (final IAlleleSpecies species : branch.getMemberSpecies()) {
			if (!this.isSecret(species)) {
				return false;
			}
		}
		return true;
	}

	public final Collection<IClassification> getDiscoveredBranches(final World world, final GameProfile player) {
		final List<IClassification> branches = new ArrayList<IClassification>();
		for (final IClassification branch : this.getAllBranches()) {
			boolean discovered = false;
			for (final IAlleleSpecies species : branch.getMemberSpecies()) {
				if (this.isSpeciesDiscovered(species, world, player)) {
					discovered = true;
				}
			}
			if (discovered) {
				branches.add(branch);
			}
		}
		return branches;
	}

	public final Collection<IClassification> getDiscoveredBranches(final IBreedingTracker tracker) {
		final List<IClassification> branches = new ArrayList<IClassification>();
		for (final IClassification branch : this.getAllBranches()) {
			boolean discovered = false;
			for (final IAlleleSpecies species : branch.getMemberSpecies()) {
				if (this.isSpeciesDiscovered(species, tracker)) {
					discovered = true;
				}
			}
			if (discovered) {
				branches.add(branch);
			}
		}
		return branches;
	}

	public final Collection<IAlleleSpecies> getDiscoveredSpecies(final World world, final GameProfile player) {
		final List<IAlleleSpecies> speciesList = new ArrayList<IAlleleSpecies>();
		for (final IAlleleSpecies species : this.getAllSpecies()) {
			if (this.isSpeciesDiscovered(species, world, player)) {
				speciesList.add(species);
			}
		}
		return speciesList;
	}

	public final Collection<IAlleleSpecies> getDiscoveredSpecies(final IBreedingTracker tracker) {
		final List<IAlleleSpecies> speciesList = new ArrayList<IAlleleSpecies>();
		for (final IAlleleSpecies species : this.getAllSpecies()) {
			if (this.isSpeciesDiscovered(species, tracker)) {
				speciesList.add(species);
			}
		}
		return speciesList;
	}

	public final List<IMutation> getDiscoveredMutations(final World world, final GameProfile player) {
		final List<IMutation> speciesList = new ArrayList<IMutation>();
		for (final IMutation species : this.getAllMutations()) {
			if (this.isMutationDiscovered(species, world, player)) {
				speciesList.add(species);
			}
		}
		return speciesList;
	}

	public final List<IMutation> getDiscoveredMutations(final IBreedingTracker tracker) {
		final List<IMutation> speciesList = new ArrayList<IMutation>();
		for (final IMutation species : this.getAllMutations()) {
			if (this.isMutationDiscovered(species, tracker)) {
				speciesList.add(species);
			}
		}
		return speciesList;
	}

	public final int getDiscoveredBranchMembers(final IClassification branch, final IBreedingTracker tracker) {
		int discoveredSpecies = 0;
		for (final IAlleleSpecies species : branch.getMemberSpecies()) {
			if (this.isSpeciesDiscovered(species, tracker)) {
				++discoveredSpecies;
			}
		}
		return discoveredSpecies;
	}

	public IIcon getUndiscoveredIcon() {
		return this.iconUndiscovered.getIcon();
	}

	public IIcon getDiscoveredIcon() {
		return this.iconDiscovered.getIcon();
	}

	public abstract float getChance(final IMutation p0, final EntityPlayer p1, final IAllele p2, final IAllele p3);

	public abstract Class<? extends IBreedingTracker> getTrackerClass();

	@SubscribeEvent
	public final void onSyncBreedingTracker(final ForestryEvent.SyncedBreedingTracker event) {
		final IBreedingTracker tracker = event.tracker;
		if (!this.getTrackerClass().isInstance(tracker)) {
			return;
		}
		this.syncTracker(tracker);
	}

	public final void syncTracker(final IBreedingTracker tracker) {
		this.discoveredSpeciesPercentage = 0.0f;
		this.totalSpeciesCount = 0;
		this.discoveredSpeciesCount = 0;
		this.totalSecretCount = 0;
		this.discoveredSecretCount = 0;
		final Collection<IAlleleSpecies> discoveredSpecies = this.getDiscoveredSpecies(tracker);
		final Collection<IAlleleSpecies> allSpecies = this.getAllSpecies();
		for (final IAlleleSpecies species : allSpecies) {
			if (!this.isSecret(species)) {
				++this.totalSpeciesCount;
				if (!this.isSpeciesDiscovered(species, tracker)) {
					continue;
				}
				++this.discoveredSpeciesCount;
			}
			else {
				++this.totalSecretCount;
				if (!this.isSpeciesDiscovered(species, tracker)) {
					continue;
				}
				++this.discoveredSecretCount;
			}
		}
		this.discoveredBranchPercentage = 0.0f;
		this.totalBranchCount = 0;
		this.discoveredBranchCount = 0;
		final Collection<IClassification> discoveredBranches = this.getDiscoveredBranches(tracker);
		final Collection<IClassification> allBranches = this.getAllBranches();
		for (final IClassification branch : allBranches) {
			if (!this.isSecret(branch)) {
				++this.totalBranchCount;
				if (!discoveredBranches.contains(branch)) {
					continue;
				}
				++this.discoveredBranchCount;
			}
			else {
				++this.totalSecretBranchCount;
				if (!discoveredBranches.contains(branch)) {
					continue;
				}
				++this.discoveredSecretBranchCount;
			}
		}
		this.discoveredSpeciesPercentage = this.discoveredSpeciesCount / this.totalSpeciesCount;
		this.discoveredBranchPercentage = this.discoveredBranchCount / this.totalBranchCount;
		final String epithet = this.getEpitome();
		this.onSyncBreedingTracker(tracker);
	}

	public void onSyncBreedingTracker(final IBreedingTracker tracker) {
	}

	public String getEpitome() {
		return this.getEpitome(this.discoveredSpeciesPercentage);
	}

	public final String getDescriptor() {
		return BinnieCore.proxy.localise(this.getSpeciesRoot().getUID() + ".descriptor");
	}

	public final String getIdent() {
		return this.getSpeciesRoot().getUID();
	}

	public final IChromosomeType getChromosome(final int i) {
		for (final IChromosomeType chromosome : this.getSpeciesRoot().getKaryotype()) {
			if (i == chromosome.ordinal()) {
				return chromosome;
			}
		}
		return null;
	}

	public abstract int getColour();

	public String getAlleleName(final IChromosomeType chromosome, final IAllele allele) {
		if (allele instanceof IAlleleBoolean) {
			return ((IAlleleBoolean) allele).getValue() ? Binnie.Language.localise(BinnieCore.instance, "allele.true") : Binnie.Language.localise(BinnieCore.instance, "allele.false");
		}
		if (allele.getName() == "for.gui.maximum") {
			return Binnie.Language.localise(BinnieCore.instance, "allele.fertility.maximum");
		}
		return allele.getName();
	}

	public String getName() {
		return BinnieCore.proxy.localise(this.getSpeciesRoot().getUID() + ".shortName");
	}

	@Override
	public ItemStack getItemStackRepresentitive() {
		final IIndividual first = this.getSpeciesRoot().getIndividualTemplates().get(0);
		return this.getSpeciesRoot().getMemberStack(first, this.getDefaultType());
	}

	@Override
	public String toString() {
		return this.getName();
	}

	public abstract boolean isDNAManipulable(final ItemStack p0);

	public IIndividual getConversion(final ItemStack stack) {
		return null;
	}

	public final IIndividual getDefaultIndividual() {
		return this.getSpeciesRoot().templateAsIndividual(this.getSpeciesRoot().getDefaultTemplate());
	}

	public final int getDefaultType() {
		return this.getActiveTypes()[0];
	}

	public abstract int[] getActiveTypes();

	public abstract void addExtraAlleles(final IChromosomeType p0, final TreeSet<IAllele> p1);

	public ItemStack getConversionStack(final ItemStack stack) {
		return this.getSpeciesRoot().getMemberStack(this.getConversion(stack), this.getDefaultType());
	}

	public final Collection<IChromosomeType> getActiveKaryotype() {
		return Binnie.Genetics.getActiveChromosomes(this.getSpeciesRoot());
	}

	public ItemStack getDefaultMember(final String uid) {
		return this.getSpeciesRoot().getMemberStack(this.getIndividual(uid), this.getDefaultType());
	}

	public IIndividual getIndividual(final String uid) {
		return this.getSpeciesRoot().templateAsIndividual(this.getSpeciesRoot().getTemplate(uid));
	}

	public IGenome getGenome(final String uid) {
		return this.getSpeciesRoot().templateAsGenome(this.getSpeciesRoot().getTemplate(uid));
	}
}
