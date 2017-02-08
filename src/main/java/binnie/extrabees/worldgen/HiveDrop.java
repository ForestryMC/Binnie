package binnie.extrabees.worldgen;

import binnie.Binnie;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IHiveDrop;
import forestry.api.genetics.IAllele;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class HiveDrop implements IHiveDrop {
	private IAllele[] template;
	private NonNullList<ItemStack> extra;
	private int chance;

	public HiveDrop(final IAlleleBeeSpecies species, final int chance) {
		this(Binnie.GENETICS.getBeeRoot().getTemplate(species), NonNullList.create(), chance);
	}

	public HiveDrop(IAllele[] template, final NonNullList<ItemStack> extra, final int chance) {
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
		return Binnie.GENETICS.getBeeRoot().getBee(Binnie.GENETICS.getBeeRoot().templateAsGenome(this.template));
	}

}
