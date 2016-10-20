// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.genetics;

import forestry.api.arboriculture.EnumGrowthConditions;
import net.minecraft.world.World;
import forestry.api.arboriculture.ITreeGenome;
import forestry.api.arboriculture.IGrowthProvider;
import forestry.api.arboriculture.IAlleleGrowth;

public class ExtraTreeGrowthProvider implements IAlleleGrowth, IGrowthProvider
{
	@Override
	public String getUID() {
		return "extratrees.fruit." + this.toString().toLowerCase();
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
	public boolean canGrow(final ITreeGenome genome, final World world, final int xPos, final int yPos, final int zPos, final int expectedGirth, final int expectedHeight) {
		return false;
	}

	@Override
	public EnumGrowthConditions getGrowthConditions(final ITreeGenome genome, final World world, final int xPos, final int yPos, final int zPos) {
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
		return this.getProvider().getDescription();
	}

	@Override
	public String getUnlocalizedName() {
		return this.getUID();
	}
}
