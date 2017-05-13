package binnie.extratrees.machines.craftgui;

import binnie.Binnie;
import binnie.core.BinnieCore;
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
import binnie.core.genetics.BreedingSystem;
import binnie.extratrees.ExtraTrees;
import forestry.api.arboriculture.*;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleSpecies;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SideOnly(Side.CLIENT)
public class PageSpeciesTreeGenome extends PageSpecies {
	public PageSpeciesTreeGenome(final IWidget parent, final DatabaseTab tab) {
		super(parent, tab);
	}

	@Override
	public void onValueChanged(final IAlleleSpecies species) {
		this.deleteAllChildren();
		final IAllele[] template = Binnie.GENETICS.getTreeRoot().getTemplate(species.getUID());
		if (template == null) {
			return;
		}
		final ITree tree = Binnie.GENETICS.getTreeRoot().templateAsIndividual(template);

		final ITreeGenome genome = tree.getGenome();
		final IAlleleTreeSpecies treeSpecies = genome.getPrimary();
		final int w = 144;
		final int h = 176;
		new ControlText(this, new Area(0, 4, w, 16), this.getValue().toString(), TextJustification.MiddleCenter);
		final ControlScrollableContent scrollable = new ControlScrollableContent(this, 4, 20, w - 8, h - 8 - 16, 12);
		final Control contents = new Control(scrollable, 0, 0, w - 8 - 12, h - 8 - 16);
		final int tw = w - 8 - 12;
		final int w2 = 65;
		final int w3 = tw - 50;
		int y = 0;
		final int th = 14;
		final int th2 = 18;
		final BreedingSystem syst = Binnie.GENETICS.treeBreedingSystem;
		new ControlText(contents, new Area(w2, y, w3, th), treeSpecies.getPlantType().toString(), TextJustification.MIDDLE_LEFTt);
		y += th;
		new ControlText(contents, new Area(0, y, w2, th), BinnieCore.getBinnieProxy().localise("gui.temperature.short") + " : ", TextJustification.MiddleRight);
		new ControlText(contents, new Area(w2, y, w3, th), treeSpecies.getTemperature().getName(), TextJustification.MIDDLE_LEFTt);
		y += th;
		TextureMap map = Minecraft.getMinecraft().getTextureMapBlocks();
		ILeafSpriteProvider spriteProvider = treeSpecies.getLeafSpriteProvider();
		TextureAtlasSprite leaf = map.getAtlasSprite(spriteProvider.getSprite(false, false).toString());
		int leafColour = spriteProvider.getColor(false);
		TextureAtlasSprite fruit = null;
		int fruitColour = 16777215;
		IFruitProvider fruitProvider = genome.getFruitProvider();
		try {
			fruit = map.getAtlasSprite(fruitProvider.getSprite(genome, null, BlockPos.ORIGIN, 100).toString());
			fruitColour = fruitProvider.getColour(genome, null, BlockPos.ORIGIN, 100);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (leaf != null) {
		new ControlText(contents, new Area(0, y, w2, th2), ExtraTrees.proxy.localise("gui.database.leaves") + " : ", TextJustification.MiddleRight);
		new ControlBlockIconDisplay(contents, w2, y, leaf).setColour(leafColour);
		if (fruit != null && !treeSpecies.getUID().equals("forestry.treeOak")) {
		 new ControlBlockIconDisplay(contents, w2, y, fruit).setColour(fruitColour);
		}
			y += th2;
		}
		Map<ItemStack, Float> products = fruitProvider.getProducts();
		ItemStack log = treeSpecies.getWoodProvider().getWoodStack();
		if (log.isEmpty()) {
			new ControlText(contents, new Area(0, y, w2, th2), ExtraTrees.proxy.localise("gui.database.log") + " : ", TextJustification.MiddleRight);
			final ControlItemDisplay display = new ControlItemDisplay(contents, w2, y);
			display.setItemStack(log);
			display.setTooltip();
			y += th2;
		}
		new ControlText(contents, new Area(0, y, w2, th), syst.getChromosomeShortName(EnumTreeChromosome.HEIGHT) + " : ", TextJustification.MiddleRight);
		new ControlText(contents, new Area(w2, y, w3, th), genome.getHeight() + "x", TextJustification.MIDDLE_LEFTt);
		y += th;
		new ControlText(contents, new Area(0, y, w2, th), syst.getChromosomeShortName(EnumTreeChromosome.FERTILITY) + " : ", TextJustification.MiddleRight);
		new ControlText(contents, new Area(w2, y, w3, th), genome.getFertility() + "x", TextJustification.MIDDLE_LEFTt);
		y += th;
		List<ItemStack> fruits = new ArrayList<>();
		fruits.addAll(products.keySet());
		if (!fruits.isEmpty()) {
			new ControlText(contents, new Area(0, y, w2, th2), syst.getChromosomeShortName(EnumTreeChromosome.FRUITS) + " : ", TextJustification.MiddleRight);
			for (ItemStack fruitw : fruits) {
				final ControlItemDisplay display2 = new ControlItemDisplay(contents, w2, y);
				display2.setItemStack(fruitw);
				display2.setTooltip();
				y += th2;
			}
		}
		new ControlText(contents, new Area(0, y, w2, th), syst.getChromosomeShortName(EnumTreeChromosome.YIELD) + " : ", TextJustification.MiddleRight);
		new ControlText(contents, new Area(w2, y, w3, th), genome.getYield() + "x", TextJustification.MIDDLE_LEFTt);
		y += th;
		new ControlText(contents, new Area(0, y, w2, th), syst.getChromosomeShortName(EnumTreeChromosome.SAPPINESS) + " : ", TextJustification.MiddleRight);
		new ControlText(contents, new Area(w2, y, w3, th), genome.getSappiness() + "x", TextJustification.MIDDLE_LEFTt);
		y += th;
		new ControlText(contents, new Area(0, y, w2, th), syst.getChromosomeShortName(EnumTreeChromosome.MATURATION) + " : ", TextJustification.MiddleRight);
		new ControlText(contents, new Area(w2, y, w3, th), genome.getMaturationTime() + "x", TextJustification.MIDDLE_LEFTt);
		y += th;
		new ControlText(contents, new Area(0, y, w2, th), syst.getChromosomeShortName(EnumTreeChromosome.GIRTH) + " : ", TextJustification.MiddleRight);
		new ControlText(contents, new Area(w2, y, w3, th), genome.getGirth() + "x" + genome.getGirth(), TextJustification.MIDDLE_LEFTt);
		y += th;
		contents.setSize(new Point(contents.size().x(), y));
		scrollable.setScrollableContent(contents);
	}

	public static String tolerated(final boolean t) {
		if (t) {
			return BinnieCore.getBinnieProxy().localise("gui.tolerated");
		}
		return BinnieCore.getBinnieProxy().localise("gui.nottolerated");
	}
}
