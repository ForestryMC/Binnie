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
    IFlower getMember(final ItemStack p0);

    @Override
    IFlower templateAsIndividual(final IAllele[] p0);

    @Override
    IFlower templateAsIndividual(final IAllele[] p0, final IAllele[] p1);

    @Override
    IFlowerGenome templateAsGenome(final IAllele[] p0);

    @Override
    IFlowerGenome templateAsGenome(final IAllele[] p0, final IAllele[] p1);

    @Override
    IBotanistTracker getBreedingTracker(final World p0, final GameProfile p1);

    @Override
    List<? extends IFlowerMutation> getMutations(boolean shuffle);

    EnumFlowerStage getStageType(final ItemStack p0);

    IFlower getFlower(final World p0, final IFlowerGenome p1);

    void addConversion(final ItemStack p0, final IAllele[] p1);

    IFlower getConversion(final ItemStack p0);

    Collection<IColourMix> getColourMixes(final boolean p0);

    void registerColourMix(final IColourMix p0);
}
