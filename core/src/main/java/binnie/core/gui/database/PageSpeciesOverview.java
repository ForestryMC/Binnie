package binnie.core.gui.database;

import java.util.Objects;

import net.minecraft.util.text.TextFormatting;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.genetics.IAlleleSpecies;

import binnie.core.gui.CraftGUI;
import binnie.core.api.gui.IWidget;
import binnie.core.gui.controls.ControlText;
import binnie.core.gui.controls.ControlTextCentered;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.geometry.TextJustification;
import binnie.core.util.I18N;
import binnie.core.util.EmptyHelper;

import org.apache.commons.lang3.StringUtils;

@SideOnly(Side.CLIENT)
public class PageSpeciesOverview extends PageSpecies {
	private final ControlText controlName;
	private final ControlText controlScientific;
	private final ControlText controlAuthority;
	private final ControlText controlComplexity;
	private final ControlText controlDescription;
	private final ControlText controlSignature;
	private final ControlIndividualDisplay controlInd1;
	private final ControlIndividualDisplay controlInd2;

	public PageSpeciesOverview(final IWidget parent, final DatabaseTab tab) {
		super(parent, tab);
		this.controlInd1 = new ControlIndividualDisplay(this, 5, 5);
		this.controlInd2 = new ControlIndividualDisplay(this, 123, 5);
		this.controlName = new ControlTextCentered(this, 8, StringUtils.EMPTY);
		this.controlScientific = new ControlTextCentered(this, 32, StringUtils.EMPTY);
		this.controlAuthority = new ControlTextCentered(this, 44, StringUtils.EMPTY);
		this.controlComplexity = new ControlTextCentered(this, 68, StringUtils.EMPTY);
		this.controlDescription = new ControlText(this, new Area(8, 84, this.getSize().xPos() - 16, 0), StringUtils.EMPTY, TextJustification.MIDDLE_CENTER);
		this.controlSignature = new ControlText(this, new Area(8, 84, this.getSize().xPos() - 16, 0), StringUtils.EMPTY, TextJustification.BOTTOM_RIGHT);
	}

	@Override
	public void onValueChanged(final IAlleleSpecies species) {
		this.controlInd1.setSpecies(species, EnumDiscoveryState.SHOW);
		this.controlInd2.setSpecies(species, EnumDiscoveryState.SHOW);
		final String branchBinomial = species.getBranch().getScientific();
		this.controlName.setValue(TextFormatting.UNDERLINE + species.getAlleleName());
		this.controlScientific.setValue(TextFormatting.ITALIC + branchBinomial + ' ' + species.getBinomial());
		this.controlAuthority.setValue(I18N.localise(DatabaseConstants.KEY + ".discovered") + ": " + TextFormatting.BOLD + species.getAuthority());
		this.controlComplexity.setValue(I18N.localise(DatabaseConstants.KEY + ".overview.complexity") + ": " + species.getComplexity());
		final String desc = species.getDescription();
		final StringBuilder descBody = new StringBuilder(TextFormatting.ITALIC.toString());
		String descSig = StringUtils.EMPTY;
		if (desc == null || desc.length() == 0 || desc.contains("for.description") || desc.contains(".desc")) {
			descBody.append(I18N.localise(DatabaseConstants.KEY + ".no.description"));
		} else {
			final String[] descStrings = desc.split("\\|");
			descBody.append(descStrings[0]);
			for (int i = 1; i < descStrings.length - 1; ++i) {
				descBody.append(' ').append(descStrings[i]);
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
