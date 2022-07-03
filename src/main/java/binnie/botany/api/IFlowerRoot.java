package binnie.botany.api;

import com.mojang.authlib.GameProfile;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.ISpeciesRoot;
import java.util.Collection;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IFlowerRoot extends ISpeciesRoot {
    @Override
    IFlower getMember(ItemStack stack);

    @Override
    IFlower templateAsIndividual(IAllele[] template);

    @Override
    IFlower templateAsIndividual(IAllele[] templateActive, IAllele[] templateInactive);

    @Override
    IFlowerGenome templateAsGenome(IAllele[] template);

    @Override
    IFlowerGenome templateAsGenome(IAllele[] templateActive, IAllele[] templateInactive);

    @Override
    IBotanistTracker getBreedingTracker(World world, GameProfile player);

    @Override
    Collection<IFlowerMutation> getMutations(boolean shuffle);

    EnumFlowerStage getStageType(ItemStack stack);

    IFlower getFlower(World world, IFlowerGenome genome);

    void addConversion(ItemStack stack, IAllele[] template);

    IFlower getConversion(ItemStack stack);

    Collection<IColourMix> getColourMixes(boolean shuffle);

    void registerColourMix(IColourMix colorMix);
}
