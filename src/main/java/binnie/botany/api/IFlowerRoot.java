package binnie.botany.api;

import com.mojang.authlib.GameProfile;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.ISpeciesRoot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.List;

public interface IFlowerRoot extends ISpeciesRoot {
    @Override
    IFlower getMember(ItemStack memberStack);

    @Override
    IFlower templateAsIndividual(IAllele[] template);

    @Override
    IFlower templateAsIndividual(IAllele[] templateFirst, IAllele[] templateSecond);

    @Override
    IFlowerGenome templateAsGenome(IAllele[] template);

    @Override
    IFlowerGenome templateAsGenome(IAllele[] templateFrist, IAllele[] templateSecond);

    @Override
    IBotanistTracker getBreedingTracker(World world, GameProfile profile);

    @Override
    List<? extends IFlowerMutation> getMutations(boolean shuffle);
    
    @Override
    EnumFlowerStage getType(ItemStack itemStack);
    
    IFlower getFlower(World world, IFlowerGenome genome);

    void addConversion(ItemStack memberStack, IAllele[] template);

    IFlower getConversion(ItemStack memberStack);

    Collection<IColourMix> getColourMixes(boolean p0);

    void registerColourMix(IColourMix colourMix);
}
