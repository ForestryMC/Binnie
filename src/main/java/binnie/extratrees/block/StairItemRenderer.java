package binnie.extratrees.block;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class StairItemRenderer implements IItemRenderer {
    private void renderStairBlock(RenderBlocks renderBlocks, ItemStack item, float f, float g, float h) {
        Tessellator tessellator = Tessellator.instance;
        Block block = ((ItemBlock) item.getItem()).field_150939_a;
        IIcon textureIndex = PlankType.ExtraTreePlanks.values()[item.getItemDamage()].getIcon();

        for (int i = 0; i < 2; ++i) {
            if (i == 0) {
                renderBlocks.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 0.5);
            }
            if (i == 1) {
                renderBlocks.setRenderBounds(0.0, 0.0, 0.5, 1.0, 0.5, 1.0);
            }

            GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0f, -1.0f, 0.0f);
            renderBlocks.renderFaceYNeg(block, 0.0, 0.0, 0.0, textureIndex);
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0f, 1.0f, 0.0f);
            renderBlocks.renderFaceYPos(block, 0.0, 0.0, 0.0, textureIndex);
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0f, 0.0f, -1.0f);
            renderBlocks.renderFaceXNeg(block, 0.0, 0.0, 0.0, textureIndex);
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0f, 0.0f, 1.0f);
            renderBlocks.renderFaceXPos(block, 0.0, 0.0, 0.0, textureIndex);
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(-1.0f, 0.0f, 0.0f);
            renderBlocks.renderFaceZNeg(block, 0.0, 0.0, 0.0, textureIndex);
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(1.0f, 0.0f, 0.0f);
            renderBlocks.renderFaceZPos(block, 0.0, 0.0, 0.0, textureIndex);
            tessellator.draw();
            GL11.glTranslatef(0.5f, 0.5f, 0.5f);
        }
    }

    @Override
    public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type) {
        switch (type) {
            case ENTITY:
            case EQUIPPED:
            case INVENTORY:
                return true;
        }
        return false;
    }

    @Override
    public boolean shouldUseRenderHelper(
            IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper) {
        return true;
    }

    @Override
    public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data) {
        switch (type) {
            case ENTITY:
                renderStairBlock((RenderBlocks) data[0], item, -0.5f, -0.5f, -0.5f);
                break;

            case EQUIPPED:
                renderStairBlock((RenderBlocks) data[0], item, 0.0f, 0.0f, 0.0f);
                break;

            case INVENTORY:
                renderStairBlock((RenderBlocks) data[0], item, -0.5f, -0.5f, -0.5f);
                break;
        }
    }
}
