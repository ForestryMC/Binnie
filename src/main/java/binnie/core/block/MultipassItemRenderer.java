package binnie.core.block;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class MultipassItemRenderer implements IItemRenderer {
	private void render(RenderBlocks renderer, ItemStack item, float f, float g, float h) {
		GL11.glTranslatef(f, g, h);
		Block block = ((ItemBlock) item.getItem()).field_150939_a;
		GL11.glEnable(3008);
		if (block.getRenderBlockPass() != 0) {
			GL11.glAlphaFunc(516, 0.1f);
			GL11.glEnable(3042);
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		} else {
			GL11.glAlphaFunc(516, 0.5f);
			GL11.glDisable(3042);
		}

		MultipassBlockRenderer.instance.renderInventoryBlock(block, TileEntityMetadata.getItemDamage(item), 0, renderer);
		if (block.getRenderBlockPass() == 0) {
			GL11.glAlphaFunc(516, 0.1f);
		}
		GL11.glTranslatef(-f, -g, -h);
	}

	@Override
	public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type) {
		switch (type) {
			case ENTITY:
			case EQUIPPED:
			case INVENTORY:
			case EQUIPPED_FIRST_PERSON:
				return true;
		}
		return false;
	}

	@Override
	public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data) {
		switch (type) {
			case INVENTORY:
			case ENTITY:
				render((RenderBlocks) data[0], item, 0.0f, 0.0f, 0.0f);
				break;

			case EQUIPPED:
			case EQUIPPED_FIRST_PERSON: 
				render((RenderBlocks) data[0], item, 0.5f, 0.5f, 0.5f);
				break;
		}
	}
}
