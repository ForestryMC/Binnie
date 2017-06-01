package binnie.core.block;

public class ItemMetadataRenderer //implements IItemRenderer
{
	/*@Override
	public boolean handleRenderType(final ItemStack item, final IItemRenderer.ItemRenderType type) {
		return type == IItemRenderer.ItemRenderType.INVENTORY || type == IItemRenderer.ItemRenderType.ENTITY || type == IItemRenderer.ItemRenderType.EQUIPPED || type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON;
	}

	@Override
	public boolean shouldUseRenderHelper(final IItemRenderer.ItemRenderType type, final ItemStack item, final IItemRenderer.ItemRendererHelper helper) {
		if (type == IItemRenderer.ItemRenderType.INVENTORY) {
			return helper == IItemRenderer.ItemRendererHelper.INVENTORY_BLOCK;
		}
		if (type == IItemRenderer.ItemRenderType.ENTITY) {
			return helper == IItemRenderer.ItemRendererHelper.ENTITY_BOBBING || helper == IItemRenderer.ItemRendererHelper.ENTITY_ROTATION;
		}
		return (type == IItemRenderer.ItemRenderType.EQUIPPED || type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON) && helper == IItemRenderer.ItemRendererHelper.EQUIPPED_BLOCK;
	}

	@Override
	public void renderItem(final IItemRenderer.ItemRenderType type, final ItemStack item, final Object... data) {
		GlStateManager.pushMatrix();
		final Block block = Block.getBlockFromItem(item.getItem());
		if (type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON) {
			GlStateManager.translate(0.5, 0.5, 0.5);
		}
		if (type == IItemRenderer.ItemRenderType.INVENTORY && block.getRenderBlockPass() != 0) {
			GL11.glAlphaFunc(516, 0.1f);
			GlStateManager.enableBlend();
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		}
		GlStateManager.pushMatrix();
		((RenderBlocks) data[0]).renderBlockAsItem(block, TileEntityMetadata.getItemDamage(item), 1.0f);
		GlStateManager.popMatrix();
		GlStateManager.popMatrix();
	}*/
}
