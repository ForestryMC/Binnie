package binnie.botany.genetics;

import binnie.botany.api.EnumFlowerChromosome;
import binnie.botany.api.IAlleleFlowerSpecies;
import binnie.botany.api.IColourMix;
import binnie.botany.api.IFlower;
import binnie.botany.api.IFlowerColor;
import binnie.botany.api.IFlowerGenome;
import binnie.botany.api.IFlowerMutation;
import binnie.botany.core.BotanyCore;
import binnie.core.util.I18N;
import binnie.genetics.genetics.AlleleHelper;
import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosome;
import forestry.core.genetics.Chromosome;
import forestry.core.genetics.Individual;
import java.util.List;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class Flower extends Individual implements IFlower {
    public IFlowerGenome genome;
    public IFlowerGenome mate;

    protected int age;
    protected boolean wilting;
    protected boolean flowered;

    public Flower(NBTTagCompound nbttagcompound) {
        age = 0;
        wilting = false;
        readFromNBT(nbttagcompound);
    }

    public Flower(IFlowerGenome genome, int age) {
        this.genome = genome;
        this.age = age;
        wilting = false;
        flowered = age > 0;
    }

    @Override
    public String getDisplayName() {
        IAlleleFlowerSpecies species = getGenome().getPrimary();
        String name = "";
        if (species != null) {
            name += species.getName();
        }
        return name;
    }

    @Override
    public void addTooltip(List<String> list) {
        IAlleleFlowerSpecies primary = genome.getPrimary();
        IAlleleFlowerSpecies secondary = genome.getSecondary();
        if (!isPureBred(EnumFlowerChromosome.SPECIES)) {
            list.add(EnumChatFormatting.BLUE
                    + I18N.localise("botany.item.tooltip.hybrid", primary.getName(), secondary.getName()));
        }

        list.add(EnumChatFormatting.GRAY + I18N.localise("botany.item.tooltip.age", getAge()));
        list.add(EnumChatFormatting.GREEN
                + I18N.localise(
                        "botany.item.tooltip.temperature",
                        AlleleHelper.toDisplay(primary.getTemperature()),
                        AlleleHelper.toDisplay(genome.getToleranceTemperature())));
        list.add(EnumChatFormatting.GREEN
                + I18N.localise(
                        "botany.item.tooltip.moisture",
                        AlleleHelper.toDisplay(primary.getMoisture()),
                        AlleleHelper.toDisplay(genome.getToleranceMoisture())));
        list.add(EnumChatFormatting.GREEN
                + I18N.localise(
                        "botany.item.tooltip.pH",
                        AlleleHelper.toDisplay(primary.getHumidity()),
                        AlleleHelper.toDisplay(genome.getTolerancePH())));
        list.add(EnumChatFormatting.GRAY + I18N.localise("botany.item.tooltip.fertility", genome.getFertility()));
    }

    @Override
    public String getIdent() {
        return getGenome().getPrimary().getUID();
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        if (nbttagcompound == null) {
            genome = BotanyCore.getFlowerRoot()
                    .templateAsGenome(BotanyCore.getFlowerRoot().getDefaultTemplate());
            return;
        }

        age = nbttagcompound.getInteger("Age");
        wilting = nbttagcompound.getBoolean("Wilt");
        flowered = nbttagcompound.getBoolean("Flowered");
        if (nbttagcompound.hasKey("Genome")) {
            genome = new FlowerGenome(nbttagcompound.getCompoundTag("Genome"));
        }
        if (nbttagcompound.hasKey("Mate")) {
            mate = new FlowerGenome(nbttagcompound.getCompoundTag("Mate"));
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setInteger("Age", age);
        nbttagcompound.setBoolean("Wilt", wilting);
        nbttagcompound.setBoolean("Flowered", flowered);

        if (genome != null) {
            NBTTagCompound NBTmachine = new NBTTagCompound();
            genome.writeToNBT(NBTmachine);
            nbttagcompound.setTag("Genome", NBTmachine);
        }

        if (mate != null) {
            NBTTagCompound NBTmachine = new NBTTagCompound();
            mate.writeToNBT(NBTmachine);
            nbttagcompound.setTag("Mate", NBTmachine);
        }
    }

    public int getMetadata() {
        return 0;
    }

    @Override
    public IFlowerGenome getGenome() {
        return genome;
    }

    @Override
    public void mate(IFlower other) {
        mate = new FlowerGenome(other.getGenome().getChromosomes());
    }

    @Override
    public IFlowerGenome getMate() {
        return mate;
    }

    @Override
    public IFlower copy() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        writeToNBT(nbttagcompound);
        return new Flower(nbttagcompound);
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public void age() {
        if (age < 15) {
            age++;
        }
    }

    @Override
    public int getMaxAge() {
        return getGenome().getLifespan();
    }

    @Override
    public boolean isWilted() {
        return wilting;
    }

    @Override
    public void setWilted(boolean value) {
        wilting = value;
    }

    @Override
    public boolean hasFlowered() {
        return flowered;
    }

    @Override
    public void setFlowered(boolean value) {
        flowered = value;
    }

    @Override
    public void removeMate() {
        mate = null;
    }

    @Override
    public IFlower getOffspring(World world) {
        IChromosome[] chromosomes = new IChromosome[genome.getChromosomes().length];
        IChromosome[] parent1 = mutateSpecies(world, genome, mate);
        IChromosome[] parent2 = mutateSpecies(world, mate, genome);
        for (int i = 0; i < parent1.length; ++i) {
            if (parent1[i] != null && parent2[i] != null) {
                chromosomes[i] = Chromosome.inheritChromosome(world.rand, parent1[i], parent2[i]);
            }
        }
        return new Flower(new FlowerGenome(chromosomes), 0);
    }

    private IChromosome[] mutateSpecies(World world, IFlowerGenome genomeOne, IFlowerGenome genomeTwo) {
        IChromosome[] parent1 = genomeOne.getChromosomes();
        IChromosome[] parent2 = genomeTwo.getChromosomes();
        IAllele allele0;
        IAllele allele2;
        IFlowerGenome genome0;
        IFlowerGenome genome2;

        if (world.rand.nextBoolean()) {
            allele0 = parent1[EnumTreeChromosome.SPECIES.ordinal()].getPrimaryAllele();
            allele2 = parent2[EnumTreeChromosome.SPECIES.ordinal()].getSecondaryAllele();
            genome0 = genomeOne;
            genome2 = genomeTwo;
        } else {
            allele0 = parent2[EnumTreeChromosome.SPECIES.ordinal()].getPrimaryAllele();
            allele2 = parent1[EnumTreeChromosome.SPECIES.ordinal()].getSecondaryAllele();
            genome0 = genomeTwo;
            genome2 = genomeOne;
        }

        IFlowerColor colour1 = genome0.getPrimaryColor();
        IFlowerColor colour2 = genome2.getPrimaryColor();
        if (colour1 != colour2) {
            for (IColourMix mutation : BotanyCore.getFlowerRoot().getColourMixes(true)) {
                if (mutation.isMutation(colour1, colour2) && world.rand.nextFloat() * 100.0f < mutation.getChance()) {
                    parent1[EnumFlowerChromosome.PRIMARY.ordinal()] =
                            new Chromosome(mutation.getResult().getAllele());
                }
            }
        }

        colour1 = genome0.getSecondaryColor();
        colour2 = genome2.getSecondaryColor();
        if (colour1 != colour2) {
            for (IColourMix mutation : BotanyCore.getFlowerRoot().getColourMixes(true)) {
                if (mutation.isMutation(colour1, colour2) && world.rand.nextFloat() * 100.0f < mutation.getChance()) {
                    parent1[EnumFlowerChromosome.SECONDARY.ordinal()] =
                            new Chromosome(mutation.getResult().getAllele());
                }
            }
        }

        colour1 = genome0.getStemColor();
        colour2 = genome2.getStemColor();
        if (colour1 != colour2) {
            for (IColourMix mutation : BotanyCore.getFlowerRoot().getColourMixes(true)) {
                if (mutation.isMutation(colour1, colour2) && world.rand.nextFloat() * 100.0f < mutation.getChance()) {
                    parent1[EnumFlowerChromosome.STEM.ordinal()] =
                            new Chromosome(mutation.getResult().getAllele());
                }
            }
        }

        IChromosome[] template = null;
        for (IFlowerMutation mutation2 : BotanyCore.getFlowerRoot().getMutations(true)) {
            float chance = mutation2.getChance(allele0, allele2, genome0, genome2);
            if (chance > 0.0f && world.rand.nextFloat() * 100.0f < chance && template == null) {
                template = BotanyCore.getFlowerRoot().templateAsChromosomes(mutation2.getTemplate());
            }
        }

        if (template != null) {
            parent1 = template;
        }
        return parent1;
    }
}
