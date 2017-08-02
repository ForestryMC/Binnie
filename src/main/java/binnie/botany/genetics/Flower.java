package binnie.botany.genetics;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosome;
import forestry.core.genetics.Chromosome;
import forestry.core.genetics.Individual;
import forestry.core.utils.Translator;

import binnie.botany.api.EnumFlowerChromosome;
import binnie.botany.api.IAlleleFlowerSpecies;
import binnie.botany.api.IColorMix;
import binnie.botany.api.IFlower;
import binnie.botany.api.IFlowerColor;
import binnie.botany.api.IFlowerGenome;
import binnie.botany.api.IFlowerMutation;
import binnie.botany.core.BotanyCore;
import binnie.core.util.I18N;

public class Flower extends Individual implements IFlower {
	public IFlowerGenome genome;

	@Nullable
	public IFlowerGenome mate;

	private int age;
	private boolean wilting;
	private boolean flowered;

	public Flower(NBTTagCompound nbt) {
		super(nbt);

		if (nbt.hasKey("Age")) {
			age = nbt.getInteger("Age");
		} else {
			age = 0;
		}

		wilting = nbt.hasKey("Wilt") && nbt.getBoolean("Wilt");

		if (nbt.hasKey("Flowered")) {
			flowered = nbt.getBoolean("Flowered");
		} else {
			flowered = age > 0;
		}

		if (nbt.hasKey("Genome")) {
			genome = new FlowerGenome(nbt.getCompoundTag("Genome"));
		} else {
			genome = BotanyCore.getFlowerRoot().templateAsGenome(BotanyCore.getFlowerRoot().getDefaultTemplate());
		}

		if (nbt.hasKey("Mate")) {
			mate = new FlowerGenome(nbt.getCompoundTag("Mate"));
		}
	}

	public Flower(IFlowerGenome genome, int age) {
		this.age = 0;
		this.genome = genome;
		this.age = age;
		wilting = false;
		flowered = age > 0;
	}

	@Override
	public String getDisplayName() {
		IAlleleFlowerSpecies species = getGenome().getPrimary();
		String name = species.getAlleleName();
		if (age == 0) {
			name += "";
		}
		return name;
	}

	@Override
	public void addTooltip(List<String> list) {
		IAlleleFlowerSpecies primary = genome.getPrimary();
		IAlleleFlowerSpecies secondary = genome.getSecondary();
		if (!isPureBred(EnumFlowerChromosome.SPECIES)) {
			list.add(TextFormatting.BLUE + Translator.translateToLocal("for.bees.hybrid").replaceAll("%PRIMARY", primary.getAlleleName()).replaceAll("%SECONDARY", secondary.getAlleleName()));
		}

		list.add(TextFormatting.GOLD + I18N.localise("item.botany.flower.age", getAge()));
		list.add(TextFormatting.GREEN + "T: " + getGenome().getPrimary().getTemperature() + " / " + getGenome().getToleranceTemperature());
		list.add(TextFormatting.AQUA + "M: " + getGenome().getPrimary().getMoisture() + " / " + getGenome().getToleranceMoisture());
		list.add(TextFormatting.AQUA + "pH: " + getGenome().getPrimary().getHumidity() + " / " + getGenome().getTolerancePH());
		list.add(TextFormatting.GOLD + "Fert: " + getGenome().getFertility() + "x");
	}

	@Override
	public String getIdent() {
		return getGenome().getPrimary().getUID();
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound2) {
		NBTTagCompound nbttagcompound = super.writeToNBT(nbttagcompound2);
		nbttagcompound.setInteger("Age", age);
		nbttagcompound.setBoolean("Wilt", wilting);
		nbttagcompound.setBoolean("Flowered", flowered);
		NBTTagCompound nbtGenome = new NBTTagCompound();
		genome.writeToNBT(nbtGenome);
		nbttagcompound.setTag("Genome", nbtGenome);
		if (mate != null) {
			NBTTagCompound nbtMate = new NBTTagCompound();
			mate.writeToNBT(nbtMate);
			nbttagcompound.setTag("Mate", nbtMate);
		}
		return nbttagcompound;
	}

	@Override
	public IFlowerGenome getGenome() {
		return genome;
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
		mate = new FlowerGenome(other.getGenome().getChromosomes());
	}

	@Override
	@Nullable
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
	public void setAge(int i) {
		age = i;
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
	public void setWilted(boolean wilted) {
		wilting = wilted;
	}

	@Override
	public boolean hasFlowered() {
		return flowered;
	}

	@Override
	public void setFlowered(boolean flowered) {
		this.flowered = flowered;
	}

	@Override
	public void removeMate() {
		mate = null;
	}

	@Override
	public IFlower getOffspring(World world, BlockPos pos) {
		if (mate != null) {
			IChromosome[] chromosomes = new IChromosome[genome.getChromosomes().length];
			IChromosome[] parentFirst = mutateSpecies(world, pos, genome, mate);
			IChromosome[] parentSecond = mutateSpecies(world, pos, mate, genome);
			for (int i = 0; i < parentFirst.length; ++i) {
				if (parentFirst[i] != null && parentSecond[i] != null) {
					chromosomes[i] = Chromosome.inheritChromosome(world.rand, parentFirst[i], parentSecond[i]);
				}
			}
			return new Flower(new FlowerGenome(chromosomes), 0);
		}

		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT(nbt);
		return new Flower(nbt);
	}

	private IChromosome[] mutateSpecies(World world, BlockPos pos, IFlowerGenome genomeFirst, IFlowerGenome genomeSecond) {
		IChromosome[] parentFirst = genomeFirst.getChromosomes();
		IChromosome[] parentSecond = genomeSecond.getChromosomes();
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

		IFlowerColor colorFirst = genome0.getPrimaryColor();
		IFlowerColor colorSecond = genome2.getPrimaryColor();
		if (colorFirst != colorSecond) {
			for (IColorMix mutation : BotanyCore.getFlowerRoot().getColorMixes(true)) {
				if (mutation.isMutation(colorFirst, colorSecond) && world.rand.nextFloat() * 100.0f < mutation.getChance()) {
					parentFirst[EnumFlowerChromosome.PRIMARY.ordinal()] = new Chromosome(mutation.getResult().getAllele());
				}
			}
		}

		colorFirst = genome0.getSecondaryColor();
		colorSecond = genome2.getSecondaryColor();
		if (colorFirst != colorSecond) {
			for (IColorMix mutation : BotanyCore.getFlowerRoot().getColorMixes(true)) {
				if (mutation.isMutation(colorFirst, colorSecond) && world.rand.nextFloat() * 100.0f < mutation.getChance()) {
					parentFirst[EnumFlowerChromosome.SECONDARY.ordinal()] = new Chromosome(mutation.getResult().getAllele());
				}
			}
		}
		colorFirst = genome0.getStemColor();
		colorSecond = genome2.getStemColor();
		if (colorFirst != colorSecond) {
			for (IColorMix mutation : BotanyCore.getFlowerRoot().getColorMixes(true)) {
				if (mutation.isMutation(colorFirst, colorSecond) && world.rand.nextFloat() * 100.0f < mutation.getChance()) {
					parentFirst[EnumFlowerChromosome.STEM.ordinal()] = new Chromosome(mutation.getResult().getAllele());
				}
			}
		}

		IChromosome[] template = null;
		for (IFlowerMutation mutation2 : BotanyCore.getFlowerRoot().getMutations(true)) {
			float chance = mutation2.getChance(world, pos, alleleFrist, alleleSecond, genome0, genome2);
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
