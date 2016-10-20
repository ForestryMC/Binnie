// 
// Decompiled by Procyon v0.5.30
// 

package binnie.botany.api;

import java.util.Collection;
import com.mojang.authlib.GameProfile;
import net.minecraft.world.World;
import forestry.api.genetics.IAllele;
import net.minecraft.item.ItemStack;
import forestry.api.genetics.ISpeciesRoot;

public interface IFlowerRoot extends ISpeciesRoot
{
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
	Collection<IFlowerMutation> getMutations(final boolean p0);

	EnumFlowerStage getStageType(final ItemStack p0);

	IFlower getFlower(final World p0, final IFlowerGenome p1);

	void addConversion(final ItemStack p0, final IAllele[] p1);

	IFlower getConversion(final ItemStack p0);

	Collection<IColourMix> getColourMixes(final boolean p0);

	void registerColourMix(final IColourMix p0);
}
