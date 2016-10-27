package binnie.extrabees.worldgen;

import binnie.Binnie;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IHiveDrop;
import forestry.api.genetics.IAllele;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class HiveDrop implements IHiveDrop {
	private IAllele[] template;
	private ArrayList<ItemStack> additional;
	private int chance;

	public HiveDrop(final IAlleleBeeSpecies species, final int chance) {
		this(Binnie.Genetics.getBeeRoot().getTemplate(species.getUID()), new ItemStack[0], chance);
	}

	public HiveDrop(IAllele[] template, final ItemStack[] bonus, final int chance) {
		this.additional = new ArrayList<>();
		if (template == null) {
			template = Binnie.Genetics.getBeeRoot().getDefaultTemplate();
		}
		this.template = template;
		this.chance = chance;
		Collections.addAll(this.additional, bonus);
	}

	@Override
	public Collection<ItemStack> getExtraItems(IBlockAccess world, BlockPos pos, int fortune) {
		final ArrayList<ItemStack> ret = new ArrayList<>();
		for (final ItemStack stack : this.additional) {
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
		return 0; //TODO implement
	}

	@Override
	public IBee getBeeType(IBlockAccess world, BlockPos pos) {
		return Binnie.Genetics.getBeeRoot().getBee(Binnie.Genetics.getBeeRoot().templateAsGenome(this.template));
	}

}
