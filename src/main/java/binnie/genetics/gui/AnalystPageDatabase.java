package binnie.genetics.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import net.minecraft.util.text.TextFormatting;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IIndividual;

import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.controls.ControlTextEdit;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.controls.listbox.ControlListBox;
import binnie.core.craftgui.controls.scroll.ControlScrollBar;
import binnie.core.craftgui.controls.scroll.ControlScrollableContent;
import binnie.core.craftgui.events.EventMouse;
import binnie.core.craftgui.geometry.Area;
import binnie.core.craftgui.geometry.Point;
import binnie.core.craftgui.geometry.TextJustification;
import binnie.core.craftgui.minecraft.MinecraftGUI;
import binnie.core.craftgui.renderer.RenderUtil;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;
import binnie.core.craftgui.window.Panel;
import binnie.core.genetics.BreedingSystem;
import binnie.core.util.I18N;

public class AnalystPageDatabase extends ControlAnalystPage {
	private final ControlScrollableContent scroll;
	boolean isMaster;

	public AnalystPageDatabase(final IWidget parent, final Area area, final BreedingSystem system, final boolean isMaster) {
		super(parent, area);
		this.isMaster = isMaster;
		final int cOfSystem = system.getColour();
		final int cr = (0xFF0000 & cOfSystem) >> 16;
		final int cg = (0xFF00 & cOfSystem) >> 8;
		final int cb = 0xFF & cOfSystem;
		float brightness = 0.1f * cb / 255.0f + 0.35f * cr / 255.0f + 0.55f * cg / 255.0f;
		brightness = 0.3f / brightness;
		if (brightness > 1.0f) {
			brightness = 1.0f;
		}
		final int newColour = (int) (cr * brightness) * 65536 + (int) (cg * brightness) * 256 + (int) (cb * brightness);
		this.setColour(newColour);
		int y = 4;
		new ControlTextCentered(this, y, TextFormatting.UNDERLINE + getTitle()).setColour(this.getColour());
		y += 16;
		new ControlTextEdit(this, 20, y, this.width() - 40, 16) {
			@Override
			public void onTextEdit(final String value) {
				final Collection<IAlleleSpecies> options = new ArrayList<>();
				AnalystPageDatabase.this.getSpecies(system);
				for (final IAlleleSpecies species : AnalystPageDatabase.this.getSpecies(system)) {
					if (value != null) {
						if (!Objects.equals(value, "")) {
							if (!species.getName().toLowerCase().contains(value.toLowerCase())) {
								continue;
							}
						}
					}
					options.add(species);
				}
				AnalystPageDatabase.this.scroll.deleteAllChildren();
				AnalystPageDatabase.this.scroll.setScrollableContent(AnalystPageDatabase.this.getItemScrollList(system, options));
			}

			@Override
			@SideOnly(Side.CLIENT)
			public void onRenderBackground(int guiWidth, int guiHeight) {
				RenderUtil.setColour(5592405);
				CraftGUI.render.texture(CraftGUITexture.TabSolid, this.getArea().inset(1));
				RenderUtil.setColour(AnalystPageDatabase.this.getColour());
				CraftGUI.render.texture(CraftGUITexture.TabOutline, this.getArea());
				super.renderTextField();
			}
		};
		y += 22;
		new Panel(this, 3, y - 1, this.width() - 6, this.height() - y - 8 + 2, MinecraftGUI.PanelType.TabOutline).setColour(this.getColour());
		final boolean textView = false;
		final Collection<IAlleleSpecies> options = this.getSpecies(system);
		for (final IAlleleSpecies species : options) {
			final String height = system.getAlleleName(EnumTreeChromosome.HEIGHT, system.getIndividual(species.getUID()).getGenome().getActiveAllele(EnumTreeChromosome.HEIGHT));
			final String fertility = system.getAlleleName(EnumTreeChromosome.FERTILITY, system.getIndividual(species.getUID()).getGenome().getActiveAllele(EnumTreeChromosome.FERTILITY));
			final String yield = system.getAlleleName(EnumTreeChromosome.YIELD, system.getIndividual(species.getUID()).getGenome().getActiveAllele(EnumTreeChromosome.YIELD));
			final String sappiness = system.getAlleleName(EnumTreeChromosome.SAPPINESS, system.getIndividual(species.getUID()).getGenome().getActiveAllele(EnumTreeChromosome.SAPPINESS));
			final String maturation = system.getAlleleName(EnumTreeChromosome.MATURATION, system.getIndividual(species.getUID()).getGenome().getActiveAllele(EnumTreeChromosome.MATURATION));
		}
		if (textView) {
			this.scroll = new ControlListBox<IAlleleSpecies>(this, 4, y, this.width() - 8, this.height() - y - 8 - 20, 0) {
				@Override
				public void initialise() {
					super.initialise();
					this.setOptions(options);
				}

				@Override
				public IWidget createOption(final IAlleleSpecies v, final int y) {
					return new Control(this.getContent(), 0, y, this.width(), 12) {
						IAlleleSpecies value = v;

						@Override
						@SideOnly(Side.CLIENT)
						public void onRenderBackground(int guiWidth, int guiHeight) {
							RenderUtil.drawText(this.getArea(), TextJustification.MIDDLE_CENTER, this.value.getName(), 16777215);
						}
					};
				}
			};
		} else {
			this.scroll = new ControlScrollableContent(this, 4, y, this.width() - 8, this.height() - y - 8, 0);
			this.scroll.setScrollableContent(this.getItemScrollList(system, options));
		}
		new ControlScrollBar(this, this.scroll.xPos() + this.scroll.width() - 6, this.scroll.yPos() + 3, 3, this.scroll.height() - 6, this.scroll) {
			@Override
			@SideOnly(Side.CLIENT)
			public void onRenderBackground(int guiWidth, int guiHeight) {
				if (!this.isEnabled()) {
					return;
				}
				RenderUtil.drawGradientRect(this.getArea(), 1140850688 + AnalystPageDatabase.this.getColour(), 1140850688 + AnalystPageDatabase.this.getColour());
				RenderUtil.drawSolidRect(this.getRenderArea(), AnalystPageDatabase.this.getColour());
			}
		};
	}

	private IWidget getItemScrollList(final BreedingSystem system, final Collection<IAlleleSpecies> options) {
		return new Control(this.scroll, 0, 0, this.scroll.width(), this.scroll.height()) {
			@Override
			public void initialise() {
				final int maxBiomePerLine = (this.width() - 4 + 2) / 18;
				final int biomeListX = -6 + (this.width() - (maxBiomePerLine * 18 - 2)) / 2;
				int dx = 0;
				int dy = 0;
				for (final IAlleleSpecies species : options) {
					final IIndividual ind = system.getSpeciesRoot().templateAsIndividual(system.getSpeciesRoot().getTemplate(species));
					new ControlIndividualDisplay(this, biomeListX + dx, 2 + dy, ind) {
						@Override
						public void initialise() {
							this.addSelfEventHandler(new EventMouse.Down.Handler() {
								@Override
								@SideOnly(Side.CLIENT)
								public void onEvent(final EventMouse.Down event) {
									final WindowAnalyst window = (WindowAnalyst) AnalystPageDatabase.this.getTopParent();
									window.setIndividual(ind);
								}
							});
						}

						@Override
						@SideOnly(Side.CLIENT)
						public void onRenderBackground(int guiWidth, int guiHeight) {
							final WindowAnalyst window = (WindowAnalyst) AnalystPageDatabase.this.getTopParent();
							if (window.getIndividual() != null && window.getIndividual().getGenome().getPrimary() == species) {
								RenderUtil.setColour(15658734);
								CraftGUI.render.texture(CraftGUITexture.TabSolid, this.getArea().outset(1));
								RenderUtil.setColour(AnalystPageDatabase.this.getColour());
								CraftGUI.render.texture(CraftGUITexture.TabOutline, this.getArea().outset(1));
							} else if (this.calculateIsMouseOver()) {
								RenderUtil.setColour(15658734);
								CraftGUI.render.texture(CraftGUITexture.TabSolid, this.getArea().outset(1));
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
				this.setSize(new Point(this.width(), 4 + dy + 18));
			}
		};
	}

	@Override
	public String getTitle() {
		return I18N.localise("genetics.gui.analyst.registry.title");
	}

	private Collection<IAlleleSpecies> getSpecies(final BreedingSystem system) {
		final Collection<IAlleleSpecies> species = new ArrayList<>();
		species.addAll(this.isMaster ? system.getAllSpecies() : system.getDiscoveredSpecies(this.getWindow().getWorld(), this.getWindow().getPlayer().getGameProfile()));
		return species;
	}
}
