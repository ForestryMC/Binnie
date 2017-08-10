package binnie.genetics.gui.analyst;

import java.util.Objects;

import net.minecraft.util.text.TextFormatting;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IIndividual;

import binnie.core.gui.CraftGUI;
import binnie.core.gui.IWidget;
import binnie.core.gui.controls.ControlText;
import binnie.core.gui.controls.ControlTextCentered;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.geometry.TextJustification;
import binnie.core.util.I18N;

@SideOnly(Side.CLIENT)
public class AnalystPageDescription extends ControlAnalystPage {
	public AnalystPageDescription(IWidget parent, Area area, IIndividual ind) {
		super(parent, area);
		setColor(3355443);
		int y = 4;
		IAlleleSpecies species = ind.getGenome().getPrimary();
		String branchBinomial = species.getBranch().getScientific();
		String branchName = species.getBranch().getName();
		String desc = species.getDescription();
		StringBuilder descBody = new StringBuilder(TextFormatting.ITALIC.toString());
		String descSig = "";
		if (Objects.equals(desc, "") || desc.contains("for.description") || desc.contains(".desc")) {
			descBody.append("");
		} else {
			String[] descStrings = desc.split("\\|");
			descBody.append(descStrings[0]);
			for (int i = 1; i < descStrings.length - 1; ++i) {
				descBody.append(" ").append(descStrings[i]);
			}
			if (descStrings.length > 1) {
				descSig += descStrings[descStrings.length - 1];
			}
		}
		String authority = species.getAuthority();
		if (authority.contains("Binnie")) {
			authority = TextFormatting.DARK_BLUE.toString() + TextFormatting.BOLD + authority;
		}
		if (authority.contains("Sengir")) {
			authority = TextFormatting.DARK_GREEN.toString() + TextFormatting.BOLD + authority;
		}
		if (authority.contains("MysteriousAges")) {
			authority = TextFormatting.DARK_PURPLE.toString() + TextFormatting.BOLD + authority;
		}
		new ControlTextCentered(this, y, TextFormatting.UNDERLINE + getTitle()).setColor(getColor());
		y += 16;
		new ControlTextCentered(this, y, species.getAlleleName() + TextFormatting.RESET).setColor(getColor());
		y += 10;
		new ControlTextCentered(this, y, TextFormatting.ITALIC + branchBinomial + " " + species.getBinomial() + TextFormatting.RESET).setColor(getColor());
		y += 20;
		String discovered = I18N.localise(AnalystConstants.DESCRIPTION_KEY + ".discovered") + " " + TextFormatting.BOLD + authority + TextFormatting.RESET;
		new ControlTextCentered(this, y, discovered).setColor(getColor());
		y += (int) (3.0f + CraftGUI.RENDER.textHeight(discovered, getWidth()));
		new ControlTextCentered(this, y, I18N.localise(AnalystConstants.DESCRIPTION_KEY + ".complexity") + ": " + species.getComplexity()).setColor(getColor());
		y += 26;
		ControlText descText = new ControlText(this, new Area(8, y, getWidth() - 16, 0), descBody + "§r", TextJustification.TOP_CENTER);
		IWidget signatureText = new ControlText(this, new Area(8, y, getWidth() - 16, 0), descSig + "§r", TextJustification.BOTTOM_RIGHT);
		descText.setColor(getColor());
		signatureText.setColor(getColor());
		int descHeight = CraftGUI.RENDER.textHeight(descText.getValue(), descText.getSize().xPos());
		signatureText.setPosition(new Point(signatureText.getPosition().xPos(), descText.getPosition().yPos() + descHeight + 10));
		setSize(new Point(getWidth(), 20 + signatureText.getYPos()));
	}

	@Override
	public String getTitle() {
		return I18N.localise(AnalystConstants.DESCRIPTION_KEY + ".title");
	}
}
