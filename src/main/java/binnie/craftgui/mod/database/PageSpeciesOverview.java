package binnie.craftgui.mod.database;

import binnie.craftgui.controls.ControlText;
import binnie.craftgui.controls.ControlTextCentered;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.core.geometry.TextJustification;
import forestry.api.genetics.IAlleleSpecies;

public class PageSpeciesOverview extends PageSpecies {
    private ControlText controlName;
    private ControlText controlScientific;
    private ControlText controlAuthority;
    private ControlText controlComplexity;
    private ControlText controlDescription;
    private ControlText controlSignature;
    private ControlDatabaseIndividualDisplay controlInd1;
    private ControlDatabaseIndividualDisplay controlInd2;

    public PageSpeciesOverview(final IWidget parent, final DatabaseTab tab) {
        super(parent, tab);
        this.controlInd1 = new ControlDatabaseIndividualDisplay(this, 5.0f, 5.0f);
        this.controlInd2 = new ControlDatabaseIndividualDisplay(this, 123.0f, 5.0f);
        this.controlName = new ControlTextCentered(this, 8.0f, "");
        this.controlScientific = new ControlTextCentered(this, 32.0f, "");
        this.controlAuthority = new ControlTextCentered(this, 44.0f, "");
        this.controlComplexity = new ControlTextCentered(this, 56.0f, "");
        this.controlDescription = new ControlText(this, new IArea(8.0f, 84.0f, this.getSize().x() - 16.0f, 0.0f), "", TextJustification.MiddleCenter);
        this.controlSignature = new ControlText(this, new IArea(8.0f, 84.0f, this.getSize().x() - 16.0f, 0.0f), "", TextJustification.BottomRight);
    }

    @Override
    public void onValueChanged(final IAlleleSpecies species) {
        this.controlInd1.setSpecies(species, EnumDiscoveryState.Show);
        this.controlInd2.setSpecies(species, EnumDiscoveryState.Show);
        final String branchBinomial = (species.getBranch() != null) ? species.getBranch().getScientific() : "<Unknown>";
        final String branchName = (species.getBranch() != null) ? species.getBranch().getName() : "Unknown";
        this.controlName.setValue("§n" + species.getName() + "§r");
        this.controlScientific.setValue("§o" + branchBinomial + " " + species.getBinomial() + "§r");
        this.controlAuthority.setValue("Discovered by §l" + species.getAuthority() + "§r");
        this.controlComplexity.setValue("Complexity: " + species.getComplexity());
        final String desc = species.getDescription();
        String descBody = "§o";
        String descSig = "";
        if (desc == null || desc == "") {
            descBody += "No Description Provided.";
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
        this.controlDescription.setValue(descBody + "§r");
        this.controlSignature.setValue(descSig + "§r");
        final float descHeight = CraftGUI.Render.textHeight(this.controlDescription.getValue(), this.controlDescription.getSize().x());
        this.controlSignature.setPosition(new IPoint(this.controlSignature.pos().x(), this.controlDescription.getPosition().y() + descHeight + 10.0f));
    }
}
