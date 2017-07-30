package binnie.genetics.gui.database;

import javax.annotation.Nullable;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;

import com.mojang.authlib.GameProfile;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;

import binnie.Binnie;
import binnie.core.craftgui.Attribute;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.events.EventMouse;
import binnie.core.craftgui.geometry.Point;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.minecraft.control.ControlItemDisplay;
import binnie.core.craftgui.renderer.RenderUtil;
import binnie.core.genetics.BreedingSystem;

public class ControlDatabaseIndividualDisplay extends ControlItemDisplay implements ITooltip {
	EnumDiscoveryState discovered;
	@Nullable
	private IAlleleSpecies species;

	public ControlDatabaseIndividualDisplay(final IWidget parent, final int x, final int y) {
		this(parent, x, y, 16);
	}

	public ControlDatabaseIndividualDisplay(final IWidget parent, final int x, final int y, final int size) {
		super(parent, x, y, size);
		this.species = null;
		this.discovered = EnumDiscoveryState.Show;
		this.addSelfEventHandler(new EventMouse.Down.Handler() {
			@Override
			public void onEvent(final EventMouse.Down event) {
				if (event.getButton() == 0 && ControlDatabaseIndividualDisplay.this.species != null && EnumDiscoveryState.Show == ControlDatabaseIndividualDisplay.this.discovered) {
					((WindowAbstractDatabase) ControlDatabaseIndividualDisplay.this.getTopParent()).gotoSpeciesDelayed(ControlDatabaseIndividualDisplay.this.species);
				}
			}
		});
	}

	public void setSpecies(final IAlleleSpecies species) {
		this.setSpecies(species, EnumDiscoveryState.Show);
	}

	public void setSpecies(final IAlleleSpecies species, EnumDiscoveryState state) {
		final ISpeciesRoot speciesRoot = Binnie.GENETICS.getSpeciesRoot(species);
		final BreedingSystem system = Binnie.GENETICS.getSystem(speciesRoot);
		final IAllele[] template = system.getSpeciesRoot().getTemplate(species);
		final IIndividual ind = system.getSpeciesRoot().templateAsIndividual(template);
		super.setItemStack(system.getSpeciesRoot().getMemberStack(ind, system.getDefaultType()));
		this.species = species;
		final GameProfile username = Window.get(this).getUsername();
		if (state == EnumDiscoveryState.Undetermined) {
			state = (system.isSpeciesDiscovered(species, Window.get(this).getWorld(), username) ? EnumDiscoveryState.Discovered : EnumDiscoveryState.Undiscovered);
		}
		if (Window.get(this) instanceof WindowAbstractDatabase && ((WindowAbstractDatabase) Window.get(this)).isNEI) {
			state = EnumDiscoveryState.Show;
		}
		this.discovered = state;
		this.addAttribute(Attribute.MouseOver);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderForeground(int guiWidth, int guiHeight) {

		TextureAtlasSprite icon = null;
		if (this.species == null) {
			return;
		}
		BreedingSystem system = Binnie.GENETICS.getSystem(this.species.getRoot());
		switch (this.discovered) {
			case Show: {
				super.onRenderForeground(guiWidth, guiHeight);
				return;
			}
			case Discovered: {
				icon = system.getDiscoveredIcon();
				break;
			}
			case Undiscovered: {
				icon = system.getUndiscoveredIcon();
				break;
			}
		}
		if (icon != null) {
			RenderUtil.drawGuiSprite(Point.ZERO, icon);
		}
	}

	@Override
	public void getTooltip(final Tooltip tooltip) {
		if (this.species != null) {
			switch (this.discovered) {
				case Show: {
					tooltip.add(this.species.getName());
					break;
				}
				case Discovered: {
					tooltip.add("Discovered Species");
					break;
				}
				case Undiscovered: {
					tooltip.add("Undiscovered Species");
					break;
				}
			}
		}
	}
}
