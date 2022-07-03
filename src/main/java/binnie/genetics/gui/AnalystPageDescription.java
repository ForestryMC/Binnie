package binnie.genetics.gui;

import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.geometry.TextJustification;
import binnie.core.util.I18N;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IIndividual;
import net.minecraft.util.EnumChatFormatting;

public class AnalystPageDescription extends ControlAnalystPage {
    public AnalystPageDescription(IWidget parent, IArea area, IIndividual ind) {
        super(parent, area);
        setColor(0x333333);
        int y = 4;

        IAlleleSpecies species = ind.getGenome().getPrimary();
        String branchBinomial = (species.getBranch() != null)
                ? species.getBranch().getScientific()
                : I18N.localise("genetics.gui.analyst.description.unknown");
        String desc = species.getDescription();
        StringBuilder descBody = new StringBuilder(EnumChatFormatting.ITALIC.toString());
        String descSig = "";
        if (desc == null || desc.isEmpty() || desc.matches("(\\w+\\.?)+")) {
            descBody.append("");
        } else {
            String[] descStrings = desc.split("\\|");
            descBody.append(descStrings[0]);
            for (int i = 1; i < descStrings.length - 1; ++i) {
                descBody.append(" ").append(descStrings[i]);
            }
            if (descStrings.length > 1) {
                descSig += descStrings[descStrings.length - 1];
            }
        }

        String authority = species.getAuthority();
        if (authority.contains("Binnie")) {
            authority = EnumChatFormatting.DARK_BLUE.toString() + authority;
        }
        if (authority.contains("Sengir")) {
            authority = EnumChatFormatting.DARK_GREEN.toString() + authority;
        }
        if (authority.contains("MysteriousAges")) {
            authority = EnumChatFormatting.DARK_PURPLE.toString() + authority;
        }

        new ControlTextCentered(this, y, EnumChatFormatting.UNDERLINE + getTitle()).setColor(getColor());

        y += 16;
        new ControlTextCentered(this, y, species.getName() + EnumChatFormatting.RESET).setColor(getColor());

        y += 10;
        new ControlTextCentered(
                        this,
                        y,
                        EnumChatFormatting.ITALIC + branchBinomial + " " + species.getBinomial()
                                + EnumChatFormatting.RESET)
                .setColor(getColor());

        y += 20;
        new ControlTextCentered(
                        this,
                        y,
                        EnumChatFormatting.BOLD
                                + I18N.localise("genetics.gui.analyst.description.discoveredBy", authority)
                                + EnumChatFormatting.RESET)
                .setColor(getColor());

        y += (int) (3.0f
                + CraftGUI.render.textHeight(
                        EnumChatFormatting.BOLD
                                + I18N.localise("genetics.gui.analyst.description.discoveredBy", authority)
                                + EnumChatFormatting.RESET,
                        w()));
        new ControlTextCentered(
                        this, y, I18N.localise("genetics.gui.analyst.description.complexity", species.getComplexity()))
                .setColor(getColor());

        y += 26;
        ControlText descText = new ControlText(
                this,
                new IArea(8.0f, y, w() - 16.0f, 0.0f),
                descBody.toString() + EnumChatFormatting.RESET,
                TextJustification.TOP_CENTER);
        IWidget signatureText = new ControlText(
                this,
                new IArea(8.0f, y, w() - 16.0f, 0.0f),
                descSig + EnumChatFormatting.RESET,
                TextJustification.BOTTOM_RIGHT);
        descText.setColor(getColor());
        signatureText.setColor(getColor());
        float descHeight = CraftGUI.render.textHeight(
                descText.getValue(), descText.getSize().x());
        signatureText.setPosition(
                new IPoint(signatureText.pos().x(), descText.getPosition().y() + descHeight + 10.0f));
        setSize(new IPoint(w(), 20.0f + signatureText.y()));
    }

    @Override
    public String getTitle() {
        return I18N.localise("genetics.gui.analyst.description");
    }
}
