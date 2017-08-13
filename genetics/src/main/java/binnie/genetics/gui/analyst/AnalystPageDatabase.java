package binnie.genetics.gui.analyst;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import binnie.core.api.genetics.IBreedingSystem;
import binnie.core.api.gui.IArea;
import net.minecraft.util.text.TextFormatting;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IIndividual;

import binnie.core.gui.CraftGUI;
import binnie.core.api.gui.IWidget;
import binnie.core.gui.controls.ControlTextCentered;
import binnie.core.gui.controls.ControlTextEdit;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.controls.listbox.ControlListBox;
import binnie.core.gui.controls.scroll.ControlScrollBar;
import binnie.core.gui.controls.scroll.ControlScrollableContent;
import binnie.core.gui.events.EventMouse;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.geometry.TextJustification;
import binnie.core.gui.minecraft.MinecraftGUI;
import binnie.core.gui.renderer.RenderUtil;
import binnie.core.gui.resource.textures.CraftGUITexture;
import binnie.core.gui.window.Panel;
import binnie.core.util.I18N;

public class AnalystPageDatabase extends ControlAnalystPage {
	private final ControlScrollableContent scroll;
	boolean isMaster;

	public AnalystPageDatabase(IWidget parent, IArea area, IBreedingSystem system, boolean isMaster) {
		super(parent, area);
		this.isMaster = isMaster;
		setColor(getColor(system));
		int y = 4;
		new ControlTextCentered(this, y, TextFormatting.UNDERLINE + getTitle()).setColor(getColor());
		y += 16;
		new ControlTextEdit(this, 20, y, getWidth() - 40, 16) {
			@Override
			public void onTextEdit(String value) {
				Collection<IAlleleSpecies> options = new ArrayList<>();
				getSpecies(system);
				for (IAlleleSpecies species : getSpecies(system)) {
					if (value != null) {
						if (!Objects.equals(value, "")) {
							if (!species.getAlleleName().toLowerCase().contains(value.toLowerCase())) {
								continue;
							}
						}
					}
					options.add(species);
				}
				scroll.deleteAllChildren();
				scroll.setScrollableContent(getItemScrollList(system, options));
			}

			@Override
			@SideOnly(Side.CLIENT)
			public void onRenderBackground(int guiWidth, int guiHeight) {
				RenderUtil.setColour(5592405);
				CraftGUI.RENDER.texture(CraftGUITexture.TAB_SOLID, getArea().inset(1));
				RenderUtil.setColour(AnalystPageDatabase.this.getColor());
				CraftGUI.RENDER.texture(CraftGUITexture.TAB_OUTLINE, getArea());
				renderTextField();
			}
		};
		y += 22;
		new Panel(this, 3, y - 1, getWidth() - 6, getHeight() - y - 8 + 2, MinecraftGUI.PanelType.TAB_OUTLINE).setColor(getColor());
		boolean textView = false;
		Collection<IAlleleSpecies> options = getSpecies(system);
		for (IAlleleSpecies species : options) {
			String height = system.getAlleleName(EnumTreeChromosome.HEIGHT, system.getIndividual(species.getUID()).getGenome().getActiveAllele(EnumTreeChromosome.HEIGHT));
			String fertility = system.getAlleleName(EnumTreeChromosome.FERTILITY, system.getIndividual(species.getUID()).getGenome().getActiveAllele(EnumTreeChromosome.FERTILITY));
			String yield = system.getAlleleName(EnumTreeChromosome.YIELD, system.getIndividual(species.getUID()).getGenome().getActiveAllele(EnumTreeChromosome.YIELD));
			String sappiness = system.getAlleleName(EnumTreeChromosome.SAPPINESS, system.getIndividual(species.getUID()).getGenome().getActiveAllele(EnumTreeChromosome.SAPPINESS));
			String maturation = system.getAlleleName(EnumTreeChromosome.MATURATION, system.getIndividual(species.getUID()).getGenome().getActiveAllele(EnumTreeChromosome.MATURATION));
		}
		if (textView) {
			scroll = new ControlListBox<IAlleleSpecies>(this, 4, y, getWidth() - 8, getHeight() - y - 8 - 20, 0) {
				@Override
				public void initialise() {
					super.initialise();
					setOptions(options);
				}

				@Override
				public IWidget createOption(IAlleleSpecies v, int y) {
					return new Control(getContent(), 0, y, getWidth(), 12) {
						IAlleleSpecies value = v;

						@Override
						@SideOnly(Side.CLIENT)
						public void onRenderBackground(int guiWidth, int guiHeight) {
							RenderUtil.drawText(getArea(), TextJustification.MIDDLE_CENTER, value.getAlleleName(), 16777215);
						}
					};
				}
			};
		} else {
			scroll = new ControlScrollableContent(this, 4, y, getWidth() - 8, getHeight() - y - 8, 0);
			scroll.setScrollableContent(getItemScrollList(system, options));
		}
		new ControlScrollBar(this, scroll.getXPos() + scroll.getWidth() - 6, scroll.getYPos() + 3, 3, scroll.getHeight() - 6, scroll) {
			@Override
			@SideOnly(Side.CLIENT)
			public void onRenderBackground(int guiWidth, int guiHeight) {
				if (!isEnabled()) {
					return;
				}
				RenderUtil.drawGradientRect(getArea(), 1140850688 + AnalystPageDatabase.this.getColor(), 1140850688 + AnalystPageDatabase.this.getColor());
				RenderUtil.drawSolidRect(getRenderArea(), AnalystPageDatabase.this.getColor());
			}
		};
	}

	private static int getColor(IBreedingSystem system){
		int cOfSystem = system.getColour();
		int cr = (0xFF0000 & cOfSystem) >> 16;
		int cg = (0xFF00 & cOfSystem) >> 8;
		int cb = 0xFF & cOfSystem;
		float brightness = 0.1f * cb / 255.0f + 0.35f * cr / 255.0f + 0.55f * cg / 255.0f;
		brightness = 0.3f / brightness;
		if (brightness > 1.0f) {
			brightness = 1.0f;
		}
		return (int) (cr * brightness) * 65536 + (int) (cg * brightness) * 256 + (int) (cb * brightness);
	}

	private IWidget getItemScrollList(IBreedingSystem system, Collection<IAlleleSpecies> options) {
		return new Control(scroll, 0, 0, scroll.getWidth(), scroll.getHeight()) {
			@Override
			public void initialise() {
				int maxBiomePerLine = (getWidth() - 4 + 2) / 18;
				int biomeListX = -6 + (getWidth() - (maxBiomePerLine * 18 - 2)) / 2;
				int dx = 0;
				int dy = 0;
				for (IAlleleSpecies species : options) {
					IIndividual ind = system.getSpeciesRoot().templateAsIndividual(system.getSpeciesRoot().getTemplate(species));
					new ControlIndividualDisplay(this, biomeListX + dx, 2 + dy, ind) {
						@Override
						public void initialise() {
							addSelfEventHandler(EventMouse.Down.class, event -> {
								WindowAnalyst window = (WindowAnalyst) AnalystPageDatabase.this.getTopParent();
								window.setIndividual(ind);
							});
						}

						@Override
						@SideOnly(Side.CLIENT)
						public void onRenderBackground(int guiWidth, int guiHeight) {
							WindowAnalyst window = (WindowAnalyst) AnalystPageDatabase.this.getTopParent();
							if (window.getIndividual() != null && window.getIndividual().getGenome().getPrimary() == species) {
								RenderUtil.setColour(15658734);
								CraftGUI.RENDER.texture(CraftGUITexture.TAB_SOLID, getArea().outset(1));
								RenderUtil.setColour(AnalystPageDatabase.this.getColor());
								CraftGUI.RENDER.texture(CraftGUITexture.TAB_OUTLINE, getArea().outset(1));
							} else if (calculateIsMouseOver()) {
								RenderUtil.setColour(15658734);
								CraftGUI.RENDER.texture(CraftGUITexture.TAB_SOLID, getArea().outset(1));
							}
							super.onRenderBackground(guiWidth, guiHeight);
						}
					};
					dx += 18;
					if (dx >= 18 * maxBiomePerLine) {
						dx = 0;
						dy += 18;
					}
				}
				setSize(new Point(getWidth(), 4 + dy + 18));
			}
		};
	}

	@Override
	public String getTitle() {
		return I18N.localise("genetics.gui.analyst.registry.title");
	}

	private Collection<IAlleleSpecies> getSpecies(IBreedingSystem system) {
		Collection<IAlleleSpecies> species = new ArrayList<>();
		species.addAll(isMaster ? system.getAllSpecies() : system.getDiscoveredSpecies(getWindow().getWorld(), getWindow().getPlayer().getGameProfile()));
		return species;
	}
}
