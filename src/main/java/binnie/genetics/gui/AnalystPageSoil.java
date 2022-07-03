package binnie.genetics.gui;

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
import binnie.core.util.I18N;
import binnie.genetics.genetics.AlleleHelper;
import forestry.api.genetics.EnumTolerance;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class AnalystPageSoil extends ControlAnalystPage {
    public AnalystPageSoil(IWidget parent, IArea area, IFlower flower) {
        super(parent, area);
        setColor(0x663300);
        EnumMoisture moisture = flower.getGenome().getPrimary().getMoisture();
        EnumTolerance moistureTol = flower.getGenome().getToleranceMoisture();
        EnumAcidity pH = flower.getGenome().getPrimary().getPH();
        EnumTolerance pHTol = flower.getGenome().getTolerancePH();
        int y = 4;
        new ControlTextCentered(this, y, EnumChatFormatting.UNDERLINE + getTitle()).setColor(getColor());

        y += 16;
        new ControlText(
                        this,
                        new IArea(4.0f, y, w() - 8.0f, 14.0f),
                        I18N.localise("genetics.gui.analyst.soil.moistureTolerance"),
                        TextJustification.MIDDLE_CENTER)
                .setColor(getColor());

        y += 12;
        createMoisture(this, (w() - 100.0f) / 2.0f, y, 100.0f, 10.0f, moisture, moistureTol);

        y += 16;
        new ControlText(
                        this,
                        new IArea(4.0f, y, w() - 8.0f, 14.0f),
                        I18N.localise("genetics.gui.analyst.soil.pHTolerance"),
                        TextJustification.MIDDLE_CENTER)
                .setColor(getColor());

        y += 12;
        createAcidity(this, (w() - 100.0f) / 2.0f, y, 100.0f, 10.0f, pH, pHTol);

        y += 16;
        new ControlText(
                        this,
                        new IArea(4.0f, y, w() - 8.0f, 14.0f),
                        I18N.localise("genetics.gui.analyst.soil.recommendedSoil"),
                        TextJustification.MIDDLE_CENTER)
                .setColor(getColor());

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
            if (canTolDry) {
                recomMoisture = EnumMoisture.DRY;
            } else if (canTolDamp) {
                recomMoisture = EnumMoisture.DAMP;
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
        ControlItemDisplay recomSoil = new ControlItemDisplay(this, (w() - 24.0f) / 2.0f, y, 24.0f);
        recomSoil.setItemStack(stack);
        recomSoil.setTooltip();
        y += 32;
        new ControlText(
                        this,
                        new IArea(4.0f, y, w() - 8.0f, 14.0f),
                        I18N.localise("genetics.gui.analyst.soil.otherSoils"),
                        TextJustification.MIDDLE_CENTER)
                .setColor(getColor());

        y += 12;
        List<ItemStack> stacks = new ArrayList<>();
        for (EnumAcidity a : EnumSet.range(EnumAcidity.ACID, EnumAcidity.ALKALINE)) {
            for (EnumMoisture b : EnumSet.range(EnumMoisture.DRY, EnumMoisture.DAMP)) {
                if (Tolerance.canTolerate(pH, a, pHTol)
                        && Tolerance.canTolerate(moisture, b, moistureTol)
                        && (a != recomPH || b != recomMoisture)) {
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

    protected void createMoisture(
            IWidget parent, float x, float y, float w, float h, EnumMoisture value, EnumTolerance tol) {
        new ControlToleranceBar<EnumMoisture>(parent, x, y, w, h, EnumMoisture.class) {
            @Override
            protected String getName(EnumMoisture value) {
                return AlleleHelper.toDisplay(value);
            }

            @Override
            protected int getColor(EnumMoisture value) {
                return (new int[] {0xccffcc, 0x66ccff, 0x3366ff})[value.ordinal()];
            }
        }.setValues(value, tol);
    }

    protected void createAcidity(
            IWidget parent, float x, float y, float w, float h, EnumAcidity value, EnumTolerance tol) {
        new ControlToleranceBar<EnumAcidity>(parent, x, y, w, h, EnumAcidity.class) {
            @Override
            protected String getName(EnumAcidity value) {
                return AlleleHelper.toDisplay(value);
            }

            @Override
            protected int getColor(EnumAcidity value) {
                return (new int[] {0xff0066, 0x00ff00, 0x0066ff})[value.ordinal()];
            }
        }.setValues(value, tol);
    }

    @Override
    public String getTitle() {
        return I18N.localise("genetics.gui.analyst.soil");
    }
}
