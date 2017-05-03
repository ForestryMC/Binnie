package binnie.extratrees.alcohol.drink;

import binnie.extratrees.alcohol.ModuleAlcohol;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

// TODO unused class?
public class BlockDrinkRenderer implements ISimpleBlockRenderingHandler {
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
		// ignored
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		GL11.glPushMatrix();
		Tessellator.instance.setColorRGBA(255, 255, 255, 150);
		Tessellator tess = Tessellator.instance;
		renderer.renderFaceYPos(block, x, y, z, Blocks.dirt.getIcon(0, 0));
		float s = 0.0625f;

		for (int h = 0; h < 16; ++h) {
			float d1 = 8.0f * s;
			float h2 = h * s;
			tess.addVertexWithUV(x + 0.5f - d1, y + h2, z + 0.5f - d1, 0.0, 0.0);
			tess.addVertexWithUV(x + 0.5f - d1, y + h2, z + 0.5f - d1, 0.0, 0.0);
			tess.addVertexWithUV(x + 0.5f + d1, y + h2, z + 0.5f - d1, 0.0, 0.0);
			tess.addVertexWithUV(x + 0.5f + d1, y + h2, z + 0.5f - d1, 0.0, 0.0);
		}
		GL11.glPopMatrix();
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return false;
	}

	@Override
	public int getRenderId() {
		return ModuleAlcohol.drinkRendererID;
	}
}
