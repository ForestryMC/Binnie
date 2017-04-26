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
import com.mojang.authlib.GameProfile;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;
import net.minecraft.util.IIcon;

public class ControlDatabaseIndividualDisplay extends ControlItemDisplay implements ITooltip {
	protected EnumDiscoveryState discovered;

	private IAlleleSpecies species;

	public void setSpecies(IAlleleSpecies species) {
		setSpecies(species, EnumDiscoveryState.Show);
	}

	public void setSpecies(IAlleleSpecies species, EnumDiscoveryState state) {
		ISpeciesRoot speciesRoot = Binnie.Genetics.getSpeciesRoot(species);
		BreedingSystem system = Binnie.Genetics.getSystem(speciesRoot.getUID());
		IIndividual ind = system.getSpeciesRoot().templateAsIndividual(system.getSpeciesRoot().getTemplate(species.getUID()));
		super.setItemStack(system.getSpeciesRoot().getMemberStack(ind, system.getDefaultType()));
		this.species = species;
		GameProfile username = Window.get(this).getUsername();
		if (state == EnumDiscoveryState.Undetermined) {
			state = (system.isSpeciesDiscovered(species, Window.get(this).getWorld(), username) ? EnumDiscoveryState.Discovered : EnumDiscoveryState.Undiscovered);
		}
		if (Window.get(this) instanceof WindowAbstractDatabase && ((WindowAbstractDatabase) Window.get(this)).isNEI) {
			state = EnumDiscoveryState.Show;
		}
		discovered = state;
		addAttribute(WidgetAttribute.MouseOver);
	}

	public ControlDatabaseIndividualDisplay(IWidget parent, float x, float y) {
		this(parent, x, y, 16.0f);
	}

	public ControlDatabaseIndividualDisplay(IWidget parent, float x, float y, float size) {
		super(parent, x, y, size);
		species = null;
		discovered = EnumDiscoveryState.Show;
		addSelfEventHandler(new EventMouse.Down.Handler() {
			@Override
			public void onEvent(EventMouse.Down event) {
				if (event.getButton() == 0 && species != null && EnumDiscoveryState.Show == discovered) {
					((WindowAbstractDatabase) getSuperParent()).gotoSpeciesDelayed(species);
				}
			}
		});
	}

	@Override
	public void onRenderForeground() {
		IIcon icon = null;
		if (species == null) {
			return;
		}

		BreedingSystem system = Binnie.Genetics.getSystem(species.getRoot());
		switch (discovered) {
			case Show:
				super.onRenderForeground();
				return;

			case Discovered:
				icon = system.getDiscoveredIcon();
				break;

			case Undiscovered:
				icon = system.getUndiscoveredIcon();
				break;
		}
		if (icon != null) {
			CraftGUI.Render.iconItem(IPoint.ZERO, icon);
		}
	}

	@Override
	public void getTooltip(Tooltip tooltip) {
		if (species == null) {
			return;
		}

		switch (discovered) {
			case Show:
				tooltip.add(species.getName());
				break;

			case Discovered:
				tooltip.add("Discovered Species");
				break;

			case Undiscovered:
				tooltip.add("Undiscovered Species");
				break;
		}
	}
}
