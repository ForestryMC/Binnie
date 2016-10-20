package binnie.extratrees.block.decor;

//TODO RENDERING
public class FenceRenderer //implements ISimpleBlockRenderingHandler
{
//	public static int layer;
//
//	@Override
//	public void renderInventoryBlock(final Block block, final int metadata, final int modelID, final RenderBlocks renderer) {
//		final Tessellator tess = Tessellator.instance;
//		for (int i = 0; i < 5; ++i) {
//			final float thickness = 0.125f;
//			FenceRenderer.layer = 0;
//			if (i == 0) {
//				block.setBlockBounds(0.5f - thickness, 0.0f, 0.0f, 0.5f + thickness, 1.0f, thickness * 2.0f);
//			}
//			if (i == 1) {
//				block.setBlockBounds(0.5f - thickness, 0.0f, 1.0f - thickness * 2.0f, 0.5f + thickness, 1.0f, 1.0f);
//			}
//			final float s = 0.0625f;
//			final FenceType fenceType = (block == ExtraTrees.blockMultiFence) ? WoodManager.getFenceType(metadata) : new FenceType(0);
//			final boolean bottomBar = !fenceType.solid;
//			float topBarMaxY = 1.0f - s;
//			float topBarMinY = 1.0f - s * 3.0f;
//			float bottomBarMaxY = 0.5f - s;
//			float bottomBarMinY = 0.5f - s * 3.0f;
//			if (fenceType.size == 2) {
//				bottomBarMinY -= 4.0f * s;
//				bottomBarMaxY -= 4.0f * s;
//				topBarMinY -= 4.0f * s;
//				topBarMaxY -= 4.0f * s;
//			}
//			if (fenceType.size == 1) {
//				bottomBarMinY -= 4.0f * s;
//				bottomBarMaxY -= 4.0f * s;
//			}
//			if (fenceType.solid) {
//				topBarMinY = bottomBarMinY;
//			}
//			float minX = 0.5f - s;
//			float maxX = 0.5f + s;
//			float minZ = -s * 2.0f;
//			float maxZ = 1.0f + s * 2.0f;
//			if (i == 2) {
//				block.setBlockBounds(minX, topBarMinY, minZ, maxX, topBarMaxY, maxZ);
//				FenceRenderer.layer = 1;
//			}
//			if (i == 3) {
//				if (!bottomBar) {
//					continue;
//				}
//				block.setBlockBounds(minX, bottomBarMinY, minZ, maxX, bottomBarMaxY, maxZ);
//				FenceRenderer.layer = 1;
//			}
//			if (i == 4) {
//				if (fenceType.embossed) {
//					minX -= s * 0.9f;
//					maxX += s * 0.9f;
//					minZ -= s;
//					maxZ += s;
//					float minY = 0.0f;
//					float maxY = 1.0f;
//					if (fenceType.size != 1 && !fenceType.solid) {
//						minY = bottomBarMinY + 2.0f * s;
//						maxY = topBarMaxY - 2.0f * s;
//					}
//					else if (fenceType.size == 1 && fenceType.solid) {
//						minY = bottomBarMinY + 2.0f * s;
//						maxY = topBarMaxY - 2.0f * s;
//					}
//					else {
//						minY = 0.5f - 2.0f * s;
//						maxY = 0.5f + 2.0f * s;
//					}
//					if (fenceType.solid && fenceType.size == 0) {
//						minY -= s;
//						maxY -= s;
//					}
//					if (fenceType.solid && fenceType.size == 2) {
//						minY += s;
//						maxY += s;
//					}
//					block.setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
//					FenceRenderer.layer = 0;
//				}
//				else {
//					if (fenceType.size != 1 || fenceType.solid) {
//						continue;
//					}
//					block.setBlockBounds(minX, 0.5f - s, minZ, maxX, 0.5f + s, maxZ);
//					FenceRenderer.layer = 1;
//				}
//			}
//			renderer.setRenderBoundsFromBlock(block);
//			GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
//			tess.startDrawingQuads();
//			tess.setNormal(0.0f, -1.0f, 0.0f);
//			renderer.renderFaceYNeg(block, 0.0, 0.0, 0.0, block.getIcon(0, metadata));
//			tess.draw();
//			tess.startDrawingQuads();
//			tess.setNormal(0.0f, 1.0f, 0.0f);
//			renderer.renderFaceYPos(block, 0.0, 0.0, 0.0, block.getIcon(1, metadata));
//			tess.draw();
//			tess.startDrawingQuads();
//			tess.setNormal(0.0f, 0.0f, -1.0f);
//			renderer.renderFaceXPos(block, 0.0, 0.0, 0.0, block.getIcon(2, metadata));
//			tess.draw();
//			tess.startDrawingQuads();
//			tess.setNormal(0.0f, 0.0f, 1.0f);
//			renderer.renderFaceXNeg(block, 0.0, 0.0, 0.0, block.getIcon(3, metadata));
//			tess.draw();
//			tess.startDrawingQuads();
//			tess.setNormal(-1.0f, 0.0f, 0.0f);
//			renderer.renderFaceZNeg(block, 0.0, 0.0, 0.0, block.getIcon(4, metadata));
//			tess.draw();
//			tess.startDrawingQuads();
//			tess.setNormal(1.0f, 0.0f, 0.0f);
//			renderer.renderFaceZPos(block, 0.0, 0.0, 0.0, block.getIcon(5, metadata));
//			tess.draw();
//			GL11.glTranslatef(0.5f, 0.5f, 0.5f);
//		}
//		block.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
//		renderer.setRenderBoundsFromBlock(block);
//	}
//
//	@Override
//	public boolean renderWorldBlock(final IBlockAccess world, final int par2, final int par3, final int par4, final Block block, final int modelId, final RenderBlocks renderer) {
//		FenceRenderer.layer = 0;
//		final BlockFence blockFence = (BlockFence) block;
//		final float i = 0.0625f;
//		FenceType fenceType = new FenceType(0);
//		final TileEntity tile = world.getTileEntity(par2, par3, par4);
//		if (tile != null && tile instanceof TileEntityMetadata && block instanceof BlockMultiFence) {
//			fenceType = WoodManager.getFenceType(((TileEntityMetadata) tile).getTileMetadata());
//		}
//		boolean rendered = false;
//		final float postWidth = 0.25f;
//		final float postHeight = 1.0f;
//		float minPostPos = 0.5f - postWidth / 2.0f;
//		float maxPostPos = 0.5f + postWidth / 2.0f;
//		renderer.setRenderBounds(minPostPos, 0.0, minPostPos, maxPostPos, postHeight, maxPostPos);
//		renderer.renderStandardBlock(block, par2, par3, par4);
//		rendered = true;
//		boolean connectAnyX = false;
//		boolean connectAnyZ = false;
//		if (blockFence.canConnectFenceTo(renderer.blockAccess, par2 - 1, par3, par4) || blockFence.canConnectFenceTo(renderer.blockAccess, par2 + 1, par3, par4)) {
//			connectAnyX = true;
//		}
//		if (blockFence.canConnectFenceTo(renderer.blockAccess, par2, par3, par4 - 1) || blockFence.canConnectFenceTo(renderer.blockAccess, par2, par3, par4 + 1)) {
//			connectAnyZ = true;
//		}
//		final boolean connectNegX = blockFence.canConnectFenceTo(renderer.blockAccess, par2 - 1, par3, par4);
//		final boolean connectPosX = blockFence.canConnectFenceTo(renderer.blockAccess, par2 + 1, par3, par4);
//		final boolean connectNegZ = blockFence.canConnectFenceTo(renderer.blockAccess, par2, par3, par4 - 1);
//		final boolean connectPosZ = blockFence.canConnectFenceTo(renderer.blockAccess, par2, par3, par4 + 1);
//		if (!connectAnyX && !connectAnyZ) {
//			connectAnyX = true;
//		}
//		minPostPos = 7.0f * i;
//		maxPostPos = 9.0f * i;
//		float barMinY = 12.0f * i;
//		float barMaxY = 15.0f * i;
//		final float minX = connectNegX ? 0.0f : minPostPos;
//		final float maxX = connectPosX ? 1.0f : maxPostPos;
//		final float minZ = connectNegZ ? 0.0f : minPostPos;
//		final float maxZ = connectPosZ ? 1.0f : maxPostPos;
//		boolean renderBottom = true;
//		if (fenceType.size == 2) {
//			barMaxY -= 5.0f * i;
//			barMinY -= 5.0f * i;
//		}
//		if (fenceType.solid) {
//			renderBottom = false;
//			if (fenceType.size == 0) {
//				barMinY = 6.0f * i;
//			}
//			else {
//				barMinY = i;
//			}
//		}
//		final float totalMaxY = barMaxY;
//		FenceRenderer.layer = 1;
//		if (connectAnyX) {
//			renderer.setRenderBounds(minX, barMinY, minPostPos, maxX, barMaxY, maxPostPos);
//			renderer.renderStandardBlock(blockFence, par2, par3, par4);
//			rendered = true;
//		}
//		if (connectAnyZ) {
//			renderer.setRenderBounds(minPostPos, barMinY, minZ, maxPostPos, barMaxY, maxZ);
//			renderer.renderStandardBlock(blockFence, par2, par3, par4);
//			rendered = true;
//		}
//		if (renderBottom) {
//			barMinY -= 6.0f * i;
//			barMaxY -= 6.0f * i;
//			if (fenceType.size == 1) {
//				barMinY += i;
//			}
//			if (connectAnyX) {
//				renderer.setRenderBounds(minX, barMinY, minPostPos, maxX, barMaxY, maxPostPos);
//				renderer.renderStandardBlock(blockFence, par2, par3, par4);
//				rendered = true;
//			}
//			if (connectAnyZ) {
//				renderer.setRenderBounds(minPostPos, barMinY, minZ, maxPostPos, barMaxY, maxZ);
//				renderer.renderStandardBlock(blockFence, par2, par3, par4);
//				rendered = true;
//			}
//		}
//		if (renderBottom && fenceType.size == 1) {
//			barMinY -= 6.0f * i;
//			barMaxY -= 6.0f * i;
//			barMaxY += i;
//			if (connectAnyX) {
//				renderer.setRenderBounds(minX, barMinY, minPostPos, maxX, barMaxY, maxPostPos);
//				renderer.renderStandardBlock(blockFence, par2, par3, par4);
//				rendered = true;
//			}
//			if (connectAnyZ) {
//				renderer.setRenderBounds(minPostPos, barMinY, minZ, maxPostPos, barMaxY, maxZ);
//				renderer.renderStandardBlock(blockFence, par2, par3, par4);
//				rendered = true;
//			}
//		}
//		final float totalMinY = barMinY;
//		FenceRenderer.layer = 0;
//		if (fenceType.embossed) {
//			minPostPos -= (float) (i - 0.25 * i);
//			maxPostPos += (float) (i - 0.25 * i);
//			float minY = totalMinY + 2.0f * i;
//			float maxY = totalMaxY - 2.0f * i;
//			if (fenceType.size == 1 && !fenceType.solid) {
//				minY = 6.0f * i;
//				maxY = 10.0f * i;
//			}
//			else if (fenceType.size == 0 && fenceType.solid) {
//				minY -= 4.0f * i;
//				maxY -= 4.0f * i;
//			}
//			else if (fenceType.size == 2 && fenceType.solid) {
//				minY += 4.0f * i;
//				maxY += 4.0f * i;
//			}
//			if (connectAnyX) {
//				renderer.setRenderBounds(minX, minY, minPostPos, maxX, maxY, maxPostPos);
//				renderer.renderStandardBlock(block, par2, par3, par4);
//			}
//			if (connectAnyZ) {
//				renderer.setRenderBounds(minPostPos, minY, minZ, maxPostPos, maxY, maxZ);
//				renderer.renderStandardBlock(block, par2, par3, par4);
//			}
//		}
//		blockFence.setBlockBoundsBasedOnState(renderer.blockAccess, par2, par3, par4);
//		return rendered;
//	}
//
//	@Override
//	public boolean shouldRender3DInInventory(final int i) {
//		return true;
//	}
//
//	@Override
//	public int getRenderId() {
//		return ExtraTrees.fenceID;
//	}
}
