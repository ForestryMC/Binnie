package binnie.genetics.machine;

import binnie.core.BinnieCore;
import binnie.core.machines.Machine;
import binnie.core.machines.component.IRender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MachineRendererGenetics {
	public static MachineRendererGenetics instance = new MachineRendererGenetics();

	public void renderMachine(Machine machine, double x, double y, double z, float partialTicks, int destroyStage) {
		GlStateManager.pushMatrix();
		int i1 = 0;
		BlockPos pos = machine.getTileEntity().getPos();
		final int ix = pos.getX();
		final int iy = pos.getY();
		final int iz = pos.getZ();
		if (machine.getTileEntity() != null) {
			i1 = ix * iy * iz + ix * iy - ix * iz + iy * iz - ix + iy - iz;
		}
		final float phase = (float) Math.max(0.0, Math.sin((System.currentTimeMillis() + i1) * 0.003));
		GlStateManager.translate(x + 0.5, y + 1.5, z + 0.5);
		GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
		BinnieCore.getBinnieProxy().getMinecraftInstance();
		if (Minecraft.isFancyGraphicsEnabled()) {
			for (IRender.Render render : machine.getInterfaces(IRender.Render.class)) {
				render.renderInWorld(x, y, z);
			}
		}
		GlStateManager.popMatrix();
	}

}
