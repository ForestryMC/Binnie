// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.block;

import binnie.extratrees.ExtraTrees;
import net.minecraft.world.IBlockAccess;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.block.Block;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class BranchBlockRenderer implements ISimpleBlockRenderingHandler
{
	@Override
	public void renderInventoryBlock(final Block block, final int metadata, final int modelID, final RenderBlocks renderer) {
	}

	@Override
	public boolean renderWorldBlock(final IBlockAccess world, final int par2, final int par3, final int par4, final Block par1Block, final int modelId, final RenderBlocks renderer) {
		renderer.renderStandardBlock(par1Block, par2, par3, par4);
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(final int i) {
		return false;
	}

	@Override
	public int getRenderId() {
		return ExtraTrees.branchRenderId;
	}
}
