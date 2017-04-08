package binnie.core.craftgui.database;

import binnie.Binnie;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.geometry.Area;
import binnie.core.craftgui.geometry.Point;
import binnie.core.craftgui.geometry.TextJustification;
import forestry.api.genetics.IAlleleSpecies;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Objects;

@SideOnly(Side.CLIENT)
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
		this.controlInd1 = new ControlDatabaseIndividualDisplay(this, 5, 5);
		this.controlInd2 = new ControlDatabaseIndividualDisplay(this, 123, 5);
		this.controlName = new ControlTextCentered(this, 8, "");
		this.controlScientific = new ControlTextCentered(this, 32, "");
		this.controlAuthority = new ControlTextCentered(this, 44, "");
		this.controlComplexity = new ControlTextCentered(this, 68, "");
		this.controlDescription = new ControlText(this, new Area(8, 84, this.getSize().x() - 16, 0), "", TextJustification.MiddleCenter);
		this.controlSignature = new ControlText(this, new Area(8, 84, this.getSize().x() - 16, 0), "", TextJustification.BottomRight);
	}

	@Override
	public void onValueChanged(final IAlleleSpecies species) {
		this.controlInd1.setSpecies(species, EnumDiscoveryState.Show);
		this.controlInd2.setSpecies(species, EnumDiscoveryState.Show);
		final String branchBinomial = species.getBranch().getScientific();
		final String branchName = species.getBranch().getName();
		this.controlName.setValue(TextFormatting.UNDERLINE + species.getName());
		this.controlScientific.setValue(TextFormatting.ITALIC + branchBinomial + " " + species.getBinomial());
		this.controlAuthority.setValue(Binnie.LANGUAGE.localise("binniecore.gui.database.discovered") + ": " + TextFormatting.BOLD + species.getAuthority());
		this.controlComplexity.setValue(Binnie.LANGUAGE.localise("binniecore.gui.database.overview.complexity") + ": " + species.getComplexity());
		final String desc = species.getDescription();
		String descBody = TextFormatting.ITALIC.toString();
		String descSig = "";
		if (desc == null || Objects.equals(desc, "")) {
			descBody += Binnie.LANGUAGE.localise("binniecore.gui.database.no.description");
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
		this.controlDescription.setValue(descBody + TextFormatting.RESET);
		this.controlSignature.setValue(descSig);
		final int descHeight = CraftGUI.render.textHeight(this.controlDescription.getValue(), this.controlDescription.getSize().x());
		this.controlSignature.setPosition(new Point(this.controlSignature.pos().x(), this.controlDescription.getPosition().y() + descHeight + 10));
	}
}
