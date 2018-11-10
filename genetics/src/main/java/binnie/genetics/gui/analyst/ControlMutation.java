package binnie.genetics.gui.analyst;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IMutation;

import binnie.core.api.genetics.IBreedingSystem;
import binnie.core.api.gui.IWidget;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.minecraft.EnumColor;
import binnie.core.gui.renderer.RenderUtil;
import binnie.core.resource.BinnieSprite;

public abstract class ControlMutation extends Control {
	protected final IMutation mutation;
	protected final float specificChance;
	protected final IBreedingSystem system;
	protected final IAlleleSpecies firstSpecies;
	protected final IAlleleSpecies secondSpecies;

	protected ControlMutation(IWidget parent, int x, int y, IMutation mutation, float specificChance, IBreedingSystem system, IAlleleSpecies firstSpecies, IAlleleSpecies secondSpecies) {
		super(parent, x, y, 44, 16);
		this.mutation = mutation;
		this.specificChance = specificChance;
		this.system = system;
		this.firstSpecies = firstSpecies;
		this.secondSpecies = secondSpecies;
	}

	protected EnumColor getMutationColour(float percent) {
		if (percent >= 20.0f) {
			return EnumColor.DARK_GREEN;
		}
		if (percent >= 15.0f) {
			return EnumColor.GREEN;
		}
		if (percent >= 10.0f) {
			return EnumColor.YELLOW;
		}
		if (percent >= 5.0f) {
			return EnumColor.GOLD;
		}
		if (percent > 0.0f) {
			return EnumColor.RED;
		}
		return EnumColor.DARK_RED;
	}

	@SideOnly(Side.CLIENT)
	protected void drawSprite(BinnieSprite sprite) {
		RenderUtil.drawSprite(new Point(14, 0), sprite.getSprite());
	}
}
