package binnie.core.integration.jei;

import binnie.genetics.integration.jei.GeneticsJeiPlugin;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableStatic;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class Drawables {
    private static Drawables INSTANCE;

    public static Drawables getDrawables(IGuiHelper guiHelper) {
        if (INSTANCE == null) {
            INSTANCE = new Drawables(guiHelper);
        }
        return INSTANCE;
    }

    @Nonnull
    public static final ResourceLocation guiTank = new ResourceLocation("binniecore:textures/gui/craftgui-slots.png");
    @Nonnull
    public static final ResourceLocation guiArrow = new ResourceLocation("binniecore:textures/gui/craftgui-panels.png");

    @Nonnull
    private final IDrawable tank;
    @Nonnull
    private final IDrawable tankOverlay;
    @Nonnull
    private final IDrawable arrow;
    @Nonnull
    private final IDrawableStatic arrowWhite;

    private Drawables(IGuiHelper guiHelper) {
        this.tank = guiHelper.createDrawable(guiTank, 8, 28, 18, 60);
        this.tankOverlay = guiHelper.createDrawable(guiTank, 33, 29, 16, 58);
        this.arrow = guiHelper.createDrawable(guiArrow, 191, 79, 14, 10);
        this.arrowWhite = guiHelper.createDrawable(guiArrow, 207, 79, 14, 10);
    }

    @Nonnull
    public IDrawable getTank() {
        return tank;
    }

    @Nonnull
    public IDrawable getTankOverlay() {
        return tankOverlay;
    }

    @Nonnull
    public IDrawable getArrow() {
        return arrow;
    }

    @Nonnull
    public IDrawableStatic getArrowWhite() {
        return arrowWhite;
    }
}
