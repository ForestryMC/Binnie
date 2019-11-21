package binnie.core.gui.database;

import binnie.core.api.genetics.IBreedingSystem;
import binnie.core.api.gui.IWidget;
import binnie.core.gui.Attribute;
import binnie.core.gui.CraftGUI;
import binnie.core.gui.ITooltip;
import binnie.core.gui.Tooltip;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.minecraft.Window;
import binnie.core.gui.resource.textures.CraftGUITextureSheet;
import binnie.core.gui.resource.textures.StandardTexture;
import binnie.core.gui.resource.textures.Texture;
import binnie.core.util.I18N;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IMutation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

class ControlMutationSymbol extends Control implements ITooltip {
	private static final Texture MutationPlus = new StandardTexture(2, 94, 16, 16, CraftGUITextureSheet.CONTROLS);
	private static final Texture MutationArrow = new StandardTexture(20, 94, 32, 16, CraftGUITextureSheet.CONTROLS);
	private IMutation value;
	private boolean discovered;
	private final int type;

	protected ControlMutationSymbol(final IWidget parent, final int x, final int y, final int type) {
		super(parent, x, y, 16 + type * 16, 16);
		this.value = null;
		this.type = type;
		this.addAttribute(Attribute.MOUSE_OVER);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		super.onRenderBackground(guiWidth, guiHeight);
		if (this.type == 0) {
			CraftGUI.RENDER.texture(ControlMutationSymbol.MutationPlus, Point.ZERO);
		} else {
			CraftGUI.RENDER.texture(ControlMutationSymbol.MutationArrow, Point.ZERO);
		}
	}

	public void setValue(final IMutation value) {
		this.value = value;
		final boolean isMaster = ((WindowAbstractDatabase) this.getTopParent()).isMaster();
		final IBreedingSystem system = ((WindowAbstractDatabase) this.getTopParent()).getBreedingSystem();
		this.discovered = (isMaster || system.isMutationDiscovered(value, Window.get(this).getWorld(), Window.get(this).getUsername()));
		if (this.discovered) {
			this.setColor(16777215);
		} else {
			this.setColor(7829367);
		}
	}

	@Override
	public void getTooltip(final Tooltip tooltip, ITooltipFlag tooltipFlag) {
		if (this.type == 1 && this.discovered) {
			final IAlleleSpecies species1 = this.value.getAllele0();
			final IAlleleSpecies species2 = this.value.getAllele1();
			final IBreedingSystem system = ((WindowAbstractDatabase) this.getTopParent()).getBreedingSystem();
			final float chance = system.getChance(this.value, Window.get(this).getPlayer(), species1, species2);
			tooltip.add(I18N.localise(DatabaseConstants.CONTROL_KEY + ".chance", chance));
			for (final String string : this.value.getSpecialConditions()) {
				tooltip.add(string);
			}
		}
	}
}
