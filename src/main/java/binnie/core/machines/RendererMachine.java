package binnie.core.machines;

import binnie.*;
import cpw.mods.fml.client.registry.*;
import net.minecraft.block.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.*;

// TODO unused class?
public class RendererMachine extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler {
	RenderBlocks blockRenderer;

	@Override
	public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float partialTick) {
		renderTileEntity((TileEntityMachine) entity, x, y, z, partialTick, blockRenderer);
	}

	public void renderTileEntity(TileEntityMachine entity, double x, double y, double z, float partialTick, RenderBlocks renderer) {
		if (entity != null && entity.getMachine() != null) {
			MachinePackage machinePackage = entity.getMachine().getPackage();
			machinePackage.renderMachine(entity.getMachine(), x, y, z, partialTick, renderer);
		}
	}

	public void renderInvBlock(RenderBlocks renderblocks, Block block, int i, int j) {
		TileEntity entity = block.createTileEntity(null, i);
		this.renderTileEntity((TileEntityMachine) entity, 0.0, -0.1, 0.0, 0.0625f, renderblocks);
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		if (modelID == Binnie.Machine.getMachineRenderID()) {
			renderInvBlock(renderer, block, metadata, modelID);
		}
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		TileEntityMachine tile = (TileEntityMachine) world.getTileEntity(x, y, z);
		if (tile != null && tile.getMachine() != null && tile.getMachine().getPackage() != null && tile.getMachine().getPackage().getGroup() != null && !tile.getMachine().getPackage().getGroup().customRenderer) {
			renderTileEntity(tile, x, y, z, 1.0f, renderer);
		}
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int i) {
		return true;
	}

	@Override
	public int getRenderId() {
		return Binnie.Machine.getMachineRenderID();
	}

	@Override
	public void func_147496_a(World par1World) {
		blockRenderer = new RenderBlocks(par1World);
	}
}
