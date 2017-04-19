package binnie.core.craftgui.database;

import binnie.Binnie;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.renderer.RenderUtil;
import forestry.api.genetics.IClassification;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SideOnly(Side.CLIENT)
public class PageBranchOverview extends PageBranch {
	private ControlText pageBranchOverview_branchName;
	private ControlText pageBranchOverview_branchScientific;
	private ControlText pageBranchOverview_branchAuthority;
	private List<ControlText> pageBranchOverview_branchDescription;

	public PageBranchOverview(final IWidget parent, final DatabaseTab tab) {
		super(parent, tab);
		this.pageBranchOverview_branchDescription = new ArrayList<>();
		this.pageBranchOverview_branchName = new ControlTextCentered(this, 8, "");
		this.pageBranchOverview_branchScientific = new ControlTextCentered(this, 32, "");
		this.pageBranchOverview_branchAuthority = new ControlTextCentered(this, 44, "");
	}

	@Override
	public void onValueChanged(final IClassification branch) {
		this.pageBranchOverview_branchName.setValue(TextFormatting.UNDERLINE + branch.getName() + Binnie.LANGUAGE.localise("binniecore.gui.database.branch.branch"));
		this.pageBranchOverview_branchScientific.setValue(TextFormatting.ITALIC + Binnie.LANGUAGE.localise("binniecore.gui.database.branch.apidae") + " " + branch.getScientific());
		this.pageBranchOverview_branchAuthority.setValue(Binnie.LANGUAGE.localise("binniecore.gui.database.discovered") + " " + TextFormatting.BOLD + branch.getMemberSpecies()[0].getAuthority());
		for (final IWidget widget : this.pageBranchOverview_branchDescription) {
			this.deleteChild(widget);
		}
		this.pageBranchOverview_branchDescription.clear();
		String desc = branch.getDescription();
		if (desc == null || Objects.equals(desc, "")) {
			desc = Binnie.LANGUAGE.localise("binniecore.gui.database.no.description");
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
			this.pageBranchOverview_branchDescription.add(new ControlTextCentered(this, 84 + 12 * i++, dLine));
		}
	}
}
