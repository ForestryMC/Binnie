package binnie.extrabees.worldgen;

import binnie.Binnie;
import forestry.api.apiculture.EnumBeeType;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IBeeRoot;
import forestry.api.apiculture.IHiveDrop;
import forestry.api.genetics.IAllele;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.ArrayList;

public class HiveDrop implements IHiveDrop {
	private IAllele[] template;
	private ArrayList<ItemStack> additional;
	private int chance;

	public HiveDrop(IAlleleBeeSpecies species, int chance) {
		this(Binnie.Genetics.getBeeRoot().getTemplate(species.getUID()), new ItemStack[0], chance);
	}

	public HiveDrop(IAllele[] template, ItemStack[] bonus, int chance) {
		additional = new ArrayList<>();
		if (template == null) {
			template = Binnie.Genetics.getBeeRoot().getDefaultTemplate();
		}
		this.template = template;
		this.chance = chance;
		for (ItemStack stack : bonus) {
			additional.add(stack);
		}
	}

	@Override
	public ItemStack getPrincess(World world, int x, int y, int z, int fortune) {
		IBeeRoot beeRoot = Binnie.Genetics.getBeeRoot();
		return beeRoot.getMemberStack(beeRoot.getBee(world, beeRoot.templateAsGenome(template)), EnumBeeType.PRINCESS.ordinal());
	}

	@Override
	public ArrayList<ItemStack> getDrones(World world, int x, int y, int z, int fortune) {
		ArrayList<ItemStack> ret = new ArrayList<>();
		IBeeRoot beeRoot = Binnie.Genetics.getBeeRoot();
		ret.add(beeRoot.getMemberStack(beeRoot.templateAsIndividual(template), EnumBeeType.DRONE.ordinal()));
		return ret;
	}

	@Override
	public ArrayList<ItemStack> getAdditional(World world, int x, int y, int z, int fortune) {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		for (ItemStack stack : additional) {
			ret.add(stack.copy());
		}
		return ret;
	}

	@Override
	public int getChance(World world, int x, int y, int z) {
		return chance;
	}
}
