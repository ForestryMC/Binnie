package binnie.genetics.gui;

import binnie.Binnie;
import binnie.botany.Botany;
import binnie.botany.api.EnumAcidity;
import binnie.botany.api.EnumMoisture;
import binnie.botany.api.IFlower;
import binnie.botany.gardening.BlockSoil;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.TextJustification;
import binnie.core.craftgui.minecraft.control.ControlItemDisplay;
import binnie.core.genetics.Tolerance;
import forestry.api.genetics.EnumTolerance;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class AnalystPageSoil extends ControlAnalystPage {
	public AnalystPageSoil(IWidget parent, IArea area, IFlower flower) {
		super(parent, area);
		setColor(6697728);
		EnumMoisture moisture = flower.getGenome().getPrimary().getMoisture();
		EnumTolerance moistureTol = flower.getGenome().getToleranceMoisture();
		EnumAcidity pH = flower.getGenome().getPrimary().getPH();
		EnumTolerance pHTol = flower.getGenome().getTolerancePH();
		int y = 4;
		new ControlTextCentered(this, y, EnumChatFormatting.UNDERLINE + "Soil").setColor(getColor());
		y += 16;
		new ControlText(this, new IArea(4.0f, y, w() - 8.0f, 14.0f), "Moisture Tolerance", TextJustification.MiddleCenter).setColor(getColor());
		y += 12;
		createMoisture(this, (w() - 100.0f) / 2.0f, y, 100.0f, 10.0f, moisture, moistureTol);
		y += 16;
		new ControlText(this, new IArea(4.0f, y, w() - 8.0f, 14.0f), "pH Tolerance", TextJustification.MiddleCenter).setColor(getColor());
		y += 12;
		createAcidity(this, (w() - 100.0f) / 2.0f, y, 100.0f, 10.0f, pH, pHTol);
		y += 16;
		new ControlText(this, new IArea(4.0f, y, w() - 8.0f, 14.0f), "Recommended Soil", TextJustification.MiddleCenter).setColor(getColor());
		y += 12;
		EnumMoisture recomMoisture = EnumMoisture.Normal;
		boolean canTolNormal = Tolerance.canTolerate(moisture, EnumMoisture.Normal, moistureTol);
		boolean canTolDamp = Tolerance.canTolerate(moisture, EnumMoisture.Damp, moistureTol);
		boolean canTolDry = Tolerance.canTolerate(moisture, EnumMoisture.Dry, moistureTol);
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
		boolean canTolNeutral = Tolerance.canTolerate(pH, EnumAcidity.Neutral, pHTol);
		boolean canTolAcid = Tolerance.canTolerate(pH, EnumAcidity.Acid, pHTol);
		boolean canTolAlkaline = Tolerance.canTolerate(pH, EnumAcidity.Alkaline, pHTol);
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
		ItemStack stack = new ItemStack(Botany.soil, 1, BlockSoil.getMeta(recomPH, recomMoisture));
		ControlItemDisplay recomSoil = new ControlItemDisplay(this, (w() - 24.0f) / 2.0f, y, 24.0f);
		recomSoil.setItemStack(stack);
		recomSoil.setTooltip();
		y += 32;
		new ControlText(this, new IArea(4.0f, y, w() - 8.0f, 14.0f), "Other Soils", TextJustification.MiddleCenter).setColor(getColor());
		y += 12;
		List<ItemStack> stacks = new ArrayList<ItemStack>();
		for (EnumAcidity a : EnumSet.range(EnumAcidity.Acid, EnumAcidity.Alkaline)) {
			for (EnumMoisture b : EnumSet.range(EnumMoisture.Dry, EnumMoisture.Damp)) {
				if (Tolerance.canTolerate(pH, a, pHTol) && Tolerance.canTolerate(moisture, b, moistureTol) && (a != recomPH || b != recomMoisture)) {
					stacks.add(new ItemStack(Botany.soil, 1, BlockSoil.getMeta(a, b)));
				}
			}
		}
		float soilListWidth = 17 * stacks.size() - 1;
		float soilListX = (w() - soilListWidth) / 2.0f;
		int t = 0;
		for (ItemStack soilStack : stacks) {
			ControlItemDisplay display = new ControlItemDisplay(this, soilListX + 17 * t++, y);
			display.setItemStack(soilStack);
			display.setTooltip();
		}
	}

	protected void createMoisture(IWidget parent, float x, float y, float w, float h, EnumMoisture value, EnumTolerance tol) {
		new ControlToleranceBar<EnumMoisture>(parent, x, y, w, h, EnumMoisture.class) {
			@Override
			protected String getName(EnumMoisture value) {
				return Binnie.I18N.localise(value);
			}

			@Override
			protected int getColour(EnumMoisture value) {
				return (new int[]{13434828, 6737151, 3368703})[value.ordinal()];
			}
		}.setValues(value, tol);
	}

	protected void createAcidity(IWidget parent, float x, float y, float w, float h, EnumAcidity value, EnumTolerance tol) {
		new ControlToleranceBar<EnumAcidity>(parent, x, y, w, h, EnumAcidity.class) {
			@Override
			protected String getName(EnumAcidity value) {
				return Binnie.I18N.localise(value);
			}

			@Override
			protected int getColour(EnumAcidity value) {
				return (new int[]{16711782, 65280, 26367})[value.ordinal()];
			}
		}.setValues(value, tol);
	}

	@Override
	public String getTitle() {
		return "Soil";
	}
}
