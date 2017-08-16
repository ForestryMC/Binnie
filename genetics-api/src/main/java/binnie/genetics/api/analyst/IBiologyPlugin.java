package binnie.genetics.api.analyst;

import binnie.core.api.gui.IWidget;
import forestry.api.genetics.IIndividual;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IBiologyPlugin<T extends IIndividual> {
	@SideOnly(Side.CLIENT)
	int addBiologyPages(T individual, IWidget parent, int y, IAnalystManager analystManager);
}
