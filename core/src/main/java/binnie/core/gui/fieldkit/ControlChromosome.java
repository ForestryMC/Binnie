package binnie.core.gui.fieldkit;

import com.google.common.base.Preconditions;

import javax.annotation.Nullable;
import java.util.Map;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.ISpeciesRoot;

import binnie.core.api.genetics.IBreedingSystem;
import binnie.core.api.genetics.IFieldKitPlugin;
import binnie.core.api.gui.IPoint;
import binnie.core.api.gui.ITexture;
import binnie.core.api.gui.IWidget;
import binnie.core.gui.CraftGUI;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.controls.core.IControlValue;
import binnie.core.gui.geometry.Point;

public class ControlChromosome extends Control implements IControlValue<IChromosomeType> {
	@Nullable
	private IChromosomeType chromo;
	@Nullable
	private IBreedingSystem breedingSystem;

	public ControlChromosome(IWidget parent, int x, int y) {
		super(parent, x, y, 96, 96);
	}

	public ISpeciesRoot getRoot() {
		Preconditions.checkState(breedingSystem != null, "root has not been set");
		return breedingSystem.getSpeciesRoot();
	}

	public void setSystem(@Nullable IBreedingSystem breedingSystem) {
		if (this.breedingSystem != breedingSystem) {
			this.breedingSystem = breedingSystem;
			deleteAllChildren();

			if (breedingSystem != null) {
				IFieldKitPlugin fieldKitPlugin = breedingSystem.getFieldKitPlugin();
				fieldKitPlugin.getChromosomePickerPositions();

				for (Map.Entry<IChromosomeType, IPoint> entry : fieldKitPlugin.getChromosomePickerPositions().entrySet()) {
					IChromosomeType chromosomeType = entry.getKey();
					IPoint position = entry.getValue();
					new ControlChromoPicker(this, position.xPos(), position.yPos(), chromosomeType);
				}
			}
		}
	}

	@Override
	public IChromosomeType getValue() {
		return chromo;
	}

	@Override
	public void setValue(IChromosomeType value) {
		chromo = value;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		if (breedingSystem == null) {
			return;
		}
		super.onRenderBackground(guiWidth, guiHeight);
		ITexture texture = getTypeTexture();
		CraftGUI.RENDER.texture(texture, Point.ZERO);
	}

	@Nullable
	private ITexture getTypeTexture() {
		if (breedingSystem == null) {
			return null;
		}
		IFieldKitPlugin fieldKitPlugin = breedingSystem.getFieldKitPlugin();
		return fieldKitPlugin.getTypeTexture();
	}
}
