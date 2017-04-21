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

	public String getChromosomeName(IChromosomeType chromo) {
		return BinnieCore.proxy.localise(this.getSpeciesRoot().getUID() + ".chromosome." + chromo.getName());
	}

	public String getChromosomeShortName(IChromosomeType chromo) {
		return BinnieCore.proxy.localise(this.getSpeciesRoot().getUID() + ".chromosome." + chromo.getName() + ".short");
	}

	public String getEpitome(float discoveredPercentage) {
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

	public List<IClassification> getAllBranches() {
		return allBranches;
	}

	public Collection<IAlleleSpecies> getAllSpecies() {
		return allActiveSpecies;
	}

	public Collection<IMutation> getAllMutations() {
		return allMutations;
	}

	public void calculateArrays() {
		Collection<IAllele> allAlleles = AlleleManager.alleleRegistry.getRegisteredAlleles().values();
		resultantMutations = new HashMap<IAlleleSpecies, List<IMutation>>();
		furtherMutations = new HashMap<IAlleleSpecies, List<IMutation>>();
		allResultantMutations = new HashMap<IAlleleSpecies, List<IMutation>>();
		allFurtherMutations = new HashMap<IAlleleSpecies, List<IMutation>>();
		allActiveSpecies = new ArrayList<IAlleleSpecies>();
		allSpecies = new ArrayList<IAlleleSpecies>();
		for (IAllele species : allAlleles) {
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
		Collection<IClassification> allRegBranches = AlleleManager.alleleRegistry.getRegisteredClassifications().values();
		allBranches = new ArrayList<IClassification>();
		for (IClassification branch : allRegBranches) {
			if (branch.getMemberSpecies().length > 0 && this.getSpeciesRoot().getTemplate(branch.getMemberSpecies()[0].getUID()) != null) {
				boolean possible = false;
				for (IAlleleSpecies species2 : branch.getMemberSpecies()) {
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
			Set<IMutation> mutations = new LinkedHashSet<IMutation>();
			mutations.addAll(this.getSpeciesRoot().getMutations(false));
			if (this == Binnie.Genetics.beeBreedingSystem) {
				mutations.addAll(ExtraBeeMutation.mutations);
			}
			for (IMutation mutation : mutations) {
				this.allMutations.add(mutation);
				Set<IAlleleSpecies> participatingSpecies = new LinkedHashSet<IAlleleSpecies>();
				if (mutation.getAllele0() instanceof IAlleleSpecies) {
					participatingSpecies.add(mutation.getAllele0());
				}
				if (mutation.getAllele1() instanceof IAlleleSpecies) {
					participatingSpecies.add(mutation.getAllele1());
				}
				for (IAlleleSpecies species3 : participatingSpecies) {
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

	public boolean isBlacklisted(IAllele allele) {
		return AlleleManager.alleleRegistry.isBlacklisted(allele.getUID());
	}

	public List<IMutation> getResultantMutations(IAlleleSpecies species, boolean includeInactive) {
		if (this.resultantMutations.isEmpty()) {
			this.calculateArrays();
		}
		return includeInactive ? this.allResultantMutations.get(species) : this.resultantMutations.get(species);
	}

	public List<IMutation> getResultantMutations(IAlleleSpecies species) {
		if (this.resultantMutations.isEmpty()) {
			this.calculateArrays();
		}
		return this.resultantMutations.get(species);
	}

	public List<IMutation> getFurtherMutations(IAlleleSpecies species, boolean includeInactive) {
		if (this.furtherMutations.isEmpty()) {
			this.calculateArrays();
		}
		return includeInactive ? this.allFurtherMutations.get(species) : this.furtherMutations.get(species);
	}

	public List<IMutation> getFurtherMutations(IAlleleSpecies species) {
		if (this.furtherMutations.isEmpty()) {
			this.calculateArrays();
		}
		return this.furtherMutations.get(species);
	}

	public boolean isMutationDiscovered(IMutation mutation, World world, GameProfile name) {
		return this.isMutationDiscovered(mutation, this.getSpeciesRoot().getBreedingTracker(world, name));
	}

	public boolean isMutationDiscovered(IMutation mutation, IBreedingTracker tracker) {
		return tracker == null || tracker.isDiscovered(mutation);
	}

	public boolean isSpeciesDiscovered(IAlleleSpecies species, World world, GameProfile name) {
		return this.isSpeciesDiscovered(species, this.getSpeciesRoot().getBreedingTracker(world, name));
	}

	public boolean isSpeciesDiscovered(IAlleleSpecies species, IBreedingTracker tracker) {
		return tracker == null || tracker.isDiscovered(species);
	}

	public boolean isSecret(IAlleleSpecies species) {
		return !species.isCounted();
	}

	public boolean isSecret(IClassification branch) {
		for (IAlleleSpecies species : branch.getMemberSpecies()) {
			if (!this.isSecret(species)) {
				return false;
			}
		}
		return true;
	}

	public Collection<IClassification> getDiscoveredBranches(World world, GameProfile player) {
		List<IClassification> branches = new ArrayList<IClassification>();
		for (IClassification branch : this.getAllBranches()) {
			boolean discovered = false;
			for (IAlleleSpecies species : branch.getMemberSpecies()) {
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

	public Collection<IClassification> getDiscoveredBranches(IBreedingTracker tracker) {
		List<IClassification> branches = new ArrayList<IClassification>();
		for (IClassification branch : this.getAllBranches()) {
			boolean discovered = false;
			for (IAlleleSpecies species : branch.getMemberSpecies()) {
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

	public Collection<IAlleleSpecies> getDiscoveredSpecies(World world, GameProfile player) {
		List<IAlleleSpecies> speciesList = new ArrayList<IAlleleSpecies>();
		for (IAlleleSpecies species : this.getAllSpecies()) {
			if (this.isSpeciesDiscovered(species, world, player)) {
				speciesList.add(species);
			}
		}
		return speciesList;
	}

	public Collection<IAlleleSpecies> getDiscoveredSpecies(IBreedingTracker tracker) {
		List<IAlleleSpecies> speciesList = new ArrayList<IAlleleSpecies>();
		for (IAlleleSpecies species : this.getAllSpecies()) {
			if (this.isSpeciesDiscovered(species, tracker)) {
				speciesList.add(species);
			}
		}
		return speciesList;
	}

	public List<IMutation> getDiscoveredMutations(World world, GameProfile player) {
		List<IMutation> speciesList = new ArrayList<IMutation>();
		for (IMutation species : this.getAllMutations()) {
			if (this.isMutationDiscovered(species, world, player)) {
				speciesList.add(species);
			}
		}
		return speciesList;
	}

	public List<IMutation> getDiscoveredMutations(IBreedingTracker tracker) {
		List<IMutation> speciesList = new ArrayList<IMutation>();
		for (IMutation species : this.getAllMutations()) {
			if (this.isMutationDiscovered(species, tracker)) {
				speciesList.add(species);
			}
		}
		return speciesList;
	}

	public int getDiscoveredBranchMembers(IClassification branch, IBreedingTracker tracker) {
		int discoveredSpecies = 0;
		for (IAlleleSpecies species : branch.getMemberSpecies()) {
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

	public abstract float getChance(IMutation p0, EntityPlayer p1, IAllele p2, IAllele p3);

	public abstract Class<? extends IBreedingTracker> getTrackerClass();

	@SubscribeEvent
	public void onSyncBreedingTracker(ForestryEvent.SyncedBreedingTracker event) {
		IBreedingTracker tracker = event.tracker;
		if (!this.getTrackerClass().isInstance(tracker)) {
			return;
		}
		this.syncTracker(tracker);
	}

	public void syncTracker(IBreedingTracker tracker) {
		this.discoveredSpeciesPercentage = 0.0f;
		this.totalSpeciesCount = 0;
		this.discoveredSpeciesCount = 0;
		this.totalSecretCount = 0;
		this.discoveredSecretCount = 0;
		Collection<IAlleleSpecies> discoveredSpecies = this.getDiscoveredSpecies(tracker);
		Collection<IAlleleSpecies> allSpecies = this.getAllSpecies();
		for (IAlleleSpecies species : allSpecies) {
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
		Collection<IClassification> discoveredBranches = this.getDiscoveredBranches(tracker);
		Collection<IClassification> allBranches = this.getAllBranches();
		for (IClassification branch : allBranches) {
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
		String epithet = this.getEpitome();
		this.onSyncBreedingTracker(tracker);
	}

	public void onSyncBreedingTracker(IBreedingTracker tracker) {
	}

	public String getEpitome() {
		return this.getEpitome(this.discoveredSpeciesPercentage);
	}

	public String getDescriptor() {
		return BinnieCore.proxy.localise(this.getSpeciesRoot().getUID() + ".descriptor");
	}

	public String getIdent() {
		return this.getSpeciesRoot().getUID();
	}

	public IChromosomeType getChromosome(int i) {
		for (IChromosomeType chromosome : this.getSpeciesRoot().getKaryotype()) {
			if (i == chromosome.ordinal()) {
				return chromosome;
			}
		}
		return null;
	}

	public abstract int getColour();

	public String getAlleleName(IChromosomeType chromosome, IAllele allele) {
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
		IIndividual first = this.getSpeciesRoot().getIndividualTemplates().get(0);
		return this.getSpeciesRoot().getMemberStack(first, this.getDefaultType());
	}

	@Override
	public String toString() {
		return this.getName();
	}

	public abstract boolean isDNAManipulable(ItemStack p0);

	public IIndividual getConversion(ItemStack stack) {
		return null;
	}

	public IIndividual getDefaultIndividual() {
		return this.getSpeciesRoot().templateAsIndividual(this.getSpeciesRoot().getDefaultTemplate());
	}

	public int getDefaultType() {
		return this.getActiveTypes()[0];
	}

	public abstract int[] getActiveTypes();

	public abstract void addExtraAlleles(IChromosomeType p0, TreeSet<IAllele> p1);

	public ItemStack getConversionStack(ItemStack stack) {
		return this.getSpeciesRoot().getMemberStack(this.getConversion(stack), this.getDefaultType());
	}

	public Collection<IChromosomeType> getActiveKaryotype() {
		return Binnie.Genetics.getActiveChromosomes(this.getSpeciesRoot());
	}

	public ItemStack getDefaultMember(String uid) {
		return this.getSpeciesRoot().getMemberStack(this.getIndividual(uid), this.getDefaultType());
	}

	public IIndividual getIndividual(String uid) {
		return this.getSpeciesRoot().templateAsIndividual(this.getSpeciesRoot().getTemplate(uid));
	}

	public IGenome getGenome(String uid) {
		return this.getSpeciesRoot().templateAsGenome(this.getSpeciesRoot().getTemplate(uid));
	}
}
