package binnie.core.gui.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import net.minecraft.util.text.TextFormatting;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.genetics.IClassification;

import binnie.core.api.gui.IWidget;
import binnie.core.gui.controls.ControlText;
import binnie.core.gui.controls.ControlTextCentered;
import binnie.core.gui.renderer.RenderUtil;
import binnie.core.util.I18N;
import binnie.core.util.EmptyHelper;

@SideOnly(Side.CLIENT)
public class PageBranchOverview extends PageBranch {
	private final ControlText branchName;
	private final ControlText branchScientific;
	private final ControlText branchAuthority;
	private final List<ControlText> branchDescription;

	public PageBranchOverview(final IWidget parent, final DatabaseTab tab) {
		super(parent, tab);
		this.branchDescription = new ArrayList<>();
		this.branchName = new ControlTextCentered(this, 8, EmptyHelper.EMPTY_STRING);
		this.branchScientific = new ControlTextCentered(this, 32, EmptyHelper.EMPTY_STRING);
		this.branchAuthority = new ControlTextCentered(this, 44, EmptyHelper.EMPTY_STRING);
	}

	@Override
	public void onValueChanged(final IClassification branch) {
		this.branchName.setValue(TextFormatting.UNDERLINE + I18N.localise(DatabaseConstants.BRANCH_KEY + ".name", branch.getName()));
		this.branchScientific.setValue(TextFormatting.ITALIC + I18N.localise(DatabaseConstants.BRANCH_KEY + ".apidae", branch.getScientific()));
		this.branchAuthority.setValue(I18N.localise(DatabaseConstants.BRANCH_KEY + ".discoveredBy", TextFormatting.BOLD + branch.getMemberSpecies()[0].getAuthority()));
		for (final IWidget widget : this.branchDescription) {
			this.deleteChild(widget);
		}
		this.branchDescription.clear();
		String desc = branch.getDescription();
		if (desc == null || Objects.equals(desc, EmptyHelper.EMPTY_STRING) || desc.contains("for.")) {
			desc = I18N.localise(DatabaseConstants.BRANCH_KEY + ".noDesc");
		}
		StringBuilder line = new StringBuilder();
		final List<String> descLines = new ArrayList<>();
		for (final String str : desc.split(" ")) {
			if (RenderUtil.getTextWidth(line + " " + str) > 134) {
				descLines.add(TextFormatting.ITALIC + line.toString() + TextFormatting.RESET);
				line.setLength(0);
			}
			line.append(' ').append(str);
		}
		descLines.add(line.toString());
		int i = 0;
		for (final String dLine : descLines) {
			this.branchDescription.add(new ControlTextCentered(this, 84 + 12 * i++, dLine));
		}
	}
}
