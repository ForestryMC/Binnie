package binnie.botany.genetics;

import binnie.botany.Botany;
import binnie.botany.api.EnumFlowerChromosome;
import binnie.botany.api.EnumFlowerStage;
import binnie.botany.api.IAlleleFlowerSpecies;
import binnie.botany.api.IBotanistTracker;
import binnie.botany.api.IColorMix;
import binnie.botany.api.IFlower;
import binnie.botany.api.IFlowerGenome;
import binnie.botany.api.IFlowerMutation;
import binnie.botany.api.IFlowerRoot;
import binnie.botany.flower.TileEntityFlower;
import com.mojang.authlib.GameProfile;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlyzerPlugin;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.IMutation;
import forestry.api.genetics.ISpeciesType;
import forestry.core.genetics.SpeciesRoot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FlowerRoot extends SpeciesRoot implements IFlowerRoot {
	static final String UID = "rootFlowers";
	public static int flowerSpeciesCount = -1;
	public static ArrayList<IFlower> flowerTemplates = new ArrayList<>();
	private static ArrayList<IFlowerMutation> flowerMutations = new ArrayList<>();
	private static ArrayList<IColorMix> colourMixes = new ArrayList<>();
	Map<ItemStack, IFlower> conversions;

	public FlowerRoot() {
		conversions = new HashMap<>();
	}

	@Override
	public String getUID() {
		return UID;
	}

	@Override
	public int getSpeciesCount() {
		if (FlowerRoot.flowerSpeciesCount < 0) {
			FlowerRoot.flowerSpeciesCount = 0;
			for (Map.Entry<String, IAllele> entry : AlleleManager.alleleRegistry.getRegisteredAlleles().entrySet()) {
				if (entry.getValue() instanceof IAlleleFlowerSpecies && ((IAlleleFlowerSpecies) entry.getValue()).isCounted()) {
					++FlowerRoot.flowerSpeciesCount;
				}
			}
		}
		return FlowerRoot.flowerSpeciesCount;
	}

	@Override
	public boolean isMember(ItemStack stack) {
		return !stack.isEmpty() && getType(stack) != null;
	}

	@Override
	public boolean isMember(ItemStack stack, ISpeciesType type) {
		return getType(stack) == type;
	}

	@Override
	public boolean isMember(IIndividual individual) {
		return individual instanceof IFlower;
	}

	@Override
	public IAlyzerPlugin getAlyzerPlugin() {
		return FlowerAlyzerPlugin.INSTANCE;
	}

	@Override
	public ISpeciesType getIconType() {
		return EnumFlowerStage.FLOWER;
	}

	@Override
	@Nullable
	public EnumFlowerStage getType(ItemStack stack) {
		Item item = stack.getItem();
		if (Botany.flowerItem == item) {
			return EnumFlowerStage.SEED;
		} else if (Botany.pollen == item) {
			return EnumFlowerStage.POLLEN;
		} else if (Botany.seed == item) {
			return EnumFlowerStage.SEED;
		}
		return null;
	}

	@Override
	public ItemStack getMemberStack(IIndividual flower, ISpeciesType type) {
		if (!isMember(flower)) {
			return ItemStack.EMPTY;
		}

		Item flowerItem = Botany.flowerItem;
		if (type == EnumFlowerStage.SEED) {
			flowerItem = Botany.seed;
		} else if (type == EnumFlowerStage.POLLEN) {
			flowerItem = Botany.pollen;
		}
		if (flowerItem != Botany.flowerItem) {
			((IFlower) flower).setAge(0);
		}

		NBTTagCompound nbttagcompound = new NBTTagCompound();
		flower.writeToNBT(nbttagcompound);
		ItemStack flowerStack = new ItemStack(flowerItem);
		flowerStack.setTagCompound(nbttagcompound);
		return flowerStack;
	}

	@Override
	@Nullable
	public IFlower getMember(ItemStack stack) {
		if (!isMember(stack) || stack.getTagCompound() == null) {
			return null;
		}
		return new Flower(stack.getTagCompound());
	}

	@Override
	public IFlower getFlower(IFlowerGenome genome) {
		return new Flower(genome, 2);
	}

	@Override
	public IFlowerGenome templateAsGenome(IAllele[] template) {
		return new FlowerGenome(templateAsChromosomes(template));
	}

	@Override
	public IFlowerGenome templateAsGenome(IAllele[] templateActive, IAllele[] templateInactive) {
		return new FlowerGenome(templateAsChromosomes(templateActive, templateInactive));
	}

	@Override
	public IFlower templateAsIndividual(IAllele[] template) {
		return new Flower(templateAsGenome(template), 2);
	}

	@Override
	public IFlower templateAsIndividual(IAllele[] templateActive, IAllele[] templateInactive) {
		return new Flower(templateAsGenome(templateActive, templateInactive), 2);
	}

	@Override
	public ArrayList<IFlower> getIndividualTemplates() {
		return FlowerRoot.flowerTemplates;
	}

	@Override
	public void registerTemplate(IAllele[] template) {
		registerTemplate(template[0].getUID(), template);
	}

	@Override
	public void registerTemplate(String identifier, IAllele[] template) {
		FlowerRoot.flowerTemplates.add(new Flower(templateAsGenome(template), 2));
		if (!speciesTemplates.containsKey(identifier)) {
			speciesTemplates.put(identifier, template);
		}
	}

	@Override
	public IAllele[] getTemplate(String identifier) {
		return speciesTemplates.get(identifier);
	}

	@Override
	public IAllele[] getDefaultTemplate() {
		return FlowerTemplates.getDefaultTemplate();
	}

	@Override
	public IAllele[] getRandomTemplate(Random rand) {
		return speciesTemplates.values().toArray(new IAllele[0][])[rand.nextInt(speciesTemplates.values().size())];
	}

	@Override
	public ArrayList<IFlowerMutation> getMutations(boolean shuffle) {
		if (shuffle) {
			Collections.shuffle(FlowerRoot.flowerMutations);
		}
		return FlowerRoot.flowerMutations;
	}

	@Override
	public void registerMutation(IMutation mutation) {
		if (AlleleManager.alleleRegistry.isBlacklisted(mutation.getTemplate()[0].getUID())) {
			return;
		}
		if (AlleleManager.alleleRegistry.isBlacklisted(mutation.getAllele0().getUID())) {
			return;
		}
		if (AlleleManager.alleleRegistry.isBlacklisted(mutation.getAllele1().getUID())) {
			return;
		}
		FlowerRoot.flowerMutations.add((IFlowerMutation) mutation);
	}

	@Override
	public IBotanistTracker getBreedingTracker(World world, @Nullable GameProfile player) {
		String filename = "BotanistTracker." + ((player == null) ? "common" : player.getId());
		BotanistTracker tracker = (BotanistTracker) world.loadData(BotanistTracker.class, filename);
		if (tracker == null) {
			tracker = new BotanistTracker(filename);
			world.setData(filename, tracker);
		}
		return tracker;
	}

	@Override
	public IIndividual getMember(NBTTagCompound compound) {
		return new Flower(compound);
	}

	@Override
	public Class<IFlower> getMemberClass() {
		return IFlower.class;
	}

	@Override
	public IChromosomeType[] getKaryotype() {
		return EnumFlowerChromosome.values();
	}

	@Override
	public IChromosomeType getSpeciesChromosomeType() {
		return EnumFlowerChromosome.SPECIES;
	}

	@Override
	public void addConversion(ItemStack itemstack, IAllele[] template) {
		IFlower flower = getFlower(templateAsGenome(template));
		conversions.put(itemstack, flower);
	}

	@Override
	@Nullable
	public IFlower getConversion(ItemStack itemstack) {
		for (Map.Entry<ItemStack, IFlower> entry : conversions.entrySet()) {
			if (entry.getKey().isItemEqual(itemstack)) {
				return (IFlower) entry.getValue().copy();
			}
		}
		return null;
	}

	@Override
	public void registerColourMix(IColorMix colorMix) {
		FlowerRoot.colourMixes.add(colorMix);
	}

	@Override
	public Collection<IColorMix> getColorMixes(boolean shuffle) {
		if (shuffle) {
			Collections.shuffle(FlowerRoot.colourMixes);
		}
		return FlowerRoot.colourMixes;
	}

	public boolean plant(World world, BlockPos pos, IFlower flower, GameProfile owner) {
		boolean set = world.setBlockState(pos, Botany.flower.getDefaultState());
		if (!set) {
			return false;
		}

		TileEntity tile = world.getTileEntity(pos);
		TileEntity below = world.getTileEntity(pos.down());
		if (tile != null && tile instanceof TileEntityFlower) {
			TileEntityFlower tileFlower = (TileEntityFlower) tile;
			if (below instanceof TileEntityFlower) {
				tileFlower.setSection(((TileEntityFlower) below).getSection());
			} else {
				tileFlower.create(flower, owner);
			}
		}

		tryGrowSection(world, pos);
		return true;
	}

	@Override
	public void tryGrowSection(World world, BlockPos pos) {
		if (world.isRemote) {
			return;
		}

		TileEntity tileFlower = world.getTileEntity(pos);
		if (tileFlower == null || !(tileFlower instanceof TileEntityFlower)) {
			return;
		}

		IFlower flower = ((TileEntityFlower) tileFlower).getFlower();
		int section = ((TileEntityFlower) tileFlower).getSection();
		if (flower == null || section >= flower.getGenome().getPrimary().getType().getSections() - 1 || flower.getAge() <= 0) {
			return;
		}

		world.setBlockState(pos.up(), Botany.flower.getDefaultState());
		TileEntity flowerAbove = world.getTileEntity(pos.up());
		if (flowerAbove != null && flowerAbove instanceof TileEntityFlower) {
			((TileEntityFlower) flowerAbove).setSection(section + 1);
		}
	}

	@Override
	public void onGrowFromSeed(World world, BlockPos pos) {
		tryGrowSection(world, pos);
	}
}
