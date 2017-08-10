package binnie.core.gui.database;

import java.util.Objects;

import net.minecraft.util.text.TextFormatting;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.genetics.IAlleleSpecies;

import binnie.core.gui.CraftGUI;
import binnie.core.gui.IWidget;
import binnie.core.gui.controls.ControlText;
import binnie.core.gui.controls.ControlTextCentered;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.geometry.TextJustification;
import binnie.core.util.I18N;

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
		this.controlDescription = new ControlText(this, new Area(8, 84, this.getSize().xPos() - 16, 0), "", TextJustification.MIDDLE_CENTER);
		this.controlSignature = new ControlText(this, new Area(8, 84, this.getSize().xPos() - 16, 0), "", TextJustification.BOTTOM_RIGHT);
	}

	@Override
	public void onValueChanged(final IAlleleSpecies species) {
		this.controlInd1.setSpecies(species, EnumDiscoveryState.Show);
		this.controlInd2.setSpecies(species, EnumDiscoveryState.Show);
		final String branchBinomial = species.getBranch().getScientific();
		this.controlName.setValue(TextFormatting.UNDERLINE + species.getAlleleName());
		this.controlScientific.setValue(TextFormatting.ITALIC + branchBinomial + " " + species.getBinomial());
		this.controlAuthority.setValue(I18N.localise(DatabaseConstants.KEY + ".discovered") + ": " + TextFormatting.BOLD + species.getAuthority());
		this.controlComplexity.setValue(I18N.localise(DatabaseConstants.KEY + ".overview.complexity") + ": " + species.getComplexity());
		final String desc = species.getDescription();
		StringBuilder descBody = new StringBuilder(TextFormatting.ITALIC.toString());
		String descSig = "";
		if (desc == null || Objects.equals(desc, "") || desc.contains("for.description") || desc.contains(".desc")) {
			descBody.append(I18N.localise(DatabaseConstants.KEY + ".no.description"));
		} else {
			final String[] descStrings = desc.split("\\|");
			descBody.append(descStrings[0]);
			for (int i = 1; i < descStrings.length - 1; ++i) {
				descBody.append(" ").append(descStrings[i]);
			}
			if (descStrings.length > 1) {
				descSig += descStrings[descStrings.length - 1];
			}
		}
		this.controlDescription.setValue(descBody.toString() + TextFormatting.RESET);
		this.controlSignature.setValue(descSig);
		final int descHeight = CraftGUI.RENDER.textHeight(this.controlDescription.getValue(), this.controlDescription.getSize().xPos());
		this.controlSignature.setPosition(new Point(controlSignature.getPosition().xPos(), this.controlDescription.getPosition().yPos() + descHeight + 10));
	}
}
