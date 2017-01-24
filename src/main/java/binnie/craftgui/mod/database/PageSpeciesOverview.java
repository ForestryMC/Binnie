package binnie.craftgui.mod.database;

import binnie.Binnie;
import binnie.craftgui.controls.ControlText;
import binnie.craftgui.controls.ControlTextCentered;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.core.geometry.TextJustification;
import forestry.api.genetics.IAlleleSpecies;
import net.minecraft.util.text.TextFormatting;

import java.util.Objects;

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
		this.controlComplexity = new ControlTextCentered(this, 68.0f, "");
		this.controlDescription = new ControlText(this, new IArea(8.0f, 84.0f, this.getSize().x() - 16.0f, 0.0f), "", TextJustification.MiddleCenter);
		this.controlSignature = new ControlText(this, new IArea(8.0f, 84.0f, this.getSize().x() - 16.0f, 0.0f), "", TextJustification.BottomRight);
	}

	@Override
	public void onValueChanged(final IAlleleSpecies species) {
		this.controlInd1.setSpecies(species, EnumDiscoveryState.Show);
		this.controlInd2.setSpecies(species, EnumDiscoveryState.Show);
		final String branchBinomial = (species.getBranch() != null) ? species.getBranch().getScientific() : "<Unknown>";
		final String branchName = (species.getBranch() != null) ? species.getBranch().getName() : "Unknown";
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
		final float descHeight = CraftGUI.render.textHeight(this.controlDescription.getValue(), this.controlDescription.getSize().x());
		this.controlSignature.setPosition(new IPoint(this.controlSignature.pos().x(), this.controlDescription.getPosition().y() + descHeight + 10.0f));
	}
}
