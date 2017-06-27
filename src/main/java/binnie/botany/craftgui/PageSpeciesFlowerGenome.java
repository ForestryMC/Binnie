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
import binnie.core.craftgui.database.DatabaseTab;
import binnie.core.craftgui.database.PageSpecies;
import binnie.core.craftgui.geometry.Area;
import binnie.core.craftgui.geometry.Point;
import binnie.core.craftgui.geometry.TextJustification;
import binnie.core.craftgui.minecraft.control.ControlItemDisplay;

public class PageSpeciesFlowerGenome extends PageSpecies {
	public PageSpeciesFlowerGenome(final IWidget parent, final DatabaseTab tab) {
		super(parent, tab);
	}

	public static String tolerated(final boolean t) {
		if (t) {
			return "Tolerated";
		}
		return "Not Tolerated";
	}

	@Override
	public void onValueChanged(final IAlleleSpecies species) {
		this.deleteAllChildren();
		final IAllele[] template = Binnie.GENETICS.getFlowerRoot().getTemplate(species.getUID());
		if (template == null) {
			return;
		}
		final IFlower tree = Binnie.GENETICS.getFlowerRoot().templateAsIndividual(template);
		if (tree == null) {
			return;
		}
		final IFlowerGenome genome = tree.getGenome();
		final IAlleleFlowerSpecies treeSpecies = genome.getPrimary();
		final int w = 144;
		final int h = 176;
		new ControlText(this, new Area(0, 4, w, 16), "Genome", TextJustification.MiddleCenter);
		final ControlScrollableContent scrollable = new ControlScrollableContent(this, 4, 20, w - 8, h - 8 - 16, 12);
		final Control contents = new Control(scrollable, 0, 0, w - 8 - 12, h - 8 - 16);
		final int tw = w - 8 - 12;
		final int w2 = 55;
		final int w3 = tw - 50;
		int y = 0;
		final int th = 14;
		final int th2 = 18;
		new ControlText(contents, new Area(0, y, w2, th), "Temp. : ", TextJustification.MiddleRight);
		new ControlText(contents, new Area(w2, y, w3, th), treeSpecies.getTemperature().getName(), TextJustification.MIDDLE_LEFTt);
		y += th;
		new ControlText(contents, new Area(0, y, w2, th), "Moist. : ", TextJustification.MiddleRight);
		new ControlText(contents, new Area(w2, y, w3, th), treeSpecies.getMoisture().getLocalisedName(false), TextJustification.MIDDLE_LEFTt);
		y += th;
		new ControlText(contents, new Area(0, y, w2, th), "pH. : ", TextJustification.MiddleRight);
		new ControlText(contents, new Area(w2, y, w3, th), treeSpecies.getPH().getLocalisedName(false), TextJustification.MIDDLE_LEFTt);
		y += th;
		new ControlText(contents, new Area(0, y, w2, th), "Fertility : ", TextJustification.MiddleRight);
		new ControlText(contents, new Area(w2, y, w3, th), genome.getFertility() + "x", TextJustification.MIDDLE_LEFTt);
		y += th;
		final float lifespan = genome.getLifespan() * 68.27f / genome.getAgeChance() / 24000.0f;
		new ControlText(contents, new Area(0, y, w2, th), "Lifespan : ", TextJustification.MiddleRight);
		new ControlText(contents, new Area(w2, y, w3, th), "" + String.format("%.2f", lifespan) + " days", TextJustification.MIDDLE_LEFTt);
		y += th;
		new ControlText(contents, new Area(0, y, w2, th), "Nectar : ", TextJustification.MiddleRight);
		new ControlText(contents, new Area(w2, y, w3, th), genome.getActiveAllele(EnumFlowerChromosome.SAPPINESS).getName(), TextJustification.MIDDLE_LEFTt);
		y += th;
		int x = w2;
		int tot = 0;
		for (final IIndividual vid : BotanyCore.getFlowerRoot().getIndividualTemplates()) {
			if (vid.getGenome().getPrimary() == treeSpecies) {
				if (tot > 0 && tot % 3 == 0) {
					x -= 54;
					y += 18;
				}
				final ItemStack stack = BotanyCore.getFlowerRoot().getMemberStack(vid, EnumFlowerStage.FLOWER);
				final ControlItemDisplay display = new ControlItemDisplay(contents, x, y);
				display.setItemStack(stack);
				++tot;
				x += 18;
			}
		}
		final int numOfLines = 1 + (tot - 1) / 3;
		new ControlText(contents, new Area(0, y - (numOfLines - 1) * 18, w2, 4 + 18 * numOfLines), "Varieties : ", TextJustification.MiddleRight);
		y += th;
		contents.setSize(new Point(contents.size().x(), y));
		scrollable.setScrollableContent(contents);
	}
}
