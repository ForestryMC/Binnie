package binnie.genetics.machine;

import binnie.core.BinnieCore;
import binnie.core.machines.Machine;
import binnie.core.machines.component.IRender;
import binnie.core.resource.BinnieResource;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MachineRendererGenetics {
    public static MachineRendererGenetics instance = new MachineRendererGenetics();

    public RenderItem customRenderItem;

    private ModelMachine model;

    public MachineRendererGenetics() {
        model = new ModelMachine();
        customRenderItem = new RenderItem() {
            @Override
            public boolean shouldBob() {
                return false;
            }

            @Override
            public boolean shouldSpreadItems() {
                return false;
            }
        };
        customRenderItem.setRenderManager(RenderManager.instance);
    }

    public void renderMachine(
            Machine machine, int colour, BinnieResource texture, double x, double y, double z, float var8) {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5, y + 1.5, z + 0.5);
        GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
        BinnieCore.proxy.bindTexture(texture);
        GL11.glPushMatrix();
        model.render((float) x, (float) y, (float) z, 0.0625f, 0.0625f, 0.0625f);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        BinnieCore.proxy.getMinecraftInstance();

        if (Minecraft.isFancyGraphicsEnabled()) {
            for (IRender.Render render : machine.getInterfaces(IRender.Render.class)) {
                render.renderInWorld(customRenderItem, x, y, z);
            }
        }

        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }
}
