package binnie.botany.genetics;

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
import com.mojang.authlib.GameProfile;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.IMutation;
import forestry.api.genetics.ISpeciesType;
import forestry.core.genetics.SpeciesRoot;
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

public class FlowerHelper extends SpeciesRoot implements IFlowerRoot {
    public static int flowerSpeciesCount = -1;
    public static ArrayList<IFlower> flowerTemplates = new ArrayList<>();

    protected static String UID = "rootFlowers";

    private static ArrayList<IFlowerMutation> flowerMutations = new ArrayList<>();
    private static ArrayList<IColourMix> colourMixes = new ArrayList<>();

    protected Map<ItemStack, IFlower> conversions;

    public FlowerHelper() {
        conversions = new HashMap<>();
    }

    @Override
    public String getUID() {
        return UID;
    }

    @Override
    public int getSpeciesCount() {
        if (FlowerHelper.flowerSpeciesCount < 0) {
            FlowerHelper.flowerSpeciesCount = 0;
            for (Map.Entry<String, IAllele> entry :
                    AlleleManager.alleleRegistry.getRegisteredAlleles().entrySet()) {
                if (entry.getValue() instanceof IAlleleFlowerSpecies
                        && ((IAlleleFlowerSpecies) entry.getValue()).isCounted()) {
                    FlowerHelper.flowerSpeciesCount++;
                }
            }
        }
        return FlowerHelper.flowerSpeciesCount;
    }

    @Override
    public boolean isMember(ItemStack stack) {
        return stack != null && getStageType(stack) != EnumFlowerStage.NONE;
    }

    @Override
    public boolean isMember(ItemStack stack, int type) {
        return getStageType(stack).ordinal() == type;
    }

    @Override
    public boolean isMember(IIndividual individual) {
        return individual instanceof IFlower;
    }

    @Override
    public ItemStack getMemberStack(IIndividual flower, int type) {
        if (!isMember(flower)) {
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

        NBTTagCompound nbttagcompound = new NBTTagCompound();
        flower.writeToNBT(nbttagcompound);
        ItemStack flowerStack = new ItemStack(flowerItem);
        flowerStack.setTagCompound(nbttagcompound);
        return flowerStack;
    }

    @Override
    public EnumFlowerStage getStageType(ItemStack stack) {
        if (stack == null) {
            return EnumFlowerStage.NONE;
        }
        if (stack.getItem() == Botany.flowerItem) {
            return EnumFlowerStage.FLOWER;
        }
        if (stack.getItem() == Botany.pollen) {
            return EnumFlowerStage.POLLEN;
        }
        if (stack.getItem() == Botany.seed) {
            return EnumFlowerStage.SEED;
        }
        return EnumFlowerStage.NONE;
    }

    @Override
    public IFlower getMember(ItemStack stack) {
        if (!isMember(stack)) {
            return null;
        }
        return new Flower(stack.getTagCompound());
    }

    @Override
    public IFlower getFlower(World world, IFlowerGenome genome) {
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
        return FlowerHelper.flowerTemplates;
    }

    @Override
    public void registerTemplate(IAllele[] template) {
        registerTemplate(template[0].getUID(), template);
    }

    @Override
    public void registerTemplate(String identifier, IAllele[] template) {
        FlowerHelper.flowerTemplates.add(new Flower(templateAsGenome(template), 2));
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
        return speciesTemplates.values()
                .toArray(new IAllele[0][])[
                rand.nextInt(speciesTemplates.values().size())];
    }

    @Override
    public Collection<IFlowerMutation> getMutations(boolean shuffle) {
        if (shuffle) {
            Collections.shuffle(FlowerHelper.flowerMutations);
        }
        return FlowerHelper.flowerMutations;
    }

    @Override
    public void registerMutation(IMutation mutation) {
        if (AlleleManager.alleleRegistry.isBlacklisted(mutation.getTemplate()[0].getUID())
                || AlleleManager.alleleRegistry.isBlacklisted(
                        mutation.getAllele0().getUID())
                || AlleleManager.alleleRegistry.isBlacklisted(
                        mutation.getAllele1().getUID())) {
            return;
        }
        FlowerHelper.flowerMutations.add((IFlowerMutation) mutation);
    }

    @Override
    public IBotanistTracker getBreedingTracker(World world, GameProfile player) {
        String filename = "BotanistTracker." + ((player == null) ? "common" : player.getId());
        BotanistTracker tracker = (BotanistTracker) world.loadItemData(BotanistTracker.class, filename);
        if (tracker == null) {
            tracker = new BotanistTracker(filename);
            world.setItemData(filename, tracker);
        }
        return tracker;
    }

    @Override
    public IIndividual getMember(NBTTagCompound compound) {
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
    public void addConversion(ItemStack stack, IAllele[] template) {
        IFlower flower = getFlower(null, templateAsGenome(template));
        conversions.put(stack, flower);
    }

    @Override
    public IFlower getConversion(ItemStack stack) {
        for (Map.Entry<ItemStack, IFlower> entry : conversions.entrySet()) {
            if (entry.getKey().isItemEqual(stack)) {
                return (IFlower) entry.getValue().copy();
            }
        }
        return null;
    }

    @Override
    public void registerColourMix(IColourMix colorMix) {
        FlowerHelper.colourMixes.add(colorMix);
    }

    @Override
    public Collection<IColourMix> getColourMixes(boolean shuffle) {
        if (shuffle) {
            Collections.shuffle(FlowerHelper.colourMixes);
        }
        return FlowerHelper.colourMixes;
    }

    // ??
    @Override
    public ISpeciesType getType(ItemStack stack) {
        return null;
    }
}
