package binnie.genetics.gui.flower;

import binnie.botany.Botany;
import binnie.botany.api.EnumAcidity;
import binnie.botany.api.EnumMoisture;
import binnie.botany.api.IFlower;
import binnie.botany.gardening.BlockSoil;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.geometry.Area;
import binnie.core.craftgui.geometry.TextJustification;
import binnie.core.craftgui.minecraft.control.ControlItemDisplay;
import binnie.core.genetics.Tolerance;
import binnie.genetics.Genetics;
import binnie.genetics.gui.ControlAnalystPage;
import binnie.genetics.gui.ControlToleranceBar;
import forestry.api.genetics.EnumTolerance;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class AnalystPageSoil extends ControlAnalystPage {
	public AnalystPageSoil(final IWidget parent, final Area area, final IFlower flower) {
		super(parent, area);
		this.setColour(6697728);
		final EnumMoisture moisture = flower.getGenome().getPrimary().getMoisture();
		final EnumTolerance moistureTol = flower.getGenome().getToleranceMoisture();
		final EnumAcidity pH = flower.getGenome().getPrimary().getPH();
		final EnumTolerance pHTol = flower.getGenome().getTolerancePH();
		int y = 4;
		new ControlTextCentered(this, y, TextFormatting.UNDERLINE + getTitle()).setColour(this.getColour());
		y += 16;
		new ControlText(this, new Area(4, y, this.width() - 8, 14), Genetics.proxy.localise("gui.analyst.soil.tolerance.moisture"), TextJustification.MiddleCenter).setColour(this.getColour());
		y += 12;
		this.createMoisture(this, (this.width() - 100) / 2, y, 100, 10, moisture, moistureTol);
		y += 16;
		new ControlText(this, new Area(4, y, this.width() - 8, 14), Genetics.proxy.localise("gui.analyst.soil.tolerance.ph"), TextJustification.MiddleCenter).setColour(this.getColour());
		y += 12;
		this.createAcidity(this, (this.width() - 100) / 2, y, 100, 10, pH, pHTol);
		y += 16;
		new ControlText(this, new Area(4, y, this.width() - 8, 14), Genetics.proxy.localise("gui.analyst.soil.recommended"), TextJustification.MiddleCenter).setColour(this.getColour());
		y += 12;
		EnumMoisture recomMoisture = EnumMoisture.Normal;
		final boolean canTolNormal = Tolerance.canTolerate(moisture, EnumMoisture.Normal, moistureTol);
		final boolean canTolDamp = Tolerance.canTolerate(moisture, EnumMoisture.Damp, moistureTol);
		final boolean canTolDry = Tolerance.canTolerate(moisture, EnumMoisture.Dry, moistureTol);
		if (canTolNormal) {
			if (canTolDamp && !canTolDry) {
				recomMoisture = EnumMoisture.Damp;
			} else if (canTolDry && !canTolDamp) {
				recomMoisture = EnumMoisture.Dry;
			}
		} else {
			if (canTolDamp) {
				recomMoisture = EnumMoisture.Damp;
			}
			if (canTolDry) {
				recomMoisture = EnumMoisture.Dry;
			}
		}
		EnumAcidity recomPH = EnumAcidity.Neutral;
		final boolean canTolNeutral = Tolerance.canTolerate(pH, EnumAcidity.Neutral, pHTol);
		final boolean canTolAcid = Tolerance.canTolerate(pH, EnumAcidity.Acid, pHTol);
		final boolean canTolAlkaline = Tolerance.canTolerate(pH, EnumAcidity.Alkaline, pHTol);
		if (canTolNeutral) {
			if (canTolAcid && !canTolAlkaline) {
				recomPH = EnumAcidity.Acid;
			} else if (canTolAlkaline && !canTolAcid) {
				recomPH = EnumAcidity.Alkaline;
			}
		} else {
			if (canTolAcid) {
				recomPH = EnumAcidity.Acid;
			}
			if (canTolAlkaline) {
				recomPH = EnumAcidity.Alkaline;
			}
		}
		final ItemStack stack = new ItemStack(Botany.soil, 1, BlockSoil.getMeta(recomPH, recomMoisture));
		final ControlItemDisplay recomSoil = new ControlItemDisplay(this, (this.width() - 24) / 2, y, 24);
		recomSoil.setItemStack(stack);
		recomSoil.setTooltip();
		y += 32;
		new ControlText(this, new Area(4, y, this.width() - 8, 14), Genetics.proxy.localise("gui.analyst.soil.other"), TextJustification.MiddleCenter).setColour(this.getColour());
		y += 12;
		final List<ItemStack> stacks = new ArrayList<>();
		for (final EnumAcidity a : EnumSet.range(EnumAcidity.Acid, EnumAcidity.Alkaline)) {
			for (final EnumMoisture b : EnumSet.range(EnumMoisture.Dry, EnumMoisture.Damp)) {
				if (Tolerance.canTolerate(pH, a, pHTol) && Tolerance.canTolerate(moisture, b, moistureTol) && (a != recomPH || b != recomMoisture)) {
					stacks.add(new ItemStack(Botany.soil, 1, BlockSoil.getMeta(a, b)));
				}
			}
		}
		final int soilListWidth = 17 * stacks.size() - 1;
		final int soilListX = (this.width() - soilListWidth) / 2;
		int t = 0;
		for (final ItemStack soilStack : stacks) {
			final ControlItemDisplay display = new ControlItemDisplay(this, soilListX + 17 * t++, y);
			display.setItemStack(soilStack);
			display.setTooltip();
		}
	}

	protected void createMoisture(final IWidget parent, final int x, final int y, final int w, final int h, final EnumMoisture value, final EnumTolerance tol) {
		new ControlToleranceBar<EnumMoisture>(parent, x, y, w, h, EnumMoisture.class) {
			@Override
			protected String getName(final EnumMoisture value) {
				return value.getTranslated(false);
			}

			@Override
			protected int getColour(final EnumMoisture value) {
				return (new int[]{13434828, 6737151, 3368703})[value.ordinal()];
			}
		}.setValues(value, tol);
	}

	protected void createAcidity(final IWidget parent, final int x, final int y, final int w, final int h, final EnumAcidity value, final EnumTolerance tol) {
		new ControlToleranceBar<EnumAcidity>(parent, x, y, w, h, EnumAcidity.class) {
			@Override
			protected String getName(final EnumAcidity value) {
				return value.getTranslated(false);
			}

			@Override
			protected int getColour(final EnumAcidity value) {
				return (new int[]{16711782, 65280, 26367})[value.ordinal()];
			}
		}.setValues(value, tol);
	}

	@Override
	public String getTitle() {
		return Genetics.proxy.localise("gui.analyst.soil.title");
	}
}
