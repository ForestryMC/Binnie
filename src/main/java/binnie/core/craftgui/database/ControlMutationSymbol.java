package binnie.core.craftgui.database;

import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.WidgetAttribute;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.resource.Texture;
import binnie.core.craftgui.resource.minecraft.CraftGUITextureSheet;
import binnie.core.craftgui.resource.minecraft.StandardTexture;
import binnie.core.genetics.BreedingSystem;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IMutation;

class ControlMutationSymbol extends Control implements ITooltip {
    private static Texture MutationPlus = new StandardTexture(2, 94, 16, 16, CraftGUITextureSheet.Controls2);
    private static Texture MutationArrow = new StandardTexture(20, 94, 32, 16, CraftGUITextureSheet.Controls2);
    private IMutation value;
    private boolean discovered;
    private int type;

    @Override
    public void onRenderBackground() {
        super.onRenderBackground();
        if (type == 0) {
            CraftGUI.render.texture(ControlMutationSymbol.MutationPlus, IPoint.ZERO);
        } else {
            CraftGUI.render.texture(ControlMutationSymbol.MutationArrow, IPoint.ZERO);
        }
    }

    protected ControlMutationSymbol(IWidget parent, int x, int y, int type) {
        super(parent, x, y, 16 + type * 16, 16.0f);
        value = null;
        this.type = type;
        addAttribute(WidgetAttribute.MOUSE_OVER);
    }

    public void setValue(IMutation value) {
        this.value = value;
        boolean isNEI = ((WindowAbstractDatabase) getSuperParent()).isNEI();
        BreedingSystem system = ((WindowAbstractDatabase) getSuperParent()).getBreedingSystem();
        discovered = (isNEI
                || system.isMutationDiscovered(
                        value, Window.get(this).getWorld(), Window.get(this).getUsername()));
        if (discovered) {
            setColor(0xffffff);
        } else {
            setColor(0x777777);
        }
    }

    @Override
    public void getTooltip(Tooltip tooltip) {
        if (type != 1 || !discovered) {
            return;
        }

        IAllele species1 = value.getAllele0();
        IAllele species2 = value.getAllele1();
        BreedingSystem system = ((WindowAbstractDatabase) getSuperParent()).getBreedingSystem();
        float chance = system.getChance(value, Window.get(this).getPlayer(), species1, species2);
        tooltip.add("Current Chance - " + chance + "%");
        if (value.getSpecialConditions() != null) {
            for (String string : value.getSpecialConditions()) {
                tooltip.add(string);
            }
        }
    }
}
