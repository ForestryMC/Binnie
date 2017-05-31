package binnie.core.craftgui.database;

import binnie.core.craftgui.Attribute;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.geometry.Point;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.resource.Texture;
import binnie.core.craftgui.resource.minecraft.CraftGUITextureSheet;
import binnie.core.craftgui.resource.minecraft.StandardTexture;
import binnie.core.genetics.BreedingSystem;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IMutation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

class ControlMutationSymbol extends Control implements ITooltip {
	private static Texture MutationPlus = new StandardTexture(2, 94, 16, 16, CraftGUITextureSheet.Controls2);
	private static Texture MutationArrow = new StandardTexture(20, 94, 32, 16, CraftGUITextureSheet.Controls2);
	private IMutation value;
	private boolean discovered;
	private int type;

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		super.onRenderBackground(guiWidth, guiHeight);
		if (this.type == 0) {
			CraftGUI.render.texture(ControlMutationSymbol.MutationPlus, Point.ZERO);
		} else {
			CraftGUI.render.texture(ControlMutationSymbol.MutationArrow, Point.ZERO);
		}
	}

	protected ControlMutationSymbol(final IWidget parent, final int x, final int y, final int type) {
		super(parent, x, y, 16 + type * 16, 16);
		this.value = null;
		this.type = type;
		this.addAttribute(Attribute.MouseOver);
	}

	public void setValue(final IMutation value) {
		this.value = value;
		final boolean isNEI = ((WindowAbstractDatabase) this.getTopParent()).isNEI();
		final BreedingSystem system = ((WindowAbstractDatabase) this.getTopParent()).getBreedingSystem();
		this.discovered = (isNEI || system.isMutationDiscovered(value, Window.get(this).getWorld(), Window.get(this).getUsername()));
		if (this.discovered) {
			this.setColour(16777215);
		} else {
			this.setColour(7829367);
		}
	}

	@Override
	public void getTooltip(final Tooltip tooltip) {
		if (this.type == 1 && this.discovered) {
			final IAlleleSpecies species1 = this.value.getAllele0();
			final IAlleleSpecies species2 = this.value.getAllele1();
			final BreedingSystem system = ((WindowAbstractDatabase) this.getTopParent()).getBreedingSystem();
			final float chance = system.getChance(this.value, Window.get(this).getPlayer(), species1, species2);
			tooltip.add("Current Chance - " + chance + "%");
			for (final String string : this.value.getSpecialConditions()) {
				tooltip.add(string);
			}
		}
	}
}
