package binnie.core.api.genetics;

import java.util.Map;

import binnie.core.api.gui.IPoint;
import binnie.core.api.gui.ITexture;
import forestry.api.genetics.IChromosomeType;

public interface IFieldKitPlugin {
	Map<IChromosomeType, IPoint> getChromosomePickerPositions();

	ITexture getTypeTexture();
}
