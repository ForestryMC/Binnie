package binnie.core.machines;

import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class TileEntityMachineRenderer extends TileEntitySpecialRenderer<TileEntityMachine> {

	@Override
	public void renderTileEntityAt(TileEntityMachine te, double x, double y, double z, float partialTicks, int destroyStage) {
		if (te != null && te.getMachine() != null) {
			te.getMachine().getPackage().renderMachine(te.getMachine(), x, y, z, partialTicks, destroyStage);
		}
	}
}