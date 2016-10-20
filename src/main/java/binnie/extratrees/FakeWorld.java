package binnie.extratrees;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.*;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.storage.WorldInfo;

import javax.annotation.Nullable;

public class FakeWorld extends World {
    public static FakeWorld instance = new FakeWorld();
    Block block;
    int metadata;
    TileEntity te;

    public ItemStack getWooLog() {
//		if (te instanceof TileWood) {
//			return TileWood.getPickBlock(block, this, 0, 0, 0);
//		} else if (te instanceof TileEntityMetadata) {
//			return new ItemStack(block, 1, ((TileEntityMetadata) te).getTileMetadata());
//		}
        return null;
    }

    public FakeWorld() {
        super(null, new WorldInfo(new WorldSettings(Long.MAX_VALUE, GameType.NOT_SET, false, false, WorldType.FLAT), ""), new WorldProvider() {
            @Override
            public DimensionType getDimensionType() {
                return DimensionType.OVERWORLD;
            }
        }, null, true);
        c = new Chunk(this, 0, 0);
        //super((SaveHandler) null, "", (WorldProvider) null, new WorldSettings(Long.MAX_VALUE, GameType.NOT_SET, false, false, WorldType.FLAT), null);
    }

    //	@Override
//	public boolean setBlock(int p_147449_1_, int p_147449_2_, int p_147449_3_, Block block) {
//		this.block = block;
//		return true;
//	}
//
//	@Override
//	public boolean setBlock(int p_147465_1_, int p_147465_2_, int p_147465_3_, Block block, int metadata, int update) {
//		this.block = block;
//		this.metadata = metadata;
//		if (block.hasTileEntity(metadata)) {
//			te = block.createTileEntity(this, metadata);
//		} else {
//			TileWood wood = new TileWood();
//			wood.setWoodType(EnumWoodType.VALUES[metadata]);
//			te = wood;
//		}
//		return true;
//	}
//
    public static Chunk c;

    @Override
    protected IChunkProvider createChunkProvider() {
        return new IChunkProvider() {
            @Nullable
            @Override
            public Chunk getLoadedChunk(int x, int z) {
                return c;
            }

            @Override
            public Chunk provideChunk(int x, int z) {
                return c;
            }

            @Override
            public boolean unloadQueuedChunks() {
                return false;
            }

            @Override
            public String makeString() {
                return "";
            }
        };
    }

    @Override
    protected boolean isChunkLoaded(int x, int z, boolean allowEmpty) {
        return true;
    }

    @Override
    public Entity getEntityByID(int p_73045_1_) {
        return null;
    }

}
