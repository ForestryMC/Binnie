package binnie.extratrees.block;

public class DoorBlockRenderer //implements ISimpleBlockRenderingHandler
{
	//	@Override
	//	public void renderInventoryBlock(final Block block, final int metadata, final int modelID, final RenderBlocks renderer) {
	//	}
	//
	//	@Override
	//	public boolean renderWorldBlock(final IBlockAccess world, final int par2, final int par3, final int par4, final Block par1Block, final int modelId, final RenderBlocks renderer) {
	//		final int c = par1Block.colorMultiplier(renderer.blockAccess, par2, par3, par4);
	//		final float c2 = (c >> 16 & 0xFF) / 255.0f;
	//		final float c3 = (c >> 8 & 0xFF) / 255.0f;
	//		final float c4 = (c & 0xFF) / 255.0f;
	//		final Tessellator tessellator = Tessellator.instance;
	//		final int l = renderer.blockAccess.getBlockMetadata(par2, par3, par4);
	//		if ((l & 0x8) != 0x0) {
	//			if (renderer.blockAccess.getBlock(par2, par3 - 1, par4) != par1Block) {
	//				return false;
	//			}
	//		}
	//		else if (renderer.blockAccess.getBlock(par2, par3 + 1, par4) != par1Block) {
	//			return false;
	//		}
	//		boolean flag = false;
	//		final float f = 0.5f;
	//		final float f2 = 1.0f;
	//		final float f3 = 0.8f;
	//		final float f4 = 0.6f;
	//		final int i1 = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3, par4);
	//		tessellator.setBrightness((renderer.renderMinY > 0.0) ? i1 : par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 - 1, par4));
	//		tessellator.setColorOpaque_F(f * c2, f * c3, f * c4);
	//		renderer.renderFaceYNeg(par1Block, par2, par3, par4, renderer.getBlockIcon(par1Block, renderer.blockAccess, par2, par3, par4, 0));
	//		flag = true;
	//		tessellator.setBrightness((renderer.renderMaxY < 1.0) ? i1 : par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 + 1, par4));
	//		tessellator.setColorOpaque_F(f2 * c2, f2 * c3, f2 * c4);
	//		renderer.renderFaceYPos(par1Block, par2, par3, par4, renderer.getBlockIcon(par1Block, renderer.blockAccess, par2, par3, par4, 1));
	//		flag = true;
	//		tessellator.setBrightness((renderer.renderMinZ > 0.0) ? i1 : par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3, par4 - 1));
	//		tessellator.setColorOpaque_F(f3 * c2, f3 * c3, f3 * c4);
	//		IIcon icon = renderer.getBlockIcon(par1Block, renderer.blockAccess, par2, par3, par4, 2);
	//		renderer.renderFaceZNeg(par1Block, par2, par3, par4, icon);
	//		flag = true;
	//		renderer.flipTexture = false;
	//		tessellator.setBrightness((renderer.renderMaxZ < 1.0) ? i1 : par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3, par4 + 1));
	//		tessellator.setColorOpaque_F(f3 * c2, f3 * c3, f3 * c4);
	//		icon = renderer.getBlockIcon(par1Block, renderer.blockAccess, par2, par3, par4, 3);
	//		renderer.renderFaceZPos(par1Block, par2, par3, par4, icon);
	//		flag = true;
	//		renderer.flipTexture = false;
	//		tessellator.setBrightness((renderer.renderMinX > 0.0) ? i1 : par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 - 1, par3, par4));
	//		tessellator.setColorOpaque_F(f4 * c2, f4 * c3, f4 * c4);
	//		icon = renderer.getBlockIcon(par1Block, renderer.blockAccess, par2, par3, par4, 4);
	//		renderer.renderFaceXNeg(par1Block, par2, par3, par4, icon);
	//		flag = true;
	//		renderer.flipTexture = false;
	//		tessellator.setBrightness((renderer.renderMaxX < 1.0) ? i1 : par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 + 1, par3, par4));
	//		tessellator.setColorOpaque_F(f4 * c2, f4 * c3, f4 * c4);
	//		icon = renderer.getBlockIcon(par1Block, renderer.blockAccess, par2, par3, par4, 5);
	//		renderer.renderFaceXPos(par1Block, par2, par3, par4, icon);
	//		flag = true;
	//		renderer.flipTexture = false;
	//		return flag;
	//	}
	//
	//	@Override
	//	public boolean shouldRender3DInInventory(final int i) {
	//		return false;
	//	}
	//
	//	@Override
	//	public int getRenderId() {
	//		return ExtraTrees.doorRenderId;
	//	}
}
