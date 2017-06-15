package binnie.botany.farm;

import forestry.api.farming.ICrop;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.Arrays;

public class FlowerCrop implements ICrop {
	NonNullList<ItemStack> drops = NonNullList.create();
	BlockPos position;

	public FlowerCrop(BlockPos pos, ItemStack... drops) {
		Arrays.stream(drops).forEach(drop -> this.drops.add(drop));
		position = pos;
	}

	@Nullable
	@Override
	public NonNullList<ItemStack> harvest() {
		return drops;
	}

	@Override
	public BlockPos getPosition() {
		return position;
	}
}
