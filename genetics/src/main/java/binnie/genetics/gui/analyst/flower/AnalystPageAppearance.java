package binnie.genetics.gui.analyst.flower;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.text.TextFormatting;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.genetics.IAlleleSpecies;

import binnie.botany.api.genetics.IFlower;
import binnie.botany.api.genetics.IFlowerGenome;
import binnie.botany.api.genetics.IFlowerType;
import binnie.botany.gui.database.ControlColorDisplay;
import binnie.botany.models.FlowerSpriteManager;
import binnie.core.gui.IWidget;
import binnie.core.gui.controls.ControlTextCentered;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.minecraft.control.ControlIconDisplay;
import binnie.core.gui.renderer.RenderUtil;
import binnie.core.util.I18N;
import binnie.genetics.gui.analyst.AnalystConstants;
import binnie.genetics.gui.analyst.ControlAnalystPage;

public class AnalystPageAppearance extends ControlAnalystPage {
	public AnalystPageAppearance(IWidget parent, Area area, IFlower flower) {
		super(parent, area);
		setColor(3355443);
		int y = 4;
		IFlowerGenome genome = flower.getGenome();
		IAlleleSpecies species = genome.getPrimary();
		new ControlTextCentered(this, y, TextFormatting.UNDERLINE + getTitle()).setColor(getColor());
		y += 12;
		ControlColorDisplay a = new ControlColorDisplay(this, getWidth() / 2 - 28, y, genome.getPrimaryColor());
		a.addTooltip(I18N.localise(AnalystConstants.APPEARANCE_KEY + ".primary"));
		ControlColorDisplay b = new ControlColorDisplay(this, getWidth() / 2 - 8, y, genome.getSecondaryColor());
		b.addTooltip(I18N.localise(AnalystConstants.APPEARANCE_KEY + ".secondary"));
		ControlColorDisplay c = new ControlColorDisplay(this, getWidth() / 2 + 12, y, genome.getStemColor());
		c.addTooltip(I18N.localise(AnalystConstants.APPEARANCE_KEY + ".stem"));
		y += 26;
		IFlowerType type = genome.getType();
		int sections = type.getSections();
		int width = (sections > 1) ? 50 : 100;
		new ControlIconDisplay(this, (getWidth() - width) / 2, y - ((sections == 1) ? 0 : 0)) {
			@Override
			@SideOnly(Side.CLIENT)
			public void onRenderForeground(int guiWidth, int guiHeight) {
				GlStateManager.pushMatrix();
				float scale = width / 16.0f;
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
		return I18N.localise(AnalystConstants.APPEARANCE_KEY + ".title");
	}
}
