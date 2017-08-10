package binnie.core.gui.database;

import javax.annotation.Nullable;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;

import com.mojang.authlib.GameProfile;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;

import binnie.core.Binnie;
import binnie.core.genetics.BreedingSystem;
import binnie.core.gui.Attribute;
import binnie.core.gui.ITooltip;
import binnie.core.gui.IWidget;
import binnie.core.gui.Tooltip;
import binnie.core.gui.events.EventMouse;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.minecraft.Window;
import binnie.core.gui.minecraft.control.ControlItemDisplay;
import binnie.core.gui.renderer.RenderUtil;
import binnie.core.util.I18N;

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
		this.addSelfEventHandler(EventMouse.Down.class, event -> {
			if (event.getButton() == 0 && ControlDatabaseIndividualDisplay.this.species != null && EnumDiscoveryState.Show == ControlDatabaseIndividualDisplay.this.discovered) {
				((WindowAbstractDatabase) ControlDatabaseIndividualDisplay.this.getTopParent()).gotoSpeciesDelayed(ControlDatabaseIndividualDisplay.this.species);
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
		this.addAttribute(Attribute.MOUSE_OVER);
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
	public void getTooltip(final Tooltip tooltip, ITooltipFlag tooltipFlag) {
		if (this.species != null) {
			switch (this.discovered) {
				case Show: {
					tooltip.add(this.species.getAlleleName());
					break;
				}
				case Discovered: {
					tooltip.add(I18N.localise(DatabaseConstants.DISCOVERED_KEY + ".discovered"));
					break;
				}
				case Undiscovered: {
					tooltip.add(I18N.localise(DatabaseConstants.DISCOVERED_KEY + ".undiscovered"));
					break;
				}
			}
		}
	}
}
