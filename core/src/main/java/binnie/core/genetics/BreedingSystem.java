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

import binnie.core.api.genetics.IBreedingSystem;
import binnie.core.util.collect.ListMultiMap;
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
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.IMutation;
import forestry.api.genetics.ISpeciesRoot;
import forestry.api.genetics.ISpeciesType;

import binnie.core.Binnie;
import binnie.core.resource.BinnieSprite;
import binnie.core.util.I18N;

public abstract class BreedingSystem implements IBreedingSystem, IItemStackRepresentitive {
	private float discoveredSpeciesPercentage;
	private int totalSpeciesCount;
	private int discoveredSpeciesCount;
	private int totalSecretCount;
	private int discoveredSecretCount;
	private float discoveredBranchPercentage;
	private int totalBranchCount;
	private int discoveredBranchCount;
	protected BinnieSprite iconUndiscovered;
	protected BinnieSprite iconDiscovered;
	protected List<IAlleleSpecies> allActiveSpecies;
	protected String currentEpithet;
	private List<IClassification> allBranches;
	private List<IAlleleSpecies> allSpecies;
	private List<IMutation> allMutations;
	private final ListMultiMap<IAlleleSpecies, IMutation> resultantMutations;
	private final ListMultiMap<IAlleleSpecies, IMutation> furtherMutations;
	private final ListMultiMap<IAlleleSpecies, IMutation> allResultantMutations;
	private final ListMultiMap<IAlleleSpecies, IMutation> allFurtherMutations;
	private int totalSecretBranchCount;
	private int discoveredSecretBranchCount;

	public BreedingSystem() {
		this.allBranches = new ArrayList<>();
		this.allActiveSpecies = new ArrayList<>();
		this.allSpecies = new ArrayList<>();
		this.allMutations = new ArrayList<>();
		this.resultantMutations = new ListMultiMap<>();
		this.furtherMutations = new ListMultiMap<>();
		this.allResultantMutations = new ListMultiMap<>();
		this.allFurtherMutations = new ListMultiMap<>();
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public String getChromosomeName(final IChromosomeType chromo) {
		return I18N.localise("binniecore." + this.getSpeciesRoot().getUID() + ".chromosome." + chromo.getName());
	}

	@Override
	public String getChromosomeShortName(final IChromosomeType chromo) {
		return I18N.localise("binniecore." + this.getSpeciesRoot().getUID() + ".chromosome." + chromo.getName() + ".short");
	}

	@Override
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

	@Override
	public abstract ISpeciesRoot getSpeciesRoot();

	@Override
	public final List<IClassification> getAllBranches() {
		return this.allBranches;
	}

	@Override
	public final Collection<IAlleleSpecies> getAllSpecies() {
		return this.allActiveSpecies;
	}

	@Override
	public final Collection<IMutation> getAllMutations() {
		return this.allMutations;
	}

	@Override
	public void calculateArrays() {
		ISpeciesRoot speciesRoot = getSpeciesRoot();
		calculateAlleles(speciesRoot);
		calculateBranches(speciesRoot);
		calculateMutations(speciesRoot);
	}

	@Override
	public void calculateAlleles(ISpeciesRoot speciesRoot) {
		this.allSpecies = new ArrayList<>();
		this.allActiveSpecies = new ArrayList<>();
		this.resultantMutations.clear();
		this.furtherMutations.clear();
		this.allResultantMutations.clear();
		this.allFurtherMutations.clear();
		Collection<IAllele> allAlleles = AlleleManager.alleleRegistry.getRegisteredAlleles().values();
		for (IAllele allele : allAlleles) {
			String uid = allele.getUID();
			IAllele[] template = speciesRoot.getTemplate(uid);
			if (template != null) {
				IAlleleSpecies species = (IAlleleSpecies) allele;
				this.allSpecies.add(species);
				if (isBlacklisted(allele) || uid.contains("speciesBotAlfheim")) {
					continue;
				}
				this.allActiveSpecies.add((IAlleleSpecies) allele);
			}
		}
	}

	@Override
	public void calculateBranches(ISpeciesRoot speciesRoot) {
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
	}

	@Override
	public void calculateMutations(ISpeciesRoot speciesRoot) {
		this.allMutations = new ArrayList<>();
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
					this.allFurtherMutations.put(species, mutation);
					if (this.allActiveSpecies.contains(species)) {
						this.furtherMutations.put(species, mutation);
					}
				}
				IAllele[] template = mutation.getTemplate();
				IAlleleSpecies speciesAllele = (IAlleleSpecies) template[0];
				this.allResultantMutations.put(speciesAllele, mutation);
				this.resultantMutations.put(speciesAllele, mutation);
			}
		}
	}

	@Override
	public final boolean isBlacklisted(final IAllele allele) {
		return AlleleManager.alleleRegistry.isBlacklisted(allele.getUID());
	}

	@Override
	public final List<IMutation> getResultantMutations(final IAlleleSpecies species) {
		if (this.resultantMutations.isEmpty()) {
			this.calculateArrays();
		}
		return this.resultantMutations.get(species);
	}

	@Override
	public final List<IMutation> getFurtherMutations(final IAlleleSpecies species) {
		if (this.furtherMutations.isEmpty()) {
			this.calculateArrays();
		}
		return this.furtherMutations.get(species);
	}

	@Override
	public final boolean isMutationDiscovered(final IMutation mutation, final World world, final GameProfile name) {
		return this.isMutationDiscovered(mutation, this.getSpeciesRoot().getBreedingTracker(world, name));
	}

	@Override
	public final boolean isMutationDiscovered(final IMutation mutation, final IBreedingTracker tracker) {
		return tracker.isDiscovered(mutation);
	}

	@Override
	public final boolean isSpeciesDiscovered(final IAlleleSpecies species, final World world, final GameProfile name) {
		return this.isSpeciesDiscovered(species, this.getSpeciesRoot().getBreedingTracker(world, name));
	}

	@Override
	public final boolean isSpeciesDiscovered(final IAlleleSpecies species, final IBreedingTracker tracker) {
		return tracker.isDiscovered(species);
	}

	@Override
	public final boolean isSecret(final IAlleleSpecies species) {
		return !species.isCounted();
	}

	@Override
	public final boolean isSecret(final IClassification branch) {
		for (final IAlleleSpecies species : branch.getMemberSpecies()) {
			if (!this.isSecret(species)) {
				return false;
			}
		}
		return true;
	}

	@Override
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

	@Override
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

	@Override
	public final Collection<IAlleleSpecies> getDiscoveredSpecies(final World world, final GameProfile player) {
		final List<IAlleleSpecies> speciesList = new ArrayList<>();
		for (final IAlleleSpecies species : this.getAllSpecies()) {
			if (this.isSpeciesDiscovered(species, world, player)) {
				speciesList.add(species);
			}
		}
		return speciesList;
	}

	@Override
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

	@Override
	@SideOnly(Side.CLIENT)
	public TextureAtlasSprite getUndiscoveredIcon() {
		return this.iconUndiscovered.getSprite();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public TextureAtlasSprite getDiscoveredIcon() {
		return this.iconDiscovered.getSprite();
	}

	@Override
	public abstract float getChance(IMutation mutation, EntityPlayer player, IAlleleSpecies firstSpecies, IAlleleSpecies secondSpecies);

	@Override
	@SubscribeEvent
	public final void onSyncBreedingTracker(final ForestryEvent.SyncedBreedingTracker event) {
		final IBreedingTracker tracker = event.tracker;
		if (!this.getTrackerClass().isInstance(tracker)) {
			return;
		}
		this.syncTracker(tracker);
	}

	@Override
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

	@Override
	public void onSyncBreedingTracker(final IBreedingTracker tracker) {
	}

	@Override
	public String getEpitome() {
		return this.getEpitome(this.discoveredSpeciesPercentage);
	}

	@Override
	public final String getDescriptor() {
		return I18N.localise("binniecore." + this.getSpeciesRoot().getUID() + ".descriptor");
	}

	@Override
	public final String getIdent() {
		return this.getSpeciesRoot().getUID();
	}

	@Override
	public abstract int getColour();

	@Override
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

	@Override
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

	@Override
	@Nullable
	public IIndividual getConversion(final ItemStack stack) {
		return null;
	}

	@Override
	public final IIndividual getDefaultIndividual() {
		return this.getSpeciesRoot().templateAsIndividual(this.getSpeciesRoot().getDefaultTemplate());
	}

	@Override
	public final ISpeciesType getDefaultType() {
		return this.getActiveTypes()[0];
	}

	@Override
	public abstract void addExtraAlleles(final IChromosomeType p0, final TreeSet<IAllele> p1);

	@Override
	public ItemStack getConversionStack(final ItemStack stack) {
		IIndividual conversion = this.getConversion(stack);
		if (conversion == null) {
			return ItemStack.EMPTY;
		}
		return this.getSpeciesRoot().getMemberStack(conversion, this.getDefaultType());
	}

	@Override
	public final Collection<IChromosomeType> getActiveKaryotype() {
		return Binnie.GENETICS.getActiveChromosomes(this.getSpeciesRoot());
	}

	@Override
	public ItemStack getDefaultMember(final String uid) {
		IIndividual individual = this.getIndividual(uid);
		if (individual == null) {
			return ItemStack.EMPTY;
		}
		return this.getSpeciesRoot().getMemberStack(individual, this.getDefaultType());
	}

	@Override
	@Nullable
	public IIndividual getIndividual(String uid) {
		IAllele[] template = this.getSpeciesRoot().getTemplate(uid);
		if (template == null) {
			return null;
		}
		return this.getSpeciesRoot().templateAsIndividual(template);
	}

	@Override
	public int getDiscoveredSpeciesCount() {
		return discoveredSpeciesCount;
	}

	@Override
	public int getTotalSpeciesCount() {
		return totalSpeciesCount;
	}

	@Override
	public float getDiscoveredSpeciesPercentage() {
		return discoveredSpeciesPercentage;
	}

	@Override
	public int getTotalSecretCount() {
		return totalSecretCount;
	}

	@Override
	public int getDiscoveredSecretCount() {
		return discoveredSecretCount;
	}

	@Override
	public float getDiscoveredBranchPercentage() {
		return discoveredBranchPercentage;
	}

	@Override
	public int getTotalBranchCount() {
		return totalBranchCount;
	}

	@Override
	public int getDiscoveredBranchCount() {
		return discoveredBranchCount;
	}
}
