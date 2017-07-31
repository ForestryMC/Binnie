package binnie.genetics.gui.database.bee;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.genetics.EnumTolerance;
import forestry.api.genetics.IAllele;

import binnie.Binnie;
import binnie.core.gui.Attribute;
import binnie.core.gui.CraftGUI;
import binnie.core.gui.ITooltip;
import binnie.core.gui.IWidget;
import binnie.core.gui.Tooltip;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.renderer.RenderUtil;
import binnie.core.gui.resource.minecraft.CraftGUITexture;

public class ControlClimateBar extends Control implements ITooltip {
	private final int[] TEMP_COLORS = new int[]{
		0x00fffb,
		0x78bbff,
		0x4fff30,
		0xffff00,
		0xffa200,
		0xff0000
	};
	private final int[] HUMID_COLORS = new int[]{
		0xffe7a3,
		0x1aff00,
		0x307cff
	};

	boolean isHumidity;
	List<Integer> tolerated;

	public ControlClimateBar(IWidget parent, int x, int y, int width, int height) {
		this(parent, x, y, width, height, false);
	}

	public ControlClimateBar(IWidget parent, int x, int y, int width, int height, boolean humidity) {
		super(parent, x, y, width, height);
		isHumidity = false;
		tolerated = new ArrayList<>();
		addAttribute(Attribute.MouseOver);
		isHumidity = humidity;
	}

	@Override
	public void getTooltip(Tooltip list) {
		if (tolerated.isEmpty()) {
			return;
		}

		int types = isHumidity ? 3 : 6;
		int type = (int) ((int) (getRelativeMousePosition().x() - 1.0f) / ((getSize().x() - 2.0f) / types));
		if (!tolerated.contains(type) || type >= types) {
			return;
		}

		if (isHumidity) {
			list.add(EnumHumidity.values()[type].name);
		} else {
			list.add(EnumTemperature.values()[type + 1].name);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		CraftGUI.RENDER.texture(CraftGUITexture.EnergyBarBack, getArea());
		int types = isHumidity ? 3 : 6;
		int w = (int) ((getSize().x() - 2.0f) / types);
		for (int i = 0; i < types; ++i) {
			int x = i * w;
			if (tolerated.contains(i)) {
				int color;
				if (isHumidity) {
					color = HUMID_COLORS[i];
				} else {
					color = TEMP_COLORS[i];
				}
				RenderUtil.drawSolidRect(new Area(x + 1, 1, w, getSize().y() - 2), color);
			}
		}
		CraftGUI.RENDER.texture(CraftGUITexture.EnergyBarGlass, getArea());
	}

	public void setSpecies(IAlleleBeeSpecies species) {
		tolerated.clear();
		int main;
		EnumTolerance tolerance;
		IAllele[] template = Binnie.GENETICS.getBeeRoot().getTemplate(species);
		IBeeGenome genome = Binnie.GENETICS.getBeeRoot().templateAsGenome(template);

		if (!isHumidity) {
			main = species.getTemperature().ordinal() - 1;
			tolerance = genome.getToleranceTemp();
		} else {
			main = species.getHumidity().ordinal();
			tolerance = genome.getToleranceHumid();
		}

		tolerated.add(main);
		switch (tolerance) {
			case BOTH_5:
			case UP_5: {
				tolerated.add(main + 5);
			}
			case BOTH_4:
			case UP_4: {
				tolerated.add(main + 4);
			}
			case BOTH_3:
			case UP_3: {
				tolerated.add(main + 3);
			}
			case BOTH_2:
			case UP_2: {
				tolerated.add(main + 2);
			}
			case BOTH_1:
			case UP_1: {
				tolerated.add(main + 1);
				break;
			}
		}

		switch (tolerance) {
			case BOTH_5:
			case DOWN_5: {
				tolerated.add(main - 5);
			}
			case BOTH_4:
			case DOWN_4: {
				tolerated.add(main - 4);
			}
			case BOTH_3:
			case DOWN_3: {
				tolerated.add(main - 3);
			}
			case BOTH_2:
			case DOWN_2: {
				tolerated.add(main - 2);
			}
			case BOTH_1:
			case DOWN_1: {
				tolerated.add(main - 1);
				break;
			}
		}
	}
}
