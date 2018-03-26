package binnie.botany.gui.database;

import binnie.botany.EnumHelper;
import net.minecraft.item.ItemStack;

import binnie.botany.api.BotanyAPI;
import binnie.botany.api.genetics.EnumFlowerChromosome;
import binnie.botany.api.genetics.EnumFlowerStage;
import binnie.botany.api.genetics.IAlleleFlowerSpecies;
import binnie.botany.api.genetics.IFlower;
import binnie.botany.api.genetics.IFlowerGenome;
import binnie.botany.core.BotanyCore;
import binnie.core.api.gui.IWidget;
import binnie.core.gui.controls.ControlText;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.controls.scroll.ControlScrollableContent;
import binnie.core.gui.database.DatabaseConstants;
import binnie.core.gui.database.DatabaseTab;
import binnie.core.gui.database.PageSpecies;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.geometry.TextJustification;
import binnie.core.gui.minecraft.control.ControlItemDisplay;
import binnie.core.util.I18N;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IIndividual;

public class PageSpeciesFlowerGenome extends PageSpecies {
	public PageSpeciesFlowerGenome(IWidget parent, DatabaseTab tab) {
		super(parent, tab);
	}

	@Override
	public void onValueChanged(IAlleleSpecies species) {
		deleteAllChildren();
		IAllele[] template = BotanyAPI.flowerRoot.getTemplate(species.getUID());
		if (template == null) {
			return;
		}

		IFlower flower = BotanyAPI.flowerRoot.templateAsIndividual(template);
		IFlowerGenome genome = flower.getGenome();
		IAlleleFlowerSpecies flowerSpecies = genome.getPrimary();
		int w = 144;
		int h = 176;
		new ControlText(this, new Area(0, 4, w, 16), I18N.localise(DatabaseConstants.BOTANY_GENOME_KEY + ".title"), TextJustification.MIDDLE_CENTER);
		ControlScrollableContent scrollable = new ControlScrollableContent(this, 4, 20, w - 8, h - 8 - 16, 12);
		Control contents = new Control(scrollable, 0, 0, w - 8 - 12, h - 8 - 16);
		int tw = w - 8 - 12;
		int w2 = 55;
		int w3 = tw - 50;
		int y = 0;
		int th = 14;
		int th2 = 18;
		new ControlText(contents, new Area(0, y, w2, th), I18N.localise(DatabaseConstants.BOTANY_GENOME_KEY + ".temp"), TextJustification.MIDDLE_RIGHT);
		new ControlText(contents, new Area(w2, y, w3, th), flowerSpecies.getTemperature().getName(), TextJustification.MIDDLE_LEFT);

		y += th;
		new ControlText(contents, new Area(0, y, w2, th), I18N.localise(DatabaseConstants.BOTANY_GENOME_KEY + ".moist"), TextJustification.MIDDLE_RIGHT);
		new ControlText(contents, new Area(w2, y, w3, th), flowerSpecies.getMoisture().getLocalisedName(false), TextJustification.MIDDLE_LEFT);

		y += th;
		new ControlText(contents, new Area(0, y, w2, th), I18N.localise(DatabaseConstants.BOTANY_GENOME_KEY + ".ph"), TextJustification.MIDDLE_RIGHT);
		new ControlText(contents, new Area(w2, y, w3, th), EnumHelper.getLocalisedName(flowerSpecies.getPH(), false), TextJustification.MIDDLE_LEFT);

		y += th;
		new ControlText(contents, new Area(0, y, w2, th), I18N.localise(DatabaseConstants.BOTANY_GENOME_KEY + ".fertility"), TextJustification.MIDDLE_RIGHT);
		new ControlText(contents, new Area(w2, y, w3, th), genome.getFertility() + "x", TextJustification.MIDDLE_LEFT);

		y += th;
		float lifespan = genome.getLifespan() * 68.27f / genome.getAgeChance() / 24000.0f;
		String lifespanValue = String.format("%.2f", lifespan);
		new ControlText(contents, new Area(0, y, w2, th), I18N.localise(DatabaseConstants.BOTANY_GENOME_KEY + ".lifespan"), TextJustification.MIDDLE_RIGHT);
		new ControlText(contents, new Area(w2, y, w3, th), I18N.localise(DatabaseConstants.BOTANY_GENOME_KEY + ".lifespan.value", lifespanValue), TextJustification.MIDDLE_LEFT);

		y += th;
		new ControlText(contents, new Area(0, y, w2, th), I18N.localise(DatabaseConstants.BOTANY_GENOME_KEY + ".nectar"), TextJustification.MIDDLE_RIGHT);
		new ControlText(contents, new Area(w2, y, w3, th), genome.getActiveAllele(EnumFlowerChromosome.SAPPINESS).getAlleleName(), TextJustification.MIDDLE_LEFT);

		y += th;
		int x = w2;
		int tot = 0;
		for (IIndividual vid : BotanyCore.getFlowerRoot().getIndividualTemplates()) {
			if (vid.getGenome().getPrimary() == flowerSpecies) {
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
		new ControlText(contents, new Area(0, y - (numOfLines - 1) * 18, w2, 4 + 18 * numOfLines), I18N.localise(DatabaseConstants.BOTANY_GENOME_KEY + ".varieties"), TextJustification.MIDDLE_RIGHT);
		y += th;
		contents.setSize(new Point(contents.getSize().xPos(), y));
		scrollable.setScrollableContent(contents);
	}
}
