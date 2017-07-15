package binnie.botany.api;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.mojang.authlib.GameProfile;

import forestry.api.genetics.IAllele;
import forestry.api.genetics.ISpeciesRoot;

public interface IFlowerRoot extends ISpeciesRoot {
	@Override
	@Nullable
	IFlower getMember(ItemStack memberStack);

	@Override
	IFlower templateAsIndividual(IAllele[] template);

	@Override
	IFlower templateAsIndividual(IAllele[] templateFirst, IAllele[] templateSecond);

	@Override
	IFlowerGenome templateAsGenome(IAllele[] template);

	@Override
	IFlowerGenome templateAsGenome(IAllele[] templateActive, IAllele[] templateInactive);

	@Override
	IBotanistTracker getBreedingTracker(World world, @Nullable GameProfile profile);

	@Override
	List<? extends IFlowerMutation> getMutations(boolean shuffle);

	@Override
	@Nullable
	EnumFlowerStage getType(ItemStack itemStack);

	IFlower getFlower(IFlowerGenome genome);

	void addConversion(ItemStack memberStack, IAllele[] template);

	@Nullable
	IFlower getConversion(ItemStack memberStack);

	Collection<IColorMix> getColorMixes(boolean shuffle);

	void registerColourMix(IColorMix colorMix);

	boolean plant(World world, BlockPos pos, IFlower flower, GameProfile owner);

	void tryGrowSection(World world, BlockPos pos);

	void onGrowFromSeed(World world, BlockPos pos);
}
