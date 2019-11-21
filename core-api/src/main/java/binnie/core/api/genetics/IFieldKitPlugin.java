package binnie.core.api.genetics;

import binnie.core.api.gui.IPoint;
import binnie.core.api.gui.ITexture;
import forestry.api.genetics.IChromosomeType;

import java.util.Map;

public interface IFieldKitPlugin {
	Map<IChromosomeType, IPoint> getChromosomePickerPositions();

	ITexture getTypeTexture();
}
