package binnie.core.machines.storage;

import binnie.core.BinnieCore;
import binnie.core.machines.Machine;
import binnie.core.resource.BinnieResource;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
class MachineRendererCompartment {
    public static MachineRendererCompartment instance = new MachineRendererCompartment();
    private ModelCompartment model;

    public MachineRendererCompartment() {
        model = new ModelCompartment();
    }

    public void renderMachine(
            Machine machine, int colour, BinnieResource texture, double x, double y, double z, float var8) {
        GL11.glPushMatrix();
        int i1 = 0;
        int ix = machine.getTileEntity().xCoord;
        int iy = machine.getTileEntity().yCoord;
        int iz = machine.getTileEntity().zCoord;
        if (machine.getTileEntity() != null) {
            i1 = ix * iy * iz + ix * iy - ix * iz + iy * iz - ix + iy - iz;
        }
        float phase = (float) Math.max(0.0, Math.sin((System.currentTimeMillis() + i1) * 0.003));
        GL11.glTranslated(x + 0.5, y + 1.5, z + 0.5);
        GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
        BinnieCore.proxy.bindTexture(texture);
        GL11.glPushMatrix();
        model.render(null, (float) x, (float) y, (float) z, 0.0625f, 0.0625f, 0.0625f);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }
}
