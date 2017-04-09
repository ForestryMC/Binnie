package binnie.genetics.gui.flower;

import binnie.botany.api.IFlower;
import binnie.botany.api.IFlowerGenome;
import binnie.botany.api.IFlowerType;
import binnie.botany.craftgui.ControlColourDisplay;
import binnie.botany.flower.FlowerSpriteManager;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.geometry.Area;
import binnie.core.craftgui.geometry.Point;
import binnie.core.craftgui.minecraft.control.ControlIconDisplay;
import binnie.core.craftgui.renderer.RenderUtil;
import binnie.genetics.Genetics;
import binnie.genetics.gui.ControlAnalystPage;
import forestry.api.genetics.IAlleleSpecies;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.text.TextFormatting;

public class AnalystPageAppearance extends ControlAnalystPage {
	public AnalystPageAppearance(final IWidget parent, final Area area, final IFlower flower) {
		super(parent, area);
		this.setColour(3355443);
		int y = 4;
		IFlowerGenome genome = flower.getGenome();
		final IAlleleSpecies species = genome.getPrimary();
		new ControlTextCentered(this, y, TextFormatting.UNDERLINE + getTitle()).setColour(this.getColour());
		y += 12;
		final ControlColourDisplay a = new ControlColourDisplay(this, this.width() / 2 - 28, y, genome.getPrimaryColor());
		a.addTooltip(Genetics.proxy.localise("gui.analyst.appearance.primary"));
		final ControlColourDisplay b = new ControlColourDisplay(this, this.width() / 2 - 8, y, genome.getSecondaryColor());
		b.addTooltip(Genetics.proxy.localise("gui.analyst.appearance.secondary"));
		final ControlColourDisplay c = new ControlColourDisplay(this, this.width() / 2 + 12, y, genome.getStemColor());
		c.addTooltip(Genetics.proxy.localise("gui.analyst.appearance.stem"));
		y += 26;
		IFlowerType type = genome.getType();
		final int sections = type.getSections();
		final int width = (sections > 1) ? 50 : 100;
		new ControlIconDisplay(this, (this.width() - width) / 2, y - ((sections == 1) ? 0 : 0)) {
			@Override
			public void onRenderForeground(int guiWidth, int guiHeight) {
				GlStateManager.pushMatrix();
				final float scale = width / 16.0f;
				int dy = (sections > 1) ? 16 : 0;
				GlStateManager.scale(scale, scale, 1.0f);
				RenderUtil.setColour(flower.getGenome().getStemColor().getColor(false));
				if (sections > 1) {
					RenderUtil.drawSprite(Point.ZERO, FlowerSpriteManager.getStem(type, 1, true));
				}
				RenderUtil.drawSprite(new Point(0, dy), FlowerSpriteManager.getStem(type, 0, true));
				RenderUtil.setColour(flower.getGenome().getPrimaryColor().getColor(false));
				if (sections > 1) {
					RenderUtil.drawSprite(Point.ZERO, FlowerSpriteManager.getPetal(type, 1, true));
				}
				RenderUtil.drawSprite(new Point(0, dy), FlowerSpriteManager.getPetal(type, 0, true));
				RenderUtil.setColour(flower.getGenome().getSecondaryColor().getColor(false));
				if (sections > 1) {
					RenderUtil.drawSprite(Point.ZERO, FlowerSpriteManager.getVariant(type, 1, true));
				}
				RenderUtil.drawSprite(new Point(0, dy), FlowerSpriteManager.getVariant(type, 0, true));
				GlStateManager.popMatrix();
			}
		};
	}

	@Override
	public String getTitle() {
		return Genetics.proxy.localise("gui.analyst.appearance.title");
	}
}
