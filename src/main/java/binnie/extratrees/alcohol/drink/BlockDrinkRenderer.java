package binnie.extratrees.alcohol.drink;

//TODO RENDERING
public class BlockDrinkRenderer //implements ISimpleBlockRenderingHandler
{
//	@Override
//	public void renderInventoryBlock(final Block block, final int metadata, final int modelId, final RenderBlocks renderer) {
//	}
//
//	@Override
//	public boolean renderWorldBlock(final IBlockAccess world, final int x, final int y, final int z, final Block block, final int modelId, final RenderBlocks renderer) {
//		GL11.glPushMatrix();
//		Tessellator.instance.setColorRGBA(255, 255, 255, 150);
//		final Tessellator tess = Tessellator.instance;
//		renderer.renderFaceYPos(block, x, y, z, Blocks.dirt.getIcon(0, 0));
//		final float s = 0.0625f;
//		for (int h = 0; h < 16; ++h) {
//			final float d1 = 8.0f * s;
//			final float d2 = 8.0f * s;
//			final float h2 = h * s;
//			final float h3 = h * s;
//			tess.addVertexWithUV(x + 0.5f - d1, y + h2, z + 0.5f - d1, 0.0, 0.0);
//			tess.addVertexWithUV(x + 0.5f - d1, y + h3, z + 0.5f - d1, 0.0, 0.0);
//			tess.addVertexWithUV(x + 0.5f + d1, y + h3, z + 0.5f - d1, 0.0, 0.0);
//			tess.addVertexWithUV(x + 0.5f + d1, y + h2, z + 0.5f - d1, 0.0, 0.0);
//		}
//		GL11.glPopMatrix();
//		return false;
//	}
//
//	@Override
//	public boolean shouldRender3DInInventory(final int modelId) {
//		return false;
//	}
//
//	@Override
//	public int getRenderId() {
//		return ModuleAlcohol.drinkRendererID;
//	}
}
