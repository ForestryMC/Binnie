package binnie.extratrees.craftgui;

import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.minecraft.MinecraftGUI;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.minecraft.control.ControlProgressBase;
import binnie.core.craftgui.resource.Texture;
import binnie.core.craftgui.resource.minecraft.StandardTexture;
import binnie.core.craftgui.window.Panel;
import binnie.extratrees.core.ExtraTreeTexture;
import binnie.extratrees.machines.lumbermill.Lumbermill;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import org.lwjgl.opengl.GL11;

public class ControlLumbermillProgress extends ControlProgressBase {
    protected static Texture saw = new StandardTexture(0, 0, 6, 32, ExtraTreeTexture.Gui);
    protected static Texture saw2 = new StandardTexture(2, 0, 4, 32, ExtraTreeTexture.Gui);

    protected float oldProgress;
    protected float animation;

    public ControlLumbermillProgress(IWidget parent, float x, float y) {
        super(parent, x, y, 66.0f, 18.0f);
        oldProgress = 0.0f;
        animation = 0.0f;
        new Panel(this, 0.0f, 0.0f, 66.0f, 18.0f, MinecraftGUI.PanelType.Black);
    }

    @Override
    public void onUpdateClient() {
        super.onUpdateClient();
        if (oldProgress != progress) {
            oldProgress = progress;
            animation += 5.0f;
        }
    }

    @Override
    public void onRenderForeground() {
        GL11.glDisable(2896);
        int sawX = (int) (63.0f * progress);
        CraftGUI.render.texture(
                ControlLumbermillProgress.saw, new IPoint(sawX, -8.0f + 6.0f * (float) Math.sin(animation)));
        ItemStack item = Window.get(this).getInventory().getStackInSlot(Lumbermill.SLOT_WOOD);
        if (item == null) {
            return;
        }

        GL11.glDisable(2896);
        Block block = null;
        if (item.getItem() instanceof ItemBlock) {
            block = ((ItemBlock) item.getItem()).field_150939_a;
        }
        if (block == null) {
            return;
        }

        IIcon icon = block.getIcon(2, item.getItemDamage());
        for (int i = 0; i < 4; ++i) {
            CraftGUI.render.iconBlock(new IPoint(1 + i * 16, 1.0f), icon);
        }
        ItemStack result = Lumbermill.getPlankProduct(item);
        if (result == null) {
            return;
        }

        Block block2 = null;
        if (item.getItem() instanceof ItemBlock) {
            block2 = ((ItemBlock) result.getItem()).field_150939_a;
        }
        if (block2 == null) {
            return;
        }

        IIcon icon2 = block2.getIcon(2, result.getItemDamage());
        IPoint pos = getAbsolutePosition();
        CraftGUI.render.limitArea(
                new IArea(pos.add(new IPoint(0.0f, 0.0f)), new IPoint(progress * 64.0f + 2.0f, 18.0f)));
        GL11.glEnable(3089);
        for (int j = 0; j < 4; ++j) {
            CraftGUI.render.iconBlock(new IPoint(1 + j * 16, 1.0f), icon2);
        }
        GL11.glDisable(3089);
        CraftGUI.render.texture(
                ControlLumbermillProgress.saw2, new IPoint(sawX + 2, -8.0f + 6.0f * (float) Math.sin(animation)));
    }
}
