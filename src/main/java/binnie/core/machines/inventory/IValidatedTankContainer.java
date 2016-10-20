// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.machines.inventory;

import net.minecraftforge.fluids.FluidStack;

public interface IValidatedTankContainer
{
	boolean isTankReadOnly(final int p0);

	boolean isLiquidValidForTank(final FluidStack p0, final int p1);
}
