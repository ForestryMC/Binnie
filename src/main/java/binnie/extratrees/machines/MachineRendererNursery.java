// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.machines;

import binnie.core.resource.ResourceType;
import binnie.extratrees.ExtraTrees;
import binnie.core.BinnieCore;
import org.lwjgl.opengl.GL11;
import net.minecraftforge.client.model.IModelCustom;
import binnie.core.resource.BinnieResource;

public class MachineRendererNursery
{
	public static MachineRendererNursery instance;
	BinnieResource texture;
	private IModelCustom casinoMachine;
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
		this.casinoMachine.renderAll();
		GL11.glPopMatrix();
	}

	static {
		MachineRendererNursery.instance = new MachineRendererNursery();
	}
}
