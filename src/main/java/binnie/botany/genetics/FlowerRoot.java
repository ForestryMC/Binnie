package binnie.botany.genetics;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.mojang.authlib.GameProfile;

import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlyzerPlugin;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.IMutation;
import forestry.api.genetics.ISpeciesType;
import forestry.core.genetics.SpeciesRoot;

import binnie.botany.Botany;
import binnie.botany.api.EnumFlowerChromosome;
import binnie.botany.api.EnumFlowerStage;
import binnie.botany.api.IAlleleFlowerSpecies;
import binnie.botany.api.IBotanistTracker;
import binnie.botany.api.IColourMix;
import binnie.botany.api.IFlower;
import binnie.botany.api.IFlowerGenome;
import binnie.botany.api.IFlowerMutation;
import binnie.botany.api.IFlowerRoot;

public class FlowerRoot extends SpeciesRoot implements IFlowerRoot {
	static final String UID = "rootFlowers";
	public static int flowerSpeciesCount = -1;
	public static ArrayList<IFlower> flowerTemplates = new ArrayList<>();
	private static ArrayList<IFlowerMutation> flowerMutations = new ArrayList<>();
	private static ArrayList<IColourMix> colourMixes = new ArrayList<>();
	Map<ItemStack, IFlower> conversions;

	public FlowerRoot() {
		this.conversions = new HashMap<>();
	}

	@Override
	public String getUID() {
		return UID;
	}

	@Override
	public int getSpeciesCount() {
		if (FlowerRoot.flowerSpeciesCount < 0) {
			FlowerRoot.flowerSpeciesCount = 0;
			for (final Map.Entry<String, IAllele> entry : AlleleManager.alleleRegistry.getRegisteredAlleles().entrySet()) {
				if (entry.getValue() instanceof IAlleleFlowerSpecies && ((IAlleleFlowerSpecies) entry.getValue()).isCounted()) {
					++FlowerRoot.flowerSpeciesCount;
				}
			}
		}
		return FlowerRoot.flowerSpeciesCount;
	}

	@Override
	public boolean isMember(final ItemStack stack) {
		return !stack.isEmpty() && this.getType(stack) != null;
	}

	@Override
	public boolean isMember(ItemStack stack, ISpeciesType type) {
		return this.getType(stack) == type;
	}

	@Override
	public boolean isMember(final IIndividual individual) {
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
		if (!this.isMember(flower)) {
			return ItemStack.EMPTY;
		}
		Item flowerItem = Botany.flowerItem;
		if (type == EnumFlowerStage.SEED) {
			flowerItem = Botany.seed;
		}
		if (type == EnumFlowerStage.POLLEN) {
			flowerItem = Botany.pollen;
		}
		if (flowerItem != Botany.flowerItem) {
			((IFlower) flower).setAge(0);
		}
		final NBTTagCompound nbttagcompound = new NBTTagCompound();
		flower.writeToNBT(nbttagcompound);
		final ItemStack flowerStack = new ItemStack(flowerItem);
		flowerStack.setTagCompound(nbttagcompound);
		return flowerStack;
	}

	@Override
	@Nullable
	public IFlower getMember(final ItemStack stack) {
		if (!this.isMember(stack) || stack.getTagCompound() == null) {
			return null;
		}
		return new Flower(stack.getTagCompound());
	}

	@Override
	public IFlower getFlower(final IFlowerGenome genome) {
		return new Flower(genome, 2);
	}

	@Override
	public IFlowerGenome templateAsGenome(final IAllele[] template) {
		return new FlowerGenome(this.templateAsChromosomes(template));
	}

	@Override
	public IFlowerGenome templateAsGenome(final IAllele[] templateActive, final IAllele[] templateInactive) {
		return new FlowerGenome(this.templateAsChromosomes(templateActive, templateInactive));
	}

	@Override
	public IFlower templateAsIndividual(final IAllele[] template) {
		return new Flower(this.templateAsGenome(template), 2);
	}

	@Override
	public IFlower templateAsIndividual(final IAllele[] templateActive, final IAllele[] templateInactive) {
		return new Flower(this.templateAsGenome(templateActive, templateInactive), 2);
	}

	@Override
	public ArrayList<IFlower> getIndividualTemplates() {
		return FlowerRoot.flowerTemplates;
	}

	@Override
	public void registerTemplate(final IAllele[] template) {
		this.registerTemplate(template[0].getUID(), template);
	}

	@Override
	public void registerTemplate(final String identifier, final IAllele[] template) {
		FlowerRoot.flowerTemplates.add(new Flower(this.templateAsGenome(template), 2));
		if (!this.speciesTemplates.containsKey(identifier)) {
			this.speciesTemplates.put(identifier, template);
		}
	}

	@Override
	public IAllele[] getTemplate(final String identifier) {
		return this.speciesTemplates.get(identifier);
	}

	@Override
	public IAllele[] getDefaultTemplate() {
		return FlowerTemplates.getDefaultTemplate();
	}

	@Override
	public IAllele[] getRandomTemplate(final Random rand) {
		return this.speciesTemplates.values().toArray(new IAllele[0][])[rand.nextInt(this.speciesTemplates.values().size())];
	}

	@Override
	public ArrayList<IFlowerMutation> getMutations(final boolean shuffle) {
		if (shuffle) {
			Collections.shuffle(FlowerRoot.flowerMutations);
		}
		return FlowerRoot.flowerMutations;
	}

	@Override
	public void registerMutation(final IMutation mutation) {
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
	public IBotanistTracker getBreedingTracker(final World world, @Nullable final GameProfile player) {
		final String filename = "BotanistTracker." + ((player == null) ? "common" : player.getId());
		BotanistTracker tracker = (BotanistTracker) world.loadData(BotanistTracker.class, filename);
		if (tracker == null) {
			tracker = new BotanistTracker(filename);
			world.setData(filename, tracker);
		}
		return tracker;
	}

	@Override
	public IIndividual getMember(final NBTTagCompound compound) {
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
	public void addConversion(final ItemStack itemstack, final IAllele[] template) {
		final IFlower flower = this.getFlower(this.templateAsGenome(template));
		this.conversions.put(itemstack, flower);
	}

	@Override
	@Nullable
	public IFlower getConversion(final ItemStack itemstack) {
		for (final Map.Entry<ItemStack, IFlower> entry : this.conversions.entrySet()) {
			if (entry.getKey().isItemEqual(itemstack)) {
				return (IFlower) entry.getValue().copy();
			}
		}
		return null;
	}

	@Override
	public void registerColourMix(final IColourMix colourMix) {
		FlowerRoot.colourMixes.add(colourMix);
	}

	@Override
	public Collection<IColourMix> getColourMixes(final boolean shuffle) {
		if (shuffle) {
			Collections.shuffle(FlowerRoot.colourMixes);
		}
		return FlowerRoot.colourMixes;
	}
}
