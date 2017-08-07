package binnie.core.machines;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class TileEntityMachineRenderer extends TileEntitySpecialRenderer<TileEntityMachine> {

	@Override
	public void render(TileEntityMachine te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		if (te != null && te.getMachine() != null) {
			te.getMachine().getPackage().renderMachine(te.getMachine(), x, y, z, partialTicks, destroyStage);
		}
	}
}