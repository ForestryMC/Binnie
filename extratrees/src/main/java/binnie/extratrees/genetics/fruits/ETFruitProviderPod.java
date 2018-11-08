package binnie.extratrees.genetics.fruits;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.arboriculture.IAlleleFruit;
import forestry.api.arboriculture.ITreeGenome;
import forestry.api.arboriculture.TreeManager;
import forestry.api.genetics.IFruitFamily;

public class ETFruitProviderPod extends ETFruitProviderNone {
	@Nullable
	private final FruitPod pod;

	public ETFruitProviderPod(String name, IFruitFamily family, FruitPod pod) {
		super(name, family);
		this.pod = pod;
	}

	@Override
	public boolean trySpawnFruitBlock(ITreeGenome genome, World world, Random rand, BlockPos pos) {
		if (rand.nextFloat() > genome.getSappiness()) {
			return false;
		}

		IAlleleFruit activeAllele = (IAlleleFruit) genome.getActiveAllele(EnumTreeChromosome.FRUITS);
		return TreeManager.treeRoot.setFruitBlock(world, genome, activeAllele, genome.getYield(), pos);
	}

	@Override
	public NonNullList<ItemStack> getFruits(ITreeGenome genome, World world, BlockPos pos, int ripeningTime) {
		NonNullList<ItemStack> products = NonNullList.create();
		if (this.pod != null) {
			if (ripeningTime >= RIPENING_PERIOD) {
				for (Map.Entry<ItemStack, Float> product : this.products.entrySet()) {
					ItemStack single = product.getKey().copy();
					single.setCount(1);
					for (int i = 0; i < product.getKey().getCount(); ++i) {
						if (world.rand.nextFloat() <= product.getValue()) {
							products.add(single.copy());
						}
					}
				}
			}
		}
		return products;
	}

	@Nullable
	@Override
	public String getModelName() {
		if (pod != null) {
			return pod.getModelName();
		} else {
			return null;
		}
	}
}
