package binnie.genetics.machine;

import binnie.core.BinnieCore;
import binnie.core.machines.Machine;
import binnie.core.machines.component.IRender;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MachineRendererGenetics {
	public static MachineRendererGenetics instance = new MachineRendererGenetics();

	public void renderMachine(Machine machine, double x, double y, double z, float partialTicks, int destroyStage) {
		GL11.glPushMatrix();
		int i1 = 0;
		BlockPos pos = machine.getTileEntity().getPos();
		final int ix = pos.getX();
		final int iy = pos.getY();
		final int iz = pos.getZ();
		if (machine.getTileEntity() != null) {
			i1 = ix * iy * iz + ix * iy - ix * iz + iy * iz - ix + iy - iz;
		}
		final float phase = (float) Math.max(0.0, Math.sin((System.currentTimeMillis() + i1) * 0.003));
		GL11.glTranslated(x + 0.5, y + 1.5, z + 0.5);
		GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
		GL11.glPushMatrix();
		BinnieCore.proxy.getMinecraftInstance();
		if (Minecraft.isFancyGraphicsEnabled()) {
			for (IRender.Render render : machine.getInterfaces(IRender.Render.class)) {
				render.renderInWorld(x, y, z);
			}
		}
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

}
