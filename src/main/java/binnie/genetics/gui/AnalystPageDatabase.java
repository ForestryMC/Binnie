package binnie.genetics.gui;

import binnie.core.genetics.BreedingSystem;
import binnie.craftgui.controls.ControlTextCentered;
import binnie.craftgui.controls.ControlTextEdit;
import binnie.craftgui.controls.core.Control;
import binnie.craftgui.controls.listbox.ControlListBox;
import binnie.craftgui.controls.scroll.ControlScrollBar;
import binnie.craftgui.controls.scroll.ControlScrollableContent;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.core.geometry.TextJustification;
import binnie.craftgui.events.EventMouse;
import binnie.craftgui.minecraft.MinecraftGUI;
import binnie.craftgui.resource.minecraft.CraftGUITexture;
import binnie.craftgui.window.Panel;
import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IIndividual;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class AnalystPageDatabase extends ControlAnalystPage {
	ControlScrollableContent scroll;
	boolean isMaster;

	public AnalystPageDatabase(final IWidget parent, final IArea area, final BreedingSystem system, final boolean isMaster) {
		super(parent, area);
		this.scroll = null;
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
		new ControlTextCentered(this, y, "§nRegistry").setColour(this.getColour());
		y += 16;
		new ControlTextEdit(this, 20.0f, y, this.w() - 40.0f, 16.0f) {
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
			public void onRenderBackground() {
				CraftGUI.Render.colour(5592405);
				CraftGUI.Render.texture(CraftGUITexture.TabSolid, this.getArea().inset(1));
				CraftGUI.Render.colour(AnalystPageDatabase.this.getColour());
				CraftGUI.Render.texture(CraftGUITexture.TabOutline, this.getArea());
				super.renderTextField();
			}
		};
		y += 22;
		new Panel(this, 3.0f, y - 1, this.w() - 6.0f, this.h() - y - 8.0f + 2.0f, MinecraftGUI.PanelType.TabOutline).setColour(this.getColour());
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
			this.scroll = new ControlListBox<IAlleleSpecies>(this, 4.0f, y, this.w() - 8.0f, this.h() - y - 8.0f - 20.0f, 0.0f) {
				@Override
				public void initialise() {
					super.initialise();
					this.setOptions(options);
				}

				@Override
				public IWidget createOption(final IAlleleSpecies v, final int y) {
					return new Control(this.getContent(), 0.0f, y, this.w(), 12.0f) {
						IAlleleSpecies value = v;

						@Override
						public void onRenderBackground() {
							CraftGUI.Render.text(this.getArea(), TextJustification.MiddleCenter, this.value.getName(), 16777215);
						}
					};
				}
			};
		} else {
			(this.scroll = new ControlScrollableContent(this, 4.0f, y, this.w() - 8.0f, this.h() - y - 8.0f, 0.0f)).setScrollableContent(this.getItemScrollList(system, options));
		}
		new ControlScrollBar(this, this.scroll.x() + this.scroll.w() - 6.0f, this.scroll.y() + 3.0f, 3.0f, this.scroll.h() - 6.0f, this.scroll) {
			@Override
			public void onRenderBackground() {
				if (!this.isEnabled()) {
					return;
				}
				CraftGUI.Render.gradientRect(this.getArea(), 1140850688 + AnalystPageDatabase.this.getColour(), 1140850688 + AnalystPageDatabase.this.getColour());
				CraftGUI.Render.solid(this.getRenderArea(), AnalystPageDatabase.this.getColour());
			}
		};
	}

	private IWidget getItemScrollList(final BreedingSystem system, final Collection<IAlleleSpecies> options) {
		return new Control(this.scroll, 0.0f, 0.0f, this.scroll.w(), this.scroll.h()) {
			@Override
			public void initialise() {
				final int maxBiomePerLine = (int) ((this.w() - 4.0f + 2.0f) / 18.0f);
				final float biomeListX = -6.0f + (this.w() - (maxBiomePerLine * 18 - 2)) / 2.0f;
				int dx = 0;
				int dy = 0;
				for (final IAlleleSpecies species : options) {
					final IIndividual ind = system.getSpeciesRoot().templateAsIndividual(system.getSpeciesRoot().getTemplate(species.getUID()));
					new ControlIndividualDisplay(this, biomeListX + dx, 2 + dy, ind) {
						@Override
						public void initialise() {
							this.addSelfEventHandler(new EventMouse.Down.Handler() {
								@Override
								public void onEvent(final EventMouse.Down event) {
									final WindowAnalyst window = (WindowAnalyst) AnalystPageDatabase.this.getSuperParent();
									window.setIndividual(ind);
								}
							});
						}

						@Override
						public void onRenderBackground() {
							final WindowAnalyst window = (WindowAnalyst) AnalystPageDatabase.this.getSuperParent();
							if (window.getIndividual() != null && window.getIndividual().getGenome().getPrimary() == species) {
								CraftGUI.Render.colour(15658734);
								CraftGUI.Render.texture(CraftGUITexture.TabSolid, this.getArea().outset(1));
								CraftGUI.Render.colour(AnalystPageDatabase.this.getColour());
								CraftGUI.Render.texture(CraftGUITexture.TabOutline, this.getArea().outset(1));
							} else if (this.calculateIsMouseOver()) {
								CraftGUI.Render.colour(15658734);
								CraftGUI.Render.texture(CraftGUITexture.TabSolid, this.getArea().outset(1));
							}
							super.onRenderBackground();
						}
					};
					dx += 18;
					if (dx >= 18 * maxBiomePerLine) {
						dx = 0;
						dy += 18;
					}
				}
				this.setSize(new IPoint(this.w(), 4 + dy + 18));
			}
		};
	}

	@Override
	public String getTitle() {
		return "Registry";
	}

	private Collection<IAlleleSpecies> getSpecies(final BreedingSystem system) {
		final Collection<IAlleleSpecies> species = new ArrayList<>();
		species.addAll(this.isMaster ? system.getAllSpecies() : system.getDiscoveredSpecies(this.getWindow().getWorld(), this.getWindow().getPlayer().getGameProfile()));
		return species;
	}
}
