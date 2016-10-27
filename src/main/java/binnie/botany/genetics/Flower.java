package binnie.botany.genetics;

import binnie.botany.api.EnumFlowerChromosome;
import binnie.botany.api.IAlleleFlowerSpecies;
import binnie.botany.api.IColourMix;
import binnie.botany.api.IFlower;
import binnie.botany.api.IFlowerColour;
import binnie.botany.api.IFlowerGenome;
import binnie.botany.api.IFlowerMutation;
import binnie.botany.core.BotanyCore;
import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosome;
import forestry.core.genetics.Chromosome;
import forestry.core.genetics.Individual;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class Flower extends Individual implements IFlower {
	@Nonnull
	public IFlowerGenome genome;
	@Nullable
	public IFlowerGenome mate;
	private int age;
	private boolean wilting;
	private boolean flowered;

	public Flower(@Nonnull final NBTTagCompound nbt) {
		super(nbt);
		if (nbt == null) {
			this.genome = BotanyCore.getFlowerRoot().templateAsGenome(BotanyCore.getFlowerRoot().getDefaultTemplate());
			return;
		}
		if (nbt.hasKey("Age")) {
			this.age = nbt.getInteger("Age");
		} else {
			this.age = 0;
		}
		if (nbt.hasKey("Wilt")) {
			this.wilting = nbt.getBoolean("Wilt");
		} else {
			this.wilting = false;
		}
		if (nbt.hasKey("Flowered")) {
			this.flowered = nbt.getBoolean("Flowered");
		} else {
			if (age > 0) {
				this.flowered = true;
			} else {
				this.flowered = false;
			}
		}
		if (nbt.hasKey("Genome")) {
			this.genome = new FlowerGenome(nbt.getCompoundTag("Genome"));
		}
		if (nbt.hasKey("Mate")) {
			this.mate = new FlowerGenome(nbt.getCompoundTag("Mate"));
		}
	}

	public Flower(final IFlowerGenome genome, final int age) {
		this.age = 0;
		this.wilting = false;
		this.genome = genome;
		this.age = age;
		if (age > 0) {
			this.flowered = true;
		} else {
			this.flowered = false;
		}
	}

	@Override
	public String getDisplayName() {
		final IAlleleFlowerSpecies species = this.getGenome().getPrimary();
		String name = "";
		if (species != null) {
			name += species.getName();
		}
		if (this.age == 0) {
			name += "";
		}
		return name;
	}

	@Override
	public void addTooltip(final List<String> list) {
		final IAlleleFlowerSpecies primary = this.genome.getPrimary();
		final IAlleleFlowerSpecies secondary = this.genome.getSecondary();
		if (!this.isPureBred(EnumFlowerChromosome.SPECIES)) {
			list.add("�9" + primary.getName() + "-" + secondary.getName() + " Hybrid");
		}
		list.add("�6Age: " + this.getAge());
		list.add("�6T: " + this.getGenome().getPrimary().getTemperature() + " / " + this.getGenome().getToleranceTemperature());
		list.add("�6M: " + this.getGenome().getPrimary().getMoisture() + " / " + this.getGenome().getToleranceMoisture());
		list.add("�6pH: " + this.getGenome().getPrimary().getHumidity() + " / " + this.getGenome().getTolerancePH());
		list.add("�6Fert: " + this.getGenome().getFertility() + "x");
	}

	@Override
	public String getIdent() {
		return this.getGenome().getPrimary().getUID();
	}

	@Override
	public NBTTagCompound writeToNBT(final NBTTagCompound nbttagcompound2) {
		NBTTagCompound nbttagcompound = super.writeToNBT(nbttagcompound2);
		nbttagcompound.setInteger("Age", this.age);
		nbttagcompound.setBoolean("Wilt", this.wilting);
		nbttagcompound.setBoolean("Flowered", this.flowered);
		if (this.genome != null) {
			final NBTTagCompound NBTmachine = new NBTTagCompound();
			this.genome.writeToNBT(NBTmachine);
			nbttagcompound.setTag("Genome", NBTmachine);
		}
		if (this.mate != null) {
			final NBTTagCompound NBTmachine = new NBTTagCompound();
			this.mate.writeToNBT(NBTmachine);
			nbttagcompound.setTag("Mate", NBTmachine);
		}
		return nbttagcompound;
	}

	@Override
	public IFlowerGenome getGenome() {
		return this.genome;
	}

	private IChromosome inheritChromosome(Random rand, IChromosome parentFirst, IChromosome parentSecond) {
		IAllele choiceFirst;
		if (rand.nextBoolean()) {
			choiceFirst = parentFirst.getPrimaryAllele();
		} else {
			choiceFirst = parentFirst.getSecondaryAllele();
		}
		IAllele choiceSecond;
		if (rand.nextBoolean()) {
			choiceSecond = parentSecond.getPrimaryAllele();
		} else {
			choiceSecond = parentSecond.getSecondaryAllele();
		}
		if (rand.nextBoolean()) {
			return new Chromosome(choiceFirst, choiceSecond);
		}
		return new Chromosome(choiceSecond, choiceFirst);
	}

	@Override
	public void mate(IFlower other) {
		this.mate = new FlowerGenome(other.getGenome().getChromosomes());
	}

	@Override
	public IFlowerGenome getMate() {
		return this.mate;
	}

	@Override
	public IFlower copy() {
		final NBTTagCompound nbttagcompound = new NBTTagCompound();
		this.writeToNBT(nbttagcompound);
		return new Flower(nbttagcompound);
	}

	@Override
	public int getAge() {
		return this.age;
	}

	@Override
	public void age() {
		if (this.age < 15) {
			++this.age;
		}
	}

	@Override
	public void setAge(final int i) {
		this.age = i;
	}

	@Override
	public int getMaxAge() {
		return this.getGenome().getLifespan();
	}

	@Override
	public boolean isWilted() {
		return this.wilting;
	}

	@Override
	public void setWilted(final boolean wilted) {
		this.wilting = wilted;
	}

	@Override
	public boolean hasFlowered() {
		return this.flowered;
	}

	@Override
	public void setFlowered(final boolean flowered) {
		this.flowered = flowered;
	}

	@Override
	public void removeMate() {
		this.mate = null;
	}

	@Override
	public IFlower getOffspring(final World world, final BlockPos pos) {
		final IChromosome[] chromosomes = new IChromosome[this.genome.getChromosomes().length];
		IChromosome[] parentFirst = this.genome.getChromosomes();
		IChromosome[] parentSecond = this.mate.getChromosomes();
		parentFirst = this.mutateSpecies(world, pos, this.genome, this.mate);
		parentSecond = this.mutateSpecies(world, pos, this.mate, this.genome);
		for (int i = 0; i < parentFirst.length; ++i) {
			if (parentFirst[i] != null && parentSecond[i] != null) {
				chromosomes[i] = Chromosome.inheritChromosome(world.rand, parentFirst[i], parentSecond[i]);
			}
		}
		return new Flower(new FlowerGenome(chromosomes), 0);
	}

	private IChromosome[] mutateSpecies(World world, BlockPos pos, IFlowerGenome genomeFirst, IFlowerGenome genomeSecond) {
		IChromosome[] parentFirst = genomeFirst.getChromosomes();
		final IChromosome[] parentSecond = genomeSecond.getChromosomes();
		IAlleleFlowerSpecies alleleFrist;
		IAlleleFlowerSpecies alleleSecond;
		IFlowerGenome genome0;
		IFlowerGenome genome2;
		if (world.rand.nextBoolean()) {
			alleleFrist = (IAlleleFlowerSpecies) parentFirst[EnumTreeChromosome.SPECIES.ordinal()].getPrimaryAllele();
			alleleSecond = (IAlleleFlowerSpecies) parentSecond[EnumTreeChromosome.SPECIES.ordinal()].getSecondaryAllele();
			genome0 = genomeFirst;
			genome2 = genomeSecond;
		} else {
			alleleFrist = (IAlleleFlowerSpecies) parentSecond[EnumTreeChromosome.SPECIES.ordinal()].getPrimaryAllele();
			alleleSecond = (IAlleleFlowerSpecies) parentFirst[EnumTreeChromosome.SPECIES.ordinal()].getSecondaryAllele();
			genome0 = genomeSecond;
			genome2 = genomeFirst;
		}
		IFlowerColour colourFirst = genome0.getPrimaryColor();
		IFlowerColour colourSecond = genome2.getPrimaryColor();
		if (colourFirst != colourSecond) {
			for (final IColourMix mutation : BotanyCore.getFlowerRoot().getColourMixes(true)) {
				if (mutation.isMutation(colourFirst, colourSecond) && world.rand.nextFloat() * 100.0f < mutation.getChance()) {
					parentFirst[EnumFlowerChromosome.PRIMARY.ordinal()] = new Chromosome(mutation.getResult().getAllele());
				}
			}
		}
		colourFirst = genome0.getSecondaryColor();
		colourSecond = genome2.getSecondaryColor();
		if (colourFirst != colourSecond) {
			for (final IColourMix mutation : BotanyCore.getFlowerRoot().getColourMixes(true)) {
				if (mutation.isMutation(colourFirst, colourSecond) && world.rand.nextFloat() * 100.0f < mutation.getChance()) {
					parentFirst[EnumFlowerChromosome.SECONDARY.ordinal()] = new Chromosome(mutation.getResult().getAllele());
				}
			}
		}
		colourFirst = genome0.getStemColor();
		colourSecond = genome2.getStemColor();
		if (colourFirst != colourSecond) {
			for (final IColourMix mutation : BotanyCore.getFlowerRoot().getColourMixes(true)) {
				if (mutation.isMutation(colourFirst, colourSecond) && world.rand.nextFloat() * 100.0f < mutation.getChance()) {
					parentFirst[EnumFlowerChromosome.STEM.ordinal()] = new Chromosome(mutation.getResult().getAllele());
				}
			}
		}
		IChromosome[] template = null;
		for (final IFlowerMutation mutation2 : BotanyCore.getFlowerRoot().getMutations(true)) {
			final float chance = mutation2.getChance(world, pos, alleleFrist, alleleSecond, genome0, genome2);
			if (chance > 0.0f && world.rand.nextFloat() * 100.0f < chance && template == null) {
				template = BotanyCore.getFlowerRoot().templateAsChromosomes(mutation2.getTemplate());
			}
		}
		if (template != null) {
			parentFirst = template;
		}
		return parentFirst;
	}
}
