// 
// Decompiled by Procyon v0.5.30
// 

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

public class AnalystPageSoil extends ControlAnalystPage
{
	public AnalystPageSoil(final IWidget parent, final IArea area, final IFlower flower) {
		super(parent, area);
		this.setColor(6697728);
		final EnumMoisture moisture = flower.getGenome().getPrimary().getMoisture();
		final EnumTolerance moistureTol = flower.getGenome().getToleranceMoisture();
		final EnumAcidity pH = flower.getGenome().getPrimary().getPH();
		final EnumTolerance pHTol = flower.getGenome().getTolerancePH();
		int y = 4;
		new ControlTextCentered(this, y, EnumChatFormatting.UNDERLINE + "Soil").setColor(this.getColor());
		y += 16;
		new ControlText(this, new IArea(4.0f, y, this.w() - 8.0f, 14.0f), "Moisture Tolerance", TextJustification.MiddleCenter).setColor(this.getColor());
		y += 12;
		this.createMoisture(this, (this.w() - 100.0f) / 2.0f, y, 100.0f, 10.0f, moisture, moistureTol);
		y += 16;
		new ControlText(this, new IArea(4.0f, y, this.w() - 8.0f, 14.0f), "pH Tolerance", TextJustification.MiddleCenter).setColor(this.getColor());
		y += 12;
		this.createAcidity(this, (this.w() - 100.0f) / 2.0f, y, 100.0f, 10.0f, pH, pHTol);
		y += 16;
		new ControlText(this, new IArea(4.0f, y, this.w() - 8.0f, 14.0f), "Recommended Soil", TextJustification.MiddleCenter).setColor(this.getColor());
		y += 12;
		EnumMoisture recomMoisture = EnumMoisture.Normal;
		final boolean canTolNormal = Tolerance.canTolerate(moisture, EnumMoisture.Normal, moistureTol);
		final boolean canTolDamp = Tolerance.canTolerate(moisture, EnumMoisture.Damp, moistureTol);
		final boolean canTolDry = Tolerance.canTolerate(moisture, EnumMoisture.Dry, moistureTol);
		if (canTolNormal) {
			if (canTolDamp && !canTolDry) {
				recomMoisture = EnumMoisture.Damp;
			}
			else if (canTolDry && !canTolDamp) {
				recomMoisture = EnumMoisture.Dry;
			}
		}
		else {
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
			}
			else if (canTolAlkaline && !canTolAcid) {
				recomPH = EnumAcidity.Alkaline;
			}
		}
		else {
			if (canTolAcid) {
				recomPH = EnumAcidity.Acid;
			}
			if (canTolAlkaline) {
				recomPH = EnumAcidity.Alkaline;
			}
		}
		final ItemStack stack = new ItemStack(Botany.soil, 1, BlockSoil.getMeta(recomPH, recomMoisture));
		final ControlItemDisplay recomSoil = new ControlItemDisplay(this, (this.w() - 24.0f) / 2.0f, y, 24.0f);
		recomSoil.setItemStack(stack);
		recomSoil.setTooltip();
		y += 32;
		new ControlText(this, new IArea(4.0f, y, this.w() - 8.0f, 14.0f), "Other Soils", TextJustification.MiddleCenter).setColor(this.getColor());
		y += 12;
		final List<ItemStack> stacks = new ArrayList<ItemStack>();
		for (final EnumAcidity a : EnumSet.range(EnumAcidity.Acid, EnumAcidity.Alkaline)) {
			for (final EnumMoisture b : EnumSet.range(EnumMoisture.Dry, EnumMoisture.Damp)) {
				if (Tolerance.canTolerate(pH, a, pHTol) && Tolerance.canTolerate(moisture, b, moistureTol) && (a != recomPH || b != recomMoisture)) {
					stacks.add(new ItemStack(Botany.soil, 1, BlockSoil.getMeta(a, b)));
				}
			}
		}
		final float soilListWidth = 17 * stacks.size() - 1;
		final float soilListX = (this.w() - soilListWidth) / 2.0f;
		int t = 0;
		for (final ItemStack soilStack : stacks) {
			final ControlItemDisplay display = new ControlItemDisplay(this, soilListX + 17 * t++, y);
			display.setItemStack(soilStack);
			display.setTooltip();
		}
	}

	protected void createMoisture(final IWidget parent, final float x, final float y, final float w, final float h, final EnumMoisture value, final EnumTolerance tol) {
		new ControlToleranceBar<EnumMoisture>(parent, x, y, w, h, EnumMoisture.class) {
			@Override
			protected String getName(final EnumMoisture value) {
				return Binnie.Language.localise(value);
			}

			@Override
			protected int getColour(final EnumMoisture value) {
				return (new int[] { 13434828, 6737151, 3368703 })[value.ordinal()];
			}
		}.setValues(value, tol);
	}

	protected void createAcidity(final IWidget parent, final float x, final float y, final float w, final float h, final EnumAcidity value, final EnumTolerance tol) {
		new ControlToleranceBar<EnumAcidity>(parent, x, y, w, h, EnumAcidity.class) {
			@Override
			protected String getName(final EnumAcidity value) {
				return Binnie.Language.localise(value);
			}

			@Override
			protected int getColour(final EnumAcidity value) {
				return (new int[] { 16711782, 65280, 26367 })[value.ordinal()];
			}
		}.setValues(value, tol);
	}

	@Override
	public String getTitle() {
		return "Soil";
	}
}
