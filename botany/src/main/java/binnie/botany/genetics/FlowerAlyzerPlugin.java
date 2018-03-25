package binnie.botany.genetics;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import binnie.botany.Botany;
import org.apache.commons.lang3.StringUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.genetics.IAlleleInteger;
import forestry.api.genetics.IAlyzerPlugin;

import binnie.botany.api.BotanyAPI;
import binnie.botany.api.genetics.EnumFlowerChromosome;
import binnie.botany.api.genetics.EnumFlowerStage;
import binnie.botany.api.genetics.IFlower;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.renderer.RenderUtil;
import binnie.core.util.I18N;

// TODO write flower alyzer plugin
public class FlowerAlyzerPlugin implements IAlyzerPlugin {
	public static final FlowerAlyzerPlugin INSTANCE = new FlowerAlyzerPlugin();
	public static final int COLUMN_0 = 12;
	public static final int COLUMN_1 = 90;
	public static final int COLUMN_2 = 155;
	private static final EnumFlowerChromosome[] firstPageChromosome = new EnumFlowerChromosome[]{
			EnumFlowerChromosome.SPECIES,
			EnumFlowerChromosome.SAPPINESS,
			EnumFlowerChromosome.TERRITORY,
			EnumFlowerChromosome.FERTILITY,
			EnumFlowerChromosome.EFFECT,
			EnumFlowerChromosome.LIFESPAN
	};
	private static final EnumFlowerChromosome[] secondPageColorChromosome = new EnumFlowerChromosome[]{
			EnumFlowerChromosome.PRIMARY,
			EnumFlowerChromosome.SECONDARY,
			EnumFlowerChromosome.STEM,
	};
	private static final EnumFlowerChromosome[] secondPageToleranceChromosome = new EnumFlowerChromosome[]{
			EnumFlowerChromosome.TEMPERATURE_TOLERANCE,
			EnumFlowerChromosome.HUMIDITY_TOLERANCE,
			EnumFlowerChromosome.PH_TOLERANCE
	};
	private int lastTopOffset;
	private final int rowSize = 12;
	private final int margin = 10;
	private int guiLeft;
	private int guiTop;
	private final HashMap<String, ItemStack> iconStacks = new HashMap<>();


	public FlowerAlyzerPlugin() {
		for (FlowerDefinition def : FlowerDefinition.values()) {
			iconStacks.put(def.getSpecies().getUID(), def.getMemberStack(EnumFlowerStage.FLOWER));
		}

	}

	@SideOnly(Side.CLIENT)
	public void drawLine(GuiScreen gui, int xOffset, String msg, int color) {
		gui.mc.fontRenderer.drawString(msg, guiLeft + xOffset, guiTop + lastTopOffset + margin, color);
	}

	@SideOnly(Side.CLIENT)
	public void drawChromosome(GuiScreen gui, EnumFlowerChromosome chromosome, IFlower flower) {
		drawLine(gui, COLUMN_0, I18N.localise(String.format("%s.flowers.chromosome.%s.short", Botany.instance.getModId(), chromosome.getName())), 0xEEEEEE);
		drawLine(gui, COLUMN_1, I18N.localise(flower.getGenome().getActiveAllele(chromosome).getUnlocalizedName()), 0xEEEEEE);
		drawLine(gui, COLUMN_2, I18N.localise(flower.getGenome().getInactiveAllele(chromosome).getUnlocalizedName()), 0xEEEEEE);
		lastTopOffset += rowSize;
	}

	public void newLine() {
		lastTopOffset += rowSize;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void drawAnalyticsPage1(GuiScreen gui, ItemStack itemStack) {
		lastTopOffset = 10;
		guiLeft = (gui.width - 246) / 2;
		guiTop = (gui.height - 238) / 2;
		IFlower flower = BotanyAPI.flowerRoot.getMember(itemStack);
		if (flower == null) {
			return;
		}

		ItemStack renderP = getIconStacks().get(flower.getGenome().getPrimary().getUID());
		ItemStack renderS = getIconStacks().get(flower.getGenome().getSecondary().getUID());


		gui.mc.fontRenderer.drawString("Active", guiLeft + 10 + COLUMN_1, guiTop + 10, 0xffffff);
		gui.mc.fontRenderer.drawString("Inactive", guiLeft + 10 + COLUMN_2, guiTop + 10, 0xffffff);

		if (renderP != null && renderS != null) {
			RenderItem itemRender = Minecraft.getMinecraft().getRenderItem();
			itemRender.renderItemAndEffectIntoGUI(renderP, guiLeft + 10 + COLUMN_1, guiTop + 10 + 12);
			itemRender.renderItemOverlayIntoGUI(gui.mc.fontRenderer, renderP, guiLeft + 10 + COLUMN_1, guiTop + 10 + 12, null);

			itemRender.renderItemAndEffectIntoGUI(renderS, guiLeft + 10 + COLUMN_2, guiTop + 10 + 12);
			itemRender.renderItemOverlayIntoGUI(gui.mc.fontRenderer, renderS, guiLeft + 10 + COLUMN_1, guiTop + 10 + 12, null);
		}

		guiTop += rowSize * 3;
		for (EnumFlowerChromosome chromosom : firstPageChromosome) {
			drawChromosome(gui, chromosom, flower);

		}

	}

	@SideOnly(Side.CLIENT)
	@Override
	public void drawAnalyticsPage2(GuiScreen gui, ItemStack itemStack) {
		lastTopOffset = 0;
		guiLeft = (gui.width - 246) / 2;
		guiTop = (gui.height - 238) / 2;

		IFlower flower = BotanyAPI.flowerRoot.getMember(itemStack);
		if (flower == null) {
			return;
		}

		for (EnumFlowerChromosome chromosome : secondPageColorChromosome) {
			drawChromosome(gui, chromosome, flower);
			RenderUtil.drawSolidRect(new Area(guiLeft + COLUMN_1, guiTop + lastTopOffset + margin, 50, 10), ((IAlleleInteger) flower.getGenome().getActiveAllele(chromosome)).getValue());
			RenderUtil.drawSolidRect(new Area(guiLeft + COLUMN_2, guiTop + lastTopOffset + margin, 50, 10), ((IAlleleInteger) flower.getGenome().getInactiveAllele(chromosome)).getValue());
			newLine();
		}

		for (EnumFlowerChromosome chromosome : secondPageToleranceChromosome) {
			drawChromosome(gui, chromosome, flower);
			newLine();
		}

	}

	@SideOnly(Side.CLIENT)
	@Override
	public void drawAnalyticsPage3(GuiScreen gui, ItemStack itemStack) {
		//TODO soil
	}

	@Override
	public Map<String, ItemStack> getIconStacks() {
		return iconStacks;
	}

	@Override
	public List<String> getHints() {
		return Collections.EMPTY_LIST;
	}
}
