package binnie.genetics.api.analyst;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.genetics.IIndividual;

import binnie.core.api.gui.IWidget;

public interface IBiologyPlugin<T extends IIndividual> {
	@SideOnly(Side.CLIENT)
	int addBiologyPages(T individual, IWidget parent, int y, IAnalystManager analystManager);
}
