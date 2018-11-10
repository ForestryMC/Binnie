package binnie.genetics.api.analyst;

import java.util.List;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.genetics.IIndividual;

import binnie.core.api.gui.IArea;
import binnie.core.api.gui.ITitledWidget;
import binnie.core.api.gui.IWidget;

public interface IAnalystPagePlugin<T extends IIndividual> {
	boolean handles(IIndividual individual);

	@SideOnly(Side.CLIENT)
	void addAnalystPages(T individual, IWidget parent, IArea pageSize, List<ITitledWidget> analystPages, IAnalystManager analystManager);
}
