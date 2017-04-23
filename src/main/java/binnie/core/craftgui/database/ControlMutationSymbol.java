// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui.database;

import binnie.core.craftgui.Attribute;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.resource.Texture;
import binnie.core.craftgui.resource.minecraft.CraftGUITextureSheet;
import binnie.core.craftgui.resource.minecraft.StandardTexture;
import binnie.core.genetics.BreedingSystem;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IMutation;

class ControlMutationSymbol extends Control implements ITooltip
{
	private static Texture MutationPlus;
	private static Texture MutationArrow;
	private IMutation value;
	private boolean discovered;
	private int type;

	@Override
	public void onRenderBackground() {
		super.onRenderBackground();
		if (type == 0) {
			CraftGUI.Render.texture(ControlMutationSymbol.MutationPlus, IPoint.ZERO);
		}
		else {
			CraftGUI.Render.texture(ControlMutationSymbol.MutationArrow, IPoint.ZERO);
		}
	}

	protected ControlMutationSymbol(final IWidget parent, final int x, final int y, final int type) {
		super(parent, x, y, 16 + type * 16, 16.0f);
		value = null;
		this.type = type;
		addAttribute(Attribute.MouseOver);
	}

	public void setValue(final IMutation value) {
		this.value = value;
		final boolean isNEI = ((WindowAbstractDatabase) getSuperParent()).isNEI();
		final BreedingSystem system = ((WindowAbstractDatabase) getSuperParent()).getBreedingSystem();
		discovered = (isNEI || system.isMutationDiscovered(value, Window.get(this).getWorld(), Window.get(this).getUsername()));
		if (discovered) {
			setColour(16777215);
		}
		else {
			setColour(7829367);
		}
	}

	@Override
	public void getTooltip(final Tooltip tooltip) {
		if (type == 1 && discovered) {
			final IAllele species1 = value.getAllele0();
			final IAllele species2 = value.getAllele1();
			final BreedingSystem system = ((WindowAbstractDatabase) getSuperParent()).getBreedingSystem();
			final float chance = system.getChance(value, Window.get(this).getPlayer(), species1, species2);
			tooltip.add("Current Chance - " + chance + "%");
			if (value.getSpecialConditions() != null) {
				for (final String string : value.getSpecialConditions()) {
					tooltip.add(string);
				}
			}
		}
	}

	static {
		ControlMutationSymbol.MutationPlus = new StandardTexture(2, 94, 16, 16, CraftGUITextureSheet.Controls2);
		ControlMutationSymbol.MutationArrow = new StandardTexture(20, 94, 32, 16, CraftGUITextureSheet.Controls2);
	}
}
