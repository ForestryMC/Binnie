package binnie.core.api.genetics;

import java.util.Map;

import forestry.api.genetics.IChromosomeType;

import binnie.core.api.gui.IPoint;
import binnie.core.api.gui.ITexture;

public interface IFieldKitPlugin {
	Map<IChromosomeType, IPoint> getChromosomePickerPositions();

	ITexture getTypeTexture();
}
