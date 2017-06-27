package binnie.core.craftgui.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import net.minecraft.util.text.TextFormatting;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.genetics.IClassification;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.renderer.RenderUtil;
import binnie.core.util.I18N;

@SideOnly(Side.CLIENT)
public class PageBranchOverview extends PageBranch {
	private ControlText branchName;
	private ControlText branchScientific;
	private ControlText branchAuthority;
	private List<ControlText> branchDescription;

	public PageBranchOverview(final IWidget parent, final DatabaseTab tab) {
		super(parent, tab);
		this.branchDescription = new ArrayList<>();
		this.branchName = new ControlTextCentered(this, 8, "");
		this.branchScientific = new ControlTextCentered(this, 32, "");
		this.branchAuthority = new ControlTextCentered(this, 44, "");
	}

	@Override
	public void onValueChanged(final IClassification branch) {
		this.branchName.setValue(TextFormatting.UNDERLINE + branch.getName() + " " + I18N.localise("binniecore.gui.database.branch.branch"));
		this.branchScientific.setValue(TextFormatting.ITALIC + I18N.localise("binniecore.gui.database.branch.apidae") + " " + branch.getScientific());
		this.branchAuthority.setValue(I18N.localise("binniecore.gui.database.discovered") + " " + TextFormatting.BOLD + branch.getMemberSpecies()[0].getAuthority());
		for (final IWidget widget : this.branchDescription) {
			this.deleteChild(widget);
		}
		this.branchDescription.clear();
		String desc = branch.getDescription();
		if (desc == null || Objects.equals(desc, "") || desc.contains("for.")) {
			desc = I18N.localise("binniecore.gui.database.no.description");
		}
		StringBuilder line = new StringBuilder();
		final List<String> descLines = new ArrayList<>();
		for (final String str : desc.split(" ")) {
			if (RenderUtil.getTextWidth(line + " " + str) > 134) {
				descLines.add(TextFormatting.ITALIC + line.toString() + TextFormatting.RESET);
				line = new StringBuilder();
			}
			line.append(" ").append(str);
		}
		descLines.add(line.toString());
		int i = 0;
		for (final String dLine : descLines) {
			this.branchDescription.add(new ControlTextCentered(this, 84 + 12 * i++, dLine));
		}
	}
}
