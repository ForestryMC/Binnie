package binnie.genetics.gui;

import binnie.craftgui.controls.ControlText;
import binnie.craftgui.controls.ControlTextCentered;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.core.geometry.TextJustification;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IIndividual;

public class AnalystPageDescription extends ControlAnalystPage {
    public AnalystPageDescription(final IWidget parent, final IArea area, final IIndividual ind) {
        super(parent, area);
        this.setColour(3355443);
        int y = 4;
        final IAlleleSpecies species = ind.getGenome().getPrimary();
        final String branchBinomial = (species.getBranch() != null) ? species.getBranch().getScientific() : "<Unknown>";
        final String branchName = (species.getBranch() != null) ? species.getBranch().getName() : "Unknown";
        final String desc = species.getDescription();
        String descBody = "§o";
        String descSig = "";
        if (desc == null || desc == "" || desc.contains("for.description")) {
            descBody += "";
        } else {
            final String[] descStrings = desc.split("\\|");
            descBody += descStrings[0];
            for (int i = 1; i < descStrings.length - 1; ++i) {
                descBody = descBody + " " + descStrings[i];
            }
            if (descStrings.length > 1) {
                descSig += descStrings[descStrings.length - 1];
            }
        }
        String authority = species.getAuthority();
        if (authority.contains("Binnie")) {
            authority = "§1§l" + authority;
        }
        if (authority.contains("Sengir")) {
            authority = "§2§l" + authority;
        }
        if (authority.contains("MysteriousAges")) {
            authority = "§5§l" + authority;
        }
        new ControlTextCentered(this, y, "§nDescription").setColour(this.getColour());
        y += 16;
        new ControlTextCentered(this, y, species.getName() + "§r").setColour(this.getColour());
        y += 10;
        new ControlTextCentered(this, y, "§o" + branchBinomial + " " + species.getBinomial() + "§r").setColour(this.getColour());
        y += 20;
        new ControlTextCentered(this, y, "Discovered by §l" + authority + "§r").setColour(this.getColour());
        y += (int) (3.0f + CraftGUI.Render.textHeight("Discovered by §l" + authority + "§r", this.w()));
        new ControlTextCentered(this, y, "Genetic Complexity: " + species.getComplexity()).setColour(this.getColour());
        y += 26;
        final ControlText descText = new ControlText(this, new IArea(8.0f, y, this.w() - 16.0f, 0.0f), descBody + "§r", TextJustification.TopCenter);
        final IWidget signatureText = new ControlText(this, new IArea(8.0f, y, this.w() - 16.0f, 0.0f), descSig + "§r", TextJustification.BottomRight);
        descText.setColour(this.getColour());
        signatureText.setColour(this.getColour());
        final float descHeight = CraftGUI.Render.textHeight(descText.getValue(), descText.getSize().x());
        signatureText.setPosition(new IPoint(signatureText.pos().x(), descText.getPosition().y() + descHeight + 10.0f));
        this.setSize(new IPoint(this.w(), 20.0f + signatureText.y()));
    }

    @Override
    public String getTitle() {
        return "Description";
    }
}
