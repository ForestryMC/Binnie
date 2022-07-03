package binnie.core.craftgui.database;

import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.geometry.TextJustification;
import binnie.core.util.I18N;
import forestry.api.genetics.IAlleleSpecies;
import net.minecraft.util.EnumChatFormatting;

public class PageSpeciesOverview extends PageSpecies {
    private ControlText controlName;
    private ControlText controlScientific;
    private ControlText controlAuthority;
    private ControlText controlComplexity;
    private ControlText controlDescription;
    private ControlText controlSignature;
    private ControlDatabaseIndividualDisplay controlInd1;
    private ControlDatabaseIndividualDisplay controlInd2;

    public PageSpeciesOverview(IWidget parent, DatabaseTab tab) {
        super(parent, tab);
        controlInd1 = new ControlDatabaseIndividualDisplay(this, 5.0f, 5.0f);
        controlInd2 = new ControlDatabaseIndividualDisplay(this, 123.0f, 5.0f);
        controlName = new ControlTextCentered(this, 8.0f, "");
        controlScientific = new ControlTextCentered(this, 32.0f, "");
        controlAuthority = new ControlTextCentered(this, 44.0f, "");
        controlComplexity = new ControlTextCentered(this, 56.0f, "");
        controlDescription = new ControlText(
                this, new IArea(8.0f, 84.0f, getSize().x() - 16.0f, 0.0f), "", TextJustification.MIDDLE_CENTER);
        controlSignature = new ControlText(
                this, new IArea(8.0f, 84.0f, getSize().x() - 16.0f, 0.0f), "", TextJustification.BOTTOM_RIGHT);
    }

    @Override
    public void onValueChanged(IAlleleSpecies species) {
        controlInd1.setSpecies(species, EnumDiscoveryState.SHOW);
        controlInd2.setSpecies(species, EnumDiscoveryState.SHOW);
        String branchBinomial = (species.getBranch() != null)
                ? species.getBranch().getScientific()
                : I18N.localise("extratrees.genetics.unknown");

        controlName.setValue(EnumChatFormatting.BOLD + species.getName() + EnumChatFormatting.RESET);
        controlScientific.setValue(
                EnumChatFormatting.ITALIC + branchBinomial + " " + species.getBinomial() + EnumChatFormatting.RESET);
        controlAuthority.setValue(EnumChatFormatting.BOLD
                + I18N.localise("extratrees.genetics.discoveredBy", species.getAuthority())
                + EnumChatFormatting.RESET);
        controlComplexity.setValue(I18N.localise("extratrees.genetics.complexity", species.getComplexity()));
        String desc = species.getDescription();
        String descBody = EnumChatFormatting.ITALIC.toString();
        String descSig = "";

        if (desc == null || desc.isEmpty() || desc.matches("(\\w+\\.?)+")) {
            descBody += I18N.localise("binniecore.gui.database.species.noDesc");
        } else {
            String[] descStrings = desc.split("\\|");
            descBody += descStrings[0];
            for (int i = 1; i < descStrings.length - 1; ++i) {
                descBody = descBody + " " + descStrings[i];
            }
            if (descStrings.length > 1) {
                descSig += descStrings[descStrings.length - 1];
            }
        }

        controlDescription.setValue(descBody + EnumChatFormatting.RESET);
        controlSignature.setValue(descSig + EnumChatFormatting.RESET);
        float descHeight = CraftGUI.render.textHeight(
                controlDescription.getValue(), controlDescription.getSize().x());
        controlSignature.setPosition(new IPoint(
                controlSignature.pos().x(), controlDescription.getPosition().y() + descHeight + 10.0f));
    }
}
