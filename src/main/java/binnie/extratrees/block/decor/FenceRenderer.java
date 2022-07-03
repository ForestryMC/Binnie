package binnie.extratrees.block.decor;

import binnie.core.block.TileEntityMetadata;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.block.WoodManager;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

public class FenceRenderer implements ISimpleBlockRenderingHandler {
    public static int layer;

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        Tessellator tess = Tessellator.instance;
        for (int i = 0; i < 5; ++i) {
            float thickness = 0.125f;
            FenceRenderer.layer = 0;
            if (i == 0) {
                block.setBlockBounds(0.5f - thickness, 0.0f, 0.0f, 0.5f + thickness, 1.0f, thickness * 2.0f);
            }
            if (i == 1) {
                block.setBlockBounds(0.5f - thickness, 0.0f, 1.0f - thickness * 2.0f, 0.5f + thickness, 1.0f, 1.0f);
            }

            float s = 0.0625f;
            FenceType fenceType =
                    (block == ExtraTrees.blockMultiFence) ? WoodManager.getFenceType(metadata) : new FenceType(0);
            boolean bottomBar = !fenceType.solid;
            float topBarMaxY = 1.0f - s;
            float topBarMinY = 1.0f - s * 3.0f;
            float bottomBarMaxY = 0.5f - s;
            float bottomBarMinY = 0.5f - s * 3.0f;
            if (fenceType.size == 2) {
                bottomBarMinY -= 4.0f * s;
                bottomBarMaxY -= 4.0f * s;
                topBarMinY -= 4.0f * s;
                topBarMaxY -= 4.0f * s;
            }

            if (fenceType.size == 1) {
                bottomBarMinY -= 4.0f * s;
                bottomBarMaxY -= 4.0f * s;
            }

            if (fenceType.solid) {
                topBarMinY = bottomBarMinY;
            }

            float minX = 0.5f - s;
            float maxX = 0.5f + s;
            float minZ = -s * 2.0f;
            float maxZ = 1.0f + s * 2.0f;
            if (i == 2) {
                block.setBlockBounds(minX, topBarMinY, minZ, maxX, topBarMaxY, maxZ);
                FenceRenderer.layer = 1;
            } else if (i == 3) {
                if (!bottomBar) {
                    continue;
                }
                block.setBlockBounds(minX, bottomBarMinY, minZ, maxX, bottomBarMaxY, maxZ);
                FenceRenderer.layer = 1;
            } else if (i == 4) {
                if (fenceType.embossed) {
                    minX -= s * 0.9f;
                    maxX += s * 0.9f;
                    minZ -= s;
                    maxZ += s;
                    float minY;
                    float maxY;

                    if (fenceType.size != 1 && !fenceType.solid) {
                        minY = bottomBarMinY + 2.0f * s;
                        maxY = topBarMaxY - 2.0f * s;
                    } else if (fenceType.size == 1 && fenceType.solid) {
                        minY = bottomBarMinY + 2.0f * s;
                        maxY = topBarMaxY - 2.0f * s;
                    } else {
                        minY = 0.5f - 2.0f * s;
                        maxY = 0.5f + 2.0f * s;
                    }
                    if (fenceType.solid && fenceType.size == 0) {
                        minY -= s;
                        maxY -= s;
                    }
                    if (fenceType.solid && fenceType.size == 2) {
                        minY += s;
                        maxY += s;
                    }
                    block.setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
                    FenceRenderer.layer = 0;
                } else {
                    if (fenceType.size != 1 || fenceType.solid) {
                        continue;
                    }
                    block.setBlockBounds(minX, 0.5f - s, minZ, maxX, 0.5f + s, maxZ);
                    FenceRenderer.layer = 1;
                }
            }

            renderer.setRenderBoundsFromBlock(block);
            GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
            tess.startDrawingQuads();
            tess.setNormal(0.0f, -1.0f, 0.0f);
            renderer.renderFaceYNeg(block, 0.0, 0.0, 0.0, block.getIcon(0, metadata));
            tess.draw();
            tess.startDrawingQuads();
            tess.setNormal(0.0f, 1.0f, 0.0f);
            renderer.renderFaceYPos(block, 0.0, 0.0, 0.0, block.getIcon(1, metadata));
            tess.draw();
            tess.startDrawingQuads();
            tess.setNormal(0.0f, 0.0f, -1.0f);
            renderer.renderFaceXPos(block, 0.0, 0.0, 0.0, block.getIcon(2, metadata));
            tess.draw();
            tess.startDrawingQuads();
            tess.setNormal(0.0f, 0.0f, 1.0f);
            renderer.renderFaceXNeg(block, 0.0, 0.0, 0.0, block.getIcon(3, metadata));
            tess.draw();
            tess.startDrawingQuads();
            tess.setNormal(-1.0f, 0.0f, 0.0f);
            renderer.renderFaceZNeg(block, 0.0, 0.0, 0.0, block.getIcon(4, metadata));
            tess.draw();
            tess.startDrawingQuads();
            tess.setNormal(1.0f, 0.0f, 0.0f);
            renderer.renderFaceZPos(block, 0.0, 0.0, 0.0, block.getIcon(5, metadata));
            tess.draw();
            GL11.glTranslatef(0.5f, 0.5f, 0.5f);
        }
        block.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        renderer.setRenderBoundsFromBlock(block);
    }

    @Override
    public boolean renderWorldBlock(
            IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        FenceRenderer.layer = 0;
        BlockFence blockFence = (BlockFence) block;
        float i = 0.0625f;
        FenceType fenceType = new FenceType(0);
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof TileEntityMetadata && block instanceof BlockMultiFence) {
            fenceType = WoodManager.getFenceType(((TileEntityMetadata) tile).getTileMetadata());
        }

        boolean rendered;
        float postWidth = 0.25f;
        float postHeight = 1.0f;
        float minPostPos = 0.5f - postWidth / 2.0f;
        float maxPostPos = 0.5f + postWidth / 2.0f;
        renderer.setRenderBounds(minPostPos, 0.0, minPostPos, maxPostPos, postHeight, maxPostPos);
        renderer.renderStandardBlock(block, x, y, z);
        rendered = true;
        boolean connectAnyX = false;
        boolean connectAnyZ = false;
        if (blockFence.canConnectFenceTo(renderer.blockAccess, x - 1, y, z)
                || blockFence.canConnectFenceTo(renderer.blockAccess, x + 1, y, z)) {
            connectAnyX = true;
        }
        if (blockFence.canConnectFenceTo(renderer.blockAccess, x, y, z - 1)
                || blockFence.canConnectFenceTo(renderer.blockAccess, x, y, z + 1)) {
            connectAnyZ = true;
        }

        boolean connectNegX = blockFence.canConnectFenceTo(renderer.blockAccess, x - 1, y, z);
        boolean connectPosX = blockFence.canConnectFenceTo(renderer.blockAccess, x + 1, y, z);
        boolean connectNegZ = blockFence.canConnectFenceTo(renderer.blockAccess, x, y, z - 1);
        boolean connectPosZ = blockFence.canConnectFenceTo(renderer.blockAccess, x, y, z + 1);
        if (!connectAnyX && !connectAnyZ) {
            connectAnyX = true;
        }

        minPostPos = 7.0f * i;
        maxPostPos = 9.0f * i;
        float barMinY = 12.0f * i;
        float barMaxY = 15.0f * i;
        float minX = connectNegX ? 0.0f : minPostPos;
        float maxX = connectPosX ? 1.0f : maxPostPos;
        float minZ = connectNegZ ? 0.0f : minPostPos;
        float maxZ = connectPosZ ? 1.0f : maxPostPos;
        boolean renderBottom = true;
        if (fenceType.size == 2) {
            barMaxY -= 5.0f * i;
            barMinY -= 5.0f * i;
        }
        if (fenceType.solid) {
            renderBottom = false;
            if (fenceType.size == 0) {
                barMinY = 6.0f * i;
            } else {
                barMinY = i;
            }
        }

        float totalMaxY = barMaxY;
        FenceRenderer.layer = 1;
        if (connectAnyX) {
            renderer.setRenderBounds(minX, barMinY, minPostPos, maxX, barMaxY, maxPostPos);
            renderer.renderStandardBlock(blockFence, x, y, z);
            rendered = true;
        }

        if (connectAnyZ) {
            renderer.setRenderBounds(minPostPos, barMinY, minZ, maxPostPos, barMaxY, maxZ);
            renderer.renderStandardBlock(blockFence, x, y, z);
            rendered = true;
        }

        if (renderBottom) {
            barMinY -= 6.0f * i;
            barMaxY -= 6.0f * i;
            if (fenceType.size == 1) {
                barMinY += i;
            }
            if (connectAnyX) {
                renderer.setRenderBounds(minX, barMinY, minPostPos, maxX, barMaxY, maxPostPos);
                renderer.renderStandardBlock(blockFence, x, y, z);
                rendered = true;
            }
            if (connectAnyZ) {
                renderer.setRenderBounds(minPostPos, barMinY, minZ, maxPostPos, barMaxY, maxZ);
                renderer.renderStandardBlock(blockFence, x, y, z);
                rendered = true;
            }
        }

        if (renderBottom && fenceType.size == 1) {
            barMinY -= 6.0f * i;
            barMaxY -= 6.0f * i;
            barMaxY += i;
            if (connectAnyX) {
                renderer.setRenderBounds(minX, barMinY, minPostPos, maxX, barMaxY, maxPostPos);
                renderer.renderStandardBlock(blockFence, x, y, z);
                rendered = true;
            }
            if (connectAnyZ) {
                renderer.setRenderBounds(minPostPos, barMinY, minZ, maxPostPos, barMaxY, maxZ);
                renderer.renderStandardBlock(blockFence, x, y, z);
                rendered = true;
            }
        }

        float totalMinY = barMinY;
        FenceRenderer.layer = 0;
        if (fenceType.embossed) {
            minPostPos -= (float) (i - 0.25 * i);
            maxPostPos += (float) (i - 0.25 * i);
            float minY = totalMinY + 2.0f * i;
            float maxY = totalMaxY - 2.0f * i;
            if (fenceType.size == 1 && !fenceType.solid) {
                minY = 6.0f * i;
                maxY = 10.0f * i;
            } else if (fenceType.size == 0 && fenceType.solid) {
                minY -= 4.0f * i;
                maxY -= 4.0f * i;
            } else if (fenceType.size == 2 && fenceType.solid) {
                minY += 4.0f * i;
                maxY += 4.0f * i;
            }

            if (connectAnyX) {
                renderer.setRenderBounds(minX, minY, minPostPos, maxX, maxY, maxPostPos);
                renderer.renderStandardBlock(block, x, y, z);
            }

            if (connectAnyZ) {
                renderer.setRenderBounds(minPostPos, minY, minZ, maxPostPos, maxY, maxZ);
                renderer.renderStandardBlock(block, x, y, z);
            }
        }
        blockFence.setBlockBoundsBasedOnState(renderer.blockAccess, x, y, z);
        return rendered;
    }

    @Override
    public boolean shouldRender3DInInventory(int i) {
        return true;
    }

    @Override
    public int getRenderId() {
        return ExtraTrees.fenceID;
    }
}
