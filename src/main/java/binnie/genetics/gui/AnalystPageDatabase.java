package binnie.genetics.gui;

import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.controls.ControlTextEdit;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.controls.listbox.ControlListBox;
import binnie.core.craftgui.controls.scroll.ControlScrollBar;
import binnie.core.craftgui.controls.scroll.ControlScrollableContent;
import binnie.core.craftgui.events.EventMouse;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.geometry.TextJustification;
import binnie.core.craftgui.minecraft.MinecraftGUI;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;
import binnie.core.craftgui.window.Panel;
import binnie.core.genetics.BreedingSystem;
import binnie.core.util.I18N;
import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IIndividual;
import java.util.ArrayList;
import java.util.Collection;
import net.minecraft.util.EnumChatFormatting;

public class AnalystPageDatabase extends ControlAnalystPage {
    ControlScrollableContent scroll;
    boolean isMaster;

    public AnalystPageDatabase(IWidget parent, IArea area, BreedingSystem system, boolean isMaster) {
        super(parent, area);
        scroll = null;
        this.isMaster = isMaster;
        int cOfSystem = system.getColor();
        int cr = (0xFF0000 & cOfSystem) >> 16;
        int cg = (0xFF00 & cOfSystem) >> 8;
        int cb = 0xFF & cOfSystem;
        float brightness = 0.1f * cb / 255.0f + 0.35f * cr / 255.0f + 0.55f * cg / 255.0f;
        brightness = 0.3f / brightness;
        if (brightness > 1.0f) {
            brightness = 1.0f;
        }
        int newColour = (int) (cr * brightness) * 65536 + (int) (cg * brightness) * 256 + (int) (cb * brightness);
        setColor(newColour);
        int y = 4;
        new ControlTextCentered(this, y, EnumChatFormatting.UNDERLINE + getTitle()).setColor(getColor());
        y += 16;
        new ControlTextEdit(this, 20.0f, y, w() - 40.0f, 16.0f) {
            @Override
            public void onTextEdit(String value) {
                Collection<IAlleleSpecies> options = new ArrayList<>();
                getSpecies(system);
                for (IAlleleSpecies species : getSpecies(system)) {
                    if (value != null
                            && !value.isEmpty()
                            && !species.getName().toLowerCase().contains(value.toLowerCase())) {
                        continue;
                    }
                    options.add(species);
                }
                scroll.deleteAllChildren();
                scroll.setScrollableContent(getItemScrollList(system, options));
            }

            @Override
            public void onRenderBackground() {
                CraftGUI.render.color(0x555555);
                CraftGUI.render.texture(CraftGUITexture.TabSolid, getArea().inset(1));
                CraftGUI.render.color(AnalystPageDatabase.this.getColor());
                CraftGUI.render.texture(CraftGUITexture.TabOutline, getArea());
                super.renderTextField();
            }
        };
        y += 22;
        new Panel(this, 3.0f, y - 1, w() - 6.0f, h() - y - 8.0f + 2.0f, MinecraftGUI.PanelType.TabOutline)
                .setColor(getColor());
        boolean textView = false;
        Collection<IAlleleSpecies> options = getSpecies(system);
        for (IAlleleSpecies species : options) {
            String height = system.getAlleleName(
                    EnumTreeChromosome.HEIGHT,
                    system.getIndividual(species.getUID()).getGenome().getActiveAllele(EnumTreeChromosome.HEIGHT));
            String fertility = system.getAlleleName(
                    EnumTreeChromosome.FERTILITY,
                    system.getIndividual(species.getUID()).getGenome().getActiveAllele(EnumTreeChromosome.FERTILITY));
            String yield = system.getAlleleName(
                    EnumTreeChromosome.YIELD,
                    system.getIndividual(species.getUID()).getGenome().getActiveAllele(EnumTreeChromosome.YIELD));
            String sappiness = system.getAlleleName(
                    EnumTreeChromosome.SAPPINESS,
                    system.getIndividual(species.getUID()).getGenome().getActiveAllele(EnumTreeChromosome.SAPPINESS));
            String maturation = system.getAlleleName(
                    EnumTreeChromosome.MATURATION,
                    system.getIndividual(species.getUID()).getGenome().getActiveAllele(EnumTreeChromosome.MATURATION));
        }

        if (textView) {
            scroll = new ControlListBox<IAlleleSpecies>(this, 4.0f, y, w() - 8.0f, h() - y - 8.0f - 20.0f, 0.0f) {
                @Override
                public void initialise() {
                    super.initialise();
                    setOptions(options);
                }

                @Override
                public IWidget createOption(IAlleleSpecies v, int y) {
                    return new Control(getContent(), 0.0f, y, w(), 12.0f) {
                        IAlleleSpecies value = v;

                        @Override
                        public void onRenderBackground() {
                            CraftGUI.render.text(getArea(), TextJustification.MIDDLE_CENTER, value.getName(), 0xffffff);
                        }
                    };
                }
            };
        } else {
            scroll = new ControlScrollableContent(this, 4.0f, y, w() - 8.0f, h() - y - 8.0f, 0.0f);
            scroll.setScrollableContent(getItemScrollList(system, options));
        }
        new ControlScrollBar(this, scroll.x() + scroll.w() - 6.0f, scroll.y() + 3.0f, 3.0f, scroll.h() - 6.0f, scroll) {
            @Override
            public void onRenderBackground() {
                if (!isEnabled()) {
                    return;
                }
                CraftGUI.render.gradientRect(
                        getArea(),
                        0x44000000 + AnalystPageDatabase.this.getColor(),
                        0x44000000 + AnalystPageDatabase.this.getColor());
                CraftGUI.render.solid(getRenderArea(), AnalystPageDatabase.this.getColor());
            }
        };
    }

    private IWidget getItemScrollList(BreedingSystem system, Collection<IAlleleSpecies> options) {
        return new Control(scroll, 0.0f, 0.0f, scroll.w(), scroll.h()) {
            @Override
            public void initialise() {
                int maxBiomePerLine = (int) ((w() - 4.0f + 2.0f) / 18.0f);
                float biomeListX = -6.0f + (w() - (maxBiomePerLine * 18 - 2)) / 2.0f;
                int dx = 0;
                int dy = 0;
                for (IAlleleSpecies species : options) {
                    IIndividual ind = system.getSpeciesRoot()
                            .templateAsIndividual(system.getSpeciesRoot().getTemplate(species.getUID()));
                    new ControlIndividualDisplay(this, biomeListX + dx, 2 + dy, ind) {
                        @Override
                        public void initialise() {
                            addSelfEventHandler(new EventMouse.Down.Handler() {
                                @Override
                                public void onEvent(EventMouse.Down event) {
                                    WindowAnalyst window = (WindowAnalyst) AnalystPageDatabase.this.getSuperParent();
                                    window.setIndividual(ind);
                                }
                            });
                        }

                        @Override
                        public void onRenderBackground() {
                            WindowAnalyst window = (WindowAnalyst) AnalystPageDatabase.this.getSuperParent();
                            if (window.getIndividual() != null
                                    && window.getIndividual().getGenome().getPrimary() == species) {
                                CraftGUI.render.color(0xeeeeee);
                                CraftGUI.render.texture(
                                        CraftGUITexture.TabSolid, getArea().outset(1));
                                CraftGUI.render.color(AnalystPageDatabase.this.getColor());
                                CraftGUI.render.texture(
                                        CraftGUITexture.TabOutline, getArea().outset(1));
                            } else if (calculateIsMouseOver()) {
                                CraftGUI.render.color(0xeeeeee);
                                CraftGUI.render.texture(
                                        CraftGUITexture.TabSolid, getArea().outset(1));
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
                setSize(new IPoint(w(), 4 + dy + 18));
            }
        };
    }

    @Override
    public String getTitle() {
        return I18N.localise("genetics.gui.analyst.registry");
    }

    private Collection<IAlleleSpecies> getSpecies(BreedingSystem system) {
        Collection<IAlleleSpecies> species = new ArrayList<>();
        species.addAll(
                isMaster
                        ? system.getAllSpecies()
                        : system.getDiscoveredSpecies(
                                getWindow().getWorld(), getWindow().getPlayer().getGameProfile()));
        return species;
    }
}
