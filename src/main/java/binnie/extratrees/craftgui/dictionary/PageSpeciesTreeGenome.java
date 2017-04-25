// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.craftgui.dictionary;

import java.util.List;

import net.minecraft.util.IIcon;

import binnie.core.genetics.BreedingSystem;
import forestry.api.arboriculture.IAlleleTreeSpecies;
import forestry.api.arboriculture.ITreeGenome;
import forestry.api.arboriculture.ITree;
import forestry.api.genetics.IAllele;
import binnie.core.craftgui.geometry.IPoint;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;

import binnie.core.craftgui.minecraft.control.ControlItemDisplay;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.FakeWorld;

import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

import forestry.api.core.ForestryAPI;
import binnie.core.BinnieCore;
import forestry.api.arboriculture.EnumTreeChromosome;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.controls.scroll.ControlScrollableContent;
import binnie.core.craftgui.geometry.TextJustification;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.controls.ControlText;
import binnie.Binnie;
import forestry.api.genetics.IAlleleSpecies;
import binnie.core.craftgui.database.DatabaseTab;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.database.PageSpecies;

public class PageSpeciesTreeGenome extends PageSpecies
{
	public PageSpeciesTreeGenome(final IWidget parent, final DatabaseTab tab) {
		super(parent, tab);
	}

	@Override
	public void onValueChanged(final IAlleleSpecies species) {
		this.deleteAllChildren();
		final IAllele[] template = Binnie.Genetics.getTreeRoot().getTemplate(species.getUID());
		if (template == null) {
			return;
		}
		final ITree tree = Binnie.Genetics.getTreeRoot().templateAsIndividual(template);
		if (tree == null) {
			return;
		}

		ItemStack log = null;
		try {
			FakeWorld world = FakeWorld.instance;
			tree.getGenome().getPrimary().getGenerator().setLogBlock(tree.getGenome(), world, 0, 0, 0, ForgeDirection.UP);// getGenome().getPrimary().getGenerator().getWorldGenerator(tree);
			log = world.getWooLog();

		} catch (Exception e) {
			e.printStackTrace();
		}

		final ITreeGenome genome = tree.getGenome();
		final IAlleleTreeSpecies treeSpecies = genome.getPrimary();
		final int w = 144;
		final int h = 176;
		new ControlText(this, new IArea(0.0f, 4.0f, w, 16.0f), this.getValue().toString(), TextJustification.MiddleCenter);
		final ControlScrollableContent scrollable = new ControlScrollableContent(this, 4.0f, 20.0f, w - 8, h - 8 - 16, 12.0f);
		final Control contents = new Control(scrollable, 0.0f, 0.0f, w - 8 - 12, h - 8 - 16);
		final int tw = w - 8 - 12;
		final int w2 = 65;
		final int w3 = tw - 50;
		int y = 0;
		final int th = 14;
		final int th2 = 18;
		final BreedingSystem syst = Binnie.Genetics.treeBreedingSystem;
		new ControlText(contents, new IArea(0.0f, y, w2, th), syst.getChromosomeShortName(EnumTreeChromosome.PLANT) + " : ", TextJustification.MiddleRight);
		new ControlText(contents, new IArea(w2, y, w3, th), treeSpecies.getPlantType().toString(), TextJustification.MiddleLeft);
		y += th;
		new ControlText(contents, new IArea(0.0f, y, w2, th), BinnieCore.proxy.localise("gui.temperature.short") + " : ", TextJustification.MiddleRight);
		new ControlText(contents, new IArea(w2, y, w3, th), treeSpecies.getTemperature().getName(), TextJustification.MiddleLeft);
		y += th;
		final IIcon leaf = treeSpecies.getLeafIcon(false, false);
		IIcon fruit = null;
		int fruitColour = 16777215;
		try {
			fruit = ForestryAPI.textureManager.getIcon(genome.getFruitProvider().getIconIndex(genome, (IBlockAccess) null, 0, 0, 0, 100, false));
			fruitColour = genome.getFruitProvider().getColour(genome, (IBlockAccess) null, 0, 0, 0, 100);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (leaf != null) {
			new ControlText(contents, new IArea(0.0f, y, w2, th2), ExtraTrees.proxy.localise("gui.database.leaves") + " : ", TextJustification.MiddleRight);
			new ControlBlockIconDisplay(contents, w2, y, leaf).setColor(treeSpecies.getLeafColour(false));
			if (fruit != null && !treeSpecies.getUID().equals("forestry.treeOak")) {
				new ControlBlockIconDisplay(contents, w2, y, fruit).setColor(fruitColour);
			}
			y += th2;
		}
		// final ItemStack log = (genome.getFruitProvider().getProducts().length
		// > 0) ? genome.getFruitProvider().getProducts()[0] : null;
		if (log != null) {
			new ControlText(contents, new IArea(0.0f, y, w2, th2), ExtraTrees.proxy.localise("gui.database.log") + " : ", TextJustification.MiddleRight);
			final ControlItemDisplay display = new ControlItemDisplay(contents, w2, y);
			display.setItemStack(log);
			display.setTooltip();
			y += th2;
		}
		new ControlText(contents, new IArea(0.0f, y, w2, th), syst.getChromosomeShortName(EnumTreeChromosome.GROWTH) + " : ", TextJustification.MiddleRight);
		new ControlText(contents, new IArea(w2, y, w3, th), genome.getGrowthProvider().getDescription(), TextJustification.MiddleLeft);
		y += th;
		new ControlText(contents, new IArea(0.0f, y, w2, th), syst.getChromosomeShortName(EnumTreeChromosome.HEIGHT) + " : ", TextJustification.MiddleRight);
		new ControlText(contents, new IArea(w2, y, w3, th), genome.getHeight() + "x", TextJustification.MiddleLeft);
		y += th;
		new ControlText(contents, new IArea(0.0f, y, w2, th), syst.getChromosomeShortName(EnumTreeChromosome.FERTILITY) + " : ", TextJustification.MiddleRight);
		new ControlText(contents, new IArea(w2, y, w3, th), genome.getFertility() + "x", TextJustification.MiddleLeft);
		y += th;
		final List<ItemStack> fruits = new ArrayList<ItemStack>();
		for (final ItemStack stack : genome.getFruitProvider().getProducts()) {
			fruits.add(stack);
		}
		if (!fruits.isEmpty()) {
			new ControlText(contents, new IArea(0.0f, y, w2, th2), syst.getChromosomeShortName(EnumTreeChromosome.FRUITS) + " : ", TextJustification.MiddleRight);
			for (final ItemStack fruitw : fruits) {
				final ControlItemDisplay display2 = new ControlItemDisplay(contents, w2, y);
				display2.setItemStack(fruitw);
				display2.setTooltip();
				y += th2;
			}
		}
		new ControlText(contents, new IArea(0.0f, y, w2, th), syst.getChromosomeShortName(EnumTreeChromosome.YIELD) + " : ", TextJustification.MiddleRight);
		new ControlText(contents, new IArea(w2, y, w3, th), genome.getYield() + "x", TextJustification.MiddleLeft);
		y += th;
		new ControlText(contents, new IArea(0.0f, y, w2, th), syst.getChromosomeShortName(EnumTreeChromosome.SAPPINESS) + " : ", TextJustification.MiddleRight);
		new ControlText(contents, new IArea(w2, y, w3, th), genome.getSappiness() + "x", TextJustification.MiddleLeft);
		y += th;
		new ControlText(contents, new IArea(0.0f, y, w2, th), syst.getChromosomeShortName(EnumTreeChromosome.MATURATION) + " : ", TextJustification.MiddleRight);
		new ControlText(contents, new IArea(w2, y, w3, th), genome.getMaturationTime() + "x", TextJustification.MiddleLeft);
		y += th;
		new ControlText(contents, new IArea(0.0f, y, w2, th), syst.getChromosomeShortName(EnumTreeChromosome.GIRTH) + " : ", TextJustification.MiddleRight);
		new ControlText(contents, new IArea(w2, y, w3, th), genome.getGirth() + "x" + genome.getGirth(), TextJustification.MiddleLeft);
		y += th;
		contents.setSize(new IPoint(contents.size().x(), y));
		scrollable.setScrollableContent(contents);
	}

	public static String tolerated(final boolean t) {
		if (t) {
			return BinnieCore.proxy.localise("gui.tolerated");
		}
		return BinnieCore.proxy.localise("gui.nottolerated");
	}
}
