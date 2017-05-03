package binnie.extratrees.machines;

import binnie.core.BinnieCore;
import binnie.core.resource.BinnieResource;
import binnie.core.resource.ResourceType;
import binnie.extratrees.ExtraTrees;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

public class MachineRendererNursery {
	public static MachineRendererNursery instance = new MachineRendererNursery();

	protected BinnieResource texture;

	private IModelCustom casinoMachine;
	private ModelNursery model;

	public MachineRendererNursery() {
		model = new ModelNursery();
	}

	public void renderMachine(BinnieResource texture, double x, double y, double z, float var8) {
		this.texture = texture;
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		GL11.glScaled(0.05, 0.05, 0.05);
		BinnieCore.proxy.bindTexture(new BinnieResource(ExtraTrees.instance, ResourceType.Tile, "test.png"));
		casinoMachine.renderAll();
		GL11.glPopMatrix();
	}
}
