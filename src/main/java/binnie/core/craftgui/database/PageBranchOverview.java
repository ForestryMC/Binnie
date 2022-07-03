package binnie.core.craftgui.database;

import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.util.I18N;
import forestry.api.genetics.IClassification;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.EnumChatFormatting;

public class PageBranchOverview extends PageBranch {
    private ControlText branchName;
    private ControlText branchScientific;
    private ControlText branchAuthority;
    private List<ControlText> branchDescription;

    public PageBranchOverview(IWidget parent, DatabaseTab tab) {
        super(parent, tab);
        branchDescription = new ArrayList<>();
        branchName = new ControlTextCentered(this, 8.0f, "");
        branchScientific = new ControlTextCentered(this, 32.0f, "");
        branchAuthority = new ControlTextCentered(this, 44.0f, "");
    }

    @Override
    public void onValueChanged(IClassification branch) {
        String name = I18N.localise(branch.getName());
        branchName.setValue(EnumChatFormatting.BOLD
                + I18N.localise("binniecore.gui.database.branch.name", name)
                + EnumChatFormatting.RESET);
        branchScientific.setValue(EnumChatFormatting.ITALIC
                + I18N.localise("binniecore.gui.database.branch.apidae", branch.getScientific())
                + EnumChatFormatting.RESET);
        branchAuthority.setValue(EnumChatFormatting.BOLD
                + I18N.localise(
                        "binniecore.gui.database.branch.discoveredBy", branch.getMemberSpecies()[0].getAuthority())
                + EnumChatFormatting.RESET);

        for (IWidget widget : branchDescription) {
            deleteChild(widget);
        }

        branchDescription.clear();
        String desc = branch.getDescription();
        if (desc == null || desc.isEmpty() || desc.matches("(\\w+\\.?)+")) { // unlocalized
            desc = I18N.localise("binniecore.gui.database.branch.noDesc");
        }

        StringBuilder line = new StringBuilder();
        List<String> descLines = new ArrayList<>();
        for (String str : desc.split(" ")) {
            if (CraftGUI.render.textWidth(line + " " + str) > 134) {
                descLines.add(EnumChatFormatting.ITALIC + line.toString() + EnumChatFormatting.RESET);
                line = new StringBuilder();
            }
            line.append(" ").append(str);
        }

        descLines.add(line.toString());
        int i = 0;
        for (String dLine : descLines) {
            branchDescription.add(new ControlTextCentered(this, 84 + 12 * i++, dLine));
        }
    }
}
