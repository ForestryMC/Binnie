package binnie.botany.api.genetics;

import javax.annotation.Nullable;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import forestry.api.genetics.IIndividual;

public interface IFlower extends IIndividual {
	/**
	 * @return Flowers's genetic information.
	 */
	@Override
	IFlowerGenome getGenome();

	/**
	 * @return Genetic information of the flower's mate, null if unmated.
	 */
	@Nullable
	IFlowerGenome getMate();

	/**
	 * Mate with the given flower.
	 *
	 * @param flower the {@link IFlower} to mate this one with.
	 */
	void mate(IFlower flower);

	/**
	 * Remove the mate of the flower.
	 */
	void removeMate();

	IFlower getOffspring(World world, BlockPos pos);

	/**
	 * @return The current age of the flower.
	 */
	int getAge();

	/**
	 * Set the current age of the flower.
	 */
	void setAge(int age);

	/**
	 * Age the flower.
	 */
	void age();

	/**
	 * @return Maximum age of the flower.
	 */
	int getMaxAge();

	/**
	 * @return true if the flower is wilted.
	 */
	boolean isWilted();

	/**
	 * Set that the flower is wilted.
	 */
	void setWilted(boolean wilted);

	/**
	 * @return true if the flower has flowered.
	 */
	boolean hasFlowered();

	/**
	 * Set that the flower has flowered.
	 */
	void setFlowered(boolean flowered);
}
