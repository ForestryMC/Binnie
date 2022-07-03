package binnie.extratrees.block;

import binnie.extratrees.ExtraTrees;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

public class StairsRenderer implements ISimpleBlockRenderingHandler {
    @Override
    public void renderInventoryBlock(Block par1Block, int metadata, int modelID, RenderBlocks renderer) {
        Tessellator var4 = Tessellator.instance;
        for (int var5 = 0; var5 < 2; ++var5) {
            if (var5 == 0) {
                renderer.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 0.5);
            }
            if (var5 == 1) {
                renderer.setRenderBounds(0.0, 0.0, 0.5, 1.0, 0.5, 1.0);
            }

            GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
            var4.startDrawingQuads();
            var4.setNormal(0.0f, -1.0f, 0.0f);
            renderer.renderFaceYNeg(par1Block, 0.0, 0.0, 0.0, par1Block.getIcon(0, metadata));
            var4.draw();
            var4.startDrawingQuads();
            var4.setNormal(0.0f, 1.0f, 0.0f);
            renderer.renderFaceYPos(par1Block, 0.0, 0.0, 0.0, par1Block.getIcon(1, metadata));
            var4.draw();
            var4.startDrawingQuads();
            var4.setNormal(0.0f, 0.0f, -1.0f);
            renderer.renderFaceXPos(par1Block, 0.0, 0.0, 0.0, par1Block.getIcon(2, metadata));
            var4.draw();
            var4.startDrawingQuads();
            var4.setNormal(0.0f, 0.0f, 1.0f);
            renderer.renderFaceXNeg(par1Block, 0.0, 0.0, 0.0, par1Block.getIcon(3, metadata));
            var4.draw();
            var4.startDrawingQuads();
            var4.setNormal(-1.0f, 0.0f, 0.0f);
            renderer.renderFaceZNeg(par1Block, 0.0, 0.0, 0.0, par1Block.getIcon(4, metadata));
            var4.draw();
            var4.startDrawingQuads();
            var4.setNormal(1.0f, 0.0f, 0.0f);
            renderer.renderFaceZPos(par1Block, 0.0, 0.0, 0.0, par1Block.getIcon(5, metadata));
            var4.draw();
            GL11.glTranslatef(0.5f, 0.5f, 0.5f);
        }
    }

    @Override
    public boolean renderWorldBlock(
            IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        BlockETStairs blockStairs = (BlockETStairs) block;
        blockStairs.func_150147_e(renderer.blockAccess, x, y, z);
        renderer.setRenderBoundsFromBlock(blockStairs);
        renderer.renderStandardBlock(blockStairs, x, y, z);
        boolean var5 = blockStairs.func_150145_f(renderer.blockAccess, x, y, z);
        renderer.setRenderBoundsFromBlock(blockStairs);
        renderer.renderStandardBlock(blockStairs, x, y, z);
        if (var5 && blockStairs.func_150144_g(renderer.blockAccess, x, y, z)) {
            renderer.setRenderBoundsFromBlock(blockStairs);
            renderer.renderStandardBlock(blockStairs, x, y, z);
        }
        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int i) {
        return true;
    }

    @Override
    public int getRenderId() {
        return ExtraTrees.stairsID;
    }
}
