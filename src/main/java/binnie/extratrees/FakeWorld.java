package binnie.extratrees;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.WorldSettings.GameType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.storage.SaveHandler;

import forestry.api.arboriculture.EnumWoodType;
import forestry.arboriculture.tiles.TileWood;
import binnie.core.block.TileEntityMetadata;

public class FakeWorld extends World {
	public static FakeWorld instance = new FakeWorld();
	Block block;
	int metadata;
	TileEntity te;

	public ItemStack getWooLog() {
		if (te instanceof TileWood) {
			return TileWood.getPickBlock(block, this, 0, 0, 0);
		} else if (te instanceof TileEntityMetadata) {
			return new ItemStack(block, 1, ((TileEntityMetadata) te).getTileMetadata());
		}
		return null;
	}

	public FakeWorld() {
		super((SaveHandler) null, "", (WorldProvider) null, new WorldSettings(Long.MAX_VALUE, GameType.NOT_SET, false, false, WorldType.FLAT), null);
	}

	@Override
	public boolean setBlock(int p_147449_1_, int p_147449_2_, int p_147449_3_, Block block) {
		this.block = block;
		return true;
	}

	@Override
	public boolean setBlock(int p_147465_1_, int p_147465_2_, int p_147465_3_, Block block, int metadata, int update) {
		this.block = block;
		this.metadata = metadata;
		if (block.hasTileEntity(metadata)) {
			te = block.createTileEntity(this, metadata);
		} else {
			TileWood wood = new TileWood();
			wood.setWoodType(EnumWoodType.VALUES[metadata]);
			te = wood;
		}
		return true;
	}

	@Override
	public boolean setBlockToAir(int p_147468_1_, int p_147468_2_, int p_147468_3_) {
		return true;
	}

	@Override
	public TileEntity getTileEntity(int x, int y, int z) {
		return te;
	}

	@Override
	public void setTileEntity(int p_147455_1_, int p_147455_2_, int p_147455_3_, TileEntity p_147455_4_) {

	}

	@Override
	public Block getBlock(int x, int y, int z) {
		return block != null ? block : Blocks.air;
	}

	@Override
	public int getBlockMetadata(int p_72805_1_, int p_72805_2_, int p_72805_3_) {
		return metadata;
	}

	@Override
	public boolean setBlockMetadataWithNotify(int p_72921_1_, int p_72921_2_, int p_72921_3_, int metadata, int p_72921_5_) {
		this.metadata = metadata;
		return true;
	}

	@Override
	protected IChunkProvider createChunkProvider() {
		return null;
	}

	@Override
	protected int func_152379_p() {
		return 0;
	}

	@Override
	public Entity getEntityByID(int p_73045_1_) {
		return null;
	}

}
