package binnie.extrabees.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IHiveDrop;
import forestry.api.genetics.IAllele;
import forestry.apiculture.genetics.IBeeDefinition;

public class HiveDrop implements IHiveDrop {

	private final IAllele[] template;
	private final NonNullList<ItemStack> extra;
	private final double chance;

	public HiveDrop(IBeeDefinition species, double chance) {
		this(species.getTemplate(), NonNullList.create(), chance);
	}

	public HiveDrop(IAlleleBeeSpecies species, double chance) {
		this(Utils.getBeeRoot().getTemplate(species), NonNullList.create(), chance);
	}

	public HiveDrop(IAllele[] template, NonNullList<ItemStack> extra, double chance) {
		this.extra = extra;
		this.template = template;
		this.chance = chance;
	}

	@Override
	public NonNullList<ItemStack> getExtraItems(IBlockAccess world, BlockPos pos, int fortune) {
		final NonNullList<ItemStack> ret = NonNullList.create();
		for (final ItemStack stack : this.extra) {
			ret.add(stack.copy());
		}
		return ret;
	}

	@Override
	public double getChance(IBlockAccess world, BlockPos pos, int fortune) {
		return chance;
	}

	@Override
	public double getIgnobleChance(IBlockAccess world, BlockPos pos, int fortune) {
		return 0.5; //TODO implement
	}

	@Override
	public IBee getBeeType(IBlockAccess world, BlockPos pos) {
		return Utils.getBeeRoot().getBee(Utils.getBeeRoot().templateAsGenome(this.template));
	}
}
