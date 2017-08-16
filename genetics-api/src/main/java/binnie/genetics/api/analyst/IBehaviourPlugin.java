package binnie.genetics.api.analyst;

import binnie.core.api.gui.IWidget;
import forestry.api.genetics.IIndividual;

public interface IBehaviourPlugin<T extends IIndividual> {
	int addBehaviourPages(T individual, IWidget parent, int y);
}
