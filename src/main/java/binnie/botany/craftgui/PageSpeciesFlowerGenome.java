package binnie.botany.craftgui;

import net.minecraft.item.ItemStack;

import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IIndividual;

import binnie.Binnie;
import binnie.botany.api.EnumFlowerChromosome;
import binnie.botany.api.EnumFlowerStage;
import binnie.botany.api.IAlleleFlowerSpecies;
import binnie.botany.api.IFlower;
import binnie.botany.api.IFlowerGenome;
import binnie.botany.core.BotanyCore;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.controls.scroll.ControlScrollableContent;
import binnie.core.craftgui.geometry.Area;
import binnie.core.craftgui.geometry.Point;
import binnie.core.craftgui.geometry.TextJustification;
import binnie.core.craftgui.minecraft.control.ControlItemDisplay;
import binnie.core.util.I18N;
import binnie.genetics.gui.database.DatabaseTab;
import binnie.genetics.gui.database.PageSpecies;

public class PageSpeciesFlowerGenome extends PageSpecies {
	public PageSpeciesFlowerGenome(IWidget parent, DatabaseTab tab) {
		super(parent, tab);
	}

	public static String tolerated(boolean t) {
		if (t) {
			return "Tolerated";
		}
		return "Not Tolerated";
	}

	@Override
	public void onValueChanged(IAlleleSpecies species) {
		deleteAllChildren();
		IAllele[] template = Binnie.GENETICS.getFlowerRoot().getTemplate(species.getUID());
		if (template == null) {
			return;
		}

		IFlower tree = Binnie.GENETICS.getFlowerRoot().templateAsIndividual(template);
		IFlowerGenome genome = tree.getGenome();
		IAlleleFlowerSpecies treeSpecies = genome.getPrimary();
		int w = 144;
		int h = 176;
		new ControlText(this, new Area(0, 4, w, 16), I18N.localise("botany.gui.database.tab.genome"), TextJustification.MIDDLE_CENTER);
		ControlScrollableContent scrollable = new ControlScrollableContent(this, 4, 20, w - 8, h - 8 - 16, 12);
		Control contents = new Control(scrollable, 0, 0, w - 8 - 12, h - 8 - 16);
		int tw = w - 8 - 12;
		int w2 = 55;
		int w3 = tw - 50;
		int y = 0;
		int th = 14;
		int th2 = 18;
		new ControlText(contents, new Area(0, y, w2, th), I18N.localise("botany.gui.database.tab.genome.temp"), TextJustification.MIDDLE_RIGHT);
		new ControlText(contents, new Area(w2, y, w3, th), treeSpecies.getTemperature().getName(), TextJustification.MIDDLE_LEFT);

		y += th;
		new ControlText(contents, new Area(0, y, w2, th), I18N.localise("botany.gui.database.tab.genome.moist"), TextJustification.MIDDLE_RIGHT);
		new ControlText(contents, new Area(w2, y, w3, th), treeSpecies.getMoisture().getLocalisedName(false), TextJustification.MIDDLE_LEFT);

		y += th;
		new ControlText(contents, new Area(0, y, w2, th), I18N.localise("botany.gui.database.tab.genome.ph"), TextJustification.MIDDLE_RIGHT);
		new ControlText(contents, new Area(w2, y, w3, th), treeSpecies.getPH().getLocalisedName(false), TextJustification.MIDDLE_LEFT);

		y += th;
		new ControlText(contents, new Area(0, y, w2, th), I18N.localise("botany.gui.database.tab.genome.fertility"), TextJustification.MIDDLE_RIGHT);
		new ControlText(contents, new Area(w2, y, w3, th), genome.getFertility() + "x", TextJustification.MIDDLE_LEFT);

		y += th;
		float lifespan = genome.getLifespan() * 68.27f / genome.getAgeChance() / 24000.0f;
		String lifespanValue = String.format("%.2f", lifespan);
		new ControlText(contents, new Area(0, y, w2, th), I18N.localise("botany.gui.database.tab.genome.lifespan"), TextJustification.MIDDLE_RIGHT);
		new ControlText(contents, new Area(w2, y, w3, th), I18N.localise("botany.gui.database.tab.genome.lifespan.value", lifespanValue), TextJustification.MIDDLE_LEFT);

		y += th;
		new ControlText(contents, new Area(0, y, w2, th), I18N.localise("botany.gui.database.tab.genome.nectar"), TextJustification.MIDDLE_RIGHT);
		new ControlText(contents, new Area(w2, y, w3, th), genome.getActiveAllele(EnumFlowerChromosome.SAPPINESS).getAlleleName(), TextJustification.MIDDLE_LEFT);

		y += th;
		int x = w2;
		int tot = 0;
		for (IIndividual vid : BotanyCore.getFlowerRoot().getIndividualTemplates()) {
			if (vid.getGenome().getPrimary() == treeSpecies) {
				if (tot > 0 && tot % 3 == 0) {
					x -= 54;
					y += 18;
				}
				ItemStack stack = BotanyCore.getFlowerRoot().getMemberStack(vid, EnumFlowerStage.FLOWER);
				ControlItemDisplay display = new ControlItemDisplay(contents, x, y);
				display.setItemStack(stack);
				++tot;
				x += 18;
			}
		}

		int numOfLines = 1 + (tot - 1) / 3;
		new ControlText(contents, new Area(0, y - (numOfLines - 1) * 18, w2, 4 + 18 * numOfLines), I18N.localise("botany.gui.database.tab.genome.varieties"), TextJustification.MIDDLE_RIGHT);
		y += th;
		contents.setSize(new Point(contents.size().x(), y));
		scrollable.setScrollableContent(contents);
	}
}
