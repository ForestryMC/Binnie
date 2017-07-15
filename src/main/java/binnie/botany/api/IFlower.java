package binnie.botany.api;

import javax.annotation.Nullable;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import forestry.api.genetics.IIndividual;

public interface IFlower extends IIndividual {
	@Override
	IFlowerGenome getGenome();

	@Nullable
	IFlowerGenome getMate();

	void mate(IFlower flower);

	int getAge();

	void setAge(int age);

	void age();

	IFlower getOffspring(World world, BlockPos pos);

	int getMaxAge();

	boolean isWilted();

	void setWilted(boolean p0);

	boolean hasFlowered();

	void setFlowered(boolean p0);

	void removeMate();
}
