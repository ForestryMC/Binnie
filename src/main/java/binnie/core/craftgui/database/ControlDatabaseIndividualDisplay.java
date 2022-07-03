package binnie.core.craftgui.database;

import binnie.Binnie;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.WidgetAttribute;
import binnie.core.craftgui.events.EventMouse;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.minecraft.control.ControlItemDisplay;
import binnie.core.genetics.BreedingSystem;
import binnie.core.util.I18N;
import com.mojang.authlib.GameProfile;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;
import net.minecraft.util.IIcon;

public class ControlDatabaseIndividualDisplay extends ControlItemDisplay implements ITooltip {
    protected EnumDiscoveryState discovered;

    private IAlleleSpecies species;

    public void setSpecies(IAlleleSpecies species) {
        setSpecies(species, EnumDiscoveryState.SHOW);
    }

    public void setSpecies(IAlleleSpecies species, EnumDiscoveryState state) {
        this.species = species;
        ISpeciesRoot speciesRoot = Binnie.Genetics.getSpeciesRoot(species);
        BreedingSystem system = Binnie.Genetics.getSystem(speciesRoot.getUID());
        IIndividual ind = system.getSpeciesRoot()
                .templateAsIndividual(system.getSpeciesRoot().getTemplate(species.getUID()));
        super.setItemStack(system.getSpeciesRoot().getMemberStack(ind, system.getDefaultType()));
        Window window = Window.get(this);
        GameProfile username = window.getUsername();

        if (window instanceof WindowAbstractDatabase && ((WindowAbstractDatabase) window).isNEI) {
            state = EnumDiscoveryState.SHOW;
        } else if (state == EnumDiscoveryState.UNDETERMINED) {
            state = system.isSpeciesDiscovered(species, window.getWorld(), username)
                    ? EnumDiscoveryState.DISCOVERED
                    : EnumDiscoveryState.UNDISCOVERED;
        }

        discovered = state;
        addAttribute(WidgetAttribute.MOUSE_OVER);
    }

    public ControlDatabaseIndividualDisplay(IWidget parent, float x, float y) {
        this(parent, x, y, 16.0f);
    }

    public ControlDatabaseIndividualDisplay(IWidget parent, float x, float y, float size) {
        super(parent, x, y, size);
        species = null;
        discovered = EnumDiscoveryState.SHOW;
        addSelfEventHandler(new MouseDownHandler());
    }

    @Override
    public void onRenderForeground() {
        IIcon icon = null;
        if (species == null) {
            return;
        }

        BreedingSystem system = Binnie.Genetics.getSystem(species.getRoot());
        switch (discovered) {
            case SHOW:
                super.onRenderForeground();
                return;

            case DISCOVERED:
                icon = system.getDiscoveredIcon();
                break;

            case UNDISCOVERED:
                icon = system.getUndiscoveredIcon();
                break;
        }
        if (icon != null) {
            CraftGUI.render.iconItem(IPoint.ZERO, icon);
        }
    }

    @Override
    public void getTooltip(Tooltip tooltip) {
        if (species == null) {
            return;
        }

        switch (discovered) {
            case SHOW:
            case DISCOVERED:
                tooltip.add(species.getName());
                break;

            case UNDISCOVERED:
                tooltip.add(I18N.localise("binniecore.gui.database.discovered.undiscovered"));
                break;
        }
    }

    private class MouseDownHandler extends EventMouse.Down.Handler {
        @Override
        public void onEvent(EventMouse.Down event) {
            if (event.getButton() == 0 && species != null && EnumDiscoveryState.SHOW == discovered) {
                ((WindowAbstractDatabase) getSuperParent()).gotoSpeciesDelayed(species);
            }
        }
    }
}
