package binnie.botany.genetics;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.genetics.IChromosome;
import forestry.core.genetics.Chromosome;
import forestry.core.genetics.Individual;
import forestry.core.utils.Translator;

import binnie.botany.api.genetics.EnumFlowerChromosome;
import binnie.botany.api.genetics.IAlleleFlowerSpecies;
import binnie.botany.api.genetics.IColorMix;
import binnie.botany.api.genetics.IFlower;
import binnie.botany.api.genetics.IFlowerColor;
import binnie.botany.api.genetics.IFlowerGenome;
import binnie.botany.api.genetics.IFlowerMutation;
import binnie.botany.core.BotanyCore;
import binnie.core.util.I18N;

public class Flower extends Individual implements IFlower {
	private final IFlowerGenome genome;

	@Nullable
	private IFlowerGenome mate;

	private int age;
	private boolean wilting;
	private boolean flowered;

	public Flower(NBTTagCompound compound) {
		super(compound);

		if (compound.hasKey("Age")) {
			age = compound.getInteger("Age");
		} else {
			age = 0;
		}

		wilting = compound.hasKey("Wilt") && compound.getBoolean("Wilt");

		if (compound.hasKey("Flowered")) {
			flowered = compound.getBoolean("Flowered");
		} else {
			flowered = age > 0;
		}

		if (compound.hasKey("Genome")) {
			genome = new FlowerGenome(compound.getCompoundTag("Genome"));
		} else {
			genome = BotanyCore.getFlowerRoot().templateAsGenome(BotanyCore.getFlowerRoot().getDefaultTemplate());
		}

		if (compound.hasKey("Mate")) {
			mate = new FlowerGenome(compound.getCompoundTag("Mate"));
		}
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
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("Age", age);
		compound.setBoolean("Wilt", wilting);
		compound.setBoolean("Flowered", flowered);
		NBTTagCompound compoundGenome = new NBTTagCompound();
		genome.writeToNBT(compoundGenome);
		compound.setTag("Genome", compoundGenome);
		if (mate != null) {
			NBTTagCompound compundMate = new NBTTagCompound();
			mate.writeToNBT(compundMate);
			compound.setTag("Mate", compundMate);
		}
		return compound;
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
	@Nullable
	public IFlowerGenome getMate() {
		return mate;
	}

	@Override
	public IFlower copy() {
		NBTTagCompound compound = new NBTTagCompound();
		writeToNBT(compound);
		return new Flower(compound);
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
		IAlleleFlowerSpecies alleleFirst;
		IAlleleFlowerSpecies alleleSecond;
		IFlowerGenome genome0;
		IFlowerGenome genome2;
		if (world.rand.nextBoolean()) {
			alleleFirst = (IAlleleFlowerSpecies) parentFirst[EnumTreeChromosome.SPECIES.ordinal()].getPrimaryAllele();
			alleleSecond = (IAlleleFlowerSpecies) parentSecond[EnumTreeChromosome.SPECIES.ordinal()].getSecondaryAllele();
			genome0 = genomeFirst;
			genome2 = genomeSecond;
		} else {
			alleleFirst = (IAlleleFlowerSpecies) parentSecond[EnumTreeChromosome.SPECIES.ordinal()].getPrimaryAllele();
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
			float chance = mutation2.getChance(world, pos, alleleFirst, alleleSecond, genome0, genome2);
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
