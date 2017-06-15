package binnie.core.machines.storage;

import net.minecraft.client.renderer.GlStateManager;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.BinnieCore;
import binnie.core.machines.Machine;
import binnie.core.resource.BinnieResource;

@SideOnly(Side.CLIENT)
class MachineRendererCompartment {
	public static MachineRendererCompartment instance = new MachineRendererCompartment();
	private ModelCompartment model;

	public MachineRendererCompartment() {
		this.model = new ModelCompartment();
	}

	public void renderMachine(final Machine machine, final int colour, final BinnieResource texture, final double x, final double y, final double z, final float var8) {
		GlStateManager.pushMatrix();
		int i1 = 0;
		final int ix = machine.getTileEntity().getPos().getX();
		final int iy = machine.getTileEntity().getPos().getY();
		final int iz = machine.getTileEntity().getPos().getZ();
		if (machine.getTileEntity() != null) {
			i1 = ix * iy * iz + ix * iy - ix * iz + iy * iz - ix + iy - iz;
		}
		final float phase = (float) Math.max(0.0, Math.sin((System.currentTimeMillis() + i1) * 0.003));
		GlStateManager.translate(x + 0.5, y + 1.5, z + 0.5);
		GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);

		BinnieCore.getBinnieProxy().bindTexture(texture);
		this.model.render(null, (float) x, (float) y, (float) z, 0.0625f, 0.0625f, 0.0625f);

		GlStateManager.popMatrix();
	}
}
