package binnie.extratrees.block;

import binnie.extratrees.ExtraTrees;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class DoorBlockRenderer implements ISimpleBlockRenderingHandler {
    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        // ignored
    }

    @Override
    public boolean renderWorldBlock(
            IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        int c = block.colorMultiplier(renderer.blockAccess, x, y, z);
        float c2 = (c >> 16 & 0xFF) / 255.0f;
        float c3 = (c >> 8 & 0xFF) / 255.0f;
        float c4 = (c & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.instance;
        int l = renderer.blockAccess.getBlockMetadata(x, y, z);
        if ((l & 0x8) != 0x0) {
            if (renderer.blockAccess.getBlock(x, y - 1, z) != block) {
                return false;
            }
        } else if (renderer.blockAccess.getBlock(x, y + 1, z) != block) {
            return false;
        }

        float f = 0.5f;
        float f2 = 1.0f;
        float f3 = 0.8f;
        float f4 = 0.6f;

        int i1 = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z);
        tessellator.setBrightness(
                (renderer.renderMinY > 0.0) ? i1 : block.getMixedBrightnessForBlock(renderer.blockAccess, x, y - 1, z));
        tessellator.setColorOpaque_F(f * c2, f * c3, f * c4);
        renderer.renderFaceYNeg(block, x, y, z, renderer.getBlockIcon(block, renderer.blockAccess, x, y, z, 0));

        tessellator.setBrightness(
                (renderer.renderMaxY < 1.0) ? i1 : block.getMixedBrightnessForBlock(renderer.blockAccess, x, y + 1, z));
        tessellator.setColorOpaque_F(f2 * c2, f2 * c3, f2 * c4);
        renderer.renderFaceYPos(block, x, y, z, renderer.getBlockIcon(block, renderer.blockAccess, x, y, z, 1));

        tessellator.setBrightness(
                (renderer.renderMinZ > 0.0) ? i1 : block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z - 1));
        tessellator.setColorOpaque_F(f3 * c2, f3 * c3, f3 * c4);
        IIcon icon = renderer.getBlockIcon(block, renderer.blockAccess, x, y, z, 2);
        renderer.renderFaceZNeg(block, x, y, z, icon);

        renderer.flipTexture = false;
        tessellator.setBrightness(
                (renderer.renderMaxZ < 1.0) ? i1 : block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z + 1));
        tessellator.setColorOpaque_F(f3 * c2, f3 * c3, f3 * c4);
        icon = renderer.getBlockIcon(block, renderer.blockAccess, x, y, z, 3);
        renderer.renderFaceZPos(block, x, y, z, icon);

        renderer.flipTexture = false;
        tessellator.setBrightness(
                (renderer.renderMinX > 0.0) ? i1 : block.getMixedBrightnessForBlock(renderer.blockAccess, x - 1, y, z));
        tessellator.setColorOpaque_F(f4 * c2, f4 * c3, f4 * c4);
        icon = renderer.getBlockIcon(block, renderer.blockAccess, x, y, z, 4);
        renderer.renderFaceXNeg(block, x, y, z, icon);

        renderer.flipTexture = false;
        tessellator.setBrightness(
                (renderer.renderMaxX < 1.0) ? i1 : block.getMixedBrightnessForBlock(renderer.blockAccess, x + 1, y, z));
        tessellator.setColorOpaque_F(f4 * c2, f4 * c3, f4 * c4);
        icon = renderer.getBlockIcon(block, renderer.blockAccess, x, y, z, 5);
        renderer.renderFaceXPos(block, x, y, z, icon);
        renderer.flipTexture = false;
        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int i) {
        return false;
    }

    @Override
    public int getRenderId() {
        return ExtraTrees.doorRenderId;
    }
}
