package binnie.extratrees.block;

public class GateItemRenderer //implements IItemRenderer
{
//	private void renderStairBlock(final RenderBlocks renderBlocks, final ItemStack item, final float f, final float g, final float h) {
//		final Tessellator tessellator = Tessellator.instance;
//		final Block block = ((ItemBlock) item.getItem()).field_150939_a;
//		final IIcon textureIndex = WoodManager.getPlankType(item.getItemDamage()).getIcon();
//		for (int k = 0; k < 3; ++k) {
//			float f2 = 0.0625f;
//			if (k == 0) {
//				renderBlocks.setRenderBounds(0.5f - f2, 0.30000001192092896, 0.0, 0.5f + f2, 1.0, f2 * 2.0f);
//			}
//			if (k == 1) {
//				renderBlocks.setRenderBounds(0.5f - f2, 0.30000001192092896, 1.0f - f2 * 2.0f, 0.5f + f2, 1.0, 1.0);
//			}
//			f2 = 0.0625f;
//			if (k == 2) {
//				renderBlocks.setRenderBounds(0.5f - f2, 0.5, 0.0, 0.5f + f2, 1.0f - f2, 1.0);
//			}
//			GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
//			tessellator.startDrawingQuads();
//			tessellator.setNormal(0.0f, -1.0f, 0.0f);
//			renderBlocks.renderFaceYNeg(block, 0.0, 0.0, 0.0, renderBlocks.getBlockIconFromSideAndMetadata(block, 0, item.getItemDamage()));
//			tessellator.draw();
//			tessellator.startDrawingQuads();
//			tessellator.setNormal(0.0f, 1.0f, 0.0f);
//			renderBlocks.renderFaceYPos(block, 0.0, 0.0, 0.0, renderBlocks.getBlockIconFromSideAndMetadata(block, 1, item.getItemDamage()));
//			tessellator.draw();
//			tessellator.startDrawingQuads();
//			tessellator.setNormal(0.0f, 0.0f, -1.0f);
//			renderBlocks.renderFaceZNeg(block, 0.0, 0.0, 0.0, renderBlocks.getBlockIconFromSideAndMetadata(block, 2, item.getItemDamage()));
//			tessellator.draw();
//			tessellator.startDrawingQuads();
//			tessellator.setNormal(0.0f, 0.0f, 1.0f);
//			renderBlocks.renderFaceZPos(block, 0.0, 0.0, 0.0, renderBlocks.getBlockIconFromSideAndMetadata(block, 3, item.getItemDamage()));
//			tessellator.draw();
//			tessellator.startDrawingQuads();
//			tessellator.setNormal(-1.0f, 0.0f, 0.0f);
//			renderBlocks.renderFaceXNeg(block, 0.0, 0.0, 0.0, renderBlocks.getBlockIconFromSideAndMetadata(block, 4, item.getItemDamage()));
//			tessellator.draw();
//			tessellator.startDrawingQuads();
//			tessellator.setNormal(1.0f, 0.0f, 0.0f);
//			renderBlocks.renderFaceXPos(block, 0.0, 0.0, 0.0, renderBlocks.getBlockIconFromSideAndMetadata(block, 5, item.getItemDamage()));
//			tessellator.draw();
//			GL11.glTranslatef(0.5f, 0.5f, 0.5f);
//		}
//	}

//	@Override
//	public boolean handleRenderType(final ItemStack item, final IItemRenderer.ItemRenderType type) {
//		switch (type) {
//		case ENTITY: {
//			return true;
//		}
//		case EQUIPPED: {
//			return true;
//		}
//		case INVENTORY: {
//			return true;
//		}
//		default: {
//			return false;
//		}
//		}
//	}

//	@Override
//	public boolean shouldUseRenderHelper(final IItemRenderer.ItemRenderType type, final ItemStack item, final IItemRenderer.ItemRendererHelper helper) {
//		return true;
//	}
//
//	@Override
//	public void renderItem(final IItemRenderer.ItemRenderType type, final ItemStack item, final Object... data) {
//		switch (type) {
//		case ENTITY: {
//			this.renderStairBlock((RenderBlocks) data[0], item, -0.5f, -0.5f, -0.5f);
//			break;
//		}
//		case EQUIPPED: {
//			this.renderStairBlock((RenderBlocks) data[0], item, 0.0f, 0.0f, 0.0f);
//			break;
//		}
//		case INVENTORY: {
//			this.renderStairBlock((RenderBlocks) data[0], item, -0.5f, -0.5f, -0.5f);
//			break;
//		}
//		}
//	}
}
