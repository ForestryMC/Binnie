// 
// Decompiled by Procyon v0.5.30
// 

package binnie.botany.genetics;

import forestry.api.genetics.ISpeciesType;
import binnie.botany.api.EnumFlowerChromosome;
import forestry.api.genetics.IChromosomeType;

import net.minecraft.world.WorldSavedData;

import binnie.botany.api.IBotanistTracker;

import com.mojang.authlib.GameProfile;

import forestry.api.genetics.IMutation;

import java.util.Collections;
import java.util.Collection;
import java.util.Random;

import binnie.botany.api.IFlowerGenome;

import net.minecraft.world.World;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;

import binnie.botany.Botany;
import forestry.api.genetics.IIndividual;
import binnie.botany.api.EnumFlowerStage;

import binnie.botany.api.IAlleleFlowerSpecies;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.AlleleManager;

import java.util.HashMap;

import binnie.botany.api.IColourMix;

import net.minecraft.item.ItemStack;

import java.util.Map;

import binnie.botany.api.IFlowerMutation;
import binnie.botany.api.IFlower;

import java.util.ArrayList;

import binnie.botany.api.IFlowerRoot;
import forestry.core.genetics.SpeciesRoot;

public class FlowerHelper extends SpeciesRoot implements IFlowerRoot
{
	public static int flowerSpeciesCount;
	static final String UID = "rootFlowers";
	public static ArrayList<IFlower> flowerTemplates;
	private static ArrayList<IFlowerMutation> flowerMutations;
	Map<ItemStack, IFlower> conversions;
	private static ArrayList<IColourMix> colourMixes;

	public FlowerHelper() {
		this.conversions = new HashMap<ItemStack, IFlower>();
	}

	@Override
	public String getUID() {
		return "rootFlowers";
	}

	@Override
	public int getSpeciesCount() {
		if (FlowerHelper.flowerSpeciesCount < 0) {
			FlowerHelper.flowerSpeciesCount = 0;
			for (final Map.Entry<String, IAllele> entry : AlleleManager.alleleRegistry.getRegisteredAlleles().entrySet()) {
				if (entry.getValue() instanceof IAlleleFlowerSpecies && ((IAlleleFlowerSpecies) entry.getValue()).isCounted()) {
					++FlowerHelper.flowerSpeciesCount;
				}
			}
		}
		return FlowerHelper.flowerSpeciesCount;
	}

	@Override
	public boolean isMember(final ItemStack stack) {
		return stack != null && this.getStageType(stack) != EnumFlowerStage.NONE;
	}

	@Override
	public boolean isMember(final ItemStack stack, final int type) {
		return this.getStageType(stack).ordinal() == type;
	}

	@Override
	public boolean isMember(final IIndividual individual) {
		return individual instanceof IFlower;
	}

	@Override
	public ItemStack getMemberStack(final IIndividual flower, final int type) {
		if (!this.isMember(flower)) {
			return null;
		}
		Item flowerItem = Botany.flowerItem;
		if (type == EnumFlowerStage.SEED.ordinal()) {
			flowerItem = Botany.seed;
		}
		if (type == EnumFlowerStage.POLLEN.ordinal()) {
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
	public EnumFlowerStage getStageType(final ItemStack stack) {
		return (stack == null) ? EnumFlowerStage.NONE : ((stack.getItem() == Botany.flowerItem) ? EnumFlowerStage.FLOWER : ((stack.getItem() == Botany.pollen) ? EnumFlowerStage.POLLEN : ((stack.getItem() == Botany.seed) ? EnumFlowerStage.SEED : EnumFlowerStage.NONE)));
	}

	@Override
	public IFlower getMember(final ItemStack stack) {
		if (!this.isMember(stack)) {
			return null;
		}
		return new Flower(stack.getTagCompound());
	}

	@Override
	public IFlower getFlower(final World world, final IFlowerGenome genome) {
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
		return FlowerHelper.flowerTemplates;
	}

	@Override
	public void registerTemplate(final IAllele[] template) {
		this.registerTemplate(template[0].getUID(), template);
	}

	@Override
	public void registerTemplate(final String identifier, final IAllele[] template) {
		FlowerHelper.flowerTemplates.add(new Flower(this.templateAsGenome(template), 2));
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
	public Collection<IFlowerMutation> getMutations(final boolean shuffle) {
		if (shuffle) {
			Collections.shuffle(FlowerHelper.flowerMutations);
		}
		return FlowerHelper.flowerMutations;
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
		FlowerHelper.flowerMutations.add((IFlowerMutation) mutation);
	}

	@Override
	public IBotanistTracker getBreedingTracker(final World world, final GameProfile player) {
		final String filename = "BotanistTracker." + ((player == null) ? "common" : player.getId());
		BotanistTracker tracker = (BotanistTracker) world.loadItemData(BotanistTracker.class, filename);
		if (tracker == null) {
			tracker = new BotanistTracker(filename);
			world.setItemData(filename, tracker);
		}
		return tracker;
	}

	@Override
	public IIndividual getMember(final NBTTagCompound compound) {
		return new Flower(compound);
	}

	@Override
	public Class getMemberClass() {
		return IFlower.class;
	}

	@Override
	public IChromosomeType[] getKaryotype() {
		return EnumFlowerChromosome.values();
	}

	@Override
	public IChromosomeType getKaryotypeKey() {
		return EnumFlowerChromosome.SPECIES;
	}

	@Override
	public void addConversion(final ItemStack itemstack, final IAllele[] template) {
		final IFlower flower = this.getFlower(null, this.templateAsGenome(template));
		this.conversions.put(itemstack, flower);
	}

	@Override
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
		FlowerHelper.colourMixes.add(colourMix);
	}

	@Override
	public Collection<IColourMix> getColourMixes(final boolean shuffle) {
		if (shuffle) {
			Collections.shuffle(FlowerHelper.colourMixes);
		}
		return FlowerHelper.colourMixes;
	}

	static {
		FlowerHelper.flowerSpeciesCount = -1;
		FlowerHelper.flowerTemplates = new ArrayList<IFlower>();
		FlowerHelper.flowerMutations = new ArrayList<IFlowerMutation>();
		FlowerHelper.colourMixes = new ArrayList<IColourMix>();
	}

	// ??
	@Override
	public ISpeciesType getType(ItemStack itemstack) {
		return null;
	}
}
