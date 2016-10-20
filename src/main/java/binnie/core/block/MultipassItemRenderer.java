package binnie.core.block;

public class MultipassItemRenderer// implements IItemRenderer
{
//	private void render(final RenderBlocks renderer, final ItemStack item, final float f, final float g, final float h) {
//		GL11.glTranslatef(f, g, h);
//		final Block block = ((ItemBlock) item.getItem()).field_150939_a;
//		GL11.glEnable(3008);
//		if (block.getRenderBlockPass() != 0) {
//			GL11.glAlphaFunc(516, 0.1f);
//			GL11.glEnable(3042);
//			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
//		}
//		else {
//			GL11.glAlphaFunc(516, 0.5f);
//			GL11.glDisable(3042);
//		}
//		MultipassBlockRenderer.instance.renderInventoryBlock(block, TileEntityMetadata.getItemDamage(item), 0, renderer);
//		if (block.getRenderBlockPass() == 0) {
//			GL11.glAlphaFunc(516, 0.1f);
//		}
//		GL11.glTranslatef(-f, -g, -h);
//	}
//
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
//		case EQUIPPED_FIRST_PERSON: {
//			return true;
//		}
//		default: {
//			return false;
//		}
//		}
//	}
//
//	@Override
//	public boolean shouldUseRenderHelper(final IItemRenderer.ItemRenderType type, final ItemStack item, final IItemRenderer.ItemRendererHelper helper) {
//		return true;
//	}
//
//	@Override
//	public void renderItem(final IItemRenderer.ItemRenderType type, final ItemStack item, final Object... data) {
//		switch (type) {
//		case ENTITY: {
//			this.render((RenderBlocks) data[0], item, 0.0f, 0.0f, 0.0f);
//			break;
//		}
//		case EQUIPPED:
//		case EQUIPPED_FIRST_PERSON: {
//			this.render((RenderBlocks) data[0], item, 0.5f, 0.5f, 0.5f);
//			break;
//		}
//		case INVENTORY: {
//			this.render((RenderBlocks) data[0], item, 0.0f, 0.0f, 0.0f);
//			break;
//		}
//		}
//	}
}
