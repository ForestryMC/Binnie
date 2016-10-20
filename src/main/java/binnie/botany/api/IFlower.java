// 
// Decompiled by Procyon v0.5.30
// 

package binnie.botany.api;

import net.minecraft.world.World;
import forestry.api.genetics.IIndividual;

public interface IFlower extends IIndividual
{
	@Override
	IFlowerGenome getGenome();

	IFlowerGenome getMate();

	void mate(final IFlower p0);

	int getAge();

	void age();

	void setAge(final int p0);

	IFlower getOffspring(final World p0);

	int getMaxAge();

	boolean isWilted();

	void setWilted(final boolean p0);

	boolean hasFlowered();

	void setFlowered(final boolean p0);

	void removeMate();
}
