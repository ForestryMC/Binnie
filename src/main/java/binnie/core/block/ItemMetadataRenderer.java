package binnie.core.block;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class ItemMetadataRenderer implements IItemRenderer {
	@Override
	public boolean handleRenderType(final ItemStack item, final IItemRenderer.ItemRenderType type) {
		return type == IItemRenderer.ItemRenderType.INVENTORY
			|| type == IItemRenderer.ItemRenderType.ENTITY
			|| type == IItemRenderer.ItemRenderType.EQUIPPED
			|| type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON;
	}

	@Override
	public boolean shouldUseRenderHelper(final IItemRenderer.ItemRenderType type, final ItemStack item, final IItemRenderer.ItemRendererHelper helper) {
		if (type == IItemRenderer.ItemRenderType.INVENTORY) {
			return helper == IItemRenderer.ItemRendererHelper.INVENTORY_BLOCK;
		}
		if (type == IItemRenderer.ItemRenderType.ENTITY) {
			return helper == IItemRenderer.ItemRendererHelper.ENTITY_BOBBING
				|| helper == IItemRenderer.ItemRendererHelper.ENTITY_ROTATION;
		}
		return (type == IItemRenderer.ItemRenderType.EQUIPPED || type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON)
			&& helper == IItemRenderer.ItemRendererHelper.EQUIPPED_BLOCK;
	}

	@Override
	public void renderItem(final IItemRenderer.ItemRenderType type, final ItemStack item, final Object... data) {
		GL11.glPushMatrix();
		final Block block = Block.getBlockFromItem(item.getItem());
		if (type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON) {
			GL11.glTranslated(0.5, 0.5, 0.5);
		}
		if (type == IItemRenderer.ItemRenderType.INVENTORY && block.getRenderBlockPass() != 0) {
			GL11.glAlphaFunc(516, 0.1f);
			GL11.glEnable(3042);
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		}
		GL11.glPushMatrix();
		((RenderBlocks) data[0]).renderBlockAsItem(block, TileEntityMetadata.getItemDamage(item), 1.0f);
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}
}
