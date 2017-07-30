package binnie.core.genetics;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.mojang.authlib.GameProfile;

import net.minecraftforge.common.MinecraftForge;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
import forestry.api.genetics.ISpeciesType;

import binnie.Binnie;
import binnie.core.resource.BinnieSprite;
import binnie.core.util.I18N;

public abstract class BreedingSystem implements IItemStackRepresentitive {
	public float discoveredSpeciesPercentage;
	public int totalSpeciesCount;
	public int discoveredSpeciesCount;
	public int totalSecretCount;
	public int discoveredSecretCount;
	public float discoveredBranchPercentage;
	public int totalBranchCount;
	public int discoveredBranchCount;
	protected BinnieSprite iconUndiscovered;
	protected BinnieSprite iconDiscovered;
	List<IAlleleSpecies> allActiveSpecies;
	String currentEpithet;
	private List<IClassification> allBranches;
	private List<IAlleleSpecies> allSpecies;
	private List<IMutation> allMutations;
	private Map<IAlleleSpecies, List<IMutation>> resultantMutations;
	private Map<IAlleleSpecies, List<IMutation>> furtherMutations;
	private Map<IAlleleSpecies, List<IMutation>> allResultantMutations;
	private Map<IAlleleSpecies, List<IMutation>> allFurtherMutations;
	private int totalSecretBranchCount;
	private int discoveredSecretBranchCount;

	public BreedingSystem() {
		this.allBranches = new ArrayList<>();
		this.allActiveSpecies = new ArrayList<>();
		this.allSpecies = new ArrayList<>();
		this.allMutations = new ArrayList<>();
		this.resultantMutations = new HashMap<>();
		this.furtherMutations = new HashMap<>();
		this.allResultantMutations = new HashMap<>();
		this.allFurtherMutations = new HashMap<>();
		Binnie.GENETICS.registerBreedingSystem(this);
		MinecraftForge.EVENT_BUS.register(this);
	}

	public String getChromosomeName(final IChromosomeType chromo) {
		return I18N.localise("binniecore." + this.getSpeciesRoot().getUID() + ".chromosome." + chromo.getName());
	}

	public String getChromosomeShortName(final IChromosomeType chromo) {
		return I18N.localise("binniecore." + this.getSpeciesRoot().getUID() + ".chromosome." + chromo.getName() + ".short");
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
		return I18N.localise("binniecore." + this.getSpeciesRoot().getUID() + ".epitome." + i);
	}

	public abstract ISpeciesRoot getSpeciesRoot();

	public final List<IClassification> getAllBranches() {
		return this.allBranches;
	}

	public final Collection<IAlleleSpecies> getAllSpecies() {
		return this.allActiveSpecies;
	}

	public final Collection<IMutation> getAllMutations() {
		return this.allMutations;
	}

	public void calculateArrays() {
		Collection<IAllele> allAlleles = AlleleManager.alleleRegistry.getRegisteredAlleles().values();
		this.resultantMutations = new HashMap<>();
		this.furtherMutations = new HashMap<>();
		this.allResultantMutations = new HashMap<>();
		this.allFurtherMutations = new HashMap<>();
		this.allActiveSpecies = new ArrayList<>();
		this.allSpecies = new ArrayList<>();
		ISpeciesRoot speciesRoot = getSpeciesRoot();
		for (IAllele allele : allAlleles) {
			String uid = allele.getUID();
			IAllele[] template = speciesRoot.getTemplate(uid);
			if (template != null) {
				IAlleleSpecies species = (IAlleleSpecies) allele;
				this.resultantMutations.put(species, new ArrayList<>());
				this.furtherMutations.put(species, new ArrayList<>());
				this.allResultantMutations.put(species, new ArrayList<>());
				this.allFurtherMutations.put(species, new ArrayList<>());
				this.allSpecies.add(species);
				if (isBlacklisted(allele) || uid.contains("speciesBotAlfheim")) {
					continue;
				}
				this.allActiveSpecies.add((IAlleleSpecies) allele);
			}
		}
		this.allMutations = new ArrayList<>();
		Collection<IClassification> allRegBranches = AlleleManager.alleleRegistry.getRegisteredClassifications().values();
		this.allBranches = new ArrayList<>();
		for (IClassification branch : allRegBranches) {
			IAlleleSpecies[] species = branch.getMemberSpecies();
			if (species.length <= 0) {
				continue;
			}
			IAlleleSpecies firstSpecies = species[0];
			IAllele[] template = speciesRoot.getTemplate(firstSpecies.getUID());
			if (template != null) {
				boolean possible = false;
				for (IAlleleSpecies species2 : branch.getMemberSpecies()) {
					if (allActiveSpecies.contains(species2)) {
						possible = true;
					}
				}
				if (!possible) {
					continue;
				}
				this.allBranches.add(branch);
			}
		}
		List<? extends IMutation> speciesMutations = speciesRoot.getMutations(false);
		if (!speciesMutations.isEmpty()) {
			final Set<IMutation> mutations = new LinkedHashSet<>();
			mutations.addAll(speciesMutations);
			for (final IMutation mutation : mutations) {
				this.allMutations.add(mutation);
				final Set<IAlleleSpecies> participatingSpecies = new LinkedHashSet<>();
				participatingSpecies.add(mutation.getAllele0());
				participatingSpecies.add(mutation.getAllele1());
				for (final IAlleleSpecies species : participatingSpecies) {
					this.allFurtherMutations.get(species).add(mutation);
					if (this.allActiveSpecies.contains(species)) {
						this.furtherMutations.get(species).add(mutation);
					}
				}
				IAllele[] template = mutation.getTemplate();
				IAlleleSpecies speciesAllele = (IAlleleSpecies) template[0];
				if (this.resultantMutations.containsKey(speciesAllele)) {
					this.allResultantMutations.get(speciesAllele).add(mutation);
					this.resultantMutations.get(speciesAllele).add(mutation);
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
		return tracker.isDiscovered(mutation);
	}

	public final boolean isSpeciesDiscovered(final IAlleleSpecies species, final World world, final GameProfile name) {
		return this.isSpeciesDiscovered(species, this.getSpeciesRoot().getBreedingTracker(world, name));
	}

	public final boolean isSpeciesDiscovered(final IAlleleSpecies species, final IBreedingTracker tracker) {
		return tracker.isDiscovered(species);
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
		final List<IClassification> branches = new ArrayList<>();
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
		final List<IClassification> branches = new ArrayList<>();
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
		final List<IAlleleSpecies> speciesList = new ArrayList<>();
		for (final IAlleleSpecies species : this.getAllSpecies()) {
			if (this.isSpeciesDiscovered(species, world, player)) {
				speciesList.add(species);
			}
		}
		return speciesList;
	}

	public final Collection<IAlleleSpecies> getDiscoveredSpecies(final IBreedingTracker tracker) {
		final List<IAlleleSpecies> speciesList = new ArrayList<>();
		for (final IAlleleSpecies species : this.getAllSpecies()) {
			if (this.isSpeciesDiscovered(species, tracker)) {
				speciesList.add(species);
			}
		}
		return speciesList;
	}

	public final List<IMutation> getDiscoveredMutations(final World world, final GameProfile player) {
		final List<IMutation> speciesList = new ArrayList<>();
		for (final IMutation species : this.getAllMutations()) {
			if (this.isMutationDiscovered(species, world, player)) {
				speciesList.add(species);
			}
		}
		return speciesList;
	}

	public final List<IMutation> getDiscoveredMutations(final IBreedingTracker tracker) {
		final List<IMutation> speciesList = new ArrayList<>();
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

	@SideOnly(Side.CLIENT)
	public TextureAtlasSprite getUndiscoveredIcon() {
		return this.iconUndiscovered.getSprite();
	}

	@SideOnly(Side.CLIENT)
	public TextureAtlasSprite getDiscoveredIcon() {
		return this.iconDiscovered.getSprite();
	}

	public abstract float getChance(IMutation mutation, EntityPlayer player, IAlleleSpecies firstSpecies, IAlleleSpecies secondSpecies);

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
		if (allActiveSpecies.isEmpty()) {
			calculateArrays();
		}
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
			} else {
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
			} else {
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
		return I18N.localise("binniecore." + this.getSpeciesRoot().getUID() + ".descriptor");
	}

	public final String getIdent() {
		return this.getSpeciesRoot().getUID();
	}

	@Nullable
	public final IChromosomeType getChromosome(int index) {
		for (IChromosomeType chromosome : this.getSpeciesRoot().getKaryotype()) {
			if (index == chromosome.ordinal()) {
				return chromosome;
			}
		}
		return null;
	}

	public abstract int getColour();

	public String getAlleleName(final IChromosomeType chromosome, final IAllele allele) {
		if (allele instanceof IAlleleBoolean) {
			IAlleleBoolean alleleBoolean = (IAlleleBoolean) allele;
			return alleleBoolean.getValue() ? I18N.localise("binniecore.allele.true") : I18N.localise("binniecore.allele.false");
		}
		if (Objects.equals(allele.getAlleleName(), "for.gui.maximum")) {
			return I18N.localise("binniecore.allele.fertility.maximum");
		}
		return allele.getAlleleName();
	}

	public String getName() {
		return I18N.localise("binniecore." + this.getSpeciesRoot().getUID() + ".shortName");
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

	public abstract boolean isDNAManipulable(ItemStack stack);

	public abstract boolean isDNAManipulable(ISpeciesType type);

	@Nullable
	public IIndividual getConversion(final ItemStack stack) {
		return null;
	}

	public final IIndividual getDefaultIndividual() {
		return this.getSpeciesRoot().templateAsIndividual(this.getSpeciesRoot().getDefaultTemplate());
	}

	public final ISpeciesType getDefaultType() {
		return this.getActiveTypes()[0];
	}

	public abstract ISpeciesType[] getActiveTypes();

	public abstract void addExtraAlleles(final IChromosomeType p0, final TreeSet<IAllele> p1);

	public ItemStack getConversionStack(final ItemStack stack) {
		IIndividual conversion = this.getConversion(stack);
		if (conversion == null) {
			return ItemStack.EMPTY;
		}
		return this.getSpeciesRoot().getMemberStack(conversion, this.getDefaultType());
	}

	public final Collection<IChromosomeType> getActiveKaryotype() {
		return Binnie.GENETICS.getActiveChromosomes(this.getSpeciesRoot());
	}

	public ItemStack getDefaultMember(final String uid) {
		IIndividual individual = this.getIndividual(uid);
		if (individual == null) {
			return ItemStack.EMPTY;
		}
		return this.getSpeciesRoot().getMemberStack(individual, this.getDefaultType());
	}

	@Nullable
	public IIndividual getIndividual(String uid) {
		IAllele[] template = this.getSpeciesRoot().getTemplate(uid);
		if (template == null) {
			return null;
		}
		return this.getSpeciesRoot().templateAsIndividual(template);
	}

	@Nullable
	public IGenome getGenome(String uid) {
		IAllele[] template = this.getSpeciesRoot().getTemplate(uid);
		if (template == null) {
			return null;
		}
		return this.getSpeciesRoot().templateAsGenome(template);
	}
}
