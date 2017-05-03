package binnie.extratrees.genetics;

import forestry.api.arboriculture.EnumGrowthConditions;
import forestry.api.arboriculture.IAlleleGrowth;
import forestry.api.arboriculture.IGrowthProvider;
import forestry.api.arboriculture.ITreeGenome;
import net.minecraft.world.World;

// TODO unused class?
public class ExtraTreeGrowthProvider implements IAlleleGrowth, IGrowthProvider {
	@Override
	public String getUID() {
		return "extratrees.fruit." + toString().toLowerCase();
	}

	@Override
	public boolean isDominant() {
		return true;
	}

	@Override
	public IGrowthProvider getProvider() {
		return this;
	}

	@Override
	public boolean canGrow(ITreeGenome genome, World world, int xPos, int yPos, int zPos, int expectedGirth, int expectedHeight) {
		return false;
	}

	@Override
	public EnumGrowthConditions getGrowthConditions(ITreeGenome genome, World world, int xPos, int yPos, int zPos) {
		return null;
	}

	@Override
	public String getDescription() {
		return null;
	}

	@Override
	public String[] getInfo() {
		return null;
	}

	@Override
	public String getName() {
		return getProvider().getDescription();
	}

	@Override
	public String getUnlocalizedName() {
		return getUID();
	}
}
