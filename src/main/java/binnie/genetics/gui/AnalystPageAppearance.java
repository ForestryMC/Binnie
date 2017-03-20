package binnie.genetics.gui;

import binnie.botany.api.IFlower;
import binnie.botany.craftgui.ControlColourDisplay;
import binnie.craftgui.controls.ControlTextCentered;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.minecraft.control.ControlIconDisplay;
import forestry.api.genetics.IAlleleSpecies;

public class AnalystPageAppearance extends ControlAnalystPage {
	public AnalystPageAppearance(final IWidget parent, final IArea area, final IFlower ind) {
		super(parent, area);
		this.setColour(3355443);
		int y = 4;
		final IAlleleSpecies species = ind.getGenome().getPrimary();
		new ControlTextCentered(this, y, "§nAppearance").setColour(this.getColour());
		y += 12;
		final ControlColourDisplay a = new ControlColourDisplay(this, this.width() / 2 - 28, y, ind.getGenome().getPrimaryColor());
		a.addTooltip("Primary Petal Colour");
		final ControlColourDisplay b = new ControlColourDisplay(this, this.width() / 2 - 8, y, ind.getGenome().getSecondaryColor());
		b.addTooltip("Secondary Petal Colour");
		final ControlColourDisplay c = new ControlColourDisplay(this, this.width() / 2 + 12, y, ind.getGenome().getStemColor());
		c.addTooltip("Stem Colour");
		y += 26;
		final int sections = ind.getGenome().getType().getSections();
		final int w = (sections > 1) ? 50 : 100;
		// TODO fix this control icon display
		new ControlIconDisplay(this, (this.width() - w) / 2, y - ((sections == 1) ? 0 : 0), null) {
			@Override
			public void onRenderForeground(int guiWidth, int guiHeight) {
//				GlStateManager.pushMatrix();
//				final float scale = w / 16.0f;
//				final float dy = (sections > 1) ? 16.0f : 0.0f;
//				GlStateManager.scale(scale, scale, 1.0f);
//				CraftGUI.Render.colour(ind.getGenome().getStemColor().getColor(false));
//				if (sections > 1) {
//					CraftGUI.Render.iconBlock(IPoint.ZERO, ind.getGenome().getType().getStem(EnumFlowerStage.FLOWER, true, 1));
//				}
//				CraftGUI.Render.iconBlock(new IPoint(0, dy), ind.getGenome().getType().getStem(EnumFlowerStage.FLOWER, true, 0));
//				CraftGUI.Render.colour(ind.getGenome().getPrimaryColor().getColor(false));
//				if (sections > 1) {
//					CraftGUI.Render.iconBlock(IPoint.ZERO, ind.getGenome().getType().getPetalIcon(EnumFlowerStage.FLOWER, true, 1));
//				}
//				CraftGUI.Render.iconBlock(new IPoint(0, dy), ind.getGenome().getType().getPetalIcon(EnumFlowerStage.FLOWER, true, 0));
//				CraftGUI.Render.colour(ind.getGenome().getSecondaryColor().getColor(false));
//				if (sections > 1) {
//					CraftGUI.Render.iconBlock(IPoint.ZERO, ind.getGenome().getType().getVariantIcon(EnumFlowerStage.FLOWER, true, 1));
//				}
//				CraftGUI.Render.iconBlock(new IPoint(0, dy), ind.getGenome().getType().getVariantIcon(EnumFlowerStage.FLOWER, true, 0));
//				GlStateManager.popMatrix();
			}
		};
	}

	@Override
	public String getTitle() {
		return "Appearance";
	}
}
