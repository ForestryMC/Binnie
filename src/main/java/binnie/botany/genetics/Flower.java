package binnie.botany.genetics;

import binnie.botany.api.*;
import binnie.botany.core.BotanyCore;
import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosome;
import forestry.core.genetics.Chromosome;
import forestry.core.genetics.Individual;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class Flower extends Individual implements IFlower {
	public IFlowerGenome genome;
	@Nullable
	public IFlowerGenome mate;
	private int age;
	private boolean wilting;
	private boolean flowered;

	public Flower(final NBTTagCompound nbt) {
		super(nbt);

		if (nbt.hasKey("Age")) {
			this.age = nbt.getInteger("Age");
		} else {
			this.age = 0;
		}

		this.wilting = nbt.hasKey("Wilt") && nbt.getBoolean("Wilt");

		if (nbt.hasKey("Flowered")) {
			this.flowered = nbt.getBoolean("Flowered");
		} else {
			this.flowered = age > 0;
		}

		if (nbt.hasKey("Genome")) {
			this.genome = new FlowerGenome(nbt.getCompoundTag("Genome"));
		} else {
			this.genome = BotanyCore.getFlowerRoot().templateAsGenome(BotanyCore.getFlowerRoot().getDefaultTemplate());
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
		this.flowered = age > 0;
	}

	@Override
	public String getDisplayName() {
		final IAlleleFlowerSpecies species = this.getGenome().getPrimary();
		String name = species.getName();
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
		final NBTTagCompound nbtGenome = new NBTTagCompound();
		this.genome.writeToNBT(nbtGenome);
		nbttagcompound.setTag("Genome", nbtGenome);
		if (this.mate != null) {
			final NBTTagCompound nbtMate = new NBTTagCompound();
			this.mate.writeToNBT(nbtMate);
			nbttagcompound.setTag("Mate", nbtMate);
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
	@Nullable
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
		if (this.mate != null) {
			final IChromosome[] chromosomes = new IChromosome[this.genome.getChromosomes().length];
			IChromosome[] parentFirst = this.mutateSpecies(world, pos, this.genome, this.mate);
			IChromosome[] parentSecond = this.mutateSpecies(world, pos, this.mate, this.genome);
			for (int i = 0; i < parentFirst.length; ++i) {
				if (parentFirst[i] != null && parentSecond[i] != null) {
					chromosomes[i] = Chromosome.inheritChromosome(world.rand, parentFirst[i], parentSecond[i]);
				}
			}
			return new Flower(new FlowerGenome(chromosomes), 0);
		} else {
			NBTTagCompound nbt = new NBTTagCompound();
			writeToNBT(nbt);
			return new Flower(nbt);
		}
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
