package binnie.craftgui.mod.database;

import binnie.core.genetics.BreedingSystem;
import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.*;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.resource.Texture;
import binnie.craftgui.resource.minecraft.CraftGUITextureSheet;
import binnie.craftgui.resource.minecraft.StandardTexture;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IMutation;

class ControlMutationSymbol extends Control implements ITooltip {
    private static Texture MutationPlus;
    private static Texture MutationArrow;
    private IMutation value;
    private boolean discovered;
    private int type;

    @Override
    public void onRenderBackground() {
        super.onRenderBackground();
        if (this.type == 0) {
            CraftGUI.Render.texture(ControlMutationSymbol.MutationPlus, IPoint.ZERO);
        } else {
            CraftGUI.Render.texture(ControlMutationSymbol.MutationArrow, IPoint.ZERO);
        }
    }

    protected ControlMutationSymbol(final IWidget parent, final int x, final int y, final int type) {
        super(parent, x, y, 16 + type * 16, 16.0f);
        this.value = null;
        this.type = type;
        this.addAttribute(Attribute.MouseOver);
    }

    public void setValue(final IMutation value) {
        this.value = value;
        final boolean isNEI = ((WindowAbstractDatabase) this.getSuperParent()).isNEI();
        final BreedingSystem system = ((WindowAbstractDatabase) this.getSuperParent()).getBreedingSystem();
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
            final IAllele species1 = this.value.getAllele0();
            final IAllele species2 = this.value.getAllele1();
            final BreedingSystem system = ((WindowAbstractDatabase) this.getSuperParent()).getBreedingSystem();
            final float chance = system.getChance(this.value, Window.get(this).getPlayer(), species1, species2);
            tooltip.add("Current Chance - " + chance + "%");
            if (this.value.getSpecialConditions() != null) {
                for (final String string : this.value.getSpecialConditions()) {
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
