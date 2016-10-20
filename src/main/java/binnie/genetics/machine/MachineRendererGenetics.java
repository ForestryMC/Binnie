// 
// Decompiled by Procyon v0.5.30
// 

package binnie.genetics.machine;

import binnie.core.machines.component.IRender;
import net.minecraft.client.Minecraft;
import binnie.core.BinnieCore;
import org.lwjgl.opengl.GL11;
import binnie.core.resource.BinnieResource;
import binnie.core.machines.Machine;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MachineRendererGenetics
{
	public static MachineRendererGenetics instance;
	public final RenderItem customRenderItem;
	private ModelMachine model;

	public MachineRendererGenetics() {
		this.model = new ModelMachine();
		(this.customRenderItem = new RenderItem() {
			@Override
			public boolean shouldBob() {
				return false;
			}

			@Override
			public boolean shouldSpreadItems() {
				return false;
			}
		}).setRenderManager(RenderManager.instance);
	}

	public void renderMachine(final Machine machine, final int colour, final BinnieResource texture, final double x, final double y, final double z, final float var8) {
		GL11.glPushMatrix();
		int i1 = 0;
		final int ix = machine.getTileEntity().xCoord;
		final int iy = machine.getTileEntity().yCoord;
		final int iz = machine.getTileEntity().zCoord;
		if (machine.getTileEntity() != null) {
			i1 = ix * iy * iz + ix * iy - ix * iz + iy * iz - ix + iy - iz;
		}
		final float phase = (float) Math.max(0.0, Math.sin((System.currentTimeMillis() + i1) * 0.003));
		GL11.glTranslated(x + 0.5, y + 1.5, z + 0.5);
		GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
		BinnieCore.proxy.bindTexture(texture);
		GL11.glPushMatrix();
		this.model.render((float) x, (float) y, (float) z, 0.0625f, 0.0625f, 0.0625f);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		BinnieCore.proxy.getMinecraftInstance();
		if (Minecraft.isFancyGraphicsEnabled()) {
			for (final IRender.Render render : machine.getInterfaces(IRender.Render.class)) {
				render.renderInWorld(this.customRenderItem, x, y, z);
			}
		}
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

	static {
		MachineRendererGenetics.instance = new MachineRendererGenetics();
	}
}
