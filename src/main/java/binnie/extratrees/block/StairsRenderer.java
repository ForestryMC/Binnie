package binnie.extratrees.block;

public class StairsRenderer //implements ISimpleBlockRenderingHandler
{
//	@Override
//	public void renderInventoryBlock(final Block par1Block, final int metadata, final int modelID, final RenderBlocks renderer) {
//		final Tessellator var4 = Tessellator.instance;
//		for (int var5 = 0; var5 < 2; ++var5) {
//			if (var5 == 0) {
//				renderer.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 0.5);
//			}
//			if (var5 == 1) {
//				renderer.setRenderBounds(0.0, 0.0, 0.5, 1.0, 0.5, 1.0);
//			}
//			GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
//			var4.startDrawingQuads();
//			var4.setNormal(0.0f, -1.0f, 0.0f);
//			renderer.renderFaceYNeg(par1Block, 0.0, 0.0, 0.0, par1Block.getIcon(0, metadata));
//			var4.draw();
//			var4.startDrawingQuads();
//			var4.setNormal(0.0f, 1.0f, 0.0f);
//			renderer.renderFaceYPos(par1Block, 0.0, 0.0, 0.0, par1Block.getIcon(1, metadata));
//			var4.draw();
//			var4.startDrawingQuads();
//			var4.setNormal(0.0f, 0.0f, -1.0f);
//			renderer.renderFaceXPos(par1Block, 0.0, 0.0, 0.0, par1Block.getIcon(2, metadata));
//			var4.draw();
//			var4.startDrawingQuads();
//			var4.setNormal(0.0f, 0.0f, 1.0f);
//			renderer.renderFaceXNeg(par1Block, 0.0, 0.0, 0.0, par1Block.getIcon(3, metadata));
//			var4.draw();
//			var4.startDrawingQuads();
//			var4.setNormal(-1.0f, 0.0f, 0.0f);
//			renderer.renderFaceZNeg(par1Block, 0.0, 0.0, 0.0, par1Block.getIcon(4, metadata));
//			var4.draw();
//			var4.startDrawingQuads();
//			var4.setNormal(1.0f, 0.0f, 0.0f);
//			renderer.renderFaceZPos(par1Block, 0.0, 0.0, 0.0, par1Block.getIcon(5, metadata));
//			var4.draw();
//			GL11.glTranslatef(0.5f, 0.5f, 0.5f);
//		}
//	}
//
//	@Override
//	public boolean renderWorldBlock(final IBlockAccess world, final int par2, final int par3, final int par4, final Block block, final int modelId, final RenderBlocks renderer) {
//		final BlockETStairs blockStairs = (BlockETStairs) block;
//		blockStairs.func_150147_e(renderer.blockAccess, par2, par3, par4);
//		renderer.setRenderBoundsFromBlock(blockStairs);
//		renderer.renderStandardBlock(blockStairs, par2, par3, par4);
//		final boolean var5 = blockStairs.func_150145_f(renderer.blockAccess, par2, par3, par4);
//		renderer.setRenderBoundsFromBlock(blockStairs);
//		renderer.renderStandardBlock(blockStairs, par2, par3, par4);
//		if (var5 && blockStairs.func_150144_g(renderer.blockAccess, par2, par3, par4)) {
//			renderer.setRenderBoundsFromBlock(blockStairs);
//			renderer.renderStandardBlock(blockStairs, par2, par3, par4);
//		}
//		return true;
//	}
//
//	@Override
//	public boolean shouldRender3DInInventory(final int i) {
//		return true;
//	}
//
//	@Override
//	public int getRenderId() {
//		return ExtraTrees.stairsID;
//	}
}
