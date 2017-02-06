package binnie.core.machines;

import binnie.core.BinnieCore;
import binnie.core.resource.BinnieResource;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MachineRendererBlock {
	public static MachineRendererBlock instance = new MachineRendererBlock();
	private BinnieResource texture;
	private ModelBlock model;

	public MachineRendererBlock() {
		this.model = new ModelBlock();
	}

	public void renderMachine(final BinnieResource texture, final double x, final double y, final double z, final float partialTicks) {
		this.texture = texture;
		GlStateManager.pushMatrix();
		GlStateManager.translate(x + 0.5, y + 1.5, z + 0.5);
		GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);

		BinnieCore.getBinnieProxy().bindTexture(texture);
		this.model.render((float) x, (float) y, (float) z, 0.0625f, 0.0625f, 0.0625f);

		GlStateManager.popMatrix();
	}

}
