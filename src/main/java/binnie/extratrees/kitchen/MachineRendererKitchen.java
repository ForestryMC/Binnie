package binnie.extratrees.kitchen;

import binnie.core.machines.Machine;
import binnie.core.resource.BinnieResource;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MachineRendererKitchen {
	public static MachineRendererKitchen instance = new MachineRendererKitchen();

	protected static int level = 0;
	protected BinnieResource texture;

	public void renderMachine(Machine machine, BinnieResource texture, double x, double y, double z, float var8, RenderBlocks renderer) {
		if (renderer != null && renderer.blockAccess != null) {
			Block block = machine.getPackage().getGroup().getBlock();
			float i = 0.0625f;
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

			IIcon icon = Blocks.dirt.getIcon(0, 0);
			Tessellator tess = Tessellator.instance;
			boolean wasTessellating = true;

			try {
				tess.draw();
			} catch (Exception e) {
				wasTessellating = false;
			}

			tess.startDrawingQuads();
			tess.draw();
			GL11.glPushMatrix();
			GL11.glTranslated(x + 0.5, y + 1.0, z + 0.5);
			RenderManager.instance.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
			float ux = icon.getMinU();
			float uy = icon.getMinV();
			float uw = icon.getMaxU();
			float uh = icon.getMaxV();
			int[] widths = {1, 1, 1, 1, 1, 1, 1, 1, 2, 4, 5, 6, 6, 6, 5, 4};

			for (int h = 0; h < 15; ++h) {
				float h2 = h * i;
				float h3 = h2 + i;
				float d1 = widths[h] * i;
				float d2 = widths[h + 1] * i;
				float ang = 0.707f;
				float half = 8.0f * i;
				if (h < 13) {
					ang -= 0.05f;
					for (int angle = 0; angle < 360; angle += 90) {
					}
					if (h == 12) {
					}
					ang += 0.05f;
				}

				for (int angle = 0; angle < 360; angle += 90) {
					GL11.glPushMatrix();
					GL11.glRotatef(angle, 0.0f, 1.0f, 0.0f);
					GL11.glTranslatef(-0.5f, 0.0f, 0.0f);
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
					GL11.glPopMatrix();
				}
			}
			GL11.glPopMatrix();
			if (wasTessellating) {
				tess.startDrawingQuads();
			}
		}
	}
}
