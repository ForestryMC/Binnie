package binnie.extratrees.machines;

import binnie.core.BinnieCore;
import binnie.core.resource.BinnieResource;
import binnie.core.resource.ResourceType;
import binnie.extratrees.ExtraTrees;
import org.lwjgl.opengl.GL11;

public class MachineRendererNursery {
    public static MachineRendererNursery instance = new MachineRendererNursery();;
    BinnieResource texture;
    //private IModelCustom casinoMachine;
    private ModelNursery model;

    public MachineRendererNursery() {
        this.model = new ModelNursery();
    }

    public void renderMachine(final BinnieResource texture, final double x, final double y, final double z, final float var8) {
        this.texture = texture;
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5, y, z + 0.5);
        GL11.glScaled(0.05, 0.05, 0.05);
        BinnieCore.proxy.bindTexture(new BinnieResource(ExtraTrees.instance, ResourceType.Tile, "test.png"));
        //this.casinoMachine.renderAll();
        GL11.glPopMatrix();
    }

}
