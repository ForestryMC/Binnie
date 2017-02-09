package binnie.botany.api;

import forestry.api.genetics.IIndividual;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public interface IFlower extends IIndividual {
	@Override
	IFlowerGenome getGenome();

	@Nullable
	IFlowerGenome getMate();

	void mate(final IFlower flower);

	int getAge();

	void age();

	void setAge(final int age);

	IFlower getOffspring(final World world, final BlockPos pos);

	int getMaxAge();

	boolean isWilted();

	void setWilted(final boolean p0);

	boolean hasFlowered();

	void setFlowered(final boolean p0);

	void removeMate();
}
