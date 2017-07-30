package binnie.genetics.gui.analyst.flower;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import forestry.api.genetics.EnumTolerance;

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
import binnie.core.util.I18N;
import binnie.genetics.gui.analyst.AnalystConstants;
import binnie.genetics.gui.analyst.ControlAnalystPage;
import binnie.genetics.gui.analyst.ControlToleranceBar;

public class AnalystPageSoil extends ControlAnalystPage {
	public AnalystPageSoil(IWidget parent, Area area, IFlower flower) {
		super(parent, area);
		setColor(6697728);
		EnumMoisture moisture = flower.getGenome().getPrimary().getMoisture();
		EnumTolerance moistureTol = flower.getGenome().getToleranceMoisture();
		EnumAcidity pH = flower.getGenome().getPrimary().getPH();
		EnumTolerance pHTol = flower.getGenome().getTolerancePH();
		int y = 4;
		new ControlTextCentered(this, y, TextFormatting.UNDERLINE + getTitle()).setColor(getColor());
		y += 16;
		new ControlText(this, new Area(4, y, width() - 8, 14), I18N.localise(AnalystConstants.SOIL_KEY + ".tolerance.moisture"), TextJustification.MIDDLE_CENTER).setColor(getColor());
		y += 12;
		createMoisture(this, (width() - 100) / 2, y, 100, 10, moisture, moistureTol);
		y += 16;
		new ControlText(this, new Area(4, y, width() - 8, 14), I18N.localise(AnalystConstants.SOIL_KEY + ".tolerance.ph"), TextJustification.MIDDLE_CENTER).setColor(getColor());
		y += 12;
		createAcidity(this, (width() - 100) / 2, y, 100, 10, pH, pHTol);
		y += 16;
		new ControlText(this, new Area(4, y, width() - 8, 14), I18N.localise(AnalystConstants.SOIL_KEY + ".recommended"), TextJustification.MIDDLE_CENTER).setColor(getColor());
		y += 12;
		EnumMoisture recomMoisture = EnumMoisture.NORMAL;
		boolean canTolNormal = Tolerance.canTolerate(moisture, EnumMoisture.NORMAL, moistureTol);
		boolean canTolDamp = Tolerance.canTolerate(moisture, EnumMoisture.DAMP, moistureTol);
		boolean canTolDry = Tolerance.canTolerate(moisture, EnumMoisture.DRY, moistureTol);
		if (canTolNormal) {
			if (canTolDamp && !canTolDry) {
				recomMoisture = EnumMoisture.DAMP;
			} else if (canTolDry && !canTolDamp) {
				recomMoisture = EnumMoisture.DRY;
			}
		} else {
			if (canTolDamp) {
				recomMoisture = EnumMoisture.DAMP;
			}
			if (canTolDry) {
				recomMoisture = EnumMoisture.DRY;
			}
		}
		EnumAcidity recomPH = EnumAcidity.NEUTRAL;
		boolean canTolNeutral = Tolerance.canTolerate(pH, EnumAcidity.NEUTRAL, pHTol);
		boolean canTolAcid = Tolerance.canTolerate(pH, EnumAcidity.ACID, pHTol);
		boolean canTolAlkaline = Tolerance.canTolerate(pH, EnumAcidity.ALKALINE, pHTol);
		if (canTolNeutral) {
			if (canTolAcid && !canTolAlkaline) {
				recomPH = EnumAcidity.ACID;
			} else if (canTolAlkaline && !canTolAcid) {
				recomPH = EnumAcidity.ALKALINE;
			}
		} else {
			if (canTolAcid) {
				recomPH = EnumAcidity.ACID;
			}
			if (canTolAlkaline) {
				recomPH = EnumAcidity.ALKALINE;
			}
		}
		ItemStack stack = new ItemStack(Botany.soil, 1, BlockSoil.getMeta(recomPH, recomMoisture));
		ControlItemDisplay recomSoil = new ControlItemDisplay(this, (width() - 24) / 2, y, 24);
		recomSoil.setItemStack(stack);
		recomSoil.setTooltip();
		y += 32;
		new ControlText(this, new Area(4, y, width() - 8, 14), I18N.localise(AnalystConstants.SOIL_KEY + ".other"), TextJustification.MIDDLE_CENTER).setColor(getColor());
		y += 12;
		List<ItemStack> stacks = new ArrayList<>();
		for (EnumAcidity a : EnumSet.range(EnumAcidity.ACID, EnumAcidity.ALKALINE)) {
			for (EnumMoisture b : EnumSet.range(EnumMoisture.DRY, EnumMoisture.DAMP)) {
				if (Tolerance.canTolerate(pH, a, pHTol) && Tolerance.canTolerate(moisture, b, moistureTol) && (a != recomPH || b != recomMoisture)) {
					stacks.add(new ItemStack(Botany.soil, 1, BlockSoil.getMeta(a, b)));
				}
			}
		}
		int soilListWidth = 17 * stacks.size() - 1;
		int soilListX = (width() - soilListWidth) / 2;
		int t = 0;
		for (ItemStack soilStack : stacks) {
			ControlItemDisplay display = new ControlItemDisplay(this, soilListX + 17 * t++, y);
			display.setItemStack(soilStack);
			display.setTooltip();
		}
	}

	protected void createMoisture(IWidget parent, int x, int y, int w, int h, EnumMoisture value, EnumTolerance tol) {
		new ControlToleranceBar<EnumMoisture>(parent, x, y, w, h, EnumMoisture.class) {
			@Override
			protected String getName(EnumMoisture value) {
				return value.getLocalisedName(false);
			}

			@Override
			protected int getColour(EnumMoisture value) {
				return (new int[]{13434828, 6737151, 3368703})[value.ordinal()];
			}
		}.setValues(value, tol);
	}

	protected void createAcidity(IWidget parent, int x, int y, int w, int h, EnumAcidity value, EnumTolerance tol) {
		new ControlToleranceBar<EnumAcidity>(parent, x, y, w, h, EnumAcidity.class) {
			@Override
			protected String getName(EnumAcidity value) {
				return value.getLocalisedName(false);
			}

			@Override
			protected int getColour(EnumAcidity value) {
				return (new int[]{16711782, 65280, 26367})[value.ordinal()];
			}
		}.setValues(value, tol);
	}

	@Override
	public String getTitle() {
		return I18N.localise(AnalystConstants.SOIL_KEY + ".title");
	}
}
