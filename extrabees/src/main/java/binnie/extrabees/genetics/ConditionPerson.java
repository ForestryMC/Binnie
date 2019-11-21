package binnie.extrabees.genetics;

import forestry.api.apiculture.IBeeHousing;
import forestry.api.climate.IClimateProvider;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IGenome;
import forestry.api.genetics.IMutationCondition;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ConditionPerson implements IMutationCondition {

	private final String name;

	public ConditionPerson(final String name) {
		this.name = name;
	}

	@Override
	public String getDescription() {
		return "Can only be bred by " + this.name;
	}

	@Override
	public float getChance(World world, BlockPos pos, IAllele allele0, IAllele allele1, IGenome genome0, IGenome genome1, IClimateProvider climate) {
		TileEntity tileEnity = world.getTileEntity(pos);
		if (tileEnity instanceof IBeeHousing) {
			IBeeHousing housing = (IBeeHousing) tileEnity;
			if (housing.getOwner() != null && housing.getOwner().getName() != null && housing.getOwner().getName().equals(this.name)) {
				return 1;
			}
		}
		return 0;
	}
}
