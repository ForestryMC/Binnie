package binnie.genetics.gui;

import java.util.Objects;

import net.minecraft.util.text.TextFormatting;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IIndividual;

import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.geometry.Area;
import binnie.core.craftgui.geometry.Point;
import binnie.core.craftgui.geometry.TextJustification;
import binnie.core.util.I18N;

@SideOnly(Side.CLIENT)
public class AnalystPageDescription extends ControlAnalystPage {
	public AnalystPageDescription(final IWidget parent, final Area area, final IIndividual ind) {
		super(parent, area);
		this.setColour(3355443);
		int y = 4;
		final IAlleleSpecies species = ind.getGenome().getPrimary();
		final String branchBinomial = species.getBranch().getScientific();
		final String branchName = species.getBranch().getName();
		final String desc = species.getDescription();
		StringBuilder descBody = new StringBuilder(TextFormatting.ITALIC.toString());
		String descSig = "";
		if (Objects.equals(desc, "") || desc.contains("for.description")) {
			descBody.append("");
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
		new ControlTextCentered(this, y, TextFormatting.UNDERLINE + getTitle()).setColour(this.getColour());
		y += 16;
		new ControlTextCentered(this, y, species.getName() + TextFormatting.RESET).setColour(this.getColour());
		y += 10;
		new ControlTextCentered(this, y, TextFormatting.ITALIC + branchBinomial + " " + species.getBinomial() + TextFormatting.RESET).setColour(this.getColour());
		y += 20;
		String discovered = I18N.localise("genetics.gui.analyst.desc.discovered") + " " + TextFormatting.BOLD + authority + TextFormatting.RESET;
		new ControlTextCentered(this, y, discovered).setColour(this.getColour());
		y += (int) (3.0f + CraftGUI.render.textHeight(discovered, this.width()));
		new ControlTextCentered(this, y, I18N.localise("genetics.gui.analyst.desc.complexity") + ": " + species.getComplexity()).setColour(this.getColour());
		y += 26;
		final ControlText descText = new ControlText(this, new Area(8, y, this.width() - 16, 0), descBody + "§r", TextJustification.TOP_CENTER);
		final IWidget signatureText = new ControlText(this, new Area(8, y, this.width() - 16, 0), descSig + "§r", TextJustification.BOTTOM_RIGHT);
		descText.setColour(this.getColour());
		signatureText.setColour(this.getColour());
		final int descHeight = CraftGUI.render.textHeight(descText.getValue(), descText.getSize().x());
		signatureText.setPosition(new Point(signatureText.pos().x(), descText.getPosition().y() + descHeight + 10));
		this.setSize(new Point(this.width(), 20 + signatureText.yPos()));
	}

	@Override
	public String getTitle() {
		return I18N.localise("genetics.gui.analyst.desc.title");
	}
}
