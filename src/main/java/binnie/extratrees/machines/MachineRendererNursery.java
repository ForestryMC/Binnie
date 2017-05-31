package binnie.extratrees.machines;

import binnie.core.BinnieCore;
import binnie.core.resource.BinnieResource;
import binnie.core.resource.ResourceType;
import binnie.extratrees.ExtraTrees;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MachineRendererNursery {
	public static MachineRendererNursery instance = new MachineRendererNursery();
	;
	BinnieResource texture;
	//private IModelCustom casinoMachine;
	private ModelNursery model;

	public MachineRendererNursery() {
		this.model = new ModelNursery();
	}

	@SideOnly(Side.CLIENT)
	public void renderMachine(final BinnieResource texture, final double x, final double y, final double z, final float var8) {
		this.texture = texture;
		GlStateManager.pushMatrix();
		GlStateManager.translate(x + 0.5, y, z + 0.5);
		GlStateManager.scale(0.05, 0.05, 0.05);
		BinnieCore.getBinnieProxy().bindTexture(new BinnieResource(ExtraTrees.instance, ResourceType.Tile, "test.png"));
		//this.casinoMachine.renderAll();
		GlStateManager.popMatrix();
	}
}
