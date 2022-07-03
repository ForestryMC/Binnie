package binnie.core.machines;

import binnie.core.BinnieCore;
import binnie.core.resource.BinnieResource;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MachineRendererBlock {
    public static MachineRendererBlock instance = new MachineRendererBlock();
    private BinnieResource texture;
    private ModelBlock model;

    public MachineRendererBlock() {
        model = new ModelBlock();
    }

    public void renderMachine(BinnieResource texture, double x, double y, double z, float var8) {
        this.texture = texture;
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5, y + 1.5, z + 0.5);
        GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
        BinnieCore.proxy.bindTexture(texture);
        GL11.glPushMatrix();
        model.render((float) x, (float) y, (float) z, 0.0625f, 0.0625f, 0.0625f);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }
}
