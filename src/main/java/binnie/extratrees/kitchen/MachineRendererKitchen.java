package binnie.extratrees.kitchen;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.machines.Machine;
import binnie.core.resource.BinnieResource;

@SideOnly(Side.CLIENT)
public class MachineRendererKitchen {
	public static MachineRendererKitchen instance = new MachineRendererKitchen();
	static int level = 0;
	;
	BinnieResource texture;

	public void renderMachine(final Machine machine, final BinnieResource texture, final double x, final double y, final double z, final float var8) {
		/*if (renderer != null && renderer.blockAccess != null) {
			final Block block = machine.getPackage().getGroup().getBlock();
			final float i = 0.0625f;
			renderer.setRenderBounds(2.0f * i, 0.0, 2.0f * i, 14.0f * i, 14.0f * i, 14.0f * i);
			renderer.renderStandardBlock(block, (int) x, (int) y, (int) z);
			MachineRendererKitchen.level = 1;
			renderer.setRenderBounds(0.0, 14.0f * i, 0.0, 1.0, 1.0, 1.0);
			renderer.renderStandardBlock(block, (int) x, (int) y, (int) z);
			MachineRendererKitchen.level = 0;
			renderer.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
			if (RenderManager.instance == null || RenderManager.instance.renderEngine == null) {
				return;
			}
			final IIcon icon = Blocks.dirt.getIcon(0, 0);
			final Tessellator tess = Tessellator.instance;
			boolean wasTessellating = true;
			try {
				tess.draw();
			} catch (Exception e) {
				wasTessellating = false;
			}
			tess.startDrawingQuads();
			tess.draw();
			GlStateManager.pushMatrix();
			GlStateManager.translate(x + 0.5, y + 1.0, z + 0.5);
			RenderManager.instance.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
			final float ux = icon.getMinU();
			final float uy = icon.getMinV();
			final float uw = icon.getMaxU();
			final float uh = icon.getMaxV();
			final int[] widths = { 1, 1, 1, 1, 1, 1, 1, 1, 2, 4, 5, 6, 6, 6, 5, 4 };
			for (int h = 0; h < 15; ++h) {
				final float h2 = h * i;
				final float h3 = h2 + i;
				final float d1 = widths[h] * i;
				final float d2 = widths[h + 1] * i;
				float ang = 0.707f;
				final float half = 8.0f * i;
				if (h < 13) {
					ang -= 0.05f;
					for (int angle = 0; angle < 360; angle += 90) {
					}
					if (h == 12) {
					}
					ang += 0.05f;
				}
				for (int angle = 0; angle < 360; angle += 90) {
					GlStateManager.pushMatrix();
					GlStateManager.rotate(angle, 0.0f, 1.0f, 0.0f);
					GlStateManager.translate(-0.5f, 0.0f, 0.0f);
					tess.startDrawingQuads();
					tess.setColorRGBA(255, 255, 255, 150);
					tess.addVertexWithUV(half - d1 * ang, h2, -d1 * ang, 0.0, 0.0);
					tess.addVertexWithUV(half - d2 * ang, h3, -d2 * ang, 0.0, 0.0);
					tess.addVertexWithUV(half + d2 * ang, h3, -d2 * ang, 0.0, 0.0);
					tess.addVertexWithUV(half + d1 * ang, h2, -d1 * ang, 0.0, 0.0);
					tess.draw();
					tess.startDrawingQuads();
					tess.addVertexWithUV(half + d1 * ang, h2, -d1 * ang, 0.0, 0.0);
					tess.addVertexWithUV(half + d2 * ang, h3, -d2 * ang, 0.0, 0.0);
					tess.addVertexWithUV(half - d2 * ang, h3, -d2 * ang, 0.0, 0.0);
					tess.addVertexWithUV(half - d1 * ang, h2, -d1 * ang, 0.0, 0.0);
					tess.draw();
					GlStateManager.popMatrix();
				}
			}
			GlStateManager.popMatrix();
			if (wasTessellating) {
				tess.startDrawingQuads();
			}
		}*/
	}
}
