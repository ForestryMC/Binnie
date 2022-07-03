package binnie.core.genetics;

import binnie.Binnie;
import binnie.core.util.I18N;
import binnie.core.util.UniqueItemStackSet;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.FakeWorld;
import binnie.extratrees.machines.lumbermill.Lumbermill;
import binnie.genetics.genetics.AlleleHelper;
import com.mojang.authlib.GameProfile;
import forestry.api.arboriculture.EnumGermlingType;
import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.arboriculture.IAlleleFruit;
import forestry.api.arboriculture.IAlleleTreeSpecies;
import forestry.api.arboriculture.IArboristTracker;
import forestry.api.arboriculture.IFruitProvider;
import forestry.api.arboriculture.ITreeGenome;
import forestry.api.arboriculture.ITreeMutation;
import forestry.api.arboriculture.ITreeRoot;
import forestry.api.arboriculture.TreeManager;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleInteger;
import forestry.api.genetics.IAllelePlantType;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IBreedingTracker;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IFruitFamily;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.IMutation;
import forestry.api.genetics.ISpeciesRoot;
import forestry.core.genetics.alleles.EnumAllele;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.util.ForgeDirection;

public class TreeBreedingSystem extends BreedingSystem {
    public UniqueItemStackSet allFruits = new UniqueItemStackSet();
    public UniqueItemStackSet allWoods = new UniqueItemStackSet();
    private UniqueItemStackSet discoveredFruits = new UniqueItemStackSet();
    private UniqueItemStackSet discoveredWoods = new UniqueItemStackSet();

    public TreeBreedingSystem() {
        iconUndiscovered = Binnie.Resource.getItemIcon(ExtraTrees.instance, "icon/undiscoveredTree");
        iconDiscovered = Binnie.Resource.getItemIcon(ExtraTrees.instance, "icon/discoveredTree");
    }

    @Override
    public float getChance(IMutation mutation, EntityPlayer player, IAllele species1, IAllele species2) {
        ISpeciesRoot speciesRoot = getSpeciesRoot();
        ITreeGenome genome0 = (ITreeGenome) speciesRoot.templateAsGenome(speciesRoot.getTemplate(species1.getUID()));
        ITreeGenome genome2 = (ITreeGenome) speciesRoot.templateAsGenome(speciesRoot.getTemplate(species2.getUID()));
        return ((ITreeMutation) mutation)
                .getChance(
                        player.worldObj,
                        (int) player.posX,
                        (int) player.posY,
                        (int) player.posZ,
                        (IAlleleTreeSpecies) species1,
                        (IAlleleTreeSpecies) species2,
                        genome0,
                        genome2);
    }

    @Override
    public ISpeciesRoot getSpeciesRoot() {
        return Binnie.Genetics.getTreeRoot();
    }

    @Override
    public int getColor() {
        return 0x00cf0e;
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
            return types.isEmpty()
                    ? I18N.localise("binniecore.allele.none")
                    : types.iterator().next().toString();
        }

        if (chromosome == EnumTreeChromosome.FRUITS && allele.getUID().contains(".")) {
            IFruitProvider provider = ((IAlleleFruit) allele).getProvider();
            return (provider.getProducts().length == 0)
                    ? I18N.localise("binniecore.allele.none")
                    : provider.getProducts()[0].getDisplayName();
        }

        if (chromosome == EnumTreeChromosome.GROWTH) {
            if (allele.getUID().contains("Tropical")) {
                return I18N.localise("binniecore.allele.growth.tropical");
            }
            if (allele.getUID().contains("Lightlevel")) {
                return I18N.localise("binniecore.allele.growth.lightlevel");
            }
        }
        return super.getAlleleName(chromosome, allele);
    }

    @Override
    public void onSyncBreedingTracker(IBreedingTracker tracker) {
        discoveredFruits.clear();
        discoveredWoods.clear();
        for (IAlleleSpecies species : getDiscoveredSpecies(tracker)) {
            IAlleleTreeSpecies tSpecies = (IAlleleTreeSpecies) species;
            ISpeciesRoot speciesRoot = getSpeciesRoot();
            ITreeGenome genome = (ITreeGenome) speciesRoot.templateAsGenome(speciesRoot.getTemplate(tSpecies.getUID()));

            FakeWorld world = FakeWorld.instance;
            genome.getPrimary().getGenerator().setLogBlock(genome, world, 0, 0, 0, ForgeDirection.UP);
            ItemStack wood = world.getWooLog();
            if (wood != null) {
                discoveredWoods.add(wood);
            }

            // TODO ???
            // for (ItemStack wood :
            // tSpecies.getRoot().templateAsIndividual(getSpeciesRoot().getTemplate(tSpecies.getUID())).getProduceList())
            // {
            // this.discoveredWoods.add(wood);
            // }
            discoveredFruits.addAll(Arrays.asList(genome.getFruitProvider().getProducts()));
            for (ItemStack wood2 : discoveredWoods) {}
        }
    }

    @Override
    public void calculateArrays() {
        super.calculateArrays();
        for (IAlleleSpecies species : allActiveSpecies) {
            IAlleleTreeSpecies tSpecies = (IAlleleTreeSpecies) species;
            ITreeGenome genome = (ITreeGenome)
                    getSpeciesRoot().templateAsGenome(getSpeciesRoot().getTemplate(tSpecies.getUID()));

            FakeWorld world = FakeWorld.instance;
            genome.getPrimary().getGenerator().setLogBlock(genome, world, 0, 0, 0, ForgeDirection.UP);
            ItemStack wood = world.getWooLog();
            if (wood != null) {
                allWoods.add(wood);
            }

            // TODO ???
            // for (ItemStack wood :
            // tSpecies.getRoot().templateAsIndividual(getSpeciesRoot().getTemplate(tSpecies.getUID())).getProduceList())
            // {
            // this.allWoods.add(wood);
            // }
            allFruits.addAll(Arrays.asList(genome.getFruitProvider().getProducts()));
        }
    }

    public Collection<IAlleleSpecies> getTreesThatBearFruit(
            ItemStack fruit, boolean nei, World world, GameProfile player) {
        Collection<IAlleleSpecies> set = nei ? getAllSpecies() : getDiscoveredSpecies(world, player);
        List<IAlleleSpecies> found = new ArrayList<>();
        for (IAlleleSpecies species : set) {
            IAlleleTreeSpecies tSpecies = (IAlleleTreeSpecies) species;
            ITreeGenome genome = (ITreeGenome)
                    getSpeciesRoot().templateAsGenome(getSpeciesRoot().getTemplate(tSpecies.getUID()));
            for (ItemStack fruit2 : genome.getFruitProvider().getProducts()) {
                if (fruit2.isItemEqual(fruit)) {
                    found.add(species);
                }
            }
        }
        return found;
    }

    public Collection<IAlleleSpecies> getTreesThatCanBearFruit(
            ItemStack fruit, boolean nei, World world, GameProfile player) {
        Collection<IAlleleSpecies> set = nei ? getAllSpecies() : getDiscoveredSpecies(world, player);
        List<IAlleleSpecies> found = new ArrayList<>();
        Set<IFruitFamily> providers = new HashSet<>();
        for (IAlleleSpecies species : set) {
            IAlleleTreeSpecies tSpecies = (IAlleleTreeSpecies) species;
            ITreeGenome genome = (ITreeGenome)
                    getSpeciesRoot().templateAsGenome(getSpeciesRoot().getTemplate(tSpecies.getUID()));
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

    public Collection<IAlleleSpecies> getTreesThatHaveWood(
            ItemStack fruit, boolean nei, World world, GameProfile player) {
        Collection<IAlleleSpecies> set = nei ? getAllSpecies() : getDiscoveredSpecies(world, player);
        List<IAlleleSpecies> found = new ArrayList<>();
        for (IAlleleSpecies species : set) {
            IAlleleTreeSpecies tSpecies = (IAlleleTreeSpecies) species;
            ITreeGenome genome =
                    TreeManager.treeRoot.templateAsGenome(TreeManager.treeRoot.getTemplate(tSpecies.getUID()));
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

    public Collection<IAlleleSpecies> getTreesThatMakePlanks(
            ItemStack fruit, boolean nei, World world, GameProfile player) {
        if (fruit == null) {
            return new ArrayList<>();
        }
        Collection<IAlleleSpecies> set = nei ? getAllSpecies() : getDiscoveredSpecies(world, player);
        List<IAlleleSpecies> found = new ArrayList<>();
        for (IAlleleSpecies species : set) {
            IAlleleTreeSpecies tSpecies = (IAlleleTreeSpecies) species;
            ITreeGenome genome =
                    TreeManager.treeRoot.templateAsGenome(TreeManager.treeRoot.getTemplate(tSpecies.getUID()));
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
        return ((ITreeRoot) getSpeciesRoot()).getType(member) == EnumGermlingType.POLLEN;
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
        return new int[] {EnumGermlingType.SAPLING.ordinal(), EnumGermlingType.POLLEN.ordinal()};
    }

    @Override
    public void addExtraAlleles(IChromosomeType chromosome, TreeSet<IAllele> alleles) {
        switch ((EnumTreeChromosome) chromosome) {
            case FERTILITY:
                for (EnumAllele.Saplings saplings : EnumAllele.Saplings.values()) {
                    alleles.add(AlleleManager.alleleRegistry.getAllele(AlleleHelper.getUid(saplings)));
                }
                break;

            case GIRTH:
                for (ForestryAllele.Int a2 : ForestryAllele.Int.values()) {
                    alleles.add(a2.getAllele());
                }
                break;

            case HEIGHT:
                for (EnumAllele.Height height : EnumAllele.Height.values()) {
                    alleles.add(AlleleManager.alleleRegistry.getAllele(AlleleHelper.getUid(height)));
                }
                break;

            case MATURATION:
                for (EnumAllele.Maturation maturation : EnumAllele.Maturation.values()) {
                    alleles.add(AlleleManager.alleleRegistry.getAllele(AlleleHelper.getUid(maturation)));
                }
                break;

            case SAPPINESS:
                for (EnumAllele.Sappiness sappiness : EnumAllele.Sappiness.values()) {
                    alleles.add(AlleleManager.alleleRegistry.getAllele(AlleleHelper.getUid(sappiness)));
                }
                break;

            case TERRITORY:
                for (EnumAllele.Territory territory : EnumAllele.Territory.values()) {
                    alleles.add(AlleleManager.alleleRegistry.getAllele(AlleleHelper.getUid(territory)));
                }
                break;

            case YIELD:
                for (EnumAllele.Yield yield : EnumAllele.Yield.values()) {
                    alleles.add(AlleleManager.alleleRegistry.getAllele(AlleleHelper.getUid(yield)));
                }
                break;

            case FIREPROOF:
                for (ForestryAllele.Bool bool : ForestryAllele.Bool.values()) {
                    alleles.add(bool.getAllele());
                }
                break;
        }
    }
}
