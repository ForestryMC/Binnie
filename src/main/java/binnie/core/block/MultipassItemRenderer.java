package binnie.core.block;

import net.minecraft.block.*;
import net.minecraft.client.renderer.*;
import net.minecraft.item.*;
import net.minecraftforge.client.*;
import org.lwjgl.opengl.*;

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
