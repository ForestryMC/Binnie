package binnie.genetics.api.analyst;

import forestry.api.genetics.IIndividual;

import binnie.core.api.gui.IWidget;

public interface IBehaviourPlugin<T extends IIndividual> {
	int addBehaviourPages(T individual, IWidget parent, int y);
}
