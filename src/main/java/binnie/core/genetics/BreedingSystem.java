package binnie.core.genetics;

import binnie.Binnie;
import binnie.core.resource.BinnieIcon;
import binnie.core.util.I18N;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public abstract class BreedingSystem implements IItemStackRepresentative {
    public float discoveredSpeciesPercentage;
    public int totalSpeciesCount;
    public int discoveredSpeciesCount;
    public int totalSecretCount;
    public int discoveredSecretCount;
    public float discoveredBranchPercentage;
    public int totalBranchCount;
    public int discoveredBranchCount;

    protected BinnieIcon iconUndiscovered;
    protected BinnieIcon iconDiscovered;
    protected List<IAlleleSpecies> allActiveSpecies = new ArrayList<>();

    private List<IClassification> allBranches = new ArrayList<>();
    private List<IMutation> allMutations = new ArrayList<>();
    private Map<IAlleleSpecies, List<IMutation>> resultantMutations = new HashMap<>();
    private Map<IAlleleSpecies, List<IMutation>> furtherMutations = new HashMap<>();

    public BreedingSystem() {
        Binnie.Genetics.registerBreedingSystem(this);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public String getChromosomeName(IChromosomeType chromo) {
        return I18N.localise("binniecore." + getSpeciesRoot().getUID() + ".chromosome." + chromo.getName());
    }

    public String getChromosomeShortName(IChromosomeType chromo) {
        return I18N.localise("binniecore." + getSpeciesRoot().getUID() + ".chromosome." + chromo.getName() + ".short");
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
        return I18N.localise("binniecore." + getSpeciesRoot().getUID() + ".epitome." + i);
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
        Collection<IAllele> allAlleles =
                AlleleManager.alleleRegistry.getRegisteredAlleles().values();
        resultantMutations = new HashMap<>();
        furtherMutations = new HashMap<>();
        allActiveSpecies = new ArrayList<>();

        for (IAllele species : allAlleles) {
            if (getSpeciesRoot().getTemplate(species.getUID()) != null) {
                resultantMutations.put((IAlleleSpecies) species, new ArrayList<>());
                furtherMutations.put((IAlleleSpecies) species, new ArrayList<>());

                if (isBlacklisted(species) || species.getUID().contains("speciesBotAlfheim")) {
                    continue;
                }
                allActiveSpecies.add((IAlleleSpecies) species);
            }
        }

        allMutations = new ArrayList<>();
        Collection<IClassification> allRegBranches =
                AlleleManager.alleleRegistry.getRegisteredClassifications().values();
        allBranches = new ArrayList<>();
        for (IClassification branch : allRegBranches) {
            if (branch.getMemberSpecies().length > 0
                    && getSpeciesRoot().getTemplate(branch.getMemberSpecies()[0].getUID()) != null) {
                boolean possible = false;
                for (IAlleleSpecies species2 : branch.getMemberSpecies()) {
                    if (allActiveSpecies.contains(species2)) {
                        possible = true;
                    }
                }
                if (!possible) {
                    continue;
                }
                allBranches.add(branch);
            }
        }

        if (getSpeciesRoot().getMutations(false) != null) {
            Set<IMutation> mutations = new LinkedHashSet<>();
            mutations.addAll(getSpeciesRoot().getMutations(false));
            // TODO Why is this necessary?
            if (this == Binnie.Genetics.beeBreedingSystem) {
                mutations.addAll(ExtraBeeMutation.mutations);
            }

            for (IMutation mutation : mutations) {
                allMutations.add(mutation);
                Set<IAlleleSpecies> participatingSpecies = new LinkedHashSet<>();
                if (mutation.getAllele0() != null) {
                    participatingSpecies.add(mutation.getAllele0());
                }
                if (mutation.getAllele1() != null) {
                    participatingSpecies.add(mutation.getAllele1());
                }

                for (IAlleleSpecies species3 : participatingSpecies) {
                    if (allActiveSpecies.contains(species3)) {
                        furtherMutations.get(species3).add(mutation);
                    }
                }
                if (resultantMutations.containsKey(mutation.getTemplate()[0])) {
                    resultantMutations.get(mutation.getTemplate()[0]).add(mutation);
                }
            }
        }
    }

    public boolean isBlacklisted(IAllele allele) {
        return AlleleManager.alleleRegistry.isBlacklisted(allele.getUID());
    }

    public List<IMutation> getResultantMutations(IAlleleSpecies species) {
        if (resultantMutations.isEmpty()) {
            calculateArrays();
        }
        return resultantMutations.get(species);
    }

    public List<IMutation> getFurtherMutations(IAlleleSpecies species) {
        if (furtherMutations.isEmpty()) {
            calculateArrays();
        }
        return furtherMutations.get(species);
    }

    public boolean isMutationDiscovered(IMutation mutation, World world, GameProfile name) {
        return isMutationDiscovered(mutation, getSpeciesRoot().getBreedingTracker(world, name));
    }

    public boolean isMutationDiscovered(IMutation mutation, IBreedingTracker tracker) {
        return tracker == null || tracker.isDiscovered(mutation);
    }

    public boolean isSpeciesDiscovered(IAlleleSpecies species, World world, GameProfile name) {
        return isSpeciesDiscovered(species, getSpeciesRoot().getBreedingTracker(world, name));
    }

    public boolean isSpeciesDiscovered(IAlleleSpecies species, IBreedingTracker tracker) {
        return tracker == null || tracker.isDiscovered(species);
    }

    public boolean isSecret(IAlleleSpecies species) {
        return !species.isCounted();
    }

    public boolean isSecret(IClassification branch) {
        for (IAlleleSpecies species : branch.getMemberSpecies()) {
            if (!isSecret(species)) {
                return false;
            }
        }
        return true;
    }

    public Collection<IClassification> getDiscoveredBranches(World world, GameProfile player) {
        List<IClassification> branches = new ArrayList<>();
        for (IClassification branch : getAllBranches()) {
            boolean discovered = false;
            for (IAlleleSpecies species : branch.getMemberSpecies()) {
                if (isSpeciesDiscovered(species, world, player)) {
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
        List<IClassification> branches = new ArrayList<>();
        for (IClassification branch : getAllBranches()) {
            boolean discovered = false;
            for (IAlleleSpecies species : branch.getMemberSpecies()) {
                if (isSpeciesDiscovered(species, tracker)) {
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
        List<IAlleleSpecies> speciesList = new ArrayList<>();
        for (IAlleleSpecies species : getAllSpecies()) {
            if (isSpeciesDiscovered(species, world, player)) {
                speciesList.add(species);
            }
        }
        return speciesList;
    }

    public Collection<IAlleleSpecies> getDiscoveredSpecies(IBreedingTracker tracker) {
        List<IAlleleSpecies> speciesList = new ArrayList<>();
        for (IAlleleSpecies species : getAllSpecies()) {
            if (isSpeciesDiscovered(species, tracker)) {
                speciesList.add(species);
            }
        }
        return speciesList;
    }

    public List<IMutation> getDiscoveredMutations(World world, GameProfile player) {
        List<IMutation> speciesList = new ArrayList<>();
        for (IMutation species : getAllMutations()) {
            if (isMutationDiscovered(species, world, player)) {
                speciesList.add(species);
            }
        }
        return speciesList;
    }

    public IIcon getUndiscoveredIcon() {
        return iconUndiscovered.getIcon();
    }

    public IIcon getDiscoveredIcon() {
        return iconDiscovered.getIcon();
    }

    public abstract float getChance(IMutation mutation, EntityPlayer player, IAllele allele1, IAllele allele2);

    public abstract Class<? extends IBreedingTracker> getTrackerClass();

    @SubscribeEvent
    public void onSyncBreedingTracker(ForestryEvent.SyncedBreedingTracker event) {
        IBreedingTracker tracker = event.tracker;
        if (!getTrackerClass().isInstance(tracker)) {
            return;
        }
        syncTracker(tracker);
    }

    public void syncTracker(IBreedingTracker tracker) {
        discoveredSpeciesPercentage = 0.0f;
        totalSpeciesCount = 0;
        discoveredSpeciesCount = 0;
        totalSecretCount = 0;
        discoveredSecretCount = 0;
        Collection<IAlleleSpecies> allSpecies = getAllSpecies();

        for (IAlleleSpecies species : allSpecies) {
            if (!isSecret(species)) {
                totalSpeciesCount++;
                if (!isSpeciesDiscovered(species, tracker)) {
                    continue;
                }
                discoveredSpeciesCount++;
            } else {
                totalSecretCount++;
                if (!isSpeciesDiscovered(species, tracker)) {
                    continue;
                }
                discoveredSecretCount++;
            }
        }

        discoveredBranchPercentage = 0.0f;
        totalBranchCount = 0;
        discoveredBranchCount = 0;
        Collection<IClassification> discoveredBranches = getDiscoveredBranches(tracker);
        Collection<IClassification> allBranches = getAllBranches();

        for (IClassification branch : allBranches) {
            if (!isSecret(branch)) {
                totalBranchCount++;
                if (!discoveredBranches.contains(branch)) {
                    continue;
                }
                discoveredBranchCount++;
            }
        }

        discoveredSpeciesPercentage = discoveredSpeciesCount / totalSpeciesCount;
        discoveredBranchPercentage = discoveredBranchCount / totalBranchCount;
        onSyncBreedingTracker(tracker);
    }

    public void onSyncBreedingTracker(IBreedingTracker tracker) {
        // ignored
    }

    public String getEpitome() {
        return getEpitome(discoveredSpeciesPercentage);
    }

    public String getDescriptor() {
        return I18N.localise("binniecore." + getSpeciesRoot().getUID() + ".descriptor");
    }

    public String getIdent() {
        return getSpeciesRoot().getUID();
    }

    public IChromosomeType getChromosome(int i) {
        for (IChromosomeType chromosome : getSpeciesRoot().getKaryotype()) {
            if (i == chromosome.ordinal()) {
                return chromosome;
            }
        }
        return null;
    }

    public abstract int getColor();

    public String getAlleleName(IChromosomeType chromosome, IAllele allele) {
        if (allele instanceof IAlleleBoolean) {
            return ((IAlleleBoolean) allele).getValue()
                    ? I18N.localise("binniecore.allele.true")
                    : I18N.localise("binniecore.allele.false");
        }
        if (allele.getName().equals("for.gui.maximum")) {
            return I18N.localise("binniecore.allele.fertility.maximum");
        }
        return allele.getName();
    }

    public String getName() {
        return I18N.localise("binniecore." + getSpeciesRoot().getUID() + ".shortName");
    }

    @Override
    public ItemStack getItemStackRepresentative() {
        ISpeciesRoot speciesRoot = getSpeciesRoot();
        IIndividual first = speciesRoot.getIndividualTemplates().get(0);
        return speciesRoot.getMemberStack(first, getDefaultType());
    }

    @Override
    public String toString() {
        return getName();
    }

    public abstract boolean isDNAManipulable(ItemStack member);

    public IIndividual getConversion(ItemStack stack) {
        return null;
    }

    public IIndividual getDefaultIndividual() {
        return getSpeciesRoot().templateAsIndividual(getSpeciesRoot().getDefaultTemplate());
    }

    public int getDefaultType() {
        return getActiveTypes()[0];
    }

    public abstract int[] getActiveTypes();

    public abstract void addExtraAlleles(IChromosomeType chromosome, TreeSet<IAllele> alleles);

    public ItemStack getConversionStack(ItemStack stack) {
        return getSpeciesRoot().getMemberStack(getConversion(stack), getDefaultType());
    }

    public Collection<IChromosomeType> getActiveKaryotype() {
        return Binnie.Genetics.getActiveChromosomes(getSpeciesRoot());
    }

    public ItemStack getDefaultMember(String uid) {
        return getSpeciesRoot().getMemberStack(getIndividual(uid), getDefaultType());
    }

    public IIndividual getIndividual(String uid) {
        return getSpeciesRoot().templateAsIndividual(getSpeciesRoot().getTemplate(uid));
    }

    public IGenome getGenome(String uid) {
        return getSpeciesRoot().templateAsGenome(getSpeciesRoot().getTemplate(uid));
    }
}
