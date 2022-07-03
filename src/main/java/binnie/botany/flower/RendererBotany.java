package binnie.botany.flower;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class RendererBotany implements ISimpleBlockRenderingHandler {
    public static int renderID;
    public static int pass;

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        // ignored
    }

    @Override
    public boolean renderWorldBlock(
            IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z));
        double d1 = x;
        double d2 = y;
        double d3 = z;
        long i1 = x * 3129871 ^ z * 116129781L;
        i1 = i1 * i1 * 42317861L + i1 * 11L;
        d1 += ((i1 >> 16 & 0xFL) / 15.0f - 0.5) * 0.3;
        d3 += ((i1 >> 24 & 0xFL) / 15.0f - 0.5) * 0.3;

        for (int j = 0; j < 3; ++j) {
            RendererBotany.pass = j;
            int l = block.colorMultiplier(renderer.blockAccess, x, y, z);
            float f = (l >> 16 & 0xFF) / 255.0f;
            float f2 = (l >> 8 & 0xFF) / 255.0f;
            float f3 = (l & 0xFF) / 255.0f;

            if (EntityRenderer.anaglyphEnable) {
                f = (f * 30.0f + f2 * 59.0f + f3 * 11.0f) / 100.0f;
                f2 = (f * 30.0f + f2 * 70.0f) / 100.0f;
                f3 = (f * 30.0f + f3 * 70.0f) / 100.0f;
            }
            tessellator.setColorOpaque_F(f, f2, f3);
            IIcon iicon = block.getIcon(world, x, y, z, 0);
            renderer.drawCrossedSquares(iicon, d1, d2, d3, 1.0f);
        }
        return false;
    }

    @Override
    public boolean shouldRender3DInInventory(int i) {
        return false;
    }

    @Override
    public int getRenderId() {
        return RendererBotany.renderID;
    }
}
